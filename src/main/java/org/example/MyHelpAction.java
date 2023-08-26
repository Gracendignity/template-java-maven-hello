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

        UserPassword passWord = new UserPassword(scanner);
        list.add(passWord);
        
        String userInput = "";

        while(true) {
            System.out.println("你当前在 manager的二级子目录下 >");
            System.out.println("登录账户:login,q 退出");
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
    }
 }