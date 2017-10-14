package cmsc355.contactapp;

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

    private ArrayList<JSONObject> contactArrayList;

    private class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtName;
        public View layout;

        ViewHolder(View v) {
            super(v);
            layout = v;
            txtName = v.findViewById(R.id.contact_name);
        }
    }

    ContactsAdapter(ArrayList<JSONObject> cList) {
        contactArrayList = cList;
    }

    public void add(int position, JSONObject item) {
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
        ViewHolder vHolder = (ViewHolder) holder;
        String name = "";
        try {
            name = contactArrayList.get(position).getString("First name");
            name = name.concat(" "+contactArrayList.get(position).getString("Last name"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        vHolder.txtName.setText(name);
        vHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO - Should goto Edit Contact screen instead of toasting
                Toast.makeText(v.getContext(), pos + " is clicked", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() { return contactArrayList.size(); }
}
