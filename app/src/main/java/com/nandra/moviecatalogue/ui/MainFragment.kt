package com.nandra.moviecatalogue.ui

import android.content.SharedPreferences
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.preference.PreferenceManager
import com.google.android.material.tabs.TabLayout
import com.nandra.moviecatalogue.R
import com.nandra.moviecatalogue.adapter.ViewPagerPageAdapter
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment() {

    private lateinit var viewPagerPageAdapter: ViewPagerPageAdapter
    private lateinit var sharedPreferences: SharedPreferences
    private var currentLanguage: String? = ""
    private var initialLanguage: String? = null
    private lateinit var languageEnglishValue : String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val activity = activity as AppCompatActivity
        activity.setSupportActionBar(main_fragment_toolbar)
        setHasOptionsMenu(true)

        prepareSharedPreferences()

        viewPagerPageAdapter = ViewPagerPageAdapter(
            childFragmentManager,
            main_fragment_tab_layout.tabCount
        )
        main_fragment_viewpager.adapter = viewPagerPageAdapter

        main_fragment_tab_layout.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener{
            override fun onTabReselected(p0: TabLayout.Tab?) {}
            override fun onTabUnselected(p0: TabLayout.Tab?) {}
            override fun onTabSelected(tab: TabLayout.Tab?) {
                main_fragment_viewpager.currentItem = tab?.position!!
            }
        })
        main_fragment_viewpager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(main_fragment_tab_layout))
        checkLanguage(currentLanguage!!)
    }

    private fun checkLanguage(language: String) {
        if (initialLanguage == null) {
            initialLanguage = language
            setTabItemTitle(language)
        } else {
            if(currentLanguage != initialLanguage){
                setTabItemTitle(language)
                initialLanguage = currentLanguage
            }
        }
    }

    private fun setTabItemTitle(language: String) {
        if (language == languageEnglishValue) {
            main_fragment_tab_layout.getTabAt(0)?.text = getString(R.string.main_tab_1_title_en)
            main_fragment_tab_layout.getTabAt(1)?.text = getString(R.string.main_tab_2_title_en)
        } else {
            main_fragment_tab_layout.getTabAt(0)?.text = getString(R.string.main_tab_1_title_id)
            main_fragment_tab_layout.getTabAt(1)?.text = getString(R.string.main_tab_2_title_id)
        }
    }

    private fun prepareSharedPreferences() {
        languageEnglishValue = getString(R.string.preferences_language_value_english)
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity)
        currentLanguage = sharedPreferences.getString(getString(R.string.preferences_language_key),
            getString(R.string.preferences_language_value_english))
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.main_menu_action_settings) {
            //findNavController(R.id.main_fragment_container).navigate(R.id.action_mainFragment_to_settingsPreferenceFragment)
            findNavController().navigate(R.id.action_mainFragment_to_settingsPreferenceFragment)
        }
        return super.onOptionsItemSelected(item)
    }
}