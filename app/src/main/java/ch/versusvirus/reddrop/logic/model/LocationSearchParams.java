package ch.versusvirus.reddrop.logic.model;

public class LocationSearchParams {
    private String PLZ;
    private String radius;
    private boolean complete = true;

    protected LocationSearchParams() {
    }

    public String getPLZ() {
        return PLZ;
    }

    public String getRadius() {
        return radius;
    }

    public boolean isComplete() {
        return complete;
    }

    public static class Builder {
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
            if ((locationSearch.radius == null || locationSearch.radius.length() <= 0) || (locationSearch.PLZ == null || locationSearch.PLZ.length() <= 3)) {
                locationSearch.complete = false;
            }
            return locationSearch;
        }
    }


}
