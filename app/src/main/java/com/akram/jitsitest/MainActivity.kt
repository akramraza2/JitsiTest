package com.akram.jitsitest

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.jitsi.meet.sdk.JitsiMeet
import org.jitsi.meet.sdk.JitsiMeetActivity
import org.jitsi.meet.sdk.JitsiMeetConferenceOptions
import java.net.MalformedURLException
import java.net.URI
import java.net.URL
import java.util.Date


class MainActivity : AppCompatActivity() {
    private val jitsiUrl = "https://turn.alamaanois.com"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        /*        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
                StrictMode.setThreadPolicy(policy)

        //        lifecycleScope.launch(Dispatchers.IO) {
                val accessToken = AccessToken.getAccessToken()
                Log.d("Access Token: ", " $accessToken")
                val textView = findViewById<TextView>(R.id.textView)
                textView.text = accessToken*/
//        }

        showDialog()


    }

    private fun showDialog() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder
            .setMessage("Choose a user!")
            .setTitle("User")
            .setPositiveButton("Moderator") { dialog, _ ->
                dialog.dismiss()
                startMeeting(true, "Moderator")
            }
            .setNegativeButton("Participant") { dialog, _ ->
                dialog.dismiss()
                startMeeting(false, "Participant")
            }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun startMeeting(isModerator: Boolean, name: String) {
        val jwtToken = generateJwtToken(isModerator, name)
        Log.d("JWT Token: ", " $jwtToken")
        // Somewhere early in your app.
        val serverURL: URL = try {
            // When using JaaS, replace "https://meet.jit.si" with the proper serverURL
            URL(jitsiUrl)
        } catch (e: MalformedURLException) {
            e.printStackTrace()
            throw RuntimeException("Invalid server URL!")
        }
        val defaultOptions = JitsiMeetConferenceOptions.Builder()
            .setServerURL(serverURL) // When using JaaS, set the obtained JWT here
            .setToken(jwtToken)
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
            .setRoom("2025-morning-1-CLASS-1-MORNING-193289") // Settings for audio and video
            .setAudioMuted(true)
            .setVideoMuted(true)
//            .setToken(jwtToken)
            .build()


        // Launch the new activity with the given options. The launch() method takes care
        // of creating the required Intent and passing the options.
        JitsiMeetActivity.launch(this, options)
    }

    private fun generateJwtToken(isModerator: Boolean, name: String): String {
        val key = Keys.hmacShaKeyFor("Al@Amaan+Online_IslamicStudiesAndroid".toByteArray())
        val now = Date()
        val expiry = Date(now.time + 5400000)

        return Jwts.builder()
            .header().add("typ", "JWT").and()
            .claims()
            .add("iss", "alamaan.ois")
            .add("aud", "alamaan.ois")
            .add("sub", extractSub(jitsiUrl))
            .add("room", "*")
            .add(
                "context", mapOf(
                    "user" to mapOf(
                        "name" to name,
//                        "affiliation" to "owner",
                        "moderator" to isModerator
                    ),
                    "features" to mapOf(
                        "recording" to true,
                        "screen-sharing" to true,
                        "file-upload" to true
                    )
                )
            )
            .and()
            .issuedAt(now)

            .expiration(expiry)
            .signWith(key, Jwts.SIG.HS256)
            .compact()
    }
    /*
        private fun generateJwtToken(): String {
            val key = Keys.hmacShaKeyFor(
                "Al@Amaan+Online_IslamicStudiesAndroid".toByteArray()
            )

            val now = Date()
            val expiry = Date(now.time + 5400000)

            return Jwts.builder()
                .header().add("typ", "JWT").and()
                .claim("iss", "alamaan.ois")
                .claim("aud", "alamaan.ois")
                .claim("sub", "meet.alamaanois.com")
                .claim("room", "*")

                .claim("moderator", true)
                .claim("affiliation", "owner")

                // Optional UI context
                .claim(
                    "context", mapOf(
                        "user" to mapOf(
                            "name" to "OJgiodsng NKNGiode"
                        ),
                        "features" to mapOf(
                            "recording" to true,
                            "screen-sharing" to false
                        )
                    )
                )
                .issuedAt(now)
                .expiration(expiry)
                .signWith(key, Jwts.SIG.HS256)
                .compact()
        }*/

    private fun extractSub(myUrl: String): String {
        return URI(myUrl).host
    }
}