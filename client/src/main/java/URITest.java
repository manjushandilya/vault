import javax.net.ssl.SSLException;
import javax.net.ssl.SSLHandshakeException;
import java.net.ConnectException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class URITest {

    public static void main(String[] args) throws Exception {
       ping();
    }

    public static boolean ping() throws Exception {
        final URI uri = new URI("https://localhost:8201");
        final String connectExceptionMessage = "Error in ping for URI: " + uri;
        try {
            final HttpRequest request = HttpRequest.newBuilder()
                    .uri(uri)
                    //.headers(standardHeaders(fetchToken()))
                    .GET()
                    .build();

            final HttpResponse<String> response = client(uri).send(request, HttpResponse.BodyHandlers.ofString());
            final int responseCode = response.statusCode();
            if (responseCode != 200) {
                throw new Exception(connectExceptionMessage + ", received responseCode: " + responseCode);
            }
            return true;
        } catch (final ConnectException e) {
            throw new ConnectException(connectExceptionMessage + ", reason: " + e.getCause());
        } catch (final SSLException e) {
            throw new ConnectException(connectExceptionMessage + ", reason: " + e.getCause());
        }
    }

    private static HttpClient client(final URI uri) throws Exception {
        final HttpClient.Builder builder = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .connectTimeout(timeoutSeconds())
                .followRedirects(HttpClient.Redirect.NORMAL);
        return builder.build();
    }

    private static Duration timeoutSeconds() {
        final int timeout = 10;
        return Duration.ofSeconds(timeout > 0 ? timeout : 20);
    }

}
