package soap.endpoints;

import edu.pwr.soap.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import soap.repositories.EventRepository;

@org.springframework.ws.server.endpoint.annotation.Endpoint
public class EventEndpoint {
	private static final String NAMESPACE_URI = "http://pwr.edu/soap";

	private final EventRepository eventRepository;

	@Autowired
	public EventEndpoint(EventRepository eventRepository) {
		this.eventRepository = eventRepository;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getManyEventRequest")
	@ResponsePayload
	public GetManyEventResponse getEvents(@RequestPayload GetManyEventRequest request) {
		GetManyEventResponse response = new GetManyEventResponse();
		response.getEvent().addAll(eventRepository.findEvents());

		return response;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getOneEventRequest")
	@ResponsePayload
	public GetOneEventResponse getEvent(@RequestPayload GetOneEventRequest request) {
		GetOneEventResponse response = new GetOneEventResponse();
		response.setEvent(eventRepository.findEvent(request.getId()));

		return response;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "saveEventRequest")
	@ResponsePayload
	public SaveEventResponse saveEvent(@RequestPayload SaveEventRequest request) {
		SaveEventResponse response = new SaveEventResponse();
		response.setMessage(eventRepository.saveEvent(request.getName(), request.getPlace(), request.getDate()));

		return response;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "deleteEventRequest")
	@ResponsePayload
	public DeleteEventResponse deleteEvent(@RequestPayload DeleteEventRequest request) {
		DeleteEventResponse response = new DeleteEventResponse();
		response.setMessage(eventRepository.deleteEvent(request.getId()));

		return response;
	}
}
