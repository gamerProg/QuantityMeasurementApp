public class Quantity<U extends IMeasurable> {

    private final double value;
    private final U unit;

    public Quantity(double value, U unit) {
        if (unit == null) throw new IllegalArgumentException("Unit null");
        if (!Double.isFinite(value)) throw new IllegalArgumentException("Invalid value");

        this.value = value;
        this.unit = unit;
    }

    private double toBase() {
        return unit.convertToBaseUnit(value);
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Quantity<?> other = (Quantity<?>) obj;

        // 🔥 prevent cross-category
        if (this.unit.getClass() != other.unit.getClass()) return false;

        return Double.compare(this.toBase(), other.toBase()) == 0;
    }

    public Quantity<U> convertTo(U targetUnit) {

        if (targetUnit == null) throw new IllegalArgumentException("Target null");

        double base = this.toBase();
        double converted = targetUnit.convertFromBaseUnit(base);

        return new Quantity<>(converted, targetUnit);
    }

    public Quantity<U> add(Quantity<U> other) {
        return add(other, this.unit);
    }

    public Quantity<U> add(Quantity<U> other, U targetUnit) {

        if (other == null) throw new IllegalArgumentException("Other null");
        if (targetUnit == null) throw new IllegalArgumentException("Target null");

        double sum = this.toBase() + other.toBase();

        double result = targetUnit.convertFromBaseUnit(sum);

        return new Quantity<>(result, targetUnit);
    }

    public Quantity<U> subtract(Quantity<U> other) {

        if (other == null) throw new IllegalArgumentException("Other is null");

        if (this.unit.getClass() != other.unit.getClass())
            throw new IllegalArgumentException("Different measurement categories");

        double resultBase = this.toBase() - other.toBase();

        double result = this.unit.convertFromBaseUnit(resultBase);

        return new Quantity<>(round(result), this.unit);
    }

    public Quantity<U> subtract(Quantity<U> other, U targetUnit) {

        if (other == null) throw new IllegalArgumentException("Other is null");
        if (targetUnit == null) throw new IllegalArgumentException("Target unit null");

        if (this.unit.getClass() != other.unit.getClass())
            throw new IllegalArgumentException("Different measurement categories");

        double resultBase = this.toBase() - other.toBase();

        double result = targetUnit.convertFromBaseUnit(resultBase);

        return new Quantity<>(round(result), targetUnit);
    }

    public double divide(Quantity<U> other) {

        if (other == null) throw new IllegalArgumentException("Other is null");

        if (this.unit.getClass() != other.unit.getClass())
            throw new IllegalArgumentException("Different measurement categories");

        double divisor = other.toBase();

        if (divisor == 0.0)
            throw new ArithmeticException("Division by zero");

        return this.toBase() / divisor;
    }

    private double round(double value) {
        return Math.round(value * 100.0) / 100.0;
    }

    @Override
    public String toString() {
        return value + " " + unit.getUnitName();
    }
}