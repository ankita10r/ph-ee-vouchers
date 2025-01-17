package org.mifos.pheevouchermanagementsystem.camel.routes;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import static org.mifos.pheevouchermanagementsystem.camel.config.CamelProperties.ENDPOINT;
import static org.mifos.pheevouchermanagementsystem.camel.config.CamelProperties.HOST;


@Component
public class ExternalApiCallRoute extends RouteBuilder {
    @Override
    public void configure() {
        from("direct:external-api-calling")
                .id("external-api-call")
                .log(LoggingLevel.DEBUG,"######## API CALL -> Calling an external api")
                .process(exchange -> {
                    // remove the trailing "/" from endpoint
                    String endpoint = exchange.getProperty(ENDPOINT, String.class);
                    if (endpoint.startsWith("/")) { exchange.setProperty(ENDPOINT, endpoint.substring(1)); }
                })
                .log(LoggingLevel.DEBUG,"Host: ${exchangeProperty." + HOST + "}")
                .log(LoggingLevel.DEBUG,"Endpoint: ${exchangeProperty." + ENDPOINT + "}")
                .log(LoggingLevel.DEBUG,"Headers: ${headers}")
                .log(LoggingLevel.DEBUG,"Request Body: ${body}")
                .toD("${exchangeProperty." + HOST + "}/${exchangeProperty." + ENDPOINT + "}" +
                        "?bridgeEndpoint=true" + "&throwExceptionOnFailure=false")
                .log(LoggingLevel.DEBUG,"Response body: ${body}");
    }
}
