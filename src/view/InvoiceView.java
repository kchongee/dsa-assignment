package view;

import java.util.function.Consumer;

import adtImplementation.ArrayList;
import adtInterfaces.ListInterface;
import application.App;
import entity.Option;
//import entity.Seller;
//import entity.ViewPage;

public class InvoiceView {
    public static ListInterface<Option> menuOptions = new ArrayList<Option>();  

    public static void main() {                
        printTitle("Live Sale System");                
        System.out.println("Product list");
        System.out.println("Total");
        //App.menuHandler(menuOptions);
    }

    public static void printTitle(String title){
        App.clearScreen();
        System.out.println(title);        
        System.out.println();
    }
    
    public static void goToPage(Consumer<String> page){
        App.history.push(i -> main());
        page.accept("t");
    }
    
}