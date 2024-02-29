package HospitalManagementSystem;
import java.sql.*;
import java.util.*;
public class Patient {
    private Connection connection;
    private Scanner  sc;

    public Patient(Connection connection , Scanner sc){
        this.connection = connection;
        this.sc = sc;
    }
    public void addPatient(){
        System.out.println("Enter Patient Name: ");
        String name  = sc.next();
        System.out.println("Enter Patient Age: ");
        int age = sc.nextInt();
        System.out.println("Enter Patient Gender: ");
        String gender = sc.next();

        try{
            String query = "INSERT INTO patients(name, age, gender) VALUES (?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,name);
            preparedStatement.setInt(2,age);
            preparedStatement.setString(3,gender);
            int affectedRows = preparedStatement.executeUpdate();
            if(affectedRows > 0){
                System.out.println("Patient Data added Successfully");
            }else{
                System.out.println("Failed to add patient data");
            }

        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    public void viewPatients(){
        String query = "select * from patients";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            System.out.println("PATIENTS");
            System.out.println("+---------------+-----------------------------------+-----------+----------------+");
            System.out.println("| Patient Id    | Patient Name                      | Age       | Gender         |");
            System.out.println("+---------------+-----------------------------------+-----------+----------------+");
            while(resultSet.next()){
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int age = resultSet.getInt("age");
                String gender = resultSet.getString("gender");
                System.out.printf("|%-15s|%-35s|%-11s|%-16s\n", id, name, age, gender);
                System.out.println("+---------------+-----------------------------------+-----------+----------------+");

            }

        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    public boolean getPatientById(int id){
        String query = "select * from patients WHERE id = ?";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                return true;
            }else{
                return false;
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }
}
