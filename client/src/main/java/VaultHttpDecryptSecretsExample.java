import com.google.gson.Gson;
import model.DecryptRequest;
import model.DecryptResponse;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.Base64;

public class VaultHttpDecryptSecretsExample {

    public static final Gson GSON = new Gson();

    public static void main(String[] args) throws Exception {
        final String token = Files.readString(Paths.get("C:\\foo\\vault\\agent\\token_file"));
        System.out.println("Token: " + token);

        final String cipherText = "vault:v1:HPvczFd5T+LD9lGc4JlQMw50mykeyz3UpJnD1vynkCxYWHxjN6nV3DG+vo+8oWI64EmAVCk2DE3bb/lcECA=";
        System.out.println("Cipher text: " + cipherText);

        final DecryptRequest decryptRequest = new DecryptRequest(cipherText);
        final String json = GSON.toJson(decryptRequest);
        System.out.println("JSON: " + json);

        final HttpRequest httpRequest = java.net.http.HttpRequest.newBuilder()
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

        final HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        final int httpResponseCode = httpResponse.statusCode();

        System.out.println("Response code: " + httpResponseCode);
        System.out.println("Response: " + httpResponse.body());
        if (httpResponseCode == 200) {
            final DecryptResponse decryptResponse = GSON.fromJson(httpResponse.body(), DecryptResponse.class);
            final String plaintext = decryptResponse.data().plaintext();
            final byte[] decodedData = Base64.getDecoder().decode(plaintext.getBytes());
            final String originalData = new String(decodedData);
            System.out.println("Original data: " + originalData);
        }
    }

}
