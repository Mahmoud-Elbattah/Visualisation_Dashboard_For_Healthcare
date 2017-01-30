package Temp;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import java.util.UUID;

import javax.faces.event.ActionListener;

/**
 *
 * @author 13232238
 */
@ManagedBean
@RequestScoped
public class IndexBean {

   private String uuid;
 
    /**
     * {@link ActionListener} implementation that generates a random UUID and
     * sets the value in the bean. The returned value can be found by calling
     * {@link #getUuid()}.
     */
    public void generateUUID() {
        try {
            // Add this so that we have a delay for the AJAX spinner.
            Thread.sleep(2000);
            uuid = UUID.randomUUID().toString();
        } catch (InterruptedException ignore) {
        }
    }
 
    /**
     * Getter for UUID.
     *
     * @return return {@link UUID#toString()}.
     */
    public String getUuid() {
        return uuid;
    }
    
}
