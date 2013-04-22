package de.minimum.hawapp.server.mensa;

public class RatingImpl implements Rating {
    int positiv;
    int negativ;

    private RatingImpl() {
        this.positiv = 0;
        this.negativ = 0;
    }

    public static RatingImpl Rating() {
        return new RatingImpl();
    }

    @Override
    public int getPosRatingInPercent() {
        if (this.positiv + this.negativ == 0)
            return 0;
        return (int) ((100.0 / (this.positiv + this.negativ)) * this.positiv);
    }

    @Override
    public void ratePoitiv() {
        this.positiv++;
    }

    @Override
    public void rateNegativ() {
        this.negativ++;
    }

}
