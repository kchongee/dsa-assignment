package entity;

import UtilityClasses.jdbcUtil;
import adtImplementation.ArrayList;
import adtImplementation.HashMap;

public class CartList {
    ArrayList<Cart> cart;
    Account account;

    public CartList(Account account) {
        this.account = account;
        this.cart = new ArrayList<Cart>();
    }

    public ArrayList<HashMap<String, Object>> fetchCartFromDb() {
        String query = String.format(
                        """
                           Select cartID,sellerID, A.userName
                           From Cart C, Account A
                           Where C.sellerID = A.accountID AND isCheckout=false AND buyerID='%s';
                        """, account.getAccountID()
        );System.out.print(query);
        return jdbcUtil.readAll(query);

    }

    public void syncCart(){
        cart.clear();
        ArrayList<HashMap<String, Object>> cartList =  fetchCartFromDb() ;
        for (int i=0; i<cartList.size(); i++){
            int cartID = (int)cartList.get(i).get("cartID");
            String sellerID = (String)cartList.get(i).get("sellerID");
            String sellerName = (String)cartList.get(i).get("userName");
            Cart c = new Cart(cartID, new Account(sellerID,sellerName,0));
            cart.add(c);
            //System.out.println(cartID);
            //System.out.println(sellerID);
            //System.out.println(sellerName);
        }
    }

    @Override
    public String toString() {

        String header = String.format("%-10s %-10s %-20s", "cartID","sellerID","sellerName");
        String completeString= header+"\n";
        for (int i = 0; i < cart.size(); i++) {
            Cart tempOrder = cart.get(i);
            int cartID = tempOrder.getCartID();
            String sellerID = tempOrder.getSeller().getAccountID();
            String sellerName = tempOrder.getSeller().getUserName();
            //System.out.println(cartID);
            //System.out.println(sellerID);
            //System.out.println(sellerName);
            String tempString = String.format("%-10s %-10s %-20s",cartID,sellerID, sellerName);
            completeString = completeString+tempString+"\n";
        } 
       
        return completeString;
    }

    // public static void main(String[] args) {
    //     CartList c = new CartList();
    //     c.fetchCartFromDb("A680");
    //     c.syncCart("A680");
    //     System.out.print(c.toString());
    // }

}