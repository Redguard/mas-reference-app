package org.masreferenceapp.storage

import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import org.masreferenceapp.ReturnStatus

class StorageHardcodedApiKey(var context: ReactApplicationContext) : ReactContextBaseJavaModule(
    context
) {


    override fun getName(): String {
        return "StorageHardcodedApiKey"
    }


    @ReactMethod(isBlockingSynchronousMethod = true)
    fun apiKeys(): String {

        // Amazon Web Services (AWS)
        val awsAccessKeyId = "AKIAIOSFODNN7EXAMPLE" // AWS Access Key ID pattern
        val awsSecretAccessKey =
            "wJalrXUtnFEMI/K7MDENG/bPxRfiCYEXAMPLEKEY" // AWS Secret Access Key pattern

        // Google Cloud Platform (GCP)
        val gcpApiKey = "AIzaSyD-9C5D5yX89GEXAMPLEK9rY1-2LQRzXY" // Google API Key pattern
        val gcpOAuthClientId =
            "123456789012-abcde1f2g3h4ijklmnop5qrstuvwxyz.apps.googleusercontent.com" // Google OAuth Client ID pattern
        val gcpOAuthClientSecret =
            "GOCSPX-3u8kxv9YZ4Q7EXAMPLEeUt-2fH2" // Google OAuth Client Secret pattern

        // Microsoft Azure
        val azureSubscriptionKey =
            "0123456789abcdef0123456789ABCDEF" // Azure Subscription Key pattern
        val azureClientId = "4a7f2e56-7b8e-4a90-bc5c-1234567890ab" // Azure Client ID pattern
        val azureClientSecret =
            "7e5b4e2a-8d9e-4a90-c1e2-3456789abcde" // Azure Client Secret pattern

        // IBM Cloud
        val ibmApiKey = "D4C0nZK3v7wY5U3p1K9X4Z0aEXAMPLEbN4L0aQ" // IBM Cloud API Key pattern

        // Dropbox
        val dropboxAccessToken =
            "sl.BG0EZ37EXAMPL9mXZG67FZX82L4G5F4w" // Dropbox Access Token pattern
        val dropboxAppKey = "q7yw3EXAMPL4pzoz5" // Dropbox App Key pattern
        val dropboxAppSecret = "5lw7dqx1x87yz9EXAMPL3rtk7" // Dropbox App Secret pattern

        // Twitter
        val twitterApiKey = "xvz1evFS4wEEPTGEFPHBogEXAMPLE" // Twitter API Key pattern
        val twitterApiSecretKey =
            "L8qq9PZyRg6ieKGEKhZolgcEXAMPLPR5FUa" // Twitter API Secret Key pattern
        val twitterAccessToken =
            "370773112-NY5IPQG6EXAMPLpRPAdUEB1H6a69z" // Twitter Access Token pattern
        val twitterAccessTokenSecret =
            "km5gQPc8uGOM8eUOEXAMPLEJFTBa1T1qDs" // Twitter Access Token Secret pattern

        // Facebook
        val facebookAppId = "123456789012345" // Facebook App ID pattern
        val facebookAppSecret = "1a2b3c4d5e6f7g8h9i0jEXAMPLE" // Facebook App Secret pattern

        // GitHub
        val githubPersonalAccessToken =
            "ghp_1234567890abcdefghijklmnOPQRSTUV" // GitHub Personal Access Token pattern

        // Stripe
        val stripePublishableKey =
            "pk_test_51H8fQEXAMPLbG6pHQlnY5Kw9Qa60v1WuD1OqQ" // Stripe Publishable Key pattern
        val stripeSecretKey = "sk_test_4eC39HqLyjWDarjtT1zdp7dcEXAMPL" // Stripe Secret Key pattern

        // PayPal
        val paypalClientId = "AbcDEFGHIjklmnopQRstuvwxyz1234567890" // PayPal Client ID pattern
        val paypalSecret = "EFGHijklmNOPQRSTuvwxYZ1234567890abcDEFg" // PayPal Secret pattern

        // Spotify
        val spotifyClientId = "1234567890abcdef1234567890abcdef" // Spotify Client ID pattern
        val spotifyClientSecret =
            "abcdef1234567890abcdef1234567890" // Spotify Client Secret pattern

        // Slack
        val slackBotToken = "xoxb-123456789012-abcdefghijklmnopqrstuvwx" // Slack Bot Token pattern
        val slackSigningSecret =
            "abcd1234efgh5678ijkl9012mnop3456qrst7890" // Slack Signing Secret pattern

        // GitLab
        val gitlabPersonalAccessToken =
            "glpat-abcdefgh1234567890ijklmnopqrstuvwx" // GitLab Personal Access Token pattern

        // Twilio
        val twilioAccountSid = "ACXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX" // Twilio Account SID pattern
        val twilioAuthToken = "your_auth_token" // Twilio Auth Token pattern

        // SendGrid
        val sendgridApiKey =
            "SG.xxxxxxxx.yyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyy" // SendGrid API Key pattern
        return ReturnStatus("OK", "Dummy-Method with API Keys").toJsonString()
    }


    @ReactMethod(isBlockingSynchronousMethod = true)
    fun tokens(): String {
        val accessToken =
            "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyLCJyb2xlIjoiT1dBU1AgTUFTIEFkbWluIn0.YODwerxN0UaqulVo32uT_Jt_QABoxvQheK2Dmfq_1Xc"
        val refreshToken = "MIOf-U1zQbyfa3MUfJHhvnUqIut9ClH0xjlDXGJAyqo"
        return ReturnStatus("OK", "Dummy-Method with Token Keys").toJsonString()
    }
}