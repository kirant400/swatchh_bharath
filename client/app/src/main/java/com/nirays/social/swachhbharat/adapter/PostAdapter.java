package com.nirays.social.swachhbharat.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;

import com.nirays.social.swachhbharat.R;
import com.nirays.social.swachhbharat.model.PostModel;

import java.util.ArrayList;

/**
 * Created by kirant400 on 02/03/2015.
 */
public class PostAdapter extends ArrayAdapter<PostModel> {
    private final Context context;
    private final ArrayList<PostModel> modelsArrayList;
    private OnPostInteractionListener mListener;
    public PostAdapter(Context context, ArrayList<PostModel> modelsArrayList) {

        super(context, R.layout.post_item, modelsArrayList);

        this.context = context;
        this.modelsArrayList = modelsArrayList;
    }
    public void setEventListener(OnPostInteractionListener mEventListener) {
        this.mListener = mEventListener;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // 1. Create inflater
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // 2. Get rowView from inflater

        View rowView = null;
        final PostModel model = modelsArrayList.get(position);
        Resources res = context.getResources();
        rowView = inflater.inflate(R.layout.post_item, parent, false);
        LinearLayout linear = (LinearLayout)rowView.findViewById(R.id.btnPostItem);
        linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onPostClick(model);
            }
        });
        // 5. retrn rowView
        return rowView;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnPostInteractionListener {
        public void onPostClick(PostModel model);
    }
}
