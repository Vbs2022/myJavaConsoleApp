package com.faith.library;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import com.faith.bean.Patients;
import com.faith.dao.*;

public class PatientManagementLib {
	static Scanner scan = new Scanner(System.in);
	static Patients pat = new Patients();
	static int tid = 0;
	static Ipatient daoService = new PatientImplementation();
	public static void receptionManagementMenu() throws Exception{
		//String choice;
		do{
			int opt = -1;
			System.out.println("\n\t\t\tRECEPTION HOME PAGE");
			System.out.println("\n\t\t\t1.Manage Patient\n\t\t\t2.View Log"
					+ "\n\t\t\t3.Logout and Exit");
			try{
				System.out.println("\n\t\t\tEnter your choice : ");
				opt = Integer.parseInt(scan.nextLine());
			}catch(Exception e){
				System.out.println("\n\t\t\tInvalid option...\n");
			}
			
			switch(opt){
			case 1:
				patientManagementMenu();
				break;
			case 2:
				viewLog();
				break;
			case 3:
				System.out.println("\t\t\tExited Successfully....\n\t\t\tThank you for using our services... See you again...");
				System.exit(0);
				break;
			default:
				System.out.println("\t\t\tRead the menu carefully and enter the right choice...");
			}
			
		}while(true);
	}
	
	
	private static void patientManagementMenu() throws Exception {
		//String choice;
		do{
			int opt = -1;
			System.out.println("\n\t\t\tPATIENT MANAGEMENT MENU");
			System.out.println("\n\t\t\t1.Add Patient\n\t\t\t2.See List of Patients"
					+ "\n\t\t\t3.Search and View Patient\n\t\t\t4.Go to Main Menu");
			try{
				System.out.println("\n\t\t\tEnter your choice : ");
				opt = Integer.parseInt(scan.nextLine());
			}catch(Exception e){
				System.out.println("\n\t\t\tInvalid option...\n");
			}
			
			switch(opt){
			case 1:
				addPatient();
				break;
			case 2:
				listPatients();
				break;
			case 3:
				searchMenu();
				break;
			case 4:
				receptionManagementMenu();
				break;
			default:
				System.out.println("\t\t\tRead the menu carefully and enter the right choice...");
			}
			
		}while(true);
		
	}
	
	
	private static void searchMenu() throws Exception {
		//String choice;
		do{
			int opt = -1;
			System.out.println("\n\t\t\tPATIENT SEARCH MENU");
			System.out.println("\n\t\t\t1.By Registration Number\n\t\t\t2.By Phone Number"
					+ "\n\t\t\t3.Go To Preveious Menu");
			try{
				System.out.println("\n\t\t\tEnter your choice : ");
				opt = Integer.parseInt(scan.nextLine());
			}catch(Exception e){
				System.out.println("\n\t\t\tInvalid option...\n");
			}
			
			switch(opt){
			case 1:
				searchByRegNo();
				break;
			case 2:
				searchByPhone();
				break;
			case 3:
				patientManagementMenu();
				break;
			default:
				System.out.println("\t\t\tRead the menu carefully and enter the right choice...");
			}
			
		}while(true);
		
	}


	private static void searchByPhone() throws Exception {
		System.out.println("\n\t\t\tEnter patient phone number : ");
		//pat.setPhone(scan.nextLine());
		String phone = scan.nextLine();
		List<Patients> patLst = daoService.findPatientByPhone(phone);
		//System.out.println(patLst);
		if(!patLst.isEmpty()){
		System.out.format("%-15s%-15s%-15s%-15s%-15s%-15s%-15s%-15s%-15s%-15s%-15s%n","ID","REG_NUMBER","FULL_NAME","GENDER","AGE","BLOOD_GROUP","PHONE","ADDRESS","REG_DATE","ACTIVE_STATUS","LAST_MOD_DATE");
		System.out.println();
		for(Patients pat1 : patLst){
			System.out.println(pat1);
		}
			//System.out.println("\n\t\t\tSize of list : "+patLst.size());
			if(patLst.size()>1)
			{
				System.out.println("\n\t\t\tMultiple Records found....");
				searchByRegNo();
			}
			else
				editDisableManu(pat);
		}
		else{
			System.out.println("\n\t\t\tNo patient found... Check the phone number and try again...");
		}
	}


	private static void searchByRegNo() throws Exception {
		System.out.println("\n\t\t\tEnter patient registration number : ");
		//pat.setPatientId(scan.nextLine());
		String regNo = scan.nextLine();
		pat = daoService.findPatientById(regNo);
		
		if(pat != null){
		System.out.format("%-15s%-15s%-15s%-15s%-15s%-15s%-15s%-15s%-15s%-15s%-15s%n","ID","REG_NUMBER","FULL_NAME","GENDER","AGE","BLOOD_GROUP","PHONE","ADDRESS","REG_DATE","ACTIVE_STATUS","LAST_MOD_DATE");
		System.out.println();
		System.out.println(pat);
		editDisableManu(pat);
		//pat = null;
		}
		else{
			System.out.println("\n\t\t\tNo patient found... Check the ID and try again...");
		}
		}


	private static void editDisableManu(Patients pat1) throws Exception {
		while(true){
		int id = pat1.getId();
		int opt = -1;
		System.out.println("\n\t\t\tHow would you like to proceed?");
		System.out.println("\n\t\t\t1.Edit Patient\n\t\t\t2.Disable Patient\n\t\t\t3.Generate Token\n\t\t\t4.Go Back");
		try{
			System.out.println("\n\t\t\tEnter your choice : ");
			opt = Integer.parseInt(scan.nextLine());
		}catch(Exception e){
			System.out.println("\n\t\t\tInvalid option...\n");
		}
		
		switch(opt){
		case 1:
			editPatient(id);
			break;
		case 2:
			disablePatient(id);
			break;
		case 3:
			tokenGenerator(pat1);
			break;
		case 4:
			searchMenu();
			break;
		default:
			System.out.println("\t\t\tRead the menu carefully and enter the right choice...");
		}
	  }
		
	}


	private static void disablePatient(int id) throws Exception {
		try{
			System.out.println("\n\t\t\tDo you want to disable this patient?(y/n) : ");
			String confirm = scan.nextLine();
			if(confirm.equalsIgnoreCase("y")){
				pat.setIsActive("no");
				//update modified date
				
				if(daoService.patStatusUpdate(pat, id)){
					System.out.println("\n\t\t\tPatient disabled successfully...");
					//daoService.readPatientFromTable();
				}else
					System.out.println("\n\t\t\tSomething went wrong...");
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		
	}


	private static void editPatient(int id) throws Exception {
			int opt = -1;
			System.out.println("\n\t\t\tWhich field you want to edit?");
			System.out.println("\n\t\t\t1.Name\n\t\t\t2.Address\n\t\t\t3.Phone number");
			try{
				System.out.println("\n\t\t\tEnter your choice : ");
				opt = Integer.parseInt(scan.nextLine());
			}catch(Exception e){
				System.out.println("\n\t\t\tInvalid option...\n");
			}
			
			switch(opt){
			case 1:
				System.out.println("\n\t\t\tEnter new name : ");
				pat.setFullName(scan.nextLine());
				
				//update modified date
				updateModDate();
				
				if(daoService.updatePatTable(pat, id, opt)){
					System.out.println("\n\t\t\t"+pat.getFullName()+"'s record updated successfully...");
				}else{
					System.out.println("\n\t\t\tSomething went wrong");
				}
				break;
			case 2:
				System.out.println("\n\t\t\tEnter new address : ");
				pat.setAddress(scan.nextLine());

				//update modified date
				updateModDate();
				
				SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
				Date date = new Date();
				String dt1 = formatter.format(date);
				java.util.Date utilDate1 = new SimpleDateFormat("dd/MM/yyyy").parse(dt1);
				java.sql.Date sqlDate1 = new java.sql.Date(utilDate1.getTime());
				pat.setModifiedDate(sqlDate1);
				
				if(daoService.updatePatTable(pat, id, opt)){
					System.out.println("\n\t\t\t"+pat.getFullName()+"'s record updated successfully...");
				}else{
					System.out.println("\n\t\t\tSomething went wrong");
				}
				break;
			case 3:
				System.out.println("\n\t\t\tEnter new phone number : ");
				pat.setPhone(scan.nextLine());

				//update modified date
				updateModDate();
				
				if(daoService.updatePatTable(pat, id, opt)){
					System.out.println("\n\t\t\t"+pat.getFullName()+"'s record updated successfully...");
				}else{
					System.out.println("\n\t\t\tSomething went wrong");
				}
				break;
			default:
				System.out.println("\t\t\tRead the menu carefully and enter the right choice...");
			}
		
	}


	private static void updateModDate() throws ParseException {
		//update modified date
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date date = new Date();
		String dt1 = formatter.format(date);
		java.util.Date utilDate1 = new SimpleDateFormat("dd/MM/yyyy").parse(dt1);
		java.sql.Date sqlDate1 = new java.sql.Date(utilDate1.getTime());
		pat.setModifiedDate(sqlDate1);
	}


	private static void listPatients() throws Exception {
		List<Patients> patLst = daoService.readPatientFromTable();
		if(!patLst.isEmpty()){
		System.out.println("\t\t\t\t\t\t\t\t\t\tPATIENT RECORDS\n");
		System.out.format("%-15s%-15s%-15s%-15s%-15s%-15s%-15s%-15s%-15s%-15s%-15s%n","ID","REG_NUMBER","FULL_NAME","GENDER","AGE","BLOOD_GROUP","PHONE","ADDRESS","REG_DATE","ACTIVE_STATUS","LAST_MOD_DATE");
		System.out.println();
		for(Patients pat1 : patLst){
			System.out.println(pat1);
		}
		}
		else{
			System.out.println("\n\t\t\tNo patients found... Your database is empty.!");
		}
	}


	private static void addPatient() throws Exception {
		System.out.println("\n\t\t\tEnter full name : ");
		pat.setFullName(scan.nextLine());
		
		System.out.println("\n\t\t\tEnter date of birth (dd/mm/yyyy) : ");
		pat.setDob(scan.nextLine());
		
		System.out.println("\n\t\t\tEnter blood group (eg:A+): ");
		pat.setBloodGroup(scan.nextLine());
		
		System.out.println("\n\t\t\tSelect Gender\n\t\t\t1.Male\n\t\t\t2.Female\n\t\t\t3.Transgender\n\t\t\tEnter your choice : ");
		String opt = scan.nextLine();
		if(opt.equals("1"))
			pat.setGender("Male");
		else if(opt.equals("2"))
			pat.setGender("Female");
		else if(opt.equals("3"))
			pat.setGender("Transgender");
		else
			System.out.println("\n\t\t\tInvalid input");
		System.out.println("\n\t\t\tEnter phone number : ");
		pat.setPhone(scan.nextLine());
		System.out.println("\n\t\t\tEnter address : ");
		pat.setAddress(scan.nextLine());

		//update modified date
		updateModDate();
		
		try{
			if(daoService.addPatientToTable(pat)){
				System.out.println("\n\t\t\tNew Patient "+pat.getFullName()+"'s Record Added Successfully");
				System.out.println(pat.getPatientId());
				tokenGenerator(pat);
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
	}


	private static void tokenGenerator(Patients pat2) {
		String tk = "0";
		LocalDate curDate = LocalDate.now();
		System.out.println("\n\t\t\t##################################################\n\t\t\t##                                              ##"
				+"\n\t\t\t##\t\tTOKEN NUMBER : "+(tk+(++tid))+"\t\t##"
				+"\n\t\t\t##\t\t\t\t\t\t\t\t\t\t\t\t\t\t##"
				+ "\n\t\t\t##\tRegister No : "+pat2.getPatientId()+"\t\t\t##"
				+"\n\t\t\t##\tCreated On : "+curDate+"\t\t\t##"
				+"\n\t\t\t##################################################");
		
	}


	@SuppressWarnings("deprecation")
	private static void viewLog() {
		FileInputStream fins = null;
		//Declare a data inputstream object
		DataInputStream dins = null;
		
		try{
			fins = new FileInputStream("logfile.txt");
			dins = new DataInputStream(fins);
			//process for read
			System.out.println("\n\t\t\t\t\tUNAUTHORIZED ATTEMPTS");
			while(dins.available()!=0){
				System.out.println("\t\t\t"+dins.readLine());
			}
			dins.close();
		}catch(IOException e){
			System.out.println("Error : "+e);
		}
		
	}
}
