package com.example.iot_project;

import java.util.HashMap;
import java.util.Map;

public class Member {
    private String account_name;
    private String bankAccount;
    private String bankNumber;
    private String birthday;
    private String createTime;
    private String email;
    private boolean is_seller;
    private String name;
    private String password;
    private String phone;
    private String picture;

    Member(){}

    public String getBirthday() {
        return birthday;
    }

    public String getPassword() {
        return password;
    }

    public String getAccount_name() {
        return account_name;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public String getBankNumber() {
        return bankNumber;
    }

    public String getCreateTime() {
        return createTime;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getPicture() {
        return picture;
    }

    public Map<String,Object> ToMap(){

        Map<String,Object> map = new HashMap<>();
        map.put("account_name",account_name);
        map.put("bankAccount",bankAccount);
        map.put("bankNumber",bankNumber);
        map.put("birthday",birthday);
        map.put("createTime",createTime);
        map.put("email",email);
        map.put("is_seller",is_seller);
        map.put("name",name);
        map.put("password",password);
        map.put("phone",phone);
        map.put("picture",picture);
        return map;

    }
}
