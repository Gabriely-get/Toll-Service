package org.gabrielyget.cloudnative.Tema05.Mappers;

import org.gabrielyget.cloudnative.Tema05.Builders.JsonResponseBuilder;
import org.gabrielyget.cloudnative.Tema05.CustomExceptions.InsufficientMoneyException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class InsufficientMoneyExceptionMapper implements ExceptionMapper<InsufficientMoneyException> {

    @Override
    public Response toResponse(InsufficientMoneyException exception) {
        return JsonResponseBuilder.builder().withStatus(Response.Status.fromStatusCode(400)).withMessage(exception.getMessage()).build();
    }
}