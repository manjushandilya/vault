import static java.util.concurrent.TimeUnit.NANOSECONDS;

public class TracedOperation {

    public static <E> E perform(TracedExecutable<E> executable, String name, boolean debug) throws Exception {
        if (!debug) {
            return executable.execute();
        }
        final long startTime = System.nanoTime();
        final E returnValue = executable.execute();
        final long endTime = System.nanoTime();
        final long elapsedTime = endTime - startTime;
        System.out.println("TracedOperation " + name + " took: " + NANOSECONDS.toMillis(elapsedTime) + " ms");
        return returnValue;
    }

}
