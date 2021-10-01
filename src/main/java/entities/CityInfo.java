package entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Entity
public class CityInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(length = 4)
    private String zipCode;
    @Column(length=35)
    private String city;


    @OneToMany (mappedBy = "cityInfo", cascade= CascadeType.PERSIST)
    private List<Address> addresses;

    public CityInfo() {
    }

    public CityInfo(String zipCode) {
        this.zipCode = zipCode;
               this.addresses = new ArrayList<>();
    }


    public void addAddress(Address address) {
        this.addresses.add(address);
        if (address != null){
            address.setCityInfo(this);
        }
    }

    public List<Address> getAddresses() {
        return addresses;
    }


    public String getZipCode() {
        return zipCode;
    }

//    den her skal vi ikke bruge
//    public void setZipCode(String zipCode) {
//        this.zipCode = zipCode;
//    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }





}