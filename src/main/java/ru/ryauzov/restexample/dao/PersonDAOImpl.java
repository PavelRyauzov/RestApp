package ru.ryauzov.restexample.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.ryauzov.restexample.entities.PersonEntity;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class PersonDAOImpl implements PersonDAO {
    private SessionFactory sessionFactory;

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void create(PersonEntity person) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(person);
    }

    @Override
    public List<PersonEntity> getPeople() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from PersonEntity").list();
    }
}
