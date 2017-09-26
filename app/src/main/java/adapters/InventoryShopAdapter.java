package adapters;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.soe.ishibori.stagesofexperience.R;
import com.soe.ishibori.stagesofexperience.merchant_guild_act;

import dialogs.ItemDialogFragment;
import dialogs.ItemInformationDialog;
import models.Character;
import models.Item;
import utils.ItemUtils;

public class InventoryShopAdapter extends RecyclerView.Adapter<InventoryShopAdapter.CustomViewHolder> {
    public static String KEY_ITEM_TITLE = "KEY_ITEM_TITLE";
    public static String KEY_ITEM_DESCRIPTION = "KEY_ITEM_DESCRIPTION";
    public static String KEY_ITEM_TYPE = "KEY_ITEM_TYPE";

    public enum InventoryType{
        Merchant,
        Character
    }

    public Character items_source;
    public InventoryShopAdapter items_target_adapter;
    private Context mContext;
    private InventoryType InvType;
    private int TransactionImageId;
    private OnItemClickListener onItemClickListener;

    public InventoryShopAdapter(Context context, Character items_source, InventoryType invType) {
        this.items_source = items_source;
        this.mContext = context;
        this.InvType = invType;

        if(invType == InventoryType.Merchant)
        {
            TransactionImageId = ItemUtils.getDrawableIdentifier(mContext, "buy_item");
        }
        else
        {
            TransactionImageId = ItemUtils.getDrawableIdentifier(mContext, "sell_item");
        }
    }

    public void setTargetAdapter(InventoryShopAdapter items_target_adapter)
    {
        this.items_target_adapter = items_target_adapter;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.inventory_shop_item, null);
        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder customViewHolder, int i) {
        final Item currentItem = items_source.Items.get(i);

        customViewHolder.imageView.setImageResource(currentItem.ImageId);
        customViewHolder.txtTitle.setText(currentItem.Title);
        customViewHolder.txtTitle.setTextColor(ItemUtils.getItemTextColor(currentItem));
        customViewHolder.txtPrice.setText(currentItem.Price + "gp");
        customViewHolder.imgTransaction.setImageResource(TransactionImageId);

        //attach listeners
        View.OnClickListener transactionListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tryItemTransaction(currentItem);
            }
        };

        View.OnClickListener examineListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ItemInformationDialog dialog = new ItemInformationDialog((Activity) mContext, ItemUtils.getItemTextColor(currentItem), currentItem);
                dialog.show();
            }
        };

        customViewHolder.imgTransaction.setOnClickListener(transactionListener);
        customViewHolder.txtTitle.setOnClickListener(examineListener);
    }

    @Override
    public int getItemCount() {
        return (null != items_source.Items ? items_source.Items.size() : 0);
    }

    public OnItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void updateSourceInto(Character character)
    {
        this.items_source = character;
    }

    public void tryItemTransaction(Item currentItem)
    {
        if(InvType == InventoryType.Character)// the character sells
        {
            items_source.Items.remove(currentItem);
            items_source.Gold += currentItem.Price;
            items_target_adapter.items_source.Items.add(currentItem);
            Toast.makeText(mContext, "You sold " + currentItem.Title + "!", Toast.LENGTH_SHORT).show();
        }
        else if(InvType == InventoryType.Merchant) //the character buys
        {
            if(items_target_adapter.items_source.Gold >= currentItem.Price) {
                items_source.Items.remove(currentItem);
                items_target_adapter.items_source.Items.add(currentItem);
                items_target_adapter.items_source.Gold -= currentItem.Price;
                Toast.makeText(mContext, "You bought " + currentItem.Title + "!", Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(mContext, "Not enough money!", Toast.LENGTH_SHORT).show();
            }
        }

        notifyDataSetChanged();
        items_target_adapter.notifyDataSetChanged();

        ((merchant_guild_act)mContext).updateGold();
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {
        protected ImageView imageView;
        protected TextView txtTitle;
        protected TextView txtPrice;
        protected ImageView imgTransaction;

        public CustomViewHolder(View view) {
            super(view);
            this.imageView = (ImageView) view.findViewById(R.id.img);
            this.txtTitle = (TextView) view.findViewById(R.id.txtTitle);
            this.txtPrice = (TextView) view.findViewById(R.id.txtPrice);
            this.imgTransaction = (ImageView) view.findViewById(R.id.imgTransaction);
        }
    }
}
