package com.example.partyernewversion.Presenter.placeMarkPresenters

import android.annotation.SuppressLint
import android.util.Log
import com.example.partyernewversion.Model.PlaceMark
import com.example.partyernewversion.View.IGetPlaceMarkView
import com.google.firebase.database.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap

class GetPlaceMarkPresenter(val getPlaceMarkView: IGetPlaceMarkView) : IGetPlaceMarkPresenter {
    private val database = FirebaseDatabase.getInstance()
    private var ref = database.reference

    override fun getAllPlaceMarks() {
        ref.child("PlaceMark").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (ds in snapshot.children) {
                    val mark: PlaceMark? = ds.getValue(PlaceMark::class.java)
                    mark?.id = ds.key
                    val timeRemoveLong = timestampToLong(mark?.timeRemoveEvent)
                    val dateTimeCurrent = Date()
                    val dateTimeRemove = Date(timeRemoveLong!!.toLong() * 1000)
                    mark?.timeEvent = timestampToDateString(timestampToLong(mark?.timeEvent)!!)
                    mark?.timeRemoveEvent = timestampToDateString(timestampToLong(mark?.timeRemoveEvent)!!)
                    if (dateTimeCurrent.time < dateTimeRemove.time) {
                        getPlaceMarkView.showPlaceMark(mark)
                    } else {
                        ds.ref.setValue(null)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("ERROR", "Failed to read value.", error.toException())
                getPlaceMarkView.errorGetPlaceMark("Failed to read value." + error.toException())
            }
        })
    }
    private fun timestampToLong(timeStamp:Any?):Long? {
        val time = timeStamp as HashMap<String, Long>
        val lTime = if(time["seconds"] == null) {
            0
        } else {
            time["seconds"]
        }
        return lTime
    }
    @SuppressLint("SimpleDateFormat")
    private fun timestampToDateString(timeLong:Long): String {
        val data = Date(timeLong * 1000)
        val parser = SimpleDateFormat("HH:mm")
        return parser.format(data)
    }
    override fun getInfoPlaceMark(idPlaceMark:String) {
        ref = FirebaseDatabase.getInstance().reference
        ref.child("PlaceMark").addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (ds in snapshot.children) {
                    if(ds.key.equals(idPlaceMark)) {
                        val mark: PlaceMark? = ds.getValue(PlaceMark::class.java)
                        mark?.id = ds.key
                        mark?.timeEvent = timestampToDateString(timestampToLong(mark?.timeEvent)!!)
                        mark?.timeRemoveEvent = timestampToDateString(timestampToLong(mark?.timeRemoveEvent)!!)
                        getPlaceMarkView.showInfoPlaceMark(mark)
                        Log.w("GET", mark?.id.toString())
                        break
                    }

                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("ERROR", "Failed to read value.", error.toException())
            }
        })
    }

    override fun updatePlaceMark() {
        ref.child("PlaceMark").addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                Log.w("EVENT", "onChildAdded")
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                Log.w("EVENT", "onChildChanged")
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                Log.w("REMPresent", "REmove in GetPlaceMarkPresrnter")
                getPlaceMarkView.removePlaceMarkOnMap(snapshot.key.toString())
            }
            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                Log.w("EVENT", "onChildMoved")
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("EVENT", "onCancelled")
            }

        })
    }

}

