package com.example.campuscollab.view;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.example.campuscollab.R;
import com.example.campuscollab.databinding.FragmentSettingsBinding;
import com.example.campuscollab.service.AuthService;
import com.example.campuscollab.service.UserService;

public class SettingsFragment extends Fragment {

    private FragmentSettingsBinding binding;

    private final AuthService authService = AuthService.getInstance();
    private final UserService userService = UserService.getInstance();

    Button signOutButton;
    SwitchCompat changeThemeSwitch;

    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        userService.isFromSettings = true;

        signOutButton = binding.signOutButton;
        changeThemeSwitch = binding.changeThemeSwitch;

        changeThemeSwitch.setChecked(userService.isDarkMode);

        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                authService.signOut();
                userService.isFromSettings = false;

                Intent landing_page_transition = new Intent(getActivity(), MainActivity.class);
                startActivity(landing_page_transition);
            }
        });

        changeThemeSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (userService.isDarkMode)
                {
                    userService.isDarkMode = false;
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                }
                else
                {
                    userService.isDarkMode = true;
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                }

            }
        });
    }
}