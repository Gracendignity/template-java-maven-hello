package org.example;

import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

public class MyLogin implements MyAction {

    private static final String ACTION_NAME = "login";
    private Scanner scanner = null;
    private MyUserManager userManager = null; 

    public MyLogin(Scanner scanner,MyUserManager userManager) {
        this.scanner = scanner;
        this.userManager = userManager;
    }
    
    @Override
    public String getActionName() {
        return MyLogin.ACTION_NAME;
    }
    String userInput = "";

    @Override
    public void run(String[] args) {

        List<MyAction> list = new ArrayList<MyAction>();


        MyPasswordManager passWord = new MyPasswordManager(scanner,userManager);
        list.add(passWord);


        System.out.println("欢迎进入登录界面!");
        while(true) {
            System.out.print("请输入用户名:");
            String username = this.scanner.nextLine();
            
            System.out.print("请输入密码:");
            String password = "";
            MyPasswordSecurity.hidePassword(password);
            
            MyPasswordSecurity.PasswordEncryption(password);

            boolean success = this.userManager.login(username, password);
            if(success){

                
                System.out.println("登录成功!");
                if(username.equals("admin")){
                System.out.println("请输入你的指令:密码管理:passWord,顾客管理:customer,商品管理:product,q 退出");
                    userInput = this.scanner.nextLine();

                    
                    if (userInput.equals("q")) {
                        break; 
                    }
                    
                    String actionName = null;
                    for(MyAction twoAction: list) {
                        actionName = twoAction.getActionName();
        
                        if (userInput.equalsIgnoreCase(actionName)) {
                            twoAction.run(null); 
                        }
                   }
                }
                else {
                System.out.println("请输入你的指令:密码管理:passWord,购物：shopping,q 退出");
                    userInput = this.scanner.nextLine();

                    if (userInput.equals("q")) {
                        break; 
                    }
                    
                    String actionName = null;
                    for(MyAction twoAction: list) {
                        actionName = twoAction.getActionName();
        
                        if (userInput.equalsIgnoreCase(actionName)) {
                            twoAction.run(null); 
                        }
                   }
                }
                break;
            }
        }
    } 
}