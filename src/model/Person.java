package model;

/**
 * Created by Notrelb on 27.03.2016.
 */

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Person {

    private  StringProperty firstName;
    private  StringProperty lastName;
    private  StringProperty street;
    private  StringProperty city;


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

        /**
         *    Achtung this.firstName ist vom typ StringProperty
         *    und der formaleParameter bzw. local variable ist vom Typ String
         *    dew wegen brauchen wir ein Object vom SimpleStringProperty zu erzeugen vo wir
         *    den String übergeben
         */

        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.street = new SimpleStringProperty(street);
        this.city = new SimpleStringProperty(city);


    }

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

}