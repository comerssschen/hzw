package com.hibay.goldking.ui.act

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.blankj.utilcode.util.BarUtils
import com.hibay.goldking.R
import com.hibay.goldking.base.BaseActivity
import com.hibay.goldking.common.showToast
import com.hibay.goldking.ui.fragment.HomeFragment
import com.hibay.goldking.ui.fragment.PersonalFragment
import kotlinx.android.synthetic.main.activity_main.*


/**
 *
 *
 * @author chenchao
 * @date 3/1/21 11:23
 */
class MainActivity : BaseActivity(R.layout.activity_main) {
    private lateinit var fragments: Map<Int, Fragment>
    private var previousTimeMillis = 0L


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        BarUtils.transparentStatusBar(this)
        fragments = mapOf(
            R.id.loan to createFragment(HomeFragment::class.java),
            R.id.proile to createFragment(PersonalFragment::class.java),
        )

        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            showFragment(menuItem.itemId)
            true
        }

        if (savedInstanceState == null) {
            bottomNavigationView.selectedItemId = R.id.loan
        }
    }

    private fun createFragment(clazz: Class<out Fragment>): Fragment {
        var fragment = supportFragmentManager.fragments.find { it.javaClass == clazz }
        if (fragment == null) {
            fragment = when (clazz) {
                HomeFragment::class.java -> HomeFragment.newInstance()
                PersonalFragment::class.java -> PersonalFragment.newInstance()
                else -> throw IllegalArgumentException("argument ${clazz.simpleName} is illegal")
            }
        }
        return fragment
    }

    private fun showFragment(menuItemId: Int) {
        val currentFragment = supportFragmentManager.fragments.find {
            it.isVisible && it in fragments.values
        }
        val targetFragment = fragments[menuItemId]
        supportFragmentManager.beginTransaction().apply {
            currentFragment?.let { if (it.isVisible) hide(it) }
            targetFragment?.let {
                if (it.isAdded) show(it) else add(R.id.fl, it)
            }
        }.commit()
    }

    override fun onBackPressed() {
        val currentTimMillis = System.currentTimeMillis()
        if (currentTimMillis - previousTimeMillis < 2000) {
            super.onBackPressed()
        } else {
            showToast(R.string.press_again_to_exit)
            previousTimeMillis = currentTimMillis
        }
    }
}