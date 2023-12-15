package com.example.algamoney.api.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;
@ConfigurationProperties("algamoney")
@Getter
@Configuration
public class AlgamoneyApiProperty {

    @Setter
    private List<String> originsPermitidas = Arrays.asList("http://localhost:8000");

    private final Seguranca seguranca = new Seguranca();

    @Getter
    @Setter
    public static class Seguranca {

        private boolean enableHttps;

    }
}
