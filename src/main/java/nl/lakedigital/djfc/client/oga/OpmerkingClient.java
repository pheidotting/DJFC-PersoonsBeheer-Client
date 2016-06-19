package nl.lakedigital.djfc.client.oga;

import com.google.gson.reflect.TypeToken;
import nl.lakedigital.djfc.commons.json.JsonOpmerking;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class OpmerkingClient extends AbstractOgaClient<JsonOpmerking> {
    private final String URL_LIJST = "http://localhost:" + poortNummer + "/oga/rest/opmerking/alles";
    private final String URL_OPSLAAN = "http://localhost:" + poortNummer + "/oga/rest/opmerking/opslaan";
    private final String URL_VERWIJDEREN = "http://localhost:" + poortNummer + "/oga/rest/opmerking/verwijderen";
    private final String URL_ZOEKEN = "http://localhost:" + poortNummer + "/oga/rest/opmerking/zoeken";

    public OpmerkingClient(int poortNummer) {
        super(poortNummer);
    }

    @Override
    protected Type getTypeToken() {
        return new TypeToken<ArrayList<JsonOpmerking>>() {
        }.getType();
    }

    @Override
    public List<JsonOpmerking> zoeken(String zoekterm) {

        System.out.println("Aanroepen " + URL_ZOEKEN);

        return uitvoerenGetLijst(URL_ZOEKEN, JsonOpmerking.class, zoekterm);
    }

    @Override
    public List<JsonOpmerking> lijst(String soortEntiteit, Long entiteitId) {

        System.out.println("Aanroepen " + URL_LIJST);

        return uitvoerenGetLijst(URL_LIJST, JsonOpmerking.class, soortEntiteit, entiteitId.toString());
    }

    @Override
    public String opslaan(List<JsonOpmerking> jsonAdressen, Long ingelogdeGebruiker, String trackAndTraceId) {

        System.out.println("Aanroepen " + URL_OPSLAAN);
        for (JsonOpmerking jsonOpmerking : jsonAdressen) {
            System.out.println(ReflectionToStringBuilder.toString(jsonOpmerking, ToStringStyle.SHORT_PREFIX_STYLE));
        }
        System.out.println("# Aanroepen " + URL_OPSLAAN);

        return aanroepenUrlPost(URL_OPSLAAN, jsonAdressen, ingelogdeGebruiker, trackAndTraceId);
    }

    @Override
    public void verwijder(String soortEntiteit, Long entiteitId, Long ingelogdeGebruiker, String trackAndTraceId) {

        System.out.println("Aanroepen " + URL_VERWIJDEREN);

        aanroepenUrlPostZonderBody(URL_VERWIJDEREN, ingelogdeGebruiker, trackAndTraceId, soortEntiteit, entiteitId.toString());
    }
}
