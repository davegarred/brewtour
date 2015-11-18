package org.garred.brewtour.brewdb;

import java.math.BigDecimal;
import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BrewDbBeer {

	public String id, name, description,isOrganic,status,statusDisplay;
	public String createDate,updateDate;
	public int styleId;
	public BigDecimal abv,ibu;
	public Style style;

	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Style {
		public int id,categoryId;
		public String name,shortName,description;
		public String createDate,updateDate;
		public BigDecimal ibuMin,ibuMax,abvMin,abvMax,srmMin,srmMax,ogMin,ogMax,fgMin,fgMax;
		public Category category;
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((abvMax == null) ? 0 : abvMax.hashCode());
			result = prime * result + ((abvMin == null) ? 0 : abvMin.hashCode());
			result = prime * result + ((category == null) ? 0 : category.hashCode());
			result = prime * result + categoryId;
			result = prime * result + ((createDate == null) ? 0 : createDate.hashCode());
			result = prime * result + ((description == null) ? 0 : description.hashCode());
			result = prime * result + ((fgMax == null) ? 0 : fgMax.hashCode());
			result = prime * result + ((fgMin == null) ? 0 : fgMin.hashCode());
			result = prime * result + ((ibuMax == null) ? 0 : ibuMax.hashCode());
			result = prime * result + ((ibuMin == null) ? 0 : ibuMin.hashCode());
			result = prime * result + id;
			result = prime * result + ((name == null) ? 0 : name.hashCode());
			result = prime * result + ((ogMax == null) ? 0 : ogMax.hashCode());
			result = prime * result + ((ogMin == null) ? 0 : ogMin.hashCode());
			result = prime * result + ((shortName == null) ? 0 : shortName.hashCode());
			result = prime * result + ((srmMax == null) ? 0 : srmMax.hashCode());
			result = prime * result + ((srmMin == null) ? 0 : srmMin.hashCode());
			result = prime * result + ((updateDate == null) ? 0 : updateDate.hashCode());
			return result;
		}
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Style other = (Style) obj;
			if (abvMax == null) {
				if (other.abvMax != null)
					return false;
			} else if (!abvMax.equals(other.abvMax))
				return false;
			if (abvMin == null) {
				if (other.abvMin != null)
					return false;
			} else if (!abvMin.equals(other.abvMin))
				return false;
			if (category == null) {
				if (other.category != null)
					return false;
			} else if (!category.equals(other.category))
				return false;
			if (categoryId != other.categoryId)
				return false;
			if (createDate == null) {
				if (other.createDate != null)
					return false;
			} else if (!createDate.equals(other.createDate))
				return false;
			if (description == null) {
				if (other.description != null)
					return false;
			} else if (!description.equals(other.description))
				return false;
			if (fgMax == null) {
				if (other.fgMax != null)
					return false;
			} else if (!fgMax.equals(other.fgMax))
				return false;
			if (fgMin == null) {
				if (other.fgMin != null)
					return false;
			} else if (!fgMin.equals(other.fgMin))
				return false;
			if (ibuMax == null) {
				if (other.ibuMax != null)
					return false;
			} else if (!ibuMax.equals(other.ibuMax))
				return false;
			if (ibuMin == null) {
				if (other.ibuMin != null)
					return false;
			} else if (!ibuMin.equals(other.ibuMin))
				return false;
			if (id != other.id)
				return false;
			if (name == null) {
				if (other.name != null)
					return false;
			} else if (!name.equals(other.name))
				return false;
			if (ogMax == null) {
				if (other.ogMax != null)
					return false;
			} else if (!ogMax.equals(other.ogMax))
				return false;
			if (ogMin == null) {
				if (other.ogMin != null)
					return false;
			} else if (!ogMin.equals(other.ogMin))
				return false;
			if (shortName == null) {
				if (other.shortName != null)
					return false;
			} else if (!shortName.equals(other.shortName))
				return false;
			if (srmMax == null) {
				if (other.srmMax != null)
					return false;
			} else if (!srmMax.equals(other.srmMax))
				return false;
			if (srmMin == null) {
				if (other.srmMin != null)
					return false;
			} else if (!srmMin.equals(other.srmMin))
				return false;
			if (updateDate == null) {
				if (other.updateDate != null)
					return false;
			} else if (!updateDate.equals(other.updateDate))
				return false;
			return true;
		}
		
	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Category {
		public int id;
		public String name,description;
		public String createDate;
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((createDate == null) ? 0 : createDate.hashCode());
			result = prime * result + ((description == null) ? 0 : description.hashCode());
			result = prime * result + id;
			result = prime * result + ((name == null) ? 0 : name.hashCode());
			return result;
		}
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Category other = (Category) obj;
			if (createDate == null) {
				if (other.createDate != null)
					return false;
			} else if (!createDate.equals(other.createDate))
				return false;
			if (description == null) {
				if (other.description != null)
					return false;
			} else if (!description.equals(other.description))
				return false;
			if (id != other.id)
				return false;
			if (name == null) {
				if (other.name != null)
					return false;
			} else if (!name.equals(other.name))
				return false;
			return true;
		}
		
	}

	@SuppressWarnings("serial")
	public static class BrewDbBeerList extends ArrayList<BrewDbBeer>{}
}
