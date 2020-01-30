package com.example.servermatch.cecs445.models;

public class Person {
    private String firstName;
    private String lastName;

    public Person(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setlastName(String lastName) { this.lastName = lastName; }

    @Override
    public String toString(){
        return ("Person[lastName=" + lastName + ", firstName=" + firstName + "]");
    }
}
