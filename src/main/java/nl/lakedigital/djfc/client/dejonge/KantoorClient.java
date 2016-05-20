package nl.lakedigital.djfc.client.dejonge;

import com.google.gson.reflect.TypeToken;
import nl.lakedigital.djfc.client.AbstractClient;
import nl.lakedigital.djfc.commons.json.JsonKantoor;
import nl.lakedigital.djfc.commons.json.JsonMedewerker;
import nl.lakedigital.djfc.commons.json.JsonRelatie;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class KantoorClient extends AbstractClient {
    private final String URL_LEES = "http://localhost:8080/dejonge/rest/applicaties/kantoor/lees";

    @Override
    protected Type getTypeToken() {
        return new TypeToken<ArrayList<JsonKantoor>>() {
        }.getType();
    }

    public JsonKantoor lees(Long id) {

        System.out.println("Aanroepen " + URL_LEES);

        return uitvoerenGet(URL_LEES, JsonKantoor.class, 9L, id.toString());
    }
}
