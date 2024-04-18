package org.example.lab_1.daos;

import org.example.lab_1.exceptions.NotFoundException;
import org.example.lab_1.manager.DAO;
import org.example.lab_1.model.Order;
import org.hibernate.Session;
import org.hibernate.query.Query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

import static org.example.lab_1.manager.DAO.*;

public class OrderDAO {
    public boolean insertOrder(Order model) {
        try {
            begin();
            getSession().save(model);
            DAO.commit();
            return true;
        } catch (Exception e) {
            if (getSession().getTransaction() != null) {
                getSession().getTransaction().rollback();
            }
            e.printStackTrace();
        }
        return false;
    }

    public List<Order> userOrders(int id) {
        List<Order> list = new ArrayList<>();
        try {
            begin();
            String hql = "FROM Order WHERE uid = :user_id ORDER BY orderId DESC";
            Query query = getSession().createQuery(hql);
            query.setParameter("user_id", id);
            list = query.list();
            DAO.commit();
        } catch (Exception e) {
            if (getSession().getTransaction() != null) {
                getSession().getTransaction().rollback();
            }
            e.printStackTrace();
        }
        return list;
    }


    public void cancelOrder(int id) {
        try {
            begin();
            String hql = "DELETE Order WHERE orderId = :order_id";
            Query query = getSession().createQuery(hql);
            query.setParameter("order_id", id);
            int result = query.executeUpdate();
            DAO.commit();
        } catch (Exception e) {
            if (getSession().getTransaction() != null) {
                getSession().getTransaction().rollback();
            }
            e.printStackTrace();
        }
    }

    public static List<Order> getAllOrders() {
        Session session = getSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Order> query = builder.createQuery(Order.class);
        Root<Order> root = query.from(Order.class);
        query.select(root);
        Query<Order> q = session.createQuery(query);
        List<Order> orders = q.getResultList();
        return orders;
    }

    public static Order getSingleOrder(int id) {
        Order order = null;

        try {
            begin();
            order = (Order) getSession().get(Order.class, id);
        } catch (Exception e) {
            if (getSession().getTransaction() != null) {
                getSession().getTransaction().rollback();
            }
            e.printStackTrace();
        }
        DAO.commit();
        return order;
    }

    public static void createOrder(Order order) {
        begin();
        getSession().save(order);
        commit();
    }

    public static void deleteOrder(Order order) {
        begin();
        getSession().delete(order);
        commit();
    }

    public static Order updateOrder(int id, Order order) {
        Session session = getSession();
        try {
            begin();
            Order existingOrder = getSingleOrder(id);
            if (existingOrder != null) {
                existingOrder.setPid(order.getPid());
                existingOrder.setUid(order.getUid());
                existingOrder.setQuantity(order.getQuantity());
                existingOrder.setDate(order.getDate());
                session.update(existingOrder);
                commit();
                return existingOrder;
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

    public static Order partialUpdateOrder(int id, Order order) {
        Session session = getSession();
        try {
            begin();
            Order existingOrder = getSingleOrder(id);
            if (existingOrder != null) {
                if (order.getPid() != null) {
                    existingOrder.setPid(order.getPid());
                }
                if (order.getUid() != 0) {
                    existingOrder.setUid(order.getUid());
                }
                if (order.getQuantity() != 0) {
                    existingOrder.setQuantity(order.getQuantity());
                }
                if (order.getDate() != null) {
                    existingOrder.setDate(order.getDate());
                }

                session.update(existingOrder);
                commit();
                return existingOrder;
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
