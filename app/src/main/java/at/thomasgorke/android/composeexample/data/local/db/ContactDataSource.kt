package at.thomasgorke.android.composeexample.data.local.db

import at.thomasgorke.android.composeexample.data.local.db.entity.ContactEntity
import kotlinx.coroutines.flow.Flow

interface ContactDataSource {
    fun getAllContactsFlow(): Flow<List<ContactEntity>>
    suspend fun getAllContacts(): List<ContactEntity>
    suspend fun createNewContact(contactEntity: ContactEntity)
    suspend fun deleteAll()
}

class ContactDataSourceImpl(
    private val contactDao: ContactDao
) : ContactDataSource {

    override suspend fun createNewContact(contactEntity: ContactEntity) {
        println("create new contact data source")
        contactDao.insertContact(contactEntity)
    }

    override suspend fun deleteAll() {
        contactDao.deleteAll()
    }

    override fun getAllContactsFlow(): Flow<List<ContactEntity>> =
        contactDao.getAllContactsFlow()

    override suspend fun getAllContacts(): List<ContactEntity> =
        contactDao.getAllContacts()
}
