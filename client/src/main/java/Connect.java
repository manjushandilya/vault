import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class Connect {

    public static void main(String[] args) {
        try {
            final URI uri = new URI("http://localhost:8200/ui");
            final HttpRequest request = HttpRequest.newBuilder()
                    .uri(uri)
                    //.headers(standardHeaders(fetchToken()))
                    .GET()
                    .build();

            final HttpResponse<String> response = client().send(request, HttpResponse.BodyHandlers.ofString());
        } catch (final Exception e) {
            System.out.println(e);
        }
    }

    private static HttpClient client() {
        return HttpClient.newBuilder().version(HttpClient.Version.HTTP_1_1)
                .connectTimeout(Duration.ofSeconds(10L))
                .followRedirects(HttpClient.Redirect.NORMAL)
                .build();
    }

}
