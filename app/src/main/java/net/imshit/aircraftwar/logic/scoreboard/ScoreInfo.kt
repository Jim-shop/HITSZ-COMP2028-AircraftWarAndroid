package net.imshit.aircraftwar.logic.scoreboard

import android.os.Parcel
import android.os.Parcelable

data class ScoreInfo(
    val name: String, val score: Int, val time: String
) : Parcelable {

    constructor(parcel: Parcel) : this(
        name = parcel.readString() ?: "", score = parcel.readInt(), time = parcel.readString() ?: ""
    )

    override fun describeContents(): Int = 0

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(this.name)
        dest.writeInt(this.score)
        dest.writeString(this.time)
    }

    companion object CREATOR : Parcelable.Creator<ScoreInfo> {
        override fun createFromParcel(parcel: Parcel): ScoreInfo {
            return ScoreInfo(parcel)
        }

        override fun newArray(size: Int): Array<ScoreInfo?> {
            return arrayOfNulls(size)
        }
    }
}
