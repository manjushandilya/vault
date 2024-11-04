import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

public final class LaunchVault {
    public static void main(final String[] args) {
        try {
            // Path to the anchor file
            final String anchorFilePath = "anchorfile";

            // Command to run the Vault server in development mode
            final ProcessBuilder processBuilder = new ProcessBuilder("vault", "server",
                    "-dev", "-dev-listen-address=localhost:8200");
            processBuilder.redirectErrorStream(true);

            // Start the process
            final Process vaultProcess = processBuilder.start();
            final Path path = Paths.get(anchorFilePath);
            final byte[] bytes = String.valueOf(vaultProcess.pid()).getBytes();
            Files.write(path, bytes, StandardOpenOption.CREATE_NEW);

            // Capture and print the output of the Vault process
            final StreamGobbler outputGobbler = new StreamGobbler(vaultProcess.getInputStream(), System.out::println);
            Executors.newSingleThreadExecutor().submit(outputGobbler);

            // Monitor the anchor file
            final File anchorFile = new File(anchorFilePath);
            while (anchorFile.exists()) {
                // Sleep for a short period before checking again
                Thread.sleep(1000);
            }

            // Anchor file deleted, terminate the Vault process
            if (vaultProcess != null) {
                System.out.println("Anchor file deleted, stopping Vault...");
                vaultProcess.destroy();
                vaultProcess.waitFor();
                System.out.println("Vault process terminated due to anchor file deletion.");
            }

        } catch (final Exception e) {
            e.printStackTrace();
        }
    }
}

class StreamGobbler implements Runnable {
    private InputStream inputStream;
    private Consumer<String> consumer;

    public StreamGobbler(final InputStream inputStream, final Consumer<String> consumer) {
        this.inputStream = inputStream;
        this.consumer = consumer;
    }

    @Override
    public void run() {
        new BufferedReader(new InputStreamReader(inputStream)).lines().forEach(consumer);
    }
}
