package com.example.ease;
import com.google.firebase.database.IgnoreExtraProperties;
@IgnoreExtraProperties
public class User {
    public String fullname;
    public String emailaddress;
    public String phone;
    public User(){
    }
    public User(String fullname,String emailaddress, String phone){
        this.fullname = fullname;
        this.emailaddress = emailaddress;
        this.phone = phone;
    }
    public String getFullname(){
        return fullname;
    }
    public void setFullname(String fullname){
        this.fullname = fullname;
    }
    public String getEmailaddress(){
        return emailaddress;

    }
    public void setEmailaddress(String emailaddress){
        this.emailaddress = emailaddress;
    }
    public String getPhone(){
        return phone;
    }
    public void setPhone(String phone){
        this.phone = phone;
    }

}









