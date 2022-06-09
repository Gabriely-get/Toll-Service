package org.gabrielyget.cloudnative.Tema05.Builders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

public class JsonResponseBuilder {
    Response.Status status = null;
    ObjectMapper mapper = new ObjectMapper();
    ObjectNode jsonNode;
    ObjectNode dataForTree;
    ObjectNode tree = mapper.createObjectNode();

    public JsonResponseBuilder() {

    }

    public static JsonResponseBuilder builder() {
        return new JsonResponseBuilder();
    }

    public JsonResponseBuilder withStatus(Response.Status status) {
        this.status = status;
        return this;
    }

    public JsonResponseBuilder withMessage(String message) {
        this.jsonNode = mapper.createObjectNode();
        this.jsonNode.put("message", message);
        return this;
    }

    public JsonResponseBuilder withData(Double data) {
        this.jsonNode = mapper.createObjectNode();
        this.jsonNode.put("data", data);
        return this;
    }

    public JsonResponseBuilder withData(String dataName01, String data01, String dataName02, Double data02) {
        this.jsonNode = mapper.createObjectNode();
        this.jsonNode.put(dataName01, data01);
        this.jsonNode.put(dataName02, data02);
        return this;
    }

    public JsonResponseBuilder withinTree(String name) {
        this.tree.set(name, this.jsonNode);
        return this;
    }

    public JsonResponseBuilder withinData() {
        this.dataForTree = mapper.createObjectNode();

        this.dataForTree.set("data", this.tree);
        return this;
    }

    public Response build() {
        ObjectNode jsonToResponse;

        if (this.status == null) this.status = Response.Status.fromStatusCode(200);

        if(this.dataForTree == null && this.tree.isEmpty()) jsonToResponse = this.jsonNode;
        else if (this.dataForTree == null) jsonToResponse = this.tree;
        else jsonToResponse = this.dataForTree;

        ResponseBuilder responseBuilder = Response.status(this.status).entity(jsonToResponse).type("application/json");
        return responseBuilder.build();
    }
}
