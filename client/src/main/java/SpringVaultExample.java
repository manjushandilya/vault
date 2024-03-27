import org.springframework.vault.authentication.AppRoleAuthentication;
import org.springframework.vault.authentication.AppRoleAuthenticationOptions;
import org.springframework.vault.authentication.ClientAuthentication;
import org.springframework.vault.authentication.TokenAuthentication;
import org.springframework.vault.client.VaultEndpoint;
import org.springframework.vault.core.VaultTemplate;
import org.springframework.vault.support.VaultToken;
import org.springframework.vault.support.Versioned;
import org.springframework.vault.support.Versioned.Metadata;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class SpringVaultExample {

    public static ClientAuthentication getClientAuth(final String auth) throws Exception {
        switch (auth) {
            case "approle":
                final String roleId = Files.readString(Paths.get("C:\\foo\\vault\\agent\\role_id_file"));
                System.out.println("Role Id: " + roleId);
                final String secretId = Files.readString(Paths.get("C:\\foo\\vault\\agent\\secret_id_file"));
                System.out.println("Secret Id: " + secretId);
                final AppRoleAuthenticationOptions options = AppRoleAuthenticationOptions.builder()
                        .roleId(AppRoleAuthenticationOptions.RoleId.provided("44e5258c-70c7-7dd3-1e4e-10e8cee5ebcd"))
                        .secretId(AppRoleAuthenticationOptions.SecretId.wrapped(VaultToken.of("bf470647-23cc-70c5-2b17-c0c503d79bbc")))
                        .build();
                final RestOperations restOperations = new RestTemplate();
                return new AppRoleAuthentication(options, restOperations);
            case "token":
                final String value = Files.readString(Paths.get("C:\\foo\\vault\\agent\\token_file"));
                System.out.println("Token: " + value);
                return new TokenAuthentication(value);
            default:
                throw new IllegalArgumentException("Unsupported authentication method: " + auth);
        }
    }

    public static void main(String[] args) throws Exception {
        VaultEndpoint vaultEndpoint = new VaultEndpoint();

        vaultEndpoint.setHost("127.0.0.1");
        vaultEndpoint.setPort(8200);
        vaultEndpoint.setScheme("http");

        // Authenticate
        ClientAuthentication authentication = getClientAuth("approle");
        VaultTemplate vaultTemplate = new VaultTemplate(vaultEndpoint, authentication);

        // Write a secret
        Map<String, String> data = new HashMap<>();
        data.put("password", "Hashi123");

        Metadata createResponse = vaultTemplate
                .opsForVersionedKeyValue("secret")
                .put("my-secret-password", data);

        System.out.println("Secret written successfully.");

        // Read a secret
        Versioned<Map<String, Object>> readResponse = vaultTemplate
                .opsForVersionedKeyValue("secret")
                .get("my-secret-password");

        String password = "";
        if (readResponse != null && readResponse.hasData()) {
            password = (String) readResponse.getData().get("password");
        }

        if (!password.equals("Hashi123")) {
            throw new Exception("Unexpected password");
        }

        System.out.println("Access granted!");
    }

}
