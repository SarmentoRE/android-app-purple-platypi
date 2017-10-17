package cmsc355.contactapp;

import java.util.ArrayList;
import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import static cmsc355.contactapp.Utilities.GenerateRandomString;

class Contact
{
    private String name;
    private String address;
    private String phoneNumber;
    private String email;

    /*Default constructor, I don't think we should have this in final code since we require at minimum a name to be provided for it
    to be considered a valid contact*/
    private Contact()
    {
        name = "N/A";
        address = "N/A";
        phoneNumber = "N/A";
        email = "N/A";
    }
    //Fully parameterized constructor
    public Contact(String nameVar, String addressVar, String phoneNumberVar, String emailVar)
    {
        name = nameVar;
        address = addressVar;
        phoneNumber = phoneNumberVar;
        email = emailVar;
    }
    //Partially parameterized constructor taking in single, required field of name
    public Contact(String nameVar)
    {
        name = nameVar;
        address = "N/A";
        phoneNumber = "N/A";
        email = "N/A";
    }

    //Setter methods for the Contact class
    public void SetName(String nameVar)
    {name = nameVar;}
    public void SetAddress(String addressVar)
    {address = addressVar;}
    public void SetPhoneNumber(String phoneNumberVar)
    {phoneNumber = phoneNumberVar;}
    public void SetEmail(String emailVar)
    {email = emailVar;}

    //Getter methods for the contact class
    public String GetName()
    {return this.name;}
    public String GetAddress()
    {return this.address;}
    public String GetPhoneNumber()
    {return this.phoneNumber;}
    public String GetEmail()
    {return this.email;}

    /*Usage for now is understandable, I think we should get rid of this later on and use standard setter methods to
    uphold encapsulation of Contact class data*/
    public void SetContactInfo(String n, String a, String p, String e)
    {
        name = n;
        address = a;
        phoneNumber = p;
        email = e;
    }

    static void GenerateRandomContacts(ArrayList<JSONObject> contactList, int numContacts)
    {
        for (int i = 0; i < numContacts; i++)
        {
            try
            {
                contactList.add(new JSONObject("{\"First name\":\" "+GenerateRandomString(8)+" \",\"Last name\":\" "+GenerateRandomString(8)+" \"}"));
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
        }//for loop
    }//GenerateRandomContact

    /*
    static void GenerateRandomContactsA(ArrayList<Contact> dummyContactList, int numOfContacts)
    {
        for (int i = 0; i < numOfContacts; i++)
        {
            Contact dummyContact = new Contact(GenerateRandomString(5));
            dummyContactList.add(dummyContact);
        }

    }
    */
}//contact class
