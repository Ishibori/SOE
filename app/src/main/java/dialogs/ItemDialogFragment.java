package dialogs;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TableRow;
import android.widget.TextView;

import com.soe.ishibori.stagesofexperience.R;

import adapters.InventoryShopAdapter;

/**
 * Created by Ishibori on 13/09/2017.
 */

public class ItemDialogFragment extends DialogFragment {
    private String title;
    private String description;
    private int itemType;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        title = getArguments().getString(InventoryShopAdapter.KEY_ITEM_TITLE,"No Name");
        description = getArguments().getString(InventoryShopAdapter.KEY_ITEM_DESCRIPTION, "Not Available");
        itemType = getArguments().getInt(InventoryShopAdapter.KEY_ITEM_TYPE, 0);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        //ContextThemeWrapper ctw = getThemeWrapper(itemType);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View customView = inflater.inflate(R.layout.item_dialog, null);

//        TextView customTitleView = new TextView(getActivity());
//        customTitleView.setTextColor(textColor);
//        customTitleView.setBackgroundColor(Color.BLACK);
//        customTitleView.setGravity(View.TEXT_ALIGNMENT_CENTER);
//        customTitleView.setText(title);
//        customTitleView.setTextSize(15);
//        customTitleView.setWidth(1080);
//        customTitleView.setHeight(100);

//        TextView customDescriptionView = (TextView) customView.findViewById(R.id.lbl_item_description);
//        customDescriptionView.setTextColor(Color.WHITE);
//        customDescriptionView.setBackgroundColor(Color.BLACK);

        //builder.setCustomTitle(customTitleView);

        builder.setView(customView);
        builder.setTitle(title);
        builder.setMessage(description);
        builder.setPositiveButton(R.string.option_ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });

        // Create the AlertDialog object and return it
        return builder.create();
    }

//    private ContextThemeWrapper getThemeWrapper(int itemType)
//    {
//        if(itemType == 0){
//            return new ContextThemeWrapper(getActivity(), R.style.ItemDialogStyle);
//        }
//        else if(itemType == 1) {
//            return new ContextThemeWrapper(getActivity(), R.style.ItemDialogStyle);
//        }
//        else{
//            return new ContextThemeWrapper(getActivity(), R.style.ItemDialogStyle);
//        }
//    }

}
