package com.nz.nztravelmate.dashboard;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nz.nztravelmate.R;
import com.nz.nztravelmate.model.Discount;
import com.nz.nztravelmate.model.Discount;

import java.util.ArrayList;

public class DiscountAdapter extends RecyclerView.Adapter<DiscountAdapter.ViewHolder> {
Context context;
    ArrayList<Discount> discountList;
    public DiscountAdapter(Context context, ArrayList<Discount> discountList) {
        this.context=context;
        this.discountList=discountList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.row_discount, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Discount Discount=discountList.get(position);

       //final Discount myListData = listdata[position];
        holder.txtName.setText(Discount.getName());
        holder.txtDiscount.setText(Discount.getDiscount());

        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(),"In Progress"+discountList.get(position).getName(),Toast.LENGTH_LONG).show();
                   /* Intent intent=new Intent(view.getContext(), ServiceDetailsActivity.class);
                    view.getContext().startActivity(intent);*/
            }
        });
    }

    @Override
    public int getItemCount() {
        return discountList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imgProfile,imgRight;
        public TextView txtName,txtDiscount,txtDistance,txtTime,txtService;
        public RelativeLayout relativeLayout;
        public ViewHolder(View itemView) {
            super(itemView);

            this.txtName= (TextView) itemView.findViewById(R.id.txtName);
            this.txtDiscount =(TextView) itemView.findViewById(R.id.txtDiscount);
            this.relativeLayout =(RelativeLayout) itemView.findViewById(R.id.relativeLayout);
        }
    }
}
