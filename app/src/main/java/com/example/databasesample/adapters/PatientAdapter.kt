package com.example.databasesample.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.databasesample.R
import com.example.databasesample.database.PatientEntitiy
import com.example.databasesample.databinding.ItemPatientBinding
import com.example.databasesample.views.TestActivity
import com.example.databasesample.views.UpdatePatientActivity


class PatientAdapter : RecyclerView.Adapter<PatientAdapter.PatientViewHolder>() {

    private var patients: List<PatientEntitiy> = emptyList()


    fun setData(patients: List<PatientEntitiy>) {
        this.patients = patients
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PatientViewHolder {
        val binding = ItemPatientBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PatientViewHolder(binding, patients)
    }

    override fun onBindViewHolder(holder: PatientViewHolder, position: Int) {
        holder.bind(patients[position])
    }

    override fun getItemCount(): Int = patients.size


    fun getItem(position: Int): PatientEntitiy {
        return patients[position]
    }


    class PatientViewHolder(private val binding: ItemPatientBinding, private val patients: List<PatientEntitiy>) : RecyclerView.ViewHolder(binding.root) {
        val patientname =  binding.root.context.getString(R.string.patient_name)
        val patient_dept= binding.root.context.getString(R.string.patient_department)
        val patient_room= binding.root.context.getString(R.string.patient_room)
        private val PREF_PATIENT_ID = "pref_patient_id"
        fun bind(patient: PatientEntitiy) {

            binding.tvPatientName.text = patientname + patient.firstname + " " + patient.lastname
            binding.tvPatientDepartment.text = patient_dept + patient.department
            binding.tvPatientRoom.text = patient_room + patient.room
        }
            init {
                val sharedPreferences = itemView.context.getSharedPreferences("my_prefs", Context.MODE_PRIVATE)

                // Add OnClickListener to the root view of the item layout
                binding.root.setOnClickListener {
                    // Get the position of the clicked item
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        val patient = patients[position]
                        // Save the patient ID to shared preferences

                        val patientId = patient.patientId
                        val editor = sharedPreferences.edit()

                        if (patientId != null) {
                            editor.putInt(PREF_PATIENT_ID, patientId)
                        }
                        else
                            editor.putInt(PREF_PATIENT_ID, -1)
                        editor.apply()


                        // Launch the patient details activity and pass the patient ID
                        val intent = Intent(itemView.context, UpdatePatientActivity::class.java).apply {
                            putExtra("source", "UpdatePatientActivity")
                        }
                        itemView.context.startActivity(intent)


                    }
                }
            }

        }

    }