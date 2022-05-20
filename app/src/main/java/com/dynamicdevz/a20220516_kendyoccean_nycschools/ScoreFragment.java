package com.dynamicdevz.a20220516_kendyoccean_nycschools;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dynamicdevz.a20220516_kendyoccean_nycschools.databinding.FragmentScoreBinding;
import com.dynamicdevz.a20220516_kendyoccean_nycschools.network.StateAnswer;

public class ScoreFragment extends BaseFragment {

    private FragmentScoreBinding binding;

    public ScoreFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentScoreBinding.inflate(inflater, container, false);


        getViewModelSchool().getScores().observe(getViewLifecycleOwner(), stateAnswer -> {
            if (stateAnswer instanceof StateAnswer.LOADING) {
                Log.d("LOADING", "LOADING....");
            } else if (stateAnswer instanceof StateAnswer.SCORES) {
                Log.d("SUCCESS", ((StateAnswer.SCORES) stateAnswer).getScores().get(0).getDbn());
                
            } else if (stateAnswer instanceof StateAnswer.ERROR){
                Log.e("ERROR", ((StateAnswer.ERROR) stateAnswer).getError().getLocalizedMessage());
            }
        });
        return binding.getRoot();
    }
}