package karate.reports.model;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SummaryGenerator {

    public static void main(String[] args) throws Exception {
        final Path jsonPath = Paths.get("C:\\foo\\vault\\client\\src\\main\\resources\\karate-summary-json.txt");
        final Path htmlTemplatePath = Paths.get("C:\\foo\\vault\\client\\src\\main\\resources\\karate-summary.html");
        final String json = new String(Files.readAllBytes(jsonPath));
        final ObjectMapper mapper = new ObjectMapper();

        String template = new String(Files.readAllBytes(htmlTemplatePath));

        try {
            // Parse the entire JSON string into a JsonNode tree
            final JsonNode rootNode = mapper.readTree(json);
            final Root root = mapper.treeToValue(rootNode, Root.class);

            template = setSucceeded(template, root.featuresPassed);
            template = setFailed(template, root.featuresFailed);

            final StringBuilder tbody = new StringBuilder();

            for (final FeatureSummary fs: root.featureSummary) {
                tbody.append("<tr>");
                tbody.append("<td><a href=\".server.staging.resources.integTest.features.");
                tbody.append(fs.packageQualifiedName);
                tbody.append(".html\">");
                tbody.append(fs.packageQualifiedName);
                tbody.append("</a></td>");
                tbody.append("<td>").append(fs.name).append("</td>");
                tbody.append("<td>").append(fs.passedCount).append("</td>");
                tbody.append("<td>").append(fs.failedCount).append("</td>");
                tbody.append("<td>").append(fs.scenarioCount).append("</td>");
                tbody.append("<td>").append(fs.durationMillis).append("</td>");
                tbody.append("</tr>");
            }
            template = setTableBody(template, tbody.toString());
            System.out.println(template);
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    private static String setSucceeded(final String template, final int succeeded) {
        final String replacement = "<div id=\"nav-pass\" class=\"bg-success\">" + succeeded + "</div>";
        return template.replace("<div id=\"nav-pass\" class=\"bg-success\">0</div>", replacement);
    }

    private static String setFailed(final String template, final int failed) {
        final String replacement = "<div id=\"nav-fail\" class=\"bg-danger\">" + failed + "</div>";
        return template.replace("<div id=\"nav-fail\" class=\"bg-danger\">0</div>", replacement);
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
