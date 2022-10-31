import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class Main {


    public static void main(String[] args) throws IOException {
        Boolean loadCon = true;
        String fileNameLoad = null;
        String fileFormatLoad = null;

        Boolean saveCon = true;
        String fileNameSave = null;
        String fileFormatSave = null;

        Boolean logCon = true;
        String fileNameLog = null;

        try {
            File fXmlFile = new File("shop.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);
            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("load");
            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    loadCon = Boolean.valueOf(eElement.getElementsByTagName("enabled")
                            .item(0).getTextContent());
                    fileNameLoad = eElement.getElementsByTagName("fileName")
                            .item(0).getTextContent();
                    fileFormatLoad = eElement.getElementsByTagName("format")
                            .item(0).getTextContent();
                }
            }
            NodeList nList2 = doc.getElementsByTagName("save");
            for (int temp = 0; temp < nList2.getLength(); temp++) {
                Node nNode = nList2.item(temp);

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    saveCon = Boolean.valueOf(eElement.getElementsByTagName("enabled")
                            .item(0).getTextContent());
                    fileNameSave = eElement.getElementsByTagName("fileName")
                            .item(0).getTextContent();
                    fileFormatSave = eElement.getElementsByTagName("format")
                            .item(0).getTextContent();
                }
            }
            NodeList nList3 = doc.getElementsByTagName("log");

            for (int temp = 0; temp < nList2.getLength(); temp++) {
                Node nNode = nList3.item(temp);

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    logCon = Boolean.valueOf(eElement.getElementsByTagName("enabled")
                            .item(0).getTextContent());
                    fileNameLog = eElement.getElementsByTagName("fileName")
                            .item(0).getTextContent();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        Basket cart = new Basket(
                List.of("Хлеб", "Яблоки", "Масло"),
                List.of(30, 90, 200));
        ClientLog clientLog = new ClientLog();

        System.out.println(cart);
        if (fileFormatLoad.equals("json") && loadCon) {
            cart.loadFromJSonFile(new File(fileNameLoad));
        } else if (loadCon) {
            cart.loadFromTxtFile(new File(fileNameLoad));
        }

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

                if (fileFormatSave.equals("json")) {
                    cart.saveJSon(new File(fileNameSave));
                } else {
                    cart.saveTxt(new File(fileNameSave));
                }

                if (logCon) {
                    clientLog.exportAsCSV(new File(fileNameLog));
                }
                break;


            }
            if ("load".equals(input)) {

                if (fileFormatLoad.equals("json")) {
                    cart.loadFromJSonFile(new File(fileNameLoad));
                } else {
                    cart.loadFromTxtFile(new File(fileNameLoad));
                }

                continue;
            }
            String[] pars = input.split(" ");
            int numProduct = Integer.parseInt(pars[0]) - 1;
            int amount = Integer.parseInt(pars[1]);
            clientLog.log(numProduct, amount);


            cart.addToCart(numProduct, amount);

            if (fileFormatSave.equals("json") && saveCon) {
                cart.saveJSon(new File(fileNameSave));
            } else if (saveCon) {
                cart.saveTxt(new File(fileNameSave));
            }
        }
    }
}