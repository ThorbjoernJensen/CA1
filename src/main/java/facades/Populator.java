/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import dtos.PersonDTO;
import entities.Person;
import javax.persistence.EntityManagerFactory;
import utils.EMF_Creator;

/**
 *
 * @author tha
 */
public class Populator {
    public static void populate(){
        EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory();
        PersonFacade fe = PersonFacade.getFacadeExample(emf);
        fe.addPerson(new PersonDTO(new Person("First 1", "Last 1", "email1")));
        fe.addPerson(new PersonDTO(new Person("First 2", "Last 2", "email2")));


    }
    
    public static void main(String[] args) {
        populate();
    }
}
