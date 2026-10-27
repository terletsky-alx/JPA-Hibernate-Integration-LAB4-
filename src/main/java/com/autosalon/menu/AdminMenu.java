package com.autosalon.menu;

import java.util.List;
import java.util.Set;

import com.autosalon.model.Employee;
import com.autosalon.model.Showroom;
import com.autosalon.model.User;

public class AdminMenu extends Menu {

    @Override
    public void menu(User authUser) {
        super.menu(authUser);
        while (true) {
            System.out.println();
            System.out.println("0. Выход");
            System.out.println("1. Посмотреть все салоны и их сотрудников");
            System.out.println("2. Найти салон по ID");
            System.out.println("3. Добавить салон");
            System.out.println("4. Перевести сотрудника в другой салон");
            System.out.println("5. Удалить салон (удаляет также его сотрудников)");
            System.out.println("6. Найти сотрудника по ID");
            System.out.println("7. Добавить сотрудника");
            System.out.println("8. Удалить сотрудника");
            System.out.println("9. Список юзеров");
            System.out.println("10. Найти юзера по никнейму");
            System.out.println("11. Добавить аккаунт сотруднику");
            System.out.println("12. Удалить юзера");
            System.out.println("13. Найти салоны в городе (HQL)");
            System.out.println("14. Найти сотрудников по должности (HQL)");
            System.out.println("15. Сотрудники со стажем не меньше N (Criteria)");
            try {
                System.out.print("Ввод: ");
                String options = in.nextLine();
                switch (options) {
                    case "0": return;
                    case "1": seeAllShowrooms(); break;
                    case "2": findShowroomByID(); break;
                    case "3": addShowroom(); break;
                    case "4": relocateEmployee(); break;
                    case "5": deleteShowroom(); break;
                    case "6": findEmployeeByID(); break;
                    case "7": addEmployee(); break;
                    case "8": deleteEmployee(); break;
                    case "9": getUsers(); break;
                    case "10": getUserByUsername(); break;
                    case "11": addUser(); break;
                    case "12": deleteUser(); break;
                    case "13": findShowroomsByCity(); break;
                    case "14": findByPosition(); break;
                    case "15": findByExperienceAtLeast(); break;
                    default: System.out.println("Неверный ввод.");
                }
            } catch (Exception ex) {
                System.out.println("Ошибка: " + ex.getMessage());
            }
        }
    }

    // 1
    private void seeAllShowrooms() {
        Set<Showroom> showrooms = showroomDAO.getShowroomsWithEmployees();
        for (Showroom showroom : showrooms) {
            System.out.println(showroom);
            for (Employee emp : showroom.getEmployees()) {
                System.out.println("\t" + emp);
            }
        }
    }

    // 2
    private void findShowroomByID() {
        System.out.print("Введите ID салона: ");
        String id = in.nextLine();
        System.out.println(showroomDAO.findShowroomByID(id));
    }

    // 3
    private void addShowroom() {
        System.out.print("Введите ID салона (3 символа): ");
        String id = in.nextLine();
        if (id.length() != 3) {
            throw new IllegalArgumentException("ID салона должен быть длиной в три символа.");
        }
        System.out.print("Введите название салона: ");
        String name = in.nextLine();
        System.out.print("Введите адрес салона: ");
        String address = in.nextLine();
        Showroom salon = new Showroom(id, name, address);
        if (showroomDAO.addShowroom(salon) == salon) {
            System.out.println("Салон успешно добавлен.");
        } else {
            System.out.println("Возникла ошибка добавления.");
        }
    }

    // 4
    private void relocateEmployee() {
        System.out.print("Введите ID сотрудника: ");
        Integer empID = Integer.valueOf(in.nextLine());
        if (employeeDAO.getEmployeeById(empID) == null) {
            throw new NullPointerException("Сотрудник не найден");
        }
        System.out.print("Введите ID салона: ");
        String salonID = in.nextLine();
        Showroom showroom = showroomDAO.findShowroomByID(salonID);
        employeeDAO.updateEmployeesShowroom(empID, showroom);
        System.out.println("Сотрудник переведен в другой салон");
    }

    // 5
    private void deleteShowroom() {
        System.out.print("Введите ID салона: ");
        String id = in.nextLine();
        showroomDAO.deleteShowroom(id);
        System.out.println("Салон удален.");
    }

    // 6
    private void findEmployeeByID() {
        System.out.print("Введите ID сотрудника: ");
        Integer empID = Integer.valueOf(in.nextLine());
        Employee emp = employeeDAO.getEmployeeById(empID);
        if (emp == null) {
            throw new NullPointerException("Сотрудник не найден");
        }
        System.out.println(emp);
    }

    // 7
    private void addEmployee() {
        System.out.print("Введите имя сотрудника: ");
        String name = in.nextLine();
        System.out.print("Введите должность: ");
        String position = in.nextLine();
        System.out.print("Введите стаж (лет): ");
        Integer experience = Integer.valueOf(in.nextLine());
        for (Showroom s : showroomDAO.getShowrooms()) {
            System.out.println(s);
        }
        System.out.print("Введите ID салона: ");
        String salonID = in.nextLine();
        Showroom showroom = showroomDAO.findShowroomByID(salonID);
        employeeDAO.addEmployee(new Employee(name, position, experience, showroom));
        System.out.println("Сотрудник успешно добавлен");
    }

    // 8
    private void deleteEmployee() {
        System.out.print("Введите ID сотрудника: ");
        Integer empID = Integer.valueOf(in.nextLine());
        if (employeeDAO.getEmployeeById(empID) == null) {
            throw new NullPointerException("Сотрудник не найден");
        }
        employeeDAO.removeEmployee(empID);
        System.out.println("Сотрудник успешно удален");
    }

    // 9
    private void getUsers() {
        for (User user : userDAO.getUsers()) {
            System.out.println(user);
        }
    }

    // 10
    private void getUserByUsername() {
        System.out.print("Введите username: ");
        String username = in.nextLine();
        System.out.println(userDAO.getUserByUsername(username));
    }

    // 11
    private void addUser() {
        seeAllShowrooms();
        System.out.print("Введите ID сотрудника, которому добавляем аккаунт: ");
        Integer empID = Integer.valueOf(in.nextLine());
        Employee emp = employeeDAO.getEmployeeById(empID);
        if (emp == null) {
            throw new NullPointerException("Сотрудник не найден");
        }
        System.out.print("Введите username: ");
        String username = in.nextLine();
        System.out.print("Введите password: ");
        String password = in.nextLine();
        User user = new User(empID, username, password);
        user.setEmployee(emp);
        System.out.println("Юзер " + userDAO.addUser(user) + " успешно добавлен");
    }

    // 12
    private void deleteUser() {
        System.out.print("Введите ID аккаунта: ");
        Integer userID = Integer.valueOf(in.nextLine());
        if (userDAO.getUserByID(userID) == null) {
            throw new NullPointerException("Юзер не найден");
        }
        userDAO.removeUser(userID);
        System.out.println("Юзер успешно удален");
    }

    // 13
    private void findShowroomsByCity() {
        System.out.print("Введите город: ");
        String city = in.nextLine();
        List<Showroom> salons = showroomDAO.findShowroomsByCity(city);
        if (salons.isEmpty()) {
            throw new NullPointerException("Салонов в этом городе нет");
        }
        salons.forEach(System.out::println);
    }

    // 14
    private void findByPosition() {
        System.out.print("Введите должность: ");
        String position = in.nextLine();
        List<Employee> emps = employeeDAO.findByPosition(position);
        if (emps.isEmpty()) {
            throw new NullPointerException("Сотрудников с такой должностью нет");
        }
        emps.forEach(System.out::println);
    }

    // 15
    private void findByExperienceAtLeast() {
        System.out.print("Введите минимальный стаж (лет): ");
        Integer experience = Integer.valueOf(in.nextLine());
        List<Employee> emps = employeeDAO.findByExperienceAtLeastCriteria(experience);
        if (emps.isEmpty()) {
            throw new NullPointerException("Сотрудников с таким стажем нет");
        }
        emps.forEach(System.out::println);
    }
}