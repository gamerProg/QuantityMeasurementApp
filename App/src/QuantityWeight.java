public class QuantityWeight {

    private final double value;
    private final WeightUnit unit;

    public QuantityWeight(double value, WeightUnit unit) {
        if (unit == null) throw new IllegalArgumentException("Unit cannot be null");
        if (!Double.isFinite(value)) throw new IllegalArgumentException("Invalid value");

        this.value = value;
        this.unit = unit;
    }

    private double toKg() {
        return unit.convertToBaseUnit(value);
    }

    // 🔥 EQUALITY
    @Override
    public boolean equals(Object obj) {

        if (this == obj) return true;

        if (obj == null || getClass() != obj.getClass()) return false;

        QuantityWeight other = (QuantityWeight) obj;

        return Double.compare(this.toKg(), other.toKg()) == 0;
    }

    // 🔥 CONVERSION
    public QuantityWeight convertTo(WeightUnit target) {

        if (target == null) throw new IllegalArgumentException("Target unit null");

        double base = this.toKg();
        double converted = target.convertFromBaseUnit(base);

        return new QuantityWeight(converted, target);
    }

    // 🔥 ADDITION (default unit)
    public QuantityWeight add(QuantityWeight other) {
        return add(other, this.unit);
    }

    // 🔥 ADDITION (target unit)
    public QuantityWeight add(QuantityWeight other, WeightUnit target) {

        if (other == null) throw new IllegalArgumentException("Other null");
        if (target == null) throw new IllegalArgumentException("Target null");

        double sumKg = this.toKg() + other.toKg();

        double result = target.convertFromBaseUnit(sumKg);

        return new QuantityWeight(result, target);
    }

    @Override
    public String toString() {
        return value + " " + unit;
    }
}