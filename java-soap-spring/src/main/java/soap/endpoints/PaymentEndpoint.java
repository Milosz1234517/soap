package soap.endpoints;

import edu.pwr.soap.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import soap.repositories.PaymentRepository;

@org.springframework.ws.server.endpoint.annotation.Endpoint
public class PaymentEndpoint {
    private static final String NAMESPACE_URI = "http://pwr.edu/soap";

    private final PaymentRepository paymentRepository;

    @Autowired
    public PaymentEndpoint(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getManyPaymentsRequest")
    @ResponsePayload
    public GetManyPaymentsResponse getPayments(@RequestPayload GetManyPaymentsRequest request) {
        GetManyPaymentsResponse response = new GetManyPaymentsResponse();
        response.getPayment().addAll(paymentRepository.findPayment());

        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getOnePaymentRequest")
    @ResponsePayload
    public GetOnePaymentResponse getPayment(@RequestPayload GetOnePaymentRequest request) {
        GetOnePaymentResponse response = new GetOnePaymentResponse();
        response.setPayment(paymentRepository.findPayment(request.getId()));

        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "savePaymentRequest")
    @ResponsePayload
    public SavePaymentResponse savePayment(@RequestPayload SavePaymentRequest request) {
        SavePaymentResponse response = new SavePaymentResponse();
        response.setMessage(paymentRepository.savePayment(request.getPaymentDate(), request.getPaymentAmount(), request.getPersonId(), request.getEventId(), request.getInstallmentNumber()));

        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "deletePaymentRequest")
    @ResponsePayload
    public DeletePaymentResponse deletePayment(@RequestPayload DeletePaymentRequest request) {
        DeletePaymentResponse response = new DeletePaymentResponse();
        response.setMessage(paymentRepository.deletePayment(request.getId()));

        return response;
    }
}
