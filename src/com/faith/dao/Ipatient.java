package com.faith.dao;

import java.util.List;

import com.faith.bean.Patients;

public interface Ipatient {
	//insert entered data to table
	public boolean addPatientToTable(Patients pat) throws Exception;
	//searching
	public List<Patients> findPatientByPhone(String phone) throws Exception;
	//read from table and display
	public List<Patients> readPatientFromTable() throws Exception;
	//searching
	public Patients findPatientById(String patId) throws Exception;
	//update employee status
	public boolean patStatusUpdate(Patients pat, int patId) throws Exception;
	//Update employee details
	public boolean updatePatTable(Patients pat, int patId, int opt) throws Exception;
}
