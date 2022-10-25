package com.example.campuscollab.view;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.campuscollab.R;
import com.example.campuscollab.databinding.FeedPageBinding;
import com.example.campuscollab.databinding.FragmentAccountInformationBinding;
import com.example.campuscollab.databinding.FragmentSettingsBinding;
import com.example.campuscollab.domain.User;
import com.example.campuscollab.service.AuthService;
import com.example.campuscollab.service.UserService;

public class SettingsFragment extends Fragment {

    private FragmentSettingsBinding binding;

    private AuthService authService = AuthService.getInstance();

    Button signOutButton;

    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        signOutButton = binding.signOutButton;

        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                authService.signOut();

                Intent landing_page_transition = new Intent(getActivity(), MainActivity.class);
                startActivity(landing_page_transition);
            }
        });
    }
}