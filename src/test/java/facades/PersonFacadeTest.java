package facades;

import dtos.PersonDTO;
import entities.*;
import org.junit.jupiter.api.*;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

//Uncomment the line below, to temporarily disable this test
//@Disabled
public class PersonFacadeTest {
    private static EntityManagerFactory emf;
    private static PersonFacade facade;
    private static Person p1, p2, p3;
    private static Phone ph1, ph2, ph3;
    private static Address a1, a2, a3;
    private static Hobby h1, h2, h3;

    public PersonFacadeTest() {
    }

    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        facade = PersonFacade.getFacadeExample(emf);
    }

    @AfterAll
    public static void tearDownClass() {
//        Clean up database after test is done or use a persistence unit with drop-and-create to start up clean on every test
    }


    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        p1 = new Person("Hansemand", "Tydelig", "snabel@snavs");
        ph1 = new Phone(38383838);
        a1 = new Address("Bermudavej", "additionalinfo", new CityInfo("0800"));
        p1.setAddress(a1);
        p1.addPhone(ph1);

        p2 = new Person("Lars", "Bobsen", "mail@snail.dk");
        ph2 = new Phone(39393939);
        a2 = new Address("Bentevej", "additionalinfo", new CityInfo("5000"));
        p2.setAddress(a2);
        p2.addPhone(ph2);

        p3 = new Person("Poul", "Robsen", "mailse@mail.dk");
        ph3 = new Phone(40404040);
        a3 = new Address("Skælskørvej", "additionalinfo", new CityInfo("0800"));
        p3.setAddress(a3);
        p3.addPhone(ph3);


        try {
            em.getTransaction().begin();
            em.createNamedQuery("Phone.deleteAllRows").executeUpdate();
            em.createNamedQuery("Person.deleteAllRows").executeUpdate();
            em.createNamedQuery("Address.deleteAllRows").executeUpdate();
            em.persist(p1);
            em.persist(p2);
            em.persist(p3);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @AfterEach
    public void tearDown() {
//        Remove any data after each test was run
    }

    @Test
    public void testGetPersonCount() throws Exception {
        assertEquals(3, facade.getPersonCount());
    }

    @Test
    public void testAddPerson() throws Exception {
        Person p4 = new Person("Preben", "Jakobsdottir", "mail@mæil.dk");
        Phone phone = new Phone(3599);
        phone.setDescription("Home");
        p4.addPhone(phone);

        PersonDTO resultDTO = facade.addPerson(new PersonDTO(p4));
        PersonDTO expectedDTO = new PersonDTO(p4);
        assertEquals(expectedDTO, resultDTO);

        int expectedNumber = 3599;
//        Testen er her afhængig af der kun er et telefonnummer i listen
        int resultNumber = resultDTO.getPhoneList().get(0).getNumber();
        assertEquals(expectedNumber, resultNumber);

//        I stedet for at sætte id kunstigt har jeg valgt at slette den fra equals funktionen.
    }


    @Test
    public void getPersonByFirstName() throws Exception {
        List<PersonDTO> returnedPersonsDTO = facade.getPersonsByFirstName("Lars");
        assertEquals("Lars", returnedPersonsDTO.get(0).getFirstName());
    }

    @Test
    void getPersonByEmail() {
        PersonDTO returnedPersonsDTO = facade.getPersonByEmail("snabel@snavs");
        assertEquals("Hansemand", returnedPersonsDTO.getFirstName());
    }
}