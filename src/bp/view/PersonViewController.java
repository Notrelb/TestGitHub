package bp.view;

import bp.MainApp;
import bp.dao.PersonDAO;
import bp.dao.imp.PersonJDBC;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import bp.model.Person;
import bp.util.ConnectionFactory;
import javafx.scene.input.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;


/**
 * Created by Notrelb on 27.03.2016.
 */
public class PersonViewController {

    private ObservableList<Person> persons = FXCollections.observableArrayList();
    private Connection connection;
    private PreparedStatement preparedStatement;
    private PersonDAO personDAO = new PersonJDBC();

    private MainApp mainApp;

    @FXML
    private TableColumn firstNameColumn;
    @FXML
    private TableColumn lastNameColumn;

    @FXML
    private TableView<Person> table;

    @FXML
    private Label lastNameLabel;
    @FXML
    private Label firstNameLabel;
    @FXML
    private Label streetLabel;
    @FXML
    private Label cityLabel;

    @FXML
    private TextField filterInput;

    @FXML
    private ChoiceBox<Integer> choiceBoxSize;




    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    public void initialize(){


        choiceBoxSize.getItems().setAll(1,2,3,4,5,6,7,8,9,10);
        //set the default
        choiceBoxSize.setValue(1);


        filterInput.textProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                filterPersonList((String) oldValue, (String) newValue);
            }
        });




//       try{
        this.connection = ConnectionFactory.getConnection();
        //Persons muss initializiert werden ObservList ist viw Collection in java.lang
     //////////////////////////////////////////////////////////   persons = FXCollections.observableArrayList();

        // Das brauchen wir nicht mehr da wir eine instanc vom PersonDAO haben rufen wir einfach die mehtode getAllPersons
        // Es wurden alle Personen aus der daten bank gelessen un im persons gepeichert
           /* ResultSet resultSet = connection.createStatement().executeQuery("SELECT * FROM Persons");
            while (resultSet.next()){
                persons.add(new Person(resultSet.getString("FirstName"),resultSet.getString("LastName"),resultSet.getString("Street"),resultSet.getString("City")));
            }*/

            persons = personDAO.getAllPersons();


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


        table.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
            }
        });

/*
       } catch (SQLException e) {
           e.printStackTrace();
       }*/
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
     * Popup if the user is not selecting nothing but is clickin on delete
     */

    @FXML
    private void handleDeletePerson(){
        // aus der Datenbank LÖSCHEN zu erst weil sonst gibt es Probleme wenn es nur eine Person in der Liste gibt.
        //  NATÜRLICH wir müssen die Selectierte Person aus der ObserverableListe entfährenen und

        //Mann mus mainApp initializieren oj lope


        // Singele Selection
        Person person =(Person) table.getSelectionModel().getSelectedItem();







       // Selection Multi because the default value is SelectionMode.Single we have to enable the multiple selectio


        table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        ObservableList<Person> persons = new SimpleListProperty<>(table.getSelectionModel().getSelectedItems());

        System.out.println(persons);

      //  int selectedIndex = table.getSelectionModel().getSelectedIndex();
        List<Integer> selectedIdexes = table.getSelectionModel().getSelectedIndices();
        System.out.println(selectedIdexes);

    /*
    ACHTUNGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGG Man muss einmal Von der Datenbank Löschen und einmal von der Tabele bzw von dein VIEW
     */
        if(!persons.isEmpty()){

            for (Person personLoop : persons) {
                this.deletePerson(personLoop);

               // table.getItems().remove();
            }

            table.getItems().removeAll(persons);



            System.out.println(table);

       // if (selectedIndex >= 0) {


         /*   //zu komplex

            table.getSelectionModel().selectedItemProperty().addListener(
                    (observable, oldValue, newValue) -> deletePerson((Person) newValue));*/

          //  table.getItems().remove(selectedIndex);

////////////////////////////////////////////////////////////////////////////////////////
       // this.deletePerson(person);

        }else{
            //Nothing selected.
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("No Selection");
            alert.setHeaderText("No Person Selected");
            alert.setContentText("Please select a person in the table.");

            alert.showAndWait();



        }


    }


    /**
     * Called when the user clicks the new button. Opens a dialog to edit
     * details for a new person.
     */

    @FXML
    private void handleNewPerson(){

        /**
         *
         *                  Auchtung enmal muss es in der Datenbank gespeicher werden und ein mar in der Liste persons
         *
         */


        Person tempPerson = new Person();
        boolean okCkliced = mainApp.showPersonEditDialog(tempPerson);
        if (okCkliced){
            personDAO.insertPerson(tempPerson);
            persons.add(tempPerson);
        }
    }

    private void deletePerson(Person person){
        if (person != null)
        personDAO.deletePerson(person.getUniqueID());

    }

    private void filterPersonList(String oldValue, String newValue){
        ObservableList<Person> filteredList = FXCollections.observableArrayList();

        if(filterInput == null || (newValue.length() < oldValue.length()) || newValue == null){
            table.setItems(persons);
        }else{

            for (Person personIterator : table.getItems()){
                String filterFirstNameAndLastName = personIterator.getFirstName() + personIterator.getLastName();
                filterFirstNameAndLastName.replaceAll("\\s+","");
                System.out.println("****************************************  " + filterFirstNameAndLastName);
                String newValueWhithOutSpace = newValue.replaceAll("\\s+","");
                if (filterFirstNameAndLastName.toUpperCase().contains(newValueWhithOutSpace.toUpperCase())){
                    filteredList.add(personIterator);
                }

           /* for (Person personIterator : table.getItems()){
                String filterFirstName = personIterator.getFirstName();
                String filterLastName = personIterator.getLastName();


                if(filterFirstName.toUpperCase().contains(newValue.toUpperCase()) || filterLastName.toUpperCase().contains(newValue.toUpperCase())){
                    filteredList.add(personIterator);
                }*/
            }
            table.setItems(filteredList);



        }
    }



}
