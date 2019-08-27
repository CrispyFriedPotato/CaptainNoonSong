package com.example.sookchat;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.sookchat.Agora.CardClickAdapter;
import com.example.sookchat.Data.ImageItem;
import com.example.sookchat.R;

import java.util.List;

import static com.example.sookchat.Retrofit.RetroFitApiClient.IMAGE_DIR;
import static com.example.sookchat.Retrofit.RetroFitApiClient.MAP_DIR;

public class MapDescAdapter extends RecyclerView.Adapter<MapDescAdapter.ViewHolder> {

    private static final String TAG = "MapDescAdapter";
    private List<MapItem> descList;
    private Context mContext;
    private OnCardListener mOnCardListener;





    public MapDescAdapter(Context mContext, List<MapItem> desclist){
        Log.e(TAG,": called.");

        this.mContext=mContext;
        this.descList = desclist;


    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView desc_memo;
        ImageView desc_image;
        ImageButton desc_btn_close;
        OnCardListener onCardListener;

        public ViewHolder(View itemView){

            super(itemView);
            Log.e(TAG,"ViewHolder: called.");
            desc_memo = itemView.findViewById(R.id.desc_memo);
            desc_image = itemView.findViewById(R.id.desc_image);
            desc_btn_close = itemView.findViewById(R.id.desc_btn_close);

        }

    }


    public void setOnClickListener(OnCardListener onCardListener){
        mOnCardListener = onCardListener;
    }

    public interface OnCardListener{

        void onCardDelete();
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.e(TAG,"onCreateViewHolder: called.");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.map_desc_cardview,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int tourid) {
        Log.e(TAG,"onBindViewHolder: called.");

        final MapItem descitem = descList.get(tourid);

        holder.desc_memo.setText(descitem.getDescription());
        Glide.with(holder.itemView.getContext())
                .load( MAP_DIR+ descitem.getFilename() + ".jpg")
                .apply(RequestOptions.circleCropTransform()
                        .diskCacheStrategy(DiskCacheStrategy.NONE))
                .centerCrop()
                .into(holder.desc_image);

        holder.desc_btn_close.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                //((Activity)ccContext).finish();
                mOnCardListener.onCardDelete();
            }
        });



    }

    @Override
    public int getItemCount() {

        return descList.size();

    }







}