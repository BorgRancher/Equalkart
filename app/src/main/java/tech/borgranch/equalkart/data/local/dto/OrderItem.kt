package tech.borgranch.equalkart.data.local.dto

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "order_items",
    foreignKeys = [
        androidx.room.ForeignKey(
            entity = Order::class,
            parentColumns = ["id"],
            childColumns = ["orderId"],
            onDelete = androidx.room.ForeignKey.CASCADE,
        ),
        androidx.room.ForeignKey(
            entity = CachedProduct::class,
            parentColumns = ["id"],
            childColumns = ["productId"],
            onDelete = androidx.room.ForeignKey.CASCADE,
        ),
    ],
)
data class OrderItem(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val orderId: Int,
    val productId: Int,
    val quantity: Int,
    val price: Double,
)
