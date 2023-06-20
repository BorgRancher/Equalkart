package tech.borgranch.equalkart.data.local.dto

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "orders")
data class Order(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val userId: Int,
    val status: String,
    val createdAt: String,
    val updatedAt: String,
    val items: List<OrderItem>,
)