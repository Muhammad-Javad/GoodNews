package me.pitok.navigation

import android.os.Bundle
import android.os.Parcelable
import java.io.Serializable

sealed class Navigate {

    data class ToDirection(val directionId: DirectionId, val bundle: Bundle? = null) : Navigate()

    data class ToDeepLink(val deepLink: String,
                          val popUpInclusive: Boolean = false,
                          val destinationId: Int? = null,
                          val clearBackStack: Boolean = false) : Navigate()

    data class ToParcelableDeepLink(
        val deepLink: String,
        val parcelableData: Parcelable?
    ) : Navigate()

    data class ToSerializableDeepLink(
        val deepLink: String,
        val serializableData: Serializable?
    ) : Navigate()

    object Up : Navigate()

    object Recreate : Navigate()
}