import android.content.ContentResolver
import android.content.Context
import android.provider.ContactsContract
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

data class Contact(val name: String, val number: String)

interface ContentManager {
    suspend fun getContacts(): MutableList<Contact>
}

class ContactProvider(private val appContext: Context) : ContentManager {

    override suspend fun getContacts(): MutableList<Contact> = withContext(Dispatchers.IO) {
        val contentResolver: ContentResolver = appContext.contentResolver
        val contacts: MutableList<Contact> = ArrayList()
        val projection = arrayOf(
            ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
            ContactsContract.CommonDataKinds.Phone.CUSTOM_RINGTONE,
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
            ContactsContract.CommonDataKinds.Phone.NUMBER
        )
        val cursor = contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            projection,
            ContactsContract.Contacts.HAS_PHONE_NUMBER + ">0 AND LENGTH(" + ContactsContract.CommonDataKinds.Phone.NUMBER + ")>0",
            null,
            "display_name ASC"
        )

        if (cursor != null && cursor.count > 0) {
            while (cursor.moveToNext()) {
                val name =
                    cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
                val mobileNumber =
                    cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                val contact = Contact(name, mobileNumber)
                contacts.add(contact)
            }
            cursor.close()
        }
        return@withContext contacts
    }
}


