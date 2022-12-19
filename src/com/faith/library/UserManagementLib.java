package com.faith.library;

import java.sql.SQLException;
import java.util.Scanner;

import com.faith.bean.User;
import com.faith.dao.IuserDaoService;
import com.faith.dao.UserDaoImplementation;

public class UserManagementLib {
	//dao service
		static IuserDaoService daoService = new UserDaoImplementation();
		static PatientManagementLib patLib = new PatientManagementLib();
		//login menu
		public static void loginMenu() throws Exception{
			User user = new User();
			int roleIdFromDb = 0;
			int i = 3;
			System.out.println("\n\t\t\tRECEPTION LOGIN");
			do{
				Scanner input = new Scanner(System.in);
				System.out.println("\n\t\t\tEnter the username : ");
				user.setUserName(input.nextLine());
				
				System.out.println("\t\t\tEnter password : ");
				user.setPassWord(input.nextLine());
				
				try{
					if(daoService.checkCredentials(user)){
						//checking role here
						roleIdFromDb = daoService.getRoleIdFrom(user);
						//go to dashboard
						if(roleIdFromDb == 1)
							receptionistMenu();
						/*else if(roleIdFromDb == 2)
							adminMenu();
						else if(roleIdFromDb == 3)
							labTechnicianMenu();
						else if(roleIdFromDb == 4)
							pharmacistMenu();*/
						else {
							System.out.println("\n\t\t\tSorry you are not authorized..!");
							input.close();
						}
					}else{
						i--;
						if(i==0){
							System.out.println("\n\t\t\tNo more attempts allowed...\n\n\t\t\tTry again after 24 hours...!");
							System.exit(0);
						}else
							System.out.println("\n\t\t\tUnauthorized attempt detected..! Only "+i+" attempts left\n");
					}
				}catch(SQLException e){
					e.printStackTrace();
				}
			}while(true);
		}
		private static void receptionistMenu() throws Exception {
			System.out.println("\n\t\t\tWELCOME RECEPTIONIST.....");
			PatientManagementLib.receptionManagementMenu();
		}
		/*
		private static void adminMenu() throws InterruptedException {
			System.out.println("\t\t\tWelcome Admin....");
		}
		private static void pharmacistMenu() {
			System.out.println("\t\t\tWelcome Pharmacist....");
			
		}

		private static void labTechnicianMenu() {
			System.out.println("\t\t\tWelcome Labtechnician....");
			
		}*/
		
}
