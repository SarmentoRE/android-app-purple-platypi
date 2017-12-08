package com.cmsc355.contactapp;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

@RunWith(PowerMockRunner.class)
@PrepareForTest({Contact.class, ContactsAdapter.class})
public class ContactsAdapterTest {
    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Test
    public void testGetItemCountMethod() throws Exception {
        Contact mockContact1 = mock(Contact.class);
        Contact mockContact2 = mock(Contact.class);
        ArrayList<Contact> contactArrayList = new ArrayList<>();
        contactArrayList.add(mockContact1);
        contactArrayList.add(mockContact2);
        int size = contactArrayList.size();
        ContactsAdapter dummyContactsAdapter = new ContactsAdapter(contactArrayList);
        assertEquals(dummyContactsAdapter.getItemCount(), size);
    }

    @Test
    public void testConstructorMethod() throws Exception {
        Contact mockContact = mock(Contact.class);
        ArrayList<Contact> contactArrayList = new ArrayList<>();
        contactArrayList.add(mockContact);
        ContactsAdapter dummyContactsAdapter = new ContactsAdapter(contactArrayList);
        assertEquals(dummyContactsAdapter.getItemCount(), 1);
    }
}
