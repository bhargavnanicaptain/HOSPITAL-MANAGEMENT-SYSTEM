package HospitalManagementSystem;
import java.sql.*;
import java.util.*;

public class Doctor {
    private Connection connection;


    public Doctor(Connection connection ){
        this.connection = connection;

    }

    public void viewDoctors(){
        String query = "select * from doctors";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            System.out.println("DOCTORS");
            System.out.println("+---------------+-----------------------------------+---------------------------+");
            System.out.println("| Doctor Id    | Doctor Name                        | Specalization             |");
            System.out.println("+---------------+-----------------------------------+---------------------------+");
            while(resultSet.next()){
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String specialization = resultSet.getString("specialization");
                System.out.printf("|%-14s|%-39s|%-27s\n", id, name, specialization);
                System.out.println("+---------------+-----------------------------------+---------------------------+");

            }

        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    public boolean getDoctorById(int id){
        String query = "select * from doctors WHERE id = ?";
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


