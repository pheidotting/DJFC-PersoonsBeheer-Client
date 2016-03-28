package nl.lakedigital.djfc.client.oga;

import com.google.gson.reflect.TypeToken;
import nl.lakedigital.djfc.client.AbstractClient;
import nl.lakedigital.djfc.commons.json.JsonAdres;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class AdresClient extends AbstractClient<JsonAdres> {
    private final String URL_LIJST = "http://localhost:7072/orga/rest/adres/alles";
    private final String URL_OPSLAAN = "http://localhost:7072/orga/rest/adres/opslaan";
    private final String URL_VERWIJDEREN = "http://localhost:7072/orga/rest/adres/verwijderen";
    private final String URL_LEES = "http://localhost:7072/orga/rest/adres/lees";

    @Override
    protected Type getTypeToken() {
        return new TypeToken<ArrayList<JsonAdres>>() {
        }.getType();
    }

    public JsonAdres lees(Long id) {

        System.out.println("Aanroepen " + URL_LEES);

        return uitvoerenGet(URL_LEES, JsonAdres.class, 9L, id.toString());
    }

    public List<JsonAdres> lijst(String soortEntiteit, Long entiteitId) {

        System.out.println("Aanroepen " + URL_LIJST);

        return uitvoerenGetLijst(URL_LIJST, JsonAdres.class, soortEntiteit, entiteitId.toString());
    }

    public String opslaan(List<JsonAdres> jsonAdressen) {

        System.out.println("Aanroepen " + URL_OPSLAAN);

        return aanroepenUrlPost(URL_OPSLAAN, jsonAdressen, 3L);
    }


    public void verwijder(String soortEntiteit, Long entiteitId) {

        System.out.println("Aanroepen " + URL_VERWIJDEREN);

        aanroepenUrlPostZonderBody(URL_VERWIJDEREN, 5L, soortEntiteit, entiteitId.toString());
    }
}
