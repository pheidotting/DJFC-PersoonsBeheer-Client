package nl.lakedigital.djfc.client.dejonge;

import com.google.gson.reflect.TypeToken;
import nl.lakedigital.djfc.client.AbstractClient;
import nl.lakedigital.djfc.commons.json.JsonRelatie;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class RelatieClient extends AbstractClient {
    private final String URL_LEES = "http://localhost:8080/dejonge/rest/applicaties/relatie/lees";
    private final String URL_ZOEK_OP_EMAILADRES  = "http://localhost:8080/dejonge/rest/applicaties/relatie/zoekOpEmailadres";
    private final String URL_ZOEK_OP_NAAM = "http://localhost:8080/dejonge/rest/applicaties/relatie/zoekOpNaam";

    @Override
    protected Type getTypeToken() {
        return new TypeToken<ArrayList<JsonRelatie>>() {
        }.getType();
    }

    public JsonRelatie lees(Long id) {

        System.out.println("Aanroepen " + URL_LEES);

        return uitvoerenGet(URL_LEES, JsonRelatie.class, 9L, id.toString());
    }

    public JsonRelatie zoekOpEmailadres(String emailadres) {
        System.out.println("Aanroepen " + URL_ZOEK_OP_EMAILADRES);

        return uitvoerenGet(URL_ZOEK_OP_EMAILADRES, JsonRelatie.class, 9L, emailadres,"dummy");
    }

    public List<JsonRelatie> zoekOpNaam(String naam) {
        System.out.println("Aanroepen " + URL_ZOEK_OP_NAAM);

        return uitvoerenGetLijst(URL_ZOEK_OP_NAAM, JsonRelatie.class, naam);
    }
}
