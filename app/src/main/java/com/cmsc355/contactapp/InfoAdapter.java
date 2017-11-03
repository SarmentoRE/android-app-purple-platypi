package com.cmsc355.contactapp;

import android.support.v4.util.ArrayMap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

class InfoAdapter extends RecyclerView.Adapter {

    //isEditEnabled decides whether the user can edit the attributes, or if they are displayed only
    private ArrayMap<String, Object> attributes;
    private boolean isEditEnabled;

    //ViewHolder holds references to each view inside a generated item on the list, so we can access them later
    class ViewHolder extends RecyclerView.ViewHolder {
        public View layout;
        TextView txtKey;
        EditText txtValue;

        ViewHolder(View v) {
            super(v);
            layout = v;
            txtKey = v.findViewById(R.id.info_key);
            txtValue = v.findViewById(R.id.info_value);
        }
    }

    //constructor method
    InfoAdapter(Contact c, boolean iEE) {
        attributes = Utilities.JSONToMap(c.getAttributes());
        isEditEnabled = iEE;
    }

    public void add(String key, String value) {
        attributes.put(key, value);
        notifyItemInserted(attributes.indexOfKey(key));
    }

    public void remove(int position) {
        attributes.removeAt(position);
        notifyItemRemoved(position);
    }

    //this is called to make a new item on the list - it adds the views to the activity then returns
    //the viewholder to be able to reference these views later
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.item_attribute, parent, false);
        return new InfoAdapter.ViewHolder(v);
    }

    //this is where we actually modify the contents of the views. In this case, we determine the item type,
    //then set the contents appropriately. note that we use two different ViewHolders, depending on the
    //item type
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        InfoAdapter.ViewHolder vHolder = (InfoAdapter.ViewHolder) holder;
        String key = attributes.keyAt(position);
        String value = (String) attributes.get(key);        //TODO - need to make this generic for non-string value types
        key = key.concat(":");
        vHolder.txtKey.setText(key);
        vHolder.txtValue.setHint(value);
        if (!isEditEnabled) {
            vHolder.txtValue.setEnabled(false);
            vHolder.txtValue.setClickable(false);
            vHolder.txtValue.setKeyListener(null);
        }
    }

    //actually really important. this determines how many elements it's going to inflate, so
    //this must return the desired number of elements after the constructor is called
    @Override
    public int getItemCount() {
        return attributes.size();
    }
}
