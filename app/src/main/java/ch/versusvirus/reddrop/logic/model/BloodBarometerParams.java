package ch.versusvirus.reddrop.logic.model;

public class BloodBarometerParams {
    private String location;

    protected BloodBarometerParams() {
    }

    public String getLocation() {
        return location;
    }

    public class Builder {
        BloodBarometerParams bloodBarometerParams;

        public Builder() {
            bloodBarometerParams = new BloodBarometerParams();
        }

        public Builder location(String location) {
            bloodBarometerParams.location = location;
            return this;
        }

        public BloodBarometerParams build() {
            if (location == null) {
                return null;
            }
            return bloodBarometerParams;
        }
    }


}
