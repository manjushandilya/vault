enum CryptoAlgorithm {AES, DES, VAULT}

public class Alog {

    public static void main(String[] args) {
        String[] algos = {"AES", "DES", "VAULT", "INVALID"};
        for (String algo: algos) {
            CryptoAlgorithm cryptoAlgorithm = CryptoAlgorithm.valueOf(algo);
            System.out.println(cryptoAlgorithm);
        }
    }

}
