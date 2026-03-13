package fi.kotkis.springai.devtools;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springaicommunity.mcp.annotation.McpTool;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j // Lombok generates: private static final org.slf4j.Logger log = ...
@Component
@Profile("dev")
public class DevHealthTools {

    @McpTool(description = "Check project health")
    public String checkHealth() {
        return "Construction site is active!";
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
            return "Error walking filesystem: " + e.getMessage();
        }

        return report.isEmpty() ? "All packages look professional!" : report.toString();
    }
}