package com.vikram.doordashlite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import com.vikram.doordashlite.ui.main.MainFragment
import com.vikram.doordashlite.ui.main.StoreDetailsFragment

class MainActivity : AppCompatActivity() {

    companion object {
        private const val MAIN_FRAGMENT = "MAIN_FRAGMENT"
        private const val DETAIL_FRAGMENT = "STORE_DETAIL_FRAGMENT"
    }

    private val mainFragment = MainFragment.newInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, mainFragment, MAIN_FRAGMENT)
                    .commitNow()
        }
    }

    fun showDetailView() {
        supportFragmentManager.beginTransaction()
                .setCustomAnimations(
                    R.anim.slide_in,
                    R.anim.fade_out,
                    R.anim.fade_in,
                    R.anim.slide_out
                )
                .replace(R.id.container, StoreDetailsFragment.newInstance())
                .addToBackStack(DETAIL_FRAGMENT)
                .commit()
    }

    fun setToolbar(toolbar: Toolbar) {
        setSupportActionBar(toolbar)
    }
}