package com.nz.nztravelmate.dashboard.food;

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
import com.nz.nztravelmate.dashboard.ServiceDetailsActivity;
import com.nz.nztravelmate.model.Food;

import java.util.ArrayList;

public class FoodAdpater extends RecyclerView.Adapter<FoodAdpater.ViewHolder> {
Context context;
    ArrayList<Food> foodlist;
    public FoodAdpater(Context context, ArrayList<Food> foodlist) {
        this.context=context;
        this.foodlist=foodlist;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.row_service, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Food food=foodlist.get(position);

       //final Food myListData = listdata[position];
        holder.txtName.setText(food.getName());
        holder.txtAddress.setText(food.getAddress());
        holder.txtDistance.setText(food.getDistance());
        holder.txtTime.setText(food.getWeekTime()+"\n"+food.getWeekendTime());
        holder.txtService.setText(food.getService());

      //  holder.imgProfile.setImageResource(listdata[position].getImgId());
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(),"click on item: "+foodlist.get(position).getName(),Toast.LENGTH_LONG).show();
                    Intent intent=new Intent(view.getContext(), ServiceDetailsActivity.class);
            view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return foodlist.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imgProfile,imgRight;
        public TextView txtName,txtAddress,txtDistance,txtTime,txtService;
        public RelativeLayout relativeLayout;
        public ViewHolder(View itemView) {
            super(itemView);
            this.imgProfile = (ImageView) itemView.findViewById(R.id.imgProfile);
            this.txtName= (TextView) itemView.findViewById(R.id.txtName);
            this.txtAddress =(TextView) itemView.findViewById(R.id.txtAddress);
            this.txtDistance = (TextView) itemView.findViewById(R.id.txtDistance);
            this.txtTime= (TextView) itemView.findViewById(R.id.txtTime);
            this.txtService =(TextView) itemView.findViewById(R.id.txtService);
            this.relativeLayout =(RelativeLayout) itemView.findViewById(R.id.relativeLayout);
        }
    }
}
