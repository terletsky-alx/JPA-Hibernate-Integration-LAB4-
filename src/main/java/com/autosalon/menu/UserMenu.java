package com.autosalon.menu;

import com.autosalon.model.User;

public class UserMenu extends Menu {

    @Override
    public void menu(User authUser) {
        super.menu(authUser);
        while (true) {
            System.out.println();
            System.out.println("0. Выход");
            System.out.println("1. Мой салон");
            System.out.println("2. Мои коллеги");
            System.out.println("3. Сменить пароль");
            try {
                System.out.print("Ввод: ");
                String options = in.nextLine();
                switch (options) {
                    case "0": return;
                    case "1": getMyShowroom(); break;
                    case "2": getMyColleagues(); break;
                    case "3": updatePassword(); break;
                    default: System.out.println("Неверный ввод.");
                }
            } catch (Exception ex) {
                System.out.println("Ошибка: " + ex.getMessage());
            }
        }
    }

    private void getMyShowroom() {
        System.out.println("Ваш салон: " + authUser.getEmployee().getShowroom());
    }

    private void getMyColleagues() {
        showroomDAO.getEmployeesByShowroom(authUser.getEmployee().getShowroom())
                .forEach(System.out::println);
    }

    private void updatePassword() {
        System.out.print("Новый пароль: ");
        String password = in.nextLine();
        userDAO.updatePassword(authUser.getUserId(), password);
        System.out.println("Пароль изменён");
    }
}