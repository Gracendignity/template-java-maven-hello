package org.example;

import java.util.Scanner;

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
     String input="";
    @Override
    public void run(String[] args) {

        System.out.println("欢迎进入登录界面!");
        while(true) {
            System.out.print("请输入用户名:");
            String username = this.scanner.nextLine();

            System.out.print("请输入密码:");
            String password = this.scanner.nextLine();

            boolean success = this.userManager.login(username, password);
            if(success){

                System.out.println("登录成功!");
                break;
            }
        }
    }

    
}