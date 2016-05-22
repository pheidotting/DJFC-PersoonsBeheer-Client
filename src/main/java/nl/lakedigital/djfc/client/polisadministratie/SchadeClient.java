package nl.lakedigital.djfc.client.polisadministratie;

import com.google.gson.reflect.TypeToken;
import nl.lakedigital.djfc.client.AbstractClient;
import nl.lakedigital.djfc.commons.json.JsonPolis;
import nl.lakedigital.djfc.commons.json.JsonSchade;
import nl.lakedigital.djfc.commons.json.JsonSoortSchade;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class SchadeClient extends AbstractClient {
    private final String URL_OPSLAAN = "http://localhost:" + poortNummer + "/pa/rest/schade/opslaan";
    private final String URL_LIJST = "http://localhost:" + poortNummer + "/pa/rest/schade/lijst";
    private final String URL_LIJST_BEDRIJF = "http://localhost:" + poortNummer + "/pa/rest/schade/lijstBijBedrijf";
    private final String URL_LEES = "http://localhost:" + poortNummer + "/pa/rest/schade/lees";
    private final String URL_VERWIJDER = "http://localhost:" + poortNummer + "/pa/rest/schade/verwijder";
    private final String URL_SOORTEN_SCHADE = "http://localhost:" + poortNummer + "/pa/rest/schade/soortenSchade";
    private final String URL_STATUSSEN_SCHADE = "http://localhost:" + poortNummer + "/pa/rest/schade/lijstStatusSchade";

    public SchadeClient(int poortNummer) {
        super(poortNummer);
    }

    @Override
    protected Type getTypeToken() {
        return new TypeToken<ArrayList<JsonPolis>>() {
        }.getType();
    }

    public Long opslaan(JsonSchade jsonSchade) {
        return Long.valueOf(aanroepenUrlPost(URL_OPSLAAN, jsonSchade, 33L));
    }

    public List<JsonSchade> lijst(Long relatieId) {
        return uitvoerenGetLijst(URL_LIJST, JsonSchade.class, relatieId.toString());
    }

    public List<JsonSchade> lijstBijBedrijf(Long bedrijfId) {
        return uitvoerenGetLijst(URL_LIJST_BEDRIJF, JsonSchade.class, bedrijfId.toString());
    }

    public JsonSchade lees(String id) {
        return uitvoerenGet(URL_LEES, JsonSchade.class, 33L, id);
    }

    public void verwijder(Long id) {
        aanroepenUrlPostZonderBody(URL_VERWIJDER + "/" + id, 33L);
    }

    public List<JsonSoortSchade> soortenSchade(String query) {
        return uitvoerenGetLijst(URL_SOORTEN_SCHADE, JsonSoortSchade.class, query);
    }

    public List<String> lijstStatusSchade() {
        return uitvoerenGetLijst(URL_SOORTEN_SCHADE, String.class);
    }
}