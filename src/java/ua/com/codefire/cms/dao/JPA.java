/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.com.codefire.cms.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.springframework.web.context.ContextLoader;

/**
 *
 * @author human
 */
public class JPA {

    public static JPA getInstance() {
        return ContextLoader.getCurrentWebApplicationContext().getBean(JPA.class);
    }

    private EntityManagerFactory factory;

    public EntityManagerFactory getFactory() {
        return factory;
    }

    public void setFactory(EntityManagerFactory factory) {
        this.factory = factory;
    }

    public EntityManager getManager() {
        return factory.createEntityManager();
    }
}
