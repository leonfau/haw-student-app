package de.minimum.hawapp.test.rest;

import org.codehaus.jackson.jaxrs.JacksonJsonProvider;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.rules.TestRule;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

import de.minimum.hawapp.server.facade.serviceprovider.MensaServiceTest;
import de.minimum.hawapp.server.facade.serviceprovider.RestTestServiceTest;
import de.minimum.hawapp.test.util.ServerStartRule;

public class TestWithServer {

    @ClassRule
    public static TestRule rule = new ServerStartRule();

    private Client client;

    public TestWithServer() {
        ClientConfig cfg = new DefaultClientConfig();
        cfg.getClasses().add(JacksonJsonProvider.class);// Sonst funktioniert
                                                        // das Mapping auf
                                                        // Listen nicht
        this.client = Client.create(cfg);
    }

    @Test
    public void restTestServiceTest() {
        new RestTestServiceTest(this.client).test();
    }

    @Test
    public void mensaServiceTest() {
        MensaServiceTest test = new MensaServiceTest(this.client);
        test.dayplanTest();
        test.weekplanTest();
    }
}
