public enum WeightUnit {

    KILOGRAM(1.0),
    GRAM(0.001),
    POUND(0.453592);

    private final double toKg;

    WeightUnit(double toKg) {
        this.toKg = toKg;
    }

    // convert TO base (kg)
    public double convertToBaseUnit(double value) {
        return value * toKg;
    }

    // convert FROM base (kg)
    public double convertFromBaseUnit(double baseValue) {
        return baseValue / toKg;
    }
}