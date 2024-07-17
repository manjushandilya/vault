public class EnvVarRead {

    public static void main(String[] args) {
        final String envVar = System.getenv("VAULT_ADDR");
        System.out.println("VAULT_ADDR: " + envVar);
    }

}
