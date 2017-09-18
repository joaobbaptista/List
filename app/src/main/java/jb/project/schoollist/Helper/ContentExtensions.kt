package jb.project.schoollist.Helper

/**
 * Created by joao.neto on 15/09/2017.
 */
import android.app.Activity
import android.app.Service
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.Intent.EXTRA_SUBJECT
import android.content.Intent.EXTRA_TEXT
import android.net.Uri
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KClass
import kotlin.reflect.KProperty

fun Context.startActivity(f: Intent.() -> Unit): Unit =
        Intent().apply(f).run(this::startActivity)

inline fun <reified T: Activity> Context.start(noinline f: Intent.() -> Unit = {}) =
        startActivity {
            component = componentFor(T::class.java)
            f(this)
        }

fun Context.start(a: String, f: Intent.() -> Unit = {}) =
        startActivity {
            action = a
            f(this)
        }

fun Context.chooseActivity(title: String = "", f: Intent.() -> Unit) =
        startActivity(Intent.createChooser(Intent().apply(f), title))

fun Activity.startForResult(requestCode: Int, f: Intent.() -> Unit): Unit =
        Intent().apply(f).run { startActivityForResult(this, requestCode) }



fun Context.startService(f: Intent.() -> Unit): ComponentName =
        Intent().apply(f).run(this::startService)

inline fun <reified T: Service> Context.launch(noinline f: Intent.() -> Unit = {}) =
        startService(T::class.java, f)

fun <T : Service> Context.startService(type: Class<T>, f: Intent.() -> Unit): ComponentName =
        Intent().apply(f).run(this::startService)



fun Context.send(f: Intent.() -> Unit) =
        Intent().apply(f).run(this::sendBroadcast)

fun Context.send(action: String, f: Intent.() -> Unit = {}) =
        Intent(action).apply(f).run(this::sendBroadcast)

fun Context.send(action: String, permission: String, f: Intent.() -> Unit = {}) =
        Intent(action).apply(f).run { sendBroadcast(this, permission) }


fun Context.componentFor(targetType: KClass<*>): ComponentName =
        componentFor(targetType.java)

fun Context.componentFor(targetType: Class<*>): ComponentName =
        ComponentName(this, targetType)

inline var Intent.url : String
    get() = dataString
    set(value) { data = Uri.parse(value) }

inline var Intent.subject
    get() = getStringExtra(EXTRA_SUBJECT)
    set(value) { putExtra(EXTRA_SUBJECT, value) }

var Intent.text by ExtraProperty.string(EXTRA_TEXT)

sealed class ExtraProperty<T> : ReadWriteProperty<Intent, T> {

    companion object {
        fun string(name: String) = StringProperty(name)
        fun int(name: String) = IntProperty(name)
    }

    class StringProperty internal constructor(private val name: String) : ExtraProperty<String>() {
        override fun getValue(thisRef: Intent, property: KProperty<*>): String =
                thisRef.getStringExtra(name)
        override fun setValue(thisRef: Intent, property: KProperty<*>, value: String) {
            thisRef.putExtra(name, value)
        }
    }

    class IntProperty internal constructor(private val name: String) : ExtraProperty<Int>() {
        override fun getValue(thisRef: Intent, property: KProperty<*>): Int =
                thisRef.getIntExtra(name, -1)
        override fun setValue(thisRef: Intent, property: KProperty<*>, value: Int) {
            thisRef.putExtra(name, value)
        }
    }

}