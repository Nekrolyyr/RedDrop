package ch.versusvirus.reddrop.data_access;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import ch.versusvirus.reddrop.logic.model.DonationListEntry;

public class DonationListParser implements LocationParser {

    @Override
    public List<DonationListEntry> parse(String input) {

        // Setup parser and obtain list of blutspendetermine
        Document doc = Jsoup.parse(input);
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
            String villageInfoFull = location.selectFirst("h3.mb-1").text();
            String villagePLZ = villageInfoFull.substring(0, villageInfoFull.indexOf(' '));
            String villageInfo = villageInfoFull.substring(villageInfoFull.indexOf(' ') + 1);
            String additionalInfo = location.selectFirst("p.mb-0").text();
            String timeRange = location.selectFirst("div.font-weight-bold").text();
            String timeStart = timeRange.substring(0, timeRange.indexOf(" Uhr - "));
            String timeEnd = timeRange.substring(timeRange.indexOf(" Uhr - ") + 7, timeRange.length() - 4);

            // Get URL and append root of website
            String infoURLEnd = urlDiv.selectFirst("a.btn").attr("href");
            String infoURL = "https://www.blutspende.ch" + infoURLEnd;

            // Create List element and append
            DonationListEntry donListEntry = new DonationListEntry(villageInfo, villagePLZ, additionalInfo, timeStart, timeEnd, weekday,
                    date, donationType, infoURL);
            donationList.add(donListEntry);

        }

        return donationList;
    }
}
