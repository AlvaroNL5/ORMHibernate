package dao;

import model.Raza;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import util.HibernateUtil;

import java.util.List;

public class RazaDAO {

    public void guardar(Raza raza) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.save(raza);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }

    public Raza obtenerPorId(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Raza.class, id);
        }
    }

    public List<Raza> listarTodas() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Raza", Raza.class).list();
        }
    }

    public void actualizar(Raza raza) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.update(raza);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }

    public void eliminar(Long id) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            Raza raza = session.get(Raza.class, id);
            if (raza != null) {
                session.delete(raza);
            }
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }

    public List<Raza> buscarPorReino(String reino) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Raza> query = session.createQuery(
                    "from Raza r where r.reinoOrigen = :reino", Raza.class);
            query.setParameter("reino", reino);
            return query.list();
        }
    }

    public Raza obtenerConPersonajes(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Raza> query = session.createQuery(
                    "select r from Raza r left join fetch r.personajes where r.id = :id", Raza.class);
            query.setParameter("id", id);
            return query.uniqueResult();
        }
    }
}