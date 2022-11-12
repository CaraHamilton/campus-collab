package com.example.campuscollab.view;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.campuscollab.databinding.FragmentParticipatingGroupMemberCardBinding;
import com.example.campuscollab.domain.User;
import com.example.campuscollab.service.UserService;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class ParticipatingGroupMemberRecyclerViewAdapter extends RecyclerView.Adapter<ParticipatingGroupMemberRecyclerViewAdapter.ViewHolder> {

    private final List<String> participants;
    private UserService userService = UserService.getInstance();

    public ParticipatingGroupMemberRecyclerViewAdapter(List<String> participants) {
        this.participants = participants;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(FragmentParticipatingGroupMemberCardBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

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

        public ViewHolder(FragmentParticipatingGroupMemberCardBinding binding) {
            super(binding.getRoot());
            userImage = binding.participatingUserImage;
            userName = binding.participatingUserName;
        }
    }
}