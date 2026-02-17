package dao;

import model.Personaje;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import util.HibernateUtil;

import java.util.List;

public class PersonajeDAO {

    public void guardar(Personaje personaje) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.save(personaje);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }

    public Personaje obtenerPorId(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Personaje.class, id);
        }
    }

    public List<Personaje> listarTodos() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Personaje", Personaje.class).list();
        }
    }

    public void actualizar(Personaje personaje) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.update(personaje);
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
            Personaje p = session.get(Personaje.class, id);
            if (p != null) {
                session.delete(p);
            }
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }

    public List<Personaje> buscarPorRaza(Long razaId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Personaje> query = session.createQuery(
                    "from Personaje p where p.raza.id = :razaId", Personaje.class);
            query.setParameter("razaId", razaId);
            return query.list();
        }
    }

    public List<Personaje> buscarPorRangoNivelPoder(Double min, Double max) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Personaje> query = session.createQuery(
                    "from Personaje p where p.nivelPoder >= :min and p.nivelPoder <= :max", Personaje.class);
            query.setParameter("min", min);
            query.setParameter("max", max);
            return query.list();
        }
    }

    public List<Personaje> buscarPorNombre(String fragmento) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Personaje> query = session.createQuery(
                    "from Personaje p where p.nombre like :fragmento", Personaje.class);
            query.setParameter("fragmento", "%" + fragmento + "%");
            return query.list();
        }
    }

    public List<Personaje> obtenerMasPoderosos(int limite) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Personaje> query = session.createQuery(
                    "from Personaje p order by p.nivelPoder desc", Personaje.class);
            query.setMaxResults(limite);
            return query.list();
        }
    }

    public List<Personaje> obtenerMasAntiguos(int limite) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Personaje> query = session.createQuery(
                    "from Personaje p where p.edad is not null order by p.edad desc", Personaje.class);
            query.setMaxResults(limite);
            return query.list();
        }
    }
}