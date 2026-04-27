public class UC11Demo {

    public static void main(String[] args) {

        Quantity<VolumeUnit> v1 = new Quantity<>(1.0, VolumeUnit.LITRE);
        Quantity<VolumeUnit> v2 = new Quantity<>(1000.0, VolumeUnit.MILLILITRE);

        System.out.println(v1.equals(v2)); // true

        System.out.println(v1.convertTo(VolumeUnit.GALLON)); // ~0.264

        System.out.println(v1.add(v2)); // 2 LITRE

        System.out.println(v1.add(v2, VolumeUnit.MILLILITRE)); // 2000 mL
    }
}