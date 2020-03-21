package com.valychbreak.calculator.controller;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.core.convert.format.Format;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;

import java.time.LocalDate;
import java.time.ZonedDateTime;

@Controller("/api")
public class DateObjectController {

    @Post("/date")
    @Consumes(value = MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_PLAIN)
    public HttpResponse<String> testDateParsing(@Body String myClass) {
        return HttpResponse.ok("Executed successfully: " + myClass);
    }

    @Introspected
    public static class MyClass {
        @Format("yyyy-MM-dd")
        private LocalDate date;
        private String name;

        public MyClass() {
        }

        public LocalDate getDate() {
            return date;
        }

        public void setDate(@Format("yyyy-MM-dd") LocalDate date) {
            this.date = date;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "MyClass{" +
                    "date=" + date +
                    ", name='" + name + '\'' +
                    '}';
        }
    }
}
