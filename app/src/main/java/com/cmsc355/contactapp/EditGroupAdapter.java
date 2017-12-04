package com.cmsc355.contactapp;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import java.util.ArrayList;

public class EditGroupAdapter extends RecyclerView.Adapter {

    //holds every contact
    private ArrayList<Contact> contactArrayList;

    //ViewHolder holds references to each view inside a generated item on the list, so we can access them later
    class ViewHolder extends RecyclerView.ViewHolder {
        public View layout;
        CheckBox checkBox;

        ViewHolder(View view) {
            super(view);
            layout = view;
            checkBox = view.findViewById(R.id.edit_group_check);
        }
    }

    //constructor method
    EditGroupAdapter(ArrayList<Contact> contactList) {
        Log.d("EditGroup", "Receiving " + contactList.size() + " contacts");
        this.contactArrayList = contactList;
    }

    public void add(int position, Contact item) {
        contactArrayList.add(position, item);
        notifyItemInserted(position);
    }

    public void remove(int position) {
        contactArrayList.remove(position);
        notifyItemRemoved(position);
    }

    //this is called to make a new item on the list - it adds the views to the activity then returns
    //the viewholder to be able to reference these views later
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_edit_group, parent, false);
        return new ViewHolder(view);
    }

    //this is where we actually modify the contents of the views. In this case, we just set the contact's
    //name.
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Log.d("EditGroup","Binding view " + position + " from array size " + contactArrayList.size());
        Contact contact = contactArrayList.get(position);
        ViewHolder viewHolder = (ViewHolder) holder;
        String name = contact.getName();
        viewHolder.checkBox.setText(name);
    }

    //actually really important. this determines how many elements it's going to inflate, so
    //this must return the desired number of elements after the constructor is called
    @Override
    public int getItemCount() {
        return contactArrayList.size();
    }
}
