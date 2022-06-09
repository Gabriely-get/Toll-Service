package org.gabrielyget.cloudnative.Tema05.Services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.gabrielyget.cloudnative.Tema05.Config.AppConfig;
import org.gabrielyget.cloudnative.Tema05.CustomExceptions.InsufficientMoneyException;
import org.gabrielyget.cloudnative.Tema05.Enums.Vehicle;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.ws.rs.core.Response;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = AppConfig.class)
public class TollServiceTest {
    @Autowired
    private TollService tollService;

    private Response response;
    private JsonNode jsonNode;
    private ObjectMapper mapper;

    @BeforeEach
    public void setup() {
        mapper = new ObjectMapper();
    }

    @Test
    public void shouldGetAllTollValues() throws JsonProcessingException {
        int vehiclesQuantityFromResponse = 0;

        response = tollService.getAllTollPrices();
        jsonNode = mapper.readValue(response.getEntity().toString(), JsonNode.class);

        assertEquals(200, response.getStatus());
        assertEquals(5, jsonNode.get("data").size());
        assertEquals(2, jsonNode.get("data").get("vehicle1").size());
        assertEquals(2, jsonNode.get("data").get("vehicle2").size());
        assertEquals(2, jsonNode.get("data").get("vehicle3").size());
        assertEquals(2, jsonNode.get("data").get("vehicle4").size());
        assertEquals(2, jsonNode.get("data").get("vehicle5").size());

        for (JsonNode vehicle : jsonNode.get("data")) {
            String name = vehicle.get("name").textValue();
            Double price = vehicle.get("price").doubleValue();

            if ("BUS".equals(name)) {
                assertEquals(1.59, price);
                vehiclesQuantityFromResponse++;
            }
            if ("MOTORCYCLE".equals(name)) {
                assertEquals(1.00, price);
                vehiclesQuantityFromResponse++;
            }
            if ("BICYCLE".equals(name)) {
                assertEquals(0.49, price);
                vehiclesQuantityFromResponse++;
            }
            if ("TRUCK".equals(name)) {
                assertEquals(3.59, price);
                vehiclesQuantityFromResponse++;
            }
            if ("BEETLE".equals(name)) {
                assertEquals(2.11, price);
                vehiclesQuantityFromResponse++;
            }
        }

        assertEquals(5, vehiclesQuantityFromResponse);
    }

    @Test
    public void shouldGetSpecificTollPrice() throws JsonProcessingException {

        response = tollService.getTollPrice(Vehicle.BUS, 0);
        jsonNode = mapper.readValue(response.getEntity().toString(), JsonNode.class);

        assertEquals(1.59, jsonNode.get("data").asDouble());

        response = tollService.getTollPrice(Vehicle.MOTORCYCLE, 0);
        jsonNode = mapper.readValue(response.getEntity().toString(), JsonNode.class);

        assertEquals(1.00, jsonNode.get("data").asDouble());

        response = tollService.getTollPrice(Vehicle.BEETLE, 0);
        jsonNode = mapper.readValue(response.getEntity().toString(), JsonNode.class);

        assertEquals(2.11, jsonNode.get("data").asDouble());

        response = tollService.getTollPrice(Vehicle.BICYCLE, 0);
        jsonNode = mapper.readValue(response.getEntity().toString(), JsonNode.class);

        assertEquals(0.49, jsonNode.get("data").asDouble());

        response = tollService.getTollPrice(Vehicle.TRUCK, 1);
        jsonNode = mapper.readValue(response.getEntity().toString(), JsonNode.class);

        assertEquals(3.59, jsonNode.get("data").asDouble());
    }

    @Test
    public void shouldReturnExceptionIfNotTruckVehicleHaveExtraAxles() {

        Throwable exception = assertThrows(IllegalArgumentException.class, () ->
                tollService.getTollPrice(Vehicle.BUS, 3));

        assertEquals("Extra axle is only valid with a Truck vehicle", exception.getMessage());

        Throwable exception02 = assertThrows(IllegalArgumentException.class, () ->
                tollService.getTollPrice(Vehicle.BICYCLE, 3));

        assertEquals("Extra axle is only valid with a Truck vehicle", exception02.getMessage());

        Throwable exception03 = assertThrows(IllegalArgumentException.class, () ->
                tollService.getTollPrice(Vehicle.BEETLE, 3));

        assertEquals("Extra axle is only valid with a Truck vehicle", exception03.getMessage());

        Throwable exception04 = assertThrows(IllegalArgumentException.class, () ->
                tollService.getTollPrice(Vehicle.MOTORCYCLE, 3));

        assertEquals("Extra axle is only valid with a Truck vehicle", exception04.getMessage());
    }

    @Test
    public void shouldReturnExceptionIfTruckVehicleDoesntHaveAxles() {
        Throwable exception = assertThrows(IllegalArgumentException.class, () ->
                tollService.getTollPrice(Vehicle.TRUCK, 0));

        assertEquals("Invalid truck's axle quantity", exception.getMessage());

        Throwable exception02 = assertThrows(IllegalArgumentException.class, () ->
                tollService.getTollPrice(Vehicle.TRUCK, null));

        assertEquals("Invalid truck's axle quantity", exception02.getMessage());
    }

    @Test
    public void shouldPayTheTollPriceAndGetChange() throws JsonProcessingException {
        this.response = tollService.getMoneyChange(Vehicle.BUS, 1.59, 0);
        jsonNode = mapper.readValue(response.getEntity().toString(), JsonNode.class);

        assertEquals(0.0, jsonNode.get("data").asDouble());

        this.response = tollService.getMoneyChange(Vehicle.BUS, 2.59, 0);
        jsonNode = mapper.readValue(response.getEntity().toString(), JsonNode.class);

        assertEquals(0.9999999999999998, jsonNode.get("data").asDouble());


        this.response = tollService.getMoneyChange(Vehicle.MOTORCYCLE, 1.00, 0);
        jsonNode = mapper.readValue(response.getEntity().toString(), JsonNode.class);

        assertEquals(0.0, jsonNode.get("data").asDouble());

        this.response = tollService.getMoneyChange(Vehicle.MOTORCYCLE, 2.00, 0);
        jsonNode = mapper.readValue(response.getEntity().toString(), JsonNode.class);

        assertEquals(1.0, jsonNode.get("data").asDouble());


        this.response = tollService.getMoneyChange(Vehicle.BICYCLE, 0.49, 0);
        jsonNode = mapper.readValue(response.getEntity().toString(), JsonNode.class);

        assertEquals(0.0, jsonNode.get("data").asDouble());

        this.response = tollService.getMoneyChange(Vehicle.BICYCLE, 1.49, 0);
        jsonNode = mapper.readValue(response.getEntity().toString(), JsonNode.class);

        assertEquals(1.0, jsonNode.get("data").asDouble());


        this.response = tollService.getMoneyChange(Vehicle.BEETLE, 2.11, 0);
        jsonNode = mapper.readValue(response.getEntity().toString(), JsonNode.class);

        assertEquals(0.0, jsonNode.get("data").asDouble());

        this.response = tollService.getMoneyChange(Vehicle.BEETLE, 3.11, 0);
        jsonNode = mapper.readValue(response.getEntity().toString(), JsonNode.class);

        assertEquals(1.0, jsonNode.get("data").asDouble());


        this.response = tollService.getMoneyChange(Vehicle.TRUCK, 10.77, 3);
        jsonNode = mapper.readValue(response.getEntity().toString(), JsonNode.class);

        assertEquals(0.0, jsonNode.get("data").asDouble());

        this.response = tollService.getMoneyChange(Vehicle.TRUCK, 11.77, 3);
        jsonNode = mapper.readValue(response.getEntity().toString(), JsonNode.class);

        assertEquals(1.0, jsonNode.get("data").asDouble());
    }

    @Test
    public void shouldReturnExceptionIfMoneyToPayTheTollIsNotEnough() {
        Throwable exception = assertThrows(InsufficientMoneyException.class, () ->
                tollService.getMoneyChange(Vehicle.BUS, 1.50, 0));

        assertEquals("There's not enough money to pay the toll", exception.getMessage());

        Throwable exception02 = assertThrows(InsufficientMoneyException.class, () ->
                tollService.getMoneyChange(Vehicle.MOTORCYCLE, 0.50, 0));

        assertEquals("There's not enough money to pay the toll", exception02.getMessage());

        Throwable exception03 = assertThrows(InsufficientMoneyException.class, () ->
                tollService.getMoneyChange(Vehicle.BICYCLE, 0.40, 0));

        assertEquals("There's not enough money to pay the toll", exception03.getMessage());

        Throwable exception04 = assertThrows(InsufficientMoneyException.class, () ->
                tollService.getMoneyChange(Vehicle.BEETLE, 1.50, 0));

        assertEquals("There's not enough money to pay the toll", exception04.getMessage());

        Throwable exception05 = assertThrows(InsufficientMoneyException.class, () ->
                tollService.getMoneyChange(Vehicle.TRUCK, 1.50, 4));

        assertEquals("There's not enough money to pay the toll", exception05.getMessage());
    }

    @Test
    public void shouldReturnExceptionIfVehicleToPayTheTollIsInvalid() {
        Throwable exception = assertThrows(IllegalArgumentException.class, () ->
                tollService.getMoneyChange(null, 1.50, 0));

        assertEquals("Invalid vehicle", exception.getMessage());
    }

    @Test
    public void shouldReturnExceptionIfPayTruckWithoutAxle() {
        Throwable exception = assertThrows(IllegalArgumentException.class, () ->
                tollService.getMoneyChange(Vehicle.TRUCK, 3.59, null));

        assertEquals("Invalid truck's axle quantity", exception.getMessage());
    }
}