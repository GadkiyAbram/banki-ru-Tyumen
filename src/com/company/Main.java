package com.company;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.sql.SQLTransactionRollbackException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static String usd;
    public static String eur;

    public static void main(String[] args) throws Exception{

        List<BankName> bankRates = new ArrayList<BankName>();

        Document doc = Jsoup.connect("http://www.banki.ru/products/currency/cash/tyumen~/").get();

        Elements bNames = doc.select("tr[data-currencies-row]");
        Elements currencies = doc.select( "th[class^=colspan font-size-medium font-bold]");

        usd = currencies.select("th[data-currency-head-code=usd]").text();
        eur = currencies.select("th[data-currency-head-code=eur]").text();

        for (Element elem : bNames){
            String bankName = elem.child(0).getElementsByAttributeValue("class", "font-bold").text();
            String rateBuyUSD = elem.child(1).select("td[class^=font-size-large]").text();
            String rateSellUSD = elem.child(2).select("td[class^=font-size-large]").text();
            String rateBuyEURO = elem.child(4).select("td[class^=font-size-large]").text();
            String rateSellEURO = elem.child(5).select("td[class^=font-size-large]").text();
            String rateUpdated = elem.child(6).select("td[class^=color-border-dark]").text();
            bankRates.add(new BankName(bankName, rateBuyUSD, rateSellUSD, rateBuyEURO, rateSellEURO, rateUpdated));
        }
        bankRates.forEach(System.out::println);
    }
}

class BankName{
    String bankName;
    String rateBuyUSD;
    String rateSellUSD;
    String rateBuyEURO;
    String rateSellEURO;
    String rateUpdt;

    public BankName(String bankName, String rateBuyUSD, String rateSellUSD,
                    String rateBuyEURO, String rateSellEURO, String rateUpdt){
        this.bankName = bankName;
        this.rateBuyUSD = rateBuyUSD;
        this.rateSellUSD = rateSellUSD;
        this.rateBuyEURO = rateBuyEURO;
        this.rateSellEURO = rateSellEURO;
        this.rateUpdt = rateUpdt;
    }



    @Override
    public String toString(){
        return bankName + "\n" +
                "USD buy: " + rateBuyUSD + " s: " + rateSellUSD + "\n" +
                "EUR buy: " + rateBuyEURO + " s: " + rateSellEURO + "\n" +
                "Updated: " + rateUpdt + "\n";
    }
}
