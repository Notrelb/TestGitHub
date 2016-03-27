package dao;

import javafx.collections.ObservableList;
import model.Person;

import java.util.List;

/**
 * Created by Notrelb on 27.03.2016.
 */
public interface PersonDAO {
    void insertPerson(String firstName,String lastName, String street, String city);
    void deletePerson(int uniqueID);
    void deletePerson(String firstName,String lastName);
    ObservableList<Person> getAllPersons();
    Person getPerson(int uniqueID);
    Person getPerson(String firstName, String lastName);
    List<Integer> getID(String firstName, String lastName);

}
