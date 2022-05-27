import java.util.Scanner;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Proyect {
    // javac Proyect.java
    // java -cp ".:mysql-connector-java-8.0.20.jar" Proyect
    public static void main(String[] args) {
        try {

            // TODO: ANALIZAR ESTO.
            String driver = "com.mysql.cj.jdbc.Driver";
            String url = "jdbc:mysql://localhost:3306/proyecto";
            String username = "root";
            String password = "mY@r3l0j_sQL"; //or root

            //Load database driver if not already loaded.
            Class.forName(driver);

            //Establish network connection to database.
            Connection connection = DriverManager.getConnection(url, username, password);
            System.out.println("Base de datos Conectada.");
            System.out.println("");
            
            //Once connected to the database, we run the menu.
            //We also pass as parameter the connection, to make the queries.
            menu(connection);

            
        } catch (Exception e) {
            System.out.println(e);
        }
    }


    private static void menu(Connection connection) throws SQLException {
        Scanner scan = new Scanner(System.in);
        while (true) {
            System.out.println("1 - Ingresar nueva habitacion.");
            System.out.println("2 - Registrar cliente en una habitacion en la fecha actual.");
            System.out.println("3 - Ver clientes y fechas que se registraron en una habitacion.");
            System.out.println("9 - Salir del menu");
            System.out.println("");
            System.out.print("Seleccionar opcion: ");
            int i = scan.nextInt();
            System.out.println("");
 
            
            switch(i) {
                case 1:
                    insertarHabitacion(connection);
                    break;
                case 2:
                    registrarCliente(connection); 
                    break;
                case 3: 
                    verClientesYFechas(connection);
                    break;
                case 9:
                    System.out.println("Usted ha decidido finalizar sus consultas. Muchas gracias, vuelva ronto!");
                    System.out.println("");
                    return;
                default: 
                    System.out.println("Opcion Incorrecta.");
                    System.out.println("");
                    break;
            }
        }
    }



    private static void insertarHabitacion(Connection connection) throws SQLException {
        Scanner scan = new Scanner(System.in);

        System.out.print("Ingrese el numero de la nueva habitacion: ");
        int nro = scan.nextInt();
        System.out.println("");

        System.out.print("Ingrese la cantidad de camas de la nueva habitacion: ");
        String cant_camas = scan.next();
        System.out.println("");

        System.out.print("Ingrese el codigo de la nueva habitacion: ");
        int cod = scan.nextInt();
        System.out.println("");


        // Once scanned the attributes, we try...
        try {
            // Setting the query.
            String query = "INSERT INTO habitaciones (nro_habitacion, cant_camas, cod_habitacion) VALUES ('" + nro + "', '"
                    + cant_camas + "', '" + cod + "')";
    
            // Prepare the statement
            // TODO: ANALIZAR ESTO.
            PreparedStatement insert = connection.prepareStatement(query);
            
            // And we execute the update to add the new data.
            // TODO: ANALIZAR ESTO.
            insert.executeUpdate();
    
            // If executed properly...
            System.out.println("Insercion completada con exito.");
            System.out.println("");
            
        } catch (Exception e) {
            // If something goes wrong...
            System.out.println(e);
            System.err.println("");
        }
    }


    private static void registrarCliente(Connection connection) {
        Scanner scan = new Scanner(System.in);
        System.out.print("Ingrese la habitacion en la que se hospedara el cliente: ");
        int nro_habitacion = scan.nextInt();
        System.out.println("");

        System.out.print("Ingrese el dni del cliente: ");
        int dni = scan.nextInt();
        System.out.println("");

        System.out.print("Ingrese la cantidad de dias que se hospedara: ");
        int cant_dias = scan.nextInt();
        System.out.println("");

        System.out.print("Ingrese el monto total del hospedaje: ");
        int monto_total = scan.nextInt();
        System.err.println("");

        // Once scanned the attributes, we try...
        try {
            // Setting the query.
            String query = "INSERT INTO es_ocupada (nro_habitacion, fecha, dni, cant_dias, monto_total) VALUES ('" + nro_habitacion + 
                    "', NOW(), '" + dni + "', '" + cant_dias + "', '" + monto_total + "')";
    
            // Prepare the statement
            // TODO: ANALIZAR ESTO.
            PreparedStatement insert = connection.prepareStatement(query);
            
            // And we execute the update to add the new data.
            // TODO: ANALIZAR ESTO.
            insert.executeUpdate();
    
            System.out.println("Insercion completada con exito.");
            System.out.println("");
            
        } catch (Exception e) {
            // If something goes wrong...
            System.out.println(e);
            System.out.println("");
        }
    }
    
    
    private static void verClientesYFechas(Connection connection) {
        Scanner scan = new Scanner(System.in);
        System.out.print("Ingrese la habitacion de la que desea obtener los datos: ");
        int nro_habitacion = scan.nextInt();
        System.out.println("");


        // Once scanned the attributes, we try...
        try {
            // Setting the query.
            String query = "SELECT fecha, dni FROM es_ocupada where nro_habitacion = '"+ nro_habitacion +"'";
            
            // TODO: ANALIZAR ESTO.
            PreparedStatement statement = connection.prepareStatement(query);

            // TODO: ANALIZAR ESTO.

            ResultSet result = statement.executeQuery();

            System.out.println("");
            System.out.println("Registro de personas y fechas de la habitacion numero: " + nro_habitacion);
            System.out.println("");

            while (result.next()){
                System.out.println("DNI: " + result.getString("dni") + 
                    " - FECHA: " + result.getString("fecha"));
                System.out.println("");
            }

        } catch (Exception e) {
            // If something goes wrong...
            System.out.println(e);
            System.out.println("");
        }
    }
}