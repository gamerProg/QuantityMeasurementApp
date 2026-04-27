public class QuantityMeasurementAppUC7 {

    enum LengthUnit {
        FEET(1.0),
        INCH(1.0 / 12.0),
        YARD(3.0),
        CM(0.393701 / 12.0);

        private final double toFeet;

        LengthUnit(double toFeet) {
            this.toFeet = toFeet;
        }

        public double toFeet(double value) {
            return value * toFeet;
        }

        public double fromFeet(double feetValue) {
            return feetValue / toFeet;
        }
    }

    static class Quantity {
        private final double value;
        private final LengthUnit unit;

        public Quantity(double value, LengthUnit unit) {
            if (unit == null) throw new IllegalArgumentException("Unit cannot be null");
            if (!Double.isFinite(value)) throw new IllegalArgumentException("Invalid value");

            this.value = value;
            this.unit = unit;
        }

        private double toFeet() {
            return unit.toFeet(value);
        }

        // 🔥 UC6 METHOD (keep it for backward compatibility)
        public Quantity add(Quantity other) {
            return add(other, this.unit);
        }

        // 🔥 UC7 CORE METHOD
        public Quantity add(Quantity other, LengthUnit targetUnit) {

            if (other == null) throw new IllegalArgumentException("Other is null");
            if (targetUnit == null) throw new IllegalArgumentException("Target unit null");

            double sumFeet = this.toFeet() + other.toFeet();

            double result = targetUnit.fromFeet(sumFeet);

            return new Quantity(result, targetUnit);
        }

        @Override
        public String toString() {
            return value + " " + unit;
        }
    }

    public static void main(String[] args) {

        Quantity q1 = new Quantity(1.0, LengthUnit.FEET);
        Quantity q2 = new Quantity(12.0, LengthUnit.INCH);

        System.out.println(q1.add(q2, LengthUnit.FEET));   // 2 FEET
        System.out.println(q1.add(q2, LengthUnit.INCH));   // 24 INCH
        System.out.println(q1.add(q2, LengthUnit.YARD));   // ~0.667 YARD

        Quantity q3 = new Quantity(36.0, LengthUnit.INCH);
        Quantity q4 = new Quantity(1.0, LengthUnit.YARD);

        System.out.println(q3.add(q4, LengthUnit.FEET));   // 6 FEET
    }
}