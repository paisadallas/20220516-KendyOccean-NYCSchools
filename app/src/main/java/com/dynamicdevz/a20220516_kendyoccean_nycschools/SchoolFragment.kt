package com.dynamicdevz.a20220516_kendyoccean_nycschools

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.dynamicdevz.a20220516_kendyoccean_nycschools.adapter.SchoolAdapter
import com.dynamicdevz.a20220516_kendyoccean_nycschools.databinding.FragmentSchoolBinding
import com.dynamicdevz.a20220516_kendyoccean_nycschools.network.StateAnswer

class SchoolFragment : BaseFragment() {

    private val binding by lazy {
        FragmentSchoolBinding.inflate(layoutInflater)
    }

    private val schoolAdapter by lazy {
        SchoolAdapter(){
            var data = it.dbn.toString()

          // viewModelSchool.dbnSchool = it.dbn.toString()
            var intention = SchoolFragmentDirections.actionSchoolFragmentToMyScoreFragment(data)
            findNavController().navigate(intention)
         //   findNavController().navigate(R.id.action_schoolFragment_to_scoreFragment)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding.rvSchool.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL,false)
            adapter = schoolAdapter
        }

        viewModelSchool.schools.observe(viewLifecycleOwner) {
            when (it) {
                is StateAnswer.LOADING -> {
                    Log.e("LOADING", "LOADING...")
                }
                is StateAnswer.SCHOOLS -> {
                    Log.d("SUCCESS1", it.schools.first().dbn.toString())
                    it.schools.let {
                        schoolAdapter.update(it)

                    }
                }
                is StateAnswer.ERROR -> {
                    Log.e("ERROR", it.error.localizedMessage)
                }
                else -> {
                    //no-op
                }
            }
        }

        viewModelSchool.getAllSchools()

        return binding.root
    }
}