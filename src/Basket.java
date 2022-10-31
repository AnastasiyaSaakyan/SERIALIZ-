import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public void setGoods(List<String> goods) {
        this.goods = goods;
    }

    public void setPrice(List<Integer> price) {
        this.price = price;
    }

    public void setAllGoods(Map<Integer, Integer> allGoods) {
        this.allGoods = allGoods;
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

    public void saveJSon(File textFile) {
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

    public void saveTxt(File textFile) throws IOException {
        try (PrintWriter out = new PrintWriter(textFile);) {
            for (int i = 0; i < getGoods().size(); i++) {
                if (getAllGoods().get(i) != null) {
                    String temp = getGoods().get(i) + " " + getAllGoods().get(i) +
                            " " + getPrice().get(i);

                    out.print(temp);
                    out.print('\n');
                } else {
                    String temp = getGoods().get(i) + " " + 0 + " " + getPrice().get(i);
                    out.print(temp);
                    out.print('\n');

                }
            }
        }
        System.out.println("Данные сохранены");

    }

    public void loadFromJSonFile(File textFile) throws RuntimeException {
        try (Reader reader = new FileReader(textFile)) {
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            Basket temp = gson.fromJson(reader, Basket.class);
            this.allGoods = temp.allGoods;
            System.out.println(temp);
            System.out.println("Данные загружены");
        } catch (Exception e) {

            System.out.println("Файл не найден");
        }
    }


    public Basket loadFromTxtFile(File textFile) {

        File file = new File(textFile.toURI());
        try (FileReader fr = new FileReader(file);) {
            BufferedReader reader = new BufferedReader(fr);
            String line = reader.readLine();
            int i = 0;
            while (line != null) {
                System.out.println(line);
                String[] pars = line.split(" ");
                this.allGoods.put(i, Integer.valueOf(pars[1]));
                i++;
                line = reader.readLine();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
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