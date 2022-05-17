import com.goit.javacore5.feature.human.HumanService;
import com.goit.javacore5.feature.storage.DatabaseInitService;
import com.goit.javacore5.feature.storage.Storage;

import java.sql.SQLException;
import java.time.LocalDate;

public class App {
    public static void main(String[] args) throws SQLException {
        Storage storage = Storage.getInstance();

        new DatabaseInitService().initDb(storage);

        HumanService humanService = new HumanService(storage);

        humanService.printHumanInfo(2);

        //humanService.createNewHuman("Johny", LocalDate.now().minusMonths(1));

        //humanService.printHumanIds();
    }
}
