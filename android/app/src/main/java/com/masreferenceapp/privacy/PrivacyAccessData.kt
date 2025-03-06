package com.masreferenceapp.privacy

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.provider.CalendarContract
import android.provider.ContactsContract
import android.provider.Telephony
import android.telephony.SmsManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.facebook.react.bridge.Promise
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import com.masreferenceapp.ReturnStatus


class PrivacyAccessData(var context: ReactApplicationContext) : ReactContextBaseJavaModule(
    context
) {
    override fun getName(): String {
        return "PrivacyAccessData"
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    fun getContacts(): String {

        if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_CONTACTS)
            != PackageManager.PERMISSION_GRANTED
        ) {
            currentActivity?.let {
                ActivityCompat.requestPermissions(
                    it,
                    arrayOf(Manifest.permission.READ_CONTACTS),
                    1
                )
            }
        }

        val cursor = context.contentResolver.query(
            ContactsContract.Contacts.CONTENT_URI,
            null,
            null,
            null,
            null
        )

        val count = cursor?.count

        cursor?.use {
            var index = it.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)
            index = it.getColumnIndex(ContactsContract.Contacts.PHONETIC_NAME)
            index = it.getColumnIndex(ContactsContract.Contacts.CONTACT_STATUS)
            index = it.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME_PRIMARY)
            index = it.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)
        }

        val r = ReturnStatus("OK", "Tried to access $count contacts.")
        return r.toJsonString()
    }


    @ReactMethod(isBlockingSynchronousMethod = true)
    fun getCalendarEvent(): String {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_CALENDAR)
            != PackageManager.PERMISSION_GRANTED
        ) {
            currentActivity?.let {
                ActivityCompat.requestPermissions(
                    it,
                    arrayOf(Manifest.permission.READ_CALENDAR),
                    1
                )
            }
        }

        val r = ReturnStatus()


        val calendarCursor = context.contentResolver.query(
            CalendarContract.Calendars.CONTENT_URI,
            null,
            null,
            null,
            null
        )

        if (calendarCursor != null) {
            r.addStatus("OK", "Tried to access ${calendarCursor.count} calendars.")
        } else {
            r.addStatus("OK", "Tried to access 0 calendars.")
        }

        val eventsCursor = context.contentResolver.query(
            CalendarContract.Events.CONTENT_URI,
            null,
            null,
            null,
            null
        )

        if (eventsCursor != null) {
            r.addStatus("OK", "Tried to access ${eventsCursor.count} events.")
        } else {
            r.addStatus("OK", "Tried to access 0 events.")
        }

        val attendeesCursor = context.contentResolver.query(
            CalendarContract.Attendees.CONTENT_URI,
            null,
            null,
            null,
            null
        )

        if (attendeesCursor != null) {
            r.addStatus("OK", "Tried to access ${attendeesCursor.count} attendees.")
        } else {
            r.addStatus("OK", "Tried to access 0 attendees.")
        }

        calendarCursor?.use {
            var index = it.getColumnIndex(CalendarContract.Calendars.CALENDAR_DISPLAY_NAME)
            index = it.getColumnIndex(CalendarContract.Calendars.NAME)
            index = it.getColumnIndex(CalendarContract.Calendars.CALENDAR_LOCATION)
            index = it.getColumnIndex(CalendarContract.Calendars.OWNER_ACCOUNT)
            index = it.getColumnIndex(CalendarContract.Calendars.SYNC_EVENTS)
        }

        eventsCursor?.use {
            var index = it.getColumnIndex(CalendarContract.Events.TITLE)
            index = it.getColumnIndex(CalendarContract.Events.CALENDAR_DISPLAY_NAME)
            index = it.getColumnIndex(CalendarContract.Events.DESCRIPTION)
            index = it.getColumnIndex(CalendarContract.Events.EVENT_LOCATION)
            index = it.getColumnIndex(CalendarContract.Events.ORGANIZER)
        }

        attendeesCursor?.use {
            var index = it.getColumnIndex(CalendarContract.Attendees.TITLE)
            index = it.getColumnIndex(CalendarContract.Attendees.DESCRIPTION)
            index = it.getColumnIndex(CalendarContract.Attendees.EVENT_LOCATION)
            index = it.getColumnIndex(CalendarContract.Attendees.ORGANIZER)
        }

        return r.toJsonString()
    }


    @ReactMethod(isBlockingSynchronousMethod = true)
    fun getSMS(): String {
        val r = ReturnStatus()
        try {

            val SMS_PERMISSION_CODE = 101

            if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_SMS)
                != PackageManager.PERMISSION_GRANTED
            ) {
                currentActivity?.let {
                    ActivityCompat.requestPermissions(
                        it,
                        arrayOf(Manifest.permission.READ_SMS),
                        SMS_PERMISSION_CODE
                    )
                }
            }

            val cursor = context.contentResolver.query(
                Telephony.Sms.CONTENT_URI,
                null,
                null,
                null,
                null
            )

            val count = cursor?.count

            cursor?.use {
                var index = it.getColumnIndex(Telephony.Sms.SUBJECT)
                index = it.getColumnIndex(Telephony.Sms.BODY)
                index = it.getColumnIndex(Telephony.Sms.ADDRESS)
                index = it.getColumnIndex(Telephony.Sms.CREATOR)
                index = it.getColumnIndex(Telephony.Sms.PERSON)
            }

            r.success("Tried to access $count SMS.")
        } catch (e: Exception) {
            r.fail(e.toString())
        }
        return r.toJsonString()
    }


    @ReactMethod(isBlockingSynchronousMethod = true)
    fun sendSMS(): String {
        val r = ReturnStatus()
        try {

            val SMS_PERMISSION_CODE = 102

            if (ContextCompat.checkSelfPermission(context, Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED
            ) {
                currentActivity?.let {
                    ActivityCompat.requestPermissions(
                        it,
                        arrayOf(Manifest.permission.SEND_SMS),
                        SMS_PERMISSION_CODE
                    )
                }
            }

            val smsManager: SmsManager = SmsManager.getDefault()
            smsManager.sendTextMessage("000000000", null, "TestMessage", null, null)

            r.success("Send a test message to 000000000.")
        } catch (e: Exception) {
            r.fail(e.toString())
        }
        return r.toJsonString()
    }

    @ReactMethod()
    fun getLocation(promise: Promise) {
        val r = ReturnStatus()
        try {
            val LOCATION_PERMISSION_REQUEST_CODE = 100

            if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
            ) {
                currentActivity?.let {
                    ActivityCompat.requestPermissions(
                        it,
                        arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                        LOCATION_PERMISSION_REQUEST_CODE
                    )
                }
            }

            val locationManager =
                context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

            val providers = locationManager.getProviders(false)
            r.success("Location providers: $providers")

            val lastLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            r.success("Last location: $lastLocation")

            locationManager.getCurrentLocation(
                LocationManager.GPS_PROVIDER,
                null,
                ContextCompat.getMainExecutor(context)
            ) { location ->
                callback(location, promise, r)
            }

        } catch (e: Exception) {
            r.fail(e.toString())
            promise.resolve(r.toJsonString())
        }
    }

    private fun callback(location: Location?, promise: Promise, r: ReturnStatus) {
        r.success("Current location: $location")
        promise.resolve(r.toJsonString())
    }

    //@method
}

