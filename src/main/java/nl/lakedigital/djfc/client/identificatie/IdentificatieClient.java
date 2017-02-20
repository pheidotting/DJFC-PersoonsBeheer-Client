package nl.lakedigital.djfc.client.identificatie;

import nl.lakedigital.djfc.client.AbstractClient;
import nl.lakedigital.djfc.commons.json.Identificatie;
import nl.lakedigital.djfc.commons.json.ZoekIdentificatieResponse;

import java.lang.reflect.Type;
import java.util.List;

public class IdentificatieClient extends AbstractClient<ZoekIdentificatieResponse> {
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

        return lijst.get(0);
    }
}
