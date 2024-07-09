public class Jewelry extends Finding {
    private String itemType;
    private double estimatedValue;
    private String imageFilename;

    public Jewelry(int id, double latitude, double longitude, int personId, String dateFound, int yearEstimated, Integer museumId, String material, String itemType, double estimatedValue, String imageFilename) {
        super(id, latitude, longitude, personId, dateFound, yearEstimated, museumId, "Jewelry", material);
        this.itemType = itemType;
        this.estimatedValue = estimatedValue;
        this.imageFilename = imageFilename;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public double getEstimatedValue() {
        return estimatedValue;
    }

    public void setEstimatedValue(double estimatedValue) {
        this.estimatedValue = estimatedValue;
    }

    public String getImageFilename() {
        return imageFilename;
    }

    public void setImageFilename(String imageFilename) {
        this.imageFilename = imageFilename;
    }

    @Override
    public String toString() {
        return "Jewelry{" +
                "itemType='" + itemType + '\'' +
                ", estimatedValue=" + estimatedValue +
                ", imageFilename='" + imageFilename + '\'' +
                ", " + super.toString() +
                '}';
    }
}
