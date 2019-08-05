package com.example.sookchat.Agora;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.sookchat.Data.Data;
import com.example.sookchat.R;

import java.util.ArrayList;
import java.util.List;

import static com.example.sookchat.Retrofit.RetroFitApiClient.IMAGE_DIR;

public class AgoraAdapter extends RecyclerView.Adapter<AgoraAdapter.ViewHolder> {
    private List<Data> mDataList;
    private List<Data> mFilteredList;
    private Context mContext;

    public AgoraAdapter(Context mContext, List<Data> mData) {

        this.mContext = mContext;
        this.mDataList = mData;
        this.mFilteredList = mData;
    }



    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView viewers;
        TextView title;
        TextView content;
        ImageView image;
        CardView cv; //for touch listener

        public ViewHolder(View itemView) {
            super(itemView);
            viewers = itemView.findViewById(R.id.viewers);
            title = itemView.findViewById(R.id.title_text);
            content = itemView.findViewById(R.id.contents_text);
            image = itemView.findViewById(R.id.agora_image);
            cv=itemView.findViewById(R.id.cv);
        }
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item, parent, false);

        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final Data data = mFilteredList.get(position);
        holder.viewers.setText(data.getViews());
        holder.title.setText(data.getTitle());
        holder.content.setText(data.getContent());
        Glide.with(mContext)
                .load( IMAGE_DIR + data.getFilename() + ".jpg")
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .centerCrop()
                .into(holder.image);

        holder.cv.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                Intent intent = new Intent(mContext, CardClickActivity.class);
                intent.putExtra("catid", data.getCatid());
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount () {
        return mFilteredList.size();
    }

    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String charString = charSequence.toString();

                if (charString.isEmpty()) {

                    mFilteredList = mDataList;
                } else {

                    ArrayList<Data> filteredList = new ArrayList<>();

                    for (Data data : mDataList) {

                        if (data.getTitle().toLowerCase().contains(charString) || data.getContent().toLowerCase().contains(charString)) {

                            filteredList.add(data);
                        }
                    }

                    mFilteredList = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mFilteredList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mFilteredList = (ArrayList<Data>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }



}