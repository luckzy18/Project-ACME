package org.example.User;

public class Teller {
    private String id;
    private String first_name;
    private String last_name;
    private String password;
    private boolean loggedIn=false;


   public Teller(String id, String first_name,String last_name){
       this.id=id;
       this.first_name=first_name;
       this.last_name=last_name;
       this.loggedIn=true;
   }
   public Teller(){}


    public boolean isLoggedIn(){
       return loggedIn;
    }


}
