import service.AddressService;

import java.time.LocalDate;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        scanner.useDelimiter("\n");
        AddressService addressService = new AddressService();

        while(true) {
            printMenu();
            int userInput = scanner.nextInt();
            switch (userInput) {
                case 1:
                    System.out.println("Введите дату в формате ГГГГ-ММ-ДД");
                    LocalDate date = LocalDate.parse(scanner.next());
                    System.out.println("Введите идентификаторы через запятую");
                    List<Long> ids = Arrays.stream(scanner.next()
                            .split(","))
                            .map(String::trim)
                            .map(Long::parseLong)
                            .collect(Collectors.toList());
                    addressService.getAddressObjectsByDate(date, ids);
                    break;
                case 2:
                    System.out.println("Введите тип адреса");
                    String text = scanner.next().trim();
                    addressService.getAddressObjectsHierarchy(text);
                    break;
                case 22132:
                    System.out.println("Программа завершена");
                    scanner.close();
                    return;
                default:
                    System.out.println("Вы ввели неверную команду!");
            }
        }
    }

    private static void printMenu() {
        System.out.println("Выберите команду: ");
        System.out.println("1 - Вывести описание адресов за переданную дату");
        System.out.println("2 - Вывести актуальные полные адреса");
    }
}