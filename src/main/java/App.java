import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import domain.Cause;
import lombok.extern.slf4j.Slf4j;
import view.CauseModel;
import view.MainFrame;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.stream.Stream;

/**
 * Create by Aviv POS
 * User: olgats
 * Date: 26/07/2020
 * Time: 09:36
 */
@Slf4j
public class App {
    public static void main(String[] args) {

        boolean fullScreen=Stream.of(args).filter("full"::equalsIgnoreCase).findFirst().orElse(null)!=null;
        String imagePath=Stream.of(args).filter(e->e.startsWith("img=")).findFirst().orElse(null);
        if(imagePath!=null){
            imagePath=imagePath.split("=")[1];
        }else{
            imagePath="./images";
        }
        CauseModel model = new CauseModel();


        ArrayList<Cause> causes;
        String data = "";
        InputStream is=App.class.getClassLoader().getResourceAsStream("data.json");
       // InputStream is=App.class.getResourceAsStream("data.json"));
        if (is != null) {
            try (BufferedReader input = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
                data = input.readLine();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Type listType = new TypeToken<ArrayList<Cause>>() {}.getType();
        causes = new Gson().fromJson(data, listType);
        ArrayList<String> columns = new ArrayList<>();

        causes.forEach(e -> {
            e.initColors(0.1f);
            if (columns.isEmpty())
                columns.addAll(e.getVerticalLocation());

        });


        model.setData(columns, causes);
        installFonts();
        MainFrame mainFrame = new MainFrame();
        mainFrame.setImagePath(imagePath);
        mainFrame.show(model,fullScreen);

    }

    private static void installFonts() {
        try {

            InputStream is=App.class.getClassLoader().getResourceAsStream("CaviarDreams.ttf");
            GraphicsEnvironment ge =
                    GraphicsEnvironment.getLocalGraphicsEnvironment();
            if (is != null && !ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, is))) {
                System.err.println("Cannot register font CaviarDreams.ttf");
            }
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }

        try {

            InputStream is=App.class.getClassLoader().getResourceAsStream("CaviarDreams_Bold.ttf");
            GraphicsEnvironment ge =
                    GraphicsEnvironment.getLocalGraphicsEnvironment();
            if (is != null && !ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, is))) {
                System.err.println("Cannot register font CaviarDreams_Bold.ttf ");
            }
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }
    }
}
