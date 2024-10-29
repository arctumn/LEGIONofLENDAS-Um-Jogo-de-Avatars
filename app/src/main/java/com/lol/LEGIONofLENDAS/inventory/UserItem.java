package com.lol.LEGIONofLENDAS.inventory;

import java.util.Comparator;

public class UserItem {
    public int image;
    public String name;
    public int force;

    public static final Comparator<UserItem> USER_ITEM_DESCENDING_COMPARATOR =
            Comparator.comparingInt(UserItem::comparator).reversed();

    public UserItem(int itemImage, String itemName, int itemForce) {
        image = itemImage;
        name = itemName;
        force = itemForce;
    }

    private int comparator() {
        return force;
    }
}
