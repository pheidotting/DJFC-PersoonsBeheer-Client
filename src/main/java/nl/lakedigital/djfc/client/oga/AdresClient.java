package nl.lakedigital.djfc.client.oga;

import com.google.gson.reflect.TypeToken;
import nl.lakedigital.djfc.commons.json.JsonAdres;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class AdresClient extends AbstractOgaClient<JsonAdres> {
    private final String URL_LIJST = "http://localhost:" + poortNummer + "/oga/rest/adres/alles";
    private final String URL_OPSLAAN = "http://localhost:" + poortNummer + "/oga/rest/adres/opslaan";
    private final String URL_VERWIJDEREN = "http://localhost:" + poortNummer + "/oga/rest/adres/verwijderen";
    private final String URL_LEES = "http://localhost:" + poortNummer + "/oga/rest/adres/lees";
    private final String URL_ADRES_BIJ_POSTCODE = "http://localhost:" + poortNummer + "/oga/rest/adres/ophalenAdresOpPostcode";
    private final String URL_ZOEKEN = "http://localhost:" + poortNummer + "/oga/rest/adres/zoeken";

    public AdresClient(int poortNummer) {
        super(poortNummer);
    }

    @Override
    protected Type getTypeToken() {
        return new TypeToken<ArrayList<JsonAdres>>() {
        }.getType();
    }

    @Override
    public List<JsonAdres> zoeken(String zoekterm) {

        System.out.println("Aanroepen " + URL_ZOEKEN);

        return uitvoerenGetLijst(URL_ZOEKEN, JsonAdres.class, zoekterm);
    }

    public JsonAdres lees(Long id) {

        System.out.println("Aanroepen " + URL_LEES);

        return uitvoerenGet(URL_LEES, JsonAdres.class, id.toString());
    }

    public List<JsonAdres> lijst(String soortEntiteit, Long entiteitId) {

        System.out.println("Aanroepen " + URL_LIJST);

        return uitvoerenGetLijst(URL_LIJST, JsonAdres.class, soortEntiteit, entiteitId.toString());
    }

    public String opslaan(List<JsonAdres> jsonAdressen, Long ingelogdeGebruiker, String trackAndTraceId) {

        System.out.println("Aanroepen " + URL_OPSLAAN);

        return aanroepenUrlPost(URL_OPSLAAN, jsonAdressen, ingelogdeGebruiker, trackAndTraceId);
    }


    public void verwijder(String soortEntiteit, Long entiteitId, Long ingelogdeGebruiker, String trackAndTraceId) {

        System.out.println("Aanroepen " + URL_VERWIJDEREN);

        aanroepenUrlPostZonderBody(URL_VERWIJDEREN, ingelogdeGebruiker, trackAndTraceId, soortEntiteit, entiteitId.toString());
    }

    public JsonAdres ophalenAdresOpPostcode(String postcode, String huisnummer) {
        return uitvoerenGet(URL_ADRES_BIJ_POSTCODE, JsonAdres.class, postcode, huisnummer);
    }
}
