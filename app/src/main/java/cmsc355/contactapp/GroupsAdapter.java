package cmsc355.contactapp;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.ArrayList;

class GroupsAdapter extends RecyclerView.Adapter {

    private ArrayList<ContactGroup> groupArrayList;

    private class ViewHolder extends RecyclerView.ViewHolder {
        TextView groupName;
        public View layout;

        ViewHolder(View v) {
            super(v);
            layout = v;
            groupName = v.findViewById(R.id.group_name);
        }
    }

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
        ArrayList<JSONObject> contactList = new ArrayList<>();
        //Contact.GenerateRandomContacts(contactList, 3);   //TODO - replace this line with pulling contacts from database

        recyclerView.setAdapter(new ContactsAdapter(contactList));


        return new GroupsAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final int pos = position;
        final GroupsAdapter.ViewHolder vHolder = (GroupsAdapter.ViewHolder) holder;
        final String name = groupArrayList.get(position).name;
        final Context context = vHolder.layout.getContext();
        vHolder.groupName.setText(name);
        vHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(JSONObject c : groupArrayList.get(pos).contacts) {

                }
                //TODO - Should inflate into group contacts display instead of toasting
//                Toast.makeText(v.getContext(), pos + " is clicked", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() { return groupArrayList.size(); }
}
