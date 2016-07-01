package nl.lakedigital.djfc.client.polisadministratie;

import com.google.gson.reflect.TypeToken;
import nl.lakedigital.djfc.client.AbstractClient;
import nl.lakedigital.djfc.commons.json.JsonPolis;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class PolisClient extends AbstractClient {
    private final String URL_ALLE_PARTICULIERE_POLIS_SOORTEN = basisUrl + "/rest/polis/alleParticulierePolisSoorten";
    private final String URL_ALLE_PARTICULIERE_ZAKELIJKE_SOORTEN = basisUrl + "/rest/polis/alleZakelijkePolisSoorten";
    private final String URL_LEES = basisUrl + "/rest/polis/lees";
    private final String URL_BEINDIGEN = basisUrl + "/rest/polis/beeindigen";
    private final String URL_LIJST = basisUrl + "/rest/polis/lijst";
    private final String URL_LIJST_BEDRIJF = basisUrl + "/rest/polis/lijstBijBedrijf";
    private final String URL_OPSLAAN = basisUrl + "/rest/polis/opslaan";
    private final String URL_VERWIJDER = basisUrl + "/rest/polis/verwijder";
    private final String URL_ZOEK_OP_POLISNUMMER = basisUrl + "/rest/polis/zoekOpPolisNummer";

    public PolisClient(String basisUrl) {
        super(basisUrl);
    }

    public PolisClient() {
    }

    public List<String> alleParticulierePolisSoorten() {
        return uitvoerenGetLijst(URL_ALLE_PARTICULIERE_POLIS_SOORTEN, String.class);
    }

    public List<String> alleZakelijkePolisSoorten() {
        return uitvoerenGetLijst(URL_ALLE_PARTICULIERE_ZAKELIJKE_SOORTEN, String.class);
    }

    public JsonPolis lees(String id) {
        return uitvoerenGet(URL_LEES, JsonPolis.class, id);
    }

    public void beeindigen(Long id, Long ingelogdeGebruiker, String trackAndTraceId) {
        aanroepenUrlPostZonderBody(URL_BEINDIGEN, ingelogdeGebruiker, trackAndTraceId, id.toString());
    }

    public List<JsonPolis> lijst(String relatieId) {
        return uitvoerenGetLijst(URL_LIJST, JsonPolis.class, relatieId);
    }

    public List<JsonPolis> lijstBijBedrijf(Long bedrijfId) {
        return uitvoerenGetLijst(URL_LIJST_BEDRIJF, JsonPolis.class, bedrijfId.toString());
    }

    public Long opslaan(JsonPolis jsonPolis, Long ingelogdeGebruiker, String trackAndTraceId) {
        return Long.valueOf(aanroepenUrlPost(URL_OPSLAAN, jsonPolis, ingelogdeGebruiker, trackAndTraceId));
    }

    public void verwijder(Long id, Long ingelogdeGebruiker, String trackAndTraceId) {
        aanroepenUrlPostZonderBody(URL_VERWIJDER, ingelogdeGebruiker, trackAndTraceId, id.toString());
    }

    public JsonPolis zoekOpPolisNummer(String polisNummer) {
        return uitvoerenGet(URL_ZOEK_OP_POLISNUMMER, JsonPolis.class, polisNummer);
    }

    @Override
    protected Type getTypeToken() {
        return new TypeToken<ArrayList<JsonPolis>>() {
        }.getType();
    }
}
