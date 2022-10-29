package com.example.campuscollab.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.campuscollab.databinding.FragmentRequestItemBinding;
import com.example.campuscollab.domain.Request;

import java.util.List;

public class RequestsRecyclerViewAdapter extends RecyclerView.Adapter<RequestsRecyclerViewAdapter.RequestViewHolder> {
    private List<Request> requests;

    public RequestsRecyclerViewAdapter(List<Request> requests) {
        this.requests = requests;
    }

    @NonNull
    @Override
    public RequestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RequestViewHolder(FragmentRequestItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RequestViewHolder holder, int position) {
        Request currReq = requests.get(position);
        holder.message.setText(currReq.getRequesting_name() + " is requesting to join " + currReq.getProject().getProjectName());

        holder.acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO Add participant to project
                Toast.makeText(view.getContext(), "Added to project", Toast.LENGTH_SHORT).show();
            }
        });

        holder.rejectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO remove request from project
                Toast.makeText(view.getContext(), "Request rejected", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return requests.size();
    }


    public class RequestViewHolder extends RecyclerView.ViewHolder {
        public TextView message;
        public ImageView image;
        public ImageView acceptButton;
        public ImageView rejectButton;

        public RequestViewHolder(FragmentRequestItemBinding binding) {
            super(binding.getRoot());
            message = binding.requestMessage;
            image = binding.profilePic;
            acceptButton = binding.acceptButton;
            rejectButton = binding.rejectButton;
        }
    }
}
