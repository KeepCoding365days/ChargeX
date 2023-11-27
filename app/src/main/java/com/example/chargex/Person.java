package com.example.chargex;

import java.util.Date;

public class Person {
    private String name;
    private String contactNumber;
    private String email;
    private String password;
    private String dateOfBirth;
    private String cnic;

    public Person(){
        name="";
        email="";
        contactNumber="";
        password="";
        dateOfBirth="";
        cnic="";
    }
    public void setName(String name){
        this.name=name;
    }
    public void setEmail(String email){
        this.email=email;
    }
    public void setNumber(String number){
        this.contactNumber=number;
    }
    public void setPassword(String pw){
        this.password=Encrypt(pw);
    }
    public void setDoB(String DoB){
        this.dateOfBirth=DoB;
    }
    public void setCNIC(String cnic){
        this.cnic=cnic;
    }

    public String getName(){
        return this.name;
    }
    public String getEmail(){
        return this.email;
    }
    public String getContactNumber(){
        return this.contactNumber;
    }
    public String getCnic(){
        return this.cnic;
    }
    public String getDateOfBirth(){
        return this.dateOfBirth;
    }
    public String getPassword(){
        return this.password;
    }

    public String Encrypt(String pw){
        char[] arr=pw.toCharArray();
        for(int i=0;i<arr.length;i++){
            arr[i]=(char)(((int) arr[i])+2);
        }
        pw=arr.toString();
        return pw;
    }

    public String Decrypt(String pw){
        char[] arr=pw.toCharArray();
        for(int i=0;i<arr.length;i++){
            arr[i]=(char)(((int) arr[i])-2);
        }
        pw=arr.toString();
        return pw;
    }



}
