package nl.lakedigital.djfc.client.dejonge;

import com.google.gson.reflect.TypeToken;
import nl.lakedigital.djfc.client.AbstractClient;
import nl.lakedigital.djfc.commons.json.JsonMedewerker;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MedewerkerClient extends AbstractClient {
    private final String URL_LEES = "http://localhost:" + poortNummer + "/dejonge/rest/applicaties/medewerker/lees";

    public MedewerkerClient(int poortNummer) {
        super(poortNummer);
    }

    @Override
    protected Type getTypeToken() {
        return new TypeToken<ArrayList<JsonMedewerker>>() {
        }.getType();
    }

    public JsonMedewerker lees(Long id) {

        System.out.println("Aanroepen " + URL_LEES);

        return uitvoerenGet(URL_LEES, JsonMedewerker.class, 9L, id.toString());
    }
}
