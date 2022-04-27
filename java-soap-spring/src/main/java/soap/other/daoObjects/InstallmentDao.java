package soap.other.daoObjects;

import soap.other.interfaces.IDao;
import soap.other.tableObjects.Event;
import soap.other.tableObjects.Installment;
import soap.other.tableObjects.Person;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class InstallmentDao implements IDao<Installment> {

    private final Connection connection;

    public InstallmentDao(Connection connection) {
        this.connection = connection;
    }

    private Installment getInstallment(ResultSet rs) throws SQLException {
        return new Installment(rs.getLong("Installment ID"), rs.getLong("Event ID"), rs.getLong("Installment Number"), rs.getDate("Due Date"), rs.getString("Installment Amount"));
    }

    public Installment get(long id) {
        Installment installment = null;
        PreparedStatement preparedStatement;
        String selectQuery = "Select * from \"Installment\" where \"Installment ID\"=?;";

        try {

            preparedStatement = connection.prepareStatement(selectQuery);
            preparedStatement.setLong(1, id);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                installment = getInstallment(rs);
            }

            preparedStatement.close();
            rs.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return installment;
    }

    @Override
    public ArrayList<Installment> getAll() {
        ArrayList<Installment> installmentArrayList = new ArrayList<>();
        PreparedStatement preparedStatement;
        String selectQuery = "Select * from \"Installment\" ;";

        try {

            preparedStatement = connection.prepareStatement(selectQuery);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                installmentArrayList.add(getInstallment(rs));
            }

            preparedStatement.close();
            rs.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return installmentArrayList;
    }

    @Override
    public boolean save(Installment installment) {

        PreparedStatement preparedStatement;
        String sql = "INSERT INTO \"Installment\" (\"Event ID\", \"Installment Number\", \"Due Date\", \"Installment Amount\") VALUES  (?, ?, ?, ?);";

        try {

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, installment.getEvent_Id());
            preparedStatement.setLong(2, installment.getInstallment_Number());
            preparedStatement.setDate(3, installment.getDue_Date());
            preparedStatement.setFloat(4, Float.parseFloat(installment.getInstallment_Amount()));
            preparedStatement.executeUpdate();

            connection.commit();
            preparedStatement.close();

        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public boolean delete(Installment installment) {

        PreparedStatement preparedStatement;
        String sql = "DELETE FROM \"Installment\" WHERE \"Installment ID\"=?;";

        try {

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, installment.getInstallment_Id());
            preparedStatement.executeUpdate();

            connection.commit();
            preparedStatement.close();

        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public long getMaxInstalmentNumberForEvent(Event event) {

        long number = 0;
        PreparedStatement preparedStatement;
        String selectQuery = "SELECT MAX(\"Installment Number\")  FROM \"Installment\" WHERE \"Event ID\"=?;";

        try {

            preparedStatement = connection.prepareStatement(selectQuery);
            preparedStatement.setLong(1, event.getEvent_Id());

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                number = rs.getLong("max");
            }

            preparedStatement.close();
            rs.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return number + 1;

    }


    public ArrayList<Installment> getAllForSpecifiedEvent(Event event) {
        ArrayList<Installment> installmentArrayList = new ArrayList<>();
        PreparedStatement preparedStatement;
        String selectQuery = "Select * from \"Installment\" where \"Event ID\"=?;";

        try {

            preparedStatement = connection.prepareStatement(selectQuery);
            preparedStatement.setLong(1, event.getEvent_Id());
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                installmentArrayList.add(getInstallment(rs));
            }

            preparedStatement.close();
            rs.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return installmentArrayList;
    }

    public ArrayList<Installment> getAllForWhoNotPay(Person person, Event event) {
        ArrayList<Installment> installmentArrayList = new ArrayList<>();
        PreparedStatement preparedStatement;

        String selectQuery = "Select * from \"Installment\" where \"Installment Number\" not in (Select \"Installment Number\" from \"Payment\" where \"Person ID\" = ? and \"Event ID\" =?) and \"Event ID\" =?;";

        try {

            preparedStatement = connection.prepareStatement(selectQuery);
            preparedStatement.setLong(1, person.getPerson_Id());
            preparedStatement.setLong(2, event.getEvent_Id());
            preparedStatement.setLong(3, event.getEvent_Id());
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                installmentArrayList.add(getInstallment(rs));
            }

            preparedStatement.close();
            rs.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return installmentArrayList;
    }
}
