package com.pluralsight.handlers;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;

import java.io.ByteArrayInputStream;
import java.net.http.HttpResponse;

public class JsonBodyHandler<T> implements HttpResponse.BodyHandler {

    private final Jsonb jsonb;
    private final Class<T> type;

    private JsonBodyHandler(Jsonb jsonb, Class<T> type) {
        this.jsonb = jsonb;
        this.type = type;
    }

    public static <T> JsonBodyHandler<T> jsonBodyHandler(final Class<T> type) {
        return new JsonBodyHandler<>(JsonbBuilder.create(), type);
    }

    @Override
    public HttpResponse.BodySubscriber<T> apply(final HttpResponse.ResponseInfo responseInfo) {
        return HttpResponse.BodySubscribers.mapping(HttpResponse.BodySubscribers.ofByteArray(),
                byteArray -> this.jsonb.fromJson(new ByteArrayInputStream(byteArray), this.type));
    }
}
