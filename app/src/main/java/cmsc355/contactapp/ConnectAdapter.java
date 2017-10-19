package cmsc355.contactapp;

import android.support.v4.util.ArrayMap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

class ConnectAdapter extends RecyclerView.Adapter {

    //holds one key/val pair for each element we want to generate
    private ArrayMap<String, String> attributes;

    //ViewHolder holds references to each view inside a generated item on the list, so we can access them later
    private class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox txtCheck;
        TextView txtValue;
        public View layout;

        //constructor method
        ViewHolder(View v) {
            super(v);
            layout = v;
            txtCheck = v.findViewById(R.id.connect_check);
            txtValue = v.findViewById(R.id.connect_value);
        }
    }

    //constructor method
    ConnectAdapter(ArrayMap<String, Object> attr) {
        attributes = new ArrayMap<>();
        for (String k : attr.keySet()) {
            if (attr.get(k).getClass() == String.class) {
                //only grabs key.val pairs when the value's type is String - protecting later code
                attributes.put(k, (String) attr.get(k));
            }
        }
    }

    public void add(String key, String value) {
        attributes.put(key, value);
        notifyItemInserted(attributes.indexOfKey(key));
    }

    public void remove(String key) {
        int index = attributes.indexOfKey(key);
        attributes.remove(key);
        notifyItemRemoved(index);
    }

    //this is called to make a new item on the list - it adds the views to the activity then returns
    //the viewholder to be able to reference these views later
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.item_connect, parent, false);
        return new ViewHolder(v);
    }

    //this is where we actually modify the contents of the views. In this case, we take a value from
    //our attributes map and simply write the key and value strings to the checkbox & textview.
    //The checkbox already handles the animation & storing the boolean for whether it is checked or unchecked.
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder vHolder = (ViewHolder) holder;
        final String key = attributes.keyAt(position);
        final String value = attributes.valueAt(position);
        vHolder.txtCheck.setText(key);
        vHolder.txtValue.setText(value);
    }

    //actually really important. this determines how many elements it's going to inflate, so
    //this must return the desired number of elements after the constructor is called
    @Override
    public int getItemCount() {
        return attributes.size();
    }
}
