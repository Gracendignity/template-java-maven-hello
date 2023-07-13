package org.example;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

public class MyHelpAction implements MyAction {

    private static final String ACTION_NAME = "manager";
    private Scanner scanner = null;

    public MyHelpAction(Scanner scanner) {
        this.scanner = scanner;
    }

    @Override
    public String getActionName() {
        return MyHelpAction.ACTION_NAME;
    }

    @Override
    public void run(String[] args) {
        System.out.println("欢迎进入管理员界面!");

        List<MyAction> list = new ArrayList<MyAction>();

        MyUserManager userManager=new MyUserManager();
        MyLogin login = new MyLogin(scanner,userManager);
        list.add(login);
        
        MyUserRegister register = new MyUserRegister(scanner,userManager);
        list.add(register);

        MyPasswordManager passWord = new MyPasswordManager(scanner,userManager);
        list.add(passWord);
        
        String userInput = "";

        while(true) {
            System.out.println("你当前在 manager的二级子目录下 >");
            System.out.println("请输入你的指令:已有账户登录:login,新用户跳转注册:register,q 退出");
            userInput = this.scanner.nextLine();

            if (userInput.equals("q")) {
                break; 
            }
            String actionName = null;
            for(MyAction oneAction: list) {
                actionName = oneAction.getActionName();

                if (userInput.equalsIgnoreCase(actionName)) {
                    oneAction.run(null);
 
                    System.out.println("请输入你的指令:密码管理:login,顾客管理:customer,商品管理:product,q 退出");
                    userInput = this.scanner.nextLine();
                    
                    for(MyAction twoAction: list) {
                        actionName = twoAction.getActionName();
        
                        if (userInput.equalsIgnoreCase(actionName)) {
                            twoAction.run(null); 
                        }
                   }
                }
           }
         System.out.println("你已退出管理员界面，回到开始页面!");
        }
    }
 }