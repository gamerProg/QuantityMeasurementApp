public class UC10Demo {

    public static void main(String[] args) {

        // 🔹 Length
        Quantity<LengthUnit2> l1 = new Quantity<>(1.0, LengthUnit2.FEET);
        Quantity<LengthUnit2> l2 = new Quantity<>(12.0, LengthUnit2.INCH);

        System.out.println(l1.equals(l2)); // true
        System.out.println(l1.convertTo(LengthUnit2.INCH)); // 12 inch
        System.out.println(l1.add(l2)); // 2 feet

        // 🔹 Weight
        Quantity<WeightUnit2> w1 = new Quantity<>(1.0, WeightUnit2.KILOGRAM);
        Quantity<WeightUnit2> w2 = new Quantity<>(1000.0, WeightUnit2.GRAM);

        System.out.println(w1.equals(w2)); // true
        System.out.println(w1.add(w2)); // 2 kg

        // 🔥 Cross-category check
        System.out.println(l1.equals(w1)); // false
    }
}