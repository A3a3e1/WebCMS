/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.com.codefire.cms.bean;

import java.io.IOException;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import ua.com.codefire.cms.dao.entity.User;

/**
 *
 * @author human
 */
@SessionScoped
@ManagedBean(name = "accountBean")
public class AccountBean implements Serializable {

    private int sessionCounter;
    private User user;
    private GregorianCalendar loggedInCalendar;

    public String getLoggedInTime() {
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext externalContext = context.getExternalContext();
        loggedInCalendar = ((GregorianCalendar) externalContext.getSessionMap().get("loggedInTime"));
        return loggedInCalendar.getTime().getHours() + ":" + loggedInCalendar.getTime().getMinutes()
                + ":" + loggedInCalendar.getTime().getSeconds();
    }

    public String getSessionExpires() {
        int sessionTimeSec;
        String resultString;
        Date currentTime = new Date();
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext externalContext = context.getExternalContext();
        loggedInCalendar = ((GregorianCalendar) externalContext.getSessionMap().get("loggedInTime"));
        sessionTimeSec = externalContext.getSessionMaxInactiveInterval();
        long diff = loggedInCalendar.getTime().getTime() - currentTime.getTime() + sessionTimeSec * 1000;
        long diffSeconds = diff / 1000 % 60;
        long diffMinutes = diff / (60 * 1000) % 60;
//        long diffHours = diff / (60 * 60 * 1000) % 24;
//        long diffDays = diff / (24 * 60 * 60 * 1000);
        if (diffSeconds < 0 || diffMinutes < 0) {
            resultString = "Session has expired";
        } else {
            resultString = ((diffMinutes >= 0 && diffMinutes < 10) ? "Session expires in 0" + diffMinutes
                    : (diffMinutes < 0 ? "" : "Session expires in " + diffMinutes)) + ":"
                    + ((diffSeconds >= 0 && diffSeconds < 10) ? "0" + diffSeconds + " minutes"
                    : (diffSeconds < 0 ? "Session has expired!" : diffSeconds + " minutes"));
        }
        return resultString;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void validateUser() throws IOException {
        if (user == null || user.getRole().getPrivileges() >= 2) {
            FacesContext context = FacesContext.getCurrentInstance();
            ExternalContext externalContext = context.getExternalContext();
            externalContext.redirect(externalContext.getRequestContextPath() + "/login.xhtml");
        }
    }

    public void signOut() {
        user = null;
        try {
            validateUser();
        } catch (IOException ex) {
            Logger.getLogger(AccountBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
