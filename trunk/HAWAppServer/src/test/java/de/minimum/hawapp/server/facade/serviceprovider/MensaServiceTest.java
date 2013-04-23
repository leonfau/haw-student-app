package de.minimum.hawapp.server.facade.serviceprovider;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.ws.rs.core.MediaType;

import org.junit.Assert;
import org.junit.Test;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;

import de.minimum.hawapp.test.rest.entities.mensaservice.DayplanEntity;

public class MensaServiceTest extends RestTest {

    private static final String MENSA_SERVICE_URL = RestTest.REST_SERVICE_ADDRESS + "/mensaservice";

    @Test
    public void dayplanTest() {
        String day = "Montag";
        WebResource webResource = this.client.resource(MensaServiceTest.MENSA_SERVICE_URL + "/dayplan/" + day);

        ClientResponse response = webResource.accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);

        checkResponse(response);

        DayplanEntity dayplan = response.getEntity(DayplanEntity.class);
        Assert.assertEquals("Day is still the same", day, dayplan.getDay());
    }

    @Test
    public void weekplanTest() {
        WebResource webResource = this.client.resource(MensaServiceTest.MENSA_SERVICE_URL + "/weekplan");

        ClientResponse response = webResource.accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);

        checkResponse(response);

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
