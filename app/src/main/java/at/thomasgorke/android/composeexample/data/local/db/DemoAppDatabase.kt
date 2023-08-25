package at.thomasgorke.android.composeexample.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import at.thomasgorke.android.composeexample.data.local.db.entity.ContactEntity

@Database(
    entities = [ContactEntity::class],
    version = 1
)
abstract class DemoAppDatabase : RoomDatabase() {
    abstract fun contactDao(): ContactDao
}