package edu.stanford.cs108.bunnyworld;

/**
 * Created by gongzibo on 12/2/17.
 */

public class Script {
    private String trigger;
    private String action;
    private String target;

    Script(String script) {
        String[] scriptArray = script.split(" ");
        trigger = scriptArray[0];
        action = scriptArray[1];
        target = scriptArray[2];
    }

    public String getAction() {
        return action;
    }

    public String getTarget() {
        return target;
    }
}
