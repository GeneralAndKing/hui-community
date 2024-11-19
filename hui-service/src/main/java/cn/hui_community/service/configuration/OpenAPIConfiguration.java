package cn.hui_community.service.configuration;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.parameters.Parameter;
import io.swagger.v3.oas.models.parameters.RequestBody;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

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

    @Bean
    public OpenApiCustomizer customOpenApiCustomizer() {
        return openApi -> {
            // 定义请求体中包含的参数
            Map<String, Schema> properties = new HashMap<>();
            properties.put("username", new Schema().type("string").description("用户名"));
            properties.put("password", new Schema().type("string").description("密码"));

            Schema schema = new Schema();
            schema.setType("object");
            schema.setProperties(properties);

            RequestBody requestBody = new RequestBody()
                    .content(new Content().addMediaType("application/json",
                            new MediaType().schema(schema))); // 定义请求体内容和结构

            PathItem pathItem = new PathItem()
                    .post(new Operation()
                            .requestBody(requestBody)  // 设置请求体
                            .responses(new ApiResponses()  // 定义响应
                                    .addApiResponse("200", new ApiResponse().description("成功登录"))
                                    .addApiResponse("400", new ApiResponse().description("用户名或密码错误"))
                                    .addApiResponse("500", new ApiResponse().description("服务器错误"))
                            )
                    );

            openApi.path("/sys/login", pathItem);
        };
    }
}
