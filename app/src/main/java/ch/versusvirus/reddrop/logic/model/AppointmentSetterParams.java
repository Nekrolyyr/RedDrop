package ch.versusvirus.reddrop.logic.model;

public class AppointmentSetterParams {
    private String id;
    private int slotIdx;
    private String action;
    private String password = "aZo32S1s0Lp?"; // TODO add as resource

    protected AppointmentSetterParams() {
    }

    public String getId() {
        return this.id;
    }
    public int getSlotIdx() {
        return this.slotIdx;
    }
    public String getAction() { return this.action; }
    public String getPassword() { return this.password; }


    public static class Builder {
        AppointmentSetterParams appointmentSetterParams;

        public Builder() {
            appointmentSetterParams = new AppointmentSetterParams();
        }

        public Builder id(String id) {
            appointmentSetterParams.id = id;
            return this;
        }
        public Builder slotIdx(int slotIdx) {
            appointmentSetterParams.slotIdx = slotIdx;
            return this;
        }
        public Builder action(String action) {
            appointmentSetterParams.action = action;
            return this;
        }

        public AppointmentSetterParams build() {
            if (appointmentSetterParams.id == null) {
                return null;
            }
            if (appointmentSetterParams.action == null) {
                return null;
            }
            return appointmentSetterParams;
        }
    }


}
