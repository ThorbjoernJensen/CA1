package facades;

import dtos.PersonDTO;
import entities.*;

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

    public PersonDTO create(PersonDTO rm) {
        Person rme = new Person(rm.getFirstName(), rm.getLastName(), rm.getEmail());
//        vi vil gerne have at der også bliver persisteret et telefonnummer med personen -
        rme.setPhoneList(rm.getPhoneList());
        System.out.println("her har vi sat en liste med inhold" + rme.getPhoneList().size());
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

    public PersonDTO getPersonByFirstName(String firstName) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Person> query = em.createQuery("select p from Person p where p.firstName = :firstName", Person.class).setParameter("firstName", firstName);
            return new PersonDTO(query.getSingleResult());

        } finally {
            em.close();
        }
    }


    public PersonDTO getPersonByPhoneNumber(int phoneNumber) {
        EntityManager em = emf.createEntityManager();
        try {

            Query query = em.createQuery("select p from Person p join p.phoneList t where t.number = :phoneNumber").setParameter("phoneNumber", phoneNumber);
//            Query query4 = em.createQuery("select e from Employee e where e.salary = (select max(e.salary) from Employee e)")

//            TypedQuery<Phone> query = em.createQuery("select ph from Phone ph where ph.number = :phoneNumber", Phone.class).setParameter("phoneNumber", phoneNumber);
//            Phone phone = query.getSingleResult();
//            System.out.println("her har vi et nummer"+phone.getNumber());
//            System.out.println("her har vi beskrivelse "+phone.getDescription());
//            Person person = phone.getPerson();
//            if(phone.getPerson() != null) {
//                System.out.println("det er ikke helt tomt");
//            }
///            System.out.println("vi har fundet denne person"+person.getFirstName());


//            TypedQuery<Department> query
//                    = entityManager.createQuery(
//                    "SELECT d FROM Employee e, Department d WHERE e.department = d", Department.class);
//            List<Department> resultList = query.getResultList();
            List<Person> personList = query.getResultList();
            if (personList.size() > 0) {
                return new PersonDTO(personList.get(0));
            } else throw new WebApplicationException("No person found", 400);

//            return new PersonDTO(new Person("Lasse", "from", "@"));

        } finally {
            em.close();
        }
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

//        //        fe.getAll().forEach(dto -> System.out.println(dto));


        //Tester
//        Address a1= new Address("Skøjteby", "vandland");
//        a1.setCityInfo(new CityInfo("1114"));
        Phone p1 = new Phone(123081);
        Person rme = new Person("Erik", "Hansen", "flotemail@dk");
//        Hobby h1= new Hobby("Ringridning", "Man rider på hest");
//        rme.addHobbies(h1);
//        rme.setAddress(a1);
        rme.addPhone(p1);
//
//        rme.setAddress(a1);
//        a1.addPerson(rme);
//
//
        EntityManager em = emf.createEntityManager();


        try {
            em.getTransaction().begin();
            em.persist(rme);

//            em.persist(new Address("Skinkevangen 5000", "hulseby"));
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(p1.getPerson().getId());

    }
}