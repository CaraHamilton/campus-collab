package com.example.campuscollab.view;

import android.content.res.Resources;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.campuscollab.R;
import com.example.campuscollab.databinding.FragmentRequestsBinding;
import com.example.campuscollab.domain.Project;
import com.example.campuscollab.domain.Request;
import com.example.campuscollab.service.RequestService;
import com.google.firebase.Timestamp;

import java.util.ArrayList;
import java.util.List;

public class RequestsFragment extends Fragment {
    private FragmentRequestsBinding binding;
    private TextView incomingRequests;
    private TextView pendingRequests;
    private RecyclerView incomingRecycler;
    private RecyclerView pendingRecycler;

    private final RequestService requestService = RequestService.getInstance();

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


        try {
            /*List<Request> incomingRequestsList = new ArrayList<>();
            List<Request> pendingRequestsList = new ArrayList<>();

            incomingRequestsList.add(new Request("owner id1", "project name1", "requester id1",
                    "requester name1", "other", "other", "other",
                    "other", "other", "other", Timestamp.now()));
            incomingRequestsList.add(new Request("owner id2", "owner name2", "requester id2",
                    "requester name1", "other", "other", "other",
                    "other", "other", "other", Timestamp.now()));

            pendingRequestsList.add(new Request("requesting one", "requester id2",
                    "requester name1", "other", "other", "other",
                    "other", "other", "other", Timestamp.now()));

            pendingRecycler.setAdapter(new RequestsRecyclerViewAdapter(pendingRequestsList));
            pendingRecycler.setLayoutManager(new LinearLayoutManager(pendingRecycler.getContext()));

            incomingRecycler.setAdapter(new RequestsRecyclerViewAdapter(incomingRequestsList));
            incomingRecycler.setLayoutManager(new LinearLayoutManager(incomingRecycler.getContext()));*/

            List<Request> incomingRequestsList = requestService.getSentRequests().get();
            if (incomingRequests == null)
            {
                Toast.makeText(requireView().getContext(), "Couldn't retrieve any incoming requests", Toast.LENGTH_SHORT).show();
            } else {
                incomingRecycler.setAdapter(new RequestsRecyclerViewAdapter(incomingRequestsList, true));
                incomingRecycler.setLayoutManager(new LinearLayoutManager(incomingRecycler.getContext()));
            }

            List<Request> pendingRequestsList = requestService.getReceivedRequests().get();
            if (pendingRequestsList == null)
            {
                Toast.makeText(requireView().getContext(), "Couldn't retrieve any pending requests", Toast.LENGTH_SHORT).show();
            } else {
                pendingRecycler.setAdapter(new RequestsRecyclerViewAdapter(pendingRequestsList, false));
                pendingRecycler.setLayoutManager(new LinearLayoutManager(pendingRecycler.getContext()));
            }

        } catch (Exception e) {
            Toast.makeText(requireView().getContext(), e.getMessage() , Toast.LENGTH_SHORT).show();
        }

        pendingRequests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int activeColor = getActiveColor();
                int inactiveColor = getInactiveColor();

                incomingRecycler.setVisibility(View.INVISIBLE);
                RequestsFragment.this.incomingRequests.setTextColor(inactiveColor);
                pendingRequests.setTextColor(activeColor);
                pendingRecycler.setVisibility(View.VISIBLE);
            }
        });

        this.incomingRequests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int activeColor = getActiveColor();
                int inactiveColor = getInactiveColor();

                pendingRecycler.setVisibility(View.INVISIBLE);
                pendingRequests.setTextColor(inactiveColor);
                RequestsFragment.this.incomingRequests.setTextColor(activeColor);
                incomingRecycler.setVisibility(View.VISIBLE);
            }
        });

        return binding.getRoot();
    }

    private int getActiveColor() {
        TypedValue typedValue = new TypedValue();
        Resources.Theme theme = requireContext().getTheme();
        theme.resolveAttribute(androidx.appcompat.R.attr.colorControlActivated, typedValue, true);
        @ColorInt int color = typedValue.data;
        return color;
    }

    private int getInactiveColor() {
        TypedValue typedValue = new TypedValue();
        Resources.Theme theme = requireContext().getTheme();
        theme.resolveAttribute(androidx.appcompat.R.attr.colorControlNormal, typedValue, true);
        @ColorInt int color = typedValue.data;
        return color;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
