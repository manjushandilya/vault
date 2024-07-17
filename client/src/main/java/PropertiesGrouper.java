import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class VaultConnectionDTO {
    String alias;
    String type;
    String enabled;
    String primary;
    String secretsCacheEnabled;
    String secretsCacheMaxEntries;
    String secretsCacheTtlSeconds;
    String secretsCacheTtiSeconds;
    Map<String, String> properties;

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("VaultConnectionObject{");
        sb.append("alias='").append(alias).append('\'');
        sb.append(", type='").append(type).append('\'');
        sb.append(", enabled='").append(enabled).append('\'');
        sb.append(", primary='").append(primary).append('\'');
        sb.append(", secretsCacheEnabled='").append(secretsCacheEnabled).append('\'');
        sb.append(", secretsCacheMaxEntries='").append(secretsCacheMaxEntries).append('\'');
        sb.append(", secretsCacheTtlSeconds='").append(secretsCacheTtlSeconds).append('\'');
        sb.append(", secretsCacheTtiSeconds='").append(secretsCacheTtiSeconds).append('\'');
        sb.append(", properties=").append(properties);
        sb.append('}');
        return sb.toString();
    }
}

public class PropertiesGrouper {

    private static final String VAULT_PREFIX = "vault.";
    private static final Pattern VAULT_PATTERN = Pattern.compile("^vault\\.(.*?)\\.(.*)$");

    private static Collection<VaultConnectionDTO> transformToDTOS(final Properties properties) throws Exception {
        final Map<String, VaultConnectionDTO> ret = new TreeMap<>();
        for (final String key : properties.stringPropertyNames()) {
            if (key.startsWith(VAULT_PREFIX)) {
                final Matcher matcher = VAULT_PATTERN.matcher(key);
                if (matcher.find() && matcher.groupCount() == 2) {
                    final String alias = matcher.group(1);
                    if (!ret.containsKey(alias)) {
                        final VaultConnectionDTO value = new VaultConnectionDTO();
                        value.alias = alias;
                        ret.put(alias, value);
                    }
                    final VaultConnectionDTO vco = ret.get(alias);
                    final String field = matcher.group(2);
                    final String value = properties.getProperty(key);
                    switch (field) {
                        case "type" -> vco.type = value;
                        case "enabled" -> vco.enabled = value;
                        case "primary" -> vco.primary = value;
                        case "secretsCache.enabled" -> vco.secretsCacheEnabled = value;
                        case "secretsCache.maxEntries" -> vco.secretsCacheMaxEntries = value;
                        case "secretsCache.ttlSeconds" -> vco.secretsCacheTtlSeconds = value;
                        case "secretsCache.ttiSeconds" -> vco.secretsCacheTtiSeconds = value;
                        default -> {
                            if (vco.properties == null) {
                                vco.properties = new HashMap<>();
                            }
                            final String genericPropName = field.substring(field.indexOf(".") + 1);
                            vco.properties.put(genericPropName, value);
                        }
                    }
                } else {
                    throw new Exception("Invalid syntax: " + key);
                }
            }
        }
        return ret.values();
    }

    public static void main(String[] args) throws Exception {
        Properties props = new Properties();
        props.put("vault.hashiCorpVault.type", "com.softwareag.is.vault.spi.hcimpl.HashiCorpVaultConnection");
        props.put("vault.hashiCorpVault.enabled", "true");
        props.put("vault.hashiCorpVault.primary", "true");
        props.put("vault.hashiCorpVault.secretsCache.enabled", "true");
        props.put("vault.hashiCorpVault.secretsCache.maxEntries", "1000");
        props.put("vault.hashiCorpVault.secretsCache.ttlSeconds", "300");
        props.put("vault.hashiCorpVault.secretsCache.ttiSeconds", "300");
        props.put("vault.hashiCorpVault.properties.baseURI", "http://localhost:8200");
        props.put("vault.hashiCorpVault.tokenSourceIdentifier", "/tmp/token_file");
        props.put("vault.hashiCorpVault.property.tokenSourceType", "file");
        props.put("vault.localFileVault.type", "com.softwareag.is.vault.spi.localfileimpl.LocalFileVaultConnection");
        props.put("vault.localFileVault.enabled", "true");
        props.put("vault.localFileVault.primary", "false");
        props.put("vault.noopVault.type", "com.softwareag.is.vault.spi.noop.NoopVaultConnection");
        props.put("vault.abc", "com.softwareag.is.vault.spi.noop.NoopVaultConnection");
        System.out.println(transformToDTOS(props));
    }
}
