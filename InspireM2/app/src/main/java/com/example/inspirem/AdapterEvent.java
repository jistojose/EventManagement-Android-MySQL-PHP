package com.example.inspirem;

import android.content.Context;

import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

public class AdapterEvent extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    List<DataEvent> data= Collections.emptyList();
    DataEvent current;
    int currentPos=0;

    // create constructor to innitilize context and data sent from MainActivity
    public AdapterEvent(Context context, List<DataEvent> data){
        this.context=context;
        inflater= LayoutInflater.from(context);
        this.data=data;
    }

    // Inflate the layout when viewholder created
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.container_event, parent,false);
        MyHolder holder=new MyHolder(view);
        return holder;
    }

    // Bind data
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        // Get current position of item in recyclerview to bind data and assign values from list
        MyHolder myHolder= (MyHolder) holder;
        DataEvent current=data.get(position);
        myHolder.textEventName.setText(current.EventName);
        myHolder.textSpeaker.setText(current.Speaker);
        myHolder.textLocation.setText(current.Eventwhere);
        myHolder.textEventstart.setText(current.EventStart);
        myHolder.textEventends.setText(current.EventEnds);
        myHolder.textUsername.setText(current.UserName);
        myHolder.textContactnum.setText(current.ContactNum);
        myHolder.textPrice.setText("Rs. " + current.price );
        myHolder.textPrice.setTextColor(ContextCompat.getColor(context, R.color.colorAccent));



        // load image into imageview using glide
        Glide.with(context).load("http://192.168.1.7/test/images/" + current.EventImage)
                .placeholder(R.drawable.ic_image)
                .error(R.drawable.ic_image)
                .into(myHolder.ivEvent);

    }

    // return total item from List
    @Override
    public int getItemCount() {
        return data.size();
    }


    class MyHolder extends RecyclerView.ViewHolder{

        TextView textEventName;
        ImageView ivEvent;
        ImageView ivUseriamge;
        TextView textSpeaker;
        TextView textLocation;
        TextView textUsername;
        TextView textPrice;
        TextView textEventstart;
        TextView textEventends;
        TextView textContactnum;
        Button btnregistration;

        // create constructor to get widget reference
        public MyHolder( View itemView) {
            super(itemView);
            btnregistration=(Button)itemView.findViewById(R.id.btnregistration);

            btnregistration.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String Texttitle = textEventName.getText().toString();
                    String Textprice = textPrice.getText().toString();
                    Intent i = new Intent(v.getContext(),event_registration.class);
                   i.putExtra("title", Texttitle);
                    i.putExtra("price", Textprice);
                    v.getContext().startActivity(i);
                }
            });

            textEventName= (TextView) itemView.findViewById(R.id.textEventName);
            ivEvent= (ImageView) itemView.findViewById(R.id.ivEvent);
            ivUseriamge= (ImageView) itemView.findViewById(R.id.ivUser);
            textSpeaker = (TextView) itemView.findViewById(R.id.textSpeaker);
            textUsername = (TextView) itemView.findViewById(R.id.textUserName);
            textLocation = (TextView) itemView.findViewById(R.id.textLocation);
            textPrice = (TextView) itemView.findViewById(R.id.textPrice);
            textEventstart = (TextView) itemView.findViewById(R.id.texteventstart);
            textEventends = (TextView) itemView.findViewById(R.id.texteventends);
            textContactnum = (TextView) itemView.findViewById(R.id.textContactnum);


        }

    }

}
