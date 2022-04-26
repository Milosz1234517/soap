package windows.insert;

import daoObjects.PersonDao;
import tableObjects.Person;

import javax.swing.*;
import java.awt.*;

public class InsertPersonWindow extends JDialog{
    private JTextField textField1;
    private JPanel panel1;
    private JTextField textField2;
    private JButton OKButton;
    private JButton cancelButton;

    public InsertPersonWindow(Frame parent, PersonDao personDAO) {
        super(parent, true);
        OKButton.addActionListener(e -> {
            if(textField1.getText().length() > 0 && textField2.getText().length() > 0) {
                personDAO.save(new Person(textField1.getText(), textField2.getText()));
            }else{
                JOptionPane.showMessageDialog(null, "Bad data!", "ERROR", JOptionPane.ERROR_MESSAGE);
            }
            dispose();
        });
        cancelButton.addActionListener(e -> dispose());
        setContentPane(panel1);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
    }


}
