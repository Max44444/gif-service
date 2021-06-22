package com.bsahomework.bsagiphy.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;

@Component
@Log4j2
public class HttpGiphyApiClient implements GiphyApiClient {

    @Value("${api.giphy-url}")
    private String giphyApiUrl;

    @Value("${api.giphy-key}")
    private String giphyApiKey;

    private final HttpClient client;

    @Autowired
    public HttpGiphyApiClient(HttpClient client) {
        this.client = client;
    }

    @Override
    public Optional<File> getGifFile(String query) {
        try {
            var request = buildGetRequest(createURI(query));
            var responseBodyHandler = HttpResponse.BodyHandlers.ofString();

            var response = client.send(request, responseBodyHandler);

            return parseResponseToGifFile(response);
        } catch (IOException | InterruptedException | UnirestException e) {
            return Optional.empty();
        }
    }

    public Optional<File> parseResponseToGifFile(HttpResponse<String> response) throws IOException, UnirestException {
        JsonNode jsonBody = (new ObjectMapper()).readTree(response.body());
        String id = jsonBody.at("/data/id").asText();
        String downloadUrl = jsonBody.at("/data/images/original/url").asText();

        var gifFile = new File(String.format("%s.gif", id));
        FileUtils.copyInputStreamToFile(
                Unirest.get(downloadUrl)
                        .asBinary()
                        .getBody(),
                gifFile
        );

        return gifFile.length() != 0 ? Optional.of(gifFile) : Optional.empty();
    }

    private HttpRequest buildGetRequest(URI uri) {
        return HttpRequest
                .newBuilder(uri)
                .GET()
                .build();
    }

    private URI createURI(String query) {
        var stringBuilder = new StringBuilder(giphyApiUrl);
        stringBuilder.append("?api_key=").append(giphyApiKey);

        if (query != null) {
            stringBuilder.append("&query=").append(query);
        }

        return URI.create(stringBuilder.toString());
    }
}
