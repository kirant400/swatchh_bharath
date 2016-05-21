package com.nirays.social.swachhbharat.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.nirays.social.swachhbharat.R;
import com.nirays.social.swachhbharat.model.ChatModel;

import java.util.ArrayList;

/**
 * Created by kirant400 on 02/03/2015.
 */
public class ChatAdapter extends ArrayAdapter<ChatModel> {
    private final Context context;
    private final ArrayList<ChatModel> modelsArrayList;
    public ChatAdapter(Context context, ArrayList<ChatModel> modelsArrayList) {

        super(context, R.layout.chat_item, modelsArrayList);

        this.context = context;
        this.modelsArrayList = modelsArrayList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // 1. Create inflater
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // 2. Get rowView from inflater

        View rowView = null;
        ChatModel model = modelsArrayList.get(position);
        Resources res = context.getResources();
        rowView = inflater.inflate(R.layout.chat_item, parent, false);
        // 5. retrn rowView
        return rowView;
    }

}
