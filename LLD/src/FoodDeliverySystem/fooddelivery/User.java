package fooddelivery;

import java.util.*;

public class User{

    private final String name;
    private final String phone;
    private final Strin pincode;
    private final String userId;
    private final String gender;
    private final List<String>orderIds = new ArrayList<>();

    public User(String name, String phone, String gender, String pincode){
        this.userId = UUID.randomUUID().toString();
        this.name = name;
        this.phone = phone;
        this.pincode = pincode;
        this.gender = gender;
    }

    public String getName(){
        return name;
    }

    public String getPincode(){
        return pincode;
    }

    public String getPhone(){
        return phone;
    }

    public List<String> getOrderIds(){
        return orderIds;
    }

    public String getUserId() { 
        return userId; 
    }
}

