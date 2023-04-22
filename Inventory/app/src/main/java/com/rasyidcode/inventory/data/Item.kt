package com.rasyidcode.inventory.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.NumberFormat
import java.util.Locale

@Entity(tableName = "item")
data class Item(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "name")
    val itemName: String,
    @ColumnInfo(name = "price")
    val itemPrice: Double,
    @ColumnInfo(name = "quantity")
    val quantityInStock: Int
)

fun Item.getFormattedPrice(): String =
    NumberFormat.getCurrencyInstance(Locale("en", "US")).format(itemPrice)