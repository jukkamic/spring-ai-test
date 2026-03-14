# MCP-Integrated Spring Boot Architecture
This repository serves as a technical exploration of Model Context Protocol (MCP) integration within a standard Spring Boot environment. The project demonstrates a "Self-Documenting Scaffolding" approach, where the development environment is built directly into the application runtime.

## Architectural Focus
The primary objective of this project is to bridge the gap between an LLM-based assistant and a running Java process. By implementing a dual-interface system—Standard I/O for MCP and a Servlet container for REST—the application allows the AI to:

**Self-Diagnose:** Inspect the Spring ApplicationContext to understand its own wiring.

**Verify Persistence:** Query the H2 file-based schema directly to confirm JPA migrations.

**Manage Lifecycle:** Control its own process state (restarts, compilations) via custom MCP tools.

By routing application logs to SYSTEM_ERR and the MCP data pipe to SYSTEM_OUT, the architecture maintains a stable communication channel that remains uncorrupted by framework noise or Tomcat initialization logs.

## Implementation Case Study

As a practical application of this architecture, the project includes a basic Construction Site Ledger. While secondary to the architectural goals, this module provides a concrete environment for testing schema evolution (such as material tracking and expiry dates) and verifying that the AI can reliably manage a persistent relational database through the established MCP tools.

# The same hype, written by human
We've got Spring Boot with a built-in MCP server (Spring AI) that Cline (or probably any) AI extension can access **even when not running**.

It can give Cline insights into code or the database, or anything. 

This is an MCP within the project we are working on.

# Configuration

## Java compiler settings

Copy this into **.vscode/settings.json**

Just so Java might work with Maven as it's supposed to.

```json
{
    "java.configuration.updateBuildConfiguration": "automatic",
    "java.import.maven.enabled": true,
    "java.autobuild.enabled": true,
}
```

You may need to open Command Palette ( **Cmd + Shift + P** ) and run Run: "Java: Clean Java Language Server Workspace".

## Cline MCP settings

Change **ABSOLUTE_PATH** (in both target/classes and target/dependency).

```json  
{
  "mcpServers": {
    "java-construction-site": {
      "disabled": false,
      "timeout": 60,
      "type": "stdio",
      "command": "java",
      "args": [
        "-Dspring.profiles.active=dev",
        "-Dspring.main.banner-mode=off",
        "-Dspring.ai.mcp.server.stdio=true",
        "-Dspring.ai.mcp.server.stdio.log-to-stderr=true",
        "-cp",
        "C:/ABSOLUTE_PATH/target/classes;C:/ABSOLUTE_PATH/target/dependency/*",
        "fi.kotkis.springai.RestServiceApplication"
      ],
      "autoApprove": []
    }
  }
}
```

## Spring properties
in src/main/resources/ create application-dev-properties. 

**NOTE:** Again, replace ABSOLUTE_PATH

```bash
# ==========================================================
# CONSTRUCTION SITE CONFIGURATION (MCP & DEV)
# ==========================================================

# MCP SERVER SETTINGS
# Enable the standard I/O transport that Cline/Claude Desktop expect
spring.ai.mcp.server.stdio=true
spring.ai.mcp.server.name=java-construction-site
spring.ai.mcp.server.version=1.0.0

# THE "SILENCE" SETTINGS (Critical for STDIO)
# Disable the Spring Boot ASCII Art banner
spring.main.banner-mode=off
# Don't start Tomcat/Netty; it's faster and avoids port conflicts
spring.main.web-application-type=servlet

# DATABASE (Scaffolding Source)

# Using H2 for the construction site makes it zero-config
# spring.datasource.url=jdbc:h2:mem:construction_db;DB_CLOSE_DELAY=-1

# Change from memory (mem:ledgerdb) to file (file:./data/ledgerdb)
spring.datasource.url=jdbc:h2:file:C:/ABSOLUTE_PATH/spring-ai-test/data/ledgerdb;DB_CLOSE_DELAY=-1

spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
spring.h2.console.enabled=true
```

# Build... and we're talking!

```bash
mvn compile
mvn dependency:copy-dependencies
```

**No need to run anything!** 

Tell Cline: "Ask the construction site about it's health"

Cline finds the classes in the directory configured in the mcpServers JSON configuration. 

In the future you won't need to copy dependencies unless of course you run *clean* or they change.

## Run the UI without the scaffolding

```bash
mvn clean compile -Ptest
mvn dependency:copy-dependencies -Ptest
mvn spring-boot:run -Ptest
```


# Troubleshooting

## Refreshing Cline's MCP after compile
When you run mvn compile there's little chance that Cline will notice anything changed. You will need to refresh Cline's MCP tool configuration.
