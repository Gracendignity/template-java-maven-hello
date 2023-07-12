package org.example;
import java.util.Scanner;

public class MyUserRegister implements MyAction {

    private static final String ACTION_NAME = "register";
    private Scanner scanner = null;
    private MyUserManager userManager = null; 

    public MyUserRegister(Scanner scanner,MyUserManager userManager) {
        this.scanner = scanner;
        this.userManager = userManager;
    }
    
    @Override
    public String getActionName() {
        return MyUserRegister.ACTION_NAME;
    }

    @Override
    public void run(String[] args) {
        System.out.println("欢迎进入注册界面!");
        
        while(true) {
            System.out.print("请输入用户名:");
            String username = this.scanner.nextLine();

            System.out.print("请输入密码:");
            String password = this.scanner.nextLine();

            boolean success = this.userManager.registerUser(username, password);
            if(success){
                System.out.println("注册成功,返回上一级");
                break;
            }
        }
    } 
}