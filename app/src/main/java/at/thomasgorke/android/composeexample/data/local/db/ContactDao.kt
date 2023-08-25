package at.thomasgorke.android.composeexample.data.local.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import at.thomasgorke.android.composeexample.data.local.db.entity.ContactEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ContactDao {

    @Insert
    suspend fun insertContact(contact: ContactEntity)

    @Query("SELECT * FROM contacts")
    suspend fun getAllContacts(): List<ContactEntity>

    @Query("SELECT * FROM contacts")
    fun getAllContactsFlow(): Flow<List<ContactEntity>>

    @Query("DELETE FROM contacts")
    suspend fun deleteAll()
}