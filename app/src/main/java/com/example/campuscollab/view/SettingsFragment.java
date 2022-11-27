package com.example.campuscollab.view;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.fragment.NavHostFragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.campuscollab.R;
import com.example.campuscollab.databinding.FragmentSettingsBinding;
import com.example.campuscollab.domain.User;
import com.example.campuscollab.service.AuthService;
import com.example.campuscollab.service.UserService;

public class SettingsFragment extends Fragment {

    private FragmentSettingsBinding binding;

    private final AuthService authService = AuthService.getInstance();
    private final UserService userService = UserService.getInstance();

    Button signOutButton;
    SwitchCompat changeThemeSwitch;

    EditText firstNameTextField;
    EditText lastNameTextField;
    Button saveButton;

    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
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

        firstNameTextField = binding.firstNameTextField;
        lastNameTextField = binding.lastNameTextField;
        saveButton = binding.saveButton;

        saveButton.setEnabled(false);
        saveButton.setBackgroundColor(getContext().getResources().getColor(R.color.input_gray));

        changeThemeSwitch.setChecked(userService.isDarkMode);

        firstNameTextField.addTextChangedListener(textWatcher);
        lastNameTextField.addTextChangedListener(textWatcher);

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

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                User currentUser = userService.getCurrentUser();

                String firstNameInput = firstNameTextField.getText().toString().trim();
                String lastNameInput = lastNameTextField.getText().toString().trim();

                if (!firstNameInput.isEmpty())
                {
                    currentUser.setFirstName(firstNameInput);
                }

                if (!lastNameInput.isEmpty())
                {
                    currentUser.setLastName(lastNameInput);
                }

                userService.updateUser(currentUser);
                Toast.makeText(requireView().getContext(), "Name changed", Toast.LENGTH_SHORT).show();
            }
        });

        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                authService.signOut();
                userService.isFromSettings = false;

                Intent landing_page_transition = new Intent(getActivity(), MainActivity.class);
                startActivity(landing_page_transition);
            }
        });
    }

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            String firstNameInput = firstNameTextField.getText().toString().trim();
            String lastNameInput = lastNameTextField.getText().toString().trim();

            if (!firstNameInput.isEmpty() || !lastNameInput.isEmpty())
            {
                saveButton.setEnabled(true);

                TypedValue typedValue = new TypedValue();
                Resources.Theme theme = requireContext().getTheme();
                theme.resolveAttribute(androidx.appcompat.R.attr.colorAccent, typedValue, true);
                @ColorInt int color = typedValue.data;

                saveButton.setBackgroundColor(color);
            }
            else
            {
                saveButton.setEnabled(false);
                saveButton.setBackgroundColor(getContext().getResources().getColor(R.color.input_gray));
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };
}