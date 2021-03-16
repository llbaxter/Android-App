package com.example.myapplication

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.widget.ListAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import kotlinx.android.synthetic.main.activity_contact.*
import java.io.BufferedInputStream

class ContactActivity : AppCompatActivity() {
    val REQUEST_PERMISSION = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact)

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_CONTACTS), REQUEST_PERMISSION)
        } else {
            getContacts()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == REQUEST_PERMISSION) {
            getContacts()
        }
    }

    private fun getContacts() {
        val adapter = ContactListAdapter(this, getContactsData())
        contacts_list.adapter = adapter
    }

    private fun getContactsData(): ArrayList<Contact> {
        val contactList = ArrayList<Contact>()
        val contactsCursor = contentResolver.query(
                ContactsContract.Contacts.CONTENT_URI,
                null,
                null,
                null,
                null
        )

        if ((contactsCursor?.count ?: 0) > 0) {
            while (contactsCursor != null && contactsCursor.moveToNext()) {
                val rowID = contactsCursor.getString(contactsCursor.getColumnIndex(ContactsContract.Contacts._ID))
                val name = contactsCursor.getString(contactsCursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
                var phoneNumber = ""
                if (contactsCursor.getInt(contactsCursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
                    val phoneNumberCursor = contentResolver.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                            arrayOf<String>(rowID),
                            null
                    )
                    if (phoneNumberCursor != null) {
                        while (phoneNumberCursor.moveToNext()) {
                            phoneNumber += phoneNumberCursor.getString(phoneNumberCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                        }
                        phoneNumberCursor.close()
                    }
                    contactList.add(Contact(name, phoneNumber))
                }
            }
            contactsCursor?.close()
        }
        return contactList
    }
}