/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.com.codefire.cms.bean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.persistence.EntityManager;
import org.springframework.jdbc.core.StatementCreatorUtils;
import ua.com.codefire.cms.dao.UsersJPA;
import ua.com.codefire.cms.dao.entity.User;

/**
 *
 * @author human
 */
@SessionScoped
@ManagedBean(name = "fake", eager = true)
public class FakeLoginBean {

    public User getUser() {
        EntityManager manager = UsersJPA.getInstance().getManager();

        try {
            return manager.createNamedQuery("User.findById", User.class)
                    .setParameter("id", 1).getSingleResult();
        } finally {
            manager.close();
        }
    }
}
