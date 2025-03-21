import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebView
import android.webkit.WebViewClient
import com.facebook.react.bridge.Promise
import org.masreferenceapp.ReturnStatus
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import okhttp3.Request


class PinningWebViewClient(
    private val testDomain: String,
    private val pins: Array<String>,
    private val promise: Promise,
    private val r: ReturnStatus
) : WebViewClient() {

    override fun shouldInterceptRequest(
        view: WebView?,
        request: WebResourceRequest?
    ): WebResourceResponse? {
        val url = request?.url.toString()

        val certificatePinner = CertificatePinner.Builder()

        for (pin in pins) {
            certificatePinner.add(testDomain, pin)
        }

        val okHttpClient = OkHttpClient.Builder()
            .certificatePinner(certificatePinner.build())
            .build()

        try {
            // Make the request using OkHttp, this will throw an exception if the validation fails
            val okRequest = Request.Builder()
                .url("https://$testDomain")
                .build()
            val response = okHttpClient.newCall(okRequest).execute()
            r.success("Pin verification successful. Connection to https://$testDomain established. Response code was: ${response.code}.")
            promise.resolve(r.toJsonString())

            // Return valid response to WebView
            return WebResourceResponse(
                response.header("content-type", "text/html"),
                response.header("content-encoding", "utf-8"),
                response.body?.byteStream()
            )
        } catch (e: Exception) {
            r.fail("Connection to https://$testDomain could not be established.")
            r.fail(e.toString())
            promise.resolve(r.toJsonString())
            return null
        }
    }
}