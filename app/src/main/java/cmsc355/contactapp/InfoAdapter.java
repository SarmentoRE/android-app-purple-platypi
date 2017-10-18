package cmsc355.contactapp;

import android.support.v4.util.ArrayMap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

class InfoAdapter extends RecyclerView.Adapter {

    private Contact contact;
    private ArrayMap<String, Object> attributes;

    private class ViewHolder extends RecyclerView.ViewHolder {
        public View layout;
        TextView txtKey;
        TextView txtValue;

        ViewHolder(View v) {
            super(v);
            layout = v;
            txtKey = v.findViewById(R.id.info_key);
            txtValue = v.findViewById(R.id.info_value);
        }
    }

    InfoAdapter(Contact c) {
        contact = c;
        attributes = Utilities.JSONToMap(contact.getAttributes());
    }

    public void add(String key, String value) {
        attributes.put(key,value);
        notifyItemInserted(attributes.indexOfKey(key));
    }

    public void remove(int position) {
        attributes.removeAt(position);
        notifyItemRemoved(position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.item_attribute, parent, false);
        return new InfoAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        InfoAdapter.ViewHolder vHolder = (InfoAdapter.ViewHolder) holder;
        String key = attributes.keyAt(position);
        String value = (String) attributes.get(key);        //TODO - need to make this generic for non-string value types
        key = key.concat(":");
        vHolder.txtKey.setText(key);
        vHolder.txtValue.setText(value);
        vHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                                                            //TODO - Implement onClick for attributes to modify values?
            }
        });
    }

    @Override
    public int getItemCount() { return attributes.size(); }
}
