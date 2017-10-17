package cmsc355.contactapp;

import android.support.v4.util.ArrayMap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

class ConnectAdapter extends RecyclerView.Adapter {

    private ArrayMap<String, String> attributes;

    private class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox txtCheck;
        TextView txtValue;
        public View layout;

        ViewHolder(View v) {
            super(v);
            layout = v;
            txtCheck = v.findViewById(R.id.connect_check);
            txtValue = v.findViewById(R.id.connect_value);
        }
    }

    ConnectAdapter(ArrayMap<String, Object> attr) {
        attributes = new ArrayMap<>();
        for (String k : attr.keySet()) {
            if (attr.get(k).getClass() == String.class) {
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

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.item_connect, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder vHolder = (ViewHolder) holder;
        final String key = attributes.keyAt(position);
        final String value = attributes.valueAt(position);
        vHolder.txtCheck.setText(key);
        vHolder.txtValue.setText(value);
        vHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                        //TODO - Implement packaging desired attributes before sending
            }
        });
    }

    @Override
    public int getItemCount() {
        return attributes.size();
    }
}
