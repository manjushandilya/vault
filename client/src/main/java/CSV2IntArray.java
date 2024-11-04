import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.IntFunction;
import java.util.stream.Stream;

public class CSV2IntArray {

    public static void main(String[] args) {
        System.out.println(Arrays.toString(convertToIntegerArray(null)));
        System.out.println(Arrays.toString(convertToIntegerArray("")));
        System.out.println(Arrays.toString(convertToIntegerArray(",")));
        System.out.println(Arrays.toString(convertToIntegerArray("1")));
        System.out.println(Arrays.toString(convertToIntegerArray("1,2")));
        System.out.println(Arrays.toString(convertToIntegerArray("1,2,3")));
        System.out.println(Arrays.toString(convertToIntegerArray("1,2,3,")));
        System.out.println(Arrays.toString(convertToIntegerArray("1,2,3 ")));
        System.out.println(Arrays.toString(convertToIntegerArray("1, 2,3 ")));
        System.out.println(Arrays.toString(convertToIntegerArray("1,, 2,3 ")));
    }

    public static Integer[] convertToIntegerArray(final String csvString) {
        final List<Integer> result = new ArrayList<>();
        if (csvString != null && !csvString.trim().isBlank()) {
            final String[] splits = csvString.split(",");
            for (final String split : splits) {
                try {
                    final int i = Integer.parseInt(split.trim());
                    result.add(i);
                } catch (final Exception e) {
                    // Do nothing!
                }
            }
        }
        return result.isEmpty() ? null : result.toArray(Integer[]::new);
    }

}
