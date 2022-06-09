package org.gabrielyget.cloudnative.Tema05.Builders;

import org.gabrielyget.cloudnative.Tema05.Config.AppConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.ws.rs.core.Response;

import static org.gabrielyget.cloudnative.Tema05.Enums.Vehicle.BUS;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = AppConfig.class)
public class JsonResponseBuilderTest {

    @Test
    public void shouldBuildACompleteTollServiceResponse() {
        Response response = JsonResponseBuilder.builder().withData("name", BUS.name(), "price", BUS.getPrice())
                .withinTree("vehicle").withinData().build();

        assertEquals("{\"data\":{\"vehicle\":{\"name\":\"BUS\",\"price\":1.59}}}", response.getEntity().toString());
    }

    @Test
    public void shouldBuildAnExceptionMapperResponse() {
        Response response = JsonResponseBuilder.builder().withStatus(Response.Status.fromStatusCode(500)).withMessage("Error").build();

        assertEquals("{\"message\":\"Error\"}", response.getEntity().toString());
    }
}
