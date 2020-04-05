package ch.versusvirus.reddrop.logic.model;

import java.util.LinkedHashMap;
import java.util.Map;

public class Regions {

    public static final Map<String, String> REGIONS = new LinkedHashMap<String, String>(){
        {
            put("gesamt", "Overall");
            //put("", "Aargau-Solothurn");
            put("basel", "Beider Basel");
            put("fribourg", "Fribourg");
            put("geneve", "Genf");
            put("graubuenden", "Graubünden");
            //put("", "Bern/Waadt/Wallis");
            put("neuchatel_jura", "Neuchâtel-Jura");
            //put("", "Ostschweiz");
            put("svizzera_italiana", "Svizzera italiana");
            put("zentralschweiz", "Zentralschweiz");
            put("zuerich", "Zürich");
        }
    };
}
