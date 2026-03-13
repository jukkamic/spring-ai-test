package fi.kotkis.springai;

import java.util.Arrays;
import java.util.List;

import org.springaicommunity.mcp.annotation.McpTool;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
@Profile("dev") // Only loads when spring.profiles.active=dev
public class ClineDevelopmentTools {

    @Bean
    @McpTool(description = "Returns the internal Spring Bean names for debugging context")
    public List<String> listInternalBeans(ApplicationContext context) {
        return Arrays.asList(context.getBeanDefinitionNames());
    }

    @Bean
    @McpTool(description = "Get the current database schema overview")
    public String getDevSchema(JdbcTemplate jdbc) {
        // Your logic to help Cline understand the DB
        return jdbc.queryForList("SELECT table_name FROM information_schema.tables WHERE table_schema='PUBLIC'")
                .toString();
    }
}