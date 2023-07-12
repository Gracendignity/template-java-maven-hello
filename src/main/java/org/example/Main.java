package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        System.out.println("Hello world!");

        Scanner scanner = new Scanner(System.in);

        List<MyAction> actionList = new ArrayList<MyAction>();

        MyHelpAction manager = new MyHelpAction(scanner);
        actionList.add(manager);

        MyAboutAction user = new MyAboutAction(scanner);
        actionList.add(user);

        String userInput = "";

        while (true) {
            System.out.print("欢迎使用购物管理系统! >");
            System.out.println("请输入你的指令:进入管理员系统:manager,进入用户系统:user, 退出:exit");
            userInput = scanner.nextLine();

            if (userInput.equals("exit")) {
                break;
            }
            
            String actionName = null;
            for(MyAction oneAction: actionList) {
                actionName = oneAction.getActionName();

                if (userInput.equalsIgnoreCase(actionName)) {
                    oneAction.run(null);
                }
            }

        }

        scanner.close();
        System.out.println("Done.");
    }
}