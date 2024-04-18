package org.example.lab_1.daos;

import org.example.lab_1.exceptions.NotFoundException;
import org.example.lab_1.model.Characteristics;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

import static org.example.lab_1.manager.DAO.*;

@Component
public class CharacteristicsDAO {
    public static List<Characteristics> getAllCharacteristics() {
        Session session = getSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Characteristics> query = builder.createQuery(Characteristics.class);
        Root<Characteristics> root = query.from(Characteristics.class);
        query.select(root);
        Query<Characteristics> q = session.createQuery(query);
        List<Characteristics> characteristics = q.getResultList();
        return characteristics;
    }

    public static Characteristics getSingleCharacteristics(int id) {
        Characteristics characteristics = null;

        try {
            begin();
            characteristics = (Characteristics) getSession().get(Characteristics.class, id);
        } catch (Exception e) {
            if (getSession().getTransaction() != null) {
                getSession().getTransaction().rollback();
            }
            e.printStackTrace();
        }
        commit();
        return characteristics;
    }

    public static void createCharacteristics(Characteristics characteristics) {
        begin();
        getSession().save(characteristics);
        commit();
    }

    public static void deleteCharacteristics(Characteristics characteristics) {
        begin();
        getSession().delete(characteristics);
        commit();
    }

    public static Characteristics updateCharacteristics(int id, Characteristics characteristics) {
        Session session = getSession();
        try {
            begin();
            Characteristics existingCharacteristics = getSingleCharacteristics(id);
            if (existingCharacteristics != null) {
                existingCharacteristics.setReleaseDate(characteristics.getReleaseDate());
                existingCharacteristics.setRamVolume(characteristics.getRamVolume());
                existingCharacteristics.setStorageVolume(characteristics.getStorageVolume());
                existingCharacteristics.setDiagonal(characteristics.getDiagonal());
                existingCharacteristics.setProcessor(characteristics.getProcessor());
                existingCharacteristics.setWeight(characteristics.getWeight());
                session.update(existingCharacteristics);
                commit();
                return existingCharacteristics;
            } else {
                throw new NotFoundException();
            }
        } catch (Exception e) {
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
            e.printStackTrace();
            throw e;
        }
    }

    public static Characteristics partialUpdateCharacteristics(int id, Characteristics characteristics) {
        Session session = getSession();
        try {
            begin();
            Characteristics existingCharacteristics = getSingleCharacteristics(id);
            if (existingCharacteristics != null) {
                if (characteristics.getReleaseDate() != null) {
                    existingCharacteristics.setReleaseDate(characteristics.getReleaseDate());
                }
                if (characteristics.getRamVolume() != null) {
                    existingCharacteristics.setRamVolume(characteristics.getRamVolume());
                }
                if (characteristics.getStorageVolume() != null) {
                    existingCharacteristics.setStorageVolume(characteristics.getStorageVolume());
                }
                if (characteristics.getDiagonal() != null) {
                    existingCharacteristics.setDiagonal(characteristics.getDiagonal());
                }
                if (characteristics.getProcessor() != null) {
                    existingCharacteristics.setProcessor(characteristics.getProcessor());
                }
                if (characteristics.getProcessor() != null) {
                    existingCharacteristics.setWeight(characteristics.getWeight());
                }
                session.update(existingCharacteristics);
                commit();
                return existingCharacteristics;
            } else {
                throw new NotFoundException();
            }
        } catch (Exception e) {
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
            e.printStackTrace();
            throw e;
        }
    }
}
