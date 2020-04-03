package ch.versusvirus.reddrop.logic.model;

public class LocationSearchParams {
    private String PLZ;
    private String radius;

    protected LocationSearchParams() {
    }

    public String getPLZ() {
        return PLZ;
    }

    public String getRadius() {
        return radius;
    }

    public class Builder {
        LocationSearchParams locationSearch;

        public Builder() {
            locationSearch = new LocationSearchParams();
        }

        public Builder PLZ(String plz) {
            locationSearch.PLZ = plz;
            return this;
        }

        public Builder radius(String radius) {
            locationSearch.radius = radius;
            return this;
        }

        public LocationSearchParams build() {
            if (PLZ == null || radius == null) {
                return null;
            }
            return locationSearch;
        }
    }


}
