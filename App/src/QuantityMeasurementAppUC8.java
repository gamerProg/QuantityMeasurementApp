public class QuantityMeasurementAppUC8 {

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
            return unit.convertToBaseUnit(value);
        }

        // 🔥 Conversion
        public Quantity convertTo(LengthUnit targetUnit) {
            if (targetUnit == null) throw new IllegalArgumentException("Target unit null");

            double base = this.toFeet();
            double converted = targetUnit.convertFromBaseUnit(base);

            return new Quantity(converted, targetUnit);
        }

        // 🔥 Addition with target unit (UC7 retained)
        public Quantity add(Quantity other, LengthUnit targetUnit) {
            if (other == null) throw new IllegalArgumentException("Other null");
            if (targetUnit == null) throw new IllegalArgumentException("Target null");

            double sumFeet = this.toFeet() + other.toFeet();

            double result = targetUnit.convertFromBaseUnit(sumFeet);

            return new Quantity(result, targetUnit);
        }

        // 🔥 Equality
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

    public static void main(String[] args) {

        Quantity q1 = new Quantity(1.0, LengthUnit.FEET);
        Quantity q2 = new Quantity(12.0, LengthUnit.INCH);

        System.out.println(q1.equals(q2)); // true

        System.out.println(q1.convertTo(LengthUnit.INCH)); // 12 INCH

        System.out.println(q1.add(q2, LengthUnit.YARD)); // ~0.667 YARD
    }
}