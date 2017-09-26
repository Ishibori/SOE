package adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.soe.ishibori.stagesofexperience.R;

import dialogs.ItemInformationDialog;
import models.Character;
import models.Item;
import utils.CharacterUtils;
import utils.ItemUtils;

public class InventoryAdapter extends RecyclerView.Adapter<InventoryAdapter.CustomViewHolder> {
    private Character user_character;
    private Context mContext;
    private OnItemClickListener onItemClickListener;

    public InventoryAdapter(Context context, Character user_character) {
        this.user_character = user_character;
        this.mContext = context;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.inventory_item, null);
        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder customViewHolder, int i) {
        final Item currentItem = user_character.Items.get(i);

        customViewHolder.imageView.setImageResource(currentItem.ImageId);
        customViewHolder.txtTitle.setText(currentItem.Title);
        customViewHolder.txtTitle.setTextColor(ItemUtils.getItemTextColor(currentItem));
        customViewHolder.chkEquipped.setChecked(currentItem.isEquipped);

        //attach listeners
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tryEquipItem(v, currentItem);
            }
        };

        View.OnClickListener examineListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ItemInformationDialog dialog = new ItemInformationDialog((Activity) mContext, ItemUtils.getItemTextColor(currentItem), currentItem);
                dialog.show();
            }
        };

        //customViewHolder.imageView.setOnClickListener(listener);
        customViewHolder.txtTitle.setOnClickListener(examineListener);
        //customViewHolder.txtEffects.setOnClickListener(listener);
        customViewHolder.chkEquipped.setOnClickListener(listener);
    }

    @Override
    public int getItemCount() {
        return (null != user_character.Items ? user_character.Items.size() : 0);
    }

    private void tryEquipItem(View v, Item currentItem)
    {
        boolean updateSheet = false;
        if(currentItem.isEquipped)
        {
            currentItem.isEquipped = false;
            updateSheet = true;
        }
        else{
            //error codes: -2(Amount Restricted), -1(Class Restricted), 0(succes)
            int res = CharacterUtils.canEquipItem(user_character, currentItem);

            if(res == 0){
                currentItem.isEquipped = true;
                updateSheet = true;
            }
            else
            {
                CheckBox currentBox = (CheckBox)v;
                currentBox.setChecked(false);
                String msg = "Class Restricted Item!"; // res==-1

                if(res == -2){
                    msg = "First unequip " + currentItem.ItemMeta.ItemParentClass.toString() + "!";
                }

                Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
            }
        }

        if(updateSheet) {
            onItemClickListener.onItemClick(currentItem);
        }
    }

    public OnItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void updateCharacterInto(Character character)
    {
        this.user_character = character;
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {
        protected ImageView imageView;
        protected TextView txtTitle;
        protected CheckBox chkEquipped;

        public CustomViewHolder(View view) {
            super(view);
            this.imageView = (ImageView) view.findViewById(R.id.img);
            this.txtTitle = (TextView) view.findViewById(R.id.txtTitle);
            this.chkEquipped = (CheckBox) view.findViewById(R.id.chkEquipped);
        }
    }
}
