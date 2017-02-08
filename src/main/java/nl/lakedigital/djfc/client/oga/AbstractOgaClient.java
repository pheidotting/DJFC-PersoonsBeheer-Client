package nl.lakedigital.djfc.client.oga;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;
import nl.lakedigital.djfc.client.AbstractClient;
import nl.lakedigital.djfc.client.LeesFoutException;
import nl.lakedigital.djfc.commons.json.AbstracteJsonEntiteitMetSoortEnId;
import org.slf4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

public abstract class AbstractOgaClient<T extends AbstracteJsonEntiteitMetSoortEnId, D> extends AbstractClient {
    private GsonBuilder builder = new GsonBuilder();
    protected Gson gson = new Gson();
    protected XmlMapper mapper = new XmlMapper();


    public AbstractOgaClient(Logger LOGGER) {
        super(LOGGER);
    }

    public AbstractOgaClient(String basisUrl, Logger LOGGER) {
        super(basisUrl, LOGGER);
    }


    public abstract List<T> lijst(String soortEntiteit, Long entiteitId);

    public abstract String opslaan(List<T> jsonAdressen, Long ingelogdeGebruiker, String trackAndTraceId);

    public abstract void verwijder(String soortEntiteit, Long entiteitId, Long ingelogdeGebruiker, String trackAndTraceId);

    public abstract List<T> zoeken(String zoekterm);

    protected D getXMLVoorLijstOGA(String uri, Class<D> clazz, String... args) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        if (args != null) {
            for (String arg : args) {
                stringBuilder.append("/");
                stringBuilder.append(arg);
            }
        }

        URL url = new URL(uri + stringBuilder.toString());
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Accept", "application/xml");

        InputStream xml = connection.getInputStream();

        D response = mapper.readValue(xml, clazz);

        connection.disconnect();

        return response;
    }

    protected String aanroepenUrlPost(String adres, Object object, Long ingelogdeGebruiker, String trackAndTraceId) {
        Gson gson = builder.create();

        Client client = Client.create();

        WebResource webResource = client.resource(basisUrl + adres);
        String verstuurObject = gson.toJson(object);
        LOGGER.info("Versturen {}", verstuurObject);
        System.out.println("Versturen " + verstuurObject + " naar " + basisUrl + adres);

        String gebruiker = null;
        if (ingelogdeGebruiker != null) {
            gebruiker = String.valueOf(ingelogdeGebruiker);
        }

        ClientResponse cr = webResource.accept("application/json").type("application/json").header("ingelogdeGebruiker", gebruiker).header("trackAndTraceId", trackAndTraceId).post(ClientResponse.class, verstuurObject);

        return cr.getEntity(String.class);
    }

    protected void aanroepenUrlPostZonderBody(String adres, String trackAndTraceId, Long ingelogdeGebruiker, String... args) {
        Client client = Client.create();

        StringBuilder stringBuilder = new StringBuilder();
        if (args != null) {
            for (String arg : args) {
                stringBuilder.append("/");
                stringBuilder.append(arg);
            }
        }

        String gebruiker = null;
        if (ingelogdeGebruiker != null) {
            gebruiker = String.valueOf(ingelogdeGebruiker);
        }

        WebResource webResource = client.resource(basisUrl + adres + stringBuilder.toString());

        webResource.accept("application/json").type("application/json").header("ingelogdeGebruiker", gebruiker).header("trackAndTraceId", trackAndTraceId).post();
    }

    protected String uitvoerenGet(String adres) {
        LOGGER.info("Aanroepen via GET " + basisUrl + adres);
        System.out.println("Aanroepen via GET " + basisUrl + adres);

        ClientConfig clientConfig = new DefaultClientConfig();
        clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
        Client client = Client.create(clientConfig);
        WebResource webResource = client.resource(basisUrl + adres);
        ClientResponse response = webResource.accept("application/json").type("application/json").get(ClientResponse.class);
        if (response.getStatus() != 200) {
            throw new LeesFoutException("Failed : HTTP error code : " + response.getStatus());
        }

        String ret = response.getEntity(String.class);

        LOGGER.debug(ret);

        return ret;
    }

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
        System.out.println("Aanroepen via GET " + basisUrl + adres + stringBuilder.toString());

        ClientConfig clientConfig = new DefaultClientConfig();
        clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
        Client client = Client.create(clientConfig);
        WebResource webResource = client.resource(basisUrl + adres);
        ClientResponse response;
        response = webResource.accept("application/json").type("application/json").get(ClientResponse.class);
        if (response.getStatus() != 200) {
            throw new LeesFoutException("Failed : HTTP error code : " + response.getStatus());
        }

        String ret = response.getEntity(String.class);

        LOGGER.debug(ret);

        return gson.fromJson(ret, clazz);
    }

    protected <T> List<T> uitvoerenGetLijst(String url, Class<T> clazz, String... args) {
        String adres = url;
        StringBuilder stringBuilder = new StringBuilder();
        if (args != null) {
            for (String arg : args) {
                stringBuilder.append("/");
                stringBuilder.append(arg);
            }
        }
        LOGGER.info("Aanroepen via GET " + basisUrl + adres + stringBuilder.toString());
        try {
            adres = URLEncoder.encode(adres, "UTF-8").replace("+", "%20");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
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

    protected <T> List<T> uitvoerenGetLijstZonderEncoding(String adres, Class<T> clazz, String... args) {
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
