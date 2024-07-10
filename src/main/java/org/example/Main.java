package org.example;

import com.aimiko.task5.persistence.entity.User;
import com.aimiko.task5.persistence.exeption.FileIOException;
import com.aimiko.task5.persistence.repository.TxtPathFactory;
import com.aimiko.task5.persistence.repository.UserRepository;
import com.aimiko.task5.persistence.repository.UserTxtRepository;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    static UserRepository userTxtRepository = new UserTxtRepository();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nОберіть операцію:");
            System.out.println("1. Введення даних");
            System.out.println("2. Редагування даних співробітника корпорації");
            System.out.println("3. Видалення співробітника корпорації");
            System.out.println("4. Пошук співробітника корпорації по прізвищу");
            System.out.println("5. Вивід інформації про всіх співробітників");
            System.out.println("6. Вивід інформації про співробітників, вказання віку");
            System.out.println("7. Вивід інформації про співробітників, вказання прізвища які починаються на вказану букву");
            System.out.println("8. Збереження записів");
            System.out.println("0. Вихід");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    addEmployee(scanner);
                    break;
                case 2:
                    editEmployee(scanner);
                    break;
                case 3:
                    deleteEmployee( scanner);
                    break;
                case 4:
                    findEmployeeByLastName(scanner);
                    break;
                case 5:
                    printAllEmployees();
                    break;
                case 6:
                    printEmployeesByAge(scanner);
                    break;
                case 7:
                    printEmployeesWithLastNameStartingWith( scanner);
                    break;
                case 8:
                    saveChanges();
                    break;
                case 0:
                    userTxtRepository.saveChanges();
                    System.out.println("Завершення програми.");
                    return;
                default:
                    System.out.println("Невідомий вибір. Спробуйте ще раз.");
            }
        }


    }

    public static void addEmployee(Scanner scanner) {
        System.out.println("Введіть ім'я та прізвище співробітника:");
        String name = scanner.nextLine();

        System.out.println("Введіть вік співробітника:");
        int age = scanner.nextInt();
        scanner.nextLine();

        userTxtRepository.add(new User(UUID.randomUUID(),name, age));
        System.out.println("Співробітник доданий.");
    }

    public static void editEmployee(Scanner scanner) {
        System.out.println("Введіть прізвище співробітника для редагування:");
        String lastName = scanner.nextLine();
        User user = userTxtRepository.findByName(lastName).stream().findFirst().orElse(null);
        if (user != null) {
            System.out.println("Введіть нове ім'я та прізвище співробітника:");
            String newName = scanner.nextLine();

            System.out.println("Введіть новий вік співробітника:");
            int newAge = scanner.nextInt();
            scanner.nextLine();
            user = new User(user.id(), newName, newAge);
            userTxtRepository.add(user);
        } else {
            System.out.println("Користувача з таким прізвищем не знайдено");
        }
    }

    public static void deleteEmployee(Scanner scanner) {
        System.out.println("Введіть прізвище співробітника для видалення:");
        String lastName = scanner.nextLine();
        User user = userTxtRepository.findByName(lastName).stream().findFirst().orElse(null);
        if (user != null) {
            userTxtRepository.remove(user);
        } else {
            System.out.println("Користувача з таким прізвищем не знайдено");
        }
    }

    public static void findEmployeeByLastName(Scanner scanner) {
        System.out.println("Введіть прізвище співробітника для пошуку:");
        String lastName = scanner.nextLine();
        User user = userTxtRepository.findByName(lastName).stream().findFirst().orElse(null);
        if (user != null) {
            System.out.println(user);
        } else {
            System.out.println("Користувача з таким прізвищем не знайдено");
        }
    }

    public static void printAllEmployees() {
        Set<User> users = userTxtRepository.findAll();
        for (User user : users)
        {
            System.out.println(user);
        }
    }

    public static void printEmployeesByAge(Scanner scanner) {
        System.out.println("Введіть вік співробітників для пошуку:");
        int age = scanner.nextInt();
        Set<User> users = userTxtRepository.findByAge(age);
        if (users != null) {
            for (User user : users) {
                System.out.println(user);
            }

        } else {
            System.out.println("Користувачів з таким віком не знайдено");
        }
    }

    public static void printEmployeesWithLastNameStartingWith(Scanner scanner) {
        System.out.println("Введіть першу бувку прізвищів співробітників для пошуку:");
        String name = scanner.nextLine();
        Set<User> users = userTxtRepository.findByName(name);
        if (users != null) {
            for (User user : users) {
                System.out.println(user);
            }
        } else {
            System.out.println("Користувача з таким початком прізвища не знайдено");
        }
    }

    public static void saveChanges(){
        userTxtRepository.saveChanges();
        System.out.println("Успішно збережено");
    }

}