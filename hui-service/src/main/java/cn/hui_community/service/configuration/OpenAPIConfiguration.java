package cn.hui_community.service.configuration;

import cn.hui_community.service.configuration.security.authentication.password.SysUserPasswordAuthenticationFilter;
import cn.hui_community.service.configuration.security.authentication.token.RefreshTokenAuthenticationFilter;
import cn.hui_community.service.configuration.security.authentication.wechat.UserWechatMiniAuthenticationFilter;
import cn.hui_community.service.model.dto.response.TokenResponse;
import io.swagger.v3.core.converter.ModelConverters;
import io.swagger.v3.core.converter.ResolvedSchema;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.parameters.RequestBody;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import org.apache.commons.lang3.tuple.Pair;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@OpenAPIDefinition(
        info = @Info(title = "Hui-Community Doc", version = "0.0.1"),
        servers = {
                @Server(description = "Test env", url = "http://139.155.2.12:8080"),
                @Server(description = "Development env", url = "http://localhost:8080")
        },
        security = {
                @SecurityRequirement(name = "bearerAuth") // 全局定义需要的安全认证
        }

//        ,
//        externalDocs =
//        @ExternalDocumentation(
//                description = "Project Description",
//                url = "https://github.com/MetaTrustLabs/insight-backend/README.md")
)
@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT"
)
@Configuration
public class OpenAPIConfiguration {

    @Bean
    public OpenApiCustomizer customOpenApiCustomizer() {
        ResolvedSchema responseSchema = ModelConverters.getInstance().readAllAsResolvedSchema(TokenResponse.class);
        return openApi -> {
            List.of(Pair.of("/sys-api/login", SysUserPasswordAuthenticationFilter.Request.class),
                    Pair.of("/user-api/login", UserWechatMiniAuthenticationFilter.Request.class),
                    Pair.of("/refresh-token", RefreshTokenAuthenticationFilter.Request.class)
            ).forEach(pair -> {
                ResolvedSchema requestSchema = ModelConverters.getInstance().readAllAsResolvedSchema(pair.getRight());
                ApiResponse successResponse = new ApiResponse()
                        .content(new Content()
                                .addMediaType("application/json", new MediaType().schema(responseSchema.schema)));
                PathItem pathItem = new PathItem()
                        .post(new Operation()
                                .requestBody(new RequestBody()
                                        .content(new Content().addMediaType("application/json",
                                                new MediaType().schema(requestSchema.schema))))  // 设置请求体
                                .responses(new ApiResponses()  // 定义响应
                                        .addApiResponse("200", successResponse)
                                )
                        );
                openApi.path(pair.getLeft(), pathItem);
            });

        };
    }
}
