import com.google.gson.Gson;
import model.login.response.Root;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;

public class VaultHttpAppRoleExample {

    public static final Gson GSON = new Gson();

    public static void main(String[] args) throws Exception {
        final String roleId = Files.readString(Paths.get("C:\\foo\\vault\\agent\\role_id_file"));
        System.out.println("Role Id: " + roleId);

        final String secretId = Files.readString(Paths.get("C:\\foo\\vault\\agent\\secret_id_file"));
        System.out.println("Secret Id: " + secretId);

        final model.login.request.Root root = new model.login.request.Root(roleId, secretId);

        final String json = GSON.toJson(root);

        final HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8200/v1/auth/approle/login"))
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        final HttpClient httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .connectTimeout(Duration.ofSeconds(20))
                .build();

        final HttpResponse<String> httpResponse = httpClient.send(httpRequest, BodyHandlers.ofString());
        final int httpResponseCode = httpResponse.statusCode();

        System.out.println("Response code: " + httpResponseCode);

        if (httpResponseCode == 200) {
            final Root loginResponse = GSON.fromJson(httpResponse.body(), Root.class);
            System.out.println("Response: " + loginResponse);
            //System.out.println("Client token: " + loginResponse.auth().client_token());
        }
    }

}
