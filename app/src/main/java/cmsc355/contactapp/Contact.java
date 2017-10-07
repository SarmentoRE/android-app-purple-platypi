package cmsc355.contactapp;

public class Contact {
    public String name;
    public String address;
    public String phoneNumber;
    public String email;

    public Contact() {
        name = "N/A";
        address = "N/A";
        phoneNumber = "N/A";
        email = "N/A";
    }

    public Contact(String n, String a, String p, String e) {
        name = n;
        address = a;
        phoneNumber = p;
        email = e;
    }
}
