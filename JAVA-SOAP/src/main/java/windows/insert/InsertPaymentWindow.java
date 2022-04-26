package windows.insert;

import daoObjects.EventDao;
import daoObjects.InstallmentDao;
import daoObjects.PaymentDao;
import daoObjects.PersonDao;
import tableObjects.Event;
import tableObjects.Installment;
import tableObjects.Payment;
import tableObjects.Person;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class InsertPaymentWindow extends JDialog{

    private JList<Event> list1;
    private JPanel panel1;
    private JList<Installment> list2;
    private JList<Person> list3;
    private JButton OKButton;
    private JButton cancelButton;

    public InsertPaymentWindow(Frame parent, PersonDao personDAO, EventDao eventDAO, InstallmentDao installmentDAO, PaymentDao paymentDAO) {
        super(parent, true);
        getEvents(eventDAO);
        OKButton.addActionListener(e -> {
            try {
                paymentDAO.save(new Payment(null, list2.getSelectedValue().getInstallment_Amount(),
                        list3.getSelectedValue().getPerson_Id(), list2.getSelectedValue().getEvent_Id(),
                        list2.getSelectedValue().getInstallment_Number()));
            }catch (Exception ex){
                JOptionPane.showMessageDialog(null, "Bad data!", "ERROR", JOptionPane.ERROR_MESSAGE);
            }
            dispose();
        });
        cancelButton.addActionListener(e -> dispose());

        list1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                getInstalments(installmentDAO);
            }
        });

        list2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                getPersons(personDAO);
            }
        });
        setContentPane(panel1);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
    }

    private void getEvents(EventDao eventDAO){
        ArrayList<Event> events = eventDAO.getAll();
        DefaultListModel<Event> eventDefaultListModel = new DefaultListModel<>();
        for (Event e : events) {
            eventDefaultListModel.addElement(e);
        }
        list1.setModel(eventDefaultListModel);
    }

    private void getInstalments(InstallmentDao installmentDAO){
        ArrayList<Installment> installments = installmentDAO.getAllForSpecifiedEvent(list1.getSelectedValue());
        DefaultListModel<Installment> installmentDefaultListModel = new DefaultListModel<>();
        for (Installment i : installments) {
            installmentDefaultListModel.addElement(i);
        }
        list2.setModel(installmentDefaultListModel);
    }

    private void getPersons(PersonDao personDAO){
        ArrayList<Person> people = personDAO.getAllWhoNotPay(list2.getSelectedValue());
        DefaultListModel<Person> personDefaultListModel = new DefaultListModel<>();
        for (Person p : people) {
            personDefaultListModel.addElement(p);
        }
        list3.setModel(personDefaultListModel);
    }
}
