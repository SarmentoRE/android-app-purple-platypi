package cmsc355.contactapp;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Toast;

import java.util.List;

class ConnectAdapter extends RecyclerView.Adapter {

    private List<String> attributeNames;

    private class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox txtCheck;
        public View layout;

        ViewHolder(View v) {
            super(v);
            layout = v;
            txtCheck = v.findViewById(R.id.connect_check);
        }
    }

    ConnectAdapter(List<String> attrNames) {
        attributeNames = attrNames;
    }

    public void add(int position, String item) {
        attributeNames.add(position, item);
        notifyItemInserted(position);
    }

    public void remove(int position) {
        attributeNames.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.item_connect, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final int pos = position;
        ViewHolder vHolder = (ViewHolder) holder;
        final String name = attributeNames.get(position);
        vHolder.txtCheck.setText(name);
        vHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO - Should check/uncheck attributes instead of toasting
                if(((CheckBox)v).isChecked()) {
                    Toast.makeText(v.getContext(), pos + " is checked", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(v.getContext(), pos + " is unchecked", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return attributeNames.size();
    }
}
