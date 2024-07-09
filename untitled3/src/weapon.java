public class Weapon extends Finding {
    private String itemType;
    private double weight;

    public Weapon(int id, double latitude, double longitude, int personId, String dateFound, int yearEstimated, Integer museumId, String material, String itemType, double weight) {
        super(id, latitude, longitude, personId, dateFound, yearEstimated, museumId, "Weapon", material);
        this.itemType = itemType;
        this.weight = weight;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "Weapon{" +
                "itemType='" + itemType + '\'' +
                ", weight=" + weight +
                ", " + super.toString() +
                '}';
    }
}
