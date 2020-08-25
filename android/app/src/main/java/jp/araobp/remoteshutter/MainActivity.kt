package jp.araobp.remoteshutter

import android.bluetooth.BluetoothAdapter
import android.os.Bundle
import android.os.Handler
import android.view.KeyEvent
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val mHandler = Handler()
    var mSno = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val pairedDevices = BluetoothAdapter.getDefaultAdapter()?.bondedDevices

        pairedDevices?.forEach { device ->
            val deviceName = device.name
            val deviceHardwareAddress = device.address
            textView.append("pairedDevices: $deviceName $deviceHardwareAddress\n")
        }
    }

    fun shutter() {
        if (!switch1.isChecked) switch1.isChecked = true
        mHandler.postDelayed(Runnable {
            switch1.isChecked = false
        }, 3000)
    }

    override fun dispatchKeyEvent(event: KeyEvent): Boolean {
        val action: Int = event.action
        return when (event.keyCode) {
            KeyEvent.KEYCODE_VOLUME_UP -> {
                if (action == KeyEvent.ACTION_DOWN) {
                    textView.append("${mSno++}: Keycode volume up\n")
                }
                shutter()
                true
            }
            // Note: this case is never called
            KeyEvent.KEYCODE_VOLUME_DOWN -> {
                if (action == KeyEvent.ACTION_DOWN) {
                    textView.append("${mSno++}: Keycode volume down\n")
                }
                true
            }
            else -> super.dispatchKeyEvent(event)
        }
    }
}