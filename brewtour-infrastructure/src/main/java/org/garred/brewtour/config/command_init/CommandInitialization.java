package org.garred.brewtour.config.command_init;

import static java.lang.String.format;
import static java.sql.Types.CLOB;
import static java.sql.Types.VARCHAR;
import static org.garred.brewtour.domain.AvailableImages.NO_IMAGES;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.sql.DataSource;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.garred.brewtour.application.IdentifierFactory;
import org.garred.brewtour.application.command.location.AbstractLocationCommand;
import org.garred.brewtour.application.command.location.AddBeerCommand;
import org.garred.brewtour.application.command.location.AddLocationCommand;
import org.garred.brewtour.application.command.location.UpdateLocationAddressCommand;
import org.garred.brewtour.application.command.location.UpdateLocationDescriptionCommand;
import org.garred.brewtour.application.command.location.UpdateLocationHoursOfOperationCommand;
import org.garred.brewtour.application.command.location.UpdateLocationImagesCommand;
import org.garred.brewtour.application.command.location.UpdateLocationPhoneCommand;
import org.garred.brewtour.application.command.location.UpdateLocationPositionCommand;
import org.garred.brewtour.application.command.location.UpdateLocationWebsiteCommand;
import org.garred.brewtour.application.command.user.AddUserCommand;
import org.garred.brewtour.brewdb.BrewDbBeer;
import org.garred.brewtour.brewdb.BrewDbBeer.BrewDbBeerList;
import org.garred.brewtour.brewdb.BrewDbBeer.Style;
import org.garred.brewtour.brewdb.BrewDbLocation;
import org.garred.brewtour.brewdb.BrewDbLocation.BrewDbLocationList;
import org.garred.brewtour.domain.AvailableImages;
import org.garred.brewtour.domain.Beer;
import org.garred.brewtour.domain.Image;
import org.garred.brewtour.domain.LocationId;
import org.garred.brewtour.domain.UserId;
import org.garred.brewtour.infrastructure.ObjectMapperFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.core.RowMapper;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CommandInitialization implements ApplicationListener<ContextRefreshedEvent> {

	private static volatile boolean completed = false;

	private final CommandGateway gateway;
	private final IdentifierFactory<LocationId> identifierFactory;
	private final JdbcTemplate jdbcTemplate;
	private final ObjectMapper objectMapper = ObjectMapperFactory.objectMapper();
	private final PreparedStatementCreatorFactory insert = new PreparedStatementCreatorFactory(
			format("INSERT INTO commands(id,object_type,data) values (?,?,?)"), VARCHAR, VARCHAR, CLOB);

	public CommandInitialization(CommandGateway gateway, IdentifierFactory<LocationId> identifierFactory, DataSource dataSource) {
		this.gateway = gateway;
		this.identifierFactory = identifierFactory;
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}


	private final List<UpdateLocationAddressCommand> addressUpdates = new ArrayList<>();
	private final List<UpdateLocationDescriptionCommand> descriptionUpdates = new ArrayList<>();
	private final List<UpdateLocationHoursOfOperationCommand> hoursOfOperationUpdates = new ArrayList<>();
	private final List<UpdateLocationImagesCommand> imagesUpdates = new ArrayList<>();
	private final List<UpdateLocationPhoneCommand> phoneUpdates = new ArrayList<>();
	private final List<UpdateLocationPositionCommand> positionUpdates = new ArrayList<>();
	private final List<UpdateLocationWebsiteCommand> websiteUpdates = new ArrayList<>();

	private final Set<AddBeerCommand> addBeerCommands = new HashSet<>();

	private final Map<String, Beer> beerMap = new HashMap<>();

	public void buildCommandTable() throws Exception {
		loadBeerData();
		final InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("json/test_locations.json");
		final BrewDbLocationList locations = this.objectMapper.readValue(in, BrewDbLocationList.class);
		for (final BrewDbLocation brewDbLocation : locations) {
			if(brewDbLocation.locality.equalsIgnoreCase("seattle")) {
				populateLists(brewDbLocation);
			}
		}
		this.jdbcTemplate.execute(
				"CREATE TABLE if not exists commands(id varchar(36) NOT NULL, " +
				"  object_type varchar(100) NOT NULL, " +
				"  FIRE_COMMAND VARCHAR(36) DEFAULT 'no'," +
				"  data clob)");
		push(this.addressUpdates);
		push(this.descriptionUpdates);
		push(this.hoursOfOperationUpdates);
		push(this.imagesUpdates);
		push(this.phoneUpdates);
		push(this.positionUpdates);
		push(this.websiteUpdates);
		push(this.addBeerCommands);

	}

	private void push(Collection<? extends AbstractLocationCommand> commands) {
		try {
			commands.stream().forEach(c -> storeInTable(c));
			commands.stream().forEach(c -> this.gateway.send(c));
		} catch(final Exception e) {
			e.printStackTrace();
		}
	}

	private void populateLists(BrewDbLocation brewDbLocation) {
		final AddLocationCommand command = new AddLocationCommand(brewDbLocation.brewery.name);
		this.gateway.sendAndWait(command);
		final LocationId locationId = this.identifierFactory.last();
		storeInTable(locationId, command);

		this.addressUpdates.add(new UpdateLocationAddressCommand(locationId, brewDbLocation.streetAddress, "",
				brewDbLocation.locality, brewDbLocation.region, brewDbLocation.postalCode));

		if(brewDbLocation.brewery.description != null) {
			this.descriptionUpdates.add(new UpdateLocationDescriptionCommand(locationId, brewDbLocation.brewery.description));
		}

		if(brewDbLocation.hoursOfOperation != null) {
			this.hoursOfOperationUpdates.add(new UpdateLocationHoursOfOperationCommand(locationId, brewDbLocation.hoursOfOperation));
		}


		final Map<String, String> bdbImages = brewDbLocation.brewery.images;
		final AvailableImages images = bdbImages == null ? NO_IMAGES
				: new AvailableImages(new Image(bdbImages.get("icon")), new Image(bdbImages.get("medium")),
						new Image(bdbImages.get("large")));
		this.imagesUpdates.add(new UpdateLocationImagesCommand(locationId, images));

		this.phoneUpdates.add(new UpdateLocationPhoneCommand(locationId, brewDbLocation.phone));

		this.positionUpdates.add(new UpdateLocationPositionCommand(locationId, brewDbLocation.latitude, brewDbLocation.longitude));

		this.websiteUpdates.add(new UpdateLocationWebsiteCommand(locationId, brewDbLocation.website));

		if (brewDbLocation.beerIds != null) {
			for (final String beerId : brewDbLocation.beerIds) {
				final Beer beer = this.beerMap.get(beerId);
				final String beerName = beer.getName();
				final AddBeerCommand addBeerCommand = new AddBeerCommand(locationId, beerName, beer.getStyle(), beer.getCategory(), beer.getAbv(), beer.getIbu());
				this.addBeerCommands.add(addBeerCommand);
			}
		}
	}

	private void loadBeerData() throws JsonParseException, JsonMappingException, IOException {
		final Set<Style> bdbStyles = new HashSet<>();
		try (final InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("json/test_beers.json")) {
			final BrewDbBeerList beers = this.objectMapper.readValue(in, BrewDbBeerList.class);
			for (final BrewDbBeer brewDbBeer : beers) {
				final Beer beer = convert(brewDbBeer);
				bdbStyles.add(brewDbBeer.style);
				this.beerMap.put(brewDbBeer.id, beer);
			}
		}
	}

	private static Beer convert(BrewDbBeer beer) {
		final Beer result = Beer.fromEvent(beer.name, beer.status, beer.style == null ? "" : beer.style.name,
				beer.style == null ? "" : beer.style.category == null ? "" : beer.style.category.name,
				beer.abv, beer.ibu, true);
		return result;
	}

	private void storeInTable(LocationId locationId, Object command) {
		try {
			final List<Object> params = new ArrayList<>();
			params.add(locationId.id);
			params.add(command.getClass().getName());
			params.add(this.objectMapper.writeValueAsString(command));
			final PreparedStatementCreator statement = this.insert.newPreparedStatementCreator(params);
			this.jdbcTemplate.update(statement);
		} catch (final JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}
	private void storeInTable(final AbstractLocationCommand command) {
		storeInTable(command.locationId, command);
	}

	@Override
	public synchronized void onApplicationEvent(ContextRefreshedEvent event) {
		if(completed) {
			return;
		}
		completed = true;
		this.gateway.send(new AddUserCommand(new UserId(UUID.randomUUID().toString()), "FBS", "dave", "password"));
//		this.gateway.send(new AddUserCommand(new UserId(UUID.randomUUID().toString()), "dave", "dave@gmail.com", "password"));
		try {
//			buildCommandTable();
			fireCommandsFromTable();
		} catch (final Exception e) {
			throw new RuntimeException(e);
		}
	}

	private void fireCommandsFromTable() {
		final String initCommandsQuery = "SELECT * FROM commands where object_type like '%AddLocationCommand' AND fire_command='yes'";
		final List<Object> initcommands = queryCommands(initCommandsQuery);
		initcommands.stream().forEach(c -> this.gateway.send(c));
		final String otherCommandquery = "SELECT * FROM commands where object_type not like '%AddLocationCommand' AND fire_command='yes' group by id,FIRE_COMMAND,object_type,data";
		final List<Object> commands = queryCommands(otherCommandquery);
		commands.stream().forEach(c -> this.gateway.send(c));
	}

	private List<Object> queryCommands(String otherCommands) {
		return this.jdbcTemplate.query(otherCommands, new RowMapper<Object>() {
			@Override
			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				try {
					@SuppressWarnings("unchecked")
					final Class<Object> type = (Class<Object>) Class.forName(rs.getString("object_type"));
					final Reader reader = rs.getCharacterStream("data");
					return CommandInitialization.this.objectMapper.readValue(reader, type);
				} catch (final ClassNotFoundException | IOException e) {
					throw new RuntimeException(e);
				}
			}
		});
	}

}
