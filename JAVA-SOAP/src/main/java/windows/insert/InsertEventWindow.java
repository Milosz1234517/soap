package windows.insert;

import daoObjects.EventDao;
import tableObjects.Event;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.time.LocalDate;

public class InsertEventWindow extends JDialog{
    private JTextField textField1;
    private JPanel panel1;
    private JTextField textField2;
    private JButton OKButton;
    private JButton cancelButton;
    private JTextField textField3;

    public InsertEventWindow(Frame parent, EventDao eventDAO) {
        super(parent, true);
        OKButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LocalDate date = LocalDate.parse(textField3.getText());
                if(date != null && textField1.getText().length() > 0 && textField2.getText().length() > 0)
                    eventDAO.save(new Event(textField1.getText(), textField2.getText(), Date.valueOf(date)));
                else JOptionPane.showMessageDialog(null, "Nie wprowadzono poprawnych danych", "Blad", JOptionPane.ERROR_MESSAGE);
                dispose();
            }
        });
        cancelButton.addActionListener(e -> dispose());
        setContentPane(panel1);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
    }
}
