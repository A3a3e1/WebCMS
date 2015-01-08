/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ua.com.codefire.cms.bean;

import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;
import javax.persistence.EntityManager;
import org.primefaces.context.RequestContext;
import ua.com.codefire.cms.dao.UsersJPA;
import ua.com.codefire.cms.dao.entity.User;

/**
 *
 * @author A3a3e1
 */
@SessionScoped
@ManagedBean(name = "users", eager = true)
public class UsersBean implements Serializable {
    
    private User editedUser;
    private User addedUser;
    private User removedUser;
    
    public List<User> getAllUsers(){
        return UsersJPA.getInstance().getAllUsers();
    }
    
    public void editAction(ActionEvent ae) {
        User user = (User) ae.getComponent().getAttributes().get("users");
        editedUser = user;
        RequestContext.getCurrentInstance().execute("PF('editDialog').show();");
    }
    
    public void addAction(ActionEvent ae) {
        addedUser = new User(1, "", "");
        RequestContext.getCurrentInstance().execute("PF('addDialog').show();");
    }
    
    public void removeAction(ActionEvent ae) {
        User user = (User) ae.getComponent().getAttributes().get("remove_users");
        removedUser = user;
        RequestContext.getCurrentInstance().execute("PF('removeDialog').show();");
    }
    
    public void saveEditedUser(ActionEvent ae) {
        EntityManager manager = UsersJPA.getInstance().getManager();

        try {
            manager.getTransaction().begin();
            manager.merge(editedUser);
            manager.getTransaction().commit();
        } finally {
            manager.close();
        }
    }
    
    public void addUser(ActionEvent ae) {
        EntityManager manager = UsersJPA.getInstance().getManager();

        try {
            manager.getTransaction().begin();
            manager.persist(addedUser);
            manager.getTransaction().commit();
        } finally {
            manager.close();
        }
    }

    public void removeUser(ActionEvent ae) {
        EntityManager manager = UsersJPA.getInstance().getManager();
        
        try {
            manager.getTransaction().begin();
            removedUser = manager.find(User.class, removedUser.getId());
            manager.remove(removedUser);
            manager.getTransaction().commit();
        } finally {
            manager.close();
        }
    }
    
    public User getAddedUser() {
        return addedUser;
    }

    public void setAddedUser(User addedUser) {
        this.addedUser = addedUser;
    }

    public User getEditedUser() {
        return editedUser;
    }

    public void setEditedUser(User editedUser) {
        this.editedUser = editedUser;
    }
    
        public User getRemovedUser() {
        return removedUser;
    }

    public void setRemovedUser(User removedUser) {
        this.removedUser = removedUser;
    }

}
