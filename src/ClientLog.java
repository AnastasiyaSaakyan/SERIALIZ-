import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;

public class ClientLog {

    private int productNum;
    private int amount;

    private List<Integer> dataNum = new LinkedList<>();
    private List<Integer> dataAmount = new LinkedList<>();

    public void log(int productNum, int amount) {
        dataNum.add(productNum);
        dataAmount.add(amount);
    }

    public void exportAsCSV(File txtFile) {
        try (PrintWriter writer = new PrintWriter(new File(txtFile.toURI()))) {
            writer.append("productNum,amount" + "\n");
            for (int i = 0; i < dataNum.size(); i++) {
                writer.append(dataNum.get(i) + "," + dataAmount.get(i) + "\n");

            }
            System.out.println("File export");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}