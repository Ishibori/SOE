package dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.soe.ishibori.stagesofexperience.R;

import models.Item;

/**
 * Created by Ishibori on 13/09/2017.
 */

public class ItemInformationDialog extends Dialog {

    public Activity activity;
    public Item item;
    public int colorStyle;

    public TextView lbl_title;
    public View view_separator;
    public TextView lbl_description;
    public Button btn_ok;

    public ItemInformationDialog(Activity activity, int color, Item item){
        super(activity);
        this.activity = activity;
        this.item = item;
        this.colorStyle = color;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.item_dialog);

        lbl_title = (TextView) findViewById(R.id.txt_title);
        view_separator = (View) findViewById(R.id.view_separator);
        lbl_description = (TextView) findViewById(R.id.txt_description);
        btn_ok = (Button) findViewById(R.id.btn_ok);

        lbl_title.setTextColor(colorStyle);
        view_separator.setBackgroundColor(colorStyle);
        lbl_description.setTextColor(colorStyle);
        btn_ok.setTextColor(colorStyle);

        lbl_title.setText(item.Title);
        lbl_description.setText(item.Description);

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

}
