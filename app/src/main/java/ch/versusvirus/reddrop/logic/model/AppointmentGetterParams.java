package ch.versusvirus.reddrop.logic.model;

public class AppointmentGetterParams {
    private String id;
    private int nSlots;
    private String password = "aZo32S1s0Lp?"; // TODO add as resource

    public AppointmentGetterParams() {
    }

    public String getId() {
        return this.id;
    }
    public int getnSlots() {
        return this.nSlots;
    }
    public String getPassword() { return this.password; }


    public static class Builder {
        AppointmentGetterParams appointmentGetterParams;

        public Builder() {
            appointmentGetterParams = new AppointmentGetterParams();
        }

        public Builder id(String id) {
            appointmentGetterParams.id = id;
            return this;
        }
        public Builder nSlots(int nSlots) {
            appointmentGetterParams.nSlots = nSlots;
            return this;
        }

        public AppointmentGetterParams build() {
            if (appointmentGetterParams.id == null) {
                return null;
            }
            return appointmentGetterParams;
        }
    }


}
