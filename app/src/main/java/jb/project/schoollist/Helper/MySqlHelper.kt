package jb.project.schoollist.Helper

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.db.*

/**
 * Created by joao.neto on 13/09/2017.
 */
class MySqlHelper(ctx: Context) : ManagedSQLiteOpenHelper(ctx, "mydb") {

    companion object {
        private var instance: MySqlHelper? = null

        @Synchronized
        fun getInstance(ctx: Context): MySqlHelper {
            if (instance == null) {
                instance = MySqlHelper(ctx.applicationContext)
            }
            return instance!!
        }
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.createTable("Person", true,
                "person_id" to INTEGER + PRIMARY_KEY,
                "email" to TEXT,
                "password" to TEXT)

        db.createTable("notes", true,
                "note_id" to INTEGER + PRIMARY_KEY,
                FOREIGN_KEY("person_id", "Person", "person_id"),
                "title" to TEXT,
                "body" to TEXT)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
    }

}

// Access property for Context
val Context.database: MySqlHelper
    get() = MySqlHelper.getInstance(applicationContext)
