import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.*;
import java.util.*;

public class db {

    public static void main (String [] Args) throws SQLException
    {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Load the driver
            Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/user", "root", "5fdpman");
            System.out.println("Connected to database!");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        String APIkey = "d0ro19pr01qumepfebu0d0ro19pr01qumepfebug";
        String baseURL = "https://finnhub.io/api/v1";
        User user;
        while (true)
        {
            Scanner scanner = new Scanner(System.in);
            String response = "";
            System.out.println("What would you like to do?\n" +
                    "Type 'create' to create an account\n" +
                    "Type 'login' to login\n" +
                    "Type 'exit' to exit application\n");

            response = scanner.nextLine();
            response = response.toLowerCase();
            switch (response)
            {
                case "create":
                    createAccount();
                    break;
                case "login":
                    user = login();
                    sessionHandler(user);
                    break;
                case "exit":
                    System.exit(0);
                    break;
                default:
                    System.out.println("Something went wrong");
                    break;
            }
        }

        //createAccount();
        //user = login();
        //RFP(user);

        //viewPortfolio(user);
        //System.out.println("$"+user.getWallet());
        //addFunds(user);
        /*
        try
        {
            searchStock(APIkey,baseURL,user);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

         */
    }
    public static void sessionHandler(User user)
    {
        String response = "";
        Scanner scanner = new Scanner(System.in);
        while (true)
        {
            System.out.println("What would you like to do?\n" +
                    "Type 'search' to search for a stock by name\n" +
                    "Type 'ticker' to search for a stock by ticker\n" +
                    "Type 'portfolio' to view portfolio\n" +
                    "Type 'sell' to sell a stock\n" +
                    "Type 'add' to add funds\n" +
                    "Type 'logout' to log out\n" +
                    "Type 'exit' to close application\n" +
                    "Type 'Watchlist' to view watchlist");
            if (scanner.hasNext())
            {
                response = scanner.next();
            }
            response = response.toLowerCase();

            switch (response)
            {
                case "search":
                    try
                    {
                        searchStockName("d0ro19pr01qumepfebu0d0ro19pr01qumepfebug","https://finnhub.io/api/v1");
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    break;

                case "sell":
                    try{
                        RFP(user);
                    }
                    catch (SQLException e)
                    {
                        e.printStackTrace();
                    }
                case "ticker":
                    try
                    {
                        searchStockTicker("d0ro19pr01qumepfebu0d0ro19pr01qumepfebug","https://finnhub.io/api/v1",user);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case "portfolio":
                    try {
                        viewPortfolio(user);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case "watchlist":
                    try{
                        VWL(user,1);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case "logout":
                    return;
                case "exit":
                    System.exit(0);
                    break;
                case "add":
                    try{
                        addFunds(user);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                default:
                    System.out.println("Something went wrong");
                    break;
            }

        }
    }
    public static void searchStockName (String key, String base) throws IOException
    {
        System.out.println("Please input a stock name you would like to search");
        Scanner scanner = new Scanner(System.in);
        String ticker = scanner.next();
        String symbol;

        URL url = new URL(base+"/search?q="+ticker+"&token="+key);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        int responseCode = connection.getResponseCode();
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

        if (responseCode == HttpURLConnection.HTTP_OK) {
            String line;
            StringBuilder response = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }

            if (response.toString().length() == 23)
            {
                System.out.println("Invalid search");
                return;
            }

            System.out.println("============================================================================");
            System.out.println(response.substring(response.indexOf("description"),response.indexOf(",\"displaySymbol")));
            System.out.println(response.substring(response.indexOf("symbol"),response.indexOf(",\"type")));
            symbol = response.substring(response.indexOf("symbol") + 9,response.indexOf(",\"type") - 1);


            url = new URL(base+"/quote?symbol="+symbol+"&token="+key);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            responseCode = connection.getResponseCode();
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            if (responseCode == HttpURLConnection.HTTP_OK){
                response = new StringBuilder();
                line = "";
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }

                System.out.println("Current Price:" + response.substring(response.indexOf(":") + 1,response.indexOf(",")) + "$");
                System.out.println("Change in price:" + response.substring(response.indexOf("\"d\":")+4,response.indexOf(",\"dp\"")) + "$");
                System.out.println("Previous close price:" + response.substring(response.indexOf("\"pc\":")+5,response.indexOf(",\"t\"")) + "$");
                System.out.println("============================================================================");
            }

        }


    }
    public static void RFW (User user) throws SQLException
    {
        Scanner scanner = new Scanner(System.in);
        String response;
        Stack <Integer> stack = new Stack<Integer>();
        Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/user","root","5fdpman");
        PreparedStatement stmt = connection.prepareStatement("DELETE FROM watchlist WHERE WatchlistID = ?");
        PreparedStatement stmt2 = connection.prepareStatement("SELECT * FROM watchlist WHERE User = ?");

        stmt2.setString(1,user.getUsername());
        ResultSet resultSet = stmt2.executeQuery();

        if (!resultSet.next())
        {
            System.out.println("Watchlist is empty");
            return;
        }
        resultSet = stmt.executeQuery();
        while (resultSet.next())
        {
            stack.push(resultSet.getInt("WatchlistID"));
        }

        System.out.println("Enter the watchlistID of the stock to remove from watchlist");
        VWL(user,0);
        response = scanner.next();

        while (!stack.contains(Integer.parseInt(response)))
        {
            System.out.println("Invalid input");
            response = scanner.next();
        }

        stmt.setInt(1,Integer.parseInt(response));
        stmt.execute();

    }
    public static void ATW (User user, String ticker) throws SQLException
    {
        Scanner scanner = new Scanner (System.in);
        float response = 0;
        Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/user","root","5fdpman");
        PreparedStatement stmt = connection.prepareStatement("INSERT INTO watchlist(Stock,User,Alert) VALUES(?,?,?)");

        System.out.println("What price for " + ticker + " would you like to be notified for?");
        try{
            response = scanner.nextFloat();
        }
        catch (InputMismatchException e)
        {
            System.out.println("Invalid input detected");
            e.printStackTrace();
        }

        stmt.setString(1,ticker);
        stmt.setString(2, user.getUsername());
        stmt.setFloat(3,response);
        stmt.execute();
    }
    public static void VWL (User user, int condition) throws SQLException
    {
        Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/user","root","5fdpman");
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM watchlist WHERE user = ?");
        stmt.setString(1,user.getUsername());
        ResultSet resultSet = stmt.executeQuery();
        Scanner scanner = new Scanner(System.in);
        if (!resultSet.next())
        {
            System.out.println("========================");
            System.out.println("No stocks in watchlist");
            System.out.println("========================");
            return;
        }
        resultSet = stmt.executeQuery();

            System.out.println("==================================");
            System.out.println(user.getUsername() + "'s watchlist:\n" +
                    "Stock\t\t" +
                    "Alert\t\t" +
                    "WatchlistID");
            while (resultSet.next())
            {
                System.out.print(resultSet.getString("Stock") + "\t\t");
                System.out.print(resultSet.getString("Alert") + "\t\t");
                System.out.print(resultSet.getString("WatchlistID") + "\t\t\n");
            }
            System.out.println("==================================");

        if (condition == 1)
        {
            System.out.println("Would you like to remove any stock from your watchlist?\n" +
                    "'yes' to remove\n" +
                    "'no' to return to the dashboard");
            String response = scanner.next();
            response = response.toLowerCase();

            while (true)
            {
                switch (response)
                {
                    case "yes":
                        RFW(user);
                        return;
                    case "no":
                        return;
                    default:
                        System.out.println("Input not recognized, please try again");
                        response = scanner.next();
                        response = response.toLowerCase();
                }
            }
        }

    }
    public static void RFP (User user) throws SQLException
    {
        Stack <Integer> stack = new Stack<Integer>();
        Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/user","root","5fdpman");
        PreparedStatement stmt = connection.prepareStatement("DELETE FROM portfolio WHERE TransactionID = ?");
        PreparedStatement stmt2  = connection.prepareStatement("SELECT * FROM portfolio WHERE Username = ?");
        PreparedStatement stmt3 = connection.prepareStatement("SELECT * FROM portfolio WHERE TransactionID = ?");
        PreparedStatement stmt4 = connection.prepareStatement("UPDATE `user`.`user` SET Wallet = ? WHERE Username = ?");
        stmt2.setString(1,user.getUsername());
        ResultSet resultSet = stmt2.executeQuery();
        while (resultSet.next())
        {
            stack.push(resultSet.getInt("TransactionID"));
        }
        Scanner scanner = new Scanner(System.in);
        String response;
        viewPortfolio(user);
        System.out.println("Please enter the transaction ID of the stock you wish to remove" +
                "Press '0' to cancel");
        response = scanner.next();
        if (Integer.parseInt(response) == 0)
        {
            return;
        }
        while (!stack.contains(Integer.parseInt(response)))
        {
            if(Integer.parseInt(response) == 0)
            {
                return;
            }
            System.out.println("Invalid transaction ID please enter a valid option from below or press '0' to cancel");
            viewPortfolio(user);
            response = scanner.next();
        }
        stmt3.setInt(1,Integer.parseInt(response));
        resultSet = stmt3.executeQuery();
        resultSet.next();

        stmt4.setFloat(1,(resultSet.getInt("Quantity")*resultSet.getFloat("BuyPrice")) + user.getWallet());
        stmt4.setString(2,user.getUsername());
        stmt4.execute();
        user.setWallet(resultSet.getInt("Quantity")*resultSet.getFloat("BuyPrice"));

        stmt.setInt(1,Integer.parseInt(response));
        stmt.execute();
        //scanner.close();

    }
    public static void viewPortfolio (User user) throws SQLException
    {
        Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/user","root","5fdpman");
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM portfolio WHERE Username = ?");
        stmt.setString(1,user.getUsername());
        ResultSet resultSet = stmt.executeQuery();
        if (!resultSet.next())
        {
            System.out.println("===========================================");
            System.out.println("You have no stocks in your portfolio");
            System.out.println("===========================================");
            return;
        }
        resultSet = stmt.executeQuery();
        System.out.println(user.getUsername()+"'s portfolio: ");
        System.out.println("=================================================");
        System.out.println("ID \tTicker \tPrice Bought \tQuantity owned");
        while(resultSet.next())
        {
            System.out.print(resultSet.getString("TransactionID") + "\t");
            System.out.print(resultSet.getString("Stock") + "\t");
            System.out.print(resultSet.getString("BuyPrice") + "\t\t\t");
            System.out.print(resultSet.getString("Quantity") + "\n");
        }
        System.out.println("=================================================");

    }
    public static void addFunds (User user) throws SQLException
    {
        Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/user","root","5fdpman");

        Scanner scanner = new Scanner(System.in);
        System.out.println("How much money would you like to deposit?");
        float input = scanner.nextFloat();
        user.setWallet(input);

        PreparedStatement stmt = connection.prepareStatement("UPDATE `user`.`user` SET Wallet = ? WHERE Username = ?");
        stmt.setFloat(1,user.getWallet());
        stmt.setString(2,user.getUsername());
        stmt.execute();
        //scanner.close();
        System.out.println("New balance = " + user.getWallet());
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
        PreparedStatement stmt = connection.prepareStatement("INSERT INTO user.user(Username,Password,Wallet) VALUES(?,?,?)");
        stmt.setString(1,username);
        stmt.setString(2,password);
        stmt.setFloat(3,0);
        stmt.execute();
        //scanner.close();
    }
    public static void ATP (String ticker, float buyPrice, User user) throws SQLException
    {
        Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/user","root","5fdpman");
        PreparedStatement stmt = connection.prepareStatement("INSERT INTO portfolio(Username,Stock,BuyPrice,Quantity) VALUES (?,?,?,?)");
        PreparedStatement update = connection.prepareStatement("UPDATE `user`.`user` SET Wallet = ? WHERE Username = ?");
        Scanner scanner = new Scanner(System.in);
        System.out.println("How many stocks of " + ticker + " would you like to buy");
        int quantity = scanner.nextInt();
        while (quantity < 1)
        {
            System.out.println("Stock quantity cannot be zero or negative");
            try
            {
                quantity = scanner.nextInt();
            }
            catch (InputMismatchException e)
            {
                System.out.println("Invalid input");
            }

        }
        if (user.getWallet() < (quantity * buyPrice))
        {
            System.out.println("Error: Account balance," + user.getWallet() + ", is less than total price, " + (quantity*buyPrice));
            return;
        }
        user.setWallet((-1)*(quantity*buyPrice));
        update.setFloat(1,user.getWallet());
        update.setString(2,user.getUsername());
        update.execute();
        System.out.println("New balance is: " + user.getWallet());
        stmt.setString(1,user.getUsername());
        stmt.setString(2,ticker);
        stmt.setFloat(3,buyPrice);
        stmt.setInt(4,quantity);
        stmt.execute();
    }
    public static void searchStockTicker (String key, String base,User user) throws IOException
    {
        System.out.println("Please input a stock ticker you would like to search");
        Scanner scanner = new Scanner(System.in);
        String ticker = scanner.next();
        String response2;
        ticker = ticker.toUpperCase();

        URL url = new URL(base+"/quote?symbol="+ticker+"&token="+key);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        int responseCode = connection.getResponseCode();
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

        if (responseCode == HttpURLConnection.HTTP_OK) {
                String line;
                StringBuilder response = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                //System.out.println(response.toString());
                float buyPrice = Float.parseFloat(response.substring(response.indexOf(":") + 1,response.indexOf(",")));
                if (buyPrice == 0)
                {
                    System.out.println("Invalid stock ticker");
                    return;
                }
            System.out.println("=================================================================================");
                System.out.println("Current stock information about " + ticker);
                System.out.println("Current Price:" + response.substring(response.indexOf(":") + 1,response.indexOf(",")) + "$");
                System.out.println("Change in price:" + response.substring(response.indexOf("\"d\":")+4,response.indexOf(",\"dp\"")) + "$");
                System.out.println("Previous close price:" + response.substring(response.indexOf("\"pc\":")+5,response.indexOf(",\"t\"")) + "$");
            System.out.println("=================================================================================");

            System.out.println("Would you like to add this stock to your watchlist or portfolio?\n" +
                    "Type 'portfolio' to add it to your portfolio. " +
                    "Type 'watchlist' to add it to your watchlist. " +
                    "Type 'return' to return to the dashboard.");
            response2 = scanner.next();
            response2 = response2.toLowerCase();
            switch (response2)
            {
                case "portfolio":
                    try
                    {
                        ATP(ticker,buyPrice,user);
                    }
                    catch (SQLException e)
                    {
                        e.printStackTrace();
                    }
                    break;
                case "watchlist":
                    try
                    {
                        ATW(user,ticker);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case "return":
                    return;
                default:
                    System.out.println("Something went wrong");
                    break;
            }
            }
        else{
                System.out.println("Error: " + responseCode);
            }
        reader.close();
        //scanner.close();
        connection.disconnect();
    }
    public static User login() throws SQLException
    {

        //connecting to the database and preparing queries
        Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/user","root","5fdpman");
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT username FROM USER");
        PreparedStatement stmt = connection.prepareStatement("SELECT Password FROM USER WHERE Username = ?");
        PreparedStatement stmt2 = connection.prepareStatement("SELECT Wallet FROM USER WHERE Username = ?");

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
                Console console = System.console();

                char[] passwordMask = console.readPassword("Enter password: ");

                stmt.setString(1,username);
                ResultSet resultSet2 = stmt.executeQuery();
                resultSet2.next();
                //System.out.print("Please enter a password: ");
                password = String.valueOf(passwordMask);
                Arrays.fill(passwordMask,' ');

                //if the password matches the username from the database, save the username
                if (password.equals(resultSet2.getString("Password")))
                {
                    stmt2.setString(1,username);
                    System.out.println("Welcome, " + username);
                    resultSet = stmt2.executeQuery();
                    resultSet.next();
                    return new User(username,resultSet.getFloat("Wallet"));
                }
                else
                {
                    System.out.println("Invalid credentials");
                    resultSet = statement.executeQuery("SELECT username FROM USER");
                    System.out.print("Please enter a username: ");
                    username = scanner.next();
                }
            }
        }
        System.out.println("Invalid credentials");
        return login();
    }
}
