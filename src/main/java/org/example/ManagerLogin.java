package org.example;

import java.util.Scanner;

import java.util.List;
import java.util.ArrayList;

public class ManagerLogin implements MyAction{
    private static final String ACTION_NAME = "Login";
    private Scanner scanner = null;
    private MyUserManager userManager = null; 

    public ManagerLogin(Scanner scanner,MyUserManager userManager) {
        this.scanner = scanner;
        this.userManager = userManager;
    }
    
    @Override
    public String getActionName() {
        return ManagerLogin.ACTION_NAME;
    }
    String userInput = "";

    @Override
    public void run(String[] args) {

        List<MyAction> list = new ArrayList<MyAction>();


        ManagerPassword Password = new ManagerPassword(scanner);
        list.add(Password);
    

        MyCustomerManager customer = new MyCustomerManager(scanner);
        list.add(customer);
        
        MyProductManager product = new MyProductManager(scanner);
        list.add(product);

        System.out.println("欢迎进入登录界面!");
        int count=1;
        boolean success=false;
        while(true) {
            System.out.print("请输入用户名:");
            String username = this.scanner.nextLine();
 
             System.out.print("请输入密码:");
             String password = this.scanner.nextLine();
             password=MyPasswordSecurity.PasswordEncryption(password);

             success = this.userManager.managerLogin(username, password);
            if(success){
                System.out.println("登录成功!");
                break;
            }
            else{
                count++;
                if(count==5){
                    System.out.println("你的账户已被锁定!使用密码管理功能重置密码：passWord");
                    userInput = this.scanner.nextLine();

                    String actionName = null;
                    for(MyAction twoAction: list) {
                        actionName = twoAction.getActionName();
        
                        if (userInput.equalsIgnoreCase(actionName)) {
                            twoAction.run(null); 
                        }
                   }
                   System.out.println("重置成功，请重新登录!");
                   
                  }
            }
        }
        while(success) {

                while(true){
                    System.out.println("请输入你的指令:密码管理:Password,顾客管理:customer,商品管理:product,q 退出");
                    userInput = this.scanner.nextLine();
                    if (userInput.equals("Password")) {
                        break; 
                    }
                    if (userInput.equals("customer")) {
                        break; 
                    }
                    if (userInput.equals("product")) {
                        break; 
                       
                    }
                    if (userInput.equals("q")) {
                        break; 
                       
                    }
                }
                if (userInput.equals("q")) {
                    break; 
                   
                }
                while(true){
                    String actionName = null;
                    for(MyAction twoAction: list) {
                        actionName = twoAction.getActionName();
        
                        if (userInput.equalsIgnoreCase(actionName)) {
                            twoAction.run(null); 
                        }
                   }
                   System.out.println("q退出,继续使用"+userInput+"功能:G");
                   String input = this.scanner.nextLine();
                   if (input.equals("q")) {
                    break; 
                   }
                   else if(input.equals("G")){
                    for(MyAction twoAction: list) {
                        actionName = twoAction.getActionName();
        
                        if (userInput.equalsIgnoreCase(actionName)) {
                            twoAction.run(null); 
                        }
                   }
                   System.out.println("q退出,继续使用客户管理功能:G");
                   input = this.scanner.nextLine();
                   if (input.equals("q")) {
                    break; 
                   } 
                }
            }
         }
    }
    
}
