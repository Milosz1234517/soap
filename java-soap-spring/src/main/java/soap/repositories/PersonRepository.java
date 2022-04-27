package soap.repositories;

import edu.pwr.soap.PersonServer;
import org.springframework.stereotype.Component;
import soap.other.Connector;
import soap.other.daoObjects.PersonDao;
import soap.other.tableObjects.Person;

import javax.annotation.PostConstruct;
import java.util.ArrayList;

@Component
public class PersonRepository {

    private PersonDao personDao;

    @PostConstruct
    public void initData() {
        personDao = new PersonDao(Connector.connectToDB());
    }

    public PersonServer findPerson(long id) {
        PersonServer personServer = new PersonServer();
        Person person = personDao.get(id);
        personServer.setPersonId(person.getPerson_Id());
        personServer.setName(person.getName());
        personServer.setSurname(person.getSurname());

        return personServer;
    }

    public ArrayList<PersonServer> findPersons() {
        ArrayList<PersonServer> personServers = new ArrayList<>();
        for (Person person : personDao.getAll()) {
            PersonServer personServer = new PersonServer();
            personServer.setPersonId(person.getPerson_Id());
            personServer.setName(person.getName());
            personServer.setSurname(person.getSurname());
            personServers.add(personServer);
        }
        return personServers;
    }

    public String savePersons(String name, String surname) {
        if (personDao.save(new Person(name, surname))) {
            return "Saved Succesfully";
        } else {
            return "Saving Failed";
        }

    }

    public String deletePerson(long id) {

        if (personDao.delete(new Person(id, null, null))) {
            return "Deleted Succesfully";
        } else {
            return "Deleting Failed";
        }

    }
}