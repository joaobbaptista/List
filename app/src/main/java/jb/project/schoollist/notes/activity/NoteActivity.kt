package jb.project.schoollist.notes.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import jb.project.schoollist.R
import jb.project.schoollist.notes.model.MessageModule
import kotlinx.android.synthetic.main.activity_note.*

class NoteActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note)

        val msg1 = MessageModule(msgTitle = "title 1", msgContent = "text 1111")
        val msg2 = MessageModule(msgTitle = "title 2", msgContent = "text 2222")
        val msg3 = MessageModule(msgTitle = "title 3", msgContent = "text 3333")
        val msg4 = MessageModule(msgTitle = "title 4", msgContent = "text 4444")

        val listmsg = mutableListOf<MessageModule>()

        listmsg.add(msg1)
        listmsg.add(msg2)
        listmsg.add(msg3)
        listmsg.add(msg4)

        list_notes_spinner.adapter = ArrayAdapter<MessageModule>(this, android.R.layout.simple_list_item_1, listmsg)

        list_notes_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                tv_title.setText(listmsg.get(p2).msgTitle)
                tv_body.setText(listmsg.get(p2).msgContent)
            }
        }
    }
}
