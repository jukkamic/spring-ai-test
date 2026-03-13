## VSCode

### Java compiler settings

Copy this into .vscode/settings.json

It gives the vmarg to Java that we're using dev profile.

```json
{
    "java.configuration.updateBuildConfiguration": "automatic",
    "java.import.maven.enabled": true
}
```

You may need to open Command Palette ( **Cmd + Shift + P** ) and run Run: "Java: Clean Java Language Server Workspace".


### Cline MCP settings

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
        "-jar",
        "/ABSOLUTE/PATH/TO/target/restservice-0.0.1-SNAPSHOT.jar"
      ]
    }
  }
}
```

### Build -- and we're talking!

```bash
mvn clean install -Pdev
```

Tell Cline: "Ask java-construction-site about it's health"

**No need to run anything!** Cline finds the .jar in the directory configured in the mcpServers JSON configuration. 