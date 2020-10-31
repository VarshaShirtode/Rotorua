package com.nz.nztravelmate.startup;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nz.nztravelmate.R;
import com.nz.nztravelmate.dashboard.ServiceDetailsActivity;
import com.nz.nztravelmate.model.City;
import com.nz.nztravelmate.webservice.ApiConstants;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CityAdpater extends RecyclerView.Adapter<CityAdpater.ViewHolder> {
Context context;
    ArrayList<City> cityList;
    public CityAdpater(Context context, ArrayList<City> cityList) {
        this.context=context;
        this.cityList=cityList;
    }

    private void loadImage(ImageView profileImageView, String imagePath) {
      //  String filePath = ApiConstants.GET_IMAGE + "contacts/" + imagePath;
        String filePath =imagePath;
        Log.i("@RESP", "ImageUrl: " + imagePath);
        Picasso.with(context)
                .load(imagePath.replace(" ", "%20"))
                .placeholder(R.drawable.splash)
                .into(profileImageView);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.row_city, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        City food=cityList.get(position);

       //final City myListData = listdata[position];
        holder.txtName.setText(food.getName());
        if (food.getImage() != null) {
            loadImage(holder.imgCity, food.getImage());
        } else {
            loadImage(holder.imgCity, "https://st2.depositphotos.com/3104995/6728/i/950/depositphotos_67280377-stock-photo-rotorua-city-and-lake-landscape.jpg");
           // holder.imgCity.setImageResource(R.drawable.splash);
        }


      //  holder.imgProfile.setImageResource(listdata[position].getImgId());
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(),"click on item: "+cityList.get(position).getName(),Toast.LENGTH_LONG).show();
                    Intent intent=new Intent(view.getContext(), SplashActivity.class);
                    intent.putExtra("CityObject",food);
            view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return cityList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imgCity;
        public TextView txtName;
        public FrameLayout relativeLayout;
        public ViewHolder(View itemView) {
            super(itemView);
            this.imgCity = (ImageView) itemView.findViewById(R.id.imgCity);
            this.txtName= (TextView) itemView.findViewById(R.id.txtName);
           /* this.txtAddress =(TextView) itemView.findViewById(R.id.txtAddress);
            this.txtDistance = (TextView) itemView.findViewById(R.id.txtDistance);
            this.txtTime= (TextView) itemView.findViewById(R.id.txtTime);
            this.txtService =(TextView) itemView.findViewById(R.id.txtService);*/
            this.relativeLayout =(FrameLayout) itemView.findViewById(R.id.relativeLayout);
        }
    }
}
