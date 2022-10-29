import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        Basket cart = new Basket(
                List.of("Хлеб", "Яблоки", "Масло"),
                List.of(30, 90, 200));
        ClientLog clientLog = new ClientLog();

        System.out.println(cart);
        Basket.loadFromTxtFile(new File("basket.json"));

        int costCart = 0;

        Scanner scan = new Scanner(System.in);

        System.out.println("Список доступных товаров:");
        for (int i = 0; i < cart.getGoods().size(); i++) {
            System.out.println((i + 1) + ". " + cart.getGoods().get(i) + " по цене: " + cart.getPrice().get(i) + " руб/шт");
        }

        while (true) {
            System.out.println("Выберите товар и количество товара, введите `end`для завершения покупки или " +
                    "'load' для загрузки списка покупок");
            String input = scan.nextLine();
            if ("end".equals(input)) {

                for (int i = 0; i < cart.getAllGoods().size(); i++) {
                    if (cart.getAllGoods().get(i) != null) {
                        costCart += cart.getAllGoods().get(i) * cart.getPrice().get(i);
                    }
                }
                System.out.println(cart);

                cart.printCart();
                System.out.println("Итого: " + costCart + " руб");
                clientLog.exportAsCSV(new File("log.csv"));
                break;
            }
            if ("load".equals(input)) {
                cart.loadFromTxtFile(new File("basket.json"));
                System.out.println(cart);
                System.out.println("Информация загружена ");
                continue;
            }
            String[] pars = input.split(" ");
            int numProduct = Integer.parseInt(pars[0]) - 1;
            int amount = Integer.parseInt(pars[1]);
            clientLog.log(numProduct, amount);

            cart.addToCart(numProduct, amount);
            cart.saveTxt(new File("basket.json"));
        }
    }
}
