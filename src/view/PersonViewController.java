package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
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




    public void initialize(){
        System.out.println("TTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTT");

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


       } catch (SQLException e) {
           e.printStackTrace();
       }
    }

}
