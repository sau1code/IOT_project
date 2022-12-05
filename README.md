# 購物商城 APP
> 勞動力發展署桃竹苗分署 幼獅訓練場 - 111年物聯網開發與行動裝置應用(Android、Python、Embedded System) 01 期 
> 
> 方士豪、張予馨、詹翔雲 專題成果

## 第一章 動機與目的
現代常以購買物品為目的，而在手機下載商家的App，方便即時確認新商品的清單，於此專題目的為以購物為主要功能建立App，並針對使用者身分提供不同功能，且即時更新使用者與商品資訊，以研究常用App的建立架構。

## 第二章 所需材料
1.	平台：Android
    * Minimum SDK：API 26 Android 8.0
    * Emulator：Pixel 5 API 26
    * 實機測試：ASUS ZenFone7 Android 10 API 29
2.	程式語言：Java
3.	資料庫：SQLite 3.18.2 , FireBase Realtime Database	20.0.6	
4.	以git和github平台實現多人協作專案。
5.	資料庫欄位設計：放在附錄SQL13.xlsx，並附上與資料表相同名稱的JSON格式檔案。

## 第三章 流程圖
不同身分別使用不同App功能

<img width="493" alt="身分別切換" src="https://user-images.githubusercontent.com/37395516/197220772-12cd6cd4-3e0f-4489-a538-fe4c8c4e9d87.png">

## 第四章 實驗結果

> | 進入App | 首頁 | 搜尋商品 | 會員登入 | 商品加入購物車 | 商品按讚功能 | 
> | --- |  --- | --- | --- | --- | --- |
>
> https://user-images.githubusercontent.com/37395516/197215075-64df4e95-39b2-42b5-83c7-33281862683c.mp4


>
> | 購物車功能 | 
> | --- |
>
> https://user-images.githubusercontent.com/37395516/197215118-602518b8-d487-4cb7-a9ed-01325880c5cb.mp4

>
> | 修改會員資料 | 會員註冊 | 會員登出 |
> | --- | --- | --- |
>
> https://user-images.githubusercontent.com/37395516/197212496-60b66356-4e19-44be-805e-6c8dff03285e.mp4

>
> | 首頁會員側拉選單 | 註冊成為賣家 |
> | --- | --- | 
>
> https://user-images.githubusercontent.com/37395516/197212538-e791fdd0-82ce-4b8a-8a08-27a62a12dd19.mp4

>
> | 管理者登入 | 
> | --- |
>
> https://user-images.githubusercontent.com/37395516/197212584-0c67dce6-3b85-4bab-9968-0eb79c4485e2.mp4

>
> | 管理會員 | 管理賣家 | 管理商品 | 管理者登出 |
> | --- | --- | --- | --- |
>
> https://user-images.githubusercontent.com/37395516/197212621-a0ae0c2a-eae6-4522-a6e0-3b50cd688119.mp4

## 第五章 未來方向

這次專題有許多邊做邊學的功能，因此在短短一個半月內，未如期達成所有目標，不過慶幸的是，在我們在這學習過程能力的提升下，待完成的目標較少有需未知的技術，所以只要再一些時間繼續努力，相信這個由大家一起努力實踐的成果，可以變得更好!

以下列出現階段未完成與或許可附加的功能:
1.	開啟APP
    * 增加入口動畫。
2.	登入
    * 串接Google帳號登入 api。
3.	大廳
    * 篩選排序功能。
    * 增加底部導覽列。
4.	會員
    * 訂單回顧(待付款、待出貨、代收貨、已完成) ，與firebaset串接。
    * 商品回顧(按讚、看過、買過)，與firebase串接。
    * 我的優惠券頁面的領取與展示功能。
    * 完成判斷使用者是否有賣家身分，隱藏成為賣家與顯示我的賣場。
5.	賣家
    * 銷售紀錄(待出貨、不成立、退貨退款、已出貨、已完成) ，與firebase串接。
    * 完成銷售紀錄頁面的關鍵字查詢功能。
    * 我的商品(架上、售完、審核中、已違規) ，與firebase串接。
    * 我的商品下架與編輯功能，與firebase串接。
    * 實現款項功能。
    * 賣家頁面點選上一頁回至會員頁面。
6.	賣場
    * 賣場名稱、頭像、商品、商品分類，與firebase串接。
    * 完成賣場關鍵字搜尋功能。
7.	商品頁面
    * 將商品加入購物車的功能，與firebase串接。
    * 增加前往該商品賣場的入口。
8.	購物車
    * 完成結帳與訂單詳情功能。
    * 結帳送出功能與firebase串接。
    * 改由購物車判斷登入狀態。
7. 其他: 改善goodsPic資料表、禁止使用者選轉畫面、上架更多商品、與相簿功能串接。

## 第六章 參考文獻

1. Firebase google 官方技術文件 for Android 
> [Connect your App to Firebase](https://firebase.google.com/docs/database/android/start) <br>
> [Structure Your Database](https://firebase.google.com/docs/database/android/structure-data) <br>
> [Real Time Database](https://firebase.google.com/docs/database/android/start) <br>
> [Read and Write Data on Android - Write data](https://firebase.google.com/docs/database/android/read-and-write#write_data) <br>
> [Read and Write Data on Android - Read data](https://firebase.google.com/docs/database/android/read-and-write#read_data) <br>
> [Read and Write Data on Android - Update specific fields](https://firebase.google.com/docs/database/android/read-and-write#update_specific_fields )<br>
> [Read and Write Data on Android - Delete data](https://firebase.google.com/docs/database/android/read-and-write#delete_data) <br>
> [Connect your App to Firebase](https://firebase.google.com/docs/database/android/start) <br>
> [Firebase Realtime Database](https://firebase.google.com/docs/database) <br>
> [Work with Lists of Data on Android](https://firebase.google.com/docs/database/android/lists-of-data) <br>
> [Optimize Database Performance](https://firebase.google.com/docs/database/usage/optimize) <br>
> [ValueEventListener](https://firebase.google.com/docs/reference/android/com/google/firebase/database/ValueEventListener) <br>
 
2. 如何在 FireBase 搜尋儲存資料
> [How to search for a value in firebase Android](https://stackoverflow.com/questions/34537369/how-to-search-for-a-value-in-firebase-android/34537728#34537728) <br>
> [How to make a query in Firebase similar to a SQL query?](https://stackoverflow.com/questions/53762924/how-to-make-a-query-in-firebase-similar-to-a-sql-query/53763552#53763552) <br>
> [How to set value to all child's data in firebase database?](https://stackoverflow.com/questions/45162528/how-to-set-value-to-all-childs-data-in-firebase-database/45162620#45162620) <br>
> [Querying by range in firebase](https://stackoverflow.com/questions/52728669/querying-by-range-in-firebase/52732340#52732340) <br>
> [How do I store images on Google Firebase?](https://stackoverflow.com/questions/72572616/how-do-i-store-images-on-google-firebase/72574798#72574798) <br>
> [How can I convert an image into a Base64 string?](https://stackoverflow.com/questions/4830711/how-can-i-convert-an-image-into-a-base64-string/4830846#4830846) <br>
> [How to convert a Drawable to a Bitmap?](https://stackoverflow.com/questions/3035692/how-to-convert-a-drawable-to-a-bitmap/3035869#3035869) <br>
> [Nested Firestore asynchronous listeners in Android](https://stackoverflow.com/questions/47972620/nested-firestore-asynchronous-listeners-in-android/47972806#47972806) <br>
> [How to merge results of two queries in Firebase Realtime Database](https://stackoverflow.com/questions/72573375/how-to-merge-results-of-two-queries-in-firebase-realtime-database/72573909#72573909)


3. 設計與呈現 JSON 資料結構相關資料
> * Learning NoSQL — NoSQL Database Designing：如何把SQL結構轉成NOSQL，幫助原本不會NOSQL，但是會SQL的人轉換概念，用圖示標註的很清楚 <br> https://medium.com/tech-tajawal/nosql-modeling-database-structuring-part-ii-4c364c4bc17a <br>
> * Flatten data structures：因為搜尋資料上的考量，JSON格式是不建議太多層，儘量平一點，少層一點 <br> https://firebase.google.com/docs/database/android/structure-data#flatten_data_structures <br>
> * JSON Crack - Seamlessly visualize your JSON data instantly into graphs：可以輕鬆把複雜的JSON結構視覺化的編輯器，方便複雜參數的整理  <br> https://jsoncrack.com/ <br>

4. SQLite 相關資料
> * Cursor 取出資料的方法：當執行 select 搜尋資料後，從 rawQuery return 的 Cursor 取出資料的方法 <br> https://stackoverflow.com/questions/7387455/android-sqlite-how-to-retrieve-specific-data-from-particular-column <br>
> * class SQLiteDatabase - execSQL：只能執行不會return data的SQL語法https://developer.android.com/reference/android/database/sqlite/SQLiteDatabase#execSQL(java.lang.String) <br>
> * class SQLiteDatabase - rawQuery: 能執行所有SQL語法https://developer.android.com/reference/android/database/sqlite/SQLiteDatabase#rawQuery(java.lang.String,%20java.lang.String[]) <br>
> * SQLite 可提供的欄位屬性：https://www.sqlite.org/datatype3.html <br>
> [how to store Image as blob in Sqlite & how to retrieve it?](https://stackoverflow.com/questions/7331310/how-to-store-image-as-blob-in-sqlite-how-to-retrieve-it) <br>
> * Save data using SQLite：https://developer.android.com/training/data-storage/sqlite <br>
> * [Version of SQLite used in Android?](https://stackoverflow.com/questions/2421189/version-of-sqlite-used-in-android) <br>
> * [android sqlite foreign key not working](https://stackoverflow.com/questions/20579035/android-sqlite-foreign-key-not-working) <br>
5. 元件相關資料
> * setOnTouchListener：可監聽元件上按下、放開、滑動變化 <br> https://www.runoob.com/w3cnote/android-tutorial-listener-edittext-change.html <br>
> * addTextChangedListener：可監聽Edittext的輸入前、中、後不同時期的即時變化 <br> https://www.runoob.com/w3cnote/android-tutorial-listener-edittext-change.html <br>
> * 日期Dialog -日期選單： https://ithelp.ithome.com.tw/articles/10251105 <br>
> * Dialog 製作圓角底圖：https://www.cfanz.cn/resource/detail/yoGogNzrrryKA <br>
> * [Disabling of EditText in Android](https://stackoverflow.com/questions/4297763/disabling-of-edittext-in-android/47966420#47966420) <br>
> * [Navigation Drawer without Actionbar](https://stackoverflow.com/questions/20971245/navigation-drawer-without-actionbar) <br>
> * [DrawerLayout must be measured with MeasureSpec.EXACTLY error](https://stackoverflow.com/questions/31746072/drawerlayout-must-be-measured-with-measurespec-exactly-error/31773661#31773661) <br>
> * TabLayout：https://developer.android.com/reference/com/google/android/material/tabs/TabLayout <br>
> * Create swipe views with tabs using ViewPager2：https://developer.android.com/guide/navigation/navigation-swipe-view-2 <br>
> * Set up the app bar：https://developer.android.com/develop/ui/views/components/appbar/setting-up <br>
> * Update UI components with NavigationUI ：https://developer.android.com/guide/navigation/navigation-ui <br>
> * Navigation drawer Material Design：https://material.io/components/navigation-drawer/android#using-navigation-drawers <br>
6. Activity & Fragment 相關資料
> * Activity：https://developer.android.com/reference/android/app/Activity <br>
> * Fragment lifecycle：https://developer.android.com/guide/fragments/lifecycle <br>
> * Create a fragment：https://developer.android.com/guide/fragments/create <br>
> * [Intent from Fragment to Activity](https://stackoverflow.com/questions/20835933/intent-from-fragment-to-activity/20835980#20835980) <br>
> * [Passing data between a fragment and its container activity](https://stackoverflow.com/questions/9343241/passing-data-between-a-fragment-and-its-container-activity/9346844#9346844) <br>
> * [How to redirect from one activity to another after delay](https://stackoverflow.com/questions/9444586/how-to-redirect-from-one-activity-to-another-after-delay/9444614#9444614) <br>
> * [Passing data between a fragment and its container activity](https://stackoverflow.com/questions/9343241/passing-data-between-a-fragment-and-its-container-activity/9977370#9977370) <br>
> * [How to prevent going back to the previous activity?](https://stackoverflow.com/questions/8631095/how-to-prevent-going-back-to-the-previous-activity/26492794#26492794) <br>
7. Android 圖片設定相關資料
> * [Set icon for Android application](https://stackoverflow.com/questions/5350624/set-icon-for-android-application/11845815#11845815) <br>
> * [How to define a circle shape in an Android XML drawable file?](https://stackoverflow.com/questions/3185103/how-to-define-a-circle-shape-in-an-android-xml-drawable-file/12121937#12121937) <br>
> * Drawable resources：在Android Studio生成xml檔圖片避免圖片在不同裝置像素不夠的問題 <br> https://developer.android.com/guide/topics/resources/drawable-resource <br>
> * google官方提供的免費icon，可生成Android專用的xml檔： <br>
> * https://fonts.google.com/icons?selected=Material+Icons+Outlined:cloud_upload&icon.platform=android <br>
8. 取得當下時間
> [How to get current time and date in Android](https://stackoverflow.com/questions/5369682/how-to-get-current-time-and-date-in-android/36168941#36168941) <br>
9. SharedPreference 相關資料
> [Android change a stored SharedPreferences value?](https://stackoverflow.com/questions/31226003/android-change-a-stored-sharedpreferences-value/31226034#31226034) <br>
10. 存取手機裝置的Permission相關資料
> [Access media files from shared storage](https://developer.android.com/training/data-storage/shared/media) <br>
11. 使用Git & GitHub 學習資源相關資料
> [30 天精通 Git 版本控管 - 作者: 黃保翕 ( Will 保哥 )](https://github.com/doggy8088/Learn-Git-in-30-days/blob/master/zh-tw/README.md) <br>
> [為你自己學 Git - 作者 : 五倍紅寶石 高見龍](https://gitbook.tw/) <br>
> [Git & GitHub 教學手冊 - 作者 : 六角學院 洧杰](https://w3c.hexschool.com/git/cfdbd310) <br>


