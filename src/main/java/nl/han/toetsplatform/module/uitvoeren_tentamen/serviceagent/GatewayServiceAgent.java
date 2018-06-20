package nl.han.toetsplatform.module.uitvoeren_tentamen.serviceagent;

import com.google.gson.Gson;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import nl.han.toetsplatform.module.uitvoeren_tentamen.exceptions.GatewayCommunicationException;
import nl.han.toetsplatform.module.uitvoeren_tentamen.util.Utils;

import java.util.logging.Level;

public class GatewayServiceAgent implements IGatewayServiceAgent {

    private String baseUrl;
    private Client client;

    public GatewayServiceAgent() {
        this.client = Client.create(new DefaultClientConfig());
        this.baseUrl = "http://94.124.143.127";
    }

    public <T> T get(String resourceUrl, Class<T> type) throws GatewayCommunicationException {
        // request to server
        WebResource webResource = client.resource(this.baseUrl).path(resourceUrl);

        try {
            // reading response from server
            ClientResponse response = webResource.get(ClientResponse.class);
            if (response.getStatus() == 200) {
                return response.getEntity(type);
            } else {
                Utils.getLogger().log(Level.INFO, "Error connecting to gateway GET: " + response.getStatusInfo());
            }
        } catch (Exception e) {
            Utils.getLogger().log(Level.WARNING, "Kon niet verbinden met gateway " + e.getMessage());
        }

        throw new GatewayCommunicationException();
    }

    public <T> void post(String resourceUrl, T entity) throws GatewayCommunicationException {
        // request to server
        WebResource webResource = client.resource(this.baseUrl).path(resourceUrl);

        String json = new Gson().toJson(entity);

        try {
            ClientResponse response = webResource.post(ClientResponse.class, json);

            if (response.getStatus() != 200) {
                Utils.getLogger().log(Level.INFO, "Error connecting to gateway POST: " + response.getStatusInfo());
                throw new GatewayCommunicationException();
            }
        } catch (Exception e) {
            Utils.getLogger().log(Level.WARNING, "Kon niet verbinden met gateway " + e.getMessage());
            throw new GatewayCommunicationException();
        }
    }
}
