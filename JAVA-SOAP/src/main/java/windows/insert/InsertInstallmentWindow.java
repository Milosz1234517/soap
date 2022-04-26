package windows.insert;

import daoObjects.EventDao;
import daoObjects.InstallmentDao;
import tableObjects.Event;
import tableObjects.Installment;

import javax.swing.*;
import java.awt.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;

public class InsertInstallmentWindow extends JDialog{
    private JList<Event> list1;
    private JPanel panel1;
    private JTextField textField1;
    private JTextField textField2;
    private JButton OKButton;
    private JButton cancelButton;

    private void getEvents(EventDao eventDAO){
        ArrayList<Event> events = eventDAO.getAll();
        DefaultListModel<Event> eventDefaultListModel = new DefaultListModel<>();
        for (Event e : events) {
            eventDefaultListModel.addElement(e);
        }
        list1.setModel(eventDefaultListModel);
    }

    public InsertInstallmentWindow(Frame parent, EventDao eventDAO, InstallmentDao installmentDAO) {
        super(parent, true);
        getEvents(eventDAO);
        OKButton.addActionListener(e -> {
            LocalDate date = LocalDate.parse(textField2.getText());
            if(date != null && textField1.getText().length() > 0 && textField2.getText().length() > 0)
                installmentDAO.save(new Installment(list1.getSelectedValue().getEvent_Id(), installmentDAO.getMaxInstalmentNumberForEvent(list1.getSelectedValue()), Date.valueOf(date), textField1.getText()));
            else JOptionPane.showMessageDialog(null, "Bad data!", "ERROR", JOptionPane.ERROR_MESSAGE);
            dispose();
        });
        cancelButton.addActionListener(e -> dispose());
        setContentPane(panel1);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
    }
}
