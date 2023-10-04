import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.io.File;

public class collections {
    public static void main(String[] args) throws Exception{
        //String filename = "Gettysburg.txt";
        File file = new File("/Users/hellenmarjoriewanyana/Desktop/Data Structures/Gettysburg_Address.txt");

        BufferedReader br = new BufferedReader(new FileReader(file));

        ArrayList<String> list = new ArrayList<>();
        String line = br.readLine();

        while(line != null)
        {
            System.out.print(line);

        }

    }
}