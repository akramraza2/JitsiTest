package com.akram.jitsitest

import android.os.Bundle
import android.os.StrictMode
import android.util.Log
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.akram.firebase_token_module.AccessToken


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

//        lifecycleScope.launch(Dispatchers.IO) {
        val accessToken = AccessToken.getAccessToken()
        Log.d("Access Token: ", " $accessToken")
        val textView = findViewById<TextView>(R.id.textView)
        textView.text = accessToken
//        }

        /*
                // Somewhere early in your app.
                val serverURL: URL
                serverURL = try {
                    // When using JaaS, replace "https://meet.jit.si" with the proper serverURL
                    URL("https://meet.jit.si")
                } catch (e: MalformedURLException) {
                    e.printStackTrace()
                    throw RuntimeException("Invalid server URL!")
                }
                val defaultOptions = JitsiMeetConferenceOptions.Builder()
                    .setServerURL(serverURL) // When using JaaS, set the obtained JWT here
                    //.setToken("MyJWT")
                    // Different features flags can be set
                    // .setFeatureFlag("toolbox.enabled", false)
                    // .setFeatureFlag("filmstrip.enabled", false)
                    .setFeatureFlag("welcomepage.enabled", false)
                    .build()
                JitsiMeet.setDefaultConferenceOptions(defaultOptions)


        // ...
        // Build options object for joining the conference. The SDK will merge the default
        // one we set earlier and this one when joining.
                val options = JitsiMeetConferenceOptions.Builder()
                    .setRoom("2025-morning-1-CLASS-1-MORNING-6193244") // Settings for audio and video
                    .setAudioMuted(true)
                    .setVideoMuted(true)
                    .build()


        // Launch the new activity with the given options. The launch() method takes care
        // of creating the required Intent and passing the options.
                JitsiMeetActivity.launch(this, options)
*/
    }
}