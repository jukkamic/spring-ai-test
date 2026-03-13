## VSCode

### Java compiler settings

Copy this into .vscode/settings.json

It give the vmarg to Java that we're using dev profile.

```json
{
    "java.configuration.updateBuildConfiguration": "automatic",
    "java.import.maven.enabled": true
}```

You may need to open Command Palette ( **Cmd + Shift + P** ) and run Run: "Java: Clean Java Language Server Workspace".
