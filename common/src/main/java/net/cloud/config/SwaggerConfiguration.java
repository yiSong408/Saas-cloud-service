package net.cloud.config;

import lombok.Data;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import springfox.documentation.builders.*;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.schema.ScalarType;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;
import java.util.List;

@Component
@Data
@EnableOpenApi
public class SwaggerConfiguration {
    @Bean
    public Docket webApiDocket() {
        return new Docket(DocumentationType.OAS_30)
                .groupName("User api document")
                .pathMapping("/")
                // define if open swagger
                .enable(true)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("net.cloud"))
                .paths(PathSelectors.ant("/api/**"))
                .build()
                // swagger 3.0 only
                .globalRequestParameters(globalRequestParameter())
                .globalResponses(HttpMethod.GET,getGlobalResponseMsg())
                .globalResponses(HttpMethod.POST,getGlobalResponseMsg());
    }

    @Bean
    public Docket adminApiDocket() {
        return new Docket(DocumentationType.OAS_30)
                .groupName("Admin api document")
                .pathMapping("/")
                .enable(true) // define if open swagger
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("net.cloud"))
                .paths(PathSelectors.ant("/admin/**"))
                .build();
    }

    private List<RequestParameter> globalRequestParameter(){
        List<RequestParameter> parameters = new ArrayList<>();
        parameters.add(new RequestParameterBuilder().name("token").description("Login token")
                .in(ParameterType.HEADER)
                .query(q->q.model(m->m.scalarModel(ScalarType.STRING)))
                .required(false)
                .build());
        return parameters;
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("Saas cloud service").description("Microservice document")
                .contact(new Contact("Rongyi", "http://www.ronghey.com", "songyi102788@gmail.com"))
                .version("v1.0").build();
    }

    // generate common response
    private List<Response> getGlobalResponseMsg(){
        List<Response> list = new ArrayList<>();
        list.add(new ResponseBuilder().code("4**").description("Request error, check code and message please.").build());
        return list;
    }
}
