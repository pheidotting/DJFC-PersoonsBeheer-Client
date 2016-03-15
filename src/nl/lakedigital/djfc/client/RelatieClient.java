package nl.lakedigital.djfc.client;

import nl.lakedigital.djfc.commons.json.JsonLijstRelaties;
import nl.lakedigital.djfc.commons.json.JsonRelatie;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RelatieClient extends AbstractClient {
    private final String URL_LIJST_RELATIES = "http://localhost:7072/persoonsbeheer/rest/gebruiker/lijstRelaties";
    private final String URL_OPSLAAN_RELATIE = "http://localhost:7072/persoonsbeheer/rest/gebruiker/opslaanRelatie";
    private final String URL_LEES_RELATIE = "http://localhost:7072/persoonsbeheer/rest/gebruiker/lees";

    public JsonRelatie leesRelatie(Long id) {
        return uitvoerenGet(URL_LEES_RELATIE, JsonRelatie.class, null, id.toString());
    }

    public String opslaanRelatie(JsonRelatie jsonRelatie) {
        return aanroepenUrlPost(URL_OPSLAAN_RELATIE, jsonRelatie);
    }

    public JsonLijstRelaties lijstRelaties(Long ingelogdeGebruiker) {
        return uitvoerenGet(URL_LIJST_RELATIES, JsonLijstRelaties.class, ingelogdeGebruiker);
    }

}
