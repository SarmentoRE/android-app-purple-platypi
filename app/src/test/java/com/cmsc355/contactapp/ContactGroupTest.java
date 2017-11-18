package com.cmsc355.contactapp;

import org.json.JSONObject;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

@RunWith(PowerMockRunner.class)
@PrepareForTest(ContactGroup.class)
public class ContactGroupTest {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    private JSONObject attributesMock;

    @Test
    public void testDefaultConstructor() {
        ContactGroup testContactGroup = new ContactGroup();
        assertEquals(testContactGroup.getName(), "Default");
        assertEquals(testContactGroup.getGroupId(), -1);
        assertEquals(testContactGroup.getContacts(), new ArrayList<>());
    }

    @Test
    public void testGettersAndSetters() {
        ArrayList<Contact> testContactList = new ArrayList<Contact>();
        ContactGroup testContactGroup = new ContactGroup();
        testContactGroup.setName("Friends");
        testContactGroup.setGroupId(5);
        testContactGroup.setContacts(testContactList);
        assertEquals(testContactGroup.getName(), "Friends");
        assertEquals(testContactGroup.getGroupId(), 5);
        assertEquals(testContactGroup.getContacts(), testContactList);
    }

    @Test
    public void testParameterizedConstructor() {
        ArrayList<Contact> testContactList = new ArrayList<Contact>();
        ContactGroup testContactGroup = new ContactGroup("CMSC 355 Group", testContactList);
        assertEquals(testContactGroup.getName(), "CMSC 355 Group");
        assertEquals(testContactGroup.getGroupId(), -1);
        assertEquals(testContactGroup.getContacts(), testContactList);
    }


}
