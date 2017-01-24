package nl.lakedigital.djfc.client.oga;

import com.google.gson.reflect.TypeToken;
import nl.lakedigital.djfc.commons.json.JsonAdres;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.String.join;

public class AdresClient extends AbstractOgaClient<JsonAdres> {
    private final static Logger LOGGER = LoggerFactory.getLogger(AdresClient.class);

    private final String URL_LIJST = "/rest/adres/alles";
    private final String URL_OPSLAAN = "/rest/adres/opslaan";
    private final String URL_VERWIJDEREN = "/rest/adres/verwijderen";
    private final String URL_LEES = "/rest/adres/lees";
    private final String URL_ADRES_BIJ_POSTCODE = "/rest/adres/ophalenAdresOpPostcode";
    private final String URL_ZOEKEN = "/rest/adres/zoeken";
    private final String URL_ALLES_BIJ_ENTITEITEN = "/rest/adres/alleAdressenBijLijstMetEntiteiten";

    public AdresClient(String basisUrl) {
        super(basisUrl, LOGGER);
    }

    public AdresClient() {
        super(LOGGER);
    }

    @Override
    protected Type getTypeToken() {
        return new TypeToken<ArrayList<JsonAdres>>() {
        }.getType();
    }

    @Override
    public List<JsonAdres> zoeken(String zoekterm) {

        LOGGER.debug("Aanroepen {}", URL_ZOEKEN);

        return uitvoerenGetLijst(URL_ZOEKEN, JsonAdres.class, zoekterm);
    }

    public JsonAdres lees(Long id) {

        LOGGER.debug("Aanroepen {}", URL_LEES);

        return uitvoerenGet(URL_LEES, JsonAdres.class, id.toString());
    }

    public List<JsonAdres> lijst(String soortEntiteit, Long entiteitId) {
        LOGGER.debug("Aanroepen {}", URL_LIJST);

        return uitvoerenGetLijst(URL_LIJST, JsonAdres.class, soortEntiteit, entiteitId.toString());
    }

    public String opslaan(List<JsonAdres> jsonAdressen, Long ingelogdeGebruiker, String trackAndTraceId) {
        LOGGER.debug("Aanroepen {}", URL_OPSLAAN);

        return aanroepenUrlPost(URL_OPSLAAN, jsonAdressen, ingelogdeGebruiker, trackAndTraceId);
    }


    public void verwijder(String soortEntiteit, Long entiteitId, Long ingelogdeGebruiker, String trackAndTraceId) {
        LOGGER.debug("Aanroepen {}", URL_VERWIJDEREN);

        aanroepenUrlPostZonderBody(URL_VERWIJDEREN, ingelogdeGebruiker, trackAndTraceId, soortEntiteit, entiteitId.toString());
    }

    public JsonAdres ophalenAdresOpPostcode(String postcode, String huisnummer, boolean toggle) {
        String toggleString = toggle ? "true" : "false";
        return uitvoerenGet(URL_ADRES_BIJ_POSTCODE, JsonAdres.class, postcode, huisnummer, toggleString);
    }

    public List<JsonAdres> alleAdressenBijLijstMetEntiteiten(List<Long> ids, String soortEntiteit) {
        String idsString = join("&lijst=", ids.stream().map(aLong -> String.valueOf(aLong)).collect(Collectors.toList()));
        return uitvoerenGetLijstZonderEncoding(URL_ALLES_BIJ_ENTITEITEN + "?soortEntiteit=" + soortEntiteit + "&lijst=" + idsString, JsonAdres.class);
    }
}
