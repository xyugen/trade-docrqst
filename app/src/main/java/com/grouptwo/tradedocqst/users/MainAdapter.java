package com.grouptwo.tradedocqst.users;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.grouptwo.tradedocqst.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {

    ArrayList<String> dRequests;

    public MainAdapter(ArrayList<String> requests) {
        dRequests = requests;
    }

    @NonNull
    @Override
    public MainAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainAdapter.ViewHolder holder, int position) {
        holder.rqstID.setText(dRequests.get(position));
    }

    @Override
    public int getItemCount() {
        return dRequests.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView rqstID;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            rqstID = itemView.findViewById(R.id.rqstID);
        }
    }
}
