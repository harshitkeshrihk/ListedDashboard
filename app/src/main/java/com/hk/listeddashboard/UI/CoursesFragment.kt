package com.hk.listeddashboard.UI

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hk.listeddashboard.R
import com.hk.listeddashboard.databinding.FragmentCoursesBinding
import com.hk.listeddashboard.databinding.FragmentLinksBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CoursesFragment : Fragment() {

    private var _binding: FragmentCoursesBinding? = null
    private val binding get() = _binding!!

    companion object{
        const val TAG = "CourseFragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentCoursesBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG,"onViewCreatedCalled")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG,"onResumeCalled")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG,"onPauseCalled")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG,"onStopCalled")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG,"onDestroyCalled")
    }

}