package com.faith.bean;

import java.text.ParseException;
import java.text.SimpleDateFormat;
//import java.time.LocalDate;
//import java.time.Period;
import java.util.Date;
import java.util.Scanner;

public class Patients {
	private int id;
	private String patientId;
	private String fullName;
	private Date dob;
	private int age;
	private String bloodGroup;
	private String gender;
	private String phone;
	private String address;
	private Date regDate;
	private Date modifiedDate;
	private String isActive;;
	Scanner scan = new Scanner(System.in);
	
	public Patients() {
		super();
		
	}
	
	public Patients(int id, String patientId, String fullName, Date dob, int age, String bloodGroup, String gender,
			String phone, String address, Date regDate, Date modifiedDate, String isActive) {
		super();
		this.id = id;
		this.patientId = patientId;
		this.fullName = fullName;
		this.dob = dob;
		this.age = age;
		this.bloodGroup = bloodGroup;
		this.gender = gender;
		this.phone = phone;
		this.address = address;
		this.regDate = regDate;
		this.modifiedDate = modifiedDate;
		this.isActive = isActive;
	}



	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPatientId() {
		return patientId;
	}

	public void setPatientId(String patientId) {
		this.patientId = patientId;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		if(fullName.matches("^[A-Za-z_ ]{3,30}$"))
			this.fullName = fullName;
		else{
			System.out.println("\n\t\t\tInvalid name entered\n\t\t\tEnter a valid name : ");
			setFullName(scan.nextLine());
		}
		
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(String testDate) throws ParseException {
		if(testDate.matches("(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[012])/((19|20)\\d\\d)")){
			//converting date of birth to sql date format
			java.util.Date utilDate = new SimpleDateFormat("dd/MM/yyyy").parse(testDate);
			java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
			//getting current date and converting to sql date format
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			Date date = new Date();
			String dt1 = formatter.format(date);
			java.util.Date utilDate1 = new SimpleDateFormat("dd/MM/yyyy").parse(dt1);
			java.sql.Date sqlDate1 = new java.sql.Date(utilDate1.getTime());
	
			int dateCheck = sqlDate.compareTo(sqlDate1);
			if(dateCheck<=0)
				this.dob = sqlDate;
			else {
				System.out.println("\n\t\t\tYou cannot input future dates....\n\n\t\t\tEnter a valid date of birth(dd/mm/yyyy) : ");
				setDob(scan.nextLine());
			}
		}else {
			System.out.println("\n\t\t\tInvalid date format... \n\n\t\t\tEnter a valid date of birth(dd/mm/yyyy) : ");
			setDob(scan.nextLine());
		}
	}

	public String getBloodGroup() {
		return bloodGroup;
	}

	public void setBloodGroup(String bloodGroup) {
		if(bloodGroup.matches("^(A|B|AB|O)[+-]$"))
			this.bloodGroup = bloodGroup;
		else{
			System.out.println("\n\t\t\tInvalid blood group entered\n\t\t\tEnter a valid blood group : ");
			setBloodGroup(scan.nextLine());
		}
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		if(gender.toLowerCase().matches("^male$|^female|^transgender$"))
			this.gender = gender;
		else{
			System.out.println("\n\t\t\tInvalid gender entered\n\t\t\tEnter a valid gender : ");
			setGender(scan.nextLine());
		}
		
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		if(phone.matches("^[6-9]\\d{9}$"))
			this.phone = phone;
		else{
			System.out.println("\n\t\t\tInvalid phone number entered\n\t\t\tEnter a valid phone number : ");
			setPhone(scan.nextLine());
		}
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		if(address.matches("^[A-Za-z ][A-Za-z0-9_ ]{5,99}$"))
			this.address = address;
		else{
			System.out.println("\n\t\t\tInvalid address entered\n\t\t\tEnter a valid address : ");
			setAddress(scan.nextLine());
		}
	}

	public int getAge() throws ParseException {
		/*String dob = String.valueOf(getDob());
		LocalDate dob1 = LocalDate.parse(dob);
		LocalDate curDate = LocalDate.now();  
		setAge(Period.between(dob1, curDate).getYears());*/
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public Date getRegDate() {
		return regDate;
	}

	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		if(isActive.toLowerCase().matches("^yes$|^no$"))
			this.isActive = isActive;
		else{
			System.out.println("\n\t\t\tInvalid gender entered\n\t\t\tEnter a valid gender : ");
			setGender(scan.nextLine());
		}
	}


	@Override
	public String toString() {
		return String.format("%-15s%-15s%-15s%-15s%-15s%-15s%-15s%-15s%-15s%-15s%-15s%n",this.id,this.patientId,this.fullName,this.gender,this.age,this.bloodGroup,this.phone,this.address,this.regDate,this.isActive,this.modifiedDate);
				
	}
	
	
}
