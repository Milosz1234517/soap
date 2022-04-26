package daoObjects;

import interfaces.IDao;
import tableObjects.Event;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EventDao implements IDao<Event> {

    private final Connection connection;

    public EventDao(Connection connection) {
        this.connection = connection;
    }

    private Event getEvent(ResultSet rs) throws SQLException {
        return new Event(rs.getLong("Event ID"), rs.getString("Name"), rs.getString("Place"), rs.getDate("Date"));
    }

    @Override
    public ArrayList<Event> getAll() {
        ArrayList<Event> eventArrayList = new ArrayList<>();
        PreparedStatement preparedStatement;
        String selectQuery = "Select * from \"Event\" ;";

        try{

            preparedStatement = connection.prepareStatement(selectQuery);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                eventArrayList.add(getEvent(rs));
            }

            preparedStatement.close();
            rs.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return eventArrayList;
    }

    @Override
    public void save(Event event) {

        PreparedStatement preparedStatement;
        String sql = "INSERT INTO \"Event\" (\"Name\", \"Place\", \"Date\") VALUES  (?, ?, ?);";

        try {

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, event.getName());
            preparedStatement.setString(2, event.getPlace());
            preparedStatement.setDate(3, event.getDate());
            preparedStatement.executeUpdate();

            connection.commit();
            preparedStatement.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
