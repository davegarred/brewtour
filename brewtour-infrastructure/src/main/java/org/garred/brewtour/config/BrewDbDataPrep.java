package org.garred.brewtour.config;

import static org.garred.brewtour.config.CustomAddBeerCommand.genericCustomBeerCommand;
import static org.garred.brewtour.config.CustomLocationCommand.genericCustomLocationCommand;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.garred.brewdb.domain.Beer;
import org.garred.brewdb.domain.BeerStyle;
import org.garred.brewdb.domain.Brewery;
import org.garred.brewdb.domain.Images;
import org.garred.brewdb.domain.Location;
import org.garred.brewtour.application.command.beer.AddBeerCommand;
import org.garred.brewtour.application.command.location.UpdateLocationWebsiteCommand;
import org.garred.brewtour.infrastructure.ObjectMapperFactory;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;

public class BrewDbDataPrep {

	private int idCounter = 1;

	public static final String LOCATION_FILE = "json/init_locations.json";
	public static final String BREWERY_FILE = "json/init_breweries.json";
	public static final String BEER_FILE = "json/init_beers.json";
	public static final String CUSTOM_LOCATION_FILE = "json/custom_location.json";
	public static final String CUSTOM_BEER_FILE = "json/custom_beers.json";
//	private static final BigDecimal LONG_MAX = new BigDecimal("-122.2");
//	private static final BigDecimal LONG_MIN = new BigDecimal("-122.4");
//	private static final BigDecimal LAT_MAX = new BigDecimal("47.65");
//	private static final BigDecimal LAT_MIN = new BigDecimal("47.53");
	private static List<String> targetLocations = new ImmutableList.Builder<String>()
			// Fremont
			.add("Rooftop Brewing Company")
			.add("Fremont Brewing")
			.add("Outlander Brewing")
			.add("Populuxe Brewing")
			.add("Lucky Envelope Brewing")
			.add("Stoup Brewing")
			.add("Reuben's Brews")
			.add("Peddler Brewing Company")
			.add("Hilliard's Beer")
			.add("NW Peaks Brewery")

			//Georgetown & South Seattle
			.add("Spinnaker Bay Brewing")
			.add("Machine House Brewery")
			.add("Seattle Cider Company")
			.add("Two Beers Brewing Company")
			.add("Georgetown Brewing Company")
			.add("Schooner Exact Brewing Company")
			.add("Seapine Brewing Company")
			.add("Flying Lion Brewing")
			.build();

	private final ObjectMapper objectMapper = ObjectMapperFactory.objectMapper();

	public final Map<String, Location> locationMap = new HashMap<>();
	public final Map<String, BreweryData> breweryMap = new HashMap<>();
	public final Map<String, Set<Beer>> beerMap = new HashMap<>();
	public final List<CustomLocationCommand> customLocationCommands = new ArrayList<>();
	public final List<CustomAddBeerCommand> customAddBeerCommands = new ArrayList<>();

	public BrewDbDataPrep() {
		try {
			loadLocationData();
			buildBreweryList();
			loadBeerData();
			addCustomLocations();
			buildCustomAddBeerCommands();
			writeAll();
		} catch (final IOException e) {
			throw new RuntimeException(e);
		}
	}


	private void writeAll() throws JsonGenerationException, JsonMappingException, IOException {
		try(OutputStream out = new FileOutputStream("src/main/resources/" + LOCATION_FILE)) {
			this.objectMapper.writeValue(out, this.locationMap);
		}
		try(OutputStream out = new FileOutputStream("src/main/resources/" + BREWERY_FILE)) {
			this.objectMapper.writeValue(out, this.breweryMap);
		}
		try(OutputStream out = new FileOutputStream("src/main/resources/" + BEER_FILE)) {
			this.objectMapper.writeValue(out, this.beerMap);
		}
		try(OutputStream out = new FileOutputStream("src/main/resources/" + CUSTOM_LOCATION_FILE)) {
			this.objectMapper.writeValue(out, this.customLocationCommands);
		}
		try(OutputStream out = new FileOutputStream("src/main/resources/" + CUSTOM_BEER_FILE)) {
			this.objectMapper.writeValue(out, this.customAddBeerCommands);
		}
	}

	private static boolean useThisLocation(Location brewDbLocation) {
		if(brewDbLocation == null || brewDbLocation.latitude == null || brewDbLocation.longitude == null) {
			return false;
		}
//		return true;
		return brewDbLocation.brewery != null && targetLocations.contains(brewDbLocation.brewery.name);
//		if (brewDbLocation.latitude.compareTo(LAT_MIN) < 1 || brewDbLocation.latitude.compareTo(LAT_MAX) > 1
//				|| brewDbLocation.longitude.compareTo(LONG_MIN) < 1
//				|| brewDbLocation.longitude.compareTo(LONG_MAX) > 1) {
//			return true;
//		}
//		return false;
	}

	public void loadLocationData() throws JsonParseException, JsonMappingException, IOException {
		final File locationfolder = new File("src/main/resources/json/location");
		for (final File file : locationfolder.listFiles()) {
			try (InputStream in = new FileInputStream(file)) {
				final Location location = this.objectMapper.readValue(in, Location.class);
				if (useThisLocation(location)) {
					this.locationMap.put(location.id, location);
				}
			}
		}
	}

	private void buildBreweryList() {
		for (final Location location : this.locationMap.values()) {
			BreweryData brewery = this.breweryMap.get(location.breweryId);
			if (brewery == null) {
				brewery = new BreweryData(location.breweryId, location.brewery.name);
				this.breweryMap.put(location.breweryId, brewery);
			}
			brewery.locationIds.add(location.id);
		}
	}

	public void loadBeerData() throws JsonParseException, JsonMappingException, IOException {
		final File beerfolder = new File("src/main/resources/json/beer");
		for (final File file : beerfolder.listFiles()) {
			try (InputStream in = new FileInputStream(file)) {
				final Beer beer = this.objectMapper.readValue(in, Beer.class);
				Set<Beer> beerList = this.beerMap.get(beer.breweryId);
				if (beerList == null) {
					beerList = new HashSet<>();
					this.beerMap.put(beer.breweryId, beerList);
				}
				beerList.add(beer);
			}
		}
	}

	private void addCustomLocations() {
		final Location location = bdbLocation("Cloudburst Brewing",
				"2116 Western Ave",
				"98121",
				"http://cloudburstbrew.com/",
				null,
				"47.6112849",
				"-122.345076",
				"resources/img/breweries/initial/cloudburst.png");
		this.locationMap.put(location.id, location);
		final String breweryId = location.breweryId;
		this.breweryMap.put(breweryId, buildBreweryData(location));

		final Set<Beer> beerSet = new HashSet<>();
		beerSet.add(buildBdbBeer("Pigeonhole IPA", "IPA with Chinook, Citra, Amarillo, and LOTS of Simcoe", null, null, "American-Style India Pale Ale"));
		beerSet.add(buildBdbBeer("Born Again", "Strong Blonde Ale spiced with grains of paradise, long peppercorns, & coriander", null, null, null));
		beerSet.add(buildBdbBeer("Punch Drunk Love", "Strong Pale Ale with Sweet Cherry puree and Pineapple juice.", null, null, null));
		beerSet.add(buildBdbBeer("Psycho Hose Beast", "Triple IPA", null, null, null));
		beerSet.add(buildBdbBeer("Market Fresh Saison", "Version 2: Grapefruit & Rosemary", null, null, null));
		beerSet.add(buildBdbBeer("Aw Shucks", "Oyster Stout", null, null, null));
		beerSet.add(buildBdbBeer("Unreliable Narrator", "Dry Hopped Pale with GR Mandarina & NZ Nelson Sauvin hops", null, null, null));
		beerSet.add(buildBdbBeer("Cure-All", "Nitro Milk Stout", null, null, null));
		beerSet.add(buildBdbBeer("Hotline Bling IPA", "IPA with Mosaic, Galaxy, & El Dorado", null, null, null));
		beerSet.add(buildBdbBeer("Hoppy Little Clouds", "Dry-Hopped Pilsner", null, null, null));
		this.beerMap.put(breweryId, beerSet);
	}
	private static Beer buildBdbBeer(final String beerName, final String beerDescription, final String abv, final String ibu,
			final String style) {
		final Beer beer = new Beer();
		beer.name = beerName;
		beer.description = beerDescription;
		beer.ibu = ibu;
		beer.abv = abv;
		beer.style = new BeerStyle();
		beer.style.name = style;
		return beer;
	}
	private static BreweryData buildBreweryData(final Location location) {
		final BreweryData brewery = new BreweryData(location.breweryId, location.brewery.name);
		brewery.locationIds.add(location.id);
		return brewery;
	}
	private Location bdbLocation(final String breweryName, final String address1, final String zip,
			final String website, final String description, final String lat, final String longit, final String image) {
		final String locationId = nextBDBId();
		final String breweryId = nextBDBId();
		final Location location = new Location();
		location.id = locationId;
		location.breweryId = breweryId;
		final Brewery brewery = new Brewery();
		brewery.images = new Images();
		brewery.images.medium = image;
		brewery.id = breweryId;
		brewery.name = breweryName;
		brewery.description = description;
		location.brewery = brewery;
		location.latitude = new BigDecimal(lat);
		location.longitude = new BigDecimal(longit);
		location.website = website;
		location.streetAddress = address1;
		location.locality = "Seattle";
		location.region = "WA";
		location.postalCode = zip;
		return location;
	}
	private String nextBDBId() {
		return "bdb" + this.idCounter++;
	}
	private void buildCustomAddBeerCommands() {
		this.customLocationCommands.add(genericCustomLocationCommand("Lucky Envelope Brewing", new UpdateLocationWebsiteCommand(null, "http://www.luckyenvelopebrewing.com/"), this.objectMapper));
		this.customLocationCommands.add(genericCustomLocationCommand("Populuxe Brewing", new UpdateLocationWebsiteCommand(null, "https://www.facebook.com/PopuluxeBrewing/"), this.objectMapper));
		this.customLocationCommands.add(genericCustomLocationCommand("NW Peaks Brewery", new UpdateLocationWebsiteCommand(null, "http://www.nwpeaksbrewery.com/"), this.objectMapper));

		this.customAddBeerCommands.add(genericCustomBeerCommand("Lucky Envelope Brewing", new AddBeerCommand("Boss Fight Triple IPA", "Triple IPA Season is upon us! Intense hop flavors and aromas from heavy double dry hopping at over 2 pounds per barrel. Our 3x IPA is bigger, meaner, and has a lot more hit points than your standard IPA.", null, null, null, "Imperial or Double India Pale Ale", new BigDecimal("10.3"), new BigDecimal("104"))));
		this.customAddBeerCommands.add(genericCustomBeerCommand("Lucky Envelope Brewing", new AddBeerCommand("ENIAC 2.0 Mosaic India Pale Ale", "Version 2.0 of our ENIAC IPA screams Mosaic hops and a balanced malt profile with notes of juicy tropical fruit and resinous pine.", null, null, null, "American-Style India Pale Ale", new BigDecimal("6.4"), new BigDecimal("68"))));
		this.customAddBeerCommands.add(genericCustomBeerCommand("Lucky Envelope Brewing", new AddBeerCommand("50th Street India Pale Ale", "Our San Diego-inspired IPA is a bitter and grapefruit-forward hoppy beer. The pilsner malt base is crisp and clean to let the hop profile shine.", null, null, null, "American-Style India Pale Ale", new BigDecimal("6.8"), new BigDecimal("80"))));
		this.customAddBeerCommands.add(genericCustomBeerCommand("Lucky Envelope Brewing", new AddBeerCommand("Galaxy Session IPA", "This hoppy, yet sessionable beer packs the hop kick of a traditional IPA but with less alcohol. Australian Galaxy hops provide a juicy, tropical fruit aroma.", null, null, null, "Session India Pale Ale", new BigDecimal("4.6"), new BigDecimal("52"))));
		this.customAddBeerCommands.add(genericCustomBeerCommand("Lucky Envelope Brewing", new AddBeerCommand("Imperial Porter", "The imperial robust porter packs complex malt flavor while still being able to enjoy a pint. This beer is bold and chewy with flavors of espresso, milk chocolate, and caramel.", null, null, null, "American-Style Imperial Porter", new BigDecimal("7.5"), new BigDecimal("42"))));
		this.customAddBeerCommands.add(genericCustomBeerCommand("Lucky Envelope Brewing", new AddBeerCommand("Flying Envelope Washington Lager", "This American Craft Lager uses 100% Washington-grown ingredients and our house lager strain. Light and crisp, this beer showcases local heirloom malt with a smooth bready finish.", null, null, null, null, new BigDecimal("4.7"), new BigDecimal("26"))));
		this.customAddBeerCommands.add(genericCustomBeerCommand("Lucky Envelope Brewing", new AddBeerCommand("Buddha’s Hand Pale Ale", "A classic Pacific Northwest pale ale infused with zest from Buddha’s Hand and Chinese pomelo citrus fruits. Both citrus fruits are native to southeastern Asia", null, null, null, "American-Style Pale Ale", new BigDecimal("5.1"), new BigDecimal("38"))));

	}

	public static void main(String[] args) throws JsonParseException, JsonMappingException, IOException {
		final BrewDbDataPrep builder = new BrewDbDataPrep();
		Set<String> locationNames = builder.locationMap.values().stream()
			.map(l -> l.brewery.name)
			.collect(Collectors.toSet());
		locationNames = new TreeSet<>(locationNames);
		locationNames.forEach(System.out::println);
	}

}
