package cmsc355.contactapp;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

class GroupsAdapter extends RecyclerView.Adapter {

    private ArrayList<ContactGroup> groupArrayList;

    GroupsAdapter(ArrayList<ContactGroup> gList) {
        groupArrayList = gList;
    }

    public void add(int position, ContactGroup item) {
        groupArrayList.add(position, item);
        notifyItemInserted(position);
    }

    public void remove(int position) {
        groupArrayList.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.item_group, parent, false);


        RecyclerView recyclerView = v.findViewById(R.id.group_contact_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(v.getContext()));
        ArrayList<Contact> contactList = new ArrayList<>();
        //Contact.GenerateRandomContacts(contactList, 3);   //TODO - replace this line with pulling contacts from database

        recyclerView.setAdapter(new ContactsAdapter(contactList));


        return new GroupsAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final int pos = position;
        final GroupsAdapter.ViewHolder vHolder = (GroupsAdapter.ViewHolder) holder;
        final String name = groupArrayList.get(position).getName();
        final Context context = vHolder.layout.getContext();
        vHolder.groupName.setText(name);
        vHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (Contact c : groupArrayList.get(pos).getContacts()) {
                    //TODO - Display contacts inside the group
                }
            }
        });
    }

    @Override
    public int getItemCount() { return groupArrayList.size(); }

    private class ViewHolder extends RecyclerView.ViewHolder {
        public View layout;
        TextView groupName;

        ViewHolder(View v) {
            super(v);
            layout = v;
            groupName = v.findViewById(R.id.group_name);
        }
    }
}
