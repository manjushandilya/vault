import java.util.Optional;

class VaultConnection {

    String alias;

    public VaultConnection(String alias) {
        this.alias = alias;
    }

    String alias() {
        return alias;
    }
}


public class LogicTester {

    private static Optional<VaultConnection> getPrimaryConnection() {
        final Optional<VaultConnection> optional = Optional.of(new VaultConnection("hcVault"));
        return optional;
    }

    public static boolean isVaultConfigChange(Optional<VaultConnection> opt, boolean dsProxyConfigured, String dsAlias) {
        //System.out.println("optional.isPresent(): " + optional.isPresent());
        final boolean upsertedVault = opt.isPresent() && (!dsProxyConfigured || (opt.isPresent() && !opt.get().alias().equals(dsAlias)));
        final boolean removedVault = opt.isEmpty() && dsProxyConfigured;
        if (upsertedVault || removedVault) {
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        Optional<VaultConnection> optional = getPrimaryConnection();
        System.out.println(isVaultConfigChange(optional, true, "abc"));
        System.out.println(isVaultConfigChange(optional, true, "hcVault"));
        System.out.println(isVaultConfigChange(optional, false, "abc"));
        System.out.println(isVaultConfigChange(optional, false, "hcVault"));

        optional = Optional.empty();
        System.out.println(isVaultConfigChange(optional, true, "abc"));
        System.out.println(isVaultConfigChange(optional, true, "hcVault"));
        System.out.println(isVaultConfigChange(optional, false, "abc"));
        System.out.println(isVaultConfigChange(optional, false, "hcVault"));
    }

}
