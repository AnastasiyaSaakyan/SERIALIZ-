import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Basket {
    private List<String> goods;
    private List<Integer> price;
    private Map<Integer, Integer> allGoods = new HashMap<>();

    public Basket(List<String> goods, List<Integer> price) {
        this.goods = goods;
        this.price = price;
    }

    List<String> getGoods() {
        return goods;
    }

    List<Integer> getPrice() {
        return price;
    }

    public Map<Integer, Integer> getAllGoods() {
        return allGoods;
    }

    public void addToCart(int numProduct, int amount) {
        if (allGoods.containsKey(numProduct)) {
            allGoods.put(numProduct, allGoods.get(numProduct) + amount);
        } else {
            allGoods.put(numProduct, amount);
        }
    }

    public void printCart() {
        for (int i = 0; i < getGoods().size(); i++) {
            if (getAllGoods().get(i) != null) {

                System.out.println(getGoods().get(i) + ": "
                        + (getAllGoods().get(i))
                        + " шт " + getPrice().get(i) + " руб/шт "
                        + getAllGoods().get(i) * getPrice().get(i) + " руб в сумме");
            }
        }
    }

    public void saveTxt(File textFile) throws IOException {
        try (Writer writer = new FileWriter(textFile)) {
            Gson gson = new Gson();
            Basket temp = new Basket(goods, price);
            temp.allGoods = this.allGoods;
            gson.toJson(temp, writer);
            System.out.println("Данные сохранены");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void loadFromTxtFile(File textFile) throws RuntimeException {
        try (Reader reader = new FileReader(textFile)) {
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            Basket temp = gson.fromJson(reader, Basket.class);
            System.out.println(temp);
            System.out.println("Данные загружены");
        } catch (Exception e) {
            System.out.println("Файл не найден");
            ;
        }
        System.out.println("Информация успешно сохранена");
    }


    @Override
    public String toString() {
        return "Cart{" +
                "goods=" + goods +
                ", price=" + price +
                ", allGoods=" + allGoods +
                '}';
    }
}