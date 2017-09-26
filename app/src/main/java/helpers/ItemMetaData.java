package helpers;

import java.io.Serializable;
import java.util.ArrayList;

import models.Item;
import utils.ItemUtils;

/**
 * Created by Ishibori on 09/09/2017.
 */

public class ItemMetaData implements Serializable{
    public ItemUtils.ItemParentClass ItemParentClass;
    public int BasePrice;
    public int MaxAllowed;
    public ArrayList<ItemUtils.ItemChildClass> ItemChildClasses;

    public ItemMetaData(ItemUtils.ItemParentClass itemParentClass, int basePrice, ArrayList<ItemUtils.ItemChildClass> itemChildClasses, int maxAllowed)
    {
        this.ItemParentClass = itemParentClass;
        this.BasePrice=basePrice;
        this.MaxAllowed=maxAllowed;
        this.ItemChildClasses = itemChildClasses;
    }

    public ItemMetaData clone(){
        return new ItemMetaData(this.ItemParentClass, this.BasePrice, this.ItemChildClasses,this.MaxAllowed);
    }
}
