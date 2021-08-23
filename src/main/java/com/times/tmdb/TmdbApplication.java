package com.times.tmdb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@SpringBootApplication
@EnableSwagger2
public class TmdbApplication {

    public static void main(String[] args) {
        SpringApplication.run(TmdbApplication.class, args);
    }

    //    @Bean
//    public Docket swaggerConfiguration(){
//       return new Docket(DocumentationType.SWAGGER_2).select().paths(PathSelectors.ant("/*")).build().apiInfo(apiDetails());
//    }
    private ApiInfo apiDetails() {
        return new ApiInfo("TMDB API", "Sample Api for Training Project", "1.0", "Free to use", new springfox.documentation.service.Contact("Project Team 1", "https://aqueous-gorge-29959.herokuapp.com/swagger-ui.html", "aman.gupta2@timesinternet.in"), "API License", "https://aqueous-gorge-29959.herokuapp.com/swagger-ui.html", Collections.emptyList());
    }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.ant("/tmdb/**"))
                .build().apiInfo(apiDetails());
    }
}
