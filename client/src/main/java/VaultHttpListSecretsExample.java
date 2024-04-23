import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;

public class VaultHttpListSecretsExample {

    public static void main(String[] args) throws Exception {
        final String token = Files.readString(Paths.get("C:\\foo\\vault\\agent\\token_file"));
        System.out.println("Token: " + token);

        final HttpRequest request = java.net.http.HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8200/v1/secret/data/esb"))
                .header("X-Vault-Token", token)
                .header("Accept", "application/json")
                .GET()
                .build();

        final HttpClient client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .connectTimeout(Duration.ofSeconds(20))
                .build();

        final HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        final int responseCode = response.statusCode();

        System.out.println("List Response code: " + responseCode);
        System.out.println("List Response: " + response.body());
    }

}
