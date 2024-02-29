package HospitalManagementSystem;
import java.sql.*;
import java.util.*;

public class HospitalManagementSystem {
    private static final String url = "jdbc:mysql://localhost:3306/hospital";
    private static final String username = "root";
    private  static final String password = "123Nani321@";
    public static void main(String args[]){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
        }catch(ClassNotFoundException e){
            e.printStackTrace();
        }
        Scanner sc = new Scanner(System.in);
        try{
            Connection connection = DriverManager.getConnection(url,username,password);
            Patient patient = new Patient(connection,sc);
            Doctor doctor  = new Doctor(connection);
            while(true){
                System.out.println("HOSPITAL MANAGEMENT SYSTEM");
                System.out.println("1. Add Patient");
                System.out.println("2. View Patients");
                System.out.println("3. View Doctors");
                System.out.println("4. Book Appointment");
                System.out.println("5. Exit");
                System.out.println("ENTER YOUR CHOICE");
                int choice = sc.nextInt();

                switch(choice){
                    case 1:
                        //Add Patient
                        patient.addPatient();
                        System.out.println();
                    case 2:
                        //View Patient
                        patient.viewPatients();
                        System.out.println();
                    case 3:
                        //view Doctors
                        doctor.viewDoctors();
                        System.out.println();
                    case 4:
                        //Book appointment
                        bookAppointment(patient, doctor, connection, sc);
                        System.out.println();

                    case 5:
                        return;
                    default:
                    System.out.println("Enter valid Choice");
                }
            }
        }catch(SQLException  e){
            e.printStackTrace();
        }
    }
    public static void bookAppointment(Patient patient,Doctor doctor,Connection connection, Scanner sc){
        System.out.println("Enter Patient Id");
        int patientId = sc.nextInt();
        System.out.println("Enter Doctor Id");
        int doctorId = sc.nextInt();
        System.out.println("Enter Appointment Date (YY-MM-DD):  ");
        String appointmentDate = sc.next();
        if(patient.getPatientById(patientId) && doctor.getDoctorById(doctorId) ){
            if(checkDoctorAvailability(doctorId,appointmentDate, connection)) {
                String appointmentQuery = "INSERT INTO appointments(patient_id, doctor_id, appointment_date) VALUES(?,?,?)";
                try {
                    PreparedStatement preparedStatement = connection.prepareStatement(appointmentQuery);
                    preparedStatement.setInt(1,patientId);
                    preparedStatement.setInt(2,doctorId);
                    preparedStatement.setString(3,appointmentDate);
                    int rowsAffected = preparedStatement.executeUpdate();
                    if(rowsAffected > 0){
                        System.out.println("Appointment Date is Booked");

                    }else{
                        System.out.println("Sorry to say this the booking of appointment is failed !!");
                    }

                }catch(SQLException e){
                    e.printStackTrace();
                }
            }else{
                System.out.println("Oops!! Doctor Not available at this date");
            }
    }else{
            System.out.println(" Oopss!!! Either Doctor or Patient doesn't exists !!!");
        }
}
public static boolean checkDoctorAvailability(int doctorId, String appointmentDate, Connection connection){
        String query = "select count(*) from appointments where doctor_id = ? and appointment_date = ?";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,doctorId);
            preparedStatement.setString(2,appointmentDate);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                int count = resultSet.getInt(1);
                if(count == 0){
                    return true;
                }else{
                    return false;
                }
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;

}
}
