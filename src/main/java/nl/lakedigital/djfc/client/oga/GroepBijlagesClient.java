package nl.lakedigital.djfc.client.oga;

import com.google.gson.reflect.TypeToken;
import nl.lakedigital.djfc.client.AbstractClient;
import nl.lakedigital.djfc.commons.json.JsonGroepBijlages;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class GroepBijlagesClient extends AbstractClient {
    private final String URL_LIJST_GROEPEN = "/rest/bijlage/alleGroepen";

    public GroepBijlagesClient(String basisUrl) {
        super(basisUrl);
    }

    public GroepBijlagesClient() {
    }

    @Override
    protected Type getTypeToken() {
        return new TypeToken<ArrayList<JsonGroepBijlages>>() {
        }.getType();
    }

    public List<JsonGroepBijlages> lijst(String soortEntiteit, Long entiteitId) {
        return uitvoerenGetLijst(URL_LIJST_GROEPEN, JsonGroepBijlages.class, soortEntiteit, entiteitId.toString());
    }

    public String opslaan(List<JsonGroepBijlages> jsonAdressen, Long ingelogdeGebruiker, String trackAndTraceId) {
        return null;
    }

    public void verwijder(String soortEntiteit, Long entiteitId, Long ingelogdeGebruiker, String trackAndTraceId) {

    }

    public List<JsonGroepBijlages> zoeken(String zoekterm) {
        return null;
    }
}
