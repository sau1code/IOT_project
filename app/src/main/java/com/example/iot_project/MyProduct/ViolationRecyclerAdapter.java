package com.example.iot_project.MyProduct;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.iot_project.R;

import java.util.List;
import java.util.Map;

public class ViolationRecyclerAdapter extends RecyclerView.Adapter<ViolationRecyclerAdapter.ViewHolder> {
    private final Context mainContext;
    private final List<Map<String, Object>> myProductList;
    private final LayoutInflater myLayoutInflater;

    //  1. 資料送進來
    public ViolationRecyclerAdapter(Context context, List<Map<String,Object>> productList){
        mainContext = context;
        myProductList = productList;
        myLayoutInflater = LayoutInflater.from(context);
    }


    //    2. 取得RecyclerView上的View (R.layout.item_layout上面設定的View)
    @NonNull
    @Override
    public ViolationRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = myLayoutInflater.inflate(R.layout.seller_product_items, parent, false);
        ViolationRecyclerAdapter.ViewHolder viewHolder = new ViolationRecyclerAdapter.ViewHolder(itemView);
        return viewHolder;
    }


    //  5.取出對應position應該要顯示的資料
    @Override
    public void onBindViewHolder(@NonNull ViolationRecyclerAdapter.ViewHolder holder, int position) {
        Map<String, Object> data = myProductList.get(position);
        String productName = data.get("productName").toString();
        int productPrice = (int)data.get("productPrice");

        holder.textView_sellerProduct_productName.setText(productName);
        holder.textView_sellerProduct_productPrice.setText(String.valueOf(productPrice));
        holder.textView_sellerProduct_productState.setText("商品已違規");

    }

    //  3. 設定 RecyclerView 的 item數量
    @Override
    public int getItemCount() {
        return myProductList.size();
    }


    //  4. 取得 RecyclerView 上 Item 的 View
    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView_sellerProduct_productName;
        private final TextView textView_sellerProduct_productPrice;
        private final TextView textView_sellerProduct_productState;
        private final ImageView imageView_sellerProduct_picture;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView_sellerProduct_productName = (TextView) itemView.findViewById(R.id.textView_sellerProduct_productName);
            textView_sellerProduct_productPrice = (TextView) itemView.findViewById(R.id.textView_sellerProduct_productPrice);
            textView_sellerProduct_productState = (TextView) itemView.findViewById(R.id.textView_sellerProduct_productState);

            imageView_sellerProduct_picture = (ImageView) itemView.findViewById(R.id.imageView_sellerProduct_picture);
        }
    }
}
