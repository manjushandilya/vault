package newmodel;

import java.util.List;

public class ScenarioResult{
    public int sectionIndex;
    public List<StepResult> stepResults;
    public int line;
    public String description;
    public double durationMillis;
    public boolean failed;
    public List<String> tags;
    public String executorName;
    public String name;
    public Object startTime;
    public String refId;
    public Object endTime;
    public int exampleIndex;
    public ExampleData exampleData;
}