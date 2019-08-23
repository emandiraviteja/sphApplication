package com.example.sphapplication;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class YearListAdapter extends RecyclerView.Adapter<YearListAdapter.YearListItemView> {

    private LayoutInflater inflater;
    private ArrayList<YearListItem> yearList;

    public YearListAdapter(Context ctx, ArrayList<YearListItem> yearList){
        inflater = LayoutInflater.from(ctx);
        this.yearList = yearList;
    }

    @Override
    public YearListItemView onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.activity_main, parent, false);
        YearListItemView holder = new YearListItemView(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final YearListItemView holder, final int position) {
        final YearListItem item = yearList.get(position);
        holder.year.setText(item.getYear());
        holder.volume.setText(item.getVolume().toString());
        if (item.isDecilned()){
            holder.declined.setVisibility(View.VISIBLE);
            holder.declined.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(holder.declined.getContext(), "Year " + item.getYear() + " was clicked!", Toast.LENGTH_SHORT).show();
                }
            });
        }else{
            holder.declined.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return yearList.size();
    }

    class YearListItemView extends RecyclerView.ViewHolder{

        TextView year, volume;
        ImageButton declined;

        public YearListItemView(View itemView) {
            super(itemView);
            year = (TextView) itemView.findViewById(R.id.year);
            volume = (TextView) itemView.findViewById(R.id.volume);
            declined = (ImageButton) itemView.findViewById(R.id.declined);

        }

    }

}
