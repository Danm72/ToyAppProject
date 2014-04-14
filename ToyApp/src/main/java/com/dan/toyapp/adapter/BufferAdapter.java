package com.dan.toyapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.dan.toyapp.R;
import com.dan.toyapp.entity.interfaces.DisplayFacade;

import java.util.LinkedList;

/**
 * Created by danmalone on 13/10/2013.
 */

public class BufferAdapter<E extends DisplayFacade> extends BaseAdapter {
    //extends BaseAdapter{//} implements Filterable {
    private static String TAG = "BufferAdapter";
    Context context;
    LinkedList<E> originalList = new LinkedList<>();
    int layout;

    public BufferAdapter(Context context, int layout) {//, int textViewResourceId) {
        this.context = context;
        this.layout = layout;

//        this.textViewResourceId = textViewResourceId;
    }


    public void addToFront(E object) {
        if (getCount() < 200 && !originalList.contains(object))
            originalList.addFirst(object);
        else if (!originalList.contains(object)) {
            originalList.removeLast();
            originalList.addFirst(object);
        }
        this.notifyDataSetChanged();

    }

    public void addToEnd(E object) {
        if (getCount() < 200 && !originalList.contains(object))
            originalList.addLast(object);
        else if (!originalList.contains(object)) {
            originalList.removeFirst();
            originalList.addLast(object);
        }
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return originalList.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return originalList.get(position);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;

        if (view == null) {

            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout
                    , null);

        }

        TextView name = (TextView) view.findViewById(R.id.listdetail_LargeText);
//        Log.i(TAG, "Position" + position + "City" + originalList.get(position).getCity());
        name.setText(originalList.get(position).getNameToDisplay());

        TextView name1 = (TextView) view.findViewById(R.id.listdetail_textView_id);
        name1.setText(originalList.get(position).getIdOfItem());
        TextView name2 = (TextView) view.findViewById(R.id.listdetail_textView_lat);
        name2.setText(originalList.get(position).getFirstItem());
        TextView name3 = (TextView) view.findViewById(R.id.listdetail_textView_long);
        name3.setText(originalList.get(position).getSecondItem());

        TextView name4 = (TextView) view.findViewById(R.id.listdetail_textView_comment);
        name4.setText(originalList.get(position).getSubtitle());
        TextView name5 = (TextView) view.findViewById(R.id.listdetail_textView_region);
        name5.setText(originalList.get(position).getExtra());

        return view;    //To change body of overridden methods use File | Settings | File Templates.
    }

    public void clear() {
        originalList.clear();
    }
//    @Override
//    public Filter getFilter() {
//        Filter filter = new Filter() {
//
//            @SuppressWarnings("unchecked")
//            @Override
//            protected void publishResults(CharSequence constraint, FilterResults results) {
//
//                backupList = originalList;
//
//                if (results.count > 0) {
//                    originalList.clear();
//                    originalList = (LinkedList<City>) results.values;
//                    notifyDataSetChanged();
//
//                }
////                originalList = backupList;
//            }
//
//            @Override
//            protected FilterResults performFiltering(CharSequence constraint) {
//                constraint = constraint.toString().toLowerCase();
//
//                FilterResults results = new FilterResults();
//
//
//                if (constraint != null && constraint.toString().length() > 0) {
//                    LinkedList<City> filteredArrayNames = new LinkedList<>();
//
//                    for (City city : originalList) {
//                        String dataNames = city.getCity();
//                        if (dataNames.toLowerCase().startsWith(constraint.toString())) {
//                            filteredArrayNames.add(city);
//                        }
//                    }
//
//
//                    results.count = filteredArrayNames.size();
//                    results.values = filteredArrayNames;
//                    Log.e("VALUES", results.values.toString());
//                } else {
//
//                    results.values = backupList;
//                    results.count = backupList.size();
//
//                    return results;
//                }
//
//                return results;
//            }
//        };
//
//        return filter;
//    }
//
//    public LinkedList<City> filtered() {
//        return backupList;
//    }
}
