public enum LengthUnit {

    FEET(1.0),
    INCH(1.0 / 12.0),
    YARD(3.0),
    CM(0.393701 / 12.0);

    private final double toFeet;

    LengthUnit(double toFeet) {
        this.toFeet = toFeet;
    }

    // Convert TO base (feet)
    public double convertToBaseUnit(double value) {
        return value * toFeet;
    }

    // Convert FROM base (feet)
    public double convertFromBaseUnit(double baseValue) {
        return baseValue / toFeet;
    }
}