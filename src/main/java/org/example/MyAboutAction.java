package org.example;

import java.util.Scanner;

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
        System.out.print("欢迎进入用户界面!");
        String userInput = null;

        while (true) {
            System.out.println("请输入你的指令,q 退出");

            userInput = this.scanner.nextLine();

            if (userInput.equals("q")) {
                break;
            }
        }
    }

}
