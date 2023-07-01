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
    indices = [
        androidx.room.Index(value = ["orderId"], unique = false),
        androidx.room.Index(value = ["productId"], unique = false),
    ],
)
data class OrderItem(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val orderId: Long,
    val productId: Long,
    val quantity: Int,
    val price: Double,
)
