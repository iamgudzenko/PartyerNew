package com.example.partyernewversion.Presenter.placeMarkPresenters

import android.util.Log
import com.example.partyernewversion.Model.PlaceMark
import com.example.partyernewversion.View.ISearchResultPlaseMarkView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class SearchResultPlaseMarkPresenter(private val searchResultPlaseMarkView: ISearchResultPlaseMarkView):
    ISearchResultPlaseMarkPresenter {
    private val database = FirebaseDatabase.getInstance()
    private var ref = database.reference

    override fun getResultSearchPlaseMark(hashtag:String) {
        ref = FirebaseDatabase.getInstance().reference
        ref.child("PlaceMark").addValueEventListener(object :
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (ds in snapshot.children) {
                    val mark: PlaceMark? = ds.getValue(PlaceMark::class.java)
                    mark?.id = ds.key
                    Log.w("SEARCH", mark?.id.toString())
                    if(mark?.hashtagsList!!.contains(hashtag)) {
                        Log.w("SEARCH", mark.id.toString())
                        searchResultPlaseMarkView.showResultSearchPlaceMark(mark)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("ERROR", "Failed to read value.", error.toException())
            }
        })
    }
}