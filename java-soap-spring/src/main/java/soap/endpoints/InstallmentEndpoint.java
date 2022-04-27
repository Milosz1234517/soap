package soap.endpoints;

import edu.pwr.soap.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import soap.repositories.InstallmentRepository;

@org.springframework.ws.server.endpoint.annotation.Endpoint
public class InstallmentEndpoint {
    private static final String NAMESPACE_URI = "http://pwr.edu/soap";

    private final InstallmentRepository installmentRepository;

    @Autowired
    public InstallmentEndpoint(InstallmentRepository installmentRepository) {
        this.installmentRepository = installmentRepository;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getManyInstallmentsRequest")
    @ResponsePayload
    public GetManyInstallmentsResponse getInstallments(@RequestPayload GetManyInstallmentsRequest request) {
        GetManyInstallmentsResponse response = new GetManyInstallmentsResponse();
        response.getInstallment().addAll(installmentRepository.findInstallments());

        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getOneInstallmentRequest")
    @ResponsePayload
    public GetOneInstallmentResponse getInstallment(@RequestPayload GetOneInstallmentRequest request) {
        GetOneInstallmentResponse response = new GetOneInstallmentResponse();
        response.setInstallment(installmentRepository.findInstallment(request.getId()));

        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "saveInstallmentRequest")
    @ResponsePayload
    public SaveInstallmentResponse saveEvent(@RequestPayload SaveInstallmentRequest request) {
        SaveInstallmentResponse response = new SaveInstallmentResponse();
        response.setMessage(installmentRepository.saveInstallment(request.getInstallmentAmount(), request.getEventId(), request.getDuedate(), request.getInstallmentNumber()));

        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "deleteInstallmentRequest")
    @ResponsePayload
    public DeleteInstallmentResponse deleteEvent(@RequestPayload DeleteInstallmentRequest request) {
        DeleteInstallmentResponse response = new DeleteInstallmentResponse();
        response.setMessage(installmentRepository.deleteInstallment(request.getId()));

        return response;
    }
}
