# Spring MCP scaffolding in a construction site

We've got Spring AI to setup MCP server that Cline (or probably any) extension can access **even when not running**.

It can give Cline insights into code or the database, or anything. 

This is an MCP within the project we are working on.

## VSCode settings

### Java compiler settings

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

### Cline MCP settings

Change **ABSOLUTE_PATH** (in both target/classes and target/dependency).

```json  
{
  "mcpServers": {
    "java-construction-site": {
      "command": "java",
      "args": [
        "-Dspring.profiles.active=dev",
        "-Dspring.main.banner-mode=off",
        "-Dspring.main.web-application-type=none",
        "-Dspring.ai.mcp.server.stdio=true",
        "-Dlogging.level.root=OFF",
        "-Dlogging.pattern.console=",
        "-Dspring.ai.mcp.server.stdio.log-to-stderr=true",
        "-cp",
        "C:/<ABSOLUTE_PATH>/target/classes;C:/<ABSOLUTE_PATH>/target/dependency/*",
        "fi.kotkis.springai.RestServiceApplication"
      ]
    }
  }
}
```

# Build -- and we're talking!

```bash
mvn compile
mvn dependency:copy-dependencies
```

**No need to run anything!** 

Tell Cline: "Ask the construction site about it's health"

Cline finds the classes in the directory configured in the mcpServers JSON configuration. 

In the future you won't need to copy dependencies unless of course you run *clean* or they change.
