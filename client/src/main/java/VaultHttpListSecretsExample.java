import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;

public class VaultHttpListSecretsExample {

    public static void main(String[] args) throws Exception {
        final HttpClient httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .connectTimeout(Duration.ofSeconds(20))
                .build();

        final String token = Files.readString(Paths.get("C:\\foo\\vault\\agent\\token_file"));
        final HttpRequest listRequest = java.net.http.HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8200/v1/secret/data/esb"))
                .header("X-Vault-Token", token)
                .header("Accept", "application/json")
                .GET()
                .build();

        final HttpResponse<String> listResponse = httpClient.send(listRequest, HttpResponse.BodyHandlers.ofString());
        final int listResponseCode = listResponse.statusCode();

        System.out.println("List Response code: " + listResponseCode);
        System.out.println("List Response: " + listResponse.body());
    }

}
