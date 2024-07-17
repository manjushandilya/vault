import java.util.*;

public class MergeCollections {
    public static void main(String[] args) {
        // Creating two collections with some duplicate elements
        Collection<String> collection1 = new ArrayList<>(Arrays.asList("Apple", "Banana", "Cherry"));
        Collection<String> collection2 = new ArrayList<>(Arrays.asList("Banana", "Date", "Fig"));

        // Merging collections without duplicates
        Set<String> mergedSet = new HashSet<>(collection1);
        mergedSet.addAll(collection2);

        // Converting the set back to a list (if needed)
        List<String> mergedList = new ArrayList<>(mergedSet);

        // Displaying the merged collection
        System.out.println("Merged Collection without duplicates: " + mergedList);
    }
}
