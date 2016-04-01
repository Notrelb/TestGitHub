package bp; /**
 * Created by Notrelb on 27.03.2016.
 */
import bp.dao.PersonDAO;
import bp.dao.imp.PersonJDBC;
import bp.model.Person;
import bp.view.PersonEditDialogController;
import bp.view.PersonViewController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by Notrelb on 27.03.2016.
 */
public class MainApp extends Application{

    private Stage primaryStage;
    private BorderPane rootLayout;


    @Override
    public void start(Stage primaryStage) throws Exception {

        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("AddressApp");

        //Metode for Initializes the root layout
        initRootLayout();


        //Show the person overview inside the root laout
        showPersonOverview();

    }

    public void initRootLayout(){

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));

            rootLayout = (BorderPane) loader.load();

            //Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showPersonOverview(){

        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/PersonView.fxml"));
            AnchorPane personView = (AnchorPane) loader.load();

            //Set personoverview into the center of root layout.
            rootLayout.setCenter(personView);


            /*************************************************************************
             *
             *      WICHTIG . PersonViewController hat auch zugrif auf MAIN
             *
             *
             */
            //Gibe the controller acces to the main app.
            PersonViewController controller = loader.getController();
            controller.setMainApp(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

                public boolean showPersonEditDialog(Person person){

        try {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApp.class.getResource("view/PersonEditDialog.fxml"));
        AnchorPane page = (AnchorPane) loader.load();

        //Create the dialog Stage.

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit Person");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            //Set the person into the controller.
            PersonEditDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setPerson(person);


            //Set the dialog and wait until the user closses it
            dialogStage.showAndWait();

            return controller.isOkClicked();

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        //Create the dialog Stage.
    }


    public Stage getPrimaryStage(){
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);

      PersonDAO personDAO = new PersonJDBC();
      // personDAO.insertPerson("Lindi","Nazimi","Doberllug","Vustrii");

        //personDAO.deletePerson(1);

         // personDAO.deletePerson("Florian","Pllana");

        System.out.println(personDAO.getAllPersons().toString());
        System.out.println("*********************************************");

    }

}
