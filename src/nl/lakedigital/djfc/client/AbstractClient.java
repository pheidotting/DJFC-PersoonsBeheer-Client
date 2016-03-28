package nl.lakedigital.djfc.client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;
import nl.lakedigital.djfc.commons.json.AbstracteJsonEntiteitMetSoortEnId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Type;
import java.util.List;

public abstract class AbstractClient<T extends AbstracteJsonEntiteitMetSoortEnId> {
    private GsonBuilder builder = new GsonBuilder();
    protected Gson gson = new Gson();

    private final static Logger LOGGER = LoggerFactory.getLogger(AbstractClient.class);


    public abstract List<T> lijst(String soortEntiteit, Long entiteitId);

    public abstract String opslaan(List<T> jsonAdressen);

    public abstract void verwijder(String soortEntiteit, Long entiteitId);

    protected String aanroepenUrlPost(String adres, Object object, Long ingelogdeGebruiker) {
        Gson gson = builder.create();

        Client client = Client.create();

        WebResource webResource = client.resource(adres);
        String verstuurObject = gson.toJson(object);
        LOGGER.info("Versturen {}", verstuurObject);
        System.out.println("Versturen " + verstuurObject + " naar " + adres);

        ClientResponse cr = webResource.accept("application/json").type("application/json").header("ingelogdeGebruiker", ingelogdeGebruiker.toString()).post(ClientResponse.class, verstuurObject);

        return cr.getEntity(String.class);
    }

    protected void aanroepenUrlPostZonderBody(String adres, Long ingelogdeGebruiker, String... args) {
        Gson gson = builder.create();

        Client client = Client.create();

        if (args != null) {
            for (String arg : args) {
                adres = adres + "/" + arg;
            }
        }

        WebResource webResource = client.resource(adres);

        webResource.accept("application/json").type("application/json").header("ingelogdeGebruiker", ingelogdeGebruiker.toString()).post();
    }

    protected String uitvoerenGet(String adres) {
        //        adres = adres.replace("{{poort}}", bepaalPoort());
        LOGGER.info("Aanroepen via GET " + adres);
        System.out.println("Aanroepen via GET " + adres);

        ClientConfig clientConfig = new DefaultClientConfig();
        clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
        Client client = Client.create(clientConfig);
        WebResource webResource = client.resource(adres);
        ClientResponse response = webResource.accept("application/json").type("application/json").get(ClientResponse.class);
        if (response.getStatus() != 200) {
            throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
        }
        return response.getEntity(String.class);
    }

    protected <T> T uitvoerenGet(String adres, Class<T> clazz, Long ingelogdeGebruiker, String... args) {
        LOGGER.debug("uitvoerenGet");

        Gson gson = builder.create();

        if (args != null) {
            for (String arg : args) {
                adres = adres + "/" + arg;
            }
        }
        LOGGER.info("Aanroepen via GET " + adres);
        System.out.println("Aanroepen via GET " + adres);

        ClientConfig clientConfig = new DefaultClientConfig();
        clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
        Client client = Client.create(clientConfig);
        WebResource webResource = client.resource(adres);
        ClientResponse response;
        if (ingelogdeGebruiker != null) {
            response = webResource.accept("application/json").type("application/json").header("ingelogdeGebruiker", ingelogdeGebruiker.toString()).get(ClientResponse.class);
        } else {
            response = webResource.accept("application/json").type("application/json").get(ClientResponse.class);
        }
        if (response.getStatus() != 200) {
            throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
        }

        return gson.fromJson(response.getEntity(String.class), clazz);
    }

    protected <T> List<T> uitvoerenGetLijst(String adres, Class<T> clazz, String... args) {
        Gson gson = builder.create();

        if (args != null) {
            for (String arg : args) {
                adres = adres + "/" + arg;
            }
        }
        LOGGER.info("Aanroepen via GET " + adres);
        System.out.println("Aanroepen via GET " + adres);

        ClientConfig clientConfig = new DefaultClientConfig();
        clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
        Client client = Client.create(clientConfig);
        WebResource webResource = client.resource(adres);
        ClientResponse response = webResource.accept("application/json").type("application/json").get(ClientResponse.class);
        if (response.getStatus() != 200) {
            throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
        }

        //        Type listOfTestObject = new TypeToken<List<T>>() {
        //        }.getType();
        //        return gson.fromJson(response.getEntity(String.class), listOfTestObject);
        Type listType = getTypeToken();
        List<T> yourClassList = new Gson().fromJson(response.getEntity(String.class), listType);

        return yourClassList;
    }

    protected abstract Type getTypeToken();
}