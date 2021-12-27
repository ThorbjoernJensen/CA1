/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

import entities.Hobby;
import entities.Person;
import entities.Phone;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author tha
 */

public class PersonDTO {
    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private List<Phone> phoneList;
    private List<Hobby> hobbyList;


    public PersonDTO(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public PersonDTO(Person person) {
        if(person.getId() != null)
            this.id = person.getId();
        this.firstName = person.getFirstName();
        this.lastName = person.getLastName();
        this.email = person.getEmail();
//        phonelist tilføjes
        this.phoneList=person.getPhoneList();

        // hobbieslist?
    }

   //hvad pokker skal vi bruge det til?
    public static List<PersonDTO> getDtos(List<Person> personList){
        List<PersonDTO> personDTOList = new ArrayList();
        personList.forEach(rm->personDTOList.add(new PersonDTO(rm)));
        return personDTOList;
    }



    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Phone> getPhoneList() {
        return phoneList;
    }


    public void setPhoneList(List<Phone> phoneList) {
        this.phoneList = phoneList;
    }

    @Override
    public String toString() {
        return "PersonDTO{" +
                "id=" + id +
                ", str1='" + firstName + '\'' +
                ", str2='" + lastName + '\'' +
                ", str3='" + email + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersonDTO personDTO = (PersonDTO) o;
        return Objects.equals(firstName, personDTO.firstName) && Objects.equals(lastName, personDTO.lastName) && Objects.equals(email, personDTO.email) && Objects.equals(phoneList, personDTO.phoneList) && Objects.equals(hobbyList, personDTO.hobbyList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, email, phoneList, hobbyList);
    }
}
