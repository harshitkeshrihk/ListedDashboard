package com.hk.listeddashboard.UI

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.utils.Utils
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.hk.listeddashboard.R
import com.hk.listeddashboard.Utils.convertDateFormat
import com.hk.listeddashboard.Utils.getDayNameFromDate
import com.hk.listeddashboard.adapter.CardAdapter
import com.hk.listeddashboard.adapter.MyPagerAdapter
import com.hk.listeddashboard.databinding.FragmentLinksBinding
import com.hk.listeddashboard.models.CardItem
import com.hk.listeddashboard.models.OverallUrlChart
import com.hk.listeddashboard.models.UserResponse
import com.hk.listeddashboard.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*


@AndroidEntryPoint
class LinksFragment : Fragment() {

    private lateinit var userResponse: UserResponse
    private val viewModel : MainViewModel by activityViewModels()
    private val tabTitles = arrayListOf("Top links","Recent links")
    private lateinit var cardAdapter: CardAdapter
    private var _binding: FragmentLinksBinding? = null
    private val binding get() = _binding!!



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentLinksBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cardAdapter = CardAdapter()

        if (viewModel.mdata.value != null) {
            userResponse = viewModel.mdata.value!!
            updateUI(userResponse)
        } else {
            setUpObservers()
        }

        setUpRecyclerView()
        setUpTabLayoutWithViewPager()
        setupTabLayoutListener()

        workInProgressUI()
    }

    private fun setUpObservers(){
        viewModel.mdata.observe(viewLifecycleOwner){
            userResponse = it
            updateUI(userResponse)
        }
    }

    private fun setUpCardData(userResponse: UserResponse){
        val cardList = listOf(
            CardItem(com.hk.listeddashboard.R.drawable.ic_software,
                userResponse.today_clicks.toString(),
                "Today's clicks"),
            CardItem(com.hk.listeddashboard.R.drawable.ic_pin,
                if(userResponse.top_location=="")  "Unavailable"
                else  userResponse.top_location,
                "Top Location"),
            CardItem(com.hk.listeddashboard.R.drawable.ic_generic,
                if(userResponse.top_source=="")  "Unavailable"
                else  userResponse.top_source, "Top source"),
            CardItem(com.hk.listeddashboard.R.drawable.ic_baseline_access_time_24,
                if(userResponse.startTime=="")  "Unavailable"
                else  userResponse.startTime, "Start Time")
        )
        cardAdapter.setList(cardList)
    }
    private fun setUpRecyclerView(){
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL, false)
            adapter = cardAdapter
        }
    }

    private fun setUpTabLayoutWithViewPager(){
        binding.viewpager.adapter = MyPagerAdapter(this)
        TabLayoutMediator(binding.tablayout,binding.viewpager){ tab,position ->
            tab.text = tabTitles[position]
        }.attach()

        for(i in 0..1){
            val textView =
                LayoutInflater.from(requireContext()).inflate(R.layout.tab_layout_title, null)
                        as TextView
            binding.tablayout.getTabAt(i)?.customView = textView
        }
    }

    private fun setupTabLayoutListener(){
        binding.tablayout.setOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                binding.viewpager.setCurrentItem(tab.position)
                when(tab?.position){
                    0-> {
                        viewModel.selectType("Top")
                    }
                    1-> {
                        viewModel.selectType("Recent")
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
    }

    private fun setupLineChart(overallUrlChart: OverallUrlChart) {
        binding.lineChart.setTouchEnabled(true)
        binding.lineChart.isDragEnabled = true
        binding.lineChart.setScaleEnabled(true)
        binding.lineChart.setPinchZoom(true)

        val xAxis: XAxis =  binding.lineChart.xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.granularity = 1f
        xAxis.setCenterAxisLabels(true)
        xAxis.valueFormatter = IndexAxisValueFormatter(getXAxisValues(overallUrlChart))


        val rightAxis: YAxis = binding.lineChart.axisRight
        rightAxis.isEnabled = false // Hide right-hand side scaling


//        val leftAxis: YAxis = binding.lineChart.axisLeft
//        leftAxis.axisMinimum = 0f // Set the minimum value to 0 to ensure only positive values


        binding.lineChart.description.isEnabled = false
        binding.lineChart.legend.isEnabled = false
    }

    private fun setData(overallUrlChart: OverallUrlChart) {
        val entries: ArrayList<Entry> = ArrayList()
        val dataClass = overallUrlChart

        val fields = dataClass.javaClass.declaredFields
        for (i in fields.indices) {
            fields[i].isAccessible = true
            val value = fields[i].getInt(dataClass)
            entries.add(Entry(i.toFloat(), value.toFloat()))
        }

        val dataSet = LineDataSet(entries, "Overall Url Chart")
        dataSet.setDrawValues(false)
        dataSet.color = Color.parseColor("#FF0E6FFF")
        dataSet.lineWidth = 2f
        dataSet.setDrawCircles(false)

        dataSet.setDrawFilled(true)
        if (Utils.getSDKInt() >= 18) {
            // fill drawable only supported on api level 18 and above
            val drawable = ContextCompat.getDrawable(requireContext(), com.hk.listeddashboard.R.drawable.fade_blue)
            dataSet.setFillDrawable(drawable)
        } else {
            dataSet.setFillColor(Color.BLACK)
        }


        val lineData = LineData(dataSet)
        binding.lineChart.data = lineData
        binding.lineChart.invalidate()
    }

    private fun getXAxisValues(overallUrlChart: OverallUrlChart): List<String> {
        val xAxisValues: MutableList<String> = ArrayList()
        val dataClass = overallUrlChart
        val fields = dataClass.javaClass.declaredFields
            for (field in fields) {
                xAxisValues.add(getDayNameFromDate(field.name))
            }


        var s: String = convertDateFormat(fields[0].name)  + "-" +  convertDateFormat(fields[fields.size-1].name)
        binding.datestartTv.text = s

        return xAxisValues
    }

    private fun updateUI(userResponse: UserResponse) {
        //setting up viewpager with initial top links
        viewModel.selectType("Top")
        binding.progressBar.visibility = View.GONE
        setUpCardData(userResponse)
        if(userResponse.data.overall_url_chart!=null){
            setupLineChart(userResponse.data.overall_url_chart)
            setData(userResponse.data.overall_url_chart)
        }
    }

    private fun showToastWIP(){
        Toast.makeText(requireContext(),"Work In Progress",Toast.LENGTH_SHORT).show()
    }

    private fun workInProgressUI(){
        binding.settingsCv.setOnClickListener{
            showToastWIP()
        }
        binding.viewAnalyticsBtn.setOnClickListener{
            showToastWIP()
        }
        binding.talkWithUs.setOnClickListener{
            showToastWIP()
        }
        binding.frequentlyAQ.setOnClickListener{
            showToastWIP()
        }
        binding.viewAllLinks.setOnClickListener{
            showToastWIP()
        }
        binding.searchCv.setOnClickListener{
            showToastWIP()
        }
    }

    companion object {
        const val TAG = "LinksFragment"
    }
}