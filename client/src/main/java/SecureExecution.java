interface Executable {
    void prod() throws Exception;
    void dev() throws Exception;
    void other() throws Exception;
    void none() throws Exception;
}

class Executor {
    static void execute(String profile, Executable e) throws Exception {
        if (profile == null || profile.trim().isBlank()) {
            e.none();
            return;
        }
        switch (profile.toLowerCase()) {
            case "prod" -> e.prod();
            case "dev" -> e.dev();
            default -> e.other();
        }
    }
}

public class SecureExecution {

    private static final String PROFILE = "PROD";

    public static void main(String[] args) {
        try {
            Executable executable = new Executable() {
                @Override
                public void prod() throws Exception {
                    // do something for prod profile
                }

                @Override
                public void dev() throws Exception {

                }

                @Override
                public void other() throws Exception {

                }

                @Override
                public void none() throws Exception {

                }
            };
            Executor.execute(System.getenv("security.profile"), executable);
        } catch (Exception e) {
            // handle error
        }
    }

}
