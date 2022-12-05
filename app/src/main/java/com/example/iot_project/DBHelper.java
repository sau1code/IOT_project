package com.example.iot_project;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    private static final String database_name = "demo.db";
    private static int Version = 1;

    private String create_memberSQL = "CREATE TABLE member (" +
            "member_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "account TEXT, " +
            "picture BLOB, " +
            "password TEXT, " +
            "name TEXT, " +
            "birthday TEXT, " +
            "phone TEXT, " +
            "email TEXT, " +
            "city TEXT, " +
            "address TEXT, " +
            "bankNumber TEXT, " +
            "bankAccount TEXT, " +
            "createTime DATETIME DEFAULT CURRENT_TIMESTAMP " +
            ");";

//    private String create_sellerSQL = "CREATE TABLE seller (" +
//            "seller_id INTEGER PRIMARY KEY, " +
//            "storeName TEXT, " +
//            "storePicture BLOB, " +
//            "sCountry TEXT, " +
//            "sName TEXT, " +
//            "sBirthday TEXT, " +
//            "IDNumber TEXT, " +
//            "residence TEXT, " +
//            "sCity TEXT, " +
//            "district TEXT, " +
//            "postalCode TEXT, " +
//            "sAddress TEXT, " +
//            "bankName TEXT, " +
//            "bankArea TEXT, " +
//            "bankBranch TEXT, " +
//            "bankNumber TEXT, " +
//            "bankAccount TEXT, " +
//            "sState TEXT, " +
//            "createTime DATETIME DEFAULT CURRENT_TIMESTAMP ," +
//            "sfrist INTEGER DEFAULT 0"+
//            ",FOREIGN KEY (seller_id) REFERENCES member (member_id) ON DELETE CASCADE ON UPDATE CASCADE" +
//            ");";

//    private String create_goodsSQL = "CREATE TABLE goods (" +
//            "goods_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
//            "seller_id INTEGER NOT NULL, " +
//            "gName TEXT, " +
//            "info TEXT DEFAULT '無描述', " +
//            "price INTEGER, " +
//            "type TEXT, " +
//            "weight INTEGER, " +
//            "packageSize TEXT, " +
//            "inventory INTEGER, " +
//            "soldQuantity INTEGER, " +
//            "gState TEXT, " +               // 上架中、已完售、審核中、已違規、已下架
//            "createTime DATETIME DEFAULT (datetime('now','localtime')), " +
//            "FOREIGN KEY (seller_id) REFERENCES seller (seller_id) ON DELETE CASCADE ON UPDATE CASCADE" +
//            ");";


    private String create_pictureSQL = "CREATE TABLE picture (" +
            "picture_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "seller_id INTEGER NOT NULL, " +
            "goods_id INTEGER NOT NULL, " +
            "picture BLOB, " +
            "FOREIGN KEY (seller_id) REFERENCES seller (seller_id) ON DELETE CASCADE ON UPDATE CASCADE," +
            "FOREIGN KEY (goods_id) REFERENCES goods (goods_id) ON DELETE CASCADE ON UPDATE CASCADE" +
            ");";

    private String create_memberGoodsSQL = "CREATE TABLE memberGoods (" +
            "member_id INTEGER, " +
            "goods_id INTEGER, " +
            "favorite INTEGER DEFAULT 0, " +
            "bought INTEGER DEFAULT 0, " +
            "createTime DATETIME DEFAULT CURRENT_TIMESTAMP, " +
            "FOREIGN KEY (member_id) REFERENCES member (member_id) ON DELETE CASCADE ON UPDATE CASCADE," +
            "FOREIGN KEY (goods_id) REFERENCES goods (goods_id) ON DELETE CASCADE ON UPDATE CASCADE," +
            "PRIMARY KEY (member_id, goods_id)" +
            ");";

    private String create_sumSQL = "CREATE TABLE sum (" +
            "member_id INTEGER, " +
            "goods_id INTEGER, " +
            "orders_id INTEGER, " +
            "sum INTEGER, " +
            "FOREIGN KEY (member_id) REFERENCES member (member_id) ON DELETE CASCADE ON UPDATE CASCADE," +
            "FOREIGN KEY (goods_id) REFERENCES goods (goods_id) ON DELETE CASCADE ON UPDATE CASCADE," +
            "FOREIGN KEY (orders_id) REFERENCES orders (orders_id) ON DELETE CASCADE ON UPDATE CASCADE," +
            "PRIMARY KEY (member_id, goods_id)" +
            ");";

    private String create_ordersSQL = "CREATE TABLE orders (" +
            "orders_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "member_id INTEGER, " +
            "payway TEXT, " +
            "pickupWay_id TEXT, " +
            "pickupPlace TEXT, " +
            "orderStatus TEXT, " +          // 待出貨、待收貨、待付款、已完成、不成立
            "invalidReason TEXT, " +
            "refundStatus TEXT, " +         // 審核中、已退款、不成立
            "refundReason TEXT, " +
            "returnStatus TEXT, " +         // 審核中、已退貨、不成立
            "returnReason TEXT, " +
            "payTime DATETIME, " +
            "shippingTime DATETIME, " +
            "pickupTime DATETIME, " +
            "createTime DATETIME DEFAULT CURRENT_TIMESTAMP, " +
            "FOREIGN KEY (member_id) REFERENCES member (member_id) ON DELETE CASCADE ON UPDATE CASCADE," +
            "FOREIGN KEY (pickupWay_id) REFERENCES pickupWay (pickupWay_id) ON UPDATE CASCADE" +
            ");";

    private String create_pickupWaySQL = "CREATE TABLE pickupWay (" +
            "pickupWay_id INTEGER PRIMARY KEY, " +
            "pName TEXT, " +
            "fare INTEGER " +
            ");";

    private String create_goodsPickupSQL = "CREATE TABLE goodsPickup (" +
            "goods_id INTEGER, " +
            "pickupWay_id INTEGER, " +
            "FOREIGN KEY (goods_id) REFERENCES goods (goods_id) ON DELETE CASCADE ON UPDATE CASCADE," +
            "FOREIGN KEY (pickupWay_id) REFERENCES pickupWay (pickupWay_id) ON DELETE CASCADE ON UPDATE CASCADE," +
            "PRIMARY KEY (goods_id, pickupWay_id)" +
            ");";

    private String create_couponSQL = "CREATE TABLE coupon (" +
            "coupon_id TEXT PRIMARY KEY, " +
            "cName TEXT, " +
            "cInfo TEXT, " +
            "expiryTime DATETIME " +
            ");";

    private String create_mamberCouponSQL = "CREATE TABLE mamberCoupon (" +
            "coupon_id TEXT, " +
            "couponCode TEXT, " +
            "member_id INTEGER, " +
            "orders_id INTEGER, " +
            "FOREIGN KEY (coupon_id) REFERENCES coupon (coupon_id) ON DELETE CASCADE ON UPDATE CASCADE," +
            "FOREIGN KEY (member_id) REFERENCES member (member_id) ON UPDATE CASCADE," +
            "FOREIGN KEY (orders_id) REFERENCES orders (orders_id) ON UPDATE CASCADE," +
            "PRIMARY KEY (coupon_id, couponCode)" +
            ");";

    private String insert_pickupWaySQL = "INSERT INTO pickupWay (pickupWay_id, pName, fare) VALUES "+
            "(1, '7-11', 60)," +
            "(2, '全家', 60)," +
            "(3, 'OK', 60)," +
            "(4, '萊爾富', 30)," +
            "(5, '中華郵政', 100)," +
            "(6, '黑貓宅急便', 150)" +
            ";";

    private String insert_couponSQL = "INSERT INTO coupon (coupon_id, cName, cInfo) VALUES "+
            "('A', '0元免運', '使用可免運，訂單金額需大於0元')," +
            "('B', '99元免運', '使用可免運，訂單金額需大於99元')," +
            "('C', '199元免運', '使用可免運，訂單金額需大於199元')," +
            "('D', '299元免運', '使用可免運，訂單金額需大於299元')," +
            "('E', '折30元', '使用整單折30元，訂單須滿299')," +
            "('F', '9.5折', '使用整單打9.5折，訂單須滿1000')," +
            "('G', '8.5折', '使用整單打8.5折，訂單須滿2000')" +
            ";";
    private String create_goodsSQL = "CREATE TABLE goods (" +
            "goods_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "gName TEXT, " +
            "info TEXT DEFAULT '無描述', " +
            "packageLength INTEGER, " +
            "packageWidth INTEGER, " +
            "packageHeight INTEGER, " +
            "inventory INTEGER, " +
            "soldQuantity INTEGER, " +
            "seven INTEGER,"+
            "familyMart INTEGER,"+
            "postOffice INTEGER,"+
            "blackCat INTEGER,"+
            "sevenFee INTEGER,"+
            "familyMartFee INTEGER,"+
            "postOfficeFee INTEGER,"+
            "blackCatFee,"+
            "gState TEXT,"+             // 上架中、已完售、審核中、已違規、已下架
            "createTime DATETIME DEFAULT (datetime('now','localtime'))"
            +");";

    private String goodsSQL_type = "CREATE TABLE goodsType (" +
            "goodsType_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "goods_name TEXT NOT NULL, " +
            "fragType Text,"+
            "type TEXT, " +
            "count INTEGER,"+
            "createTime DATETIME DEFAULT (datetime('now','localtime'))" +
//            "FOREIGN KEY (seller_id) REFERENCES seller (seller_id) ON DELETE CASCADE ON UPDATE CASCADE," +
//            "FOREIGN KEY (good_id) REFERENCES goods (good_id) ON DELETE CASCADE ON UPDATE CASCADE" +
            ");";

    private String goodsSQL_norm = "CREATE TABLE goodsNorm (" +
            "goodsNorm_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "goods_name TEXT NOT NULL, " +
            "fragNum Text,"+
            "price INTEGER, " +
            "norm TEXT, " +
            "normNum INTEGER,"+
            "count INTEGER,"+
            "createTime DATETIME DEFAULT (datetime('now','localtime'))" +
            ");";

    private String goodSQL_picture = "CREATE TABLE goodsPic (" +
            "goodsPicture_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "goods_name TEXT NOT NULL, " +
            "fragPic Text,"+
            "goodsPicture BLOB, " +
            "count INTEGER,"+
            "createTime DATETIME DEFAULT (datetime('now','localtime'))" +
            ");";

    private String create_sellerSQL = "CREATE TABLE seller (" +
            "seller_id INTEGER PRIMARY KEY, " +
            "storeName TEXT, " +
            "storePicture BLOB, " +
            "sCountry TEXT, " +
            "sName TEXT, " +
            "sBirthday TEXT, " +
            "IDNumber TEXT, " +
            "residence TEXT, " +
            "sCity TEXT, " +
            "district TEXT, " +
            "postalCode TEXT, " +
            "sAddress TEXT, " +
            "bankName TEXT, " +
            "bankArea TEXT, " +
            "bankBranch TEXT, " +
            "bankNumber TEXT, " +
            "bankAccount TEXT, " +
            "sState TEXT, " +
            "createTime DATETIME DEFAULT CURRENT_TIMESTAMP" +
//            ",FOREIGN KEY (seller_id) REFERENCES member (member_id) ON DELETE CASCADE ON UPDATE CASCADE" +
            ");";

    public DBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public DBHelper(@Nullable Context context) {
        super(context, database_name, null, Version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(create_memberSQL);
        sqLiteDatabase.execSQL(create_sellerSQL);
        sqLiteDatabase.execSQL(create_goodsSQL);
        sqLiteDatabase.execSQL(create_pictureSQL);
        sqLiteDatabase.execSQL(create_memberGoodsSQL);
        sqLiteDatabase.execSQL(create_sumSQL);
        sqLiteDatabase.execSQL(create_ordersSQL);
        sqLiteDatabase.execSQL(create_pickupWaySQL);
        sqLiteDatabase.execSQL(create_goodsPickupSQL);
        sqLiteDatabase.execSQL(create_couponSQL);
        sqLiteDatabase.execSQL(create_mamberCouponSQL);
        sqLiteDatabase.execSQL(insert_pickupWaySQL);
        sqLiteDatabase.execSQL(insert_couponSQL);

        sqLiteDatabase.execSQL(goodsSQL_type);
        sqLiteDatabase.execSQL(goodsSQL_norm);
        sqLiteDatabase.execSQL(goodSQL_picture);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if(!db.isReadOnly()) {      // Enable foreign key constraints
            db.execSQL("PRAGMA foreign_keys = ON;");
        }
    }

}
