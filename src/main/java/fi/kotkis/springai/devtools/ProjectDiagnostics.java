package fi.kotkis.springai.devtools;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springaicommunity.mcp.annotation.McpTool;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import fi.kotkis.springai.devtools.service.DatabaseInspectorService;

@Component
@Profile("dev")
public class ProjectDiagnostics {

    private final DatabaseInspectorService dbInspector;
    private final ApplicationContext context;

    public ProjectDiagnostics(DatabaseInspectorService dbInspector, ApplicationContext context) {
        this.dbInspector = dbInspector;
        this.context = context;
    }

    @McpTool(description = "Inspect the H2 database schema to verify tables, columns, and data types.")
    public String inspectDatabaseSchema() {
        return dbInspector.getSchemaDetails();
    }

    @McpTool(description = "Returns our custom Spring Bean names for the construction ledger")
    public List<String> listProjectBeans() {
        return Arrays.stream(context.getBeanDefinitionNames())
                // Filter for your package to hide the Spring infrastructure noise
                .filter(name -> name.contains("fi.kotkis") ||
                        !name.contains(".")) // Also show short names like "materialController"
                .sorted()
                .collect(Collectors.toList());
    }

}
