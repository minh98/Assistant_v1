package com.example.minh98.assistant_v1

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.view.*
import android.widget.TextView

class IntroActivity : AppCompatActivity() {

	/**
	 * The [android.support.v4.view.PagerAdapter] that will provide
	 * fragments for each of the sections. We use a
	 * [FragmentPagerAdapter] derivative, which will keep every
	 * loaded fragment in memory. If this becomes too memory intensive, it
	 * may be best to switch to a
	 * [android.support.v4.app.FragmentStatePagerAdapter].
	 */
	private var mSectionsPagerAdapter: SectionsPagerAdapter? = null

	/**
	 * The [ViewPager] that will host the section contents.
	 */
	private var mViewPager: ViewPager? = null

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_intro)

		// Create the adapter that will return a fragment for each of the three
		// primary sections of the activity.
		mSectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager)

		// Set up the ViewPager with the sections adapter.
		mViewPager = findViewById(R.id.container)
		mViewPager!!.adapter = mSectionsPagerAdapter


		val fab = findViewById<FloatingActionButton>(R.id.fab)
		fab.setOnClickListener { view ->
			Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
					.setAction("Action", null).show()
		}

	}


	override fun onCreateOptionsMenu(menu: Menu): Boolean {
		// Inflate the menu; this adds items to the action bar if it is present.
		menuInflater.inflate(R.menu.menu_intro, menu)
		return true
	}

	override fun onOptionsItemSelected(item: MenuItem): Boolean {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		val id = item.itemId


		return if (id == R.id.action_settings) {
			true
		} else super.onOptionsItemSelected(item)

	}


	/**
	 * A [FragmentPagerAdapter] that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	inner class SectionsPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

		override fun getItem(position: Int): Fragment =// getItem is called to instantiate the fragment for the given page.
		// Return a PlaceholderFragment (defined as a static inner class below).
				PlaceholderFragment.newInstance(position + 1)

		override fun getCount(): Int =// Show 3 total pages.
				3

		override fun getPageTitle(position: Int): CharSequence? {
			when (position) {
				0 -> return "SECTION 1"
				1 -> return "SECTION 2"
				2 -> return "SECTION 3"
			}
			return null
		}
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	class PlaceholderFragment : Fragment() {

		override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
		                          savedInstanceState: Bundle?): View? {
			val rootView = inflater!!.inflate(R.layout.fragment_intro, container, false)
			val textView = rootView.findViewById<TextView>(R.id.section_label)
			textView.text = getString(R.string.section_format, arguments.getInt(ARG_SECTION_NUMBER))
			return rootView
		}

		companion object {
			/**
			 * The fragment argument representing the section number for this
			 * fragment.
			 */
			private const val ARG_SECTION_NUMBER = "section_number"

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
