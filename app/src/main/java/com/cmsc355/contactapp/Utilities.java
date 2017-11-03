package com.cmsc355.contactapp;

import android.app.Activity;
import android.support.v4.util.ArrayMap;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

class Utilities {
    //Returns a random string of lowercase letters
    static String generateRandomString(int length) {
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder(length);
        for(int i =0; i<stringBuilder.capacity();i++) {
            stringBuilder.append((char) ('a' + random.nextInt(26)));
        }
        return stringBuilder.toString();
    }

    //Given a linked (ordered) map, returns the Object at a given position by using an iterator
    static String getKeyAtPosition(LinkedHashMap<String, Object> map, int position) {
        Iterator<String> itr = map.keySet().iterator();
        String key = "";
        if (position == 0) {
            key = itr.next();
        }
        else {
            int i = 0;
            while (itr.hasNext() && i++ <= position) {
                key = itr.next();
            }
        }
        return key;
    }

    static void clearJSON(JSONObject json) {
        Iterator keys = json.keys();
        while(keys.hasNext()) {
            json.remove(keys.next().toString());
        }
    }

    //Extracts the data from a JSONObject into an ArrayMap. The structure of the map will mimic the JSON,
    //with JSONObjects represented by ArrayMaps, JSONArrays represented by ArrayLists, and any other
    //object type simply stored into the map
    static ArrayMap<String, Object> JSONToMap(JSONObject json) {
        ArrayMap<String, Object> retMap = new ArrayMap<>();

        if(json != JSONObject.NULL) {
            retMap = toMap(json);
        }
        return retMap;
    }

    //Used in JSONToMap, recursively drills into JSON to extract data. Maps represent JSONObjects
    private static ArrayMap<String, Object> toMap(JSONObject object) {
        ArrayMap<String, Object> map = new ArrayMap<>();

        Iterator<String> keysItr = object.keys();
        while(keysItr.hasNext()) {
            String key = keysItr.next();
            Object value = new Object();
            try {
                value = object.get(key);
            }
            catch (JSONException e) {
                e.printStackTrace();
            }

            if(value instanceof JSONArray) {
                value = toList((JSONArray) value);
            }
            else if(value instanceof JSONObject) {
                value = toMap((JSONObject) value);
            }
            map.put(key, value);
        }
        return map;
    }

    //Used in JSONToMap, recursively drills into JSON to extract data. Lists represent JSONArrays
    private static ArrayList<Object> toList(JSONArray array) {
        ArrayList<Object> list = new ArrayList<>();
        for(int i = 0; i < array.length(); i++) {
            Object value = new Object();
            try {
                value = array.get(i);
            }
            catch (JSONException e) {
                e.printStackTrace();
            }

            if(value instanceof JSONArray) {
                value = toList((JSONArray) value);
            }
            else if(value instanceof JSONObject) {
                value = toMap((JSONObject) value);
            }
            list.add(value);
        }
        return list;
    }

    //Hides the on-screen keyboard
    static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (activity.getCurrentFocus() != null) {
            inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
        }
    }

    //Given a list of Contacts, sorts them by name and returns the sorted list.
    //IMPORTANT - this method modifies the input contactList. to keep your data in the same object,
    //call like: 'someList = Utilities.sortContactList(someList);'
    static ArrayList<Contact> sortContactList(ArrayList<Contact> contactList) {
        ArrayList<Contact> newContactList = new ArrayList<>();
        int numContacts = contactList.size();
        for (int i = 0; i < numContacts; i++) {
            Contact nextContact = new Contact();
            nextContact.setName("ZZZZZZZZZZZZZZZZZZZZZ");
            for (int j = 0; j < contactList.size(); j++) {
                if (contactList.get(j).getName().compareToIgnoreCase(nextContact.getName()) < 0) {
                    nextContact = contactList.get(j);
                }
            }
            newContactList.add(nextContact);
            contactList.remove(contactList.indexOf(nextContact));
        }
        return newContactList;
    }

    //Given a list of ContactGroups, sorts them by name and returns the sorted list.
    //IMPORTANT - this method modifies the input groupList. to keep your data in the same object,
    //call like: 'someList = Utilities.sortGroupList(someList);'
    static ArrayList<ContactGroup> sortGroupList(ArrayList<ContactGroup> groupList) {
        ArrayList<ContactGroup> newGroupList = new ArrayList<>();
        int numGroups = groupList.size();
        for (int i = 0; i < numGroups; i++) {
            ContactGroup nextGroup = new ContactGroup();
            nextGroup.setName("ZZZZZZZZZZZZZZZZZZZZZ");
            for (int j = 0; j < groupList.size(); j++) {
                if (groupList.get(j).getName().compareToIgnoreCase(nextGroup.getName()) < 0) {
                    nextGroup = groupList.get(j);
                }
            }
            newGroupList.add(nextGroup);
            groupList.remove(groupList.indexOf(nextGroup));
        }
        return newGroupList;
    }
}