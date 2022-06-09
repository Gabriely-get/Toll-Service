package org.gabrielyget.cloudnative.Tema05.Mappers;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.gabrielyget.cloudnative.Tema05.Builders.JsonResponseBuilder;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class JsonProcessingExceptionMapper implements ExceptionMapper<JsonProcessingException> {
    @Override
    public Response toResponse(JsonProcessingException exception) {
        return JsonResponseBuilder.builder().withStatus(Response.Status.fromStatusCode(500)).withMessage(exception.getMessage()).build();
    }
}
