/**
 * Created by Notrelb on 27.03.2016.
 */
import dao.PersonDAO;
import dao.imp.PersonJDBC;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.Person;

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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private Stage getPrimaryStage(){
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
        System.out.println(personDAO.getPerson(43));
    }
}
