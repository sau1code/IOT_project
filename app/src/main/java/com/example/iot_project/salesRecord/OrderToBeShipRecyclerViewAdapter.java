package com.example.iot_project.salesRecord;

import android.app.Dialog;
import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.iot_project.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class OrderToBeShipRecyclerViewAdapter extends RecyclerView.Adapter<OrderToBeShipRecyclerViewAdapter.ViewHolder>{

    private final Context mainContext;
    private final List<Map<String, Object>> myOrderList;
    private final LayoutInflater myLayoutInflater;
    private String orderNum;
    private DatabaseReference dataRef;

    //  1. 資料送進來
    public OrderToBeShipRecyclerViewAdapter(Context context, List<Map<String, Object>> orderList){
        mainContext = context;
        myOrderList = orderList;
        myLayoutInflater = LayoutInflater.from(context);
    }

    //    2. 取得RecyclerView上的View (R.layout.item_layout上面設定的View)
    @NonNull
    @Override
    public OrderToBeShipRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = myLayoutInflater.inflate(R.layout.order_to_be_ship_items, parent, false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;
    }

    //  3. 設定 RecyclerView 的 item數量
    @Override
    public int getItemCount() {
        return myOrderList.size();
    }

    //  4. 取得 RecyclerView 上 Item 的 View
    public class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView textViewTobeship_orderNum;
        private final TextView textViewTobeship_productName;
        private final TextView textViewTobeship_productNum;
        private final TextView textViewTobeship_productPrice;
        private final TextView textViewTobeship_totalProductNum;
        private final TextView textViewTobeship_totalPrice;
        private final Button buttonTobeship_shipped;
        private final Button buttonTobeship_cancelOrder;
        private final ImageView imageView_tobeship_Pic;
        private TextView textView_cancelOrderDlg_orderNum;
        private ImageView imageButton_cancelOrderDlg_cancel;
        private EditText editText_CancelOrder_reason;
        private Button button_cancelOrderDlg_ok;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //    6. 監聽哪個item被按
            textViewTobeship_orderNum = (TextView) itemView.findViewById(R.id.textView_tobeship_orderNum);
            textViewTobeship_productName = (TextView)itemView.findViewById(R.id.textView_tobeship_productName);
            textViewTobeship_productNum = (TextView)itemView.findViewById(R.id.textView_tobeship_productNum);
            textViewTobeship_productPrice = (TextView)itemView.findViewById(R.id.textView_tobeship_productPrice);
            textViewTobeship_totalProductNum = (TextView)itemView.findViewById(R.id.textView_tobeship_totalProductNum);
            textViewTobeship_totalPrice = (TextView)itemView.findViewById(R.id.textView_tobeship_totalPrice);
            imageView_tobeship_Pic = (ImageView)itemView.findViewById(R.id.imageView_tobeship_Pic);
            buttonTobeship_shipped = (Button)itemView.findViewById(R.id.button_tobeship_shipped);
            buttonTobeship_cancelOrder  = (Button)itemView.findViewById(R.id.button_tobeship_cancelOrder);


            buttonTobeship_shipped.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //----------------------------------------------------------------------------------
                    //  這裡有予馨的小小心願
                    //  將訂單編號(orders_id)為orderNum 的訂單狀態 (orderStatus) 改成"已出貨"
                }
            });


            buttonTobeship_cancelOrder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Dialog CancelOrderDlg = new Dialog(((SalesRecordActivity)itemView.getContext()));
                    CancelOrderDlg.setContentView(R.layout.cancel_order_dialog);
                    textView_cancelOrderDlg_orderNum = (TextView)CancelOrderDlg.findViewById(R.id.textView_cancelOrderDlg_orderNum);
                    imageButton_cancelOrderDlg_cancel = (ImageView)CancelOrderDlg.findViewById(R.id.imageButton_cancelOrderDlg_cancel);
                    editText_CancelOrder_reason = (EditText)CancelOrderDlg.findViewById(R.id.editText_CancelOrder_reason);
                    button_cancelOrderDlg_ok = (Button)CancelOrderDlg.findViewById(R.id.button_cancelOrderDlg_ok);
                    textView_cancelOrderDlg_orderNum.setText(orderNum);

                    CancelOrderDlg.show();
                    CancelOrderDlg.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                    String CancelReason = editText_CancelOrder_reason.getText().toString();
                    imageButton_cancelOrderDlg_cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            CancelOrderDlg.dismiss();
                        }
                    });

                    button_cancelOrderDlg_ok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(editText_CancelOrder_reason.length()==0){
                                Toast.makeText(((SalesRecordActivity)itemView.getContext()), "請輸入取消訂單原因", Toast.LENGTH_SHORT).show();
                            }else{
                                //----------------------------------------------------------------------------------
                                //  這裡有予馨的小小心願
                                //  將訂單編號(orders_id)為orderNum 的訂單狀態(orderStatus) 改成 "不成立"
                                //  將不成立原因 (invalidReason) 存到 訂單編號(orders_id)為orderNum 的訂單中
                                //--------------------------------------------------------------------------
//                              取得FireBase服務
                                FirebaseDatabase database = FirebaseDatabase.getInstance();
//                              進入orders資料表
                                dataRef = database.getReference("orders");
//                              製作需要更新的資料 : 訂單狀態(orderStatus) 改成 "不成立"
                                Map<String,Object> mapStatus = new HashMap<>();
                                mapStatus.put("orderStatus","不成立");
//                              update :  訂單編號(orders_id)為orderNum 的訂單狀態(orderStatus) 改成 "不成立"
                                dataRef.child(orderNum).updateChildren(mapStatus);
//                              update :  不成立原因 (invalidReason) 存到 訂單編號(orders_id)為orderNum 的訂單中
                                Map<String,Object> mapReason = new HashMap<>();
                                String cancelReason = editText_CancelOrder_reason.getText().toString();
                                mapReason.put("invalidReason",cancelReason);
                                dataRef.child(orderNum).updateChildren(mapReason);
                                CancelOrderDlg.dismiss();
                            }
                        }
                    });
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    ((SalesRecordActivity)itemView.getContext()).orderToBeShipDetail(myOrderList.get(position));
                }
            });

        }
    }

    //  5.取出對應position應該要顯示的資料
    @Override
    public void onBindViewHolder(@NonNull OrderToBeShipRecyclerViewAdapter.ViewHolder holder, int position) {
        Map<String, Object> data = myOrderList.get(position);
        orderNum = data.get("orderNum").toString();
        String productName = data.get("productName").toString();
        int productNum = (int)data.get("productNum");
        int productPrice = (int)data.get("productPrice");
        int allProductNum = (int)data.get("allProductNum");
        int totalPrice = (int)data.get("totalPrice");

        holder.textViewTobeship_orderNum.setText(orderNum);
        holder.textViewTobeship_productName.setText(productName);
        holder.textViewTobeship_productNum.setText(String.valueOf(productNum));
        holder.textViewTobeship_productPrice.setText(String.valueOf(productPrice));
        holder.textViewTobeship_totalProductNum.setText(String.valueOf(allProductNum));
        holder.textViewTobeship_totalPrice.setText(String.valueOf(totalPrice));

    }

    //    7. return 最新的FoodList，因為會隨時更新數量所以每次的FoodList都會不一樣
    public List<Map<String, Object>> getData(){
        return myOrderList;
    }
    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */


}