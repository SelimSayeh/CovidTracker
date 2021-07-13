package com.example.coronadetector.stat.view;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.coronadetector.R;
import com.example.coronadetector.stat.Model.TableItem;

import java.util.ArrayList;
import java.util.List;

public class TableAdapter extends RecyclerView.Adapter<TableAdapter.ViewHolder> implements Filterable {
    List<TableItem> list;
    private Context context;
    private List<TableItem>fullList;
    private OnItemClickListener mListener;
    public TableAdapter(List<TableItem>list,Context context){
        this.list=list;
        this.context=context;
        fullList=new ArrayList<>(list);
    }
    @NonNull
    @Override
    public TableAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.table_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TableAdapter.ViewHolder holder, int position) {
       TableItem tableItem=list.get(position);
       holder.country.setText(tableItem.getCountry());
       holder.totalCases.setText(""+tableItem.getTotalCases());


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView country;
        TextView totalCases;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            country=itemView.findViewById(R.id.country);
            totalCases=itemView.findViewById(R.id.totalCases);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener!=null){
                        int position=getAdapterPosition();
                        if(position!= RecyclerView.NO_POSITION){
                            mListener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
///Debut recherche
    @Override
    public Filter getFilter() {
        return exampleFilter;
    }
    private Filter exampleFilter=new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<TableItem>filteredList=new ArrayList<>();
            if(constraint==null||constraint.length()==0){
                filteredList.addAll(fullList);
            }else {
                String filterPattern=constraint.toString().toLowerCase().trim();
                for (TableItem item:fullList){
                    if(item.getCountry().toLowerCase().contains(filterPattern)){
                        if (!filteredList.contains(item)){
                        filteredList.add(item);}
                    }
                }
            }
            FilterResults results=new FilterResults();
            results.values=filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
               list.clear();
               list.addAll((List)results.values);
               notifyDataSetChanged();
        }
    };
    ///fin recherche
    public interface OnItemClickListener{
        void onItemClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener){
        mListener=listener;
    }
}
