package ru.ryauzov.restexample.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;
import ru.ryauzov.restexample.wsdl.GetConvertedXmlRequest;
import ru.ryauzov.restexample.wsdl.GetConvertedXmlResponse;

public class SoapConverterClient extends WebServiceGatewaySupport {

    public GetConvertedXmlResponse getConvertedXml(String xmlText) {
        GetConvertedXmlRequest request = new GetConvertedXmlRequest();
        request.setSourceXmlText(xmlText);

        GetConvertedXmlResponse response = (GetConvertedXmlResponse) getWebServiceTemplate()
                .marshalSendAndReceive("http://localhost:8080/service/convert", request,
                        new SoapActionCallback(
                                "http://www.test-soap.com/GetConvertedXmlRequest"));

        return response;
    }
}
