package org.garred.brewtour.application;

import static java.math.RoundingMode.HALF_UP;
import static java.util.Collections.emptyList;

import java.math.BigDecimal;
import java.util.List;

import org.garred.brewtour.api.AddBeer;
import org.garred.brewtour.api.AddBeerReview;
import org.garred.brewtour.api.AddLocationReview;
import org.garred.brewtour.api.BeerAvailable;
import org.garred.brewtour.api.BeerUnavailable;
import org.garred.brewtour.api.ModifyBeer;
import org.garred.brewtour.api.ModifyLocationDescription;
import org.garred.brewtour.service.LocationService;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Location extends AbstractEntity<LocationId> implements LocationService {

	private final String brewDbId;
	private final String name;
	private String description;
	private final BigDecimal latitude;
	private final BigDecimal longitude;
	private final AvailableImages images;
	private final List<Beer> beers;
	private final List<Review> reviews;

	private BigDecimal averageStars;

	@JsonCreator
	public Location(@JsonProperty("locationId") LocationId locationId,
			@JsonProperty("brewDbId") String brewDbId,
			@JsonProperty("name") String name,
			@JsonProperty("description") String description,
			@JsonProperty("latitude") BigDecimal latitude,
			@JsonProperty("longitude") BigDecimal longitude,
			@JsonProperty("images") AvailableImages images,
			@JsonProperty("beers") List<Beer> beers,
			@JsonProperty("reviews") List<Review> reviews) {
		super(locationId);
		this.brewDbId = brewDbId;
		this.name = name;
		this.description = description;
		this.latitude = latitude;
		this.longitude = longitude;
		this.images = images;
		this.beers = beers;
		this.reviews = reviews;
		this.updateReviewAverage();
	}

	@Override
	public void addBeer(AddBeer addBeer) {
		if(findBeer(addBeer.name) != null) {
			//TODO translate this exception back to the user
			throw new RuntimeException("Can not add a beer with the same name");
		}
		final Beer beer = new Beer(null, addBeer.name, null, addBeer.style, addBeer.category, addBeer.abv, addBeer.ibu, true, emptyList());
		this.beers.add(beer);
	}
	@Override
	public void modifyBeer(ModifyBeer modifyBeer) {
		final Beer beer = findBeer(modifyBeer.name);
		beer.setStyle(modifyBeer.style);
		beer.setCategory(modifyBeer.category);
		beer.setAbv(modifyBeer.abv);
		beer.setIbu(modifyBeer.ibu);
	}
	@Override
	public void beerAvailable(BeerAvailable beerAvailable) {
		final Beer beer = findBeer(beerAvailable.name);
		beer.setAvailable(true);
	}
	@Override
	public void beerUnavailable(BeerUnavailable beerUnavailable) {
		final Beer beer = findBeer(beerUnavailable.name);
		beer.setAvailable(false);
	}

	@Override
	public void modifyLocationDescription(ModifyLocationDescription modifyDescription) {
		this.description = modifyDescription.description;
	}

	@Override
	public void addLocationReview(AddLocationReview locationReview) {
		this.reviews.add(locationReview.review);
		updateReviewAverage();
	}

	@Override
	public void addBeerReview(AddBeerReview beerReview) {
		final Beer beer = findBeer(beerReview.name);
		beer.getReviews().add(beerReview.review);
		beer.updateReviewAverage();
	}

	private void updateReviewAverage() {
		int count = 0;
		int totalStars = 0;
		for(final Review review : this.reviews) {
			count ++;
			totalStars += review.stars;
		}
		if(count > 0) {
			this.averageStars = new BigDecimal(((double)totalStars) / (double)count).setScale(1, HALF_UP);
		}
	}

	private Beer findBeer(String beername) {
		for(final Beer beer : this.beers) {
			if(beer.getName().equals(beername)) {
				return beer;
			}
		}
		return null;
	}

	public String getDescription() {
		return this.description;
	}

	public BigDecimal getAverageStars() {
		return this.averageStars;
	}

	public String getBrewDbId() {
		return this.brewDbId;
	}

	public String getName() {
		return this.name;
	}

	public BigDecimal getLatitude() {
		return this.latitude;
	}

	public BigDecimal getLongitude() {
		return this.longitude;
	}

	public AvailableImages getImages() {
		return this.images;
	}

	public List<Beer> getBeers() {
		return this.beers;
	}

	public List<Review> getReviews() {
		return this.reviews;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((this.beers == null) ? 0 : this.beers.hashCode());
		result = prime * result + ((this.brewDbId == null) ? 0 : this.brewDbId.hashCode());
		result = prime * result + ((this.description == null) ? 0 : this.description.hashCode());
		result = prime * result + ((this.images == null) ? 0 : this.images.hashCode());
		result = prime * result + ((this.latitude == null) ? 0 : this.latitude.hashCode());
		result = prime * result + ((this.longitude == null) ? 0 : this.longitude.hashCode());
		result = prime * result + ((this.name == null) ? 0 : this.name.hashCode());
		result = prime * result + ((this.reviews == null) ? 0 : this.reviews.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		final Location other = (Location) obj;
		if (this.beers == null) {
			if (other.beers != null)
				return false;
		} else if (!this.beers.equals(other.beers))
			return false;
		if (this.brewDbId == null) {
			if (other.brewDbId != null)
				return false;
		} else if (!this.brewDbId.equals(other.brewDbId))
			return false;
		if (this.description == null) {
			if (other.description != null)
				return false;
		} else if (!this.description.equals(other.description))
			return false;
		if (this.images == null) {
			if (other.images != null)
				return false;
		} else if (!this.images.equals(other.images))
			return false;
		if (this.latitude == null) {
			if (other.latitude != null)
				return false;
		} else if (!this.latitude.equals(other.latitude))
			return false;
		if (this.longitude == null) {
			if (other.longitude != null)
				return false;
		} else if (!this.longitude.equals(other.longitude))
			return false;
		if (this.name == null) {
			if (other.name != null)
				return false;
		} else if (!this.name.equals(other.name))
			return false;
		if (this.reviews == null) {
			if (other.reviews != null)
				return false;
		} else if (!this.reviews.equals(other.reviews))
			return false;
		return true;
	}

}
