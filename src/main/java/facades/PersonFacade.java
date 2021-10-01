package facades;

import dtos.PersonDTO;
import entities.Address;
import entities.CityInfo;
import entities.Person;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

import entities.Phone;
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

    public PersonDTO create(PersonDTO rm) {
        Person rme = new Person(rm.getDummyStr1(), rm.getDummyStr2(), rm.getDummyStr3());
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(rme);
            em.getTransaction().commit();


        } finally {
            em.close();
        }
        return new PersonDTO(rme);
    }

    public PersonDTO getById(long id) {
        EntityManager em = emf.createEntityManager();
        return new PersonDTO(em.find(Person.class, id));
    }

    //TODO Remove/Change this before use
    public long getRenameMeCount() {
        EntityManager em = emf.createEntityManager();
        try {
            long renameMeCount = (long) em.createQuery("SELECT COUNT(r) FROM Person r").getSingleResult();
            return renameMeCount;
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

        //        fe.getAll().forEach(dto -> System.out.println(dto));


        //Tester
        Address a1= new Address("Skøjteby", "vandland");
        a1.setCityInfo(new CityInfo("1114"));
        Phone p1= new Phone(123080, "Homenumber");
        Person rme = new Person("Erik", "Hansen", "flotemail@dk");
        rme.setAddress(a1);
        rme.addPhone(p1);

        rme.setAddress(a1);
        a1.addPerson(rme);
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(rme);

//            em.persist(new Address("Skinkevangen 5000", "hulseby"));
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}