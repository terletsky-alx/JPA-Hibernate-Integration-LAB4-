package com.autosalon.menu;

import java.util.Scanner;

import com.autosalon.dao.EmployeeDAO;
import com.autosalon.dao.ShowroomDAO;
import com.autosalon.dao.UserDAO;
import com.autosalon.model.User;

public class Menu {

    User authUser;
    Scanner in = new Scanner(System.in);
    static EmployeeDAO employeeDAO = new EmployeeDAO();
    static UserDAO userDAO = new UserDAO();
    static ShowroomDAO showroomDAO = new ShowroomDAO();

    public void menu(User authUser) {
        this.authUser = authUser;
        System.out.printf("\nПривет, %s. Роль: %s\n",
                authUser.getEmployee().getName(), authUser.getUserRole().getRole());
    }
}