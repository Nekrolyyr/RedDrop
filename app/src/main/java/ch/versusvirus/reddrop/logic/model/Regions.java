package ch.versusvirus.reddrop.logic.model;

import java.util.HashMap;
import java.util.Map;

public class Regions {

    public static final Map<String, String> REGIONS = new HashMap<String, String>(){
        {
            put("gesamt", "Overall");
            put("", "Aargau-Solothurn");
            put("", "Beider Basel");
            put("", "Fribourg");
            put("", "Genf");
            put("graubuenden", "Graubünden");
            put("", "Bern/Waadt/Wallis");
            put("", "Neuchâtel-Jura");
            put("", "Ostschweiz");
            put("", "Svizzera italiana");
            put("", "Zentralschweiz");
            put("zurich", "Zürich");
        }
    };
}
