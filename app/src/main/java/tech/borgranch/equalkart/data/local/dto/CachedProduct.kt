package tech.borgranch.equalkart.data.local.dto

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class CachedProduct(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val description: String,
    val price: Double,
    val image: String,
    val inStock: Boolean,
)
