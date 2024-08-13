package com.masreferenceapp.storage

import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import com.masreferenceapp.R
import com.masreferenceapp.Status
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

class StorageHardcodedSecret(var context: ReactApplicationContext) : ReactContextBaseJavaModule(
    context
) {
    private fun readRawTextFile(resId: Int): String {
        return context.resources.openRawResource(resId).use { inputStream ->
            inputStream.bufferedReader().use { reader ->
                reader.readText()
            }
        }
    }

    override fun getName(): String {
        return "StorageHardcodedSecret"
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    fun privateLocalKeys(): String {
        val dsaPrivateKey = readRawTextFile(R.raw.dsa_2048)
        val ecPrivateKey = readRawTextFile(R.raw.ec_secp256k1)
        val rsaPrivateKey = readRawTextFile(R.raw.rsa_2048)
        val dsaPrivateKeyPassword = readRawTextFile(R.raw.dsa_2048_with_passphrase)
        val ecPrivateKeyPassword = readRawTextFile(R.raw.ec_secp256k1_with_passphrase)
        val rsaPrivateKeyPassword = readRawTextFile(R.raw.rsa_2048_with_passphrase)
        return Status.status("OK", "Dummy-Method with Private Keys")
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    fun privateEmbeddedKeys(): String {
        val dsaPrivateKey = """
            -----BEGIN DSA PRIVATE KEY-----
            MIIDVQIBAAKCAQEAzTY2AJP9yddv26GG1fzzR6+Ux+wyPUDG/jtvCyfuwfZkbHMO
            +cC8gQBO+YCk5T9Ysn8g9fiVk+M95ZObmd2j+ol1mYV3gD85UaPOlQ4X9erA/U8r
            lpr5VbDvqC1BtKuJEqGHBIAhaUPT7m+VF0pCWbl8grWNYWzg/3ptjYD0TtmVkn4K
            5gpEu9lWu8QlU2M9NJq5vGWuajQ9KfiL9ZZGtp3LinjnQw2/ugrGUMeZ7ueiPyLR
            tNq4e9biGHmTo9LHkBsvQXFDPL1LPbhVScZV4EGwSP34Bh/YO/xEaXJB9R+2+18h
            dOLZwLrZBu/N4ycaqP1VgmVqEU/FYBZV2wmmUwIhALV2R2/8WsKI/0xdsYuZMhnG
            MrsczK5yznCpGDq6xMA5AoIBABV4jUwPrPbxu121atAm4BgJiLcBey2SUDH9gHJ/
            YQ7ELt2FnQ9R+PjjcrPKfMCo7KrchbTyq4dLHCMCFLuvgHXmqq4lGh5wtaRUG2E/
            fB8Q8s99ARS/zqb3OjY7Nv5OOdH/Soa8+UMpUAYp6VfWbnyY4gS8RMJvqUPOkP8x
            P79/S06aE3pyhhb0EqfDQBNOkfdCiKCuMKuoHosggzBX29o9YjIOrYd3A3GgySgT
            x1M5+UIxdOBj2LsdF7vNbaEBAG2zQqetz/ZSR7p3H5R3ufbw32pQPAlBlLpDkNa5
            efZuEmqWvmayz3mWekzzsb7lrVg/IdtEzwjJ9X4e39rjFhwCggEBALVbZBEtH8zu
            nWUa1hbLAzUc/2dNHUwP05qwGEo9NpRRRQ1veXURET1mFII+7cAhNJisSztCygab
            1a8Vc07tf5u/lKKChiO6V9wOYNzo3i4PsVULm15VtByqn4vEB1JZE7PiwDp5bmPj
            1e5vuZ/ZUUfWHecT7LbSZghuZplrL50vG7jNnOVDDQ4xG7neQf3SJwxKg9Emcrf7
            rnAX7aHHvcWFLrr4rlnnuZKqyns9gVPQY7XhJgXH7QVuiNwrnovXfLGfHahvBwEq
            VxPe3IEc6oFUOEoTkE98GgZC59Hqx98jC6iY7An63vLXHIF/HRYfwKf00A1l+qTG
            6+ngLjzV9d8CH1ZtAHsoySQHj+QLIlIOBVXiO16GSoQW1ZNaz3M61AI=
            -----END DSA PRIVATE KEY-----
            
            """.trimIndent()
        val ecPrivateKey = """
            -----BEGIN EC PRIVATE KEY-----
            MHQCAQEEILm3BjMKVFegtAqLFrXh76HH6Y3Ehyhndao7Tgp2Zlm5oAcGBSuBBAAK
            oUQDQgAE1h1zcNnztBboYOOYtIu8i/amMR66/huAlYB76P5T4ZVnRL8kn45zgahs
            Ml6BDsXBCUtTdPfFPGQL6TJElpXGoQ==
            -----END EC PRIVATE KEY-----
            
            """.trimIndent()
        val rsaPrivateKey = """
            -----BEGIN PRIVATE KEY-----
            MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCkkmK83VERePyt
            IroBamhc3tll2lMRB1jSdiuQlft2rbnNfzlP73gfUiyl23bpOlS0o5RSHo2CstVy
            55ExDnrdurGCmeGuCqZpn4oWC2zc66+YCohtWdAbtFtEGLi70W+Hj+JYeIrvkMvM
            eSqss2G6li2p2Z2UrUqvIi1VSE5eQVKNbfW0Khz3kk4/NGWDiZ73MCTTIsZw6HDM
            GHKwuBOajQ5pM0HVLlZchP1HsjQv4kypPPMG1K4Nlepy1tA3DkNNh+eysL9qT0KQ
            LowWs2dvOYjmYf5VHEzIZOUNxB1nTDIEwWyD/PT/oyRIsjK3WsxV9mwuwprqC1gc
            U8IYNBP7AgMBAAECggEAK+HIqDmPO0x6e6QN2wDHkTPu3gTIL3s5CO1vkl8brTqH
            l6771j+xVRCxTQxm+auPGJehnf/9lcMfvULX6S/GPUdhu8RSc/jtBK8Av/4N6h6C
            NNViKV5QaoK97zt0Tsh6p7gLD7IcweJnJT+NzH0MaxdOV8LqDNHkXyyKz/m2w9TT
            uouqMCzw8+T/cV4EM1KMHQ+iWCh246waprYFmCCoPI53WqtVFqSs3YeHO1xHNlGc
            NZZ74EiNa96XOixKv5gZvGvcnJ5Tvb7v0Qo5LjqVEKxsxVYGvG+etp2HNQXNNRaY
            8BbHhHz4KHV2yF3gM19nGaN+sVdi4oLYpVdhWbCUgQKBgQDOZC2xnmrPsacPTe3s
            pLJQpEbVQZcs7YeYR1UCWszTlyxuW+KOK+F/QmorbZ+5e8AzDJba+4OMO1qAYDVp
            LUOchM5TeHwbmbERSvtsllBWe3qvqYUhVQY92ZVhelbuyFBQMaeg2FDAHz/9+U5o
            zkyL6lNrvLfz8gMaley4wqhfwQKBgQDMIPBi2eq02PTo208XpsQ2Fa4e8ywg0GBM
            i5wKtbkVt4qVKlrqgmjBNm5ejBmesE/x+2vAuNG+kI7mglNHIiBrYypz8o5KIS4l
            FQ+Ug9zFG6EARHvD7aMhfYXBn4Mfql6TTtHmz+xNpH20paprl9+7iMQPkO+t3suH
            z1PKoh6iuwKBgQCIBY332agbacHoXDvKEmFStHHaanfvrDwJKDnma2FCsgceVIBj
            opfi/ypppL33yI9LFaj2eXyhFxz2LnIE90fwB7bTXDyvFAOWmgxJ7GbWGFsrGlYb
            OaX7bUL+E1rn1CzaLV+EqgvOEsph38TFXfEk5TJFdwLlS0Kwas6wldnSAQKBgGTP
            h6hqEr3Jv/oYBRGbLJ/BSULDuXFjN4vWDvRDFusgv/I2/rt+OBnjtdI/wo9aZ8EL
            +AUvwXYpkklI57PjqLsgOGEW0yrNBCsQIaepD6jQokythaoXfE8X3KzpCCrlStvZ
            O1SQLxWRPPuwLWABm17Uhm9hltz5gO7Ld4hFVHftAoGBALQ9z4rhXZs4+lB8nR+b
            KXx3F7ImIvywCc+wzFuV06POtltHTj7xnq6LlnXlwLFu87vORcL/5VHwsjq58FnT
            dZMPygqLqleLxSP0vI69T1bOvpjMt5yWDepH0RkkYtCX4ro98I5a6JxPT6fIp05x
            ZTPwxXckJbmCs8hOsRYf8pnO
            -----END PRIVATE KEY-----
            
            """.trimIndent()
        val dsaPrivateKeyPassphrase = """
            -----BEGIN DSA PRIVATE KEY-----
            Proc-Type: 4,ENCRYPTED
            DEK-Info: AES-256-CBC,FA8A421E421B5721D4FBBB9143778A1A
            
            sBrmcPL/xEHNoucp9oRBhWb/jnRIVl84KJTzP+Dsc3Q2aulbI+3VUQFtrRtP7DDa
            5abTv9KssGbo6EBz0VPENJvWetkjlY+MCrhLSpRnQqOkEYhdZduLJo3lZ9591qZW
            MlbfSePaJoMC7YRumtGmrxiJVA/queCkUyDIp/Bqf47eASnpT05WOBiLDnZFXXWN
            DYIFjCP0b7/ASzhQuLLelxMjGlT2BRQEOF6+ADnCNI15W5lA+hfdbxX6nELgYze7
            FhDIIvQCm730h0iujmW9WOIev1GiZ/X2dMLHVijHm4huRPRiG5dk0zoYS8JzElw0
            v1nfd71Xo/+7BGvYQJs6yK1u34BHHTOisEMadAg7DmFRiirninF1QfjY4VYBg6Uw
            l3V7Pxpth9BhI2lZ7n9Ip19Shh7KMbIgu9WNKS1N4vYWoy7IwTmOJJvUtSkiQGSF
            rrpTWX5q84Z8k8IN+Ufa7jgPkjgMzEodiN2b5OWw36IY6yX21yvG5pIvPc6nwwJy
            gUpmO+GqgGQyHYCOG9iQq7PUibgqqm94zSm+RYMl9TgzX0E475qSMHID62cJj9Bj
            t/bK6NTaazZ2x3qK2Lyz+pJZYJmZUpYj/Bd9YlSz3ex9shwX9cW5iYipUCnJD7Vm
            Q4+Bsj25xcDAOGnPxCt/oCq0ZRhD2VBFxZnsYKT/0dQ38S2Gnz1QOvJgwTYcj0uo
            ZB1cOdbXyNNI73yQ0LDY8nRfgljHM6vQPZg5iGbsfZ9WuHF6V7TlR3oHPXpOX8Ku
            meRpYiwbA9ZEHUzc5e2is+4u1cTDaoRCHecpjHOhdRT2X53bss5bLPUgcPRTP/VC
            xfjW6epYJQRUNmC4RSvEna5Fj9Y5KSwgd6F/lJmuCGihj/7dKvLwEhoAsuaT223E
            7wZWC2RB8oH6BAQeV1WugGk6XtuQUco5Hn3J23t0R0NqOYpepBwIOruj5aB3V7ih
            jH1AaDhZH7iDE02OM9sooZUwf42JE4PdQRsi6iz1Xbd5QBK/L4Q7IMgJ/ClOmmOS
            QPRuAeP0yYM5kDkGExHGBZaCGw+WyA5Vdju3jtIxYMgiFgrFGhysyNkWySF/lUTD
            zt5fOo69AHd4di1N7I4VgqCGDX+vY58h6lyNlL/hr4keGJ3iPSXzpKE+qxxfRhFm
            -----END DSA PRIVATE KEY-----
            
            """.trimIndent()
        val ecPrivateKeyPassphrase = """
            -----BEGIN EC PRIVATE KEY-----
            Proc-Type: 4,ENCRYPTED
            DEK-Info: AES-256-CBC,3DC703C0C73A55877B9A7129CCF366AF
            
            9ODLFl4Enhp/1H75ezoltc6D30Q90Xq0ATqRsG+x02m0xSn5fi0jMlvfKMV5BekJ
            YWnvGdrN8m3xB3lS5XJmXpAhqTy4g0fXfjyGoklaDcQZZWZplErYN0anI6HUEpCQ
            G7FFIR/A1OlR0+CT/b9OmpBiMi85zbu+Bie+NogAXqk=
            -----END EC PRIVATE KEY-----
            
            """.trimIndent()
        val rsaPrivateKeyPassphrase = """
            -----BEGIN ENCRYPTED PRIVATE KEY-----
            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
            /DwXwOaFOFpjN6wV5O8NU8tPtw==
            -----END ENCRYPTED PRIVATE KEY-----
            
            """.trimIndent()
        return Status.status("OK", "Dummy-Method with Private Keys")
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
        return Status.status("OK", "Dummy-Method with API Keys")
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    fun passwords(): String {

        // Dummy passwords
        val password1 = "dummyPassword123!"
        val password2 = "SuperSecurePassword456$"

        // Dummy Database connection strings
        val dbConnectionString1 =
            "jdbc:mysql://localhost:3306/mydatabase?user=root&password=rootpassword"
        val dbConnectionString2 = "mongodb://myUser:myPassword@localhost:27017/mydatabase"
        return Status.status("OK", "Dummy-Method with Passwords Keys")
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    fun tokens(): String {
        val accessToken =
            "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyLCJyb2xlIjoiT1dBU1AgTUFTIEFkbWluIn0.YODwerxN0UaqulVo32uT_Jt_QABoxvQheK2Dmfq_1Xc"
        val refreshToken = "MIOf-U1zQbyfa3MUfJHhvnUqIut9ClH0xjlDXGJAyqo"
        return Status.status("OK", "Dummy-Method with Token Keys")
    }
}