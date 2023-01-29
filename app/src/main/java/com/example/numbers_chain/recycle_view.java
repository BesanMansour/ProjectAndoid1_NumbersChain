package com.example.numbers_chain;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.numbers_chain.databinding.AllGameListBinding;

import java.util.ArrayList;

public class recycle_view extends RecyclerView.Adapter<recycle_view.allGame>  {
    ArrayList<details_allGame_class> details_allGame_classes;
    Context context;

    public recycle_view(ArrayList<details_allGame_class> details_allGame_classes,Context context) {
        this.details_allGame_classes = details_allGame_classes;
        this.context = context;
    }
    @NonNull
    @Override
    public allGame onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AllGameListBinding binding = AllGameListBinding.inflate(LayoutInflater.from(context),parent,false);
        return new allGame(binding.getRoot());
    }
    @Override
    public void onBindViewHolder(@NonNull allGame holder, int position) {
        details_allGame_class d = details_allGame_classes.get(position);
        holder.binding.listTvFullname.setText(d.getFullName());
        holder.binding.listTvTimeDate.setText(d.getDate());
        holder.binding.listTvGrade.setText(String.valueOf(d.getGrade()));
    }
    @Override
    public int getItemCount() {
        return details_allGame_classes.size();
    }
    class allGame extends RecyclerView.ViewHolder{
        AllGameListBinding binding;
        public allGame(@NonNull View itemView) {
            super(itemView);
            binding = AllGameListBinding.bind(itemView);
        }
    }
}
