package adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.soe.ishibori.stagesofexperience.R;
import com.soe.ishibori.stagesofexperience.adventure_act;

import java.util.ArrayList;

import models.Campaign;

public class CampaignAdapter extends RecyclerView.Adapter<CampaignAdapter.CustomViewHolder> {
    private ArrayList<Campaign> campaigns;
    private Context mContext;
    private OnItemClickListener onItemClickListener;

    public CampaignAdapter(Context context, ArrayList<Campaign> campaings) {
        this.campaigns = campaings;
        this.mContext = context;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.campaign_item, null);
        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder customViewHolder, int i) {
        final Campaign currentCampaign = campaigns.get(i);

        customViewHolder.txtTitle.setText(currentCampaign.Title);
        customViewHolder.txtTargetLevel.setText("("+currentCampaign.getTargetLevelString()+")");

        //attach listeners if needed
        if(currentCampaign.IsAccesible) {
            View.OnClickListener listener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    currentCampaign.IsActive = true;
                    ((adventure_act)mContext).setNewFragment();
                }
            };

            customViewHolder.txtTitle.setOnClickListener(listener);
            customViewHolder.txtTargetLevel.setOnClickListener(listener);
        }

    }

    @Override
    public int getItemCount() {
        return (null != campaigns ? campaigns.size() : 0);
    }

    public OnItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {
        protected TextView txtTitle;
        protected TextView txtTargetLevel;

        public CustomViewHolder(View view) {
            super(view);
            this.txtTitle = (TextView) view.findViewById(R.id.txtTitle);
            this.txtTargetLevel = (TextView) view.findViewById(R.id.txtTargetLevel);
        }
    }
}
