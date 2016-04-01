package bp.view;

import bp.model.Person;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

/**
 * Created by Notrelb on 28.03.2016.
 */
public class PersonEditDialogController {


    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField streetField;
    @FXML
    private TextField cityField;
    @FXML
    private ChoiceBox<Integer> numberOfPerson;


    private Stage dialogStage;
    private Person person;
    private boolean okClicked = false;



    @FXML
    private void initialize(){


        numberOfPerson.getItems().setAll(1,2,3,4,5,6,7,8,9,10);
        //default value
        numberOfPerson.setValue(1);

    }

    public void setDialogStage(Stage dialogStage){
        this.dialogStage = dialogStage;
    }

    public void setPerson(Person person){
        this.person = person;

        firstNameField.setText(person.getFirstName());
        lastNameField.setText(person.getLastName());
        streetField.setText(person.getStreet());
        cityField.setText(person.getCity());
    }

    public boolean isOkClicked(){
        return okClicked;
    }


    @FXML
    private void handleOK(){
        if(isInputValid()){
            person.setFirstName(firstNameField.getText());
            person.setLastName(lastNameField.getText());
            person.setStreet(streetField.getText());
            person.setCity(cityField.getText());



            okClicked = true;
            dialogStage.close();
        }

    }


    private boolean isInputValid(){
        String errorMessage = "";

        if (firstNameField.getText() == null || firstNameField.getText().length() == 0){
            errorMessage += "No valid first name! \n";
        }
        if (lastNameField.getText() == null || lastNameField.getText().length() == 0){
            errorMessage += "Not valid last name! \n";
        }
        if (streetField.getText() == null || streetField.getText().length() == 0){
            errorMessage += "Not valid stree!\n";
        }
        if (cityField.getText() == null || cityField.getText().length() == 0){
            errorMessage += "Not valid city! \n";
        }

        if(errorMessage.length() == 0){
            return true;
        }else{
            //Show the error message
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }



    }
}
