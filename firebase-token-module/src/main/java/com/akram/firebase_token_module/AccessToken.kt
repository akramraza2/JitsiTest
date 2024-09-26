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
                    "  \"project_id\": \"alamaan-studies\",\n" +
                    "  \"private_key_id\": \"a293d4c3625190bf7e594702ee2918832ad7ccf2\",\n" +
                    "  \"private_key\": \"-----BEGIN PRIVATE KEY-----\\nMIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCSHIlNGlXRz0eK\\n25IVM4mUfLC2bxSjqBzi5Asn+WDima8JoMAkusTioTQIYcDbc2c/iWZitFVPAEZc\\nvHQ5i0K045u3uqlDjHZV+nYFqmtweExMGahSiUfd9+cMEOt89480McUfaUNQZtx7\\nkPAoSQz8WlJNjfuWnuShglpQpjqPF4TF5sVjoimt/P4L3AcVUx3BeUZ8snadtrHr\\n5w1vBz1AATQ8xl+OaS9tS+gkGv8hCxBiMubphpCFs3lIJfOByk7JGp7BbBlGpokK\\nhSh5jLxf/8wQgDsuNm/9DeDkaZH+hJiajZZbMI84BbqxcSXhUw95hcmyJZfReYv6\\nbB7TRW11AgMBAAECggEAFzFB5xP60tyy17r+L3DYkhIPz8mGjmrBOQ+nKK94PkfU\\nRbQ3Q61E0s9xTbJMlVj2Ztq1vo2u6+UYhiBg14CANGvbnBicRQ1e/EtPIBuaxcXG\\noJ7C/eTuHDD9QwhNhO7b05Zh2phQF8tYDKInSNG9prHklgxnRtOOaYeiM+KK8Yr1\\n/+PXyxKY7dyUmmJSd2tZLYyZ8vJ22EzNIP+BntWlcqbWFui5OzTi784m0HfoTGqi\\ndaj8GAsVIRYHFZualC3XQ5IIemDCDWGtozpgBJPLjD9QyKW3PoW7Wnsqojv5E2xu\\nEiUtIIX0vBi5jG9FdP73hrWf98GLIWhN4Pm3vElxmQKBgQDEMxf5RI0gQZE/QtSQ\\n1NqfhViOK7X9WTgQBuTzQIAkwZTGig+sBFfxuIn5BB1GMn6AAJaqzAHGOHjzIj6X\\nlrHTwegoSoBFXBBIq7hfMctXzSHzkFTroyvGewrYJDm3JkoQOEKpP6SSTyLZtti8\\nhRvZqPLEEXOlFtpN8xhCQdQgeQKBgQC+pTTxvPgiNjnKRCSe96npV2TLWaHZuMF1\\npYa6mT/2gf9FF/P66baHeP4jAoQ6WrAzYIj3SixfWOuMyHDU+xyg6mjj5wPV4bKL\\nhzOFswe3PwV3GYHR2oRAYsOlIVo62K8LKn37oMogCZ+KCbc+NUe3UIr7mj9PfLnm\\nIyVCbilN3QKBgH3H/8OXVh5qypJS5vGe9DW2iCpFDZxJOW7U7KyiGkT2McoCsr9l\\n3XejBhk1sQrSzlSDjBQdS7nndGry9ku6mXCXbJ1//bH5rprYNBAbXTCB2xVJ35dd\\neW/6a95bgwDOtEanutzEZ15BC6el2FcrwsoMzO4z/f7Foti8Uz4O886JAoGBAJ6L\\nebpH8vtQqULC5MmnIZDCczqBsg4Eji6ul5r686ync4kcYHSchIWpY3/uCdUVsk8n\\nywAMmVY59ro/v0YAB1DzYIOXjSMD7Z4HtBtORe5o5LylY8cHuNBq0lWzpPwnU3sv\\nvVHT6AQ4vIrphZCgcqHKw6p2HoyWJ2tycvwJ7SOpAoGBAJj4IAeh/XChyeZVU9iN\\nVs1IBGtoaj84UPO3WtVxiROb3qt9CQ1V6xBWNwxUJ7+tscn41Vv5Mwv1YbKGE9bR\\n70Bt99PyAnihLLVxF8v6ane+aaCYgtNkWlnJVxkIHNg+bn3SVfcMp5z95zCaaeer\\nfH3ZnlfK/+yF9WOCyi1FC/b+\\n-----END PRIVATE KEY-----\\n\",\n" +
                    "  \"client_email\": \"firebase-adminsdk-mqv7o@alamaan-studies.iam.gserviceaccount.com\",\n" +
                    "  \"client_id\": \"103330818964985861590\",\n" +
                    "  \"auth_uri\": \"https://accounts.google.com/o/oauth2/auth\",\n" +
                    "  \"token_uri\": \"https://oauth2.googleapis.com/token\",\n" +
                    "  \"auth_provider_x509_cert_url\": \"https://www.googleapis.com/oauth2/v1/certs\",\n" +
                    "  \"client_x509_cert_url\": \"https://www.googleapis.com/robot/v1/metadata/x509/firebase-adminsdk-mqv7o%40alamaan-studies.iam.gserviceaccount.com\",\n" +
                    "  \"universe_domain\": \"googleapis.com\"\n" +
                    "}"

            //al amaan
            /*  val jsonString = "{\n" +
                      "  \"type\": \"service_account\",\n" +
                      "  \"project_id\": \"alamaan-ois\",\n" +
                      "  \"private_key_id\": \"a5a3f80b57cd4a52f9e713b03b6ee1f06e60b186\",\n" +
                      "  \"private_key\": \"-----BEGIN PRIVATE KEY-----\\nMIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQDJzXL0o6rlcUxW\\n5VgYBOy5lXIh3/FaAdq09wwOJtlF0ZAmgar1XBuWsFjPO/+/KI7oVcQLve4lnnlA\\nyt+DBBVRiUN9pCPLhNwF53Ymxb6+a8NzfJOOrFFvCKip141/ubShVWxl7m+04+9P\\nBi7A4xY2f/EFF8wwH/70RGHx+Fqu2kDOg2VU7XuwHlAk5fNAB1scp+dCYq46vYpx\\nmpTrz0Pj/zMOHLYFylsoAUZJyBZIlB8sC75ICy1mvh20swL9UfqWu2HMmwH4aERa\\nvVhgwaOIkqiFmLmCsLQtJwgOWLoOCid3Mrdud/xu7sRnmKnzSwZvnRuVYyLOW6No\\ndNrv4lShAgMBAAECggEAAnSjL3yiv49syRiZ7J0Npp0CuQKv7xCz+wHyWkfPKZta\\nT8t9Bo4d7tN3twjGsvknvMs3JkmBW018ioFk3HUtoLGe2LhfY9Opar6+nxDkSaHx\\nQpeOin4LCWe3eO11azyjoYiQhDWN9jys0PG41brsc1SrzeTtLnCone33yRHusGfe\\nEjHoz0TtzysAWZHD8tlk9b/rc1zlDKJ3g/0UIcpOqT3i840JGzv+XVSstYJrjpH4\\nnYxG0SBEaZ/vuRUj9X3CsUQfK2g0PtdUie/Rlth/CaCXFdXpWHRRRBK5/SUcbAoD\\nSaDdt/rvRjRA0x55/p/pCvSIuulefvkpVleoOmdjjQKBgQDliEN8OsA047dq/vGF\\nLJ77lCrJVpOoRfBphGlW1Tprrk63MAqvHs+Ns1AV0ByhTBHf4Id9M63hidK6rhBJ\\nJWj0i6kVW8hMyMXxaAHH6g0o/nrEVVYO67ACYpJVa96jiIj9Y2sIJbZHK12DBsvh\\nai0gmzZROKcO3wXYhsyi1dE4HQKBgQDhEpw2SPlaQA649jw+hF4U815Q1xwjI+qx\\nkyMXeSH7Q7GR8ddqqyQLnyOw6C3zh4v/PB446aSamdI0mlabvLoKnxeqUiuJHOFJ\\nb5PbBXDjRdTRefbcrG71fFSpMZp1K+hpoqLWCKwFFuBYLhalsrlxEwRljry/4wZB\\nRYFwy9UPVQKBgCj6LrIa+v12OeSMJyoTUT6wsQLIxgugkM8FqJir8IYQ1FS36vBg\\nm8U8YevC/5l8LyS8vPOJKl29cTWKpbWpTBd8Z288t4GdxljWrJq4GilbZSdv5LE9\\nOmrylhVR8KlZUruJ6C3ILcZ+427k47kWb4AGtafCaXCqOCzzw8y+R1+xAoGAVqdj\\nnULbPfQLtwqWhztPPaG1XkpzetQJN/T+PbtVaffWFeqWlWrupcrPYv0BNBGrxMjQ\\nmfjadutiEv6Y+bzfo9c8tUaNnGySEEP1GQ4vOFFabowWjz+UOM91iO6gGRyejo0f\\nRGhyUkNMfKbB8WmuG+rGdpNN+FLfxaGNBHB1x0ECgYApoeS0sYMKZ99WZTyuR7sl\\n1qcG+yfxdIN4gvypA9abD31zlKtOhNSOPHRZ5k18oguGq0HKDHBywgd2jZDWTMZk\\nuK7JEvMtxyWgYAnxfhL06Br/ep/+JhsrbTfmeHorUMqXYfACWmFhuu/WhA3chrk9\\n+rY0T4UZPfPTFe4C+Brx3Q==\\n-----END PRIVATE KEY-----\\n\",\n" +
                      "  \"client_email\": \"firebase-adminsdk-r1rvt@alamaan-ois.iam.gserviceaccount.com\",\n" +
                      "  \"client_id\": \"118197112783443723967\",\n" +
                      "  \"auth_uri\": \"https://accounts.google.com/o/oauth2/auth\",\n" +
                      "  \"token_uri\": \"https://oauth2.googleapis.com/token\",\n" +
                      "  \"auth_provider_x509_cert_url\": \"https://www.googleapis.com/oauth2/v1/certs\",\n" +
                      "  \"client_x509_cert_url\": \"https://www.googleapis.com/robot/v1/metadata/x509/firebase-adminsdk-r1rvt%40alamaan-ois.iam.gserviceaccount.com\",\n" +
                      "  \"universe_domain\": \"googleapis.com\"\n" +
                      "}"*/

            var stream = ByteArrayInputStream(jsonString.toByteArray(StandardCharsets.UTF_8))

            val testCredential = GoogleCredentials.fromStream(stream) as ServiceAccountCredentials

// Access client email and project ID
            val clientEmail = testCredential.clientEmail
            val clientId = testCredential.clientId
            val projectId = testCredential.projectId
            val privateKeyId = testCredential.privateKeyId
            val privateKey = testCredential.privateKey
            Log.d("ACCESS TOKEN ", "Client Email: $clientEmail")
            Log.d("ACCESS TOKEN ", "Client Id: $clientId")
            Log.d("ACCESS TOKEN ", "Project Id: $projectId")
            Log.d("ACCESS TOKEN ", "Private key Id: $privateKeyId")
            Log.d("ACCESS TOKEN ", "Private key: $privateKey")

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