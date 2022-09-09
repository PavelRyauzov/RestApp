package ru.ryauzov.restexample.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import ru.ryauzov.restexample.client.SoapConverterClient;

@Configuration
public class SoapConverterClientConfig {
    Environment environment;

    @Autowired
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @Bean
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath("ru.ryauzov.restexample.wsdl");
        return marshaller;
    }

    @Bean
    public SoapConverterClient soapConverterClient(Jaxb2Marshaller marshaller) {
        SoapConverterClient client = new SoapConverterClient();
        client.setDefaultUri(environment.getProperty("soap.uri"));
        client.setMarshaller(marshaller);
        client.setUnmarshaller(marshaller);
        return client;
    }
}
