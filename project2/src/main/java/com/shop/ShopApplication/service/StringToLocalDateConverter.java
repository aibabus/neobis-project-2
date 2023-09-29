package com.shop.ShopApplication.service;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class StringToLocalDateConverter implements Converter<String, LocalDate> {
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");


    @Override
    public LocalDate convert(String source) {
        try {
            return LocalDate.parse(source, dateFormatter);
        } catch (Exception e) {
            return null;
        }
    }
}
