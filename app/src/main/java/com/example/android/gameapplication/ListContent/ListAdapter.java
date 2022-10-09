package com.example.android.gameapplication.ListContent;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.android.gameapplication.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListAdapter extends ArrayAdapter<ListTuple> {
    static class ViewHolderUserScore {
        @BindView(R.id.list_element0)
        TextView element0;
        @BindView(R.id.list_element1)
        TextView element1;

        ViewHolderUserScore(View view) {
            ButterKnife.bind(this, view);
        }
    }

    private int resourceId;

    public ListAdapter(Context context, int resource, List<ListTuple> objects) {
        super(context, resource, objects);
        resourceId = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ListTuple listTuple = getItem(position);
        View view;
        ViewHolderUserScore viewHolder;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, null);
            viewHolder = new ViewHolderUserScore(view);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolderUserScore) view.getTag();
        }

        viewHolder.element0.setText(listTuple.getElement0());
        viewHolder.element1.setText(listTuple.getElement1());

        return view;
    }
}