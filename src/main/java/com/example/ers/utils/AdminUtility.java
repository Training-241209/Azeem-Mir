package com.example.ers.utils;

import com.example.ers.models.User;
//utlilty class for admin verification for various admin ony functions 
//reduces redundant code in controller layer    

public class AdminUtility {
    public static boolean isAdmin(User user) {
        return (user != null && user.getRole().getRoleName().equalsIgnoreCase("admin")); 
    }
}
