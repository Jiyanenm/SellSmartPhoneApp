package project.jade.tut.ac.za.jadeapp.model;

/**
 * Created by Nkosinathi.Jiyane on 2017/03/10.
 */

public class Phone {
    private int id;
    private String brand;
    private int yearReleased;
    private String model;
    private String ram;
    private String extraFeature;
    private String serialNumber;
    private double price;
    private String image;

    public Phone() {
    }

    public Phone(int id, String brand, int yearReleased, String model, String ram, String extraFeature, String serialNumber, double price, String image) {
        this.id = id;
        this.brand = brand;
        this.yearReleased = yearReleased;
        this.model = model;
        this.ram = ram;
        this.extraFeature = extraFeature;
        this.serialNumber = serialNumber;
        this.price = price;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getYearReleased() {
        return yearReleased;
    }

    public void setYearReleased(int yearReleased) {
        this.yearReleased = yearReleased;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getRam() {
        return ram;
    }

    public void setRam(String ram) {
        this.ram = ram;
    }

    public String getExtraFeature() {
        return extraFeature;
    }

    public void setExtraFeature(String extraFeature) {
        this.extraFeature = extraFeature;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
