package com.example.iot_project;

import java.util.HashMap;
import java.util.Map;

public class Seller {


    private String IDNumber;
    private String bankAccount;
    private String bankArea;
    private String bankBranch;
    private String bankName;
    private String bankNumber;
    private String district;
    private String postalCode;
    private String sAddress;
    private String sBirthday;
    private String sCity;
    private String sCountry;
    private String sName;
    private String sState;
    private String seller_id;
    private String storeName;
    private String storePicture;
    private String createTime;

    public Seller() {
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public String getBankArea() {
        return bankArea;
    }

    public String getsBirthday() {
        return sBirthday;
    }

    public String getBankBranch() {
        return bankBranch;
    }

    public String getBankName() {
        return bankName;
    }

    public String getBankNumber() {
        return bankNumber;
    }

    public String getDistrict() {
        return district;
    }

    public String getIDNumber() {
        return IDNumber;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getsAddress() {
        return sAddress;
    }

    public String getsCity() {
        return sCity;
    }

    public String getsCountry() {
        return sCountry;
    }

    public String getSeller_id() {
        return seller_id;
    }

    public String getsName() {
        return sName;
    }

    public String getsState() {
        return sState;
    }

    public String getStoreName() {
        return storeName;
    }

    public String getStorePicture() {
        return storePicture;
    }

    public String getCreateTime() {
        return createTime;
    }

    public Map<String,Object> ToMap(){

        Map<String,Object> map = new HashMap<>();
        map.put("IDNumber",IDNumber);
        map.put("bankAccount",bankAccount);
        map.put("bankArea",bankArea);
        map.put("bankName",bankName);
        map.put("bankNumber",bankNumber);
        map.put("district",district);
        map.put("postalCode",postalCode);
        map.put("sAddress",sAddress);
        map.put("sBirthday",sBirthday);
        map.put("sCity",sCity);
        map.put("sCountry",sCountry);
        map.put("sName",sName);
        map.put("sState",sState);
        map.put("seller_id",seller_id);
        map.put("storeName",storeName);
        map.put("storePicture",storePicture);
        map.put("createTime",createTime);
        return map;

    }

}
