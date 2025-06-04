import java.sql.*;
import java.util.Scanner;

public class db {
    public static void main (String [] Args) throws SQLException
    {
        String username;
        //createAccount();
        username = login();
        User user = new User(username);

    }
    public static void createAccount () throws SQLException
    {
        //establishing connection to the database
        Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/user","root","5fdpman");
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT username FROM USER");

        //declaring variables
        String username, password;
        Scanner scanner = new Scanner(System.in);

        //prompting for user input
        System.out.println("Please enter a new username");
        username = scanner.next();
        while (resultSet.next())
        {
            //conditional if username already exists in the database and prompting for user input
            if (username.equals(resultSet.getString("Username")))
            {
                resultSet = statement.executeQuery("SELECT username FROM USER");
                System.out.println("Username already exists please enter a new username");
                username = scanner.next();
            }
        }

        //prompting for user input
        System.out.println("Please enter a password");
        password = scanner.next();

        //inserting data into the user table
        PreparedStatement stmt = connection.prepareStatement("INSERT INTO user.user(Username,Password) VALUES(?,?)");
        stmt.setString(1,username);
        stmt.setString(2,password);
        stmt.execute();
        scanner.close();
    }
    public static void searchStock ()
    {

    }
    public static String login() throws SQLException
    {

        //connecting to the database and preparing queries
        Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/user","root","5fdpman");
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT username FROM USER");
        PreparedStatement stmt = connection.prepareStatement("SELECT Password FROM USER WHERE Username = ?");

        //declaring variables
        String username, password;
        Scanner scanner = new Scanner(System.in);

        //prompting for user input
        System.out.print("Please enter a username: ");
        username = scanner.next();

        while (resultSet.next())
        {

            //if the result set matches the username from the user, prompt for password
            if (username.equals(resultSet.getString("Username")))
            {
                stmt.setString(1,username);
                ResultSet resultSet2 = stmt.executeQuery();
                resultSet2.next();
                System.out.print("Please enter a password: ");
                password = scanner.next();

                //if the password matches the username from the database, save the username
                if (password.equals(resultSet2.getString("Password")))
                {
                    System.out.println("Welcome, " + username);
                    return username;
                }
                else
                {
                    System.out.println("Incorrect password");
                    resultSet = statement.executeQuery("SELECT username FROM USER");
                    System.out.println("Please enter a username");
                    username = scanner.next();
                }
            }
        }
        System.out.println("Username does not exist");
        return null;
    }
}
