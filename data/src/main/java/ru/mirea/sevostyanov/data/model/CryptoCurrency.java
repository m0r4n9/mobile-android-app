package ru.mirea.sevostyanov.data.model;

public class CryptoCurrency {
    private String symbol;
    private double usdPrice;
    private double rubPrice;

    public CryptoCurrency(String symbol, double usdPrice, double rubPrice) {
        this.symbol = symbol;
        this.usdPrice = usdPrice;
        this.rubPrice = rubPrice;
    }

    public String getSymbol() {
        return symbol;
    }

    public double getUsdPrice() {
        return usdPrice;
    }

    public double getRubPrice() {
        return rubPrice;
    }
}