package soap.repositories;

import edu.pwr.soap.EventServer;
import org.springframework.stereotype.Component;
import soap.other.Connector;
import soap.other.daoObjects.EventDao;
import soap.other.tableObjects.Event;

import javax.annotation.PostConstruct;
import java.sql.Date;
import java.util.ArrayList;

@Component
public class EventRepository {

    private EventDao eventDao;

    @PostConstruct
    public void initData() {
        eventDao = new EventDao(Connector.connectToDB());
    }

    public EventServer findEvent(long id) {
        EventServer eventServer = new EventServer();
        Event event = eventDao.get(id);
        eventServer.setEventId(event.getEvent_Id());
        eventServer.setName(event.getName());
        eventServer.setPlace(event.getPlace());
        eventServer.setDate(event.getDate().toString());

        return eventServer;
    }

    public ArrayList<EventServer> findEvents() {
        ArrayList<EventServer> eventServers = new ArrayList<>();
        for (Event event : eventDao.getAll()) {
            EventServer eventServer = new EventServer();
            eventServer.setEventId(event.getEvent_Id());
            eventServer.setName(event.getName());
            eventServer.setPlace(event.getPlace());
            eventServer.setDate(event.getDate().toString());
            eventServers.add(eventServer);
        }
        return eventServers;
    }

    public String saveEvent(String name, String place, String date) {
        if (eventDao.save(new Event(name, place, Date.valueOf(date)))) {
            return "Saved Succesfully";
        } else {
            return "Saving Failed";
        }

    }

    public String deleteEvent(long id) {

        if (eventDao.delete(new Event(id, null, null, null))) {
            return "Deleted Succesfully";
        } else {
            return "Deleting Failed";
        }

    }
}
