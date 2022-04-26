package windows;

import daoObjects.EventDao;
import daoObjects.InstallmentDao;
import daoObjects.PaymentDao;
import daoObjects.PersonDao;
import tableObjects.Event;
import tableObjects.Installment;
import tableObjects.Payment;
import tableObjects.Person;
import windows.insert.InsertEventWindow;
import windows.insert.InsertInstallmentWindow;
import windows.insert.InsertPaymentWindow;
import windows.insert.InsertPersonWindow;
import windows.print.InstallmentPrintWindow;
import windows.print.MainPrintWindow;
import windows.print.PaymentPrintWindow;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class MainWindow extends JFrame {
    private JButton printDataButton;
    private JPanel panel1;
    private JButton fileDataButton;
    private JButton manualDataButton;
    private JButton timeButton;
    private JButton monitButton;
    private JComboBox<String> comboBox1;
    private JLabel currDate;

    private JDialog insertManual;
    private JDialog print;

    private PersonDao personDAO;
    private EventDao eventDAO;
    private PaymentDao paymentDAO;
    private InstallmentDao installmentDAO;
    private Connection connection;

    private Date date;

    public void connectToDB() {
        try {
            String portNumber = "5432";
            String databaseName = "javadatabase";
            String loginName = "postgres";
            String password = "bazadanych";

            connection = DriverManager.getConnection("jdbc:postgresql://localhost:" + portNumber + "/" + databaseName, loginName, password);
            connection.setAutoCommit(false);
            personDAO = new PersonDao(connection);
            eventDAO = new EventDao(connection);
            paymentDAO = new PaymentDao(connection);
            installmentDAO = new InstallmentDao(connection);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void setDate(LocalDate date) {
        this.date = Date.valueOf(date);
    }

    public MainWindow() {

        connectToDB();

        LocalDateTime now = LocalDateTime.now();
        String d = now.getYear() + "-" + now.getMonthValue() + "-" + now.getDayOfMonth();
        date = Date.valueOf(d);
        currDate.setText(date.toString());

        printDataButton.addActionListener(e -> {
            chooseTable();
            print.setVisible(true);
        });
        fileDataButton.addActionListener(e -> insertFileCSV());
        manualDataButton.addActionListener(e -> {
            if (insertManual != null)
                insertManual.setVisible(true);
        });
        monitButton.addActionListener(e -> getMonits());
        timeButton.addActionListener(e -> {
            LocalDate date = LocalDate.parse(JOptionPane.showInputDialog("Insert date"));
            if (date != null) {
                setDate(date);
                currDate.setText(date.toString());
            } else {
                JOptionPane.showMessageDialog(null, "Wrong date format!", "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        });

        comboBox1.addItemListener(e -> chooseTable());

        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        comboBoxInit();
        chooseTable();

        setContentPane(panel1);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }

    private void comboBoxInit() {
        comboBox1.addItem("Person");
        comboBox1.addItem("Payment");
        comboBox1.addItem("Installment");
        comboBox1.addItem("Event");
    }

    private void chooseTable() {
        switch ((String) Objects.requireNonNull(comboBox1.getSelectedItem())) {
            case "Person":
                insertManual = new InsertPersonWindow(this, personDAO);
                print = new MainPrintWindow(this, "Person", personDAO, eventDAO);
                break;
            case "Payment":
                insertManual = new InsertPaymentWindow(this, personDAO, eventDAO, installmentDAO, paymentDAO);
                print = new PaymentPrintWindow(this, personDAO, eventDAO, paymentDAO);
                break;
            case "Installment":
                insertManual = new InsertInstallmentWindow(this, eventDAO, installmentDAO);
                print = new InstallmentPrintWindow(this, personDAO, installmentDAO, eventDAO);
                break;
            case "Event":
                insertManual = new InsertEventWindow(this, eventDAO);
                print = new MainPrintWindow(this, "Event", personDAO, eventDAO);
                break;
        }
    }

    public void insertFileCSV() {
        try {
            JFileChooser jFileChooser = new JFileChooser();
            jFileChooser.setFileFilter(new FileNameExtensionFilter("CSV File", "csv"));
            jFileChooser.showOpenDialog(panel1);
            BufferedReader br = new BufferedReader(new FileReader(jFileChooser.getSelectedFile()));
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                switch ((String) Objects.requireNonNull(comboBox1.getSelectedItem())) {
                    case "Person":
                        personDAO.save(new Person(values[0], values[1]));
                        break;
                    case "Event":
                        eventDAO.save(new Event(values[0], values[1], Date.valueOf(values[2])));
                        break;
                    case "Installment":
                        installmentDAO.save(new Installment(Long.parseLong(values[0]), Long.parseLong(values[1]), Date.valueOf(values[2]), values[3]));
                        break;
                    case "Payment":
                        paymentDAO.save(new Payment(Date.valueOf(values[0]), values[1], Long.parseLong(values[2]), Long.parseLong(values[3]), Long.parseLong(values[4])));
                        break;
                }
            }
        } catch (Exception ignored) {

        }
    }

    private void getMonits() {
        try {
            FileWriter file = new FileWriter("monits.txt", true);
            FileWriter file1 = new FileWriter("escalated_monits.txt", true);

            BufferedWriter out = new BufferedWriter(file);
            BufferedWriter out1 = new BufferedWriter(file1);

            out.write("Log date: [" + date.toString() + "]........................................\n");
            out1.write("Log date: [" + date.toString() + "]........................................\n");

            ArrayList<Person> persons = personDAO.getAll();
            ArrayList<Event> events = eventDAO.getAll();

            for (Person p : persons) {
                for (Event e : events) {
                    ArrayList<Installment> installments = installmentDAO.getAllForWhoNotPay(p, e);
                    for (Installment i : installments) {

                        long diffInMillies = i.getDue_Date().getTime() - date.getTime();
                        long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);

                        if (diff < 7 && diff >= 0) {

                            out.write(p.getName() + " " + p.getSurname() + " left " + diff + " days to pay installment:  " + i.getInstallment_Number() + " cost: " + i.getInstallment_Amount() + " for event: " + e.getName() + "\n\n");

                        } else if (diff < 0) {

                            out1.write(p.getName() + " " + p.getSurname() + " delay in payment " + -1 * diff + " days to pay installment: " + i.getInstallment_Number() + " cost: " + i.getInstallment_Amount() + " for event: " + e.getName() + "\n\n");

                        }
//
                    }
                }
            }

            out.close();
            out1.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new MainWindow();
    }
}
