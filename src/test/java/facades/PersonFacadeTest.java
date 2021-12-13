package facades;

import dtos.PersonDTO;
import entities.Phone;
import org.junit.jupiter.api.*;
import utils.EMF_Creator;
import entities.Person;

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

    // Setup the DataBase in a known state BEFORE EACH TEST
    //TODO -- Make sure to change the code below to use YOUR OWN entity class
    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.createNamedQuery("RenameMe.deleteAllRows").executeUpdate();
            em.persist(new Person("Lars", "Bobsen", "mail@mail.dk"));
            em.persist(new Person("Poul", "Robsen", "mailse@mail.dk"));

            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @AfterEach
    public void tearDown() {
//        Remove any data after each test was run
    }

    // TODO: Delete or change this method 
    @Test
    public void testGetPersonCount() throws Exception {
        assertEquals(2, facade.getRenameMeCount(), "Expects two rows in the database");
    }

    @Test
    public void testAfCreateWithPhone() throws Exception {
        Person p1 = new Person("Larse", "Tyndskrap", "snabel@");
        Phone phone = new Phone(35);
        phone.setDescription("Home");
        p1.getPhoneList().add(phone);
        System.out.println("størrelsen på phonelistarray i test er: "+p1.getPhoneList().size());
        facade.create(new PersonDTO(p1));

        PersonDTO returnedPersonDTO = facade.getPersonByFirstName("Larse");
        List<Phone> returnedPhones = returnedPersonDTO.getPhoneList();
        Phone returnedPhone = returnedPhones.get(0);
        int returnedNumber = returnedPhone.getNumber();
        String returnedDescription = returnedPhone.getDescription();

        assertEquals(35, returnedNumber);
        assertEquals("Home", returnedDescription);
    }


    @Test
    public void getPersonByFirstName() throws Exception{
        PersonDTO returnedPersonDTO = facade.getPersonByFirstName("Lars");
        assertEquals("Lars", returnedPersonDTO.getFirstName());
    }


    @Test
    public void getPersonByPhoneNumber() throws  Exception{
        Person p1 = new Person("Lars", "Tyndskrap", "snabel@");
        Phone phone = new Phone(35);
        phone.setDescription("Home");
        phone.setPerson(p1);
        p1.getPhoneList().add(phone);
        facade.create(new PersonDTO(p1));
        PersonDTO returnedPersonDTO = facade.getPersonByPhoneNumber(35);
        assertEquals("Lars", returnedPersonDTO.getFirstName());







    }



}
