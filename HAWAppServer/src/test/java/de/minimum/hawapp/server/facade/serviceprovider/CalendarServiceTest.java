package de.minimum.hawapp.server.facade.serviceprovider;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import javax.ws.rs.core.MediaType;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;

import de.minimum.hawapp.server.persistence.calendar.CategoryPO;

public class CalendarServiceTest extends RestTest{
	 private static final String CALENDAR_SERVICE = RestTest.REST_SERVICE_ADDRESS + "/calendarservice";
	@Before
	public void before(){
		WebResource webResource = this.client.resource( CALENDAR_SERVICE+ "/testContext/start");
		webResource.accept(MediaType.TEXT_PLAIN).get(String.class);
	}
	@Test
	    public void getAllCategoriesTest() {
	        
	        WebResource webResource = this.client.resource( CALENDAR_SERVICE+ "/category");
	        ClientResponse response = webResource.accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);

	        checkResponse(response);

	        List<CategoryPO> categories = response.getEntity(new GenericType<List<CategoryPO>>(){
	        });
	    	List<String> categoryNames=Arrays.asList("BAI1","BAI2","BAI3", "BAI4", "BAI5", "BAI6","BTI1", "BTI2", "BTI3",
	    			"BTI4","BTI5","BTI6","BWI1","BWI2","BWI3","BWI4","BWI5","BWI6","MINF1","MINF2", "MINF3", "MINF4", "INF", "GW", "Sonstiges");
	       for(CategoryPO category:categories){
	    	   assertTrue(categoryNames.contains(category.getName()));
	       }
	 
	    }
	@Before
	public void after(){
		WebResource webResource = this.client.resource( CALENDAR_SERVICE+ "/testContext/stop");
		webResource.accept(MediaType.TEXT_PLAIN).get(String.class);
	}
}
