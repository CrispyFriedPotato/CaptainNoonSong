package com.example.sookchat;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ImageView;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

import static com.example.sookchat.CardClickActivity.ccContext;

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


  private int SELF = 100;
  private int OPTIONS=3;
  private int IMAGES=4;
  private ArrayList<Message> messageArrayList;
  private Context cContext;
  private  Spannable spanText;
  private boolean initialRequest;


  public ChatAdapter(Context cContext,ArrayList<Message> messageArrayList) {
    this.cContext=cContext;
    this.messageArrayList = messageArrayList;
  }

  @Override
  public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View itemView;

    // view type is to identify where to render the chat message
    // left or right
    if (viewType == SELF) {
      // self message
      itemView = LayoutInflater.from(parent.getContext())
              .inflate(R.layout.chat_item_self, parent, false);
    }
    //Watson Option Message
    else if(viewType==OPTIONS){
      itemView = LayoutInflater.from(parent.getContext())
              .inflate(R.layout.chat_item_watson_option, parent, false);
    }
    else if(viewType==IMAGES){
      itemView = LayoutInflater.from(parent.getContext())
              .inflate(R.layout.chat_item_watson_image,parent,false);

    }
    else {
      // WatBot message
      itemView = LayoutInflater.from(parent.getContext())
              .inflate(R.layout.chat_item_watson, parent, false);
    }


    return new ViewHolder(itemView);
  }

  @Override
  public int getItemViewType(int position) {
    Message message = messageArrayList.get(position);
    if (message.getId() != null && message.getId().equals("1")) {
      return SELF;
    }
    else if(message.getId()!=null && message.getId().equals("3")){
      return OPTIONS;
    }
    else if(message.getId()!=null && message.getId().equals("4")){
      return IMAGES;
    }

    return position;
  }


  @Override
  public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
    Message message = messageArrayList.get(position);
    if (holder.getItemViewType()==OPTIONS){
      final SpannableString ss = new SpannableString(message.getOptions());
      ClickableSpan clickableSpan1=new ClickableSpan(){
        public void onClick(View widget){
          ((WatsonActivity)WatsonActivity.mContext).FLAG=700;
          ((WatsonActivity)WatsonActivity.mContext).ClickMessage=ss.toString();
          ((WatsonActivity)WatsonActivity.mContext).sendMessage();
          ((WatsonActivity)WatsonActivity.mContext).FLAG=701;
        }
      };

      ss.setSpan(clickableSpan1,0,ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
      message.setMessage(ss.toString());
      ((ViewHolder) holder).message.setText(ss);
    }
    else if(holder.getItemViewType()==IMAGES){
      Glide
              .with(cContext)
              .load(((WatsonActivity)WatsonActivity.mContext).imageSource)
              .apply(com.bumptech.glide.request.RequestOptions.bitmapTransform(new com.bumptech.glide.load.resource.bitmap.RoundedCorners(50)))
              .into(((ViewHolder) holder).images);

      ((ViewHolder) holder).images.setOnClickListener(new View.OnClickListener(){
        @Override
        public void onClick(View v){

          Intent intent = new Intent(cContext, ImageViewActivity.class);
           cContext.startActivity(intent);
        }
      });
      ((ViewHolder) holder).timeText.setText(message.getCreatedAt());
    }
    else {
      message.setMessage(message.getMessage());
      ((ViewHolder) holder).message.setText(message.getMessage());
      ((ViewHolder) holder).timeText.setText(message.getCreatedAt());
    }
  }

  @Override
  public int getItemCount() {
    return messageArrayList.size();
  }

  public class ViewHolder extends RecyclerView.ViewHolder {
    TextView message,timeText;
    ImageView images;

    public ViewHolder(View view) {
      super(view);
      message = (TextView) itemView.findViewById(R.id.message);
      images=(ImageView)itemView.findViewById(R.id.watsonImage);
      timeText=(TextView)itemView.findViewById(R.id.text_message_time);


    }
  }


}