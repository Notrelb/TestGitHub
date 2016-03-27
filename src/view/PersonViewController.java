package view;

import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Person;
import util.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Notrelb on 27.03.2016.
 */
public class PersonViewController {

    private ObservableList<Person> persons;
    private Connection connection;
    private PreparedStatement preparedStatement;


    @FXML
    private TableColumn firstNameColumn;
    @FXML
    private TableColumn lastNameColumn;

    @FXML
    private TableView table;

    @FXML
    private Button newButton;
    @FXML
    private Button editButton;
    @FXML
    private Button deleteButton;

    @FXML
    private Label lastNameLabel;
    @FXML
    private Label firstNameLabel;
    @FXML
    private Label streetLabel;
    @FXML
    private Label cityLabel;





    public void initialize(){

       try{
        this.connection = ConnectionFactory.getConnection();
        //Persons muss initializiert werden ObservList ist viw Collection in java.lang
        persons = FXCollections.observableArrayList();


        // Es wurden alle Personen aus der daten bank gelessen un im persons gepeichert
            ResultSet resultSet = connection.createStatement().executeQuery("SELECT * FROM Persons");
            while (resultSet.next()){
                persons.add(new Person(resultSet.getString("FirstName"),resultSet.getString("LastName"),resultSet.getString("Street"),resultSet.getString("City")));
            }


        //Da wir die personen gespeichert haben müssen wir die auch mit der View verbinden
        /*
                    ACHTUMG hir wird firstNameColumn initializert der String im PropertyValueFactory muss übereinstimmen wie beim model Person
                    d.h es wird die Methode vom Person  public StringProperty firstNameProperty() aufgeruft

         */
        firstNameColumn.setCellValueFactory(new PropertyValueFactory("firstName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory("lastName"));

        //jext muss wir auch denn ganzen inhalt im tabel einfügen
           table.setItems(persons);


           /**
            *
            *   To get informed when the user selects a person in the person table, we need to listen for changes.
            *
            *   There is an interface in JavaFX called ChangeListner with on method calles changed(...).
            *   The method has three parameters: observable, odValue, and newValue.
            *
            *       WICHTIG (Person) kann Falsch sein
            */
           table.getSelectionModel().selectedItemProperty().addListener(
                   (observable, oldValue, newValue) -> showPersonDetails((Person) newValue));


       } catch (SQLException e) {
           e.printStackTrace();
       }
    }


    /**
     *            Fill the labels with the data from a single Person
     *            Fills all text fields to show details about the person.
     *            If the specified personis null, all text filds are cleared.
     *
     *            @param person the person or null
     */


    private void showPersonDetails(Person person){

        if (person != null){
            //Fill the labels with info from the person object
            firstNameLabel.setText(person.getFirstName());
            lastNameLabel.setText(person.getLastName());
            streetLabel.setText(person.getStreet());
            cityLabel.setText(person.getCity());

        }else{
            //Personis nulll, remove all the text.
            firstNameLabel.setText("");
            lastNameLabel.setText("");
            streetLabel.setText("");
            cityLabel.setText("");
        }

    }


    /**
     * Celled when the user click on the delete button.
     */

    @FXML
    private void handleDeletePerson(){
        table.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> deletePerson((Person) newValue));


    }


    private void deletePerson(Person person){

    }



}
