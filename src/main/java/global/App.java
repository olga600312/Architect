package global;

import com.google.gson.Gson;
import domain.Cause;
import domain.HorizontalLocation;
import domain.LocationType;
import domain.VerticalLocation;
import lombok.extern.slf4j.Slf4j;
import org.yaml.snakeyaml.Yaml;
import view.CauseModel;
import view.MainFrame;

import java.awt.*;
import java.util.ArrayList;

/**
 * Create by Aviv POS
 * User: olgats
 * Date: 26/07/2020
 * Time: 09:36
 */
@Slf4j
public class App {
    public static void main(String[] args) {
        String conferenceRoom = "CONFERENCE ROOM";
        String office = "OFFICE";
        String pool = "POOL";


        CauseModel model=new CauseModel();
        ArrayList<String> columns=new ArrayList<>();
        columns.add(conferenceRoom);
        columns.add(office);
        columns.add(pool);

        ArrayList<Cause> causes=new ArrayList<>();


        ArrayList<Cause.Entry> arr=new ArrayList<>();
        LocationType workplace = LocationType.WORKPLACE;
        LocationType leisure = LocationType.LEISURE;
        workplace.setInitColor(new Color(14147553));
        leisure.setInitColor(new Color(0xE0C7DB));

        arr.add(Cause.Entry.builder().file("fileName").columnHeader(VerticalLocation.builder().name(conferenceRoom).type(workplace).build()).total(10).build());
        arr.add(Cause.Entry.builder().file("fileName").columnHeader(VerticalLocation.builder().name(office).type(workplace).build()).total(3).build());
        arr.add(Cause.Entry.builder().file("fileName").columnHeader(VerticalLocation.builder().name(pool).type(leisure).build()).total(2).build());
        causes.add(Cause.builder().rowHeader(HorizontalLocation.builder().name("RESTROOM").build()).data(arr).build());

        arr=new ArrayList<>();
        arr.add(Cause.Entry.builder().file("fileName").columnHeader(VerticalLocation.builder().name("CONFERENCE ROOM").type(workplace).build()).total(10).build());
        arr.add(Cause.Entry.builder().columnHeader(VerticalLocation.builder().name(office).type(workplace).build()).total(0).build());
        arr.add(Cause.Entry.builder().columnHeader(VerticalLocation.builder().name(pool).type(leisure).build()).total(14).build());
        causes.add(Cause.builder().rowHeader(HorizontalLocation.builder().name("HOTEL").build()).data(arr).build());

        causes.forEach(e->e.initColors(0.8f));
        Yaml yaml = new Yaml();
        String dump = yaml.dump(causes);
        System.out.println(dump);
        System.out.println(new Gson().toJson(causes));

        model.setData(columns,causes);
        MainFrame mainFrame=new MainFrame();
        mainFrame.show(model);

    }
}
