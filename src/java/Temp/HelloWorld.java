package Temp;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import Temp.Message;
import entityclasses.Carehome;
import entityclasses.Summarycounts;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

@ManagedBean(name = "helloWorld", eager = false)
@RequestScoped
public class HelloWorld {

   @ManagedProperty(value="#{message}")
   private Message messageBean;

   private Integer message;

   public HelloWorld() {
      System.out.println("HelloWorld started!");  
   

           this.message = 550; 

  
   }
   public Integer getMessage() {
     // if(messageBean != null){
     //    message = messageBean.getMessage();
     // }       
      return message;
   }
   public void setMessageBean(Message message) {
      this.messageBean = message;
   }
}