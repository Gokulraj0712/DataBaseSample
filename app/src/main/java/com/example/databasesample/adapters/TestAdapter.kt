package com.example.databasesample.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.databasesample.R
import com.example.databasesample.database.TestEntitiy
import com.example.databasesample.databinding.ItemTestBinding

class TestAdapter: RecyclerView.Adapter<TestAdapter.TestViewHolder>() {

    private var tests: List<TestEntitiy> = emptyList()

    fun setData(tests: List<TestEntitiy>) {
        this.tests = tests
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TestViewHolder {
        val binding = ItemTestBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TestViewHolder(binding, tests)
    }

    override fun onBindViewHolder(holder: TestViewHolder, position: Int) {
        holder.bind(tests[position])
    }

    override fun getItemCount(): Int = tests.size


    fun getItem(position: Int): TestEntitiy {
        return tests[position]
    }

    class TestViewHolder(private val binding: ItemTestBinding, private val tests: List<TestEntitiy>) : RecyclerView.ViewHolder(binding.root) {
        val testid =  binding.root.context.getString(R.string.test_id)
        val bpl= binding.root.context.getString(R.string.bpl)
        val bph= binding.root.context.getString(R.string.bph)
        val temp= binding.root.context.getString(R.string.temperature)

        fun bind(test: TestEntitiy) {

            binding.testId.text = testid + test.testId
            binding.testBPL.text = bpl + test.bpl
            binding.testBPH.text=bph + test.bph
            binding.testtemp.text = temp + test.temperature
        }

    }

}