/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.com.codefire.cms.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import org.springframework.web.context.ContextLoader;
import ua.com.codefire.cms.dao.entity.Account;
import ua.com.codefire.cms.dao.entity.Role;
import ua.com.codefire.cms.dao.entity.User;

/**
 *
 * @author human
 */
public class UsersJPA extends JPA {

    public static UsersJPA getInstance() {
        return ContextLoader.getCurrentWebApplicationContext().getBean(UsersJPA.class);
    }

    public List<User> getAllUsers() {
        EntityManager manager = getManager();

        try {
            return manager.createNamedQuery("User.findAll", User.class)
                    .getResultList();
        } finally {
            manager.close();
        }
    }

    public List<Account> getAllAccounts() {
        EntityManager manager = getManager();

        try {
            return manager.createNamedQuery("Account.findAll", Account.class)
                    .getResultList();
        } finally {
            manager.close();
        }
    }

    public List<Role> getAllRoles() {
        EntityManager manager = getManager();

        try {
            return manager.createNamedQuery("Role.findAll", Role.class)
                    .getResultList();
        } finally {
            manager.close();
        }
    }

    public User getUserByLogin(String userName, String pass) {
        EntityManager em = getManager();

        try {
            return (User) em.createQuery("SELECT u FROM User u WHERE u.name = :name AND u.password = :pass")
                    .setParameter("name", userName).setParameter("pass", pass).getSingleResult();
        } catch (NoResultException ex) {
            return null;
        } finally {
            em.close();
        }
    }    
}
