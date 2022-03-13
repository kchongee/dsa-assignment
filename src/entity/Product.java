package entity;
public class Product implements DetailShowable{

    String title;
    double price;
    String description;    
    // String category;

    public Product(String title, double price, String description){
        this.title = title;
        this.price = price;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return String.format("%s\nPrice: %.2f\n",title,price);
    }

    @Override
    public String toDetailString() {
        // TODO Auto-generated method stub
        return String.format("Title: %s\nPrice: %.2f\nDescription: %s\n",title,price,description);
    }
            
}
