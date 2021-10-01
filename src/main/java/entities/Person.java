package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;


@Entity
@NamedQuery(name = "RenameMe.deleteAllRows", query = "DELETE from Person")
public class Person implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private String email;

    @ManyToOne (cascade = CascadeType.PERSIST)
    private Address address;

    @OneToMany(mappedBy = "person", cascade = CascadeType.PERSIST)
    private List<Phone> phoneList;

    @ManyToMany(mappedBy = "personList",  cascade = CascadeType.PERSIST)
    private List<Hobby> hobbies;

    public Person() {
    }

    public Person(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneList= new ArrayList<>();
        this.hobbies=new ArrayList<>();

    }

    public void addHobbies(Hobby hobby) {
        this.hobbies.add(hobby);
        if(hobby!=null){
            hobby.getPersonList().add(this);
        }

    }

    public void removeHobbies(Hobby hobby) {
        this.hobbies.remove(hobby);
        if(hobby!=null){
            hobby.getPersonList().remove(hobby);
        }

    }


    public void addPhone(Phone phone){
        this.phoneList.add(phone);
        if (phone!=null){
            phone.setPerson(this);
        }
    }

    public List<Phone> getPhoneList() {
        return phoneList;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
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
