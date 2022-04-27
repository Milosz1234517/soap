package soap.other.daoObjects;

import soap.other.interfaces.IDao;
import soap.other.tableObjects.Installment;
import soap.other.tableObjects.Person;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PersonDao implements IDao<Person> {

    private final Connection connection;

    public PersonDao(Connection connection) {
        this.connection = connection;
    }

    private Person getPerson(ResultSet rs) throws SQLException {
        return new Person(rs.getLong("Person ID"), rs.getString("Name"), rs.getString("Surname"));
    }

    @Override
    public ArrayList<Person> getAll() {
        ArrayList<Person> personArrayList = new ArrayList<>();
        PreparedStatement preparedStatement;
        String selectQuery = "Select * from \"Person\" ;";

        try {

            preparedStatement = connection.prepareStatement(selectQuery);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                personArrayList.add(getPerson(rs));
            }

            preparedStatement.close();
            rs.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return personArrayList;
    }

    @Override
    public boolean save(Person person) {

        PreparedStatement preparedStatement;
        String sql = "INSERT INTO \"Person\" (\"Name\", \"Surname\") VALUES  (?, ?);";

        try {

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, person.getName());
            preparedStatement.setString(2, person.getSurname());
            preparedStatement.executeUpdate();

            connection.commit();
            preparedStatement.close();

        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public boolean delete(Person person) {

        PreparedStatement preparedStatement;
        String sql = "DELETE FROM \"Person\" WHERE \"Person ID\"=?;";

        try {

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, person.getPerson_Id());
            preparedStatement.executeUpdate();

            connection.commit();
            preparedStatement.close();

        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public Person get(long id) {
        Person person = null;
        PreparedStatement preparedStatement;
        String selectQuery = "Select * from \"Person\" where \"Person ID\"=?;";

        try {

            preparedStatement = connection.prepareStatement(selectQuery);
            preparedStatement.setLong(1, id);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                person = getPerson(rs);
            }

            preparedStatement.close();
            rs.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return person;
    }

    public ArrayList<Person> getAllWhoNotPay(Installment installment) {
        ArrayList<Person> personArrayList = new ArrayList<>();
        PreparedStatement preparedStatement;
        String selectQuery = "Select * from \"Person\" where \"Person ID\" not in (Select \"Person ID\" from \"Payment\" where \"Installment Number\"=? and \"Event ID\"=?);";

        try {

            preparedStatement = connection.prepareStatement(selectQuery);
            preparedStatement.setLong(1, installment.getInstallment_Number());
            preparedStatement.setLong(2, installment.getEvent_Id());
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                personArrayList.add(getPerson(rs));
            }

            preparedStatement.close();
            rs.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return personArrayList;
    }
}
