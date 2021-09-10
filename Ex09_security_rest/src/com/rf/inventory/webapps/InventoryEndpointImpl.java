package com.rf.inventory.webapps;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rf.inventory.backend.InventoryDAO;
import com.rf.inventory.backend.InventoryDAOJDBCImpl;
import com.rf.inventory.backend.Item;
import com.rf.inventory.backend.ItemList;

/**
 * RESTful service implemented with JAX-RS 2.0
 * 
 * See https://jersey.java.net
 * See the Jersey User Guide http://jersey.java.net/documentation/latest/user-guide.html
 * See JSR 339 Javadocs http://jcp.org/aboutJava/communityprocess/final/jsr339/index.html
 * @author Mike Woinoski
 */
@Path("/item")
public class InventoryEndpointImpl {
    @SuppressWarnings("unused")
    private static Logger log = LoggerFactory.getLogger(InventoryEndpointImpl.class);

    private InventoryDAO dao = new InventoryDAOJDBCImpl();

    /**
     * Handles HTTP GET. Sends the complete inventory.
     * Request URL will be http://host:8080/inventory/rs/item/all
     */
    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_XML)
    public ItemList doGet() {
        ItemList itemList = dao.getItems();
        return itemList;
    }

    /**
     * Handles HTTP Delete. The request URI (e.g. /item/3012) identifies
     * the productId of the item to be deleted from inventory
     * Request URL will be http://host:8080/inventory/rs/item/3012
     */
    @DELETE
    @Path("/{productId}")
    public Response doDelete( @PathParam("productId") int id ) {
        if (id <= 0) {
            return Response.serverError().build();
        }
        dao.removeItem(id);
        return Response.ok().build(); 
    }

    /**
     * Handles HTTP POST. Creates a new Item with a new unique id.
     * Incoming content should be something like <item productId="3212" quantity="4" />
     * Request URL will be http://host:8080/inventory/rs/item
     */
    @POST 
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    // omit @Path since it's the class' default
    public String doPost(Item item) throws WebApplicationException {
        if (item.getProductId() <= 0 || item.getQuantity() < 0) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
        dao.addItem(item.getProductId(), item.getQuantity());
        return "<ok/>";
    }

    /**
     * Handles HTTP PUT. Updates the quantity of productId. Incoming content should be
     * something like <item quantity="4" /> The request URI (e.g. /item/3012)
     * identifies productId
     * Request URL will be http://host:8080/inventory/rs/item/3012
     */
    @PUT
    @Path("/{productId}")
    @Consumes(MediaType.APPLICATION_XML) 
    public Response doPut( @PathParam("productId") int id, 
                           Item item )
        // the last component of the URL is assigned to the path param productId, and the
        // body of the HTTP request is assigned to the parameter content
                    throws WebApplicationException {
        // ignore the productId in the XML and use the URL parameter
        item.setProductId(id);
        if (item.getProductId() <= 0 || item.getQuantity() < 0) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
        dao.updateStockCount(item.getProductId(), item.getQuantity());
        return Response.accepted().build();
    }
}
