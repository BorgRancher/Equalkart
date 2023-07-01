package tech.borgranch.equalkart.data.local.dto

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "orders")
data class Order(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val userId: Int,
    val status: String,
    val createdAt: Long,
    val updatedAt: Long,
    val discountPercent: Double,
    val taxPercent: Double,
)
