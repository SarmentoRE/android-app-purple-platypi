package com.cmsc355.contactapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedHashMap;

class GroupsAdapter extends RecyclerView.Adapter {

    // groupAndContactMap is the important one
    private ArrayList<ContactGroup> groupArrayList;
    private LinkedHashMap<String, Object> groupAndContactMap;

    // ViewHolder holds references to each view inside a generated item on the list, so we can access them later.
    // this one holds a ContactGroup item
    private class GroupViewHolder extends RecyclerView.ViewHolder {
        public View layout;
        TextView groupName;

        GroupViewHolder(View view) {
            super(view);
            layout = view;
            groupName = view.findViewById(R.id.group_name);
        }
    }

    // ViewHolder holds references to each view inside a generated item on the list, so we can access them later.
    // this one holds a Contact item
    private class ContactViewHolder extends RecyclerView.ViewHolder {
        public View layout;
        TextView contactName;

        ContactViewHolder(View view) {
            super(view);
            layout = view;
            contactName = view.findViewById(R.id.contact_name);
        }
    }

    // constructor: read the inputs, then build the map out of them
    GroupsAdapter(ArrayList<ContactGroup> groupsList) {
        groupArrayList = groupsList;
        groupAndContactMap = buildMap();
    }

    // takes all groups and contacts and builds a linked (ordered) map, where
    // the groups are in alphabetical order, and each group is followed by the contacts
    // contained in that group, also in alphabetical order
    private LinkedHashMap<String, Object> buildMap() {
        // numGroups tracks the number of groups, numContacts tracks the number of contacts per group
        int numGroups = groupArrayList.size();
        int[] numContacts = new int[numGroups];
        for (int i = 0; i < numGroups; i++) {
            numContacts[i] = groupArrayList.get(i).getContacts().size();
        }
        // numElements tracks the sum of all groups and all contacts, or how many elements will be
        // in the map at the end
        int numElements = numGroups;
        for (int numC : numContacts) {
            numElements += numC;
        }

        // gcMap is where we build our linked map
        LinkedHashMap<String, Object> gcMap = new LinkedHashMap<>();
        // list to hold all the contacts of a particular group
        ArrayList<Contact> groupContacts;
        // indexes, used when traversing our while loops
        int mainIndex = 0;
        int groupIndex = 0;
        // tracks whether a given element is a group or a contact
        boolean isGroup = true;

        // going to add to the map a total of numElements times
        while (mainIndex < numElements) {
            // if this element is a group:
            if (isGroup) {
                // get the next group in the list, then start adding that group's contacts after it
                gcMap.put("ContactGroup" + groupIndex, groupArrayList.get(groupIndex));
                mainIndex++;
                isGroup = false;
            } else {
                // if this element is not a group (must be element right after a group),
                // put every contact from that group into the map, in order
                groupContacts = groupArrayList.get(groupIndex).getContacts();
                int contactIndex = 0;
                while (contactIndex < groupContacts.size()) {
                    gcMap.put("ContactGroup" + groupIndex + "Contact" + contactIndex, groupContacts.get(contactIndex++));
                    mainIndex++;
                }
                groupIndex++;
                //after we get every contact from this group, the next element is going to be a group again
                isGroup = true;
            }
        }
        //note that the map holds references to all these objects, no new Contacts or ContactGroups are created
        return gcMap;
    }

    //this override method is used by the adapter to set the viewType in onCreateViewHolder
    @Override
    public int getItemViewType(int position) {
        //get the class of the item at this position
        String itemClass = groupAndContactMap.get(
                Utilities.getKeyAtPosition(groupAndContactMap, position)).getClass().toString();
        //check whether the item is a ContactGroup or a Contact
        if (itemClass.equals(ContactGroup.class.toString())) {
            return 0;
        } else {
            if (itemClass.equals(Contact.class.toString())) {
                return 1;
            } else {
                //if it's not a ContactGroup or a Contact, set viewType as 2 (aka do nothing with this item)
                return 2;
            }
        }
    }

    //this is called to make a new item on the list - it adds the views to the activity then returns
    //the viewholder to be able to reference these views later
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View view;
        if (viewType == 1) {
            view = inflater.inflate(R.layout.item_contact, parent, false);
            return new ContactViewHolder(view);
        } else {
            view = inflater.inflate(R.layout.item_group, parent, false);
            return new GroupViewHolder(view);
        }
    }

    //this is where we actually modify the contents of the views. In this case, we determine the item type,
    //then set the contents appropriately. note that we use two different ViewHolders, depending on the
    //item type
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        //0 = ContactGroup
        if (viewType == 0) {
            final GroupViewHolder vHolder = (GroupViewHolder) holder;
            //find the specific group from the map
            ContactGroup group = (ContactGroup) groupAndContactMap.get(Utilities.getKeyAtPosition(groupAndContactMap, position));
            final Context context = vHolder.layout.getContext();    //to be used in onClick method
            vHolder.groupName.setText(group.getName());
            vHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //TODO - on click, expand/collapse contacts below this element
                    Toast.makeText(context, "Group clicked", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            //1 = Contact
            if (viewType == 1) {
                final ContactViewHolder vHolder = (ContactViewHolder) holder;
                //find the specific contact from the map
                final Contact contact = (Contact) groupAndContactMap.get(Utilities.getKeyAtPosition(groupAndContactMap, position));
                vHolder.contactName.setText(contact.getName());
                //when you click the contact, takes you to the Contact Info screen
                vHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(view.getContext(), ContactInfoActivity.class);
                        intent.putExtra("Contact", contact.addContactToJson(new JSONObject()).toString());
                        intent.putExtra("isEditable", false);
                        view.getContext().startActivity(intent);
                    }
                });
            }
        }
    }

    //actually really important. this determines how many elements it's going to inflate, so
    //this must return the desired number of elements after the constructor is called
    @Override
    public int getItemCount() {
        return groupAndContactMap.size();
    }
}
