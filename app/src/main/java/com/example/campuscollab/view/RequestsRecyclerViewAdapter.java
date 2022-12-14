package com.example.campuscollab.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.campuscollab.databinding.FragmentRequestItemBinding;
import com.example.campuscollab.domain.Request;
import com.example.campuscollab.domain.User;
import com.example.campuscollab.service.RequestService;
import com.example.campuscollab.service.UserService;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class RequestsRecyclerViewAdapter extends RecyclerView.Adapter<RequestsRecyclerViewAdapter.RequestViewHolder> {
    private List<Request> requests;
    private UserService userService = UserService.getInstance();
    private boolean canAcceptReject;
    Context context;
    CardView pendingRequestIcon;
    byte[] imageBytes = null;

    private final RequestService requestService = RequestService.getInstance();

    public RequestsRecyclerViewAdapter(Context context, CardView pendingRequestIcon, List<Request> requests, boolean canAcceptReject) {
        this.context = context;
        this.pendingRequestIcon = pendingRequestIcon;
        this.requests = requests;
        this.canAcceptReject = canAcceptReject;
    }

    @NonNull
    @Override
    public RequestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RequestViewHolder(FragmentRequestItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RequestViewHolder holder, int position) {
        Request currReq = requests.get(position);
        holder.message.setText(currReq.getRequesterName() + " is requesting to join " + currReq.getProjectName());

        try {
            User user = userService.getAnUser(currReq.getRequesterId()).get();

            if (user.getImagePath() != null)
            {
                imageBytes = userService.getImageBytes(user.getImagePath()).get();
            }

            if (imageBytes != null)
            {
                Bitmap bmp = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                BitmapDrawable ob = new BitmapDrawable(context.getResources(), bmp);
                holder.image.setImageDrawable(ob);
            }

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Intent profile_transition = new Intent(context, ProfileActivity.class);
                    profile_transition.putExtra("user_id", currReq.getRequesterId());
                    context.startActivity(profile_transition);
                }
        });

        if (canAcceptReject) {
            holder.acceptButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    boolean isDeleted = false;

                    try {
                        requestService.acceptRequest(currReq).get();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    try {
                        isDeleted = requestService.deleteRequest(currReq.getId()).get();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (isDeleted) {
                        requests.remove(currReq);
                        notifyDataSetChanged();

                        try {
                            if (requestService.getSentRequests().get().size() == 0)
                            {
                                pendingRequestIcon.setVisibility(View.INVISIBLE);
                            }
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        Toast.makeText(view.getContext(), "Request accepted", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(view.getContext(), "Failed to delete request after accepting", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            holder.rejectButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    boolean isDeleted = false;
                    try {
                        requestService.rejectRequest(currReq).get();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    try {
                        isDeleted = requestService.deleteRequest(currReq.getId()).get();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (isDeleted) {
                        requests.remove(currReq);
                        notifyDataSetChanged();

                        try {
                            if (requestService.getSentRequests().get().size() == 0)
                            {
                                pendingRequestIcon.setVisibility(View.INVISIBLE);
                            }
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        Toast.makeText(view.getContext(), "Request rejected", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(view.getContext(), "Failed to delete request after rejection", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            holder.rejectButton.setVisibility(View.GONE);
            holder.acceptButton.setVisibility(View.GONE);
            holder.message.setMaxWidth(375);
        }
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
