package com.aevi.nexo.sample.data

import android.content.Context
import com.aevi.nexo.sample.R
import com.aevi.nexo.sample.security.TLSSocketFactory
import com.aevi.nexo.sample.ui.PagerViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.InputStream
import java.net.Socket
import java.security.KeyStore
import java.util.*
import javax.net.ssl.KeyManagerFactory
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocketFactory

object ScenarioController {

    private const val OUTPUT_LINE_LIMIT = 25
    val model = PagerViewModel()

    fun addOutput(title: String, text: String, success: Boolean) {
        model.output.let { list ->
            val locale = Locale.getDefault()
            val formatted = title.toLowerCase(locale).replace("_", "\\s").capitalize()
            list.addFirst(OutputData(formatted, text, success))
            while (list.size > OUTPUT_LINE_LIMIT) {
                list.removeLast()
            }
            model.output = list
        }
    }

    lateinit var socketFactory: TLSSocketFactory

    fun setup(context: Context) {
//        val trusted: KeyStore = KeyStore.getInstance("BKS")
//        context.resources.openRawResource(R.raw.sample).use {
//            trusted.load(it, "sample".toCharArray())
//            it.close()
//        }
//
//        val kmf: KeyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm())
//        kmf.init(trusted, "sample".toCharArray())
//        val sslContext = SSLContext.getInstance("TLSv1.2")
//        sslContext.init(kmf.keyManagers, null, null)
//        socketFactory = TLSSocketFactory(sslContext.socketFactory)
    }

    fun start(): String {
        GlobalScope.launch(Dispatchers.IO) {
            val socket = Socket("127.0.0.1", 12345)
            socket.getOutputStream().write(byteArrayOf(1, 2, 3, 4, 5))
            socket.close()
        }

        /*
        val position = model.scenarios.getOrNull(model.selectedScenario)

        if (model.transactionInProgress) {
            return "Transaction already in progress!"
        }

        model.currentTransaction = position*/

        return "Scenario started"
    }

    fun reset() {
        model.currentTransaction = null
    }
}