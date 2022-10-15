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
                System.out.println((i + 1) + ". " + getGoods().get(i) + ": " + (getGoods().get(i))
                        + " шт " + " x " + getPrice().get(i) + " руб/шт " + " = "
                        + getAllGoods().get(i) * getPrice().get(i) + " руб. ");
            }
        }
    }

    public void saveTxt(File textFile) throws IOException {
        try (PrintWriter out = new PrintWriter(textFile)) {
            for (int i = 0; i < getGoods().size(); i++) {
                if (getAllGoods().get(i) != null) {
                    String tmp = getGoods().get(i) + ": " + getAllGoods().get(i) + " шт " + " по цене " + getPrice().get(i) + " руб/шт ";
                    out.print(tmp);
                    out.print('\n');
                }
            }
        }
        System.out.println("Информация успешно сохранена");
    }

    public Basket() {
    }

    public static Basket loadFromTxtFile(File textFile) {
        Basket cart = new Basket();
        File file = new File(textFile.toURI());
        try (FileReader fileReader = new FileReader(file);) {
            BufferedReader reader = new BufferedReader(fileReader);
            String line = reader.readLine();
            int i = 0;
            while (line != null) {
                System.out.println(line);
                String[] parse = line.split(" ");
                cart.getAllGoods().put(i, Integer.valueOf(parse[1]));
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
}