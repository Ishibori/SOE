package helpers;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.ViewGroup;
import android.view.ViewManager;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import utils.ItemUtils;

import static helpers.EquipmentSlot.ItemPositions.Helm;
import static helpers.EquipmentSlot.ItemPositions.Weapon;

/**
 * Created by Ishibori on 20/09/2017.
 */

public class EquipmentSlot {
    public enum ItemPositions { // the positions fit left top-down 5 slots, and next 5 slots
        Cloak,
        Bracers,
        Weapon,
        Gauntlets,
        RingLeft,
        Helm,
        Armor,
        Shield,
        RingRight,
        Boots
    }

    public Context ActivityContext;
    public ItemPositions PosType;
    public ImageView ImgView;

    public EquipmentSlot(Context context, ItemPositions posType, int imageId){
        this.ActivityContext = context;
        this.PosType = posType;
        this.ImgView = new ImageView(context);
        this.ImgView.setImageResource(imageId);
        this.ImgView.setId(posType.ordinal()+1);
    }

    public RelativeLayout.LayoutParams getParams(int posx, int posy){
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.width = 100;
        params.height = 100;
        return params;
    }

    public void setSlotIdle(){
        this.ImgView.setAlpha(0.5f);
    }

    public void setSlotActive(){
        this.ImgView.setAlpha(1.0f);
    }

    public void setColorFilter(int color, PorterDuff.Mode mode){
        this.ImgView.setColorFilter(color, mode);
    }

    public void addToLayout(RelativeLayout rl){
        rl.addView(this.ImgView, getParamsForPositionType());
    }

    public RelativeLayout.LayoutParams getParamsForPositionType(){
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.width = 100;
        params.height = 100;

        params.leftMargin = getPosX(this.PosType);
        params.topMargin = getPosY(this.PosType);

        return params;
    }

    private int getPosX(ItemPositions posType){
        if(posType.ordinal() >= 5){
            return 650;
        }
        else{
            return 140;
        }
    }

    private int getPosY(ItemPositions posType){
        if(posType.ordinal() >= 5){
            return (posType.ordinal() - 5) * 100 + 10;
        }
        else{
            return posType.ordinal() * 100 + 10;
        }
    }
}
