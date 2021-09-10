/**
 * Exercise 9.1 client
 */
package com.rf.inventory.client;

import javax.net.ssl.SSLContext;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.SslConfigurator;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Demonstrates the JAX-RS 2.0 Client API for consuming messages from 
 * a RESTful web service
 */
public class InventoryClient {
    private static final Logger logger = LoggerFactory.getLogger(InventoryClient.class);
    
    // TODO: Note that BASE_URL uses https and the SSL port 8181 
    //       (no changes required)
    private static final String BASE_URL = "https://localhost:8181/ex09_rest/rs/item";

    // TODO: Examine the settings for the truststore and keystore 
    //       (no changes required)
    // Client truststore file contains trusted root CA certificates
    private static final String CLIENT_TRUSTSTORE = 
            System.getenv("JAVA_HOME") + "/jre/lib/security/cacerts";
    private static final String CLIENT_TRUSTSTORE_PASSWORD = "changeit";
    
    // Client keystore file contains server certificates signed by CA
    private static final String CLIENT_KEYSTORE = "./config/jaxrs-client-keystore";
    private static final String CLIENT_KEYSTORE_PASSWORD = "changeit";

    // TODO: Examine the two Client references. One will have credentials for a user
    //       with role "customer" and the other will have credentials for role "staff".
    private final Client clientCustomer;
    private final Client clientStaff;
    
    // Design note: in a multi-threaded client (for example, a servlet, JSP, or
    // JSF backing bean with session or application scope), remember to synchronize
    // references of the Client instances
    
    // Design note: you can register only one HttpAuthenticationFeature with a Client,
    // so we'll create a different Client for each user (customer and staff).
    // If requests were being sent for different users, we'd need to create a new
    // Client for each request.
    
    public InventoryClient() {
        try {
            // TODO: Examine the configuration of the SslConfigurator 
            //       (no changes required)
            SslConfigurator sslConfig = 
                    SslConfigurator.newInstance()
                                   .trustStoreFile(CLIENT_TRUSTSTORE)
                                   .trustStorePassword(CLIENT_TRUSTSTORE_PASSWORD)
                                   .keyStoreFile(CLIENT_KEYSTORE)
                                   .keyPassword(CLIENT_KEYSTORE_PASSWORD);

            // TODO: Examine the initialization of the two Client instances
            //       (no changes required)
            clientCustomer = createSecureClient(sslConfig, "musicdotcom", "bust");
            clientStaff = createSecureClient(sslConfig, "admin", "adminpass");

        }
        catch (Exception e) {
            System.err.println("Problem setting up security for REST client");
            throw e;
        }
    }

    /**
     * TODO: Note that this method creates a Client configured for HTTP Basic Auth with SSL
     *       (no changes required)
     */
    private Client createSecureClient(SslConfigurator sslConfig, String username, String password) {
    	Client client = null;
    	
        // TODO: use the SslConfigurator parameter to create an SSLContext
        // HINT: see slide 9-21
        sslConfig=SslConfigurator.newInstance()
        		.trustStoreFile(CLIENT_TRUSTSTORE)
        		.trustStorePassword(CLIENT_TRUSTSTORE_PASSWORD)
        		.keyStoreFile(CLIENT_KEYSTORE)
        		.keyStorePassword(CLIENT_KEYSTORE_PASSWORD);
        
        // TODO: Create a Client that uses the SslContext
        // HINT: see slide 9-21
        SSLContext sslContext=sslConfig.createSSLContext();
        client=ClientBuilder.newBuilder().sslContext(sslContext).build();
        
        // TODO: Create an HttpAuthenticationFeature for HTTP Basic Auth
        //       with the username and password parameters
        // HINT: see slide 9-13
        HttpAuthenticationFeature authenticationCustomer=HttpAuthenticationFeature.basic(username, password);
        
        // TODO: Register the customer HttpAuthenticationFeature with the client
        // HINT: see slide 9-13
        client.register(authenticationCustomer);
        
        // TODO: Return the Client
        return client;
    }
    
    public ItemList getItems() {
        logger.debug("getItems() called");
        
        // TODO: Note that the call that sends a GET request is exactly the same
        //       as for an unauthenticated request 
        //       (no changes required).
        ItemList itemList =  clientCustomer.target(BASE_URL)
                                                .path("/all")
                                             .request()
                                             .get(ItemList.class);
        
        logger.debug("getItems() returned a list of " + itemList.getItems().size() + " items");
        
        return itemList;
    }

    /**
     * Returns the HTTP status of the PUT request that updates an item
     */
    public int updateQuantity(int productId, int qty){
        logger.debug("updateQuantity(" + productId + ", " + qty + ") called");

        Item item = new Item();
        item.setProductId(productId);
        item.setQuantity(qty);
        
        // TODO: Note that the call that sends a PUT request is exactly the same
        //       as for an unauthenticated request 
        //       (no changes required).
        Entity<Item> itemEntity = Entity.xml(item);
        Response response = clientStaff.target(BASE_URL)
                                       .path("/{productId}")
                                       .resolveTemplate("productId", productId)
                                       .request(MediaType.APPLICATION_XML)
                                       .put(itemEntity, Response.class);
        int status = response.getStatus();
        
        logger.debug("updateQuantity(" + productId + ", " + qty + ") " +
                     "returned status " + status);
        return status;
    }
    
    public void close() {
        logger.debug("close() called");

        // TODO: close the Client for customers
        // HINT: see slide 9-21
        clientCustomer.close();

        // TODO: close the Client for staff
        clientStaff.close();
    }
    
    public int deleteItem(int productId) {
        logger.debug("deleteItem(" + productId + ") called");

        Response response = clientStaff.target(BASE_URL)
                                       .path("/{productId}")
                                       .resolveTemplate("productId", productId)
                                       .request()
                                       .delete(Response.class);
        int status = response.getStatus();
        logger.debug("deleteItem(" + productId + ") returned status " + status);
        return status;
    }
    
    public void insertItem(int productId, int qty){
        logger.debug("updateQuantity(" + productId + ", " + qty + " called");

        Item item = new Item();
        item.setProductId(productId);
        item.setQuantity(qty);
        insertItem(item);
    }
    
    public void insertItem(Item item){
        logger.debug("insertItem(" + item + ") called");

        Response response = clientStaff.target(BASE_URL)
                                       .request(MediaType.APPLICATION_XML)
                                       .post(Entity.xml(item), Response.class);
        
        String responseBody = response.readEntity(String.class);

        logger.debug("insertItem(" + item + ") returned HTTP status " + 
                response.getStatus() + ", response body: " + responseBody);
    }
    
    @SuppressWarnings("unused")
    public static void main(String[] args) throws Exception {
        InventoryClient inventoryClient = new InventoryClient();
        
        ItemList itemList = inventoryClient.getItems();
        
        int updateStatus = inventoryClient.updateQuantity(3004, 23);
        
        inventoryClient.close();
    }
}
