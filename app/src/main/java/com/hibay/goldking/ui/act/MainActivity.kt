package com.hibay.goldking.ui.act

import androidx.fragment.app.Fragment
import com.blankj.utilcode.util.BarUtils
import com.hibay.goldking.R
import com.hibay.goldking.base.BaseActivity
import com.hibay.goldking.common.showToast
import com.hibay.goldking.ui.fragment.*
import kotlinx.android.synthetic.main.activity_main.*

/**
 *
 *
 * @author chenchao
 * @date 3/1/21 11:23
 */
class MainActivity : BaseActivity(R.layout.activity_main) {
    private var previousTimeMillis = 0L
    private lateinit var fragments: Map<Int, Fragment>
    override fun initView() {
        super.initView()
        BarUtils.transparentStatusBar(this)
        fragments = mapOf(
            R.id.home to HomeFragment(),
            R.id.orders to OrdersFragment(),
            R.id.devices to DevicesFragment(),
            R.id.inspection to InspectionFragment(),
            R.id.mine to MineFragment(),
        )
        bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.orders, R.id.devices -> {
                    bottomNavigationView.selectedItemId = R.id.home
                }
                else -> {
                    showFragment(it.itemId)
                }
            }
            true
        }
        bottomNavigationView.selectedItemId = R.id.home
    }

    private fun showFragment(itemId: Int) {
        supportFragmentManager.beginTransaction().apply {
            supportFragmentManager.fragments.forEach {
                if (it.isVisible) hide(it)
            }
            fragments[itemId]?.let {
                if (it.isAdded) show(it) else add(R.id.framelayout, it)
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