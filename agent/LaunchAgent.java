import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

public class LaunchAgent {
    public static void main(String[] args) {
        try {
            // Path to the .cmd file (adjust as necessary)
            String cmdFilePath = "start.cmd"; // Adjust this path

            // Command to run the .cmd file
            ProcessBuilder processBuilder = new ProcessBuilder("cmd.exe", "/c", cmdFilePath);
            processBuilder.directory(new File(".")); // Optional: set the working directory
            processBuilder.redirectErrorStream(true);

            // Start the process
            Process process = processBuilder.start();

            // Capture and print the output of the process
            StreamGobbler outputGobbler = new StreamGobbler(process.getInputStream(), System.out::println);
            Executors.newSingleThreadExecutor().submit(outputGobbler);

            // Wait for the process to complete
            int exitCode = process.waitFor();
            System.out.println("Process exited with code: " + exitCode);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class StreamGobbler implements Runnable {
    private InputStream inputStream;
    private Consumer<String> consumer;

    public StreamGobbler(InputStream inputStream, Consumer<String> consumer) {
        this.inputStream = inputStream;
        this.consumer = consumer;
    }

    @Override
    public void run() {
        new BufferedReader(new InputStreamReader(inputStream)).lines().forEach(consumer);
    }
}
