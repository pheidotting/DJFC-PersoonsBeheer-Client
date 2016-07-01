package nl.lakedigital.djfc.client.dejonge;

import com.google.gson.reflect.TypeToken;
import nl.lakedigital.djfc.client.AbstractClient;
import nl.lakedigital.djfc.commons.json.JsonKantoor;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class KantoorClient extends AbstractClient {
    private final String URL_LEES = basisUrl + "/rest/applicaties/kantoor/lees";

    public KantoorClient(String basisUrl) {
        super(basisUrl);
    }

    @Override
    protected Type getTypeToken() {
        return new TypeToken<ArrayList<JsonKantoor>>() {
        }.getType();
    }

    public JsonKantoor lees(Long id) {

        System.out.println("Aanroepen " + URL_LEES);

        return uitvoerenGet(URL_LEES, JsonKantoor.class, id.toString());
    }
}
