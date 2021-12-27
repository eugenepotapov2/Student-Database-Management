import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class Database {
    static final String DATABASE_URL = "jdbc:sqlserver://localhost";
    static final String USERNAME = "sa";
    static final String PASSWORD = "EuPo6412";
    private static boolean connectedToDatabase = false;
    Connection connection = null;
    Map<Character, Integer> grades = new HashMap<Character, Integer>();

    public void connectToDatabase() throws SQLException {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connection = java.sql.DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD);
            if (connection != null) {
                System.out.println("Connection Successful!");
                connectedToDatabase = true;
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void dropTables() throws SQLException {
        String dropTables = "IF EXISTS (SELECT 1 FROM sys.tables WHERE NAME = 'GradeAggregate') DROP TABLE GradeAggregate " +
                "IF EXISTS (SELECT 1 FROM sys.tables WHERE NAME = 'Classes') DROP TABLE Classes " +
                "IF EXISTS (SELECT 1 FROM sys.tables WHERE NAME = 'Students') DROP TABLE Students " +
                "IF EXISTS (SELECT 1 FROM sys.tables WHERE NAME = 'Courses') DROP TABLE Courses " +
                "IF EXISTS (SELECT 1 FROM sys.tables WHERE NAME = 'Schedule') DROP TABLE Schedule ";
        PreparedStatement psDropTables = connection.prepareStatement(dropTables);
        try {
            psDropTables.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public void createTableSchedule() throws SQLException {
        String createSchedule = "CREATE TABLE Schedule( courseId CHAR(12) NOT NULL UNIQUE, " +
                "sectionNumber VARCHAR(8) NOT NULL UNIQUE, title VARCHAR(64), year INT, semester CHAR(6), " +
                "instructor VARCHAR(24), department CHAR(16), program VARCHAR(48) PRIMARY KEY (courseId, sectionNumber))";
        PreparedStatement psCreateSchedule = connection.prepareStatement(createSchedule);
        try {
            psCreateSchedule.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void bulkTableSchedule() throws SQLException {
        String bulkSchedule = "BULK INSERT Schedule FROM '/Class_Schedule_Computer_Science_Department_Spring_2021.txt' " +
                "WITH (FIELDTERMINATOR = '\t',    ROWTERMINATOR = '\n',    FIRSTROW = 2)";
        PreparedStatement psBulkSchedule = connection.prepareStatement(bulkSchedule);
        try {
            psBulkSchedule.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void createTableCourses() throws SQLException {
        String createCourses = "CREATE TABLE Courses( courseId CHAR(12) PRIMARY KEY REFERENCES Schedule (courseId), " +
                "title VARCHAR(64), department CHAR(16), program VARCHAR(48)); INSERT INTO Courses SELECT courseId, title," +
                "department, program FROM Schedule";
        PreparedStatement psCreateCourses = connection.prepareStatement(createCourses);
        try {
            psCreateCourses.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void createTableStudents() throws SQLException {
        String createStudents = "CREATE TABLE Students(empId INT PRIMARY KEY," +
                "FirstName VARCHAR(16), LastName VARCHAR(16), email VARCHAR(32)," +
                "gender CHAR CHECK (gender = 'F' OR gender = 'M' OR gender ='U'))";
        PreparedStatement psCreateStudents = connection.prepareStatement(createStudents);
        try {
            psCreateStudents.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void enterStudent(String data) throws SQLException {
        String insertStudents = "INSERT INTO Students VALUES (" + data + ")";
        PreparedStatement psInsertStudents = connection.prepareStatement(insertStudents);
        try {
            psInsertStudents.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void dropStudent(int empId, String firstName, String lastName, String email, String gender) throws SQLException {
        String insertStudents = "DELETE FROM Students SELECT" + empId + "as empId, " +
                firstName + " as firstName," + lastName + "as lastName," + email + "as email," + gender + "as gender";
        PreparedStatement psInsertStudents = connection.prepareStatement(insertStudents);
        try {
            psInsertStudents.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void createTableClasses() throws SQLException {
        String createClasses = "CREATE TABLE Classes( courseId CHAR(12) REFERENCES Schedule(courseId), empId INT REFERENCES Students(empId)," +
                " sectionNumber VARCHAR(8) REFERENCES Schedule(sectionNumber)," +
                " PRIMARY KEY(courseId, empId, sectionNumber), year INT," +
                "semester CHAR(6)," +
                "grade CHAR CHECK (grade = 'A' OR grade = 'B' OR grade ='C' OR grade = 'D' OR grade ='F' OR grade = 'W'))";
        PreparedStatement psCreateClasses = connection.prepareStatement(createClasses);
        try {
            psCreateClasses.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void enterClasses(int empId, String sectionNumber, String grade) throws SQLException {
        String enterClasses = "INSERT INTO Classes SELECT Schedule.courseId, " + empId + " as empId, " +
                "Schedule.sectionNumber, Schedule.year, Schedule.semester, '" + grade + "' as grade " +
                "FROM Schedule " +
                "WHERE Schedule.sectionNumber = '" + sectionNumber + "'";
        PreparedStatement psEnterClasses = connection.prepareStatement(enterClasses);
        try {
            psEnterClasses.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void dropClass(int empId, String sectionNumber, String grade) throws SQLException {
        String enterClasses = "DELETE FROM Classes " +
                "WHERE empId = " + empId + " AND " +
                "sectionNumber = '" + sectionNumber + "' AND  " +
                "grade = '" + grade + "' ";
        PreparedStatement psEnterClasses = connection.prepareStatement(enterClasses);
        try {
            psEnterClasses.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void deleteGrade(String grade) throws SQLException {
        String enterClasses = "UPDATE Classes SET grade = NULL WHERE grade = '" + grade + "';";
        PreparedStatement psEnterClasses = connection.prepareStatement(enterClasses);
        try {
            psEnterClasses.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public Map<Character, Integer> mapAggregateGrades() throws SQLException {
        String dropMapAggregate = "IF EXISTS (SELECT 1 FROM sys.tables WHERE NAME = 'GradeAggregate') DROP TABLE GradeAggregate";
        PreparedStatement psDropMapAggregate = connection.prepareStatement(dropMapAggregate);
        try {
            psDropMapAggregate.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        String tableGradeAggregate = "CREATE TABLE GradeAggregate( grade CHAR PRIMARY KEY, numberStudents INT)";
        PreparedStatement psTableGradeAggregate = connection.prepareStatement(tableGradeAggregate);
        try {
            psTableGradeAggregate.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        String groupGradeAggregate = "INSERT INTO GradeAggregate SELECT grade, count(grade) FROM Classes WHERE grade IS NOT NULL GROUP BY grade";
        PreparedStatement psGroupGradeAggregate = connection.prepareStatement(groupGradeAggregate);
        psGroupGradeAggregate.executeUpdate();
        try {
            ResultSet RS = connection.prepareStatement("SELECT * FROM GradeAggregate").executeQuery();
            while (RS.next()) {
                grades.put(RS.getString("grade").charAt(0), RS.getInt("numberStudents"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return grades;
    }
}
