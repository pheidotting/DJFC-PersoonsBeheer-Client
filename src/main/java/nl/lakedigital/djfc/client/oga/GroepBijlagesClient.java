package nl.lakedigital.djfc.client.oga;

import com.google.gson.reflect.TypeToken;
import nl.lakedigital.djfc.client.AbstractClient;
import nl.lakedigital.djfc.commons.json.JsonGroepBijlages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class GroepBijlagesClient extends AbstractClient {
    private final static Logger LOGGER = LoggerFactory.getLogger(GroepBijlagesClient.class);

    private final String URL_LIJST_GROEPEN = "/rest/bijlage/alleGroepen";
    private final String URL_OPSLAAN_GROEP = "/rest/bijlage/opslaanGroep";

    public GroepBijlagesClient(String basisUrl) {
        super(basisUrl, LOGGER);
    }

    public GroepBijlagesClient() {
        super(LOGGER);
    }

    @Override
    protected Type getTypeToken() {
        return new TypeToken<ArrayList<JsonGroepBijlages>>() {
        }.getType();
    }

    public List<JsonGroepBijlages> lijst(String soortEntiteit, Long entiteitId) {
        return uitvoerenGetLijst(URL_LIJST_GROEPEN, JsonGroepBijlages.class, soortEntiteit, entiteitId.toString());
    }

    public String opslaan(JsonGroepBijlages groepBijlages, Long ingelogdeGebruiker, String trackAndTraceId) {
        return aanroepenUrlPost(URL_OPSLAAN_GROEP, groepBijlages, ingelogdeGebruiker, trackAndTraceId);
    }

    public void verwijder(String soortEntiteit, Long entiteitId, Long ingelogdeGebruiker, String trackAndTraceId) {

    }

    public List<JsonGroepBijlages> zoeken(String zoekterm) {
        return null;
    }
}
