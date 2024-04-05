import com.google.gson.Gson;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.Base64;

public class VaultHttpEncryptSecretsExample {

    public static final Gson GSON = new Gson();

    public static void main(String[] args) throws Exception {
        final String token = Files.readString(Paths.get("C:\\foo\\vault\\agent\\token_file"));
        System.out.println("Token: " + token);

        final String originalData = "A quick fox jumped over a lazy dog";
        System.out.println("Original data: " + originalData);

        final String plainText = Base64.getEncoder().encodeToString(originalData.getBytes());
        System.out.println("Encoded data: " + plainText);

        final model.encrypt.request.Root payload = new model.encrypt.request.Root(plainText);
        final String json = GSON.toJson(payload);

        System.out.println("JSON: " + json);

        final HttpRequest encryptHttpRequest = java.net.http.HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8200/v1/transit/encrypt/esb"))
                .header("X-Vault-Token", token)
                .header("Accept", "application/json")
                .header("Content-type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        final HttpClient httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .connectTimeout(Duration.ofSeconds(20))
                .build();

        final HttpResponse<String> encryptHttpResponse = httpClient.send(encryptHttpRequest, HttpResponse.BodyHandlers.ofString());
        final int httpResponseCode = encryptHttpResponse.statusCode();

        System.out.println("Response code: " + httpResponseCode);
        final String body = encryptHttpResponse.body();
        System.out.println("Response: " + body);
        if (httpResponseCode == 200) {
            final model.encrypt.response.Root root = GSON.fromJson(body, model.encrypt.response.Root.class);
            final String cipherText = root.data().ciphertext();
            final Path path = Paths.get("encrypted_content");
            Files.write(path, cipherText.getBytes());
        }

    }

}
