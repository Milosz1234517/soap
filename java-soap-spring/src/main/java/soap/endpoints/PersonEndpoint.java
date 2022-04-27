package soap.endpoints;

import edu.pwr.soap.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import soap.repositories.PersonRepository;

@Endpoint
public class PersonEndpoint {
    private static final String NAMESPACE_URI = "http://pwr.edu/soap";

    private final PersonRepository personRepository;

    @Autowired
    public PersonEndpoint(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getManyPersonRequest")
    @ResponsePayload
    public GetManyPersonResponse getPersons(@RequestPayload GetManyPersonRequest request) {
        GetManyPersonResponse response = new GetManyPersonResponse();
        response.getPerson().addAll(personRepository.findPersons());

        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getOnePersonRequest")
    @ResponsePayload
    public GetOnePersonResponse getPerson(@RequestPayload GetOnePersonRequest request) {
        GetOnePersonResponse response = new GetOnePersonResponse();
        response.setPerson(personRepository.findPerson(request.getId()));

        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "savePersonRequest")
    @ResponsePayload
    public SavePersonResponse savePerson(@RequestPayload SavePersonRequest request) {
        SavePersonResponse response = new SavePersonResponse();
        response.setMessage(personRepository.savePersons(request.getName(), request.getSurname()));

        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "deletePersonRequest")
    @ResponsePayload
    public DeletePersonResponse deletePerson(@RequestPayload DeletePersonRequest request) {
        DeletePersonResponse response = new DeletePersonResponse();
        response.setMessage(personRepository.deletePerson(request.getId()));

        return response;
    }
}
