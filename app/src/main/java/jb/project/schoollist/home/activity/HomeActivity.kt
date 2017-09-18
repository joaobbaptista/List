package jb.project.schoollist.home.activity

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import jb.project.schoollist.Helper.PrefHelper
import jb.project.schoollist.Helper.database
import jb.project.schoollist.R
import jb.project.schoollist.notes.model.Notes

import kotlinx.android.synthetic.main.activity_home.*
import org.jetbrains.anko.db.MapRowParser
import org.jetbrains.anko.db.StringParser
import org.jetbrains.anko.db.select

class HomeActivity : AppCompatActivity() {

    var prefs: PrefHelper? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setSupportActionBar(toolbar)

        prefs = PrefHelper(this)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
        loadList()
    }

    private fun loadList() {
        val user_id = prefs!!.userId

        val rowSelected = database.use {
          select("notes","*").whereSimple("user_id = $user_id ")
        }
    }

}
