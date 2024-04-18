package org.example.lab_1.daos;

import org.example.lab_1.exceptions.NotFoundException;
import org.example.lab_1.manager.DAO;
import org.example.lab_1.model.Category;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

import static org.example.lab_1.manager.DAO.*;

@Component
public class CategoryDAO {

    public static List<Category> getAllCategories() {
        Session session = getSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Category> query = builder.createQuery(Category.class);
        Root<Category> root = query.from(Category.class);
        query.select(root);
        Query<Category> q = session.createQuery(query);
        List<Category> categories = q.getResultList();
        return categories;
    }

    public static Category getSingleCategory(int id) {
        Category category = null;

        try {
            begin();
            category = (Category) getSession().get(Category.class, id);
        } catch (Exception e) {
            if (getSession().getTransaction() != null) {
                getSession().getTransaction().rollback();
            }
            e.printStackTrace();
        }
        commit();
        return category;
    }

    public static void createCategory(Category category) {
        begin();
        getSession().save(category);
        commit();
    }

    public static void deleteCategory(Category category) {
        begin();
        getSession().delete(category);
        commit();
    }

    public static Category updateCategory(int id, Category category) {
        Session session = getSession();
        try {
            begin();
            Category existingCategory = getSingleCategory(id);
            if (existingCategory != null) {
                existingCategory.setName(category.getName());
                session.update(existingCategory);
                commit();
                return existingCategory;
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

    public static Category partialUpdateCategory(int id, Category category) {
        Session session = getSession();
        try {
            begin();
            Category existingCategory = getSingleCategory(id);
            if (existingCategory != null) {
                if (category.getName() != null) {
                    existingCategory.setName(category.getName());
                }
                session.update(existingCategory);
                commit();
                return existingCategory;
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
