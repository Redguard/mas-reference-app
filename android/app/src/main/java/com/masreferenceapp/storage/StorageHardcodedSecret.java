package com.masreferenceapp.storage;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.masreferenceapp.R;
import com.masreferenceapp.Status;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class StorageHardcodedSecret extends ReactContextBaseJavaModule {
    ReactApplicationContext context;

    public StorageHardcodedSecret(ReactApplicationContext context) {
        super(context);
        this.context = context;
    }

    private String readRawTextFile(int resId) {
        InputStream inputStream = this.context.getResources().openRawResource(resId);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line).append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return stringBuilder.toString();
    }


    @NonNull
    @Override
    public String getName() {
        return "StorageHardcodedSecret";
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public String privateLocalKeys(){


        String dsaPrivateKey  = this.readRawTextFile(R.raw.dsa_2048);
        String ecPrivateKey  = this.readRawTextFile(R.raw.ec_secp256k1);
        String rsaPrivateKey  = this.readRawTextFile(R.raw.rsa_2048);

        String dsaPrivateKeyPassword = this.readRawTextFile(R.raw.dsa_2048_with_passphrase);
        String ecPrivateKeyPassword = this.readRawTextFile(R.raw.ec_secp256k1_with_passphrase);
        String rsaPrivateKeyPassword = this.readRawTextFile(R.raw.rsa_2048_with_passphrase);

        return Status.status("OK", "Dummy-Method with Private Keys");
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public String privateEmbeddedKeys(){

        String dsaPrivateKey = "-----BEGIN DSA PRIVATE KEY-----\n" +
                "MIIDVQIBAAKCAQEAzTY2AJP9yddv26GG1fzzR6+Ux+wyPUDG/jtvCyfuwfZkbHMO\n" +
                "+cC8gQBO+YCk5T9Ysn8g9fiVk+M95ZObmd2j+ol1mYV3gD85UaPOlQ4X9erA/U8r\n" +
                "lpr5VbDvqC1BtKuJEqGHBIAhaUPT7m+VF0pCWbl8grWNYWzg/3ptjYD0TtmVkn4K\n" +
                "5gpEu9lWu8QlU2M9NJq5vGWuajQ9KfiL9ZZGtp3LinjnQw2/ugrGUMeZ7ueiPyLR\n" +
                "tNq4e9biGHmTo9LHkBsvQXFDPL1LPbhVScZV4EGwSP34Bh/YO/xEaXJB9R+2+18h\n" +
                "dOLZwLrZBu/N4ycaqP1VgmVqEU/FYBZV2wmmUwIhALV2R2/8WsKI/0xdsYuZMhnG\n" +
                "MrsczK5yznCpGDq6xMA5AoIBABV4jUwPrPbxu121atAm4BgJiLcBey2SUDH9gHJ/\n" +
                "YQ7ELt2FnQ9R+PjjcrPKfMCo7KrchbTyq4dLHCMCFLuvgHXmqq4lGh5wtaRUG2E/\n" +
                "fB8Q8s99ARS/zqb3OjY7Nv5OOdH/Soa8+UMpUAYp6VfWbnyY4gS8RMJvqUPOkP8x\n" +
                "P79/S06aE3pyhhb0EqfDQBNOkfdCiKCuMKuoHosggzBX29o9YjIOrYd3A3GgySgT\n" +
                "x1M5+UIxdOBj2LsdF7vNbaEBAG2zQqetz/ZSR7p3H5R3ufbw32pQPAlBlLpDkNa5\n" +
                "efZuEmqWvmayz3mWekzzsb7lrVg/IdtEzwjJ9X4e39rjFhwCggEBALVbZBEtH8zu\n" +
                "nWUa1hbLAzUc/2dNHUwP05qwGEo9NpRRRQ1veXURET1mFII+7cAhNJisSztCygab\n" +
                "1a8Vc07tf5u/lKKChiO6V9wOYNzo3i4PsVULm15VtByqn4vEB1JZE7PiwDp5bmPj\n" +
                "1e5vuZ/ZUUfWHecT7LbSZghuZplrL50vG7jNnOVDDQ4xG7neQf3SJwxKg9Emcrf7\n" +
                "rnAX7aHHvcWFLrr4rlnnuZKqyns9gVPQY7XhJgXH7QVuiNwrnovXfLGfHahvBwEq\n" +
                "VxPe3IEc6oFUOEoTkE98GgZC59Hqx98jC6iY7An63vLXHIF/HRYfwKf00A1l+qTG\n" +
                "6+ngLjzV9d8CH1ZtAHsoySQHj+QLIlIOBVXiO16GSoQW1ZNaz3M61AI=\n" +
                "-----END DSA PRIVATE KEY-----\n";
        String ecPrivateKey  = "-----BEGIN EC PRIVATE KEY-----\n" +
                "MHQCAQEEILm3BjMKVFegtAqLFrXh76HH6Y3Ehyhndao7Tgp2Zlm5oAcGBSuBBAAK\n" +
                "oUQDQgAE1h1zcNnztBboYOOYtIu8i/amMR66/huAlYB76P5T4ZVnRL8kn45zgahs\n" +
                "Ml6BDsXBCUtTdPfFPGQL6TJElpXGoQ==\n" +
                "-----END EC PRIVATE KEY-----\n";
        String rsaPrivateKey  = "-----BEGIN PRIVATE KEY-----\n" +
                "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCkkmK83VERePyt\n" +
                "IroBamhc3tll2lMRB1jSdiuQlft2rbnNfzlP73gfUiyl23bpOlS0o5RSHo2CstVy\n" +
                "55ExDnrdurGCmeGuCqZpn4oWC2zc66+YCohtWdAbtFtEGLi70W+Hj+JYeIrvkMvM\n" +
                "eSqss2G6li2p2Z2UrUqvIi1VSE5eQVKNbfW0Khz3kk4/NGWDiZ73MCTTIsZw6HDM\n" +
                "GHKwuBOajQ5pM0HVLlZchP1HsjQv4kypPPMG1K4Nlepy1tA3DkNNh+eysL9qT0KQ\n" +
                "LowWs2dvOYjmYf5VHEzIZOUNxB1nTDIEwWyD/PT/oyRIsjK3WsxV9mwuwprqC1gc\n" +
                "U8IYNBP7AgMBAAECggEAK+HIqDmPO0x6e6QN2wDHkTPu3gTIL3s5CO1vkl8brTqH\n" +
                "l6771j+xVRCxTQxm+auPGJehnf/9lcMfvULX6S/GPUdhu8RSc/jtBK8Av/4N6h6C\n" +
                "NNViKV5QaoK97zt0Tsh6p7gLD7IcweJnJT+NzH0MaxdOV8LqDNHkXyyKz/m2w9TT\n" +
                "uouqMCzw8+T/cV4EM1KMHQ+iWCh246waprYFmCCoPI53WqtVFqSs3YeHO1xHNlGc\n" +
                "NZZ74EiNa96XOixKv5gZvGvcnJ5Tvb7v0Qo5LjqVEKxsxVYGvG+etp2HNQXNNRaY\n" +
                "8BbHhHz4KHV2yF3gM19nGaN+sVdi4oLYpVdhWbCUgQKBgQDOZC2xnmrPsacPTe3s\n" +
                "pLJQpEbVQZcs7YeYR1UCWszTlyxuW+KOK+F/QmorbZ+5e8AzDJba+4OMO1qAYDVp\n" +
                "LUOchM5TeHwbmbERSvtsllBWe3qvqYUhVQY92ZVhelbuyFBQMaeg2FDAHz/9+U5o\n" +
                "zkyL6lNrvLfz8gMaley4wqhfwQKBgQDMIPBi2eq02PTo208XpsQ2Fa4e8ywg0GBM\n" +
                "i5wKtbkVt4qVKlrqgmjBNm5ejBmesE/x+2vAuNG+kI7mglNHIiBrYypz8o5KIS4l\n" +
                "FQ+Ug9zFG6EARHvD7aMhfYXBn4Mfql6TTtHmz+xNpH20paprl9+7iMQPkO+t3suH\n" +
                "z1PKoh6iuwKBgQCIBY332agbacHoXDvKEmFStHHaanfvrDwJKDnma2FCsgceVIBj\n" +
                "opfi/ypppL33yI9LFaj2eXyhFxz2LnIE90fwB7bTXDyvFAOWmgxJ7GbWGFsrGlYb\n" +
                "OaX7bUL+E1rn1CzaLV+EqgvOEsph38TFXfEk5TJFdwLlS0Kwas6wldnSAQKBgGTP\n" +
                "h6hqEr3Jv/oYBRGbLJ/BSULDuXFjN4vWDvRDFusgv/I2/rt+OBnjtdI/wo9aZ8EL\n" +
                "+AUvwXYpkklI57PjqLsgOGEW0yrNBCsQIaepD6jQokythaoXfE8X3KzpCCrlStvZ\n" +
                "O1SQLxWRPPuwLWABm17Uhm9hltz5gO7Ld4hFVHftAoGBALQ9z4rhXZs4+lB8nR+b\n" +
                "KXx3F7ImIvywCc+wzFuV06POtltHTj7xnq6LlnXlwLFu87vORcL/5VHwsjq58FnT\n" +
                "dZMPygqLqleLxSP0vI69T1bOvpjMt5yWDepH0RkkYtCX4ro98I5a6JxPT6fIp05x\n" +
                "ZTPwxXckJbmCs8hOsRYf8pnO\n" +
                "-----END PRIVATE KEY-----\n";

        String dsaPrivateKeyPassphrase = "-----BEGIN DSA PRIVATE KEY-----\n" +
                "Proc-Type: 4,ENCRYPTED\n" +
                "DEK-Info: AES-256-CBC,FA8A421E421B5721D4FBBB9143778A1A\n" +
                "\n" +
                "sBrmcPL/xEHNoucp9oRBhWb/jnRIVl84KJTzP+Dsc3Q2aulbI+3VUQFtrRtP7DDa\n" +
                "5abTv9KssGbo6EBz0VPENJvWetkjlY+MCrhLSpRnQqOkEYhdZduLJo3lZ9591qZW\n" +
                "MlbfSePaJoMC7YRumtGmrxiJVA/queCkUyDIp/Bqf47eASnpT05WOBiLDnZFXXWN\n" +
                "DYIFjCP0b7/ASzhQuLLelxMjGlT2BRQEOF6+ADnCNI15W5lA+hfdbxX6nELgYze7\n" +
                "FhDIIvQCm730h0iujmW9WOIev1GiZ/X2dMLHVijHm4huRPRiG5dk0zoYS8JzElw0\n" +
                "v1nfd71Xo/+7BGvYQJs6yK1u34BHHTOisEMadAg7DmFRiirninF1QfjY4VYBg6Uw\n" +
                "l3V7Pxpth9BhI2lZ7n9Ip19Shh7KMbIgu9WNKS1N4vYWoy7IwTmOJJvUtSkiQGSF\n" +
                "rrpTWX5q84Z8k8IN+Ufa7jgPkjgMzEodiN2b5OWw36IY6yX21yvG5pIvPc6nwwJy\n" +
                "gUpmO+GqgGQyHYCOG9iQq7PUibgqqm94zSm+RYMl9TgzX0E475qSMHID62cJj9Bj\n" +
                "t/bK6NTaazZ2x3qK2Lyz+pJZYJmZUpYj/Bd9YlSz3ex9shwX9cW5iYipUCnJD7Vm\n" +
                "Q4+Bsj25xcDAOGnPxCt/oCq0ZRhD2VBFxZnsYKT/0dQ38S2Gnz1QOvJgwTYcj0uo\n" +
                "ZB1cOdbXyNNI73yQ0LDY8nRfgljHM6vQPZg5iGbsfZ9WuHF6V7TlR3oHPXpOX8Ku\n" +
                "meRpYiwbA9ZEHUzc5e2is+4u1cTDaoRCHecpjHOhdRT2X53bss5bLPUgcPRTP/VC\n" +
                "xfjW6epYJQRUNmC4RSvEna5Fj9Y5KSwgd6F/lJmuCGihj/7dKvLwEhoAsuaT223E\n" +
                "7wZWC2RB8oH6BAQeV1WugGk6XtuQUco5Hn3J23t0R0NqOYpepBwIOruj5aB3V7ih\n" +
                "jH1AaDhZH7iDE02OM9sooZUwf42JE4PdQRsi6iz1Xbd5QBK/L4Q7IMgJ/ClOmmOS\n" +
                "QPRuAeP0yYM5kDkGExHGBZaCGw+WyA5Vdju3jtIxYMgiFgrFGhysyNkWySF/lUTD\n" +
                "zt5fOo69AHd4di1N7I4VgqCGDX+vY58h6lyNlL/hr4keGJ3iPSXzpKE+qxxfRhFm\n" +
                "-----END DSA PRIVATE KEY-----\n";
        String ecPrivateKeyPassphrase = "-----BEGIN EC PRIVATE KEY-----\n" +
                "Proc-Type: 4,ENCRYPTED\n" +
                "DEK-Info: AES-256-CBC,3DC703C0C73A55877B9A7129CCF366AF\n" +
                "\n" +
                "9ODLFl4Enhp/1H75ezoltc6D30Q90Xq0ATqRsG+x02m0xSn5fi0jMlvfKMV5BekJ\n" +
                "YWnvGdrN8m3xB3lS5XJmXpAhqTy4g0fXfjyGoklaDcQZZWZplErYN0anI6HUEpCQ\n" +
                "G7FFIR/A1OlR0+CT/b9OmpBiMi85zbu+Bie+NogAXqk=\n" +
                "-----END EC PRIVATE KEY-----\n";
        String rsaPrivateKeyPassphrase = "-----BEGIN ENCRYPTED PRIVATE KEY-----\n" +
                "MIIFHzBJBgkqhkiG9w0BBQ0wPDAbBgkqhkiG9w0BBQwwDgQI6nf23kAvXNYCAggA\n" +
                "MB0GCWCGSAFlAwQBKgQQj1+A+065zEAsFtiUOJ/22wSCBND7F7Tr0xOeE+srdx9+\n" +
                "Prm8wPDOzeqI+puPRSPrZ3DgcdczjIa8aI98g7pM2w5LON5GjSI3wPh5l47+Cv8J\n" +
                "zHXkyeQzqRagpbEdfrpEWaxbenOecWsVDv3ymKL7zNyeA67AiQtyj9lCIwkmWgeu\n" +
                "SqGuEfwXAVpnRBoi6VHgwBlgl/ezm+BO+8iC99HnoEJcIdwOTUabqbaXoQJnXArU\n" +
                "qedk6Syim0I1KTEMeKFLk8KLuSHF0yNuzM24lv+WzPpPT5kk0XdLTE+DGG2JAdNN\n" +
                "ck5Jaiz74TFRyI8QmWv+D1jrPd4zoV2jaxFDZyP0NjuCDIJlVUe0vVD6pxR5hL5D\n" +
                "J7tOPlP1TZCcddWlkPDSUxEtRZn312iblnCXD40iN7/Eoyy/QtUgwG94RGcq7fyF\n" +
                "X4GfvTgW/bDZK6Y9E2ekW3jWYY8/R0MSrToe5/f5I72TDex+jWF4Fde/Iqt3LlV+\n" +
                "Musdz7wkp7FdgKvO7djV1ygi5GcLMVxcPBqHg2jXh3XhyAhr69UEskpLiUVngHNc\n" +
                "UcHvRDLXYQyFDODkZf1hd0itGX/6Up/RYB21tK6T9rV7GBYbLWb5/zfMfW0cRlDU\n" +
                "vhxAlu4z+sixtiztmrX/TURUfINhBMhXA5dAjTI8aS2D9z0EwBKMvAlRhncGCvCk\n" +
                "tXZQc4uUwwRyeTgdohlnlOKj591eJ7XZIPUtEmDNXQMKQeoXD8V2jyohtLPwDKZi\n" +
                "rjJ5VYZomh8PVwJVUO05KnzOsU3c6t/9J4EKki7nnVtF9HTeWnDBhpYGfNrENYr1\n" +
                "MEK4gghxRsZekbgU1p/6Pl9nYMRRGDIUVAd6p2Mp+b1AvYGtXWigo4hr9i2Votpc\n" +
                "2heZXmJUV20IbEExRSYRxpkOGU1BkvFef95+JpMHgBET8whYkld/tB7PX5JEB6Q6\n" +
                "BdTJQyXCV0jD1VvF9lI1JgHPj4fvmWcCsEAyQKRPMK8PVk1EZB0DBlDNsyc1AsPy\n" +
                "BczpGMsnHRuENGxuGM1pSB4avcPuVOUb3PQuHYFdflaPQjaxj3C9gJ0yXxpXl5u1\n" +
                "UhdP0g8EWuM9+FxWwPX7ElzcQ9S/0EjEFCOe6XkNZtEyhIkXA34fy5OQeRxa0NAH\n" +
                "g5GTOo8usMwbU7K+wn50NEWeVMoiiVPoDFIkroH8mjCNZLlcTYd5eRZbSr3p1uxB\n" +
                "+XizW3JULuVdhYoW2quUkM3AXXHI7ztSy/DNbUm5qJ0/PPNBTNrpLDL+2mBR4H3B\n" +
                "FJxQ59drD5+eQta+uH4IFqxkG8kQQ2zmWNpikBqBey8tuDBH9EsmBUJdJorwfuVA\n" +
                "CyHHyBDvFxd3UU5zftnL/Qk3w//Q0Bs9YEv4KUNFz3fMud8dSzoYm2GZUBRu+wwG\n" +
                "ZtjY6HBOi0nciYts9pPI5WV4rYEIwNGWW7A/qOMNGzwhuj8zgCCSJA40BrMumceO\n" +
                "VvARASnYH1mRhD6JlEGd2sEoHfpNkOl2M7Bqx/2l/Fefvcfn5jH+7MvZ4QfiXp+H\n" +
                "uICRozYZSzryPqfeY9NLJuhd08EvyCk4reglEv4ZmufafCKaNCJjGOQ+vkNCdjPL\n" +
                "AYGFtHJMhPbJtH/l/fTxKxADJym4X1ePS0P10BB4v0i/mhjr8iO/CmGocfIkMe25\n" +
                "/DwXwOaFOFpjN6wV5O8NU8tPtw==\n" +
                "-----END ENCRYPTED PRIVATE KEY-----\n";

        return Status.status("OK", "Dummy-Method with Private Keys");
    }


    @ReactMethod(isBlockingSynchronousMethod = true)
    public String apiKeys(){

        // Amazon Web Services (AWS)
        String awsAccessKeyId = "AKIAIOSFODNN7EXAMPLE"; // AWS Access Key ID pattern
        String awsSecretAccessKey = "wJalrXUtnFEMI/K7MDENG/bPxRfiCYEXAMPLEKEY"; // AWS Secret Access Key pattern

        // Google Cloud Platform (GCP)
        String gcpApiKey = "AIzaSyD-9C5D5yX89GEXAMPLEK9rY1-2LQRzXY"; // Google API Key pattern
        String gcpOAuthClientId = "123456789012-abcde1f2g3h4ijklmnop5qrstuvwxyz.apps.googleusercontent.com"; // Google OAuth Client ID pattern
        String gcpOAuthClientSecret = "GOCSPX-3u8kxv9YZ4Q7EXAMPLEeUt-2fH2"; // Google OAuth Client Secret pattern

        // Microsoft Azure
        String azureSubscriptionKey = "0123456789abcdef0123456789ABCDEF"; // Azure Subscription Key pattern
        String azureClientId = "4a7f2e56-7b8e-4a90-bc5c-1234567890ab"; // Azure Client ID pattern
        String azureClientSecret = "7e5b4e2a-8d9e-4a90-c1e2-3456789abcde"; // Azure Client Secret pattern

        // IBM Cloud
        String ibmApiKey = "D4C0nZK3v7wY5U3p1K9X4Z0aEXAMPLEbN4L0aQ"; // IBM Cloud API Key pattern

        // Dropbox
        String dropboxAccessToken = "sl.BG0EZ37EXAMPL9mXZG67FZX82L4G5F4w"; // Dropbox Access Token pattern
        String dropboxAppKey = "q7yw3EXAMPL4pzoz5"; // Dropbox App Key pattern
        String dropboxAppSecret = "5lw7dqx1x87yz9EXAMPL3rtk7"; // Dropbox App Secret pattern

        // Twitter
        String twitterApiKey = "xvz1evFS4wEEPTGEFPHBogEXAMPLE"; // Twitter API Key pattern
        String twitterApiSecretKey = "L8qq9PZyRg6ieKGEKhZolgcEXAMPLPR5FUa"; // Twitter API Secret Key pattern
        String twitterAccessToken = "370773112-NY5IPQG6EXAMPLpRPAdUEB1H6a69z"; // Twitter Access Token pattern
        String twitterAccessTokenSecret = "km5gQPc8uGOM8eUOEXAMPLEJFTBa1T1qDs"; // Twitter Access Token Secret pattern

        // Facebook
        String facebookAppId = "123456789012345"; // Facebook App ID pattern
        String facebookAppSecret = "1a2b3c4d5e6f7g8h9i0jEXAMPLE"; // Facebook App Secret pattern

        // GitHub
        String githubPersonalAccessToken = "ghp_1234567890abcdefghijklmnOPQRSTUV"; // GitHub Personal Access Token pattern

        // Stripe
        String stripePublishableKey = "pk_test_51H8fQEXAMPLbG6pHQlnY5Kw9Qa60v1WuD1OqQ"; // Stripe Publishable Key pattern
        String stripeSecretKey = "sk_test_4eC39HqLyjWDarjtT1zdp7dcEXAMPL"; // Stripe Secret Key pattern

        // PayPal
        String paypalClientId = "AbcDEFGHIjklmnopQRstuvwxyz1234567890"; // PayPal Client ID pattern
        String paypalSecret = "EFGHijklmNOPQRSTuvwxYZ1234567890abcDEFg"; // PayPal Secret pattern

        // Spotify
        String spotifyClientId = "1234567890abcdef1234567890abcdef"; // Spotify Client ID pattern
        String spotifyClientSecret = "abcdef1234567890abcdef1234567890"; // Spotify Client Secret pattern

        // Slack
        String slackBotToken = "xoxb-123456789012-abcdefghijklmnopqrstuvwx"; // Slack Bot Token pattern
        String slackSigningSecret = "abcd1234efgh5678ijkl9012mnop3456qrst7890"; // Slack Signing Secret pattern

        // GitLab
        String gitlabPersonalAccessToken = "glpat-abcdefgh1234567890ijklmnopqrstuvwx"; // GitLab Personal Access Token pattern

        // Twilio
        String twilioAccountSid = "ACXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX"; // Twilio Account SID pattern
        String twilioAuthToken = "your_auth_token"; // Twilio Auth Token pattern

        // SendGrid
        String sendgridApiKey = "SG.xxxxxxxx.yyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyy"; // SendGrid API Key pattern


        return Status.status("OK", "Dummy-Method with API Keys");
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public String passwords(){

        // Dummy passwords
        String password1 = "dummyPassword123!";
        String password2 = "SuperSecurePassword456$";

        // Dummy Database connection strings
        String dbConnectionString1 = "jdbc:mysql://localhost:3306/mydatabase?user=root&password=rootpassword";
        String dbConnectionString2 = "mongodb://myUser:myPassword@localhost:27017/mydatabase";

        return Status.status("OK", "Dummy-Method with Passwords Keys");
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public String tokens(){

        String accessToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyLCJyb2xlIjoiT1dBU1AgTUFTIEFkbWluIn0.YODwerxN0UaqulVo32uT_Jt_QABoxvQheK2Dmfq_1Xc";
        String refreshToken = "MIOf-U1zQbyfa3MUfJHhvnUqIut9ClH0xjlDXGJAyqo";

        return Status.status("OK", "Dummy-Method with Token Keys");
    }

}