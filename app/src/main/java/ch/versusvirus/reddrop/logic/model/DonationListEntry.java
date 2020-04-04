package ch.versusvirus.reddrop.logic.model;

import com.google.gson.Gson;

public class DonationListEntry {

    private String villageInfo, additionalInfo, timeRange, weekday, date, donationType, infoURL;

    public DonationListEntry(String villageInfo, String additionalInfo, String timeRange, String weekday, String date,
                             String donationType, String infoURL) {
        this.villageInfo = villageInfo;
        this.additionalInfo = additionalInfo;
        this.timeRange = timeRange;
        this.weekday = weekday;
        this.date = date;
        this.donationType = donationType;
        this.infoURL = infoURL;
    }

    public String getVillageInfo() {
        return villageInfo;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public String getTimeRange() {
        return timeRange;
    }

    public String getWeekday() {
        return weekday;
    }

    public String getDate() {
        return date;
    }

    public String getDonationType() {
        return donationType;
    }

    public String getInfoURL() {
        return infoURL;
    }

    public static DonationListEntry fromJson(String json) {
        return (new Gson()).fromJson(json, DonationListEntry.class);
    }

    public String getId() {
        return additionalInfo + "_" + date.replace(".", "_");
    }

    public String asJson() {
        return (new Gson()).toJson(this);
    }
}
