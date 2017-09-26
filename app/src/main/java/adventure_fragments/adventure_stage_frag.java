package adventure_fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.soe.ishibori.stagesofexperience.R;
import com.soe.ishibori.stagesofexperience.adventure_act;

import java.util.ArrayList;

import models.Adventure;
import models.AdventureStage;
import models.Campaign;
import models.Character;
import models.CombatEngine;
import models.StageOption;
import utils.CharacterUtils;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link adventure_stage_frag} interface
 * to handle interaction events.
 * Use the {@link adventure_stage_frag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class adventure_stage_frag extends Fragment {
    Campaign currentCampaign;
    Adventure currentAdventure;
    AdventureStage currentStage;

    TextView txtMainTitle;
    TextView txtSubTitle;
    TextView txtMainText;
    LinearLayout layoutOptions;


    private OnFragmentInteractionListener mListener;

    public adventure_stage_frag() {
        // Required empty public constructor
    }

    public static adventure_stage_frag newInstance(String param1, String param2) {
        adventure_stage_frag fragment = new adventure_stage_frag();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            //get campaign
            currentCampaign = (Campaign) bundle.getSerializable(Campaign.CAMPAIGN_KEY);
            if(currentCampaign != null){
                currentAdventure = currentCampaign.getActiveOrFirstAdventure();
                if(currentAdventure != null){
                    currentStage = currentAdventure.getActiveOrFirstAdventureStage();
                }
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (container != null) {
            container.removeAllViews();
        }

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_adventure_stage_frag, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        txtMainTitle = (TextView) view.findViewById(R.id.txtCampaignTitle);
        txtSubTitle = (TextView) view.findViewById(R.id.txtAdventureTitle);
        txtMainText = (TextView) view.findViewById(R.id.txtMainText);
        layoutOptions = (LinearLayout) view.findViewById(R.id.optionsLayout);

        if(currentCampaign != null){
            txtMainTitle.setText(currentCampaign.Title);
            if(currentAdventure != null && currentStage != null){
                int stageNumber = currentAdventure.Stages.indexOf(currentStage) + 1;
                int numOfStages = currentAdventure.Stages.size();
                txtSubTitle.setText(currentAdventure.Title + " - " + currentStage.Title + " ("+stageNumber+"/"+numOfStages+")");
                txtMainText.setText(currentStage.MainText);

                if(currentStage.StageOptions != null) {
                    addOptions(currentStage.StageOptions);
                }
            }
        }
    }

    public void addOptions(ArrayList<StageOption> options){
        layoutOptions.setWeightSum(options.size());

        for (final StageOption sp : options) {

            Button btnOption = new Button(getActivity());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            //params.setMargins(10,10,10,10);
            params.weight = 1;
            btnOption.setLayoutParams(params);
            btnOption.setText(sp.OptionText);
            btnOption.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //select the right listener here!!
                    sp.WasSelected = true;

                    if(sp.Action == StageOption.StageActionType.Fight){
                        CombatEngine engine = new CombatEngine(currentStage.getMonsters(), currentCampaign.user_char);
                        engine.initEngine(0,0);
                        engine.startCombat();
                    }

                    currentAdventure.SelectedOptionsHist.add(sp);
                    currentAdventure.setNextAdventureStage(sp.NextStep, sp.MsgForNextStage, sp.BeforeMainNextStage);
                    adventure_act act = (adventure_act) getActivity();
                    act.setNewFragment();
                }
            });

            layoutOptions.addView(btnOption);
        }
    }

    public void onButtonPressed(Uri uri) {
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
