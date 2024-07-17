package karate.reports.model;

import java.util.List;

public class Root {
    public double efficiency;
    public int totalTime;
    public int threads;
    public String resultDate;
    public String env;
    public String version;
    public int scenariosfailed;
    public List<FeatureSummary> featureSummary;
    public int featuresPassed;
    public int featuresFailed;
    public int featuresSkipped;
    public int scenariosPassed;
    public int elapsedTime;
}