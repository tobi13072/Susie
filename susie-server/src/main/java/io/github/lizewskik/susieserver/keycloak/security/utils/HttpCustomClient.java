package io.github.lizewskik.susieserver.keycloak.security.utils;

import org.springframework.stereotype.Component;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Component
public class HttpCustomClient {

    private final HttpClient client;
    private final UncheckedObjectMapper objectMapper;

    public HttpCustomClient() {
        this.objectMapper = new UncheckedObjectMapper();
        client = HttpClient.newHttpClient();
    }

    public Map<String, String> getResponse(String URL, Map<String, String> parameters) throws URISyntaxException, InterruptedException, ExecutionException {
        return client.sendAsync(post(URL, parameters), HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenApply(objectMapper::map).get();
    }

    private HttpRequest post(String URL, Map<String, String> parameters) throws URISyntaxException {
        return HttpRequest.newBuilder()
                .uri(new URI(URL))
                .POST(getParamsUrlEncoded(parameters))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .header("Accept", "application/json")
                .build();
    }

    private HttpRequest.BodyPublisher getParamsUrlEncoded(Map<String, String> parameters) {
        String urlEncoded = parameters.entrySet()
                .stream()
                .map(e -> e.getKey().concat("=").concat(URLEncoder.encode(e.getValue(), StandardCharsets.UTF_8)))
                .collect(Collectors.joining("&"));
        return HttpRequest.BodyPublishers.ofString(urlEncoded);
    }
}
