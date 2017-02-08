package nl.lakedigital.djfc.client.oga;

import com.google.gson.reflect.TypeToken;
import nl.lakedigital.djfc.commons.json.JsonBijlage;
import nl.lakedigital.djfc.commons.json.WijzigenOmschrijvingBijlage;
import nl.lakedigital.djfc.commons.xml.OpvragenBijlagesResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

public class BijlageClient extends AbstractOgaClient<JsonBijlage, OpvragenBijlagesResponse> {
    private final static Logger LOGGER = LoggerFactory.getLogger(BijlageClient.class);

    private final String URL_LIJST = "/rest/bijlage/alles";
    private final String URL_LEES = "/rest/bijlage/lees";
    private final String URL_OPSLAAN = "/rest/bijlage/opslaan";
    private final String URL_OPSLAANEnkel = "/rest/bijlage/opslaanBijlage";
    private final String URL_VERWIJDEREN = "/rest/bijlage/verwijderen";
    private final String URL_VERWIJDER = "/rest/bijlage/verwijder";
    private final String URL_ZOEKEN = "/rest/bijlage/zoeken";
    private final String URL_BESTANDSNAAM = "/rest/bijlage/genereerBestandsnaam";
    private final String URL_UPLOADPAD = "/rest/bijlage/getUploadPad";
    private final String URL_WIJZIG_OMSCHRIJVING_BIJLAGE = "/rest/bijlage/wijzigOmschrijvingBijlage";

    public BijlageClient(String basisUrl) {
        super(basisUrl, LOGGER);
    }

    public BijlageClient() {
        super(LOGGER);
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

        List<JsonBijlage> result = newArrayList();

        try {
            result = getXMLVoorLijstOGA(basisUrl + URL_ZOEKEN, OpvragenBijlagesResponse.class, zoekterm).getBijlages();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public List<JsonBijlage> lijst(String soortEntiteit, Long entiteitId) {
        System.out.println("Aanroepen " + URL_LIJST);

        List<JsonBijlage> result = newArrayList();

        try {
            result = getXMLVoorLijstOGA(basisUrl + URL_LIJST, OpvragenBijlagesResponse.class, soortEntiteit, String.valueOf(entiteitId)).getBijlages();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
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

    public void verwijder(Long id, Long ingelogdeGebruiker, String trackAndTraceId) {

        System.out.println("Aanroepen " + URL_VERWIJDER);

        aanroepenUrlPostZonderBody(URL_VERWIJDER, ingelogdeGebruiker, trackAndTraceId, id.toString());
    }

    public String genereerBestandsnaam() {
        return uitvoerenGet(URL_BESTANDSNAAM, String.class);
    }

    public String getUploadPad() {
        return uitvoerenGet(URL_UPLOADPAD, String.class);
    }

    public void wijzigOmschrijvingBijlage(WijzigenOmschrijvingBijlage wijzigenOmschrijvingBijlage, Long ingelogdeGebruiker, String trackAndTraceId) {
        aanroepenUrlPost(URL_WIJZIG_OMSCHRIJVING_BIJLAGE, wijzigenOmschrijvingBijlage, ingelogdeGebruiker, trackAndTraceId);
    }
}