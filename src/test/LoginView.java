package test;

import entity.Account;
import entity.AccountList;
import application.App;
import entity.Buyer;
import entity.Seller;
import test.SellerHomeView;

public class LoginView{     
    public static void main() {
        printTitle("Login");

        AccountList accountList = new AccountList(100);
        Account newAcc = new Account("nathan","nathan");
        System.out.println(accountList.addAccount(newAcc));
                     
        String uname = App.promptStringInput("Enter username: ");
        String pwd = App.promptStringInput("Enter password: ");

        
        if(accountList.checkAccount(new Account(uname, pwd))){
            System.out.println("Login Successful");
            App.currentUser = new Account(uname,pwd);
            if(App.currentUser.getIsSeller()==1){
                SellerHomeView.main();
            }else if(App.currentUser.getIsSeller()==0){
                BuyerHomeView.main();
            }else{
                SellerHomeView.main();
            }
        }else{
            System.out.println("Invalid credential.");
            boolean tryAgain = App.promptTryAgain();
            if(tryAgain){
                main();
            }else{
                App.goBack();
            }
        }   
    }       
    
    public static void printTitle(String title){
        App.clearScreen();
        System.out.println(title);        
        System.out.println();
    }
}
