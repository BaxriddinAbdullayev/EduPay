package uz.alifservice.config.swagger;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Debt Control", // Optional: Qo'shimcha ma'lumot berish uchun
                version = "1.0", // OpenAPI versiyasi noto'g'ri emas, faqat ma'lumot sifatida
                description = "MY Debt Control application"
        ),
        servers = {
                @Server(url = "http://localhost:8082", description = "Local Server"),
                @Server(url = "http://52.87.181.142:8080", description = "{Production} Server")
        },
        security = @SecurityRequirement(name = "bearerAuth") // Apply globally
)
@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT", // Optional: Defines the format (JWT)
        in = SecuritySchemeIn.HEADER,
        paramName = "Authorization"
)
@ConfigurationProperties(prefix = "swagger")
public class SwaggerConfig {

    @Bean
    public List<GroupedOpenApi> userApi() {
        record ApiGroup(String name, String path) {
        }

        List<ApiGroup> apiGroups = Arrays.asList(
                new ApiGroup("User Controller", "/api/v1/users/**"),
                new ApiGroup("Role Controller", "/api/v1/roles/**"),
                new ApiGroup("Auth Controller", "/api/v1/auth/**"),
                new ApiGroup("Student Controller", "/api/v1/students/**"),
                new ApiGroup("Transaction Controller", "/api/v1/transactions/**")
        );

        return apiGroups.stream()
                .map(group -> GroupedOpenApi.builder()
                        .group(group.name())
                        .packagesToScan("uz.alifservice.controller")
                        .pathsToMatch(group.path())
                        .build())
                .toList();
    }

    // SECOND METHOD
//    private List<ApiGroup> groups;
//
//    public record ApiGroup(String name, String packageName, List<String> paths) {}
//
//    public void setGroups(List<ApiGroup> groups) {
//        this.groups = groups;
//    }
//
//    @Bean
//    public List<GroupedOpenApi> groupedOpenApis() {
//        return groups.stream()
//                .map(group -> GroupedOpenApi.builder()
//                        .group(group.name())
//                        .packagesToScan(group.packageName())
//                        .pathsToMatch(group.paths().toArray(new String[0]))
//                        .build())
//                .toList();
//    }
}
