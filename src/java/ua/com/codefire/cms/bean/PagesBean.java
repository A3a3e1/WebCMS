/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.com.codefire.cms.bean;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;
import javax.persistence.EntityManager;
import org.primefaces.context.RequestContext;
import ua.com.codefire.cms.dao.PagesJPA;
import ua.com.codefire.cms.dao.entity.Page;

/**
 *
 * @author cfuser
 */
@SessionScoped
@ManagedBean(name = "pages")
public class PagesBean implements Serializable {

    private static final Logger LOG = Logger.getLogger(PagesBean.class.getName());

    private Page editedPage;

    public List<Page> getAllPages() {
        return PagesJPA.getInstance().getAllPages();
    }

    public Page getEditedPage() {
        return editedPage;
    }

    public void setEditedPage(Page editedPage) {
        this.editedPage = editedPage;
    }
    
    public void addPageAction(ActionEvent ae) {
        editedPage = new Page();
        RequestContext.getCurrentInstance().execute("PF('addDialog').show();");
    }
    
    public void addNewPage(ActionEvent ae) {
        EntityManager manager = PagesJPA.getInstance().getManager();
        LOG.log(Level.INFO, "ADD");
        try {
            manager.getTransaction().begin();
            manager.persist(editedPage);
            manager.getTransaction().commit();
        } finally {
            manager.close();
        }
    }

    public void saveEditedPage(ActionEvent ae) {
        EntityManager manager = PagesJPA.getInstance().getManager();
        LOG.log(Level.INFO, "SAVE");
        try {
            manager.getTransaction().begin();
            manager.merge(editedPage);
            manager.getTransaction().commit();
        } finally {
            manager.close();
        }
    }

    public void deleteEditedPage(ActionEvent ae) {
        EntityManager manager = PagesJPA.getInstance().getManager();
        LOG.log(Level.INFO, "DELETE");
        try {
            manager.getTransaction().begin();
            manager.remove(manager.merge(editedPage));
            manager.getTransaction().commit();
        } finally {
            manager.close();
        }
    }
}
