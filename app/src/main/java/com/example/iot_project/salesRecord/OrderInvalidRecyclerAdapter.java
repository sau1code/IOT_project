package com.example.iot_project.salesRecord;

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

public class OrderInvalidRecyclerAdapter extends RecyclerView.Adapter<OrderInvalidRecyclerAdapter.ViewHolder> {
    private final Context mainContext;
    private final List<Map<String, Object>> myOrderList;
    private final LayoutInflater myLayoutInflater;
    private String orderNum;

    //  1. 資料送進來
    public OrderInvalidRecyclerAdapter(Context context, List<Map<String, Object>> orderList){
        mainContext = context;
        myOrderList = orderList;
        myLayoutInflater = LayoutInflater.from(context);
    }

    //    2. 取得RecyclerView上的View (R.layout.item_layout上面設定的View)
    @NonNull
    @Override
    public OrderInvalidRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = myLayoutInflater.inflate(R.layout.order_items, parent, false);
        OrderInvalidRecyclerAdapter.ViewHolder viewHolder = new OrderInvalidRecyclerAdapter.ViewHolder(itemView);
        return viewHolder;
    }

    //  5.取出對應position應該要顯示的資料
    @Override
    public void onBindViewHolder(@NonNull OrderInvalidRecyclerAdapter.ViewHolder holder, int position) {
        Map<String, Object> data = myOrderList.get(position);
        orderNum = data.get("orderNum").toString();
        String productName = data.get("productName").toString();
        int productNum = (int)data.get("productNum");
        int productPrice = (int)data.get("productPrice");
        int allProductNum = (int)data.get("allProductNum");
        int totalPrice = (int)data.get("totalPrice");


        holder.textView_invalid_orderNum.setText(orderNum);
        holder.textView_invalid_productName.setText(productName);
        holder.textView_invalid_productNum.setText(String.valueOf(productNum));
        holder.textView_invalid_productPrice.setText(String.valueOf(productPrice));
        holder.textView_invalid_totalProductNum.setText(String.valueOf(allProductNum));
        holder.textView_invalid_totalPrice.setText(String.valueOf(totalPrice));
        holder.textView_orderState.setText("訂單不成立");

    }

    //  3. 設定 RecyclerView 的 item數量
    @Override
    public int getItemCount() {
        return myOrderList.size();
    }

    //  4. 取得 RecyclerView 上 Item 的 View
    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView_invalid_orderNum;
        private final TextView textView_invalid_productName;
        private final TextView textView_invalid_productNum;
        private final TextView textView_invalid_productPrice;
        private final TextView textView_invalid_totalPrice;
        private final TextView textView_invalid_totalProductNum;
        private final ImageView imageView_invalidPic;
        private final TextView textView_orderState;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView_invalid_orderNum = (TextView)itemView.findViewById(R.id.textView_invalid_orderNum);
            textView_invalid_productName = (TextView)itemView.findViewById(R.id.textView_invalid_productName);
            textView_invalid_productNum = (TextView)itemView.findViewById(R.id.textView_invalid_productNum);
            textView_invalid_productPrice = (TextView)itemView.findViewById(R.id.textView_invalid_productPrice);
            textView_invalid_totalProductNum = (TextView)itemView.findViewById(R.id.textView_invalid_totalProductNum);
            textView_invalid_totalPrice = (TextView)itemView.findViewById(R.id.textView_invalid_totalPrice);
            textView_orderState = (TextView)itemView.findViewById(R.id.textView_orderState);

            imageView_invalidPic = (ImageView)itemView.findViewById(R.id.imageView_invalid_Pic);
        }
    }
}