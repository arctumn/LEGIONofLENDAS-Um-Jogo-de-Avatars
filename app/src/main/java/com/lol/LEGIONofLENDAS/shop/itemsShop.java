package com.lol.LEGIONofLENDAS.shop;

import java.util.Comparator;

public class itemsShop {


    private final String uid;
    private String id;

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getStr() {
        return Str;
    }

    public void setStr(String str) {
        Str = str;
    }

    public String getMagic() {
        return Magic;
    }

    public void setMagic(String magic) {
        Magic = magic;
    }

    public String getDefense() {
        return Defense;
    }

    public void setDefense(String defense) {
        Defense = defense;
    }

    public String getMagicDefense() {
        return MagicDefense;
    }

    public void setMagicDefense(String magicDefense) {
        MagicDefense = magicDefense;
    }

    public String getLife() {
        return life;
    }

    public void setLife(String life) {
        this.life = life;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    private int image;
    private String price,Str,Magic,Defense,MagicDefense,life,name;
    public itemsShop(int imagem, String price, String Str, String Magic, String Defense, String MagicDefense, String life, String id, String name, String s) {
        this.image = imagem;
        this.price = price;
        this.Str   = Str;
        this.Magic = Magic;
        this.Defense = Defense;
        this.MagicDefense = MagicDefense;
        this.life = life;
        this.name = name;
        this.id = id;
        this.uid = s;
    }
    private int value(){
        return Integer.parseInt(price);
    }
    public static final Comparator<itemsShop> DESCENDING =
            Comparator.comparingInt(itemsShop::value).reversed();

    public String getID() {
        return id;
    }

    public String getUid() {
        return uid;
    }
}
