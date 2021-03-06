package facades;

import dtos.PersonDTO;
import entities.*;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.ws.rs.WebApplicationException;

import utils.EMF_Creator;

public class PersonFacade {
    private static PersonFacade instance;
    private static EntityManagerFactory emf;

    //Private Constructor to ensure Singleton
    private PersonFacade() {
    }

    /**
     * @param _emf
     * @return an instance of this facade class.
     */

    public static PersonFacade getFacadeExample(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new PersonFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
//hvorfor laver vi ikke også en entitymanager til at starte med?

//    Her starter CRUD-operationer
//    hvilke persondata skal der være i addPerson? skal der også være by osv?
    public PersonDTO addPerson(PersonDTO personDTO) {
        Person person = new Person(personDTO.getFirstName(), personDTO.getLastName(), personDTO.getEmail());
        person.setPhoneList(personDTO.getPhoneList());
//        EntityManager em = emf.createEntityManager();
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(person);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return new PersonDTO(person);
    }

    //    denne metode giver kun den funktion at returnere alle personer med et bestemt fornavn. måske lidt tvivlsom værdi
    public List<PersonDTO> getPersonsByFirstName(String firstName) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Person> query = em.createQuery("select p from Person p where p.firstName = :firstName", Person.class).setParameter("firstName", firstName);
            List<Person> persons = query.getResultList();
            List<PersonDTO> personDTOList = new ArrayList<>();
            for (Person person : persons) {
                personDTOList.add(new PersonDTO(person));
            }
            return personDTOList;

        } finally {
            em.close();
        }
    }

    //vi går ud fra at der i praksis kun er en person med et givent telefonnummer, så selvom vi henter en liste tager vi bare den første.
//    man kunne måske sikre at flere ikke kunne have samme nummer? eller også skal det bare være ok at der kommer flere...
    public PersonDTO getPersonByPhoneNumber(int phoneNumber) {
        EntityManager em = getEntityManager();
        try {
            Query query = em.createQuery("select p from Person p join p.phoneList t where t.number = :phoneNumber").setParameter("phoneNumber", phoneNumber);
            List<Person> personList = query.getResultList();
            if (personList.size() > 0) {
                return new PersonDTO(personList.get(0));
            } else throw new WebApplicationException("No person found", 400);
        } finally {
            em.close();
        }
    }

    public PersonDTO getPersonByEmail(String email) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Person> query = em.createQuery("select p from Person p where p.email = :email", Person.class).setParameter("email", email);

            List<Person> personList = query.getResultList();
            if (personList.size() > 0) {
                return new PersonDTO(personList.get(0));
            } else throw new WebApplicationException("No person found", 400);
        } finally {
            em.close();
        }
    }



    public PersonDTO getById(long id) {
        EntityManager em = emf.createEntityManager();
        return new PersonDTO(em.find(Person.class, id));
    }

    public long getPersonCount() {
        EntityManager em = emf.createEntityManager();
        try {
            long personCount = (long) em.createQuery("SELECT COUNT(r) FROM Person r").getSingleResult();
            return personCount;
        } finally {
            em.close();
        }
    }

    public List<PersonDTO> getAll() {
        EntityManager em = emf.createEntityManager();
        TypedQuery<Person> query = em.createQuery("SELECT r FROM Person r", Person.class);
        List<Person> rms = query.getResultList();
        return PersonDTO.getDtos(rms);
    }


    public static void main(String[] args) {
        emf = EMF_Creator.createEntityManagerFactory();
        PersonFacade fe = getFacadeExample(emf);
        fe.getAll().forEach(dto -> System.out.println(dto));


//        Address a1= new Address("Skøjteby", "vandland");
//        a1.setCityInfo(new CityInfo("1114"));
        Phone p1 = new Phone(123081);
        Person person = new Person("Erik", "Hansen", "flotemail@dk");
//        Hobby h1= new Hobby("Ringridning", "Man rider på hest");
//        person.addHobbies(h1);
//        person.setAddress(a1);
        person.addPhone(p1);
//        person.setAddress(a1);
//        a1.addPerson(person);

        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();
            em.persist(person);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(p1.getPerson().getId());
    }
}