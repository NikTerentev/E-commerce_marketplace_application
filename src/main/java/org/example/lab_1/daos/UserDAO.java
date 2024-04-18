package org.example.lab_1.daos;

import org.example.lab_1.exceptions.NotFoundException;
import org.example.lab_1.model.Status;
import org.example.lab_1.model.User;
import org.hibernate.Session;
import org.hibernate.query.Query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import java.util.List;

import static org.example.lab_1.manager.DAO.*;

public class UserDAO {

    public User userLogin(String email, String password) {
        User user = null;

        try {
            Session session = getSession();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<User> query = builder.createQuery(User.class);
            Root<User> root = query.from(User.class);
            query.select(root).where(
                    builder.and(
                            builder.equal(root.get("email"), email),
                            builder.equal(root.get("password"), password)
                    )
            );
            Query<User> q = session.createQuery(query);
            user = q.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return user;
    }

    public static List<User> getAllUsers() {
        Session session = getSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<User> query = builder.createQuery(User.class);
        Root<User> root = query.from(User.class);
        query.select(root);
        Query<User> q = session.createQuery(query);
        List<User> users = q.getResultList();
        return users;
    }

    public static User getSingleUser(int id) {
        User user = null;

        try {
            user = (User) getSession().get(User.class, id);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return user;
    }


    public static void createUser(User user) {
        user.setStatus("OFFLINE");
        begin();
        getSession().save(user);
        commit();
    }

    public static User getUserByNickname(String nickname) {
        User user = null;

        try {
            Session session = getSession();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<User> query = builder.createQuery(User.class);
            Root<User> root = query.from(User.class);
            query.select(root).where(builder.equal(root.get("nickname"), nickname));
            Query<User> q = session.createQuery(query);
            user = q.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return user;
    }


    public static void disconnect(String nickname) {
        // TODO возможно потребуется по nickname искать а не id
        User storedUser = getUserByNickname(nickname);
        if (storedUser != null) {
            storedUser.setStatus("OFFLINE");
            updateUser(storedUser.getId(), storedUser);
        }
    }

    public static void connect(String nickname) {
        User storedUser = getUserByNickname(nickname);
        if (storedUser != null) {
            storedUser.setStatus("ONLINE");
            updateUser(storedUser.getId(), storedUser);
        }
    }

    public static List<User> findConnectedUsers() {
        begin();
        CriteriaBuilder builder = getSession().getCriteriaBuilder();
        CriteriaQuery<User> query = builder.createQuery(User.class);
        Root<User> root = query.from(User.class);
        query.select(root).where(builder.equal(root.get("status").as(String.class), Status.ONLINE.name()));
        Query<User> q = getSession().createQuery(query);
        List<User> users = q.getResultList();
        commit();
        return users;
    }

    public static void saveToken(String nickname, String token) {
        User storedUser = getUserByNickname(nickname);
        if (storedUser != null) {
            storedUser.setToken(token);
            updateUser(storedUser.getId(), storedUser);
        }
    }

    public static void deleteToken(String nickname) {
        User storedUser = getUserByNickname(nickname);
        if (storedUser != null) {
            storedUser.setToken(null);
            updateUser(storedUser.getId(), storedUser);
        }
    }

    public static String getToken(String nickname) {
        User storedUser = getUserByNickname(nickname);
        if (storedUser != null) {
            return storedUser.getToken();
        }
        return null;
    }

    public static void deleteUser(User user) {
        begin();
        getSession().delete(user);
        commit();
    }

    public static User updateUser(int id, User user) {
        Session session = getSession();
        try {
            begin();
            User existingUser = getSingleUser(id);
            if (existingUser != null) {
                existingUser.setName(user.getName());
                existingUser.setNickname(user.getNickname());
                existingUser.setStatus(user.getStatus());
                existingUser.setEmail(user.getEmail());
                existingUser.setPassword(user.getPassword());
                session.update(existingUser);
                commit();
                return existingUser;
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

    public static User partialUpdateUser(int id, User user) {
        Session session = getSession();
        try {
            begin();
            User existingUser = getSingleUser(id);
            if (existingUser != null) {
                if (existingUser.getName() != null) {
                    existingUser.setName(user.getName());
                }
                if (existingUser.getEmail() != null) {
                    existingUser.setEmail(user.getEmail());
                }
                if (existingUser.getPassword() != null) {
                    existingUser.setPassword(user.getPassword());
                }

                session.update(existingUser);
                commit();
                return existingUser;
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
