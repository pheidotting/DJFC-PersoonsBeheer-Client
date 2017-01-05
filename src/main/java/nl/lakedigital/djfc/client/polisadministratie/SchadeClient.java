package nl.lakedigital.djfc.client.polisadministratie;

import com.google.gson.reflect.TypeToken;
import nl.lakedigital.djfc.client.AbstractClient;
import nl.lakedigital.djfc.commons.json.JsonPolis;
import nl.lakedigital.djfc.commons.json.JsonSchade;
import nl.lakedigital.djfc.commons.json.JsonSoortSchade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class SchadeClient extends AbstractClient {
    private final static Logger LOGGER = LoggerFactory.getLogger(SchadeClient.class);

    private final String URL_OPSLAAN = basisUrl + "/rest/schade/opslaan";
    private final String URL_LIJST = basisUrl + "/rest/schade/lijst";
    private final String URL_LIJST_BEDRIJF = basisUrl + "/rest/schade/lijstBijBedrijf";
    private final String URL_LEES = basisUrl + "/rest/schade/lees";
    private final String URL_VERWIJDER = basisUrl + "/rest/schade/verwijder";
    private final String URL_SOORTEN_SCHADE = basisUrl + "/rest/schade/soortenSchade";
    private final String URL_STATUSSEN_SCHADE = basisUrl + "/rest/schade/lijstStatusSchade";

    public SchadeClient(String basisUrl) {
        super(basisUrl, LOGGER);
    }

    public SchadeClient() {
        super(LOGGER);
    }

    @Override
    protected Type getTypeToken() {
        return new TypeToken<ArrayList<JsonPolis>>() {
        }.getType();
    }

    public Long opslaan(JsonSchade jsonSchade, Long ingelogdeGebruiker, String trackAndTraceId) {
        return Long.valueOf(aanroepenUrlPost(URL_OPSLAAN, jsonSchade, ingelogdeGebruiker, trackAndTraceId));
    }

    public List<JsonSchade> lijst(Long relatieId) {
        return uitvoerenGetLijst(URL_LIJST, JsonSchade.class, relatieId.toString());
    }

    public List<JsonSchade> lijstBijBedrijf(Long bedrijfId) {
        return uitvoerenGetLijst(URL_LIJST_BEDRIJF, JsonSchade.class, bedrijfId.toString());
    }

    public JsonSchade lees(String id) {
        return uitvoerenGet(URL_LEES, JsonSchade.class, id);
    }

    public void verwijder(Long id, Long ingelogdeGebruiker, String trackAndTraceId) {
        aanroepenUrlPostZonderBody(URL_VERWIJDER + "/" + id, ingelogdeGebruiker, trackAndTraceId);
    }

    public List<JsonSoortSchade> soortenSchade(String query) {
        return uitvoerenGetLijst(URL_SOORTEN_SCHADE, JsonSoortSchade.class, query);
    }

    public List<String> lijstStatusSchade() {
        return uitvoerenGetLijst(URL_SOORTEN_SCHADE, String.class);
    }
}