package ch.versusvirus.reddrop.data_access;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import ch.versusvirus.reddrop.logic.model.BloodBarometer;
import ch.versusvirus.reddrop.logic.model.BloodBarometerEntry;

public class BloodonorBarometerParser implements BloodBarometerParser {

    @Override
    public BloodBarometer parse(String input) {
        try {
            // Convert string to JSONObject
            JSONObject input_json = new JSONObject(input);

            // Extract infos
            String date = input_json.getString("date");
            String region = input_json.getString("name");

            // Extract current supplies
            JSONObject supplies = input_json.getJSONObject("blood_supplies");
            BloodBarometerEntry currentSupply = extractEntry(supplies, date);

            // Extract history
            JSONObject history = input_json.getJSONObject("history");
            List<BloodBarometerEntry> historySupply = new ArrayList<BloodBarometerEntry>();
            Iterator<String> keys = history.keys();
            while (keys.hasNext()) {
                String entryDate = keys.next();
                if (history.get(entryDate) instanceof JSONObject) {
                    JSONObject entryData = history.getJSONObject(entryDate);
                    BloodBarometerEntry bbentry = extractEntry(entryData, entryDate);
                    historySupply.add(bbentry);
                }
            }

            // Create barometer class and return
            return new BloodBarometer(region, currentSupply, historySupply);

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }


    }

    private BloodBarometerEntry extractEntry(JSONObject supplies, String date)
            throws JSONException {
        int nullPositive = supplies.getInt("0+");
        int nullNegative = supplies.getInt("0-");
        int aPositive = supplies.getInt("a+");
        int aNegative = supplies.getInt("a-");
        int abPositive = supplies.getInt("ab+");
        int abNegative = supplies.getInt("ab-");
        int bPositive = supplies.getInt("b+");
        int bNegative = supplies.getInt("b-");

        return new BloodBarometerEntry(date, nullPositive, nullNegative, aPositive, aNegative,
                abPositive, abNegative, bPositive, bNegative);
    }
}
