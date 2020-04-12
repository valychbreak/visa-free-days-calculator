package com.valychbreak.calculator.controller;

import io.micronaut.core.io.ResourceResolver;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Error;
import io.micronaut.http.hateoas.JsonError;
import io.micronaut.http.hateoas.Link;
import io.micronaut.http.server.types.files.StreamedFile;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecuredAnnotationRule;

import java.net.URL;

@Controller("/notfound")
@Secured(SecuredAnnotationRule.IS_ANONYMOUS)
public class NotFoundController {

    private final ResourceResolver resourceResolver;

    public NotFoundController(ResourceResolver resourceResolver) {
        this.resourceResolver = resourceResolver;
    }

    @Error(status = HttpStatus.NOT_FOUND, global = true)
    public HttpResponse<?> notFound(HttpRequest<?> httpRequest) {
        if (isClientRequestPath(httpRequest)) {
            URL clientIndexPage = resourceResolver.getResource("classpath:public/index.html").orElseThrow();
            return HttpResponse.ok(new StreamedFile(clientIndexPage));
        }

        JsonError error = new JsonError("Page Not Found")
                .link(Link.SELF, Link.of(httpRequest.getUri()));

        return HttpResponse.notFound(error);
    }

    private boolean isClientRequestPath(HttpRequest<?> httpRequest) {
        return !httpRequest.getPath().contains("/api");
    }
}
