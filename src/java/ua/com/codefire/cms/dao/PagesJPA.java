/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ua.com.codefire.cms.dao;

import java.util.List;
import javax.persistence.EntityManager;
import org.springframework.web.context.ContextLoader;
import ua.com.codefire.cms.dao.entity.Page;

/**
 *
 * @author human
 */
public class PagesJPA extends JPA {

    public static PagesJPA getInstance() {
        return ContextLoader.getCurrentWebApplicationContext().getBean(PagesJPA.class);
    }
    
    public List<Page> getAllPages() {
        EntityManager manager = getManager();
        
        try {
            return manager.createNamedQuery("Page.findAll", Page.class)
                    .getResultList();
        } finally {
            manager.close();
        }
    }
}
