/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.com.codefire.cms.bean;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.GregorianCalendar;
import java.util.Properties;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import org.primefaces.context.RequestContext;
import ua.com.codefire.cms.dao.UsersJPA;
import ua.com.codefire.cms.dao.entity.User;

/**
 *
 * @author cfuser
 */
@RequestScoped
@ManagedBean(name = "loginBean")
public class LoginBean implements Serializable {

    @ManagedProperty(value = "#{accountBean}")
    private AccountBean accountBean;
    
    private String login;
    private String pass;
    
    public AccountBean getAccountBean() {
        return accountBean;
    }

    public void setAccountBean(AccountBean accountBean) {
        this.accountBean = accountBean;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public void loginButton(ActionEvent ae) throws FileNotFoundException, IOException {
        User user = UsersJPA.getInstance().getUserByLogin(login, pass);
        accountBean.setUser(user);
        
        // Start log tests ...
        
            String path = "stats.txt";
            File file = new File(path);
//            if (!file.exists()) {
//                FileOutputStream logLocalFile = new FileOutputStream(path);
//            }
            
            byte [] some = {65, 66, 67, 68, 69, 70};
            FileOutputStream fos = new FileOutputStream(path);
            fos.write(some);
            fos.close();
        
//            FileInputStream inputFis = new FileInputStream(path);
//            Properties inputDump = new Properties();
//            inputDump.load(inputFis);
        
        //...till here
        
        
        if (user == null) {
            FacesContext currentInstance = FacesContext.getCurrentInstance();
            currentInstance.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ошибка", "Неправильный логин или пароль!!!"));
//            login = "";
            pass = "";
//            RequestContext contest = RequestContext.getCurrentInstance();
//            contest.update("loginForm");
        }
    }

    public void validateAuth() throws IOException {
        if (accountBean.getUser() != null && accountBean.getUser().getRole().getPrivileges() < 2) {
            FacesContext context = FacesContext.getCurrentInstance();
            ExternalContext externalContext = context.getExternalContext();
            externalContext.getSessionMap().put("loggedInTime", new GregorianCalendar());
            externalContext.redirect(externalContext.getRequestContextPath() + "/admin/");
        }
    }
}
