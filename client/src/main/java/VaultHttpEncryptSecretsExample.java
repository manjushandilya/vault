import com.google.gson.Gson;
import model.EncryptRequest;

import java.nio.file.Files;
import java.nio.file.Paths;
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

        final EncryptRequest encryptRequest = new EncryptRequest(plainText);
        final String json = GSON.toJson(encryptRequest);

        System.out.println("JSON: " + json);

//        final HttpRequest httpRequest = java.net.http.HttpRequest.newBuilder()
//                .uri(URI.create("http://localhost:8200/v1/transit/encrypt/esb"))
//                .header("X-Vault-Token", token)
//                .header("Accept", "application/json")
//                .header("Content-type", "application/json")
//                .POST(HttpRequest.BodyPublishers.ofString(json))
//                .build();
//
//        final HttpClient httpClient = HttpClient.newBuilder()
//                .version(HttpClient.Version.HTTP_1_1)
//                .connectTimeout(Duration.ofSeconds(20))
//                .build();
//
//        final HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
//        final int httpResponseCode = httpResponse.statusCode();
//
//        System.out.println("Response code: " + httpResponseCode);
//        System.out.println("Response: " + httpResponse.body());
    }

}
