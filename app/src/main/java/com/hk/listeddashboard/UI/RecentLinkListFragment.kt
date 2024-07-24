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
import com.hk.listeddashboard.databinding.FragmentRecentLinkListBinding
import com.hk.listeddashboard.models.RecentLink
import com.hk.listeddashboard.models.TopLink
import com.hk.listeddashboard.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecentLinkListFragment : Fragment() {

    private var _binding: FragmentRecentLinkListBinding? = null
    private val binding get() = _binding!!
    private var listRecent: List<RecentLink>? = arrayListOf()


    private val viewModel by activityViewModels<MainViewModel>()
    private lateinit var recentLinksAdapter: RecentLinksAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding =  FragmentRecentLinkListBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recentLinksAdapter = RecentLinksAdapter()

        setUpRecyclerView()
        setObservers()
    }

    private fun setUpRecyclerView() {
        binding.recentLinklistRv.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = recentLinksAdapter
        }
    }

    private fun setObservers(){
        viewModel.mlinkType.observe(viewLifecycleOwner){
            listRecent = viewModel.mdata.value?.data?.recent_links
            if(it=="Recent" && listRecent!=null){
                recentLinksAdapter.setList(listRecent!!)
            }
        }
    }


}