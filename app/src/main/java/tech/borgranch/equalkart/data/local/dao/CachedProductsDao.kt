package tech.borgranch.equalkart.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import tech.borgranch.equalkart.data.local.dto.CachedProduct

@Dao
interface CachedProductsDao {
    @Insert
    fun insertAll(vararg products: CachedProduct)

    @Insert
    fun insert(product: CachedProduct)

    @Update
    fun update(product: CachedProduct)

    @Update
    fun updateAll(vararg products: CachedProduct)

    @Query("SELECT * FROM products")
    fun getAll(): List<CachedProduct>

    @Query("SELECT * FROM products WHERE id = :id")
    fun getById(id: Int): CachedProduct

    @Query("SELECT * FROM products WHERE name LIKE :name")
    fun getByName(name: String): List<CachedProduct>

    @Query("SELECT * FROM products WHERE description LIKE :description")
    fun getByDescription(description: String): List<CachedProduct>

    @Query("SELECT * FROM products WHERE price >= :minPrice AND price <= :maxPrice")
    fun getByPrice(minPrice: Double, maxPrice: Double): List<CachedProduct>

    @Query("SELECT * FROM products WHERE inStock = :inStock")
    fun getByInStock(inStock: Boolean): List<CachedProduct>

    @Query(
        """
        SELECT * FROM products 
        WHERE name LIKE :name AND 
        description LIKE :description AND 
        price >= :minPrice AND price <= :maxPrice 
        AND inStock = :inStock
        """,
    )
    fun getByAll(
        name: String,
        description: String,
        minPrice: Double,
        maxPrice: Double,
        inStock: Boolean,
    ): List<CachedProduct>
}
