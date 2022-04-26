package daoObjects;

import interfaces.IDao;
import tableObjects.Event;
import tableObjects.Payment;
import tableObjects.Person;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PaymentDao implements IDao<Payment> {

    private final Connection connection;

    public PaymentDao(Connection connection) {
        this.connection = connection;
    }

    private Payment getPayment(ResultSet rs) throws SQLException {
        return new Payment(rs.getLong("Payment ID"), rs.getDate("Payment Date"), rs.getString("Payment Amount"), rs.getLong("Person ID"), rs.getLong("Event ID"), rs.getLong("Installment Number"));
    }

    @Override
    public ArrayList<Payment> getAll() {
        ArrayList<Payment> paymentArrayList = new ArrayList<>();
        PreparedStatement preparedStatement;
        String selectQuery = "Select * from \"Payment\" ;";

        try{

            preparedStatement = connection.prepareStatement(selectQuery);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                paymentArrayList.add(getPayment(rs));
            }

            preparedStatement.close();
            rs.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return paymentArrayList;
    }

    @Override
    public void save(Payment payment) {

        PreparedStatement preparedStatement;
        String sql = "INSERT INTO \"Payment\" (\"Payment Date\", \"Payment Amount\", \"Person ID\", \"Event ID\", \"Installment Number\") VALUES  ((SELECT CURRENT_DATE), ?, ?, ?, ?);";

        try {

            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setFloat(1, Float.parseFloat(payment.getPayment_Amount()));
            preparedStatement.setLong(2, payment.getPerson_Id());
            preparedStatement.setLong(3, payment.getEvent_Id());
            preparedStatement.setLong(4, payment.getInstallment_Number());
            preparedStatement.executeUpdate();

            connection.commit();
            preparedStatement.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Payment> getAllForSpecifiedPerson(Person person, Event event) {
        ArrayList<Payment> paymentArrayList = new ArrayList<>();
        PreparedStatement preparedStatement;
        String selectQuery = "Select * from \"Payment\" where \"Person ID\"=? and \"Event ID\"=?;";

        try{

            preparedStatement = connection.prepareStatement(selectQuery);
            preparedStatement.setLong(1, person.getPerson_Id());
            preparedStatement.setLong(2, event.getEvent_Id());
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                paymentArrayList.add(getPayment(rs));
            }

            preparedStatement.close();
            rs.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return paymentArrayList;
    }
}
