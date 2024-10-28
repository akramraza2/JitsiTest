package com.akram.firebase_token_module


/**
 * Created by Akram Raza on 21/09/2024.
 * akramraza25524@gmail.com
 */
import android.util.Log
import com.google.auth.oauth2.GoogleCredentials
import com.google.auth.oauth2.ServiceAccountCredentials
import java.io.ByteArrayInputStream
import java.io.IOException
import java.nio.charset.StandardCharsets

object AccessToken {
    private const val FIREBASE_MESSAGING_SCOPE: String =
        "https://www.googleapis.com/auth/firebase.messaging"

    private var accessToken:String?=null
    fun getAccessToken(): String? {
        try {
            //dummy
            val jsonString = "{\n" +
                    "  \"type\": \"service_account\",\n" +
                    "  \"project_id\": \"project_id\",\n" +
                    "  \"private_key_id\": \"private_key_id\",\n" +
                    "  \"private_key\": \"-----BEGIN PRIVATE KEY-----\\private_key\\n-----END PRIVATE KEY-----\\n\",\n" +
                    "  \"client_email\": \"client_email\",\n" +
                    "  \"client_id\": \"client_id\",\n" +
                    "  \"auth_uri\": \"https://accounts.google.com/o/oauth2/auth\",\n" +
                    "  \"token_uri\": \"https://oauth2.googleapis.com/token\",\n" +
                    "  \"auth_provider_x509_cert_url\": \"https://www.googleapis.com/oauth2/v1/certs\",\n" +
                    "  \"client_x509_cert_url\": \"https://www.googleapis.com/robot/v1/metadata/x509/firebase-adminsdk-mqv7o%40alamaan-studies.iam.gserviceaccount.com\",\n" +
                    "  \"universe_domain\": \"googleapis.com\"\n" +
                    "}"

            var stream = ByteArrayInputStream(jsonString.toByteArray(StandardCharsets.UTF_8))

            val testCredential = GoogleCredentials.fromStream(stream) as ServiceAccountCredentials

            val clientEmail = testCredential.clientEmail
            val clientId = testCredential.clientId
            val projectId = testCredential.projectId
            val privateKeyId = testCredential.privateKeyId
            val privateKey = testCredential.privateKey
          

            stream = ByteArrayInputStream(jsonString.toByteArray(StandardCharsets.UTF_8))
            val googleCredential = GoogleCredentials.fromStream(stream)
                .createScoped(arrayListOf(FIREBASE_MESSAGING_SCOPE))
            googleCredential.refreshIfExpired()

            accessToken = googleCredential.accessToken.tokenValue
            Log.d("ACCESS TOKEN ", "Token Value: $accessToken")

            return accessToken
        } catch (e: IOException) {
            e.printStackTrace()
            Log.e("ACCESS TOKEN EXCEPTION", "${e.message}")
            return accessToken
        }
    }

}
