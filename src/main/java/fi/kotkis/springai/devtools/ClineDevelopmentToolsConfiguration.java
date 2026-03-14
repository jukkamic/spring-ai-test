package fi.kotkis.springai.devtools;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;

import fi.kotkis.springai.devtools.service.DatabaseInspectorService;

@Configuration
@Profile("dev") // Only loads when spring.profiles.active=dev
public class ClineDevelopmentToolsConfiguration {

    @Bean
    public DatabaseInspectorService databaseInspectorService(JdbcTemplate jdbcTemplate) {
        return new DatabaseInspectorService(jdbcTemplate);
    }
}