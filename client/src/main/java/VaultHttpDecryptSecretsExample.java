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

public class VaultHttpDecryptSecretsExample {

    public static final Gson GSON = new Gson();

    public static void main(String[] args) throws Exception {
        final String token = Files.readString(Paths.get("C:\\foo\\vault\\agent\\token_file"));
        System.out.println("Token: " + token);

        final Path path = Paths.get("encrypted_content");
        final String cipherText = Files.readString(path);
        System.out.println("Cipher text: " + cipherText);

        final model.decrypt.request.Root root = new model.decrypt.request.Root(cipherText);
        final String json = GSON.toJson(root);
        System.out.println("JSON: " + json);

        final HttpRequest decryptHttpRequest = java.net.http.HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8200/v1/transit/decrypt/esb"))
                .header("X-Vault-Token", token)
                .header("Accept", "application/json")
                .header("Content-type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        final HttpClient httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .connectTimeout(Duration.ofSeconds(20))
                .build();

        final HttpResponse<String> httpResponse = httpClient.send(decryptHttpRequest, HttpResponse.BodyHandlers.ofString());
        final int httpResponseCode = httpResponse.statusCode();

        System.out.println("Response code: " + httpResponseCode);
        System.out.println("Response: " + httpResponse.body());
        if (httpResponseCode == 200) {
            final model.decrypt.response.Root decryptResponse = GSON.fromJson(httpResponse.body(), model.decrypt.response.Root.class);
            final String plaintext = decryptResponse.data().plaintext();
            final byte[] decodedData = Base64.getDecoder().decode(plaintext.getBytes());
            final String originalData = new String(decodedData);
            System.out.println("Original data: " + originalData);
        }
    }

}
