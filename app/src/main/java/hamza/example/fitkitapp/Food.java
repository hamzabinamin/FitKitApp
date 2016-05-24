package hamza.example.fitkitapp;

/**
 * Created by Hamza on 2/10/2016.
 */
public class Food {

    private String name;
    private String size;
    private int quantity;
    private float calories;
    private float fat;
    private float carbs;
    private float protein;

    public Food()
    {
        name = "";
        size = "";
        quantity = 0;
        calories = 0;
        fat = 0;
        carbs = 0;
        protein = 0;

    }

    public Food(String name, String size, int quantity, float calories, float fat, float carbs, float protein)
    {
        this.name = name;
        this.size = size;
        this.quantity = quantity;
        this.calories = calories;
        this.fat = fat;
        this.carbs = carbs;
        this.protein = protein;
    }

    public String getName() {
        return name;
    }

    public String getSize() {
        return size;
    }

    public int getQuantity() {
        return quantity;
    }

    public float getCalories() {
        return calories;
    }

    public float getFat() {
        return fat;
    }

    public float getCarbs() {
        return carbs;
    }

    public float getProtein() {
        return protein;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setCalories(float calories) {
        this.calories = calories;
    }

    public void setCarbs(float carbs) {
        this.carbs = carbs;
    }

    public void setFat(float fat) {
        this.fat = fat;
    }

    public void setProtein(float protein) {
        this.protein = protein;
    }

    @Override
    public String toString() {
        return name;
    }

}
