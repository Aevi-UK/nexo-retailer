package com.aevi.nexo.sample.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aevi.nexo.sample.data.OutputData
import java.util.*

class PagerViewModel : ViewModel() {
    var currentTransaction: Any? = null
    val transactionInProgress: Boolean
        get() = currentTransaction != null

    val scenarioData = MutableLiveData(listOf(""))
    val scenarios: List<String>
        get() = scenarioData.value!!

    val selectedScenarioData = MutableLiveData(0)
    var selectedScenario: Int
        set(value) = selectedScenarioData.postValue(value)
        get() = selectedScenarioData.value ?: -1

    val selectedOutcomeData = MutableLiveData(0)
    var selectedOutcome: Int
        set(value) = selectedOutcomeData.postValue(value)
        get() = selectedOutcomeData.value ?: -1

    val outputData = MutableLiveData(LinkedList<OutputData>())
    var output: LinkedList<OutputData>
        set(value) = outputData.postValue(value)
        get() = outputData.value!!
}