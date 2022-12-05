package com.example.iot_project.member;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.example.iot_project.R;

public class MemberGoodsRecyclerViewAdapter extends RecyclerView.Adapter<MemberGoodsRecyclerViewAdapter.ViewHolder> {

    @NonNull
    @Override
    public MemberGoodsRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_goods, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MemberGoodsRecyclerViewAdapter.ViewHolder holder, int position) {
    }

    @Override
    public int getItemCount() {
        return 100;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final ImageView imageViewPicture;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewPicture = (ImageView)itemView.findViewById(R.id.imageView_goods_pic);
//            imageViewPicture.setImageDrawable(ContextCompat.getDrawable(itemView.getContext(), R.drawable.cat3));
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Toast.makeText(view.getContext(), "123", Toast.LENGTH_SHORT).show();
        }

    }

}
