package crudFiles;

import com.opencsv.CSVReader;
import crudDB.DepartmentService;
import crudDB.UserService;
import objects.Department;
import objects.Location;
import objects.User;

import javax.persistence.NoResultException;
import java.io.*;

public class ImportCSVService {

    private ImportCSVService() {}

    /**
     * Loading Users from csv file to selected table
     * <p>
     * CSV file format:
     * Last Name; First Name; Middle Name; Department Name; Position; Login; Password; E-Mail
     * @param location location to load Users
     * @param fileInputStream - csv data input stream
     * @param delimiter - delimeter used in csv
     * @throws FileNotFoundException
     * @throws UnsupportedEncodingException
     * @throws IOException
     */
    public static void loadUsersFromCSV(Location location, InputStream fileInputStream, char delimiter) throws IOException {
        try (CSVReader reader = new CSVReader(new InputStreamReader(fileInputStream, "windows-1251"), delimiter);) {
            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                if (nextLine.length == 8) {
                    User userToAdd = new User();

                    userToAdd.setLastName(nextLine[0]);
                    userToAdd.setFirstName(nextLine[1]);
                    userToAdd.setMiddleName(nextLine[2]);
                    userToAdd.setLocation(location);
                    Department department = new Department(nextLine[3]);
                    try {
                        department = DepartmentService.getByName(nextLine[3]);
                    } catch (NoResultException e) {
                        department = DepartmentService.add(department);
                    } finally {
                        userToAdd.setDepartment(department);
                    }
                    userToAdd.setPosition(nextLine[4]);
                    userToAdd.setLogin(nextLine[5]);
                    userToAdd.setPassword(nextLine[6]);
                    userToAdd.setMail(nextLine[7]);
                    UserService.add(userToAdd);
                }
            }
            reader.close();
        }
    }

}
