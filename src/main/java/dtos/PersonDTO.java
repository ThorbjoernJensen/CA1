/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

import entities.Person;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author tha
 */
public class PersonDTO {
    private long id;
    private String str1;
    private String str2;
    private String str3;

    public PersonDTO(String dummyStr1, String dummyStr2, String dummyStr3) {
        this.str1 = dummyStr1;
        this.str2 = dummyStr2;
        this.str3 = dummyStr3;
    }
    
    public static List<PersonDTO> getDtos(List<Person> rms){
        List<PersonDTO> rmdtos = new ArrayList();
        rms.forEach(rm->rmdtos.add(new PersonDTO(rm)));
        return rmdtos;
    }


    public PersonDTO(Person rm) {
        if(rm.getId() != null)
            this.id = rm.getId();
        this.str1 = rm.getFirstName();
        this.str2 = rm.getLastName();
        this.str3 = rm.getEmail();
    }

    public String getDummyStr1() {
        return str1;
    }

    public void setDummyStr1(String dummyStr1) {
        this.str1 = dummyStr1;
    }

    public String getDummyStr2() {
        return str2;
    }

    public void setDummyStr2(String dummyStr2) {
        this.str2 = dummyStr2;
    }

    public String getDummyStr3() {
        return str3;
    }

    public void setDummyStr3(String str3) {
        this.str3 = str3;
    }

//    @Override
//    public String toString() {
//        return "RenameMeDTO{" + "id=" + id + ", str1=" + str1 + ", str2=" + str2 + '}';
//    }

    @Override
    public String toString() {
        return "RenameMeDTO{" +
                "id=" + id +
                ", str1='" + str1 + '\'' +
                ", str2='" + str2 + '\'' +
                ", str3='" + str3 + '\'' +
                '}';
    }
}
