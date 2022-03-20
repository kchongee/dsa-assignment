package view.RoomViews;

import UtilityClasses.CMD;
import adtImplementation.ArrayList;
import application.App;
import entity.Account;
import entity.Cart;
import entity.CartDetails;
import entity.Option;

public class CartDetailsView
{

    CartDetails cartDetails;

    ArrayList<Option> options = new ArrayList<>
    (
        new Option[]
        {
              new Option(i->addQuantity())
        }
    );

    public CartDetailsView(CartDetails cartDetails) {
        this.cartDetails = cartDetails;
    }

//    public static void main(Cart cart)
//    {
//        // pass cart to here from cart list [seller ID + cartID + username]
//
//        // test cart
//        Cart cartTest = new Cart(1, new Account("A50","chailey1d"));
//
//
//        CartDetails cartDetails = new CartDetails(cartTest);
//        CartDetailsView view = new CartDetailsView();
//
//
//        System.out.println(cart.getProductList().toString());
//    }
    public void addQuantity()
    {
        int productNo = App.promptIntInput("Select a product");
        if (productNo > 0 && productNo <= cartDetails.getCartDetails().size())
        {
            int productQty = App.promptIntInput("How many want to add? >> ");
            boolean added =this.cartDetails.addQuantity(productNo-1, productQty);
            if (!added){
                CMD.pauseWithCustomScript("Error quantity! press any key to proceed");
            }
        }
    }


    public static void main(String[] args)
    {
        // pass cart to here from cart list [seller ID + cartID + username]

        // test cart
        Cart cartTest = new Cart(1, new Account("A50","chailey1d"));
        CartDetails cartDetails = new CartDetails(cartTest);  // maybe cart no need cart details
        cartDetails.syncCartDetails();

        CartDetailsView view = new CartDetailsView(cartDetails);

        while (true)
        {
            System.out.println(view.cartDetails.toString());
            int option = App.promptIntInput("Please select an action >> ");

            if (option >= 1 && option<= 5) {
                App.goToUserOption(option, view.options);
                CMD.cls();
            }
            else if (option >= 6 && option <= 7)
            {
                App.goToUserOption(option, view.options);
            }else {

            }
        }
    }

}
