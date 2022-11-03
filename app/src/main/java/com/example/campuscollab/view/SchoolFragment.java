package com.example.campuscollab.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.campuscollab.R;
import com.example.campuscollab.databinding.SchoolFragmentBinding;

public class SchoolFragment extends Fragment {
    private SchoolFragmentBinding binding;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = SchoolFragmentBinding.inflate(inflater, container, false);
        LinearLayout byu = binding.byuSchool;
        LinearLayout byui = binding.byuiSchool;
        LinearLayout byuh = binding.byuhSchool;

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
