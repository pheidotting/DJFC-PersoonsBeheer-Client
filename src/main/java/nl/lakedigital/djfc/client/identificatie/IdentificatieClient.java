package nl.lakedigital.djfc.client.identificatie;

import com.google.gson.Gson;
import nl.lakedigital.djfc.client.AbstractClient;
import nl.lakedigital.djfc.commons.json.Identificatie;
import nl.lakedigital.djfc.commons.json.ZoekIdentificatieResponse;
import nl.lakedigital.djfc.request.EntiteitenOpgeslagenRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Type;
import java.util.List;

public class IdentificatieClient extends AbstractClient<ZoekIdentificatieResponse> {
    private final static Logger LOGGER = LoggerFactory.getLogger(IdentificatieClient.class);

    public IdentificatieClient(String basisUrl) {
        super(basisUrl, LOGGER);
    }

    public IdentificatieClient() {
        super(LOGGER);
    }

    @Override
    protected Type getTypeToken() {
        return null;
    }

    public Identificatie zoekIdentificatie(String soortEntiteit, Long entiteitId) {
        List<Identificatie> lijst = getXML("/rest/identificatie/zoeken", ZoekIdentificatieResponse.class, false, soortEntiteit, String.valueOf(entiteitId)).getIdentificaties();

        if (!lijst.isEmpty()) {
            return lijst.get(0);
        }
        return null;
    }

    public Identificatie zoekIdentificatieCode(String identificatieCode) {
        List<Identificatie> lijst = getXML("/rest/identificatie/zoekenOpCode", ZoekIdentificatieResponse.class, false, identificatieCode).getIdentificaties();

        if (!lijst.isEmpty()) {
            return lijst.get(0);
        }
        return null;
    }

    public nl.lakedigital.djfc.commons.json.Identificatie opslaan(EntiteitenOpgeslagenRequest entiteitenOpgeslagenRequest) {
        return new Gson().fromJson(aanroepenUrlPost("/rest/identificatie/opslaan", entiteitenOpgeslagenRequest, 0L, ""), nl.lakedigital.djfc.commons.json.Identificatie.class);
    }
}
