package com.example.bookingServicePayara.controller;

import com.example.bookingServicePayara.exception.tools.GlobalExceptionMapper;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

import java.util.HashSet;
import java.util.Set;


@ApplicationPath("/api")
public class HelloApplication extends Application {
//    @Override
//    public Set<Class<?>> getClasses() {
//        Set<Class<?>> resources = new HashSet<>();
//        resources.add(GlobalExceptionMapper.class); // Регистрация маппера
//        // Добавьте и другие ресурсы JAX-RS
//        return resources;
//    }
}
