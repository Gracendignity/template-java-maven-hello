package org.example;

import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

public class MyAboutAction implements MyAction {
    private static final String ACTION_NAME = "user";

    private Scanner scanner = null;

    public MyAboutAction(Scanner scanner) {
        this.scanner = scanner;
    }

    @Override
    public String getActionName() {
        return MyAboutAction.ACTION_NAME;
    }

    @Override
    public void run(String[] args) {
        System.out.println("欢迎进入用户界面!");

        List<MyAction> list = new ArrayList<MyAction>();

        MyUserManager userManager=new MyUserManager();
        MyLogin login = new MyLogin(scanner,userManager);
        list.add(login);
        
        MyUserRegister register = new MyUserRegister(scanner,userManager);
        list.add(register);

        String userInput = null;

        while(true) {
            System.out.println("你当前在user的二级子目录下 >");
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
                }
            }
    }
    System.out.println("你已退出用户界面，回到开始页面!");
    }

}
