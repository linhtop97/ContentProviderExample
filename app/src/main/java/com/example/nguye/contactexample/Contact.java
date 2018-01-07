package com.example.nguye.contactexample;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nguye on 06/01/2018.
 */

public class Contact {
    private int mID;
    private String mName;
    private String mPhone;
    private boolean mIsFavorit;

    public boolean ismIsFavorit() {
        return mIsFavorit;
    }

    public void setmIsFavorit(boolean mIsFavorit) {
        this.mIsFavorit = mIsFavorit;
    }

    public Contact(){
    }
    public Contact(String name, String phone){
        mName = name;
        mPhone = phone;
        mIsFavorit = false;
    }

    public ArrayList<Contact> getFavoriteContacts(Context context) {
        List<Contact> contacts = new ArrayList<>();
        //tạo đối tượng ContentResolver
        ContentResolver cr = context.getContentResolver();
        //truy vấn lấy về Cursor chứa tất cả dữ liệu của danh bạ
        Cursor cur = cr.query(
                ContactsContract.Contacts.CONTENT_URI, null, ContactsContract.Contacts.STARRED + "=?",
                new String[] {"1"}, null);

        if ((cur != null ? cur.getCount() : 0) > 0) {
            while (cur != null && cur.moveToNext()) {
                String id = cur.getString(
                        cur.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cur.getString(cur.getColumnIndex(
                        ContactsContract.Contacts.DISPLAY_NAME));

                if (cur.getInt(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
                    Cursor pCur = cr.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                            new String[]{id}, null);
                    while (pCur.moveToNext()) {
                        String phoneNo = pCur.getString(pCur.getColumnIndex(
                                ContactsContract.CommonDataKinds.Phone.NUMBER));
                        contacts.add(new Contact(name, phoneNo));
                    }
                    pCur.close();
                }
            }
        }
        if (cur != null) {
            cur.close();
        }
        return (ArrayList<Contact>) contacts;

    }

    public ArrayList<Contact> getAllContact(Context context) {
        List<Contact> contacts = new ArrayList<>();
        //tạo đối tượng ContentResolver
        ContentResolver cr = context.getContentResolver();
        //truy vấn lấy về Cursor chứa tất cả dữ liệu của danh bạ
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);

        if ((cur != null ? cur.getCount() : 0) > 0) {
            while (cur != null && cur.moveToNext()) {
                String id = cur.getString(
                        cur.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cur.getString(cur.getColumnIndex(
                        ContactsContract.Contacts.DISPLAY_NAME));

                if (cur.getInt(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
                    Cursor pCur = cr.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                            new String[]{id}, null);
                    if(pCur!=null){
                        while (pCur.moveToNext()) {
                            String phoneNo = pCur.getString(pCur.getColumnIndex(
                                    ContactsContract.CommonDataKinds.Phone.NUMBER));
                            contacts.add(new Contact(name, phoneNo));
                        }
                        pCur.close();
                    }
                }
            }
        }
        if (cur != null) {
            cur.close();
        }
        return (ArrayList<Contact>) contacts;

    }

    public ArrayList<Contact> getContact(Context context){
        List<Contact> contacts1 = getAllContact(context);
        List<Contact> contacts2 = getFavoriteContacts(context);
        for (int i = 0; i<contacts1.size();i++){
            Contact contact1 = contacts1.get(i);
            for (int j = 0;j<contacts2.size();j++){
                Contact contact2 = contacts2.get(j);
                if(contact1.getmName().equals(contact2.getmName()) &&
                        contact1.getmPhone().equals(contact2.getmPhone())){
                    contacts1.get(i).setmIsFavorit(true);
                }
            }
        }
        return (ArrayList<Contact>) contacts1;
    }
//read only file system
//    public void addContact(Context context,Contact contact){
//        ContentResolver cr = context.getContentResolver();
//        ContentValues values = new ContentValues();
//            values.put(ContactsContract.Contacts.DISPLAY_NAME, contact.getmName());
//            values.put(ContactsContract.CommonDataKinds.Phone.NUMBER, contact.getmPhone());
//            Uri uri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, 10000000);
//            cr.insert(uri,values);
//        }

    public int getmID() {
        return mID;
    }

    public void setmID(int mID) {
        this.mID = mID;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmPhone() {
        return mPhone;
    }

    public void setmPhone(String mPhone) {
        this.mPhone = mPhone;
    }
}
