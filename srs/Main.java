import java.util.Arrays;
import java.util.Scanner;

/**
 * @author Aleksey Anikeev aka AgentChe
 * Date of creation: 22.06.2022
 */
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] products = {"Черешня", "Яблоки", "Персики"}; // список доступных товаров
        String[] productsDiscount = {"Черешня", "Персики"}; // акционный товар
        int[] prices = {350, 99, 170}; // цена товара
        int[] productCountList = new int[products.length]; //количество товара в корзине
        boolean[] isSelected = new boolean[products.length]; // для поиска выбранного товара
        int sumProducts = 0; // сумма в корзине
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
                sumProducts = getShareAmount(products, productsDiscount, prices, productCountList, isSelected, sumProducts);
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
                if (inputValidation(products, numberOfProduct)) {
                    continue;
                }
            } catch (NumberFormatException e) {
                System.out.println("Не корректный ввод, необходимо вводить только цифры!");
                continue;
            }
            if (productCount == 0) {
                sumProducts -= prices[numberOfProduct - 1] * productCountList[numberOfProduct - 1];
                productCountList[numberOfProduct - 1] = 0;
            }
            if ((productCountList[numberOfProduct - 1] + productCount) < 0) {
                sumProducts -= prices[numberOfProduct - 1] * productCountList[numberOfProduct - 1];
                productCountList[numberOfProduct - 1] = 0;
            } else {
                productCountList[numberOfProduct - 1] += productCount;
                sumProducts += prices[numberOfProduct - 1] * productCount;
            }
            isSelected[numberOfProduct - 1] = productCountList[numberOfProduct - 1] > 0;
        }
        System.out.println("Ваша корзина:");
        shoppingListDisplay(products, productsDiscount, prices, productCountList, isSelected);
        System.out.printf("Итого %d руб\n", sumProducts);
    }

    /**
     * Метод выводит на экран содержимое корзины покупателя с учетом акционного товара
     *
     * @param products         список доступных товаров
     * @param productsDiscount список акционного товара
     * @param prices           список цен на товар
     * @param productCountList количество приобретенных товаров
     * @param isSelected       список приобретенных товаров
     */
    private static void shoppingListDisplay(String[] products, String[] productsDiscount, int[] prices, int[] productCountList, boolean[] isSelected) {
        for (int i = 0; i < products.length; i++) {
            if (isSelected[i]) {
                if (Arrays.asList(productsDiscount).contains(products[i])) {
                    System.out.printf("%s %d шт %d руб/шт %d руб в сумме с учетов товара по акции 3 по цене 2\n", products[i],
                            productCountList[i], prices[i], prices[i] * (productCountList[i] - productCountList[i] / 3));
                } else {
                    System.out.printf("%s %d шт %d руб/шт %d руб в сумме\n", products[i],
                            productCountList[i], prices[i], prices[i] * productCountList[i]);
                }
            }
        }
    }

    /**
     * Метод обработки акционного товара (3 по цене 2)
     *
     * @param products         список доступных товаров
     * @param productsDiscount список акционного товара
     * @param prices           список цен на товар
     * @param productCountList количество приобретенных товаров
     * @param isSelected       список приобретенных товаров
     * @param sumProducts      сумма товара
     * @return сумма товара с учетом акции
     */
    private static int getShareAmount(String[] products, String[] productsDiscount, int[] prices, int[] productCountList, boolean[] isSelected, int sumProducts) {
        for (int i = 0; i < products.length; i++) {
            if (isSelected[i]) {
                if (Arrays.asList(productsDiscount).contains(products[i])) {
                    int countDisc = productCountList[i] / 3;
                    sumProducts -= prices[i] * countDisc;
                }
            }
        }
        return sumProducts;
    }

    private static boolean inputValidation(String[] products, int numberOfProduct) {
        if (numberOfProduct == 0 || numberOfProduct > products.length) {
            System.out.println("Не верный выбор продукта, необходимо выбрать корректный продукт из списка");
            return true;
        }
        return false;
    }
}
