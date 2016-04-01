package bp.model;

/**
 * Created by Notrelb on 27.03.2016.
 */

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Person {



    /*
        Diese Klasse ist ein Model bzw. POJO oder auch ander genant ein Entity
        Wir ahben Objectvariablen und Methoden bzw. getter und setter

        ACHTUNG getter sind meinche mit String rückgabetyp und andere mit StringProperty diese brauchen wir um die in einen C
     */

    private  StringProperty firstName;
    private  StringProperty lastName;
    private  StringProperty street;
    private  StringProperty city;
    private  final IntegerProperty uniqueID;


    /**
     * Default constructor.
     */
    public Person() {
        this(null, null,null,null);
    }

    /**
     * Constructor with some initial data.
     *
     * @param firstName
     * @param lastName
     */
    public Person(String firstName, String lastName,String street,String city) {
        this(-10000000,firstName,lastName,street,city);



    }


    public Person (int uniqeID, String firstName, String lastName, String street, String city ){
        /**
         *    Achtung this.firstName ist vom typ StringProperty
         *    und der formaleParameter bzw. local variable ist vom Typ String
         *    dew wegen brauchen wir ein Object vom SimpleStringProperty zu erzeugen vo wir
         *    den String übergeben
         */
        this.uniqueID = new SimpleIntegerProperty(uniqeID);
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.street = new SimpleStringProperty(street);
        this.city = new SimpleStringProperty(city);

    }



    //Da firstName eine INstanz von StringProperty ist brauchen wir die methode get() um ein String zu bekommen

    public String getFirstName() {
        return firstName.get();
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    public StringProperty firstNameProperty() {
        return firstName;
    }

    public String getLastName() {
        return lastName.get();
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }

    public StringProperty lastNameProperty() {
        return lastName;
    }

    public String getStreet() {
        return street.get();
    }

    public void setStreet(String street) {
        this.street.set(street);
    }

    public StringProperty streetProperty() {
        return street;
    }



    public String getCity() {
        return city.get();
    }

    public void setCity(String city) {
        this.city.set(city);
    }

    public StringProperty cityProperty() {
        return city;
    }

    public int getUniqueID(){
        return this.uniqueID.get();
    }

    public IntegerProperty uniqueIDProperty(){
        return  this.uniqueID;
    }


    @Override
    public String toString() {
        return  "firstName=" + getFirstName() +
                ", lastName=" + getLastName() +
                ", street=" + getStreet() +
                ", city=" + getCity() +
                ", uniqueID=" + getUniqueID();
    }
}