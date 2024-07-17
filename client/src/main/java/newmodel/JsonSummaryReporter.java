package newmodel;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonSummaryReporter {

    public static List<File> listFiles(final String dir) throws Exception {
        return Stream.of(new File(dir).listFiles())
                .sorted()
                .filter(file -> !file.isDirectory())
                .filter(file -> file.getName().toLowerCase().endsWith(".karate-json.txt"))
                .collect(Collectors.toList());
    }

    public static void main(String[] args) throws Exception {
        final Path htmlTemplatePath = Paths.get("C:\\foo\\vault\\client\\src\\main\\resources\\karate-summary.html");
        //final String jsonPath = "C:\\github\\websocketfailure\\esb\\tests\\server\\staging\\reports\\karate-reports";
        final String jsonPath = "C:\\temp\\KarateTestReports\\tests\\staging\\worker\\karate-reports";
        final List<File> jsonFiles = listFiles(jsonPath);
        //System.out.println(jsonFiles);
        final ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        int totalPassed = 0, totalFailed = 0;
        final StringBuilder tbody = new StringBuilder();
        for (final File jsonFile : jsonFiles) {
            //System.out.println("Processing file: " + jsonFile);
            final String json = new String(Files.readAllBytes(jsonFile.toPath()));
            final JsonNode rootNode = mapper.readTree(json);
            final Root root = mapper.treeToValue(rootNode, Root.class);
            final String qName = root.packageQualifiedName;
            final String feature = qName.substring(qName.lastIndexOf(".") + 1, qName.length());
            final StringBuilder link = new StringBuilder();
            link.append("<a href=\"").append(qName).append(".html\">");
            link.append(feature).append("</a>");

            final Duration duration = Duration.ofMillis(root.durationMillis);
            //System.out.println(link + ", " + root.name + ", " + root.passedCount + ", " + root.failedCount +
                    //", " + root.scenarioResults.size() + ", " + humanReadableFormat(duration));
            tbody.append("<tr><td>");
            tbody.append(link);
            tbody.append("</td>");
            tbody.append("<td>").append(root.name).append("</td>");
            tbody.append("<td>").append(root.passedCount).append("</td>");
            tbody.append("<td>").append(root.failedCount).append("</td>");
            tbody.append("<td>").append(root.scenarioResults.size()).append("</td>");
            tbody.append("<td>").append(humanReadableFormat(duration)).append("</td>");
            tbody.append("</tr>");

            totalPassed += root.passedCount;
            totalFailed += root.failedCount;
        }
        System.out.println("Total passed: " + totalPassed);
        System.out.println("Total failed: " + totalFailed);

        String template = new String(Files.readAllBytes(htmlTemplatePath));
        template = setSucceeded(template, totalPassed);
        template = setFailed(template, totalFailed);
        template = setTableBody(template, tbody.toString());

        System.out.println(template);
    }

    private static String humanReadableFormat(final Duration duration) {
        return duration.toString()
                .substring(2)
                .replaceAll("(\\d[HMS])(?!$)", "$1 ")
                .toLowerCase();
    }

    private static String setSucceeded(final String template, final int succeeded) {
        final String replacement = "<div id=\"nav-pass\" class=\"bg-success\">" + succeeded + "</div>";
        return template.replaceAll("<div id=\"nav-pass\" class=\"bg-success\">.*</div>", replacement);
    }

    private static String setFailed(final String template, final int failed) {
        final String replacement = "<div id=\"nav-fail\" class=\"bg-danger\">" + failed + "</div>";
        return template.replaceAll("<div id=\"nav-fail\" class=\"bg-danger\">.*</div>", replacement);
    }

    private static String setTableBody(final String template, final String tbody) {
        final int startIndex = template.indexOf("<tbody>");
        final int endIndex = template.indexOf("</tbody>");
        final StringBuilder ret = new StringBuilder();
        ret.append(template.substring(0, startIndex + 7));
        ret.append(tbody);
        ret.append(template.substring(endIndex, template.length()));
        return ret.toString();
    }

}
