package com.autosalon;

import java.util.Scanner;

import com.autosalon.dao.UserDAO;
import com.autosalon.menu.AdminMenu;
import com.autosalon.menu.Menu;
import com.autosalon.menu.UserMenu;
import com.autosalon.model.Role;
import com.autosalon.model.User;

import jakarta.persistence.NoResultException;

public class MainClass {

    static UserDAO userDAO = new UserDAO();

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        while (true) {
            System.out.print("Username: ");
            String username = in.nextLine();
            System.out.print("Password: ");
            String password = in.nextLine();

            User user;
            try {
                user = userDAO.AuthUser(username, password);
            } catch (NoResultException ex) {
                System.out.println("Неверный логин или пароль");
                continue;
            }

            Menu menu;
            if (user.getUserRole() == Role.ROLE_ADMIN) {
                menu = new AdminMenu();
            } else {
                menu = new UserMenu();
            }
            menu.menu(user);
            break;
        }
    }
}