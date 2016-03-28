package nl.lakedigital.djfc.client.oga;

import com.google.gson.reflect.TypeToken;
import nl.lakedigital.djfc.client.AbstractClient;
import nl.lakedigital.djfc.commons.json.JsonRekeningNummer;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class RekeningClient extends AbstractClient<JsonRekeningNummer> {
    private final String URL_LIJST = "http://localhost:7072/orga/rest/rekeningnummer/alles";
    private final String URL_OPSLAAN = "http://localhost:7072/orga/rest/rekeningnummer/opslaan";
    private final String URL_VERWIJDEREN = "http://localhost:7072/orga/rest/rekeningnummer/verwijderen";

    @Override
    protected Type getTypeToken() {
        return new TypeToken<ArrayList<JsonRekeningNummer>>() {
        }.getType();
    }

    @Override
    public List<JsonRekeningNummer> lijst(String soortEntiteit, Long entiteitId) {

        System.out.println("Aanroepen " + URL_LIJST);

        return uitvoerenGetLijst(URL_LIJST, JsonRekeningNummer.class, soortEntiteit, entiteitId.toString());
    }

    @Override
    public String opslaan(List<JsonRekeningNummer> jsonAdressen) {

        System.out.println("Aanroepen " + URL_OPSLAAN);

        return aanroepenUrlPost(URL_OPSLAAN, jsonAdressen, 5L);
    }

    @Override
    public void verwijder(String soortEntiteit, Long entiteitId) {

        System.out.println("Aanroepen " + URL_VERWIJDEREN);

        aanroepenUrlPostZonderBody(URL_VERWIJDEREN, 5L, soortEntiteit, entiteitId.toString());
    }
}