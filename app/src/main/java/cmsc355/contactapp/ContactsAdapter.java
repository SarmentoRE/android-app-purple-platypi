package cmsc355.contactapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

class ContactsAdapter extends RecyclerView.Adapter {

    private ArrayList<Contact> contactArrayList;

    ContactsAdapter(ArrayList<Contact> cList) {
        contactArrayList = cList;
    }

    public void add(int position, Contact item) {
        contactArrayList.add(position, item);
        notifyItemInserted(position);
    }

    public void remove(int position) {
        contactArrayList.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.item_contact, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final int pos = position;
        final Contact contact = contactArrayList.get(position);
        ViewHolder vHolder = (ViewHolder) holder;
        String name = contact.getName();
        vHolder.txtName.setText(name);
        vHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO - Should goto Edit Contact screen instead of toasting
//                Toast.makeText(v.getContext(), pos + " is clicked", Toast.LENGTH_SHORT).show();

                Intent i = new Intent(v.getContext(), ContactInfoActivity.class);
                i.putExtra("Contact Name", contact.getName());
                i.putExtra("Contact Attributes", contact.getAttributes().toString());
                v.getContext().startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() { return contactArrayList.size(); }

    private class ViewHolder extends RecyclerView.ViewHolder {
        public View layout;
        TextView txtName;

        ViewHolder(View v) {
            super(v);
            layout = v;
            txtName = v.findViewById(R.id.contact_name);
        }
    }
}
