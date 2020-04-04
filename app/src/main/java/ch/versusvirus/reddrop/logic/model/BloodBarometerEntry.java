package ch.versusvirus.reddrop.logic.model;

public class BloodBarometerEntry {

    private String date;
    private int nullPositive, nullNegative;
    private int aPositive, aNegative;
    private int abPositive, abNegative;
    private int bPositive, bNegative;

    public BloodBarometerEntry(String date, int nullPositive, int nullNegative,
                               int aPositive, int aNegative, int abPositive, int abNegative,
                               int bPositive, int bNegative) {
        this.date = date;
        this.nullPositive = nullPositive;
        this.nullNegative = nullNegative;
        this.aPositive = aPositive;
        this.aNegative = aNegative;
        this.abPositive = abPositive;
        this.abNegative = abNegative;
        this.bPositive = bPositive;
        this.bNegative = bNegative;
    }

    public String getDate() {
        return date;
    }


    public int getNullPositive() {
        return nullPositive;
    }

    public int getNullNegative() {
        return nullNegative;
    }

    public int getaPositive() {
        return aPositive;
    }

    public int getaNegative() {
        return aNegative;
    }

    public int getAbPositive() {
        return abPositive;
    }

    public int getAbNegative() {
        return abNegative;
    }

    public int getbPositive() {
        return bPositive;
    }

    public int getbNegative() {
        return bNegative;
    }

}

