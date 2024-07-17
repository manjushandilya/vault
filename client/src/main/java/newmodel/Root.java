package newmodel;

import java.util.List;

public class Root{
    public int failedCount;
    public String prefixedPath;
    public String packageQualifiedName;
    public int loopIndex;
    public String relativePath;
    public List<ScenarioResult> scenarioResults;
    public int callDepth;
    public String name;
    public String description;
    public String resultDate;
    public long durationMillis;
    public int passedCount;
}