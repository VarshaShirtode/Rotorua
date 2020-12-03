package com.nz.nztravelmate.dashboard;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.text.Html;
import android.text.Spannable;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
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
import com.nz.nztravelmate.model.Data;
import com.nz.nztravelmate.webservice.ApiConstants;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class BusinessAdpater extends RecyclerView.Adapter<BusinessAdpater.ViewHolder> {
Context context;
    ArrayList<Data> foodlist;
    public BusinessAdpater(Context context, ArrayList<Data> foodlist) {
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
        Data food=foodlist.get(position);

       //final Data myListData = listdata[position];
        holder.txtName.setText(food.getName());
        holder.txtAddress.setText(food.getAddress());
        holder.txtShort.setText(food.getShort_description());
        if (!food.getWebsite().equals("")) {
            String styledText = "<span style=color:red>"+food.getWebsite()+"</span>";
            holder.txtWebsite.setText(context.getResources().getString(R.string.Website)+" : "+Html.fromHtml(styledText),TextView.BufferType.SPANNABLE);
            Spannable sText = (Spannable) holder.txtWebsite.getText();
            sText.setSpan(new ForegroundColorSpan(Color.BLUE), 10, sText.length(), 0);
        }
        if (!food.getAvg_doller().equals("")) {
            holder.txtSpend.setText(context.getResources().getString(R.string.Spend)+" : "+food.getAvg_doller());
        }
        holder.txtWebsite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url=food.getWebsite().toString().trim();
                Uri webpage = Uri.parse(url);

                if (!url.startsWith("http://") && !url.startsWith("https://")) {
                    webpage = Uri.parse("http://" + url);
                }
                Intent httpIntent = new Intent(Intent.ACTION_VIEW);
                httpIntent.setData(webpage);

                context.startActivity(httpIntent);
            }
        });
        if (food.getAvg_time()==null)
        {
            holder.txtDistance.setText(context.getResources().getString(R.string.SuggestedTime)+" : "+"");
          //  holder.txtDistance.setVisibility(View.GONE);
        }else{
            holder.txtDistance.setText(context.getResources().getString(R.string.SuggestedTime)+" : "+food.getAvg_time());
           // holder.txtDistance.setVisibility(View.VISIBLE);
        }

        if (food.getBusiness_hrs()==null)
        {
            holder.txtTime.setText(context.getResources().getString(R.string.OperatedTime)+" : "+"");
            //  holder.txtTime.setVisizcw67bility(View.GONE);
        }else{
            String name=food.getBusiness_hrs().trim();
            name=name.replace("<p>","");
            name=name.replace("</p>","");
            holder.txtTime.setText(context.getResources().getString(R.string.OperatedTime)+" : "+name);

        }
        String Service="";

        /*if (food.getPayment()!=null)
        {
            // Service = Service+"Payment" + " : " + food.getPayment()+ "\n";
            String data=food.getPayment();
            int available= Integer.parseInt(data.substring(0,1));
            if (data.equals("1"))
            {*/
        if (!food.getPayment_id().isEmpty())
        {
            Service = Service+ context.getResources().getString(R.string.Payment) + " : " ;

            for (int i=0;i<food.getPayment_id().size();i++)
            {
                Service = Service+ food.getPayment_id().get(i).getName()+ ", ";
            }
            if (!Service.equals(""))
            {
                char e=Service.charAt(Service.length()-1);
                if (e==' ')
                {
                    Service=Service.substring(0,Service.length()-2);
                }
            }
            Service = Service+ "\n" ;

        }else{
            // Service = Service+ "Payment" + " : " + "Yes"+ "\n";

        }
         //   }
           /* if (available==1)
            {
                if (data.length()!=1) {
                    String msg = data.substring(2, data.length() - 1);
                    Service =Service+ "Payment"+ " : " + "Yes," + msg + "\n";
                }else{

                    Service = Service+ "Payment" + " : " + "Yes"+ "\n";
                }
            }else{
                if (data.length()!=1) {
                    String msg = data.substring(2, data.length() - 1);
                    Service =Service+ "Payment" + " : " + "No," + msg + "\n";
                }else{
                    Service =Service+ "Payment" + " : " + "No"+ "\n";
                }
            }*/
       // }
        if (food.getWifi()!=null) {
            String data = food.getWifi();
            if (!data.equals("")) {
                int available = Integer.parseInt(data.substring(0, 1));
                if (available == 1) {
                    if (data.length() != 1) {
                        String msg = data.substring(2, data.length());
                        Service = Service + context.getResources().getString(R.string.Free_Wifi) + " : " + context.getResources().getString(R.string.Yes) + ", " + msg + "\n";
                    } else {
                        Service = Service + context.getResources().getString(R.string.Free_Wifi) + " : " + context.getResources().getString(R.string.Yes) + "\n";
                    }
                } else {
                    if (data.length() != 1) {
                        String msg = data.substring(2, data.length());
                        Service = Service + context.getResources().getString(R.string.Free_Wifi) + " : " + context.getResources().getString(R.string.No) + ", " + msg + "\n";
                    } else {
                        Service = Service + context.getResources().getString(R.string.Free_Wifi) + " : " + context.getResources().getString(R.string.No) + "\n";
                    }
                }
            }
        }
        if (food.getParking()!=null)
        {
            String data=food.getParking();
            if (!data.equals("")) {
                int available = Integer.parseInt(data.substring(0, 1));
                if (available == 1) {
                    if (data.length() != 1) {
                        String msg = data.substring(2, data.length());
                        Service = Service + context.getResources().getString(R.string.Free_Parking) + " : " + context.getResources().getString(R.string.Yes) + ", " + msg + "\n";
                    } else {
                        Service = Service + context.getResources().getString(R.string.Free_Parking) + " : " + context.getResources().getString(R.string.Yes) + "\n";
                    }
                } else {
                    if (data.length() != 1) {
                        String msg = data.substring(2, data.length());
                        Service = Service + context.getResources().getString(R.string.Free_Parking) + " : " + context.getResources().getString(R.string.No) + ", " + msg + "\n";
                    } else {
                        Service = Service + context.getResources().getString(R.string.Free_Parking) + " : " + context.getResources().getString(R.string.No) + "\n";
                    }
                }
            }
        }
        if (food.getStaff()!=null)
        {
            String data=food.getStaff();
         if (!data.equals("")) {
             int available = Integer.parseInt(data.substring(0, 1));
             if (available == 1) {
                 if (data.length() != 1) {
                     String msg = data.substring(2, data.length());
                     Service = Service + context.getResources().getString(R.string.Mandarin_Speaking_Staff) + " : " + context.getResources().getString(R.string.Yes) + ", " + msg + "\n";
                 } else {
                     Service = Service + context.getResources().getString(R.string.Mandarin_Speaking_Staff) + " : " + context.getResources().getString(R.string.Yes) + "\n";
                 }
             } else {
                 if (data.length() != 1) {
                     String msg = data.substring(2, data.length());
                     Service = Service + context.getResources().getString(R.string.Mandarin_Speaking_Staff) + " : " + context.getResources().getString(R.string.No) + ", " + msg + "\n";
                 } else {
                     Service = Service + context.getResources().getString(R.string.Mandarin_Speaking_Staff) + " : " + context.getResources().getString(R.string.No) + "\n";
                 }
             }
         }
        }
        if (!Service.equals(""))
        {
            Service=Service.trim();
            /*char s=Service.charAt(0);
            char e=Service.charAt(Service.length()-1);
            if (s==' ')
            {
                Service=Service.substring(3);
            }
            if (e==' ')
            {
                Service=Service.substring(0,Service.length()-3);
            }*/
            holder.txtService.setText(Service);
        }else{
            holder.txtService.setText(context.getResources().getString(R.string.NoService));
        }

      if (food.getLogo()!=null) {
            loadImage(holder.imgProfile, food.getLogo());
        }
      //  holder.imgProfile.setImageResource(listdata[position].getImgId());
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //  Toast.makeText(view.getContext(),"click on item: "+foodlist.get(position).getName(),Toast.LENGTH_LONG).show();
                    Intent intent=new Intent(view.getContext(), ServiceDetailsActivity.class);
                    intent.putExtra("ItemObject",food);
            view.getContext().startActivity(intent);
            }
        });
    }
    private void loadImage(ImageView profileImageView, String imagePath) {
      String filePath = ApiConstants.GET_BUSINESS_LOGO_IMAGE + imagePath;
      // String filePath ="https://nztravelmate.com/uploads/city_images/1604153405.jpg";
        Log.i("@RESP", "ImageUrl: " + filePath);
        Picasso.with(context)
                .load(filePath.replace(" ", "%20"))
                .placeholder(R.drawable.website)
                .transform(new CircleTransform())
                .into(profileImageView);
    }
    @Override
    public int getItemCount() {
        return foodlist.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imgProfile,imgRight;
        public TextView txtName,txtAddress,txtDistance,txtTime,txtService,txtShort,txtSpend,txtWebsite;
        public RelativeLayout relativeLayout;
        public ViewHolder(View itemView) {
            super(itemView);
            this.imgProfile = (ImageView) itemView.findViewById(R.id.imgProfile);
            this.txtName= (TextView) itemView.findViewById(R.id.txtName);
            this.txtAddress =(TextView) itemView.findViewById(R.id.txtAddress);
            this.txtDistance = (TextView) itemView.findViewById(R.id.txtDistance);
            this.txtSpend =(TextView) itemView.findViewById(R.id.txtSpend);
            this.txtShort = (TextView) itemView.findViewById(R.id.txtShort);
            this.txtTime= (TextView) itemView.findViewById(R.id.txtTime);
            this.txtWebsite= (TextView) itemView.findViewById(R.id.txtWebsite);
            this.txtService =(TextView) itemView.findViewById(R.id.txtService);
            this.relativeLayout =(RelativeLayout) itemView.findViewById(R.id.relativeLayout);
        }
    }
}
