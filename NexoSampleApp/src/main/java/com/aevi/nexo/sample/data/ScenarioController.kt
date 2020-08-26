package com.aevi.nexo.sample.data

import com.aevi.nexo.sample.ui.PagerViewModel
import java.util.*

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

    fun start(): String {
        val position = model.scenarios.getOrNull(model.selectedScenario)

        if (model.transactionInProgress) {
            return "Transaction already in progress!"
        }

        model.currentTransaction = position

        return "Scenario started"
    }

    fun reset() {
        model.currentTransaction = null
    }
}