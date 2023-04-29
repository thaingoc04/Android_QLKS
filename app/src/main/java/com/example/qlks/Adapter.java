package com.example.qlks;

import android.app.Activity;
import android.content.Context;
import android.content.pm.LabeledIntent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;

public class Adapter extends BaseAdapter implements Filterable {
    Activity activity;
    ArrayList<MinhAnh_0909> data;
    ArrayList<MinhAnh_0909> oldData;
    LayoutInflater inflater;

    public Adapter(Activity activity, ArrayList<MinhAnh_0909> data){
        this.activity = activity;
        this.data = data;
        this.oldData = data;
        this.inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return data.get(i).getId();
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        View v = convertView;
        if(v == null){
            v = inflater.inflate(R.layout.item, null);
            TextView txtName = v.findViewById(R.id.txtName);
            txtName.setText(data.get(i).getName());
            TextView txtRoomId = v.findViewById(R.id.txtRoom);
            txtRoomId.setText("Phòng: " +data.get(i).getRoomId());
            TextView txtMoney = v.findViewById(R.id.txtMoney);
            txtMoney.setText(""+ data.get(i).tongTien());
        }
        return v;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String search = constraint.toString();
                if (search.isEmpty()){
                    data = oldData;
                }
                else {
                    int intSearch = Integer.parseInt(search);
                    ArrayList<MinhAnh_0909> list = new ArrayList<>();
                    for(MinhAnh_0909 x: oldData){
                        if(x.tongTien() > intSearch){
                            list.add(x);
                        }
                    }
                    data = list;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = data;
                filterResults.count = data.size();
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                data = (ArrayList<MinhAnh_0909>) results.values;
                notifyDataSetChanged();
            }
        };

    }

}
