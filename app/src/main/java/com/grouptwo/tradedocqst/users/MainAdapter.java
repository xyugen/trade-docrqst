package com.grouptwo.tradedocqst.users;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.grouptwo.tradedocqst.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {
    private RecyclerViewClickListener listener;
    ArrayList<String> dRequests;

    public MainAdapter(ArrayList<String> requests, RecyclerViewClickListener listener) {
        dRequests = requests;
        this.listener = listener;
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

    public interface RecyclerViewClickListener{
        void onClick(View v, int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView rqstID;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            rqstID = itemView.findViewById(R.id.rqstID);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            listener.onClick(view, getAdapterPosition());
        }
    }
}
