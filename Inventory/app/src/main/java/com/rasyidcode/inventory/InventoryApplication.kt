package com.rasyidcode.inventory

import android.app.Application
import com.rasyidcode.inventory.data.ItemRoomDatabase

class InventoryApplication : Application() {

    val database: ItemRoomDatabase by lazy { ItemRoomDatabase.getDatabase(this) }

}