package nl.lakedigital.djfc.client.oga;

import com.google.gson.reflect.TypeToken;
import nl.lakedigital.djfc.commons.json.JsonTelefoonnummer;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class TelefoonnummerClient extends AbstractOgaClient<JsonTelefoonnummer> {
    private final String URL_LIJST = "http://localhost:7072/oga/rest/telefoonnummer/alles";
    private final String URL_OPSLAAN = "http://localhost:7072/oga/rest/telefoonnummer/opslaan";
    private final String URL_VERWIJDEREN = "http://localhost:7072/oga/rest/telefoonnummer/verwijderen";
    private final String URL_ZOEKEN = "http://localhost:7072/oga/rest/telefoonnummer/zoeken";

    @Override
    protected Type getTypeToken() {
        return new TypeToken<ArrayList<JsonTelefoonnummer>>() {
        }.getType();
    }

    @Override
    public List<JsonTelefoonnummer> zoeken(String zoekterm) {

        System.out.println("Aanroepen " + URL_ZOEKEN);

        return uitvoerenGetLijst(URL_ZOEKEN, JsonTelefoonnummer.class, zoekterm);
    }

    @Override
    public List<JsonTelefoonnummer> lijst(String soortEntiteit, Long entiteitId) {

        System.out.println("Aanroepen " + URL_LIJST);

        return uitvoerenGetLijst(URL_LIJST, JsonTelefoonnummer.class, soortEntiteit, entiteitId.toString());
    }

    @Override
    public String opslaan(List<JsonTelefoonnummer> jsonAdressen) {

        System.out.println("Aanroepen " + URL_OPSLAAN);

        return aanroepenUrlPost(URL_OPSLAAN, jsonAdressen, 5L);
    }

    @Override
    public void verwijder(String soortEntiteit, Long entiteitId) {

        System.out.println("Aanroepen " + URL_VERWIJDEREN);

        aanroepenUrlPostZonderBody(URL_VERWIJDEREN, 5L, soortEntiteit, entiteitId.toString());
    }
}
