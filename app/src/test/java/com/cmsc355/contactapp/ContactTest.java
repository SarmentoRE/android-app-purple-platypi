package com.cmsc355.contactapp;

import org.json.JSONObject;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;
import java.util.Iterator;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({Contact.class, ContactRepo.class})
public class ContactTest {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    private JSONObject blankJsonMock;

    @Mock
    private Iterator<String> keysItrMock;

    @Mock
    private JSONObject attributesMock;


    @Test
    public void testGettersAndSetters() throws Exception {
        Contact testContact = new Contact("Enter Name", attributesMock);
        when(attributesMock.get("Email")).thenReturn("Aaron@mockito.org");
        testContact.setName("Aaron");
        testContact.setId(5);
        assertEquals(testContact.getName(), "Aaron");
        assertEquals(testContact.getId(), 5);
        assertEquals(testContact.getAttributes().get("Email"), "Aaron@mockito.org");
    }

    //Designed to test a parameterized constructor of the Contact class and confirm functionality
    //of all getter methods in the Contact class
    @Test
    public void testParameterizedConstructorOne() throws Exception {
        Contact testContact = new Contact("Austin", attributesMock);
        when(attributesMock.get("Email")).thenReturn("Austin@mockito.org");
        when(attributesMock.get("Phone Number")).thenReturn(1234567890);
        assertEquals(testContact.getName(), "Austin");
        assertEquals(testContact.getAttributes().get("Email"), "Austin@mockito.org");
        assertEquals(testContact.getAttributes().get("Phone Number"), 1234567890);
    }

    @Test
    public void testParameterizedConstructorTwo() throws Exception {
        Contact testContact = new Contact("Shane", attributesMock, 10);
        when(attributesMock.get("Email")).thenReturn("Shane@mockito.org");
        when(attributesMock.get("Phone Number")).thenReturn(1234567890);
        assertEquals(testContact.getName(), "Shane");
        assertEquals(testContact.getId(), 10);
        assertEquals(testContact.getAttributes().get("Email"), "Shane@mockito.org");
        assertEquals(testContact.getAttributes().get("Phone Number"), 1234567890);
    }

    @Test
    public void testCopyContactConstructor() throws Exception {
        Contact contactToCopy = new Contact("Tal", attributesMock, 20);
        when(attributesMock.get("Email")).thenReturn("Tal@mockito.org");
        Contact copiedContact = new Contact(contactToCopy);
        assertEquals(contactToCopy.getName(), "Tal");
        assertEquals(contactToCopy.getId(), 20);
        assertEquals(contactToCopy.getAttributes().get("Email"), "Tal@mockito.org");
    }

    //Designed to test the ability of a contact object to be transferred between classes
    //then be turned into a JSON object for storage in the SQLite database
    @Test
    public void contactToJsonTest() throws Exception {
        when(attributesMock.get("Email")).thenReturn("mock@mockito.org");

        when(blankJsonMock.keys()).thenReturn(keysItrMock);
        when(blankJsonMock.get("Name")).thenReturn("Mock");
        when(blankJsonMock.get("Attributes")).thenReturn(attributesMock);

        when(keysItrMock.hasNext()).thenReturn(true, false);
        when(keysItrMock.next()).thenReturn("Garbage");

        Contact contactTest = new Contact("Mock", attributesMock);
        JSONObject jsonTest = contactTest.addContactToJson(blankJsonMock);

        assertEquals("JSON email does not match contact email",
                contactTest.getAttributes().get("Email"), ((JSONObject) jsonTest.get("Attributes")).get("Email"));
    }

    //Designed to test the functionality of generating a programmer defined number of
    //random contacts (primarily for testing/debugging purposes)
    @Test
    public void generateRandomContactsTest() throws Exception {
        // NOTE - PowerMockito having trouble returning different values from the
        // static, private inner method generateRandomContact - which is why I only test list size 1
        Contact contactMock = mock(Contact.class);
        PowerMockito.stub(PowerMockito.method(Contact.class, "generateRandomContact")).toReturn(contactMock);

        PowerMockito.mockStatic(ContactRepo.class);
        when(ContactRepo.insertToDatabase(Mockito.any(Contact.class))).thenReturn(1);

        ArrayList<Contact> contactListTest = Contact.generateRandomContacts(1);

        assertEquals("Incorrect number of contacts generated", 1, contactListTest.size());
    }
}
