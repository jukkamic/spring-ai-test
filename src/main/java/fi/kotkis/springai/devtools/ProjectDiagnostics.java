package fi.kotkis.springai.devtools;

import java.util.Arrays;
import java.util.List;

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

    @McpTool(description = "Returns the internal Spring Bean names for debugging context")
    public List<String> listInternalBeans() {
        return Arrays.asList(context.getBeanDefinitionNames());
    }

}
