package com.example.campuscollab.view;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.campuscollab.databinding.FragmentAccountInformationBinding;
import com.example.campuscollab.domain.User;
import com.example.campuscollab.service.AuthService;
import com.example.campuscollab.service.UserService;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AccountInformationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AccountInformationFragment extends Fragment {

    private FragmentAccountInformationBinding binding;
    private String school;

    private AuthService authService = AuthService.getInstance();
    private UserService userService = UserService.getInstance();

    public AccountInformationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment AccountInformationFragment.
     */
    public static AccountInformationFragment newInstance() {
        AccountInformationFragment fragment = new AccountInformationFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            school = getArguments().getString("school_name");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAccountInformationBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.createAccountButton.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   binding.progressBar.setVisibility(View.VISIBLE);

                   String firstName = binding.firstName.getText().toString();
                   String lastName = binding.lastName.getText().toString();
                   String email = binding.email.getText().toString();
                   String password = binding.password.getText().toString();

                   User userWrite = new User();
                   userWrite.setFirstName(firstName);
                   userWrite.setLastName(lastName);
                   userWrite.setEmail(email);

                   try
                   {
                       User newUser = userService.registerUser(userWrite, password).get();

                       if (newUser == null)
                       {
                           Toast.makeText(getView().getContext(), "Register failed", Toast.LENGTH_SHORT).show();
                       }
                       else
                       {
                           Toast.makeText(getView().getContext(), "Register succeeded", Toast.LENGTH_SHORT).show();

                           Intent feed_transition = new Intent(getActivity(), FeedActivity.class);
                           startActivity(feed_transition);
                       }
                   }
                   catch(Exception e)
                   {
                       System.out.println("Error registering user: " + e.getMessage());
                   }
               }
           }
        );
    }
}
