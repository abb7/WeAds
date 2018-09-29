package com.example.odatem.weadds;

import static java.lang.String.valueOf;

//@IgnoreExtraProperties
public class User {

    private String id;
    private String name;
    private String intreset;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String p_id,String P_name, String p_interest) {
        setId(p_id);
        setName(P_name);
        setInterest(p_interest);
    }

    private void setId(String x){
        this.id = x;
    }
    private void setInterest(String y){
        this.intreset = y;
    }
    private void setName(String z){
        this.name = z;
    }

    public String getId(){
        return valueOf(id);
    }
    public String getName(){
        return valueOf(name);
    }
    public String getIntreset(){
        return valueOf(intreset);
    }



}
