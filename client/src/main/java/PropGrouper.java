import java.util.Properties;
import java.util.Set;

public class PropGrouper {

    private static final String VAULT_ADDR = "VAULT_ADDR";
    private static final String VAULT_TOKEN = "VAULT_TOKEN";

    private static final String PATH = "PATH";
    private static final String KEYRING = "KEYRING";

    private static Set<String> propertyKeys () {
        return Set.of(PATH, KEYRING, VAULT_ADDR, VAULT_TOKEN);
    }

    public static void main(String[] args) {
        Properties props = new Properties();
        props.put(PATH, "esb");
        props.put(KEYRING, "esb");
        props.put(VAULT_ADDR, "esb");
        props.put(VAULT_TOKEN, "esb");

        System.out.println(propertyKeys().equals(props.keySet()));
    }

}