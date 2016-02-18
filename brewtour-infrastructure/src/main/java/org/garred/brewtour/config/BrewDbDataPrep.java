package org.garred.brewtour.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.garred.brewdb.domain.Beer;
import org.garred.brewdb.domain.Location;
import org.garred.brewtour.infrastructure.ObjectMapperFactory;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;

public class BrewDbDataPrep {

	public static final String LOCATION_FILE = "json/init_locations.json";
	public static final String BREWERY_FILE = "json/init_breweries.json";
	public static final String BEER_FILE = "json/init_beers.json";
	private static final BigDecimal LONG_MAX = new BigDecimal("-122.2");
	private static final BigDecimal LONG_MIN = new BigDecimal("-122.4");
	private static final BigDecimal LAT_MAX = new BigDecimal("47.65");
	private static final BigDecimal LAT_MIN = new BigDecimal("47.53");
	private static List<String> targetLocations = new ImmutableList.Builder<String>()
			// Fremont
			.add("Rooftop Brewing Company")
			.add("Fremont Brewing")
			.add("Gilligan's Brewing Co")
			.add("Populuxe Brewing")
			.add("Lucky Envelope Brewing")
			.add("Stoup Brewing")
			.add("Reuben's Brews")
			.add("Peddler Brewing Company")
			.add("Hilliard's Beer")
			.add("NW Peaks Brewery")

			//Georgetown
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

	public BrewDbDataPrep() {
		try {
			loadLocationData();
			buildBreweryList();
			loadBeerData();
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

	public static void main(String[] args) throws JsonParseException, JsonMappingException, IOException {
		final BrewDbDataPrep builder = new BrewDbDataPrep();
		Set<String> locationNames = builder.locationMap.values().stream()
			.map(l -> l.brewery.name)
			.collect(Collectors.toSet());
		locationNames = new TreeSet<>(locationNames);
		locationNames.forEach(System.out::println);
	}

}
