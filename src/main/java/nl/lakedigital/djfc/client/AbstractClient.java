package nl.lakedigital.djfc.client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.jersey.api.client.*;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.client.filter.ClientFilter;
import com.sun.jersey.api.json.JSONConfiguration;
import org.slf4j.Logger;

import java.lang.reflect.Type;
import java.util.List;

public abstract class AbstractClient {
    protected static Logger LOGGER;

    private GsonBuilder builder = new GsonBuilder();
    protected Gson gson = new Gson();
    protected String basisUrl;

    public AbstractClient(Logger LOGGER) {
        this.LOGGER = LOGGER;
    }

    public AbstractClient(String basisUrl, Logger LOGGER) {
        this.basisUrl = basisUrl;
        this.LOGGER = LOGGER;
    }

    public void setBasisUrl(String basisUrl) {
        LOGGER.debug("zet basisurl {}", basisUrl);
        this.basisUrl = basisUrl;
    }

    protected String aanroepenUrlPost(String adres, Object object, Long ingelogdeGebruiker, String trackAndTraceId) {
        Gson gson = builder.create();

        Client client = Client.create();

        WebResource webResource = client.resource(basisUrl + adres);
        String verstuurObject = gson.toJson(object);
        LOGGER.info("Versturen {} naar {}", verstuurObject, basisUrl + adres);

        ClientResponse cr = webResource.accept("application/json").type("application/json").header("ingelogdeGebruiker", ingelogdeGebruiker.toString()).header("trackAndTraceId", trackAndTraceId).post(ClientResponse.class, verstuurObject);

        return cr.getEntity(String.class);
    }

    protected String aanroepenUrlPost(String adres, Object object, Long ingelogdeGebruiker, String trackAndTraceId, String sessie) {
        Gson gson = builder.create();

        Client client = Client.create();

        client.addFilter(new ClientFilter() {

            @Override
            public ClientResponse handle(ClientRequest clientRequest) throws ClientHandlerException {


                clientRequest.getHeaders().putSingle("sessie", sessie);

                ClientResponse response = getNext().handle(clientRequest);


                return response;
            }
        });

        WebResource webResource = client.resource(basisUrl + adres);
        String verstuurObject = gson.toJson(object);
        LOGGER.info("Versturen {} naar {}", verstuurObject, basisUrl + adres);

        ClientResponse cr = webResource.accept("application/json").type("application/json").header("sessie", sessie).header("ingelogdeGebruiker", ingelogdeGebruiker.toString()).header("trackAndTraceId", trackAndTraceId).post(ClientResponse.class, verstuurObject);

        return cr.getEntity(String.class);
    }

    protected void aanroepenUrlPostZonderBody(String adres, Long ingelogdeGebruiker, String trackAndTraceId, String... args) {
        Gson gson = builder.create();

        Client client = Client.create();

        if (args != null) {
            for (String arg : args) {
                adres = adres + "/" + arg;
            }
        }

        WebResource webResource = client.resource(basisUrl + adres);

        webResource.accept("application/json").type("application/json").header("ingelogdeGebruiker", ingelogdeGebruiker.toString()).header("trackAndTraceId", trackAndTraceId).post();
    }

    protected String uitvoerenGet(String adres) {
        //        adres = adres.replace("{{poort}}", bepaalPoort());
        LOGGER.info("Aanroepen via GET " + adres);
        System.out.println("Aanroepen via GET " + adres);

        ClientConfig clientConfig = new DefaultClientConfig();
        clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
        Client client = Client.create(clientConfig);
        WebResource webResource = client.resource(basisUrl + adres);
        ClientResponse response = webResource.accept("application/json").type("application/json").get(ClientResponse.class);
        if (response.getStatus() != 200) {
            throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
        }
        return response.getEntity(String.class);
    }

    protected <T> T uitvoerenGet(String adres, Class<T> clazz, String... args) {
        LOGGER.debug("uitvoerenGet");

        Gson gson = builder.create();

        if (args != null) {
            for (String arg : args) {
                adres = adres + "/" + arg;
            }
        }
        LOGGER.info("Aanroepen via GET " + basisUrl + adres);
        System.out.println("Aanroepen via GET " + basisUrl + adres);

        ClientConfig clientConfig = new DefaultClientConfig();
        clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
        Client client = Client.create(clientConfig);
        WebResource webResource = client.resource(basisUrl + adres);
        ClientResponse response;
        response = webResource.accept("application/json").type("application/json").get(ClientResponse.class);
        if (response.getStatus() != 200) {
            throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
        }

        String result = response.getEntity(String.class);

        System.out.println(result);

        return gson.fromJson(result, clazz);
    }

    protected <T> List<T> uitvoerenGetLijst(String adres, Class<T> clazz, String... args) {
        Gson gson = builder.create();

        if (args != null) {
            for (String arg : args) {
                adres = adres + "/" + arg;
            }
        }
        LOGGER.info("Aanroepen via GET " + basisUrl + adres);
        System.out.println("Aanroepen via GET " + basisUrl + adres);

        ClientConfig clientConfig = new DefaultClientConfig();
        clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
        Client client = Client.create(clientConfig);
        WebResource webResource = client.resource(basisUrl + adres);
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
