import com.goit.javacore5.feature.human.HumanGenerator;
import com.goit.javacore5.feature.human.HumanService;
import com.goit.javacore5.feature.human.HumanServiceV2;
import com.goit.javacore5.feature.storage.DatabaseInitService;
import com.goit.javacore5.feature.storage.Storage;

import java.sql.SQLException;
import java.time.LocalDate;

public class App {
    public static void main(String[] args) throws SQLException {
        Storage storage = Storage.getInstance();

        new DatabaseInitService().initDb(storage);

        HumanServiceV2 humanService = new HumanServiceV2(storage);

        //1) Вставка 100 000 по одному
        //2) Вставка 1000 000 пакетами по 10
        //3) Вставка 100 000 пакетами по 100
        //4) Вставка 100 000 пакетами по 1000

        HumanGenerator generator = new HumanGenerator();
        String[] names = generator.generateNames(100000);
        LocalDate[] dates = generator.generateDates(100000);

        long start = System.currentTimeMillis();
        for (int i = 0; i < names.length; i++) {
            humanService.createNewHuman(names[i], dates[i]);
        }
        long duration = System.currentTimeMillis() - start;
        System.out.println("duration = " + duration);
    }
}
