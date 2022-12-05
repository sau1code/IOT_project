package com.example.iot_project.MyProduct;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.iot_project.R;
import com.example.iot_project.salesRecord.SalesRecordActivity;

import java.util.List;
import java.util.Map;

public class SoldOutRecyclerAdapter extends RecyclerView.Adapter<SoldOutRecyclerAdapter.ViewHolder> {
    private final Context mainContext;
    private final List<Map<String, Object>> myProductList;
    private final LayoutInflater myLayoutInflater;
    private String productName;

    //  1. 資料送進來
    public SoldOutRecyclerAdapter(Context context, List<Map<String,Object>> productList){
        mainContext = context;
        myProductList = productList;
        myLayoutInflater = LayoutInflater.from(context);
    }

    //    2. 取得RecyclerView上的View (R.layout.item_layout上面設定的View)
    @NonNull
    @Override
    public SoldOutRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = myLayoutInflater.inflate(R.layout.product_launched_already_items, parent, false);
        SoldOutRecyclerAdapter.ViewHolder viewHolder = new SoldOutRecyclerAdapter.ViewHolder(itemView);
        return viewHolder;
    }

    //  5.取出對應position應該要顯示的資料
    @Override
    public void onBindViewHolder(@NonNull SoldOutRecyclerAdapter.ViewHolder holder, int position) {
        Map<String, Object> data = myProductList.get(position);
        productName = data.get("productName").toString();
        int productInventory = (int)data.get("productInventory");
        int productPrice = (int)data.get("productPrice");
        int productSoldAmount = (int)data.get("productSoldAmount");

        holder.textView_soldOut_productName.setText(productName);
        holder.textView_soldOut_productPrice.setText(String.valueOf(productPrice));
        holder.textView_soldOut_inventory.setText(String.valueOf(productInventory));
        holder.textView_soldOut_soldNum.setText(String.valueOf(productSoldAmount));


    }

    //  3. 設定 RecyclerView 的 item數量
    @Override
    public int getItemCount() {
        return myProductList.size();
    }

    //  4. 取得 RecyclerView 上 Item 的 View
    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView_soldOut_productName;
        private final TextView textView_soldOut_productPrice;
        private final TextView textView_soldOut_inventory;
        private final TextView textView_soldOut_soldNum;
        private final Button button_soldOut_delete;
        private final Button button_soldOut_edit;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView_soldOut_productName = (TextView)itemView.findViewById(R.id.textView_launchedAlready_productName);
            textView_soldOut_productPrice = (TextView)itemView.findViewById(R.id.textView_launchedAlready_productPrice);
            textView_soldOut_inventory = (TextView)itemView.findViewById(R.id.textView_productLaunched_inventory);
            textView_soldOut_soldNum = (TextView)itemView.findViewById(R.id.textView_productLaunched_soldNum);

            button_soldOut_delete=(Button)itemView.findViewById(R.id.button_launchedAlready_delete);
            button_soldOut_edit = (Button)itemView.findViewById(R.id.button_launchedAlready_edit);

            button_soldOut_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Dialog deleteProductDlg = new Dialog((MyProductActivity)itemView.getContext());
                    deleteProductDlg.setContentView(R.layout.delete_product_dialog);
                    TextView textView_deleteProductDlg_name = (TextView) deleteProductDlg.findViewById(R.id.textView_deleteProductDlg_name);
                    Button button_deleteProductDlg_cancel = (Button) deleteProductDlg.findViewById(R.id.button_deleteProductDlg_cancel);
                    Button button_deleteProductDlg_ok = (Button) deleteProductDlg.findViewById(R.id.button_deleteProductDlg_ok);
                    textView_deleteProductDlg_name.setText(productName);
                    deleteProductDlg.show();
                    deleteProductDlg.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                    button_deleteProductDlg_ok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            //---------------------------------------------------------------------
                            //   這裡將商品名稱為 productName 的商品從資料庫刪除
                            //---------------------------------------------------------------------

                            deleteProductDlg.dismiss();
                        }
                    });

                    button_deleteProductDlg_cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            deleteProductDlg.dismiss();
                        }
                    });
                }
            });

            button_soldOut_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

        }
    }
}
