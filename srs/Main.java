import java.util.Scanner;

/**
 * @author Aleksey Anikeev aka AgentChe
 * Date of creation: 22.06.2022
 */
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] products = {"Черешня", "Яблоки", "Персики"};
        int[] prices = {350, 99, 170};
        int[] productCountList = new int[products.length];
        boolean[] isSelected = new boolean[products.length];
        int sumProducts = 0;
        while (true) {
            int numberOfProduct;
            int productCount;
            System.out.println("Введи номер товара и его количество.");
            System.out.println("Список возможных товаров для покупки:");
            for (int i = 0; i < products.length; i++) {
                System.out.printf("%d. %s %d руб/шт\n", i + 1, products[i], prices[i]);
            }
            String input = scanner.nextLine();
            if ("end".equals(input)) {
                break;
            }
            try {
                String[] options = input.split(" ");
                if (options.length < 2) {
                    System.out.println("Необходимо ввести номер продукта и его количество!\n" +
                            "Вы ввели: " + input);
                    continue;
                }
                numberOfProduct = Integer.parseInt(options[0]);
                productCount = Integer.parseInt(options[1]);
                if (inputValidation(products, numberOfProduct, productCount)) {
                    continue;
                }
            } catch (NumberFormatException e) {
                System.out.println("Не корректный ввод, необходимо вводить только цифры!");
                continue;
            }
            productCountList[numberOfProduct - 1] += productCount;
            sumProducts += prices[numberOfProduct - 1] * productCount;
            isSelected[numberOfProduct - 1] = true;
        }
        System.out.println("Ваша корзина:");
        for (int i = 0; i < products.length; i++) {
            if (isSelected[i]) {
                System.out.printf("%s %d шт %d руб/шт %d руб в сумме\n", products[i], productCountList[i], prices[i], prices[i] * productCountList[i]);
            }
        }
        System.out.printf("Итого %d руб\n", sumProducts);
    }

    private static boolean inputValidation(String[] products, int numberOfProduct, int productCount) {
        if (numberOfProduct == 0 || numberOfProduct > products.length) {
            System.out.println("Не верный выбор продукта, необходимо выбрать корректный продукт из списка");
            return true;
        }
        if (productCount <= 0) {
            System.out.println("Введено не корректное количество продуктов!");
            return true;
        }
        return false;
    }
}
