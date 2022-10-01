package com.example.campuscollab.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.campuscollab.databinding.FragmentAccountInformationBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AccountInformationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AccountInformationFragment extends Fragment {

    private FragmentAccountInformationBinding binding;

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

               }
           }
        );
    }
}