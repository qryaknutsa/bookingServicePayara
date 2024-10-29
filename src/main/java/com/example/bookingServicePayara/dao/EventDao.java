package com.example.bookingServicePayara.dao;


import com.example.bookingServicePayara.model.Event;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;


@Stateless
public class EventDao {
    @PersistenceContext(unitName = "myPersistenceUnit")
    private EntityManager em;

    public EntityManager getEm() {
        return em;
    }

    private Event safeFind(Long id)  {
        return em.find(Event.class, id);
    }

    public void save(Event r) {
//        var fromId = r.getFrom().getId();
//        var toId = r.getTo().getId();
//        if (fromId != null) {
//            var query = em.createQuery(
//                    "SELECT l FROM Location l WHERE l.id = :id", Location.class);
//            query.setParameter("id", fromId);
//            try {
//                r.setFrom(query.getSingleResult());
//            } catch (NoResultException e) {
//                throw new NoResultException("There is no \"from\" Location with specified id");
//            }
//
//        }
//        if (toId != null) {
//            var query = em.createQuery(
//                    "SELECT l FROM Location l WHERE l.id = :id", Location.class);
//            query.setParameter("id", toId);
//            try {
//                r.setTo(query.getSingleResult());
//            } catch (NoResultException e) {
//                throw new NoResultException("There is no \"to\" Location with specified id");
//            }
//        }
        em.persist(r);
    }

    public Event getById(Long id) {
        return safeFind(id);
    }
}