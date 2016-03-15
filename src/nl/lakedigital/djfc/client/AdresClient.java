package nl.lakedigital.djfc.client;

import nl.lakedigital.djfc.commons.json.JsonAdres;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AdresClient extends AbstractClient {
    private final String URL_LIJST_ADRESSEN = "http://localhost:7072/orga/rest/adres/alles";
    private final String URL_OPSLAAN = "http://localhost:7072/orga/rest/adres/opslaan";
    //    private final String URL_LEES_RELATIE = "http://localhost:7072/persoonsbeheer/rest/gebruiker/lees";

    public List<JsonAdres> lijstAdressen(String soortEntiteit, Long entiteitId) {
        return uitvoerenGetLijst(URL_LIJST_ADRESSEN, JsonAdres.class, soortEntiteit, entiteitId.toString());
    }

    public String opslaan(List<JsonAdres> jsonAdressen) {
        return aanroepenUrlPost(URL_OPSLAAN, jsonAdressen);
    }
}
