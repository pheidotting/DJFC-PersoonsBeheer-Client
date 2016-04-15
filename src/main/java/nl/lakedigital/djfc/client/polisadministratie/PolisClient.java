package nl.lakedigital.djfc.client.polisadministratie;

import com.google.gson.reflect.TypeToken;
import nl.lakedigital.djfc.client.AbstractClient;
import nl.lakedigital.djfc.commons.json.JsonPolis;

import javax.ws.rs.QueryParam;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class PolisClient extends AbstractClient {
    private final String URL_ALLE_PARTICULIERE_POLIS_SOORTEN = "http://localhost:7073/pa/rest/polis/alleParticulierePolisSoorten";
    private final String URL_ALLE_PARTICULIERE_ZAKELIJKE_SOORTEN = "http://localhost:7073/pa/rest/polis/alleZakelijkePolisSoorten";
    private final String URL_LEES = "http://localhost:7073/pa/rest/polis/lees";
    private final String URL_BEINDIGEN = "http://localhost:7073/pa/rest/polis/beeindigen";
    private final String URL_LIJST = "http://localhost:7073/pa/rest/polis/lijst";
    private final String URL_LIJST_BEDRIJF = "http://localhost:7073/pa/rest/polis/lijstBijBedrijf";
    private final String URL_OPSLAAN = "http://localhost:7073/pa/rest/polis/opslaan";
    private final String URL_VERWIJDER = "http://localhost:7073/pa/rest/polis/verwijder";
    private final String URL_ZOEK_OP_POLISNUMMER = "http://localhost:7073/pa/rest/polis/zoekOpPolisNummer";

    public List<String> alleParticulierePolisSoorten() {
        return uitvoerenGetLijst(URL_ALLE_PARTICULIERE_POLIS_SOORTEN, String.class);
    }

    public List<String> alleZakelijkePolisSoorten() {
        return uitvoerenGetLijst(URL_ALLE_PARTICULIERE_ZAKELIJKE_SOORTEN, String.class);
    }

    public JsonPolis lees(String id) {
        return uitvoerenGet(URL_LEES, JsonPolis.class, 33L, id);
    }

    public void beeindigen(Long id) {
        aanroepenUrlPostZonderBody(URL_BEINDIGEN, 33L, id.toString());
    }

    public List<JsonPolis> lijst(String relatieId) {
        return uitvoerenGetLijst(URL_LIJST, JsonPolis.class, relatieId);
    }

    public List<JsonPolis> lijstBijBedrijf(Long bedrijfId) {
        return uitvoerenGetLijst(URL_LIJST_BEDRIJF, JsonPolis.class, bedrijfId.toString());
    }

    public Long opslaan(JsonPolis jsonPolis) {
        return Long.valueOf(aanroepenUrlPost(URL_OPSLAAN, jsonPolis, 33L));
    }

    public void verwijder(@QueryParam("id") Long id) {
        aanroepenUrlPostZonderBody(URL_VERWIJDER, 33L, id.toString());
    }

    public JsonPolis zoekOpPolisNummer(String polisNummer) {
        return uitvoerenGet(URL_ZOEK_OP_POLISNUMMER, JsonPolis.class, 33L, polisNummer);
    }

    @Override
    protected Type getTypeToken() {
        return new TypeToken<ArrayList<JsonPolis>>() {
        }.getType();
    }
}
