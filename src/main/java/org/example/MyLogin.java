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
     String input="";
    @Override
    public void run(String[] args) {

        System.out.println("欢迎进入登录界面!");
        
        List<MyAction> list = new ArrayList<MyAction>();

        MyPasswordManager passWord=new MyPasswordManager(scanner);
        list.add(passWord);

        while(true) {
            System.out.print("请输入用户名:");
            String username = this.scanner.nextLine();

            System.out.print("请输入密码:");
            String password = this.scanner.nextLine();

            boolean success = this.userManager.login(username, password);
            if(success){
                System.out.println("登录成功,请输入指令：密码管理:passWord,顾客管理:customer,商品管理:product,q 退出");
                input = this.scanner.nextLine();

                if (input.equals("q")) {
                    break; 
                }

            String actionName = null;
            for(MyAction oneAction: list) {
                actionName = oneAction.getActionName();

                if (input.equalsIgnoreCase(actionName)) {
                    oneAction.run(null);
                }
            }
            }
        }
        System.out.println("你已退出登录界面，回到管理员页面!");
    }

    
}