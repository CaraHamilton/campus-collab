package com.example.campuscollab.view;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.campuscollab.databinding.FragmentGroupMemberBinding;
import com.example.campuscollab.domain.User;
import com.example.campuscollab.service.UserService;

import java.util.List;
import java.util.concurrent.ExecutionException;

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
        try {
            User user = userService.getAnUser(holder.participant).get();
            holder.userName.setText(user.getFirstName() + " " + user.getLastName());
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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