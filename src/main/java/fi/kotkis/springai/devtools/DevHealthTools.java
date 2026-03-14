package fi.kotkis.springai.devtools;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springaicommunity.mcp.annotation.McpTool;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("dev")
public class DevHealthTools {


    private static final Logger log = LoggerFactory.getLogger(DevHealthTools.class);

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

    @McpTool(description = "Check if all Java files have package declarations")
    public String checkPackages() {
        log.info("Starting package health check..."); // This now goes to STDERR/File
        Path root = Paths.get("").toAbsolutePath();
        Path start = root.resolve("src/main/java");
        StringBuilder report = new StringBuilder();

        try (var stream = Files.walk(start)) {
            stream.filter(path -> path.toString().endsWith(".java"))
                    .forEach(path -> {
                        try {
                            String firstLine = Files.lines(path).findFirst().orElse("");
                            if (!firstLine.trim().startsWith("package ")) {
                                log.warn("Found missing package in: {}", path.getFileName());
                                report.append("Missing package: ").append(path.getFileName()).append("\n");
                            }
                        } catch (IOException e) {
                            log.error("Failed to read file: {}", path, e);
                        }
                    });
        } catch (IOException e) {
            log.error("Error walking filesystem: " + e.getMessage());
            return "Error walking filesystem: " + e.getMessage();
        }

        return report.isEmpty() ? "All packages look professional!" : report.toString();
    }
}