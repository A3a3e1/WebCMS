/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ua.com.codefire.cms.bean;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;
import javax.persistence.EntityManager;
import org.primefaces.context.RequestContext;
import ua.com.codefire.cms.dao.UsersJPA;
import ua.com.codefire.cms.dao.entity.Role;

/**
 *
 * @author A3a3e1
 */
@SessionScoped
@ManagedBean(name = "roles", eager = true)
public class RolesBean implements Serializable {
    
    private Role editedRole;
    private Role addedRole;
    private Role removedRole;
    
    public List<Role> getAllRoles(){
        return UsersJPA.getInstance().getAllRoles();
    }
    
    public void editAction(ActionEvent ae) {
        Role role = (Role) ae.getComponent().getAttributes().get("roles");
        editedRole = role;
        RequestContext.getCurrentInstance().execute("PF('editDialog').show();");
    }
    
    public void addAction(ActionEvent ae) {
        addedRole = new Role(1, "", 3);
        RequestContext.getCurrentInstance().execute("PF('addDialog').show();");
    }
    
    public void removeAction(ActionEvent ae) {
        Role role = (Role) ae.getComponent().getAttributes().get("remove_roles");
        removedRole = role;
        RequestContext.getCurrentInstance().execute("PF('removeDialog').show();");
    }
    
    public void saveEditedRole(ActionEvent ae) {
        EntityManager manager = UsersJPA.getInstance().getManager();

        try {
            manager.getTransaction().begin();
            manager.merge(editedRole);
            manager.getTransaction().commit();
        } finally {
            manager.close();
        }
    }
    
    public void addRole(ActionEvent ae) {
        EntityManager manager = UsersJPA.getInstance().getManager();

        try {
            manager.getTransaction().begin();
            manager.persist(addedRole);
            manager.getTransaction().commit();
        } finally {
            manager.close();
        }
    }

    public void removeRole(ActionEvent ae) {
        EntityManager manager = UsersJPA.getInstance().getManager();
        
        try {
            manager.getTransaction().begin();
            removedRole = manager.find(Role.class, removedRole.getId());
            manager.remove(removedRole);
            manager.getTransaction().commit();
        } finally {
            manager.close();
        }
    }
    
    public Role getAddedRole() {
        return addedRole;
    }

    public void setAddedRole(Role addedRole) {
        this.addedRole = addedRole;
    }

    public Role getEditedRole() {
        return editedRole;
    }

    public void setEditedRole(Role editedRole) {
        this.editedRole = editedRole;
    }
    
        public Role getRemovedRole() {
        return removedRole;
    }

    public void setRemovedRole(Role removedRole) {
        this.removedRole = removedRole;
    }

}
