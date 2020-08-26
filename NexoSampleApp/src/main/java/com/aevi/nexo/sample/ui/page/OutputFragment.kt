package com.aevi.nexo.sample.ui.page

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aevi.nexo.sample.R
import com.aevi.nexo.sample.data.OutputData
import com.aevi.nexo.sample.data.ScenarioController
import com.aevi.nexo.sample.data.ScenarioController.model
import com.aevi.nexo.sample.ui.PagerViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.fragment_out.view.*
import kotlinx.android.synthetic.main.view_text.view.*
import java.util.*

class OutputFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var viewModel: PagerViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_out, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.recyclerView
        recyclerView.apply {
            layoutManager = viewManager
            adapter = viewAdapter
        }
        onFabAction(view)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = model
        viewManager = LinearLayoutManager(context)
        bindOutputList()
    }

    private fun bindOutputList() {
        val data = mutableListOf<OutputData>()
        viewAdapter = ViewAdapter(data)
        viewModel.outputData.observe(this, Observer {
            data.clear()
            data.addAll(it)
            viewAdapter.notifyDataSetChanged()
        })
    }

    private fun onFabAction(view: View) {
        val fab: FloatingActionButton = view.findViewById(R.id.fab)
        fab.setOnClickListener {
            model.output = LinkedList()
        }
    }

    internal class ViewAdapter(private val data: MutableList<OutputData>) : RecyclerView.Adapter<ViewAdapter.MyViewHolder>() {

        class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val titleView: TextView = view.title
            val textView: TextView = view.text
            val status: CheckBox = view.status
        }


        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.view_text, parent, false)
            return MyViewHolder(view)
        }

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            val output = data[position]
            holder.titleView.text = output.title
            holder.textView.text = output.content
            holder.status.isChecked = output.outcome
        }

        override fun getItemCount() = data.size
    }

}
