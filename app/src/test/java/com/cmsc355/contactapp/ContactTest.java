package com.cmsc355.contactapp;

import org.json.JSONObject;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;
import java.util.Iterator;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest(Contact.class)
public class ContactTest {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    private JSONObject blankJSONMock;

    @Mock
    private Iterator<String> keysItrMock;

    @Mock
    private JSONObject attributesMock;

    @Test
    public void contactToJSONTest() throws Exception {
        when(attributesMock.get("Email")).thenReturn("mock@mockito.org");

        when(blankJSONMock.keys()).thenReturn(keysItrMock);
        when(blankJSONMock.get("Name")).thenReturn("Mock");
        when(blankJSONMock.get("Attributes")).thenReturn(attributesMock);

        when(keysItrMock.hasNext()).thenReturn(true, false);
        when(keysItrMock.next()).thenReturn("Garbage");

        Contact contactTest = new Contact("Mock",attributesMock);
        JSONObject jsonTest = contactTest.addContactToJson(blankJSONMock);

        assertEquals("JSON email does not match contact email",
                contactTest.getAttributes().get("Email"), ((JSONObject)jsonTest.get("Attributes")).get("Email"));
    }

    /*@Test
    public void generateRandomContactsTest() throws Exception {
        // NOTE - PowerMockito having trouble returning different values from the
        // static, private inner method generateRandomContact - which is why I only test list size 1
        Contact contactMock = mock(Contact.class);

        PowerMockito.stub(PowerMockito.method(Contact.class,"generateRandomContact")).toReturn(contactMock);

        ArrayList<Contact> contactListTest = Contact.generateRandomContacts(1);

        assertEquals("Incorrect number of contacts generated",1,contactListTest.size());
    }*/
}
