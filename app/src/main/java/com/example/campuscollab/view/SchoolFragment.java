package com.example.campuscollab.view;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.Layout;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavAction;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.campuscollab.R;
import com.example.campuscollab.databinding.SchoolFragmentBinding;
import com.example.campuscollab.service.SchoolService;

public class SchoolFragment extends Fragment {
    private SchoolFragmentBinding binding;
    private SchoolService schoolService;
    private LinearLayout byu;
    private LinearLayout byui;
    private LinearLayout byuh;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = SchoolFragmentBinding.inflate(inflater, container, false);
        byu = binding.byuSchool;
        byui = binding.byuiSchool;
        byuh = binding.byuhSchool;

        byu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("school_name", "Brigham Young University");
                NavHostFragment.findNavController(SchoolFragment.this)
                        .navigate(R.id.action_schoolFragment_to_accountInformationFragment, bundle);
            }
        });
        byui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("school_name", "Brigham Young University - Idaho");
                NavHostFragment.findNavController(SchoolFragment.this)
                        .navigate(R.id.action_schoolFragment_to_accountInformationFragment, bundle);
            }
        });
        byuh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("school_name", "Brigham Young University - Hawaii");
                NavHostFragment.findNavController(SchoolFragment.this)
                        .navigate(R.id.action_schoolFragment_to_accountInformationFragment, bundle);
            }
        });
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}