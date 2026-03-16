# Build... and we're talking!

```bash
mvn compile
mvn dependency:copy-dependencies
mvn spring-boot:run
```

**Tell Cline:** "Use the tool to describe project's dependencies"

**No need to run anything!** 

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

This is an MCP within the project we are working on. For introspection.

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

In Cline panel's title bar click "MCP Servers" (small icon next to plus sign), select Configure tab and click Configure MCP Servers.


```json  
{
  "mcpServers": {
    "java-construction-site": {
      "disabled": false,
      "timeout": 60,
      "type": "sse",
      "url": "http://localhost:9090/sse"
    }
  }
}
```

## Run the application without the scaffolding that is the MCP server

```bash
mvn clean compile -Ptest
mvn dependency:copy-dependencies -Ptest
mvn spring-boot:run -Ptest
```

