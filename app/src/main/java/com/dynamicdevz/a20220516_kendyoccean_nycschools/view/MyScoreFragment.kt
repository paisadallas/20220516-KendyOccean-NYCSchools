package com.dynamicdevz.a20220516_kendyoccean_nycschools.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.dynamicdevz.a20220516_kendyoccean_nycschools.BaseFragment
import com.dynamicdevz.a20220516_kendyoccean_nycschools.R
import com.dynamicdevz.a20220516_kendyoccean_nycschools.databinding.FragmentMyScoreBinding
import com.dynamicdevz.a20220516_kendyoccean_nycschools.model.Scores
import com.dynamicdevz.a20220516_kendyoccean_nycschools.network.StateAnswer


class MyScoreFragment : BaseFragment() {

    val args:MyScoreFragmentArgs by navArgs()

    private val binding by lazy {
        FragmentMyScoreBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModelSchool.getScoreByDbn(args.dbn)
        viewModelSchool.scores.observe(viewLifecycleOwner){
            when(it){
                is StateAnswer.LOADING -> {
                    Log.e("LOADING", "LOADING...")
                }
                is StateAnswer.SCORES -> {
                  //  Log.d("SUCCESS2", it.schools.toString())

                    it.scores.forEach{
                        Log.d("READING_WELL",it.schoolName.toString())

                        if (it.dbn.toString() == args.dbn){
                            Log.d("RESULT_SCHOOL",it.schoolName.toString())

                            bind(it)
                        }
                        else{
                            binding.tvSchoolName.text = "Empty"
                        }

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



        return binding.root
    }

    private fun bind(score:Scores) {
        binding.tvSchoolName.text = score.schoolName
        binding.tvTakers.text = context?.getString(R.string.school_taker, score.satWritingAvgScore)
        binding.tvCritical.text = context?.getString(R.string.school_critical,score.numOfSatTestTakers)
        binding.tvMath.text = context?.getString(R.string.school_math,score.satMathAvgScore)
        binding.tvWriting.text = context?.getString(R.string.school_writing,score.satCriticalReadingAvgScore)
    }

}