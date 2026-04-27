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

        validateArithmeticOperands(other, targetUnit, true);

        double baseResult = performBaseArithmetic(other, ArithmeticOperation.ADD);

        double result = targetUnit.convertFromBaseUnit(baseResult);

        return new Quantity<>(round(result), targetUnit);
    }
    @Override
    public String toString() {
        return value + " " + unit.getUnitName();
    }

    public Quantity<U> subtract(Quantity<U> other) {
        return subtract(other, this.unit);
    }

    public Quantity<U> subtract(Quantity<U> other, U targetUnit) {

        validateArithmeticOperands(other, targetUnit, true);

        double baseResult = performBaseArithmetic(other, ArithmeticOperation.SUBTRACT);

        double result = targetUnit.convertFromBaseUnit(baseResult);

        return new Quantity<>(round(result), targetUnit);
    }

    public double divide(Quantity<U> other) {

        validateArithmeticOperands(other, null, false);

        return performBaseArithmetic(other, ArithmeticOperation.DIVIDE);
    }

    private double round(double value) {
        return Math.round(value * 100.0) / 100.0;
    }

    private enum ArithmeticOperation {
        ADD, SUBTRACT, DIVIDE
    }

    private void validateArithmeticOperands(Quantity<U> other, U targetUnit, boolean targetRequired) {

        if (other == null) throw new IllegalArgumentException("Other is null");

        if (this.unit.getClass() != other.unit.getClass())
            throw new IllegalArgumentException("Different measurement categories");

        if (!Double.isFinite(this.value) || !Double.isFinite(other.value))
            throw new IllegalArgumentException("Invalid numeric value");

        if (targetRequired && targetUnit == null)
            throw new IllegalArgumentException("Target unit null");
    }

    private double performBaseArithmetic(Quantity<U> other, ArithmeticOperation op) {

        double a = this.toBase();
        double b = other.toBase();

        switch (op) {
            case ADD:
                return a + b;

            case SUBTRACT:
                return a - b;

            case DIVIDE:
                if (b == 0.0) throw new ArithmeticException("Division by zero");
                return a / b;

            default:
                throw new IllegalArgumentException("Invalid operation");
        }
    }

}