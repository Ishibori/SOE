package adventure_fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.soe.ishibori.stagesofexperience.R;

import adapters.CampaignAdapter;
import utils.AdventureUtils;

public class campaign_selection_frag extends Fragment {
    private OnFragmentInteractionListener mListener;
    private CampaignAdapter campaignAdapter;
    private RecyclerView recyclerViewCampaignList;

    public campaign_selection_frag() {
        // Required empty public constructor
    }

    public static campaign_selection_frag newInstance(String param1, String param2) {
        campaign_selection_frag fragment = new campaign_selection_frag();
        //Bundle args = new Bundle();
        //args.putString(ARG_PARAM1, param1);
        //args.putString(ARG_PARAM2, param2);
        //fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
           // mParam1 = getArguments().getString(ARG_PARAM1);
           // mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (container != null) {
            container.removeAllViews();
        }

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_campaign_selection_frag, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        campaignAdapter = new CampaignAdapter(getContext(), AdventureUtils.getCampaigns());
        recyclerViewCampaignList = (RecyclerView) view.findViewById(R.id.campaign_list);
        recyclerViewCampaignList.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewCampaignList.setAdapter(campaignAdapter);
    }

    public void onButtonPressed() {
        if (mListener != null) {
            mListener.onFragmentInteraction();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
}
