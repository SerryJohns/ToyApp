package com.example.johnseremba.toyapp

import android.animation.ArgbEvaluator
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup

import kotlinx.android.synthetic.main.activity_onboarding.*
import kotlinx.android.synthetic.main.fragment_onboarding.view.*

class OnboardingActivity : AppCompatActivity() {

    private var mSectionsPagerAdapter: SectionsPagerAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding)

        mSectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager)
        container.adapter = mSectionsPagerAdapter
        val evaluator = ArgbEvaluator()

        val colorList = listOf(
                ContextCompat.getColor(this, R.color.cyan),
                ContextCompat.getColor(this, R.color.orange),
                ContextCompat.getColor(this, R.color.green)
        )

        container.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {
                val colorUpdate = evaluator.evaluate(p1, colorList[p0], colorList[if(p0 == 2) p0 else p0 + 1]).toString().toInt()
                container.setBackgroundColor(colorUpdate)
            }

            override fun onPageScrollStateChanged(p0: Int) { }

            override fun onPageSelected(p0: Int) {
                updateIndicators(p0)
                when(p0) {
                    0 -> container.setBackgroundColor(colorList[0])
                    1 -> container.setBackgroundColor(colorList[1])
                    2 -> container.setBackgroundColor(colorList[2])
                }
                intro_btn_next.visibility = if (p0 == 2) View.GONE else View.VISIBLE
                intro_btn_finish.visibility = if (p0 == 2) View.VISIBLE else View.GONE
            }
        })
    }

    fun updateIndicators(position: Int) {
        val indicators = listOf(intro_indicator_0, intro_indicator_1, intro_indicator_2)
        for (i in 0 until indicators.size) {
            indicators[i].setBackgroundResource(
                    if (i == position)
                        R.drawable.indicator_selected
                    else
                        R.drawable.indicator_unselected
            )
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_onboarding, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        if (id == R.id.action_settings) {
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    inner class SectionsPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        override fun getItem(position: Int): Fragment {
            return PlaceholderFragment.newInstance(position + 1)
        }

        override fun getCount(): Int {
            return 3
        }
    }

    class PlaceholderFragment : Fragment() {

        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                                  savedInstanceState: Bundle?): View? {
            val rootView = inflater.inflate(R.layout.fragment_onboarding, container, false)
            rootView.section_label.text = getString(R.string.section_format, arguments?.getInt(ARG_SECTION_NUMBER))
            return rootView
        }

        companion object {
            /**
             * The fragment argument representing the section number for this
             * fragment.
             */
            private val ARG_SECTION_NUMBER = "section_number"

            /**
             * Returns a new instance of this fragment for the given section
             * number.
             */
            fun newInstance(sectionNumber: Int): PlaceholderFragment {
                val fragment = PlaceholderFragment()
                val args = Bundle()
                args.putInt(ARG_SECTION_NUMBER, sectionNumber)
                fragment.arguments = args
                return fragment
            }
        }
    }
}
