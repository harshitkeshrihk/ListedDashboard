package com.hk.listeddashboard.UI

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.hk.listeddashboard.R
import com.hk.listeddashboard.adapter.LinksAdapter
import com.hk.listeddashboard.adapter.RecentLinksAdapter
import com.hk.listeddashboard.databinding.FragmentLinksListBinding
import com.hk.listeddashboard.models.RecentLink
import com.hk.listeddashboard.models.TopLink
import com.hk.listeddashboard.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LinksListFragment : Fragment() {

    private var _binding: FragmentLinksListBinding? = null
    private val binding get() = _binding!!
    private var listTop: List<TopLink> = arrayListOf()


    private val viewModel by activityViewModels<MainViewModel>()

    lateinit var linkListAdapter : LinksAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding =  FragmentLinksListBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        linkListAdapter = LinksAdapter()
        binding.linkslistRv.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = linkListAdapter
        }
//        setObservers()
        viewModel.mdata.observe(viewLifecycleOwner){
            Log.d("hello6","hii")
            listTop = it.data.top_links
            if(viewModel.mlinkType.value == "Top"){
                Log.d("hello5",listTop.toString())
                linkListAdapter.setList(listTop)
                linkListAdapter.notifyDataSetChanged()
            }
        }
        viewModel.mlinkType.observe(viewLifecycleOwner){
            if(it=="Top"){
                linkListAdapter.setList(listTop)
            }else if(it=="Recent"){

            }
        }
    }

    private fun setObservers(){


    }

}