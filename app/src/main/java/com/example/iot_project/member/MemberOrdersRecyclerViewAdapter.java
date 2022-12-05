package com.example.iot_project.member;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.example.iot_project.R;

public class MemberOrdersRecyclerViewAdapter extends RecyclerView.Adapter<MemberOrdersRecyclerViewAdapter.ViewHolder> {

    private String name;
    private Context context;

    public MemberOrdersRecyclerViewAdapter(String name) {
        this.name = name;
    };

    @NonNull
    @Override
    public MemberOrdersRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_member_orders, parent, false);
        return new MemberOrdersRecyclerViewAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MemberOrdersRecyclerViewAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        context = holder.itemView.getContext();

        holder.buttonCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "position: " + position , Toast.LENGTH_SHORT).show();
            }
        });

        switch (name) {
            case "待付款":
                holder.buttonCardView.setText("已付款");
                break;
            case "待出貨":
                holder.buttonCardView.setVisibility(View.GONE);
                break;
            case "待取貨":
                holder.buttonCardView.setText("已取貨");
                holder.imageViewPic.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.okinawa));
                break;
            case "已完成":
                holder.buttonCardView.setVisibility(View.GONE);
                break;
        }

    }

    @Override
    public int getItemCount() {
        return 100;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView textViewStoreName;
        private final Button buttonCardView;
        private final ImageView imageViewPic;
        private final TextView textViewPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewStoreName = (TextView)itemView.findViewById(R.id.TextView_cardview_member_orders_storeName);
            buttonCardView = (Button)itemView.findViewById(R.id.button_cardview_member_orders);
            imageViewPic = (ImageView)itemView.findViewById(R.id.imageView_cardview_member_orders_picture);
            textViewPrice = (TextView)itemView.findViewById(R.id.textView_member_orders_total);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Toast.makeText(view.getContext(), "" + getLayoutPosition(), Toast.LENGTH_SHORT).show();
            ((MemberActivity)itemView.getContext()).showOrdersDetailedFragment("訂單詳情");
        }

    }

}
