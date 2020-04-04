package ch.versusvirus.reddrop.logic.model;

public class User {
    private String PLZ;

    public static User getCurrent() {
        //TODO: load from key/Value store
        return new User();
    }

    public String getPLZ() {
        return PLZ;
    }
}
