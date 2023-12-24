package com.akbar.suitmediatestaplication.data

import android.os.Parcel
import android.os.Parcelable

data class User(
    val email: String,
    val first_name: String,
    val last_name: String,
    val avatar: String
) : Parcelable {
    // Parcelable implementation goes here

    // Example implementation using parceler library
    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<User> = object : Parcelable.Creator<User> {
            override fun createFromParcel(parcel: Parcel): User {
                return User(parcel)
            }

            override fun newArray(size: Int): Array<User?> {
                return arrayOfNulls(size)
            }
        }
    }

    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: ""
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(email)
        parcel.writeString(first_name)
        parcel.writeString(last_name)
        parcel.writeString(avatar)
    }

    override fun describeContents(): Int {
        return 0
    }
}
