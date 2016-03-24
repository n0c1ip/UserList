package crudFiles;

import com.opencsv.CSVReader;
import crudDB.DepartmentService;
import crudDB.LocationService;
import crudDB.PcService;
import crudDB.UserService;
import objects.*;

import javax.persistence.NoResultException;
import java.io.*;

public final class ImportCSVService {

    private ImportCSVService() {}

    /**
     * Loading Users from csv file to selected table
     * <p>
     * CSV file format:
     * Last Name; First Name; Middle Name; Location; Department Name; Position; PC name; Login; Password; E-Mail
     * @param organization organization to load Users
     * @param fileInputStream - csv data input stream
     * @param delimiter - delimeter used in csv
     * @throws FileNotFoundException
     * @throws UnsupportedEncodingException
     * @throws IOException
     */
    public static void loadUsersFromCSV(Organization organization, InputStream fileInputStream, char delimiter) throws IOException {
        try (CSVReader reader = new CSVReader(new InputStreamReader(fileInputStream, "windows-1251"), delimiter)) {
            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                if (nextLine.length == 10) {
                    User userToAdd = new User();

                    userToAdd.setLastName(nextLine[0]);
                    userToAdd.setFirstName(nextLine[1]);
                    userToAdd.setMiddleName(nextLine[2]);
                    Location location = new Location(nextLine[3]);
                    try {
                        location = LocationService.getByName(nextLine[3]);
                    } catch (NoResultException e) {
                        location = LocationService.add(location);
                    } finally {
                        userToAdd.setLocation(location);
                    }
                    Department department = new Department(nextLine[4]);
                    try {
                        department = DepartmentService.getByName(nextLine[4]);
                    } catch (NoResultException e) {
                        department.setOrganization(organization);
                        department = DepartmentService.add(department);
                    } finally {
                        userToAdd.setDepartment(department);
                    }
                    userToAdd.setPosition(nextLine[5]);
                    Pc newPc = new Pc(nextLine[6]);
                    newPc = PcService.add(newPc);
                    userToAdd.setPc(newPc);
                    userToAdd.setLogin(nextLine[7]);
                    userToAdd.setPassword(nextLine[8]);
                    userToAdd.setMail(nextLine[9]);

                    UserService.add(userToAdd);
                }
            }
            reader.close();
        }
    }

}
