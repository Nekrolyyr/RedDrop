package ch.versusvirus.reddrop.data_access;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import ch.versusvirus.reddrop.logic.model.Appointment;
import ch.versusvirus.reddrop.logic.model.BloodBarometer;
import ch.versusvirus.reddrop.logic.model.BloodBarometerEntry;

public class BloodonorAppointmentParser implements AppointmentParser {

    @Override
    public Appointment parse(String input) {
        try {
            // Convert string to JSONObject
            JSONObject input_json = new JSONObject(input);

            // Extract infos
            String id = input_json.getString("id");
            int nSlots = input_json.getInt("n_slots");
            int maxCapacityPerSlot = input_json.getInt("max_capacity_per_slot");
            JSONArray caps = input_json.getJSONArray("remaining_capacities");
            int[] remainingCapacities = new int[caps.length()];

            for (int i = 0; i < caps.length(); ++i) {
                remainingCapacities[i] = caps.optInt(i);
            }

            if (input_json.has("success")) {
                Boolean success = input_json.getBoolean("success");
                String action = input_json.getString("action");
                return new Appointment(id, nSlots, maxCapacityPerSlot, remainingCapacities, success, action);
            } else {
                return new Appointment(id, nSlots, maxCapacityPerSlot, remainingCapacities);
            }

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }


    }

}
