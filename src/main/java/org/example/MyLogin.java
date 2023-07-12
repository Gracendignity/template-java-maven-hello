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
            System.out.println("请输入你的用户名及密码,q 退出");
            System.out.println("用户名: >");
            userInput = this.scanner.nextLine();



            if (userInput.equals("q")) {
                break;
            }
        }
        System.out.println("你已退出登录界面，回到管理员页面!");
    }

    
}