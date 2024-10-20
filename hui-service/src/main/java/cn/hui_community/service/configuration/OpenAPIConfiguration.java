package cn.hui_community.service.configuration;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(title = "Hui-Community Doc", version = "0.0.1"),
        servers = {
//                @Server(description = "Test env", url = "https://vuu5A:Phuquee4up@insight.meta001.net/"),
                @Server(description = "Development env", url = "http://localhost:8080")
        }
//        ,
//        externalDocs =
//        @ExternalDocumentation(
//                description = "Project Description",
//                url = "https://github.com/MetaTrustLabs/insight-backend/README.md")
)
@Configuration
public class OpenAPIConfiguration {
}
