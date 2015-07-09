package com.ev3.item;

import com.ev3.startup.StartupEvent;

import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collection;


@Path("/items")
public class ItemService {

    @Inject
    Items items;


    public void createSampleTodoItems(@Observes StartupEvent startupEvent) {
        int i = 20;
        for (int j = 1; j <= i; j++) {
            String title = "Item #" + j;
            items.createItem(title);
        }
    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllItems() {
        Collection<Item> allItems = items.findAllItems();
        GenericEntity<Collection<Item>> list = new GenericEntity<Collection<Item>>(allItems) {
        };

        return Response.ok(list).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response createItem(Item item) {
        Item newItem = items.createItem(item.getTitle());
        return Response.status(Response.Status.CREATED).entity(newItem).build();
    }

    @GET
    @Path("/{itemId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getItem(@PathParam("itemId") String itemId) {
        Item item = items.findItem(itemId);

        return Response.ok(item).build();
    }
}