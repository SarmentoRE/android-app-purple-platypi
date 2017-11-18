package com.cmsc355.contactapp;

import android.support.v4.util.ArrayMap;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UtilitiesTest {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Test
    public void generateRandomStringTest() throws Exception {
        int length = 10;
        String testString = Utilities.generateRandomString(length);

        assertEquals("Random string is incorrect length", length, testString.length());

        assertTrue("String contains characters other than lowercase letters",
                testString.matches("[a-z]*"));
    }


    @Test
    public void getKeyAtPositionTest() throws Exception {
        LinkedHashMap<String, Object> testMap = new LinkedHashMap<>();
        int elements = 5;
        String[] keyArray = new String[elements];

        for (int i = 0; i < elements; i++) {
            String key = "Key" + i;
            testMap.put(key, null);
            keyArray[i] = key;
        }

        int position = 0;
        assertEquals("Map key at does not match array value at first position",
                keyArray[position], Utilities.getKeyAtPosition(testMap, position));

        position = elements - 1;
        assertEquals("Map key at does not match array value at last position",
                keyArray[position], Utilities.getKeyAtPosition(testMap, position));
    }


    @Mock
    private JSONObject jsonUpper;

    @Mock
    private JSONObject jsonLower;

    @Mock
    private JSONArray jsonArrayUpper;

    @Mock
    private JSONArray jsonArrayLower;

    @Mock
    private Iterator<String> keysItrUpper;

    @Mock
    private Iterator<String> keysItrLower;

    @Test
    public void jsonToMapTest() throws Exception {

        when(jsonUpper.get("JSONObject")).thenReturn(jsonLower);
        when(jsonUpper.get("JSONArray")).thenReturn(jsonArrayUpper);
        when(jsonUpper.keys()).thenReturn(keysItrUpper);

        when(keysItrUpper.hasNext()).thenReturn(true, true, false);
        when(keysItrUpper.next()).thenReturn("JSONObject", "JSONArray");

        when(jsonLower.get("Key1")).thenReturn("Value1");
        when(jsonLower.keys()).thenReturn(keysItrLower);

        when(keysItrLower.hasNext()).thenReturn(true, false, true, false);
        when(keysItrLower.next()).thenReturn("Key1");

        when(jsonArrayUpper.get(0)).thenReturn(jsonLower);
        when(jsonArrayUpper.get(1)).thenReturn(jsonArrayLower);
        when(jsonArrayUpper.length()).thenReturn(2);

        when(jsonArrayLower.get(0)).thenReturn("Value2");
        when(jsonArrayLower.length()).thenReturn(1);

        ArrayMap<String, Object> testMap = Utilities.jsonToMap(jsonUpper);

        assertEquals("Map value does not match first level JSONObject value",
                ((JSONObject) jsonUpper.get("JSONObject")).get("Key1"),
                ((ArrayMap<String, Object>) testMap.get("JSONObject")).get("Key1"));

        assertEquals("Map value does not match second level JSONObject value",
                ((JSONObject) ((JSONArray) jsonUpper.get("JSONArray")).get(0)).get("Key1"),
                ((ArrayMap<String, Object>) ((ArrayList<Object>) testMap.get("JSONArray")).get(0)).get("Key1"));

        assertEquals("List value does not match second level JSONArray value",
                ((JSONArray) ((JSONArray) jsonUpper.get("JSONArray")).get(1)).get(0),
                ((ArrayList<String>) ((ArrayList<Object>) testMap.get("JSONArray")).get(1)).get(0));

        testMap = Utilities.jsonToMap(null);
        assertTrue(testMap.isEmpty());
    }


    @Mock
    private Contact firstContact;

    @Mock
    private Contact secondContact;

    @Mock
    private Contact thirdContact;

    @Test
    public void sortContactListTest() throws Exception {

        when(firstContact.getName()).thenReturn("A");
        when(secondContact.getName()).thenReturn("bb");
        when(thirdContact.getName()).thenReturn("C");

        ArrayList<Contact> testContactList = new ArrayList<>();
        testContactList.add(thirdContact);
        testContactList.add(firstContact);
        testContactList.add(secondContact);

        //assert that we start unsorted
        assertTrue("Group started sorted", !(testContactList.get(0).getName().compareTo(firstContact.getName()) == 0));
        assertTrue("Group started sorted", !(testContactList.get(1).getName().compareTo(secondContact.getName()) == 0));
        assertTrue("Group started sorted", !(testContactList.get(2).getName().compareTo(thirdContact.getName()) == 0));

        testContactList = Utilities.sortContactList(testContactList);

        assertTrue("Group did not sort correctly", testContactList.get(0).getName().compareTo(firstContact.getName()) == 0);
        assertTrue("Group did not sort correctly", testContactList.get(1).getName().compareTo(secondContact.getName()) == 0);
        assertTrue("Group did not sort correctly", testContactList.get(2).getName().compareTo(thirdContact.getName()) == 0);
    }


    @Mock
    private ContactGroup firstGroup;

    @Mock
    private ContactGroup secondGroup;

    @Mock
    private ContactGroup thirdGroup;

    @Test
    public void sortGroupListTest() throws Exception {
        when(firstGroup.getName()).thenReturn("A");
        when(secondGroup.getName()).thenReturn("bb");
        when(thirdGroup.getName()).thenReturn("C");

        ArrayList<ContactGroup> testGroupList = new ArrayList<>();
        testGroupList.add(thirdGroup);
        testGroupList.add(firstGroup);
        testGroupList.add(secondGroup);

        //assert that we start unsorted
        assertTrue("Group started sorted", !(testGroupList.get(0).getName().compareTo(firstGroup.getName()) == 0));
        assertTrue("Group started sorted", !(testGroupList.get(1).getName().compareTo(secondGroup.getName()) == 0));
        assertTrue("Group started sorted", !(testGroupList.get(2).getName().compareTo(thirdGroup.getName()) == 0));

        testGroupList = Utilities.sortGroupList(testGroupList);

        assertTrue("Group did not sort correctly", testGroupList.get(0).getName().compareTo(firstGroup.getName()) == 0);
        assertTrue("Group did not sort correctly", testGroupList.get(1).getName().compareTo(secondGroup.getName()) == 0);
        assertTrue("Group did not sort correctly", testGroupList.get(2).getName().compareTo(thirdGroup.getName()) == 0);
    }
}