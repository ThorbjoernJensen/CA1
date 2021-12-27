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
    private static Phone ph1, ph2, ph3;

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
        p1.addPhone(ph1);

        p2 = new Person("Lars", "Bobsen", "mail@snail.dk");
        ph2 = new Phone(39393939);
        p2.addPhone(ph2);

        p3 = new Person("Poul", "Robsen", "mailse@mail.dk");
        ph3 = new Phone(40404040);
        p3.addPhone(ph3);

        try {
            em.getTransaction().begin();
            em.createNamedQuery("Phone.deleteAllRows").executeUpdate();
            em.createNamedQuery("Person.deleteAllRows").executeUpdate();
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
    public void testAfCreateWithPhone() throws Exception {
        Phone phone = new Phone(3599);
        phone.setDescription("Home");
        p1.addPhone(phone);

//      Det er denne her der giver problemer
//        p1.getPhoneList().add(phone);
//        phone bliver added til p1's phonelist men person-objektet bliver ikke knyttet til person-objektet. phone
//        har ikke nogen reference til person-objektet. der skal peges begge veje, og jeg har kun peget den ene vej, og
//        troet at den anden vej var implicit.
//        Person-objektet kan ikke tvinge data ind i phone-objektet.

//        skulle jpa ikke automatisk knytte telefon til person når vi persisterer person?!
//        phone.setPerson(p1);
//        denne linie kan vi komme ud over ved blot at bruge den add-funktion vi har i vores personobjekt, da vi her sørger for
//        at phoneobjektet også peger tilbage på person.

//        System.out.println("er p1 født med et id? så kommer det her: "+p1.getId());
//        p1 bliver persisteret i setup, det er derfor den har et id.

        PersonDTO resultDTO = facade.addPerson(new PersonDTO(p1));
        PersonDTO expectedDTO = new PersonDTO(p1);

//        I stedet for at sætte id kunstigt har jeg valgt at slette den fra equals funktionen.
        assertEquals(expectedDTO, resultDTO);
        // hvad har jeg tænkt at jeg testede her?
//        PersonDTO returnedPersonDTO = facade.getPersonsByFirstName("Hansemand");
//        Phone returnedPhone = returnedPersonDTO.getPhoneList().get(0);
//        int returnedNumber = returnedPhone.getNumber();
//        String returnedDescription = returnedPhone.getDescription();
//        assertEquals(3599, returnedNumber);
//        assertEquals("Home", returnedDescription);
    }


    @Test
    public void getPersonByFirstName() throws Exception {
        List<PersonDTO> returnedPersonsDTO = facade.getPersonsByFirstName("Lars");
        assertEquals("Lars", returnedPersonsDTO.get(0).getFirstName());
    }


    @Test
    public void getPersonByPhoneNumber() throws Exception {
        facade.addPerson(new PersonDTO(p1));
        PersonDTO returnedPersonDTO = facade.getPersonByPhoneNumber(38383838);
        assertEquals("Hansemand", returnedPersonDTO.getFirstName());


    }


}
