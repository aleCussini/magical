package com.mtsoftware.magical;

/**
 * Created by NESEA on 20/11/2017.
 */

public class CardPostBean {

    private String  cardName;
    private int     cardPrice;
    private String  rarity;
    private String  expansion;
    private boolean  altered;
    private boolean foil;

    public boolean isAltered() {
        return altered;
    }

    public void setAltered(boolean altered) {
        this.altered = altered;
    }

    public boolean isFoil() {
        return foil;
    }

    public void setFoil(boolean foil) {
        this.foil = foil;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    private int     amount;

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public int getCardPrice() {
        return cardPrice;
    }

    public void setCardPrice(int cardPrice) {
        this.cardPrice = cardPrice;
    }

    public String getRarity() {
        return rarity;
    }

    public void setRarity(String rarity) {
        this.rarity = rarity;
    }

    public String getExpansion() {
        return expansion;
    }

    public void setExpansion(String expansion) {
        this.expansion = expansion;
    }
}
