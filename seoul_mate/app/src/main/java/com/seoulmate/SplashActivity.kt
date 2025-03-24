package com.seoulmate

import android.os.Build
import android.os.Bundle
import android.os.StrictMode
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_splash)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Safer intents on Android 15
        // Android 15 introduces new security measures to make intents safer and more robust.
        // These changes are aimed at preventing potential vulnerabilities and misuse of intents that can be exploited by malicious apps.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            StrictMode.setVmPolicy(
                StrictMode.VmPolicy.Builder()
                    .detectUnsafeIntentLaunch()
                    .build()
            )
        }
    }
}