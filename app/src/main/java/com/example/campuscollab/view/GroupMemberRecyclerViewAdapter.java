package com.example.campuscollab.view;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.campuscollab.databinding.FragmentGroupMemberBinding;
import com.example.campuscollab.service.UserService;

import java.util.List;

public class GroupMemberRecyclerViewAdapter extends RecyclerView.Adapter<GroupMemberRecyclerViewAdapter.ViewHolder> {

    private final List<String> participants;
    private UserService userService = UserService.getInstance();

    public GroupMemberRecyclerViewAdapter(List<String> participants) {
        this.participants = participants;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(FragmentGroupMemberBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.participant = participants.get(position);
        // Get user by their ID and set attributes in the view
//        holder.mIdView.setText(mValues.get(position).id);
//        holder.mContentView.setText(mValues.get(position).content);
    }

    @Override
    public int getItemCount() {
        return participants.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final ImageView userImage;
        public final TextView userName;
        public String participant;

        public ViewHolder(FragmentGroupMemberBinding binding) {
            super(binding.getRoot());
            userImage = binding.participatingUserImage;
            userName = binding.participatingUserName;
        }
    }
}