package com.nirays.social.swachhbharat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.nirays.social.swachhbharat.adapter.PostAdapter;
import com.nirays.social.swachhbharat.model.Constant;
import com.nirays.social.swachhbharat.model.PostModel;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link NearByFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NearByFragment extends Fragment implements PostAdapter.OnPostInteractionListener{
    private PostAdapter mListAdapter;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    //private static final String ARG_PARAM1 = "param1";
    //private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;


    public NearByFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment NearByFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NearByFragment newInstance() {
        NearByFragment fragment = new NearByFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }


    }

    private ArrayList<PostModel> generateData() {
        ArrayList<PostModel> models = new ArrayList<PostModel>();
//        if(dataSource == null){
//            dataSource = new ConfigDataSource(getActivity());
//            dataSource.open();
//        }

        //List<PhoneModel> configs = dataSource.getPhones(groupId);
        //for (PhoneModel phoneModel : configs) {
            models.add(new PostModel());
        models.add(new PostModel());
        models.add(new PostModel());
        models.add(new PostModel());
        models.add(new PostModel());
        models.add(new PostModel());
        models.add(new PostModel());
        models.add(new PostModel());
        models.add(new PostModel());
        models.add(new PostModel());
        models.add(new PostModel());
        models.add(new PostModel());
        models.add(new PostModel());
        //}
        return models;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        mListAdapter = new PostAdapter(getActivity(),generateData());
        mListAdapter.setEventListener(this);
        ListView mListView = (ListView) rootView.findViewById(R.id.post_list);
        mListView.setAdapter(mListAdapter);
        return rootView;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onPostClick(PostModel model) {
        Intent details = new Intent(getActivity(),DetailsActivity.class);
        Bundle b = new Bundle();
        b.putLong(Constant.ID, model.getId()); //Your id
        details.putExtras(b); //Put your id to your next Intent
        getActivity().startActivity(details);
    }
}
