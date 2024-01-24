package com.zebra.nilac.mestest

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.zebra.nilac.emdkloader.EMDKLoader
import com.zebra.nilac.emdkloader.ProfileLoader
import com.zebra.nilac.emdkloader.interfaces.EMDKManagerInitCallBack
import com.zebra.nilac.emdkloader.interfaces.ProfileLoaderResultCallback

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initEMDKManager()
    }

    private fun initEMDKManager() {
        //Initialising EMDK First...
        Log.i(TAG, "Initialising EMDK Manager")

        EMDKLoader.getInstance().initEMDKManager(this, object : EMDKManagerInitCallBack {
            override fun onFailed(message: String) {
                Log.e(TAG, "Failed to initialise EMDK Manager")
            }

            override fun onSuccess() {
                Log.i(TAG, "EMDK Manager was successfully initialised")

                grantManageExternalStoragePermission()
            }
        })
    }

    private fun grantManageExternalStoragePermission() {
        Log.i(TAG, "Granting External Storage Permission through MX")
        ProfileLoader().processProfile(
            "MES-Config",
            null,
            object : ProfileLoaderResultCallback {
                override fun onProfileLoadFailed(message: String) {
                    runOnUiThread {
                        Toast.makeText(
                            this@MainActivity,
                            "Failed to process profile! Check logs...",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }

                override fun onProfileLoaded() {
                    Log.i(TAG, "Successfully granted permission to the app")
                    finish()
                }
            })
    }

    companion object {
        const val TAG = "MainActivity"
    }
}
