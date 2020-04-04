package ch.versusvirus.reddrop.logic.model;

public class DonationListEntry {

    private String villageInfo, villagePLZ, additionalInfo, timeStart, timeEnd, weekday, date, donationType, infoURL;

    public DonationListEntry(String villageInfo, String villagePLZ, String additionalInfo, String timeStart, String timeEnd, String weekday, String date,
                             String donationType, String infoURL) {
        this.villageInfo = villageInfo;
        this.villagePLZ = villagePLZ;
        this.additionalInfo = additionalInfo;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
        this.weekday = weekday;
        this.date = date;
        this.donationType = donationType;
        this.infoURL = infoURL;
    }

    public String getVillageInfo() {
        return villageInfo;
    }

    public String getVillagePLZ() {
        return villagePLZ;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public String getTimeStart() {
        return timeStart;
    }

    public String getTimeEnd() {
        return timeEnd;
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

}
