package entities;

import java.io.Serializable;
import javax.persistence.*;


@Entity
@NamedQuery(name = "RenameMe.deleteAllRows", query = "DELETE from Person")
public class Person implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Person() {
    }

    private String firstName;
    private String lastName;
    private String email;

//    @ManyToOne
//    private Address address;
//
//    public Address getAddress() {
//        return address;
//    }
//    public void setAddress(Address address) {
//        this.address = address;
//    }

//    public Person(String firstName, String lastName, String email) {
//        this.firstName = firstName;
//        this.lastName = lastName;
//        this.email = email;
//    }


    public Person(String firstName, String lastName, String email/*,Address address*/) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
//        this.address = address;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

}
