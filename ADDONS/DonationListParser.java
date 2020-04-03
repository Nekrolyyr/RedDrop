// This class uses the Jsoup libary (jsoup-1.13.1) https://jsoup.org/
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


public class DonationListParser {

    List<DonationListEntry> parse(String sourceCode) {
        // Setup parser and obtain list of blutspendetermine
        Document doc = Jsoup.parse(sourceCode);
        Elements donDates = doc.select("div.blutspendetermine");

        // Setup returning list
        List<DonationListEntry> donationList = new ArrayList<DonationListEntry>();

        // Iterate over all appointments
        for (Element donDate : donDates) {
            // Get upper most divs
            Element info = donDate.selectFirst("div.info");
            Element location = donDate.selectFirst("div.location");
            Element urlDiv = donDate.selectFirst("div.cta");

            // Get corresponding entries of interest
            String weekday = info.selectFirst("div.weekday").text();
            String date = info.selectFirst("div.date").text();
            String donationType = info.selectFirst("div.type").text();
            String villageInfo = location.selectFirst("h3.mb-1").text();
            String additionalInfo = location.selectFirst("p.mb-0").text();
            String timeRange = location.selectFirst("div.font-weight-bold").text();

            // Get URL and append root of website
            String infoURLEnd = urlDiv.selectFirst("a.btn").attr("href");
            String infoURL = "https://www.blutspende.ch" + infoURLEnd;

            // Create List element and append
            DonationListEntry donListEntry = new DonationListEntry(villageInfo, additionalInfo, timeRange, weekday,
                    date, donationType, infoURL);
            donationList.add(donListEntry);

        }

        return donationList;
    }



}

class DonationListEntry {

    String villageInfo, additionalInfo, timeRange, weekday, date, donationType, infoURL;

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

}
