package com.bsahomework.bsagiphy.util;

import com.bsahomework.bsagiphy.exception.DownloadFileException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import lombok.NonNull;
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
import java.nio.file.Path;

@Component
public class HttpGiphyApiClient implements GiphyApiClient {

    @Value("${api.giphy-url}")
    private String giphyApiUrl;

    @Value("${api.giphy-key}")
    private String giphyApiKey;

    @Value("${fs-db.cache-directory}")
    private String filepath;

    private final HttpClient client;

    @Autowired
    public HttpGiphyApiClient(HttpClient client) {
        this.client = client;
    }

    @Override
    public void loadGifToFileByQuery(@NonNull String query) {
        try {
            var request = buildGetRequest(createURI(query));
            var responseBodyHandler = HttpResponse.BodyHandlers.ofString();
            var responseBody = client.send(request, responseBodyHandler).body();
            parseResponseToGifFile(responseBody, filepath, query);
        } catch (IOException | UnirestException e) {
            throw new DownloadFileException(query, e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new DownloadFileException(query, e);
        }
    }

    private void parseResponseToGifFile(String response, String filepath, String query) throws IOException, UnirestException {
        JsonNode jsonBody = (new ObjectMapper()).readTree(response);
        String id = jsonBody.at("/data/id").asText();
        String downloadUrl = jsonBody.at("/data/images/original/url").asText();

        var fileDirectoryName = query.isBlank() ? jsonBody.at("slug").asText() : query;
        var path = Path.of(filepath, fileDirectoryName, id).toString() + ".gif";
        downloadFileFromUrl(path, downloadUrl);
    }

    private URI createURI(@NonNull String query) {
        var stringBuilder = new StringBuilder(giphyApiUrl);
        stringBuilder.append("?api_key=").append(giphyApiKey);

        if (!query.isBlank()) {
            stringBuilder.append("&tag=").append(query);
        }

        return URI.create(stringBuilder.toString());
    }

    private HttpRequest buildGetRequest(URI uri) {
        return HttpRequest
                .newBuilder(uri)
                .GET()
                .build();
    }

    private void downloadFileFromUrl(String filepath, String downloadUrl) throws UnirestException, IOException {
        var gifFile = new File(filepath);
        FileUtils.copyInputStreamToFile(
                Unirest.get(downloadUrl)
                        .asBinary()
                        .getBody(),
                gifFile
        );
    }
}
