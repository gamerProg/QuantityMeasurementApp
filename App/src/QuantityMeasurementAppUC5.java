public class QuantityMeasurementAppUC5 {

    // 🔹 Enum with conversion factors (to FEET base)
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

    // 🔹 Quantity class (same as UC3/UC4 but with conversion)
    static class Quantity {
        private final double value;
        private final LengthUnit unit;

        public Quantity(double value, LengthUnit unit) {
            if (unit == null) throw new IllegalArgumentException("Unit cannot be null");
            if (!Double.isFinite(value)) throw new IllegalArgumentException("Invalid value");

            this.value = value;
            this.unit = unit;
        }

        // Convert to another unit
        public Quantity convertTo(LengthUnit targetUnit) {
            if (targetUnit == null) throw new IllegalArgumentException("Target unit null");

            double base = unit.toFeet(value);
            double converted = targetUnit.fromFeet(base);

            return new Quantity(converted, targetUnit);
        }

        private double toFeet() {
            return unit.toFeet(value);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;

            Quantity other = (Quantity) obj;
            return Double.compare(this.toFeet(), other.toFeet()) == 0;
        }

        @Override
        public String toString() {
            return value + " " + unit;
        }
    }

    // 🔥 Static API method (IMPORTANT for marks)
    public static double convert(double value, LengthUnit from, LengthUnit to) {

        if (from == null || to == null)
            throw new IllegalArgumentException("Unit cannot be null");

        if (!Double.isFinite(value))
            throw new IllegalArgumentException("Invalid value");

        double base = from.toFeet(value);
        return to.fromFeet(base);
    }

    // 🔹 Demo
    public static void main(String[] args) {

        System.out.println(convert(1.0, LengthUnit.FEET, LengthUnit.INCH)); // 12
        System.out.println(convert(3.0, LengthUnit.YARD, LengthUnit.FEET)); // 9
        System.out.println(convert(36.0, LengthUnit.INCH, LengthUnit.YARD)); // 1

        Quantity q = new Quantity(1.0, LengthUnit.YARD);
        System.out.println(q.convertTo(LengthUnit.INCH)); // 36 inches
    }
}