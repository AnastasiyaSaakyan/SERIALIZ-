import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        Basket cart = new Basket(List.of("Хлеб", "Яблоки", "Масло", "Молоко", "Яйца"), List.of(30, 90, 200, 100, 250));
        System.out.println("Список доступных товаров: ");
        for (int i = 0; i < cart.getGoods().size(); i++) {
            System.out.println((i + 1) + ". " + cart.getGoods().get(i) + " по цене: " + cart.getPrice().get(i) + " руб/шт");
        }
        Scanner scanner = new Scanner(System.in);
        int costCart = 0;
        while (true) {

            System.out.println("Для завершения и расчета стоимости покупок нажмите end");
            String input = scanner.nextLine();

            if ("end".equals(input)) {
                for (int i = 0; i < cart.getAllGoods().size(); i++) {
                    if (cart.getAllGoods().get(i) == null) {
                        costCart = cart.getAllGoods().get(i) * cart.getPrice().get(i);
                    } else {
                        costCart += cart.getAllGoods().get(i) * cart.getPrice().get(i);
                    }
                }
                cart.printCart();
                System.out.println("Итого товаров  на сумму: " + costCart + " руб.");
                break;
            }
            if ("load".equals(input)) {
                cart.loadFromTxtFile(new File("basket.txt"));
                System.out.println("Информация загружена");
                continue;
            }
            String[] parse = input.split(" ");
            if (parse.length != 2) {
                System.out.println("Необходимо ввести 2 значения: номер товара и его количество. Вы ввели: " + parse.length);
            } else {
                try {
                    int numProduct = Integer.parseInt(parse[0]) - 1;
                    int amount = Integer.parseInt(parse[1]);
                    cart.addToCart(numProduct, amount);
                    cart.saveTxt(new File("basket.txt"));
                } catch (NumberFormatException e) {
                    System.out.println("Введены некорректные данные: необходимо ввести числовые значения");
                }
            }
        }
    }
}
