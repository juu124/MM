package com.example.mm3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ListViewAdapter extends BaseAdapter
{
    private ArrayList<ListViewItem> listViewItemList = new ArrayList<>();

    public ListViewAdapter(){}

    @Override
    public int getCount()
    {
        return listViewItemList.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        final int pos = position;
        final Context context = parent.getContext();
        Button cancel_bt;

        if(convertView == null)
        {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listview_item,parent,false);
        }

        TextView hourTextView = (TextView) convertView.findViewById(R.id.hour_text);
        TextView minTextView =(TextView) convertView.findViewById(R.id.min_text);
        TextView ampmTextView = (TextView) convertView.findViewById(R.id.ampm_text);

        ListViewItem listViewItem = listViewItemList.get(position);
/*      final ArrayAdapter adapter = new ArrayList<ListViewItem>(, android.R.layout.simple_list_item_single_choice, listViewItem);*/

        hourTextView.setText(listViewItem.getHour());
        minTextView.setText(listViewItem.getMin());
        ampmTextView.setText(listViewItem.getAmpm());

        cancel_bt = convertView.findViewById(R.id.cancel_bt);
        cancel_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(context, "삭제", Toast.LENGTH_SHORT).show();
            }
        });

        return convertView;
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public Object getItem(int position)
    {
        return listViewItemList.get(position);
    }

    public void addItem(String hour, String min,String ampm)
    {
        ListViewItem item = new ListViewItem();
        item.set_time(hour);
        item.set_min(min);
        item.set_ampm(ampm);
        listViewItemList.add(item);
    }
}