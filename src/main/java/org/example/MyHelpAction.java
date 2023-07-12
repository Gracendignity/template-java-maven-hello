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
        System.out.print("欢迎进入管理员界面!");
        List<MyAction> list = new ArrayList<MyAction>();

        MyLogin login = new MyLogin(scanner);

        list.add(login);
        
        String userInput = "";

        while(true) {
            System.out.print("你当前在 manager的二级子目录下 >");
            System.out.println("请输入你的指令:登录:login,密码管理:passwordManager,客户管理:userManager,商品管理:productManager,q 退出");
            userInput = this.scanner.nextLine();

            if (userInput.equals("q")) {
                break;
            }
    }
    System.out.println("你已退出管理员界面，回到开始页面!");
    }
}