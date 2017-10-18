package cmsc355.contactapp;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.LinkedHashMap;

class GroupsAdapter extends RecyclerView.Adapter {

    private ArrayList<ContactGroup> groupArrayList;
    private ArrayList<ArrayList<Contact>> contactLists;
    private LinkedHashMap<String, Object> groupAndContactMap;

    GroupsAdapter(ArrayList<ContactGroup> gList, ArrayList<ArrayList<Contact>> cLists) {
        groupArrayList = gList;
        contactLists = cLists;
        groupAndContactMap = BuildMap();
    }

    private LinkedHashMap<String, Object> BuildMap() {
        int numGroups = groupArrayList.size();
        int[] numContacts = new int[numGroups];
        for (int i = 0; i < numGroups; i++) {
            numContacts[i] = groupArrayList.get(i).getContacts().size();
        }
        int numElements = numGroups;
        for(int numC : numContacts) {
            numElements += numC;
        }

        LinkedHashMap<String, Object> gcMap = new LinkedHashMap<>();
        ArrayList<Contact> groupContacts;
        int idx = 0, gIdx = 0;
        boolean isGroup = true;
        while (idx < numElements) {
            if (isGroup) {
                gcMap.put("Group"+gIdx, groupArrayList.get(gIdx));
                idx++;
                isGroup = false;
            }
            else {
                groupContacts = contactLists.get(gIdx);
                int cIdx = 0;
                while (cIdx < groupContacts.size()) {
                    gcMap.put("Group"+gIdx+"Contact"+cIdx,groupContacts.get(cIdx++));
                    idx++;
                }
                gIdx++;
                isGroup = true;
            }
        }
        return gcMap;
    }

    @Override
    public int getItemViewType(int position) {
        String itemClass = groupAndContactMap.get(Utilities.GetKeyAtPosition(groupAndContactMap,position)).getClass().toString();
        if (itemClass.equals(ContactGroup.class.toString())) {
            return 0;
        }
        else {
            if (itemClass.equals(Contact.class.toString())) {
                return 1;
            }
            else {
                return 2;
            }
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View v;
        if (viewType == 1) {
            v = inflater.inflate(R.layout.item_contact, parent, false);
            return new ContactViewHolder(v);
        }
        else {
            v = inflater.inflate(R.layout.item_group, parent, false);
            return new GroupViewHolder(v);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        if (viewType == 0) {
            final GroupViewHolder vHolder = (GroupViewHolder) holder;
            ContactGroup group = (ContactGroup) groupAndContactMap.get(Utilities.GetKeyAtPosition(groupAndContactMap,position));
            final Context context = vHolder.layout.getContext();
            vHolder.groupName.setText(group.getName());
            vHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
        else {
            if (viewType == 1) {
                final ContactViewHolder vHolder = (ContactViewHolder) holder;
                Contact contact = (Contact) groupAndContactMap.get(Utilities.GetKeyAtPosition(groupAndContactMap, position));
                final Context context = vHolder.layout.getContext();
                vHolder.contactName.setText(contact.getName());
                vHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
            }
        }
    }

    @Override
    public int getItemCount() { return groupAndContactMap.size(); }

    private class GroupViewHolder extends RecyclerView.ViewHolder {
        public View layout;
        TextView groupName;

        GroupViewHolder(View v) {
            super(v);
            layout = v;
            groupName = v.findViewById(R.id.group_name);
        }
    }

    private  class ContactViewHolder extends  RecyclerView.ViewHolder {
        public View layout;
        TextView contactName;

        ContactViewHolder(View v) {
            super(v);
            layout = v;
            contactName = v.findViewById(R.id.contact_name);
        }
    }
}
