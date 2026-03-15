package fi.kotkis.springai.devtools;

import org.springaicommunity.mcp.annotation.McpTool;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("dev")
public class McpHealthTools {

    @McpTool(description = "Check project health")
    public String checkHealth() {
        return "Construction site is active! Nice!";
    }

    @McpTool(description = "Refresh the project by shutting down the server so Cline is forced to restart it with new code")
    public String rebootServer() {
        new Thread(() -> {
            try {
                Thread.sleep(500);
            } catch (Exception e) {
            }
            System.exit(0);
        }).start();
        return "Shutting down... Please wait for Cline to auto-restart me.";
    }

}