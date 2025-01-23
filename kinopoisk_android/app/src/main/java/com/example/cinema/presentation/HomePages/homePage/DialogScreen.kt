package com.example.cinema.presentation.HomePages.homePage

import android.app.AlertDialog
import android.widget.EditText
import androidx.fragment.app.FragmentActivity
import com.example.cinema.R
import com.example.cinema.data.dataBases.CollectionEntity

class DialogScreen {
    companion object {
        const val ADD_COLLECTION_DIALOG = 2
    }

    fun getDialog(activity: FragmentActivity, id: Int, viewModel: HomePageViewModel): AlertDialog? {
        val builder = AlertDialog.Builder(activity)
        val view = activity.layoutInflater.inflate(R.layout.dialog_add_collection, null)
        val editText = view.findViewById<EditText>(R.id.editText)
        return when (id) {
            ADD_COLLECTION_DIALOG -> {
                with(builder) {
                    setTitle(R.string.add_name_collection)
                    setView(view)
                    setPositiveButton(R.string.done) { _, _ ->
                        viewModel.addCollection(CollectionEntity(null, editText.text.toString()))
                    }
                    create()
                }
            }

            else -> null
        }

    }
}