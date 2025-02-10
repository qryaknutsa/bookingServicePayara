package com.example.bookingServicePayara.exception.tools;

import com.example.bookingServicePayara.exception.*;
import jakarta.json.bind.JsonbException;
import jakarta.ws.rs.ProcessingException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import jakarta.xml.soap.*;

import javax.xml.namespace.QName;


@Provider
public class GlobalExceptionMapper implements ExceptionMapper<Throwable> {
    private static final String UNPROCESSABLE_ENTITY = "Ошибка валидации данных";
    private static final String BAD_REQUEST = "Некорректные данные";
    private static final String INTERNAL_SERVER_ERROR = "Внутренняя ошибка сервера";
    private static final String NOT_FOUND = "Объект не найден";
    private static final String SERVICE_UNAVAILABLE = "Сервис временно не доступен";


    @Override
    public Response toResponse(Throwable exception)  {
        if (exception instanceof CustomNotFound) {
            try {
                return eventNotFoundHandler(exception);
            } catch (SOAPException e) {
                throw new RuntimeException(e);
            }
        }
//        else if (exception instanceof MultipleNotFound) {
//            return multipleNotFoundHandler(exception);
//        } else if (exception instanceof InvalidParameter) {
//            return invalidParameterHandler(exception);
//        } else if (exception instanceof TooLateToDelete) {
//            return tooLateToDeleteHandler(exception);
//        } else if (exception instanceof IncorrectParameter) {
//            return incorrectTypeHandler(exception);
//        } else if (exception instanceof TicketServiceNotAvailable) {
//            return ticketServiceNotAvailableHandler(exception);
//        } else if (exception instanceof ProcessingException) {
//            return processingExceptionHandler(exception);
//        } else if (exception instanceof AlreadyVIPException) {
//            return alreadyVipHandler(exception);
//        } else {
//            return exceptionHandler(exception);
//        }
        return null;
    }


    //    public static SOAPFault eventNotFoundHandler(Throwable exception) throws SOAPException {
//        CustomNotFound customNotFound = (CustomNotFound) exception;
//
//        MessageFactory messageFactory = MessageFactory.newInstance();
//        SOAPMessage message = messageFactory.createMessage();
//        SOAPBody body = message.getSOAPBody();
//        QName faultName = new QName(NOT_FOUND);
//        return body.addFault(faultName, customNotFound.getMessage());
//
//    }

    public Response eventNotFoundHandler(Throwable exception) throws SOAPException {
        CustomNotFound customNotFound = (CustomNotFound) exception;
        CustomErrorResponse errorResponse = new CustomErrorResponse(NOT_FOUND, customNotFound.getMessage(), "");
        return Response.status(Response.Status.NOT_FOUND).entity(errorResponse).build();

    }

    public static SOAPFault ticketServiceNotAvailableHandler(Throwable exception) throws SOAPException {
        TicketServiceNotAvailable ticketServiceNotAvailable = (TicketServiceNotAvailable) exception;

        MessageFactory messageFactory = MessageFactory.newInstance();
        SOAPMessage message = messageFactory.createMessage();
        SOAPBody body = message.getSOAPBody();
        QName faultName = new QName(SERVICE_UNAVAILABLE);
        return body.addFault(faultName, ticketServiceNotAvailable.getMessage());
    }

    public static SOAPFault invalidParameterHandler(Throwable exception) throws SOAPException {
        InvalidParameter invalidParameter = (InvalidParameter) exception;
//        ErrorResponseArray errorResponse = new ErrorResponseArray(UNPROCESSABLE_ENTITY, invalidParameter.getMessages(), getFullURL());
//        return Response.status(422)
//                .entity(errorResponse)
//                .type(MediaType.APPLICATION_JSON)
//                .build();

        MessageFactory messageFactory = MessageFactory.newInstance();
        SOAPMessage message = messageFactory.createMessage();
        SOAPBody body = message.getSOAPBody();
        QName faultName = new QName(UNPROCESSABLE_ENTITY);
        return body.addFault(faultName, invalidParameter.getMessage());
    }


    public static SOAPFault tooLateToDeleteHandler(Throwable exception) throws SOAPException {
        TooLateToDelete tooLateToDelete = (TooLateToDelete) exception;
//        CustomErrorResponse errorResponse = new CustomErrorResponse(UNPROCESSABLE_ENTITY, tooLateToDelete.getMessage(), getFullURL());
//        return Response.status(422)
//                .entity(errorResponse)
//                .type(MediaType.APPLICATION_JSON)
//                .build();
        MessageFactory messageFactory = MessageFactory.newInstance();
        SOAPMessage message = messageFactory.createMessage();
        SOAPBody body = message.getSOAPBody();
        QName faultName = new QName(UNPROCESSABLE_ENTITY);
        return body.addFault(faultName, tooLateToDelete.getMessage());
    }

    public static SOAPFault alreadyVipHandler(Throwable exception) throws SOAPException {
        AlreadyVIPException alreadyVIPException = (AlreadyVIPException) exception;
//        CustomErrorResponse errorResponse = new CustomErrorResponse(UNPROCESSABLE_ENTITY, tooLateToDelete.getMessage(), getFullURL());
//        return Response.status(Response.Status.BAD_REQUEST)
//                .entity(errorResponse)
//                .type(MediaType.APPLICATION_JSON)
//                .build();
        MessageFactory messageFactory = MessageFactory.newInstance();
        SOAPMessage message = messageFactory.createMessage();
        SOAPBody body = message.getSOAPBody();
        QName faultName = new QName(BAD_REQUEST);
        return body.addFault(faultName, alreadyVIPException.getMessage());
    }

    public static SOAPFault incorrectTypeHandler(Throwable exception) throws SOAPException {
        IncorrectParameter incorrectParameter = (IncorrectParameter) exception;
//        ErrorResponseArray errorResponse = new ErrorResponseArray(BAD_REQUEST, incorrectParameter.getMessages(), getFullURL());
//        return Response.status(Response.Status.BAD_REQUEST)
//                .entity(errorResponse)
//                .type(MediaType.APPLICATION_JSON)
//                .build();
        MessageFactory messageFactory = MessageFactory.newInstance();
        SOAPMessage message = messageFactory.createMessage();
        SOAPBody body = message.getSOAPBody();
        QName faultName = new QName(BAD_REQUEST);
        return body.addFault(faultName, incorrectParameter.getMessage());
    }

    public static SOAPFault multipleNotFoundHandler(Throwable exception) throws SOAPException {
        MultipleNotFound multipleNotFound = (MultipleNotFound) exception;
//        ErrorResponseArray errorResponse = new ErrorResponseArray(BAD_REQUEST, multipleNotFound.getMessages(), getFullURL());
//        return Response.status(Response.Status.BAD_REQUEST)
//                .entity(errorResponse)
//                .type(MediaType.APPLICATION_JSON)
//                .build();

        MessageFactory messageFactory = MessageFactory.newInstance();
        SOAPMessage message = messageFactory.createMessage();
        SOAPBody body = message.getSOAPBody();
        QName faultName = new QName(BAD_REQUEST);
        return body.addFault(faultName, multipleNotFound.getMessage());

    }

    public static SOAPFault processingExceptionHandler(Throwable exception) throws SOAPException {
        ProcessingException q = (ProcessingException) exception;
        if (q.getCause() instanceof JsonbException) {
            JsonbException qq = (JsonbException) q.getCause();
            String field = qq.getMessage().split("'")[1].split("'")[0];
//            List<String> mes = new ArrayList<>();
//            mes.add("Не получилось десериализовать значение этого поля: " + field);
//            ErrorResponseArray errorResponse = new ErrorResponseArray(BAD_REQUEST, mes, getFullURL());
//            return Response.status(Response.Status.BAD_REQUEST)
//                    .entity(errorResponse)
//                    .type(MediaType.APPLICATION_JSON)
//                    .build();

            MessageFactory messageFactory = MessageFactory.newInstance();
            SOAPMessage message = messageFactory.createMessage();
            SOAPBody body = message.getSOAPBody();
            QName faultName = new QName(BAD_REQUEST);
            return body.addFault(faultName, "Не получилось десериализовать значение этого поля: " + field);
        }
        return exceptionHandler(exception);
    }


    public static SOAPFault exceptionHandler(Throwable exception) throws SOAPException {
        Exception exception1 = (Exception) exception;
//        CustomErrorResponse errorResponse = new CustomErrorResponse(INTERNAL_SERVER_ERROR, exception1.toString(), getFullURL());
//        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
//                .entity(errorResponse)
//                .type(MediaType.APPLICATION_JSON)
//                .build();

        MessageFactory messageFactory = MessageFactory.newInstance();
        SOAPMessage message = messageFactory.createMessage();
        SOAPBody body = message.getSOAPBody();
        QName faultName = new QName(INTERNAL_SERVER_ERROR);
        return body.addFault(faultName, exception1.toString());

    }
}
