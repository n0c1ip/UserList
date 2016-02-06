import controllers.ImportCSVController;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;

//todo: use mockito for UnsupportedEncodingException & IOException tests
public class ImportCsvTest {

    @Test(expected = FileNotFoundException.class)
    public void shouldThrowFileNotFoundException() throws IOException {
        ImportCSVController controller = new ImportCSVController();
        controller.loadUsersFromCSV("TableName","thereisnocsvfile",';');
    }

}
