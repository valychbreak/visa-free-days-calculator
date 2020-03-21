package com.valychbreak.calculator.converter;

import io.micronaut.core.convert.ConversionContext;
import io.micronaut.core.convert.TypeConverter;

import javax.inject.Singleton;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Singleton
public class LocalDateToStringConverter implements TypeConverter<LocalDate, String> {
    @Override
    public Optional<String> convert(LocalDate object, Class<String> targetType) {
        return Optional.of(object.format(DateTimeFormatter.ISO_DATE));
    }

    @Override
    public Optional<String> convert(LocalDate object, Class<String> targetType, ConversionContext context) {
        return this.convert(object, targetType);
    }
}
