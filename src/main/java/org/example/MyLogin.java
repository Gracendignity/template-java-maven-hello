package org.example;

import java.util.Scanner;

public class MyLogin implements MyAction {

    private static final String ACTION_NAME = "login";
    private Scanner scanner = null;

    public MyLogin(Scanner scanner) {
        this.scanner = scanner;
    }

    @Override
    public String getActionName() {
        return MyLogin.ACTION_NAME;
    }

    @Override
    public void run(String[] args) {
        System.out.print("欢迎进入登录界面!");

        String userInput = "";

        while(true) {
            System.out.print("请输入用户名:");
            String username = this.scanner.nextLine();

            System.out.print("请输入密码:");
            String password = this.scanner.nextLine();


            userInput = this.scanner.nextLine();

            if (userInput.equals("q")) {
                break;
            }
        }
        System.out.println("你已退出登录界面，回到管理员页面!");
    }

    
}