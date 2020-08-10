package com.example.jaroslav.myapplication;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.jaroslav.myapplication.database.ResultsDBManager;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Statistics extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        RecyclerView recyclerView = findViewById(R.id.stat_list);

        ResultsDBManager database = new ResultsDBManager(this);
        List<Result> results = database.getResultsList();
        Collections.reverse(results);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new SAdapter(results));
    }

    private class SAdapter extends RecyclerView.Adapter<SViewHolder> {
        List<Result> mResults;

        public SAdapter(List<Result> results) {
            mResults = results;
        }

        @NonNull
        @Override
        public SViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_stat, parent, false);
            return new SViewHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull SViewHolder holder, int position) {
            holder.bind(mResults.get(position), position);
        }

        @Override
        public int getItemCount() {
            return mResults.size();
        }
    }

    private class SViewHolder extends RecyclerView.ViewHolder {
        Result mResult;
        TextView position;
        TextView speed;
        TextView points;
        TextView date;

        public SViewHolder(View itemView) {
            super(itemView);

            position = itemView.findViewById(R.id.item_stat_position);
            speed = itemView.findViewById(R.id.item_stat_speed);
            points = itemView.findViewById(R.id.item_stat_points);
            date = itemView.findViewById(R.id.item_stat_date);
        }

        public void bind(Result result, int pos){
            mResult = result;
            position.setText(String.valueOf(pos + 1));
            speed.setText(String.valueOf(mResult.getSpeed() + 1));
            points.setText(String.valueOf(mResult.getPoint()));
            date.setText(DateFormat.getDateInstance(DateFormat.SHORT).format(mResult.getDate()));
        }
    }
}