package de.minimum.hawapp.server.facade.serviceprovider;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.ws.rs.core.MediaType;

import org.junit.Assert;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;

import de.minimum.hawapp.test.rest.entities.mensaservice.DayplanEntity;

public class MensaServiceTest extends RestTest {

    public MensaServiceTest(Client client) {
        super(client);
    }

    public void dayplanTest() {
        String day = "Montag";
        WebResource webResource = this.client.resource(RestTest.REST_SERVICE_ADDRESS + "/mensaservice/dayplan/" + day);

        ClientResponse response = webResource.accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);

        if (response.getStatus() != 200) {
            throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
        }

        DayplanEntity dayplan = response.getEntity(DayplanEntity.class);
        Assert.assertEquals("Day is still the same", day, dayplan.getDay());
    }

    public void weekplanTest() {
        WebResource webResource = this.client.resource(RestTest.REST_SERVICE_ADDRESS + "/mensaservice/weekplan");

        ClientResponse response = webResource.accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);

        if (response.getStatus() != 200) {
            throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
        }

        List<DayplanEntity> planList = response.getEntity(new GenericType<List<DayplanEntity>>() {
        });
        Assert.assertTrue("Jeder Tag einmalig", isEveryDayUnique(planList));
        Assert.assertTrue("Jeder Wochentag vorhanden", isEveryWeekdayExisting(planList));
    }

    private boolean isEveryWeekdayExisting(List<DayplanEntity> planList) {
        List<String> dayList = new ArrayList<>();
        for(DayplanEntity dayplan : planList) {
            dayList.add(dayplan.getDay());
        }
        return dayList.containsAll(Arrays.asList("Montag", "Dienstag", "Mittwoch", "Donnerstag", "Freitag"));
    }

    private boolean isEveryDayUnique(List<DayplanEntity> planList) {
        List<String> dayList = new ArrayList<>();
        for(DayplanEntity dayplan : planList) {
            if (dayList.contains(dayplan.getDay()))
                return false;
            else
                dayList.add(dayplan.getDay());
        }
        return true;
    }
}
