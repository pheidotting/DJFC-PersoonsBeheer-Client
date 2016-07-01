package nl.lakedigital.djfc.client.oga;

import com.google.gson.reflect.TypeToken;
import nl.lakedigital.djfc.commons.json.JsonBijlage;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class BijlageClient extends AbstractOgaClient<JsonBijlage> {
    private final String URL_LIJST = "/rest/bijlage/alles";
    private final String URL_LEES = "/rest/bijlage/lees";
    private final String URL_OPSLAAN = "/rest/bijlage/opslaan";
    private final String URL_OPSLAANEnkel = "/rest/bijlage/opslaanBijlage";
    private final String URL_VERWIJDEREN = "/rest/bijlage/verwijderen";
    private final String URL_ZOEKEN = "/rest/bijlage/zoeken";
    private final String URL_BESTANDSNAAM = "/rest/bijlage/genereerBestandsnaam";
    private final String URL_UPLOADPAD = "/rest/bijlage/getUploadPad";

    public BijlageClient(String basisUrl) {
        super(basisUrl);
    }

    public BijlageClient() {
    }

    @Override
    protected Type getTypeToken() {
        return new TypeToken<ArrayList<JsonBijlage>>() {
        }.getType();
    }

    public JsonBijlage lees(Long id) {

        System.out.println("Aanroepen " + URL_LEES);

        return uitvoerenGet(URL_LEES, JsonBijlage.class, id.toString());
    }
    @Override
    public List<JsonBijlage> zoeken(String zoekterm) {

        System.out.println("Aanroepen " + URL_ZOEKEN);

        return uitvoerenGetLijst(URL_ZOEKEN, JsonBijlage.class, zoekterm);
    }

    @Override
    public List<JsonBijlage> lijst(String soortEntiteit, Long entiteitId) {

        System.out.println("Aanroepen " + URL_LIJST);

        return uitvoerenGetLijst(URL_LIJST, JsonBijlage.class, soortEntiteit, entiteitId.toString());
    }

    @Override
    public String opslaan(List<JsonBijlage> bijlages, Long ingelogdeGebruiker, String trackAndTraceId) {

        System.out.println("Aanroepen " + URL_OPSLAAN);

        return aanroepenUrlPost(URL_OPSLAAN, bijlages, ingelogdeGebruiker, trackAndTraceId);
    }

    public String opslaan(JsonBijlage bijlage, Long ingelogdeGebruiker, String trackAndTraceId) {

        System.out.println("Aanroepen " + URL_OPSLAAN);

        return aanroepenUrlPost(URL_OPSLAAN, bijlage, ingelogdeGebruiker, trackAndTraceId);
    }

    @Override
    public void verwijder(String soortEntiteit, Long entiteitId, Long ingelogdeGebruiker, String trackAndTraceId) {

        System.out.println("Aanroepen " + URL_VERWIJDEREN);

        aanroepenUrlPostZonderBody(URL_VERWIJDEREN, ingelogdeGebruiker, trackAndTraceId, soortEntiteit, entiteitId.toString());
    }

    public String genereerBestandsnaam() {
        return uitvoerenGet(URL_BESTANDSNAAM, String.class);
    }
    public String getUploadPad(){
        return uitvoerenGet(URL_UPLOADPAD, String.class);
    }

}
