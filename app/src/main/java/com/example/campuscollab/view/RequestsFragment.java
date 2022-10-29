package com.example.campuscollab.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.campuscollab.R;
import com.example.campuscollab.databinding.FragmentRequestsBinding;
import com.example.campuscollab.domain.Project;
import com.example.campuscollab.domain.Request;
import com.google.firebase.Timestamp;

import java.util.ArrayList;
import java.util.List;

public class RequestsFragment extends Fragment {
    private FragmentRequestsBinding binding;
    private TextView incomingRequests;
    private TextView pendingRequests;
    private RecyclerView incomingRecycler;
    private RecyclerView pendingRecycler;

    public RequestsFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentRequestsBinding.inflate(inflater, container, false);

        incomingRequests = binding.incomingrequests;
        pendingRequests = binding.pendingrequests;
        incomingRecycler = binding.incomingrecycler;
        pendingRecycler = binding.pendingrecycler;

            //TODO make backend call of some kind or generate requests from projects
            Project project = new Project("name", "ownerid", "description", new ArrayList<>(),
                    Timestamp.now(), Timestamp.now(), 5);
            List<Request> incomingRequests = new ArrayList<>();
            List<Request> pendingRequestsList = new ArrayList<>();

            incomingRequests.add(new Request("owner id1", "owner name1",
                                  "requester id1", "requester name1", project));
            incomingRequests.add(new Request("owner id2", "owner name2",
                                  "requester id2", "requester name2", project));
            incomingRequests.add(new Request("owner id3", "owner name3",
                    "requester id3", "requester name3", project));
            incomingRequests.add(new Request("owner id4", "owner name4",
                    "requester id4", "requester name4", project));
            incomingRequests.add(new Request("owner id5", "owner name5",
                    "requester id5", "requester name5", project));

            pendingRequestsList.add(new Request("requesing one", "owner name5",
                "requester id5", "pending page", project));

            pendingRecycler.setAdapter(new RequestsRecyclerViewAdapter(pendingRequestsList));
            pendingRecycler.setLayoutManager(new LinearLayoutManager(pendingRecycler.getContext()));

            incomingRecycler.setAdapter(new RequestsRecyclerViewAdapter(incomingRequests));
            incomingRecycler.setLayoutManager(new LinearLayoutManager(incomingRecycler.getContext()));

        pendingRequests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                incomingRecycler.setVisibility(View.INVISIBLE);
                RequestsFragment.this.incomingRequests.setTextColor(getResources().getColor(R.color.transparent_white));
                pendingRequests.setTextColor(getResources().getColor(R.color.white));
                pendingRecycler.setVisibility(View.VISIBLE);
            }
        });

        this.incomingRequests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pendingRecycler.setVisibility(View.INVISIBLE);
                pendingRequests.setTextColor(getResources().getColor(R.color.transparent_white));
                RequestsFragment.this.incomingRequests.setTextColor(getResources().getColor(R.color.white));
                incomingRecycler.setVisibility(View.VISIBLE);
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
