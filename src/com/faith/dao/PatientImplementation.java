package com.faith.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.faith.bean.Patients;
import com.faith.config.DatabaseConnection;

public class PatientImplementation implements Ipatient{
	//sql queries
	private static String INSERT_PATIENT = "INSERT INTO patients(fullName,dob,bloodGroup,gender,phone,address,modifiedDate) VALUES(?,?,?,?,?,?,?)";
	//query to display
	private static String DISPLAY_ALL = "SELECT * FROM patients WHERE isActive = ?";
	//query to search by id
	private static String SEARCH_BY_REGNO = "SELECT * FROM patients WHERE patientId = ?";
	//query to search by registration number
	private static String SEARCH_BY_PHONE = "SELECT * FROM patients WHERE phone = ?";
	//query to disable
	private static String DISABLE_BY_ID = 	"UPDATE patients SET isActive = ?, modifiedDate = ? WHERE id = ?";
	//query for update name
	private static String UPDATE_NAME = "UPDATE patients SET fullName = ?, modifiedDate = ? WHERE id = ?";
	//query for update address
	private static String UPDATE_ADDRESS = "UPDATE patients SET address = ?, modifiedDate = ? WHERE id = ?";
	//query for update phone
	private static String UPDATE_PHONE = "UPDATE patients SET phone = ?, modifiedDate = ? WHERE id = ?";
	//query to get last id
	private static String LAST_ID = "SELECT MAX(id) FROM patients";
	//getting id to generate token using regNo
	//use this id to set foreign key value at token table
	private static String ID_FOR_TOKEN1 = "SELECT patientId FROM patients WHERE id = ?";
	//getting id to generate token using phone number
	//private static String ID_FOR_TOKEN2 = "SELECT id FROM patients WHERE phone = ?";
	
	//connection
	private Connection connection = null;
	private PreparedStatement statement = null;
	private ResultSet resultSet = null;
	
	@Override
	public boolean addPatientToTable(Patients pat) throws Exception {
		boolean result = false;
		try{
			connection = DatabaseConnection.getDataBaseConnection();
			statement = connection.prepareStatement(INSERT_PATIENT);
			statement.setString(1, pat.getFullName());
			statement.setDate(2,new java.sql.Date(pat.getDob().getTime()));
			statement.setString(3, pat.getBloodGroup());
			statement.setString(4, pat.getGender());
			statement.setString(5, pat.getPhone());
			statement.setString(6, pat.getAddress());
			statement.setDate(7, new java.sql.Date(pat.getModifiedDate().getTime()));
			if(1==statement.executeUpdate()){
				result = true;
				statement = connection.prepareStatement(LAST_ID);
				resultSet = statement.executeQuery();
				resultSet.next();
				statement = connection.prepareCall("{call custom_id_generator(?)}");
				statement.setInt(1, resultSet.getInt("MAX(id)"));
				if(1==statement.executeUpdate()){
					statement = connection.prepareStatement(ID_FOR_TOKEN1);
					statement.setInt(1,resultSet.getInt("MAX(id)"));
					resultSet = statement.executeQuery();
					resultSet.next();
					pat.setPatientId(resultSet.getString("patientId"));
				}
				
			}
		}catch(Exception err){
			System.out.println(err);
		}finally{
			statement.close();
		}
		return result;
	}
	
	@Override
	public List<Patients> readPatientFromTable() throws Exception {
		//ArrayList
		List<Patients> listPatients = new ArrayList<Patients>();
		try{
			//open connection
			connection = DatabaseConnection.getDataBaseConnection();					
			//create statement
			statement = connection.prepareStatement(DISPLAY_ALL);
			statement.setString(1, "yes");
			//execute statement
			resultSet = statement.executeQuery();		
			while(resultSet.next()){
				int patId = resultSet.getInt("id");
				String regNo = resultSet.getString("patientId");
				String fullName = resultSet.getString("fullName");
				Date dob = resultSet.getDate("dob");
				String bloodGroup = resultSet.getString("bloodGroup");
				String gender = resultSet.getString("gender");
				String phone = resultSet.getString("phone");
				String address = resultSet.getString("address");
				Date regDate = resultSet.getDate("regDate");
				Date modDate = resultSet.getDate("modifiedDate");
				String isActive = resultSet.getString("isActive");
				
				String dob1 = String.valueOf(dob);
				LocalDate dob2 = LocalDate.parse(dob1);
				LocalDate curDate = LocalDate.now();  
				int age = Period.between(dob2, curDate).getYears();
				listPatients.add(new Patients(patId,regNo,fullName,dob,age,bloodGroup,gender,phone,address,regDate,modDate,isActive));
				}
			}catch(Exception err){
				System.out.println(err);
			}finally{
				//close connection
				resultSet.close();
				statement.close();
			}
		return listPatients;
	}

	public Patients findPatientById(String patId) throws Exception {
		Patients pat = null;
		try{
			//open connection
			connection = DatabaseConnection.getDataBaseConnection();	
			//set sql statement
			statement = connection.prepareStatement(SEARCH_BY_REGNO);
			statement.setString(1,patId);
			
			//execute query
			resultSet = statement.executeQuery();
			while(resultSet.next()){
				int pId = resultSet.getInt("id");
				String regNo = resultSet.getString("patientId");
				//System.out.println("iam in search+"+regNo);
				String fullName = resultSet.getString("fullName");
				Date dob = resultSet.getDate("dob");
				String bloodGroup = resultSet.getString("bloodGroup");
				String gender = resultSet.getString("gender");
				String phone = resultSet.getString("phone");
				String address = resultSet.getString("address");
				Date regDate = resultSet.getDate("regDate");
				Date modDate = resultSet.getDate("modifiedDate");
				String isActive = resultSet.getString("isActive");
				
				String dob1 = String.valueOf(dob);
				LocalDate dob2 = LocalDate.parse(dob1);
				LocalDate curDate = LocalDate.now();  
				int age = Period.between(dob2, curDate).getYears();
				//create of product//single record
				pat = new Patients(pId,regNo,fullName,dob,age,bloodGroup,gender,phone,address,regDate,modDate,isActive);
			}
			
		}catch(Exception err){
			System.out.println(err);
		}finally{
			//close connection
			resultSet.close();
			statement.close();
		}
		
		return pat;
	}
	public List<Patients> findPatientByPhone(String phone) throws Exception {
		//ArrayList
		List<Patients> listPatients = new ArrayList<Patients>();
		//Patients pat = null;
		try{
			//open connection
			connection = DatabaseConnection.getDataBaseConnection();	
			//set sql statement
			statement = connection.prepareStatement(SEARCH_BY_PHONE);
			statement.setString(1,phone);
			
			//execute query
			resultSet = statement.executeQuery();
			while(resultSet.next()){
				int pId = resultSet.getInt("id");
				String regNo = resultSet.getString("patientId");
				String fullName = resultSet.getString("fullName");
				Date dob = resultSet.getDate("dob");
				String bloodGroup = resultSet.getString("bloodGroup");
				String gender = resultSet.getString("gender");
				//String ph = resultSet.getString("phone");
				String address = resultSet.getString("address");
				Date regDate = resultSet.getDate("regDate");
				Date modDate = resultSet.getDate("modifiedDate");
				String isActive = resultSet.getString("isActive");
				
				String dob1 = String.valueOf(dob);
				LocalDate dob2 = LocalDate.parse(dob1);
				LocalDate curDate = LocalDate.now();  
				int age = Period.between(dob2, curDate).getYears();
				//create of product//single record
				listPatients.add(new Patients(pId,regNo,fullName,dob,age,bloodGroup,gender,phone,address,regDate,modDate,isActive));
			}
			
		}catch(Exception err){
			System.out.println(err);
		}finally{
			//close connection
			resultSet.close();
			statement.close();
		}
		return listPatients;
	}

	@Override
	public boolean patStatusUpdate(Patients pat, int patId) throws Exception {
		boolean result = false;
		try{
			//open connection
			connection = DatabaseConnection.getDataBaseConnection();
			//set sql statement
			statement = connection.prepareStatement(DISABLE_BY_ID);
			statement.setString(1,pat.getIsActive());
			statement.setDate(2, new java.sql.Date(pat.getModifiedDate().getTime()));
			statement.setInt(3,patId);
			
			//execute query
			if(statement.executeUpdate()==1)
				result = true;
		}catch(Exception err){
			System.out.println(err);
		}finally{
			//close connection
			statement.close();
		}
		return result;
	}

	@Override
	public boolean updatePatTable(Patients pat, int patId, int opt)
			throws Exception {
		boolean result = false;
		try{
			//open connection
			connection = DatabaseConnection.getDataBaseConnection();
			//set sql statement
			if(opt==1){
				statement = connection.prepareStatement(UPDATE_NAME);
				statement.setString(1,pat.getFullName());
				statement.setDate(2, new java.sql.Date(pat.getModifiedDate().getTime()));
				statement.setInt(3, patId);
			}
			else if(opt==2){
				statement = connection.prepareStatement(UPDATE_ADDRESS);
				statement.setString(1,pat.getAddress());
				statement.setDate(2, new java.sql.Date(pat.getModifiedDate().getTime()));
				statement.setInt(3, patId);
			}
			//if(opt==3)
			else{
				statement = connection.prepareStatement(UPDATE_PHONE);
				statement.setString(1,pat.getPhone());
				statement.setDate(2, new java.sql.Date(pat.getModifiedDate().getTime()));
				statement.setInt(3, patId);
			}
				
			if(statement.executeUpdate()==1){
				result = true;
			}
			}catch(Exception err){
				System.out.println(err);
			}finally{
				//close connection
				statement.close();
			}
		return result;
	}

	
	
}
