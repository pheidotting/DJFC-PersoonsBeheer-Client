package nl.lakedigital.djfc.client;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.jersey.api.client.*;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.client.filter.ClientFilter;
import com.sun.jersey.api.json.JSONConfiguration;
import org.slf4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

public abstract class AbstractClient<D> {
    protected static Logger LOGGER;

    private GsonBuilder builder = new GsonBuilder();
    protected Gson gson = new Gson();
    protected String basisUrl;
    protected XmlMapper mapper = new XmlMapper();

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

    protected D getXML(String uri, Class<D> clazz, boolean urlEncoden, String... args) {
        StringBuilder stringBuilder = new StringBuilder();
        if (args != null) {
            for (String arg : args) {
                stringBuilder.append("/");
                stringBuilder.append(arg);
            }
        }
        URL url;
        try {
            if (urlEncoden) {
                url = new URL(basisUrl + uri + URLEncoder.encode(stringBuilder.toString(), "UTF-8").replace("+", "%20"));
            } else {
                url = new URL(basisUrl + uri + stringBuilder.toString());
            }
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/xml");

            InputStream xml = connection.getInputStream();

            D response = mapper.readValue(xml, clazz);

            connection.disconnect();

            return response;
        } catch (IOException e) {
            throw new LeesFoutException("Fout bij omzetten adres", e);
        }
    }

    @Deprecated
    protected String aanroepenUrlPost(String adres, Object object, Long ingelogdeGebruiker, String trackAndTraceId) {
        Gson gson = builder.create();

        Client client = Client.create();

        WebResource webResource = client.resource(basisUrl + adres);
        String verstuurObject = gson.toJson(object);
        LOGGER.info("Versturen {} naar {}", verstuurObject, basisUrl + adres);

        ClientResponse cr = webResource.accept("application/json").type("application/json").header("ingelogdeGebruiker", ingelogdeGebruiker.toString()).header("trackAndTraceId", trackAndTraceId).post(ClientResponse.class, verstuurObject);

        return cr.getEntity(String.class);
    }

    @Deprecated
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

    @Deprecated
    protected void aanroepenUrlPostZonderBody(String adres, Long ingelogdeGebruiker, String trackAndTraceId, String... args) {
        Client client = Client.create();

        StringBuilder stringBuilder = new StringBuilder();
        if (args != null) {
            for (String arg : args) {
                stringBuilder.append("/");
                stringBuilder.append(arg);
            }
        }

        WebResource webResource = client.resource(basisUrl + adres + stringBuilder.toString());

        webResource.accept("application/json").type("application/json").header("ingelogdeGebruiker", ingelogdeGebruiker.toString()).header("trackAndTraceId", trackAndTraceId).post();
    }

    //Functie bestaat alleen tbv PowerMock
    @Deprecated
    protected String uitvoerenGetString(String adres) {
        return uitvoerenGet(adres);
    }

    @Deprecated
    protected String uitvoerenGet(String adres) {
        LOGGER.info("Aanroepen via GET " + adres);
        System.out.println("Aanroepen via GET " + adres);

        ClientConfig clientConfig = new DefaultClientConfig();
        clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
        Client client = Client.create(clientConfig);
        WebResource webResource = client.resource(basisUrl + adres);
        ClientResponse response = webResource.accept("application/json").type("application/json").get(ClientResponse.class);
        if (response.getStatus() != 200) {
            throw new LeesFoutException("Failed : HTTP error code : " + response.getStatus());
        }
        return response.getEntity(String.class);
    }

    @Deprecated
    protected <T> T uitvoerenGet(String adres, Class<T> clazz, String... args) {
        LOGGER.debug("uitvoerenGet");

        Gson gson = builder.create();

        StringBuilder stringBuilder = new StringBuilder();
        if (args != null) {
            for (String arg : args) {
                stringBuilder.append("/");
                stringBuilder.append(arg);
            }
        }
        LOGGER.info("Aanroepen via GET " + basisUrl + adres + stringBuilder.toString());
        System.out.println("Aanroepen via GET " + basisUrl + adres);

        ClientConfig clientConfig = new DefaultClientConfig();
        clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
        Client client = Client.create(clientConfig);
        WebResource webResource = client.resource(basisUrl + adres);
        ClientResponse response;
        response = webResource.accept("application/json").type("application/json").get(ClientResponse.class);
        if (response.getStatus() != 200) {
            throw new LeesFoutException("Failed : HTTP error code : " + response.getStatus());
        }

        String result = response.getEntity(String.class);

        System.out.println(result);

        return gson.fromJson(result, clazz);
    }

    @Deprecated
    protected <T> List<T> uitvoerenGetLijst(String adres, Class<T> clazz, String... args) {
        StringBuilder stringBuilder = new StringBuilder();
        if (args != null) {
            for (String arg : args) {
                stringBuilder.append("/");
                stringBuilder.append(arg);
            }
        }
        LOGGER.info("Aanroepen via GET " + basisUrl + adres + stringBuilder.toString());
        System.out.println("Aanroepen via GET " + basisUrl + adres + stringBuilder.toString());

        ClientConfig clientConfig = new DefaultClientConfig();
        clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
        Client client = Client.create(clientConfig);
        WebResource webResource = client.resource(basisUrl + adres);
        ClientResponse response = webResource.accept("application/json").type("application/json").get(ClientResponse.class);
        if (response.getStatus() != 200) {
            throw new LeesFoutException("Failed : HTTP error code : " + response.getStatus());
        }

        Type listType = getTypeToken();
        List<T> yourClassList = new Gson().fromJson(response.getEntity(String.class), listType);

        return yourClassList;
    }

    protected abstract Type getTypeToken();
}
