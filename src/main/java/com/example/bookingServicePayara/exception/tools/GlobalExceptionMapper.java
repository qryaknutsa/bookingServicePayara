package com.example.bookingServicePayara.exception.tools;

import com.example.bookingServicePayara.exception.TicketDeletingException;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.util.List;
import java.util.stream.Collectors;

@Provider
public class GlobalExceptionMapper implements ExceptionMapper<Throwable> {

    @Override
    public Response toResponse(Throwable exception) {
        int statusCode = Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(); // Код по умолчанию

        if (exception instanceof ConstraintViolationException) {
            statusCode = Response.Status.BAD_REQUEST.getStatusCode(); // Код для ошибок валидации
            ConstraintViolationException validationEx = (ConstraintViolationException) exception;

            // Преобразование ошибок валидации в понятный список
            List<String> errors = validationEx.getConstraintViolations()
                    .stream()
                    .map(violation -> violation.getPropertyPath() + ": " + violation.getMessage())
                    .collect(Collectors.toList());

            ErrorResponseArray errorResponse = new ErrorResponseArray("Ошибка валидации", errors, "Validation Failed");
//            return Response.status(statusCode).entity(errorResponse).build();
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(errorResponse)
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        } else if (exception instanceof TicketDeletingException) {
            String errorResponse = exception.getMessage();
            return Response.status(Response.Status.SERVICE_UNAVAILABLE)
                    .entity(((TicketDeletingException) exception).getObj())
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }

        return Response.status(Response.Status.SERVICE_UNAVAILABLE)
                .entity(exception)
                .type(MediaType.APPLICATION_JSON)
                .build();
//        else if (exception instanceof SomeOtherException) {
//            // Логика для других пользовательских исключений
//            statusCode = Response.Status.NOT_FOUND.getStatusCode(); // Пример кода для других исключений
//            ErrorResponse errorResponse = new ErrorResponse("Resource Not Found", List.of(exception.getMessage()));
//            return Response.status(statusCode).entity(errorResponse).build();
//        }

        // Общий ответ для всех других ошибок
//        CustomErrorResponse errorResponse = new CustomErrorResponse("Server Error: " + exception.getCause().getCause().getObj(), exception.getClass().getName(), "");
//        TicketDeletingException e = (TicketDeletingException) exception.getCause().getCause();
////
//        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
//                .entity(e.getObj())
//                .type(MediaType.APPLICATION_JSON)
//                .build();
    }
}
