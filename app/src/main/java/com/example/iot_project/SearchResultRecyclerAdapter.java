package com.example.iot_project;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Map;

public class SearchResultRecyclerAdapter extends RecyclerView.Adapter<SearchResultRecyclerAdapter.ViewHolder>{


    private final Context mainContext;
    private final List<Map<String, Object>> myList;
    private final LayoutInflater myLayoutInflater;

    //  1. 資料送進來
    public SearchResultRecyclerAdapter(Context context, List<Map<String, Object>> List) {
        mainContext = context;
        myList = List;
        myLayoutInflater = LayoutInflater.from(context);
    }


    //    2. 取得RecyclerView上的View
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = myLayoutInflater.inflate(R.layout.listview_search_reault, parent, false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;
    }

    //  3. 設定 RecyclerView 的 item數量

    @Override
    public int getItemCount() {
        return myList.size();
    }

    //  4. 取得 RecyclerView 上 Item 的 View

    public class ViewHolder extends RecyclerView.ViewHolder{

        private final TextView textViewTitle;
        private final TextView textViewStore;
        private final TextView textViewPrice;
        private final TextView textViewDate;
        private final ImageView imageViewResult;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = (TextView) itemView.findViewById(R.id.textView_search_title) ;
            textViewStore = (TextView) itemView.findViewById(R.id.textView_search_store) ;
            textViewPrice = (TextView) itemView.findViewById(R.id.textView_search_price) ;
            textViewDate = (TextView) itemView.findViewById(R.id.textView_search_date) ;
            imageViewResult = (ImageView) itemView.findViewById(R.id.imageView_search_result) ;
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    Log.d("main", "position=" + position);

                    Map<String, Object> data = myList.get(position);
                    String goods_id = data.get("goods_id").toString();
                    String goods_name = data.get("goods_name").toString();
                    String storeName = data.get("storeName").toString();
                    String price = data.get("price").toString();
                    String createTime = data.get("createTime").toString();
                    String inventory = data.get("inventory").toString();
                    String info = data.get("info").toString();
//                   base64 字串 轉成
                    String goodsPicture = data.get("goodsPicture").toString();

                    Activity SearchActivity = (Activity) mainContext;
//                  商品資料一併傳入商品詳細資訊頁面
                    Intent intent = new Intent(SearchActivity, ItemDetailActivity.class);
                    intent.putExtra("goods_id",goods_id);
                    intent.putExtra("goods_name",goods_name);
                    intent.putExtra("storeName",storeName);
                    intent.putExtra("price",price);
                    intent.putExtra("createTime",createTime);
                    intent.putExtra("goodsPicture",goodsPicture );

                    intent.putExtra("inventory",inventory);
                    intent.putExtra("info",info);
                    SearchActivity.startActivity(intent);
                }
            });

        }
    }

    //  5.取出對應position應該要顯示的資料
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Map<String, Object> data = myList.get(position);
        String goods_name = data.get("goods_name").toString();
        String storeName = data.get("storeName").toString();
        String price = data.get("price").toString();
        String createTime = data.get("createTime").toString();
//                   base64 字串 轉成
        String goodsPicture = data.get("goodsPicture").toString();
        byte[] decodedString = Base64.decode(goodsPicture, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        holder.textViewTitle.setText(goods_name);
        holder.textViewStore.setText(storeName);
        holder.textViewPrice.setText(price);
        holder.textViewDate.setText(createTime);
        holder.imageViewResult.setImageBitmap(decodedByte);
    }

}
