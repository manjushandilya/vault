import java.nio.file.Path;
import java.nio.file.Paths;

public class RegexValidator {

    public static final String FILE = "^\\$file\\{(.*)\\}$";
    public static final String ENV_VAR = "^\\$env\\{(.*)\\}$";

    public static void main(String[] args) throws Exception{
        final String[] subjects = {
                "$file{/vault/token}",
                "$file{C:\\foo\\vault\\server\\token}",
                "$file/vault/token}",
                "$file{/vault/token",
                "$env{/vault/token}",
                "$env{C:\\foo\\vault\\server\\token}",
                "$env/vault/token}",
                "$env{/vault/token",
        };

        for (final String subject: subjects) {
            System.out.println(subject + ": " + subject.matches(FILE));
            System.out.println(subject + ": " + subject.matches(FILE));
            System.out.println(subject + ": " + subject.matches(ENV_VAR));
        }

        System.out.println(Paths.get("C:\\foo\\vault\\server\\token"));
        System.out.println(Paths.get("C:\\foo1\\vault\\server\\token"));
        System.out.println(Path.of("subject.matches(FILE))").toFile().getCanonicalPath());
    }

}
