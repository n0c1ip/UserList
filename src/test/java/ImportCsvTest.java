import org.junit.Test;
import util.ListUtil;

import java.io.FileNotFoundException;
import java.io.IOException;

public class ImportCsvTest {

    @Test(expected = FileNotFoundException.class)
    public void shouldThrowFileNotFoundException() throws IOException {
        ListUtil.loadUsersFromCSV("TableName","C:/thereisnocsvfile.csv",';');
    }
}
