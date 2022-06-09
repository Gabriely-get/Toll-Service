package org.gabrielyget.cloudnative.Tema05.Services;

import org.gabrielyget.cloudnative.Tema05.Builders.JsonResponseBuilder;
import org.gabrielyget.cloudnative.Tema05.CustomExceptions.InsufficientMoneyException;
import org.gabrielyget.cloudnative.Tema05.Enums.Vehicle;
import org.gabrielyget.cloudnative.Tema05.Mappers.IllegalArgumentExceptionMapper;
import org.gabrielyget.cloudnative.Tema05.Mappers.InsufficientMoneyExceptionMapper;
import org.gabrielyget.cloudnative.Tema05.Mappers.JsonProcessingExceptionMapper;
import org.springframework.stereotype.Service;

import javax.ws.rs.*;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;
import java.util.*;

@Service
@ApplicationPath("toll")
@Produces("application/json")
public class TollService extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> classes = new HashSet<>();
        classes.add(InsufficientMoneyExceptionMapper.class);
        classes.add(IllegalArgumentExceptionMapper.class);
        classes.add(JsonProcessingExceptionMapper.class);
        return classes;
    }

    @GET
    @Path("prices")
    public Response getAllTollPrices() {
        int i = 1;
        Map<Vehicle, Double> mapVehicleValue = new HashMap<>();
        JsonResponseBuilder jsonResponseBuilder = new JsonResponseBuilder();

        Arrays.stream(Vehicle.values()).forEach(vehicleName -> mapVehicleValue.put(vehicleName, vehicleName.getPrice()) );

        for (Vehicle vehicle : mapVehicleValue.keySet()) {
            String name = "vehicle" + i;

            jsonResponseBuilder.withData("name", vehicle.name(), "price", vehicle.getPrice())
                    .withinTree(name);

            ++i;
        }

        return jsonResponseBuilder.withinData().build();
    }

    @GET
    @Path("price/{vehicle}")
    public Response getTollPrice(
            @PathParam("vehicle") Vehicle vehicle,
            @QueryParam("axle") Integer axle
    ) throws NullPointerException, IllegalArgumentException {

        if (vehicle == null) throw new IllegalArgumentException("Invalid vehicle");
        Double price = getPriceVerification(vehicle, axle);

        return JsonResponseBuilder.builder().withData(price).build();
    }

    @GET
    @Path("payment/{money}/change")
    public Response getMoneyChange(
            @QueryParam("vehicle") Vehicle vehicle,
            @PathParam("money") Double money,
            @QueryParam("axle") Integer axle
    ) throws IllegalArgumentException, InsufficientMoneyException {
        
        if (vehicle == null) throw new IllegalArgumentException("Invalid vehicle");

        Double price;
        price = getPriceVerification(vehicle, axle);
        JsonResponseBuilder jsonResponseBuilder = new JsonResponseBuilder();

        if (money < price) {
            throw new InsufficientMoneyException("There's not enough money to pay the toll");
        } else if (money > price) {
            return jsonResponseBuilder.withData(money - price).build();
        } else {
            return jsonResponseBuilder.withData(0.0).build();
        }
    }

    private Double getPriceVerification(Vehicle vehicle, Integer axle) {
        Double price;

        if (!(vehicle.equals(Vehicle.TRUCK)) && (!(axle == null) && axle > 0))
            throw new IllegalArgumentException("Extra axle is only valid with a Truck vehicle");

        if (vehicle.equals(Vehicle.TRUCK)) {
            if (axle == null || axle == 0)
                throw new IllegalArgumentException("Invalid truck's axle quantity");

            price = Vehicle.TRUCK.truckPerAxis(axle);
        }
        else price = vehicle.getPrice();

        return price;
    }
}
