package com.dongumen.nickolas.kpiweeks.fragments


import android.content.Context
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.dongumen.nickolas.kpiweeks.R
import com.dongumen.nickolas.kpiweeks.model.enteties.Item
import com.dongumen.nickolas.kpiweeks.presenters.WeekPresenter
import com.dongumen.nickolas.kpiweeks.utils.DayInformationUtil
import com.dongumen.nickolas.kpiweeks.views.WeekView
import com.dongumen.nickolas.kpiweeks.widgets.FragmentChanger
import com.dongumen.nickolas.kpiweeks.widgets.adapters.WeekAdapter
import com.dongumen.nickolas.kpiweeks.widgets.adapters.delegateAdapter.CompositeDelegateAdapter
import com.dongumen.nickolas.kpiweeks.widgets.adapters.week.WeekHeaderAdapter
import com.dongumen.nickolas.kpiweeks.widgets.adapters.week.WeekLessonAdapter
import kotlinx.android.synthetic.main.fragment_week.*
import org.koin.android.ext.android.inject


class WeekFragment : MvpAppCompatFragment(), WeekView, WeekAdapter.OnDeleteListener {

    var scroll = false
    @InjectPresenter
    lateinit var presenter: WeekPresenter
    private val dayInformationUtil: DayInformationUtil by inject()

    private val weekAdapter by lazy {
        CompositeDelegateAdapter.Builder<Item>()
                .add(WeekHeaderAdapter())
                .add(WeekLessonAdapter {})
                .build()
    }

    private lateinit var fragmentChanger: FragmentChanger

    private val week: Int by lazy {
        arguments?.getInt("weekNumber") ?: 0
    }
    private val name: String by lazy {
        arguments?.getString("name", "") ?: ""
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        fragmentChanger = context as FragmentChanger
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter.view = this
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_week, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycler_view.layoutManager = LinearLayoutManager(activity)
        recycler_view.adapter = weekAdapter
        presenter.getSchedule(name, week)
    }

    private fun scroll() {
        if (scroll) {
            val position = dayInformationUtil.scrollTo()
            if (position == 8) fragmentChanger.change()
            recycler_view.scrollToPosition(position * 2)
            scroll = false
        }
    }


    override fun showSchedule(schedule: List<Item>) {
        weekAdapter.swapData(schedule)
        scroll()
    }


    override fun delete(day: Int, position: Int) {
        presenter.deleteItem(week, day, position)
    }

    companion object {
        fun newInstance(weekNumber: Int, name: String): WeekFragment {
            val weekFragment = WeekFragment()
            val bundle = Bundle()
            bundle.putString("name", name)
            bundle.putInt("weekNumber", weekNumber)
            weekFragment.arguments = bundle
            return weekFragment
        }
    }
}