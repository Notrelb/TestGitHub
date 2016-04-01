package bp.dao.imp;

import bp.dao.PersonDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import bp.model.Person;
import bp.util.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Notrelb on 27.03.2016.
 */
public class PersonJDBC implements PersonDAO {

    Connection connetion = ConnectionFactory.getConnection();
    PreparedStatement preparedStatement = null;

    @Override
    public void insertPerson(String firstName, String lastName, String street, String city) {
        try {
            preparedStatement = connetion.prepareStatement("INSERT INTO Persons (FirstName,lastName,street,city)" +
                    " VALUES (?,?,?,?)");
            preparedStatement.setString(1,firstName);
            preparedStatement.setString(2,lastName);
            preparedStatement.setString(3,street);
            preparedStatement.setString(4,city);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void insertPerson(Person person) {
        this.insertPerson(person.getFirstName(),
                person.getLastName(),person.getStreet(),person.getCity());

    }

    @Override
    public void deletePerson(int uniqueID) {
        try {
            preparedStatement = connetion.prepareStatement("DELETE FROM Persons WHERE id=?");
            preparedStatement.setInt(1,uniqueID);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deletePerson(String firstName, String lastName) {
        try {
            preparedStatement = connetion.prepareStatement("DELETE From Persons Where firstname=? and lastname=?");
            preparedStatement.setString(1,firstName);
            preparedStatement.setString(2,lastName);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ObservableList<Person> getAllPersons() {

        ObservableList<Person> result = FXCollections.observableArrayList();

        try {
            ResultSet resultSet = connetion.createStatement().executeQuery("SELECT * FROM Persons");
            while (resultSet.next()){

                result.add(new Person(resultSet.getInt(1),resultSet.getString(2),resultSet.getString("lastname"),resultSet.getString(4),resultSet.getString("city")));

            }


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;

    }

    @Override
    public Person getPerson(int uniqueID) {
        Person resultPerson = null;
        ResultSet resultSet = null;
        String selectSQL = "SELECT * From Persons WHERE id=?";


        try {
            preparedStatement = connetion.prepareStatement(selectSQL);
            preparedStatement.setInt(1,uniqueID);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){

                resultPerson = new Person(resultSet.getInt(1),resultSet.getString(2),resultSet.getString("lastname"),resultSet.getString(4),resultSet.getString("city"));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return resultPerson;
    }

    @Override
    public Person getPerson(String firstName, String lastName) {
        return null;
    }



    /*

                It cann be that you have Persons whith same firstname and lastName

     */
    @Override
    public List<Integer> getID(String firstName, String lastName) {
        return null;
    }
}
