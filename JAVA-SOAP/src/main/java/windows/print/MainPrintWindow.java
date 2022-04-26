package windows.print;

import daoObjects.EventDao;
import daoObjects.PersonDao;
import tableObjects.Event;
import tableObjects.Person;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class MainPrintWindow extends JDialog{
    private JTable table1;
    private JPanel panel1;
    DefaultTableModel tableModel;

    private void personTableInit(PersonDao personDAO){
        tableModel = new DefaultTableModel();
        tableModel.addColumn("Name");
        tableModel.addColumn("Surname");
        ArrayList<Person> people = personDAO.getAll();
        for(Person p : people){
            tableModel.addRow(new Object[]{p.getName(), p.getSurname()});
        }
        table1.setModel(tableModel);
    }

    private void eventTableInit(EventDao eventDAO){
        tableModel = new DefaultTableModel();
        tableModel.addColumn("Name");
        tableModel.addColumn("Place");
        tableModel.addColumn("Date");
        ArrayList<Event> events = eventDAO.getAll();
        for(Event e : events){
            tableModel.addRow(new Object[]{e.getName(), e.getPlace(), e.getDate()});
        }
        table1.setModel(tableModel);
    }

    private void chooseTable(String tableName, PersonDao personDAO, EventDao eventDAO){
        switch (tableName){
            case "Person":
                personTableInit(personDAO);
                break;
            case "Event":
                eventTableInit(eventDAO);
                break;
        }
    }

    public MainPrintWindow(Frame parent, String tableName, PersonDao personDAO, EventDao eventDAO) {
        super(parent, true);
        setContentPane(panel1);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        chooseTable(tableName,personDAO, eventDAO);
    }
}
