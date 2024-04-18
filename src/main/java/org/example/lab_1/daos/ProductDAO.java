package org.example.lab_1.daos;

import com.sun.security.auth.UnixNumericUserPrincipal;
import org.example.lab_1.exceptions.NotFoundException;
import org.example.lab_1.manager.DAO;
import org.example.lab_1.model.Cart;
import org.example.lab_1.model.Product;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

import static org.example.lab_1.manager.DAO.*;

@Component
public class ProductDAO {

    /**
     * Возвращает все товары.
     *
     * @return Список со всеми товарами.
     */
    public static List<Product> getAllProducts() {
        Session session = getSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Product> query = builder.createQuery(Product.class);
        Root<Product> root = query.from(Product.class);
        query.select(root);
        Query<Product> q = session.createQuery(query);
        List<Product> products = q.getResultList();
        return products;
    }

    public List<Cart> getCartProducts(ArrayList<Cart> cartList) {
        List<Cart> products = new ArrayList<Cart>();

        try {

            if (!cartList.isEmpty()) {
                Session session = getSession();
                CriteriaBuilder builder = session.getCriteriaBuilder();

                for (Cart item : cartList) {
                    CriteriaQuery<Product> query = builder.createQuery(Product.class);
                    Root<Product> root = query.from(Product.class);
                    query.select(root).where(builder.equal(root.get("id"), item.getId()));
                    Query<Product> q = session.createQuery(query);
                    List<Product> result = q.getResultList();

                    for (Product product : result) {
                        Cart row = new Cart();
                        row.setId(product.getId());
                        row.setName(product.getName());
                        row.setCategory(product.getCategory());
                        row.setPrice(product.getPrice() * item.getQuantity());
                        row.setQuantity(item.getQuantity());
                        products.add(row);
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }

        return products;
    }

    public static Product getSingleProduct(int id) {
        Product product = null;
        try {
            DAO.begin();
            product = (Product) DAO.getSession().get(Product.class, id);
            DAO.commit();
        } catch (Exception e) {
            if (DAO.getSession().getTransaction() != null) {
                DAO.getSession().getTransaction().rollback();
            }
            e.printStackTrace();
        }
        return product;
    }


    public double getTotalCartPrice(ArrayList<Cart> cartList) {
        double sum = 0;

        try {
            if (!cartList.isEmpty()) {
                Session session = getSession();
                CriteriaBuilder builder = session.getCriteriaBuilder();

                for (Cart item : cartList) {
                    CriteriaQuery<Product> query = builder.createQuery(Product.class);
                    Root<Product> root = query.from(Product.class);
                    query.select(root).where(builder.equal(root.get("id"), item.getId()));
                    Query<Product> q = session.createQuery(query);
                    List<Product> result = q.getResultList();

                    for (Product product : result) {
                        sum += product.getPrice() * item.getQuantity();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sum;
    }

    public static void createProduct(Product product) {
        DAO.begin();
        getSession().save(product);
        DAO.commit();
    }

    public static void deleteProduct(Product product) {
        begin();
        getSession().delete(product);
        commit();
    }

    public static Product updateProduct(int id, Product product) {
        Session session = getSession();
        try {
            begin();
            Product existingProduct = getSingleProduct(id);
            if (existingProduct != null) {
                existingProduct.setName(product.getName());
                existingProduct.setCategory(product.getCategory());
                existingProduct.setPrice(product.getPrice());
                existingProduct.setImage(product.getImage());
                existingProduct.setCharacteristics(product.getCharacteristics());
                session.update(existingProduct);
                commit();
                return existingProduct;
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

    public static Product partialUpdateProduct(int id, Product product) {
        Session session = getSession();
        try {
            begin();
            Product existingProduct = getSingleProduct(id);
            if (existingProduct != null) {
                if (product.getName() != null) {
                    existingProduct.setName(product.getName());
                }

                if (product.getCategory() != null) {
                    existingProduct.setCategory(product.getCategory());
                }

                if (product.getPrice() != null) {
                    existingProduct.setPrice(product.getPrice());
                }

                if (product.getImage() != null) {
                    existingProduct.setImage(product.getImage());
                }

                if (product.getCharacteristics() != null) {
                    existingProduct.setCharacteristics(product.getCharacteristics());
                }
                session.update(existingProduct);
                commit();
                return existingProduct;
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
