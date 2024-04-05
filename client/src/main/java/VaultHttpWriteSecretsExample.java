import com.google.gson.Gson;
import model.MapWrapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class VaultHttpWriteSecretsExample {

    public static final Gson GSON = new Gson();

    public static void main(String[] args) throws Exception {
        final String token = Files.readString(Paths.get("C:\\foo\\vault\\agent\\token_file"));
        System.out.println("Token: " + token);

        final Map<String, String> data = new HashMap<>();
        data.put("username", "Administrator");
        data.put("password", "secret");

        final MapWrapper mapWrapper = new MapWrapper(data);

        final String json = GSON.toJson(mapWrapper);
        System.out.println("Request: " + json);

        final HttpRequest httpRequest = java.net.http.HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8200/v1/secret/data/esb"))
                .header("X-Vault-Token", token)
                .header("Accept", "application/json")
                .header("Content-type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        final HttpClient httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .connectTimeout(Duration.ofSeconds(20))
                .build();

        final HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        final int httpResponseCode = httpResponse.statusCode();

        System.out.println("Response code: " + httpResponseCode);
        System.out.println("Response: " + httpResponse.body());
    }

}
