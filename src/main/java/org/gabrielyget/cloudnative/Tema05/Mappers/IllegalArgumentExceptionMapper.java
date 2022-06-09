package org.gabrielyget.cloudnative.Tema05.Mappers;

import org.gabrielyget.cloudnative.Tema05.Builders.JsonResponseBuilder;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class IllegalArgumentExceptionMapper implements ExceptionMapper<IllegalArgumentException> {

    @Override
    public Response toResponse(IllegalArgumentException exception) {
        return JsonResponseBuilder.builder().withStatus(Response.Status.fromStatusCode(400)).withMessage(exception.getMessage()).build();
    }
}