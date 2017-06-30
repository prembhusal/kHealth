package org.knoesis.health.dataHolders;




/*
 * This class will hold the information for the user's name, Date of Birth, gender, zipCode of location.
 */
public class UserDataHolder {
	// member variable for the name
	private String name;
	//member variable for date of birth
	//private String dob;
	//member variable for age
	private int age;
	//Member variable for gender.
	private String gender;
	//Member variable for zipcode.
	private int zipCode;
	//Member variable for Other Zip Code used by user.
	private int otherZipCode;
	//Member variable for Albuterol.
	private int albuterol;

	private int ventolin;

	private int proair;

	private int xoponex;

	private int atrovent;

	private int combivent;

	private int duoneb;

	private int dulera;

	private int symbicort;

	private int advair;

	private int flovent;

	private int asmanex;

	private int qvar;

	private int pulmicort;

	private int budenoside;

	private int prednisone;

	private int prednisolone;

	private int orapred;

	private int montelukast;

	private int singulair;



	// This is the constructor for the class
	public UserDataHolder(String name, int age, String gender, int zipCode,int otherZipCode,int albuterol,int ventolin,
						  int proair, int xoponex, int atrovent, int combivent, int duoneb, int dulera, int symbicort, int advair,
						  int flovent,int asmanex, int qvar, int pulmicort, int budenoside, int prednisone, int prednisolone,
						  int orapred, int montelukast, int singulair) {
		this.name = name;
		//this.dob=dob;
		this.age=age;
		this.gender=gender;
		this.zipCode=zipCode;
		this.otherZipCode = otherZipCode;
		this.albuterol = albuterol;
		this.ventolin = ventolin;
		this. proair = proair;
		this.xoponex = xoponex;
		this.atrovent = atrovent;
		this.combivent = combivent;
		this.duoneb = duoneb;
		this.dulera = dulera;
		this.symbicort = symbicort;
		this.advair = advair;
		this.flovent = flovent;
		this.asmanex = asmanex ;
		this.qvar = qvar;
		this.pulmicort = pulmicort;
		this.budenoside = budenoside;
		this.prednisone = prednisone;
		this.prednisolone = prednisolone;
		this.orapred = orapred;
		this.montelukast = montelukast;
		this.singulair = singulair;
	}
	//This is the constructor for the class with only one parameter
	public UserDataHolder(String name)
	{
		this.name=name;
	}

	// This method returns the users name
	public String getName() {
		return this.name;
	}
	// This method returns the user age
	public int getAge() {
		return age;
	}
	//This method set the user age
	public void setAge(int age) {
		this.age = age;
	}

	//This method returns the users DOB
	/*public String getDOB()
	{
		return this.dob;
	}*/
	//This method returns the user's gender.
	public String getGender()
	{
		return this.gender;
	}
	//This method returns the user's ZIPCODE.
	public int getZipCode() {return this.zipCode; }

	public int getOtherZipCode() {return this.otherZipCode ;}
	//This method set the user's name
	public void  setName(String name){
		this.name=name;
	}
	//This method set the user's DOB
//	public void setDOB(String dob)
//	{
//		this.dob=dob;
//	}
	//This method set the user's gender
	public void setGender(String gender){
		this.gender=gender;
	}
	//This method sets the user's ZIPCODE.
	public void setZipCode(int zipCode) { this.zipCode=zipCode; }
	public void setOtherZipCode(int otherZipCode){this.otherZipCode = otherZipCode;}
	public int getAlbuterol() {
		return albuterol;
	}

	public void setAlbuterol(int albuterol) {
		this.albuterol = albuterol;
	}

	public int getVentolin() {
		return ventolin;
	}

	public void setVentolin(int ventolin) {
		this.ventolin = ventolin;
	}

	public int getProair() {
		return proair;
	}

	public void setProair(int proair) {
		this.proair = proair;
	}

	public int getXoponex() {
		return xoponex;
	}

	public void setXoponex(int xoponex) {
		this.xoponex = xoponex;
	}

	public int getAtrovent() {
		return atrovent;
	}

	public void setAtrovent(int atrovent) {
		this.atrovent = atrovent;
	}

	public int getCombivent() {
		return combivent;
	}

	public void setCombivent(int combivent) {
		this.combivent = combivent;
	}

	public int getDuoneb() {
		return duoneb;
	}

	public void setDuoneb(int duoneb) {
		this.duoneb = duoneb;
	}

	public int getDulera() {
		return dulera;
	}

	public void setDulera(int dulera) {
		this.dulera = dulera;
	}

	public int getSymbicort() {
		return symbicort;
	}

	public void setSymbicort(int symbicort) {
		this.symbicort = symbicort;
	}

	public int getAdvair() {
		return advair;
	}

	public void setAdvair(int advair) {
		this.advair = advair;
	}

	public int getFlovent() {
		return flovent;
	}

	public void setFlovent(int flovent) {
		this.flovent = flovent;
	}

	public int getAsmanex() {
		return asmanex;
	}

	public void setAsmanex(int asmanex) {
		this.asmanex = asmanex;
	}

	public int getQvar() {
		return qvar;
	}

	public void setQvar(int qvar) {
		this.qvar = qvar;
	}

	public int getPulmicort() {
		return pulmicort;
	}

	public void setPulmicort(int pulmicort) {
		this.pulmicort = pulmicort;
	}

	public int getBudenoside() {
		return budenoside;
	}

	public void setBudenoside(int budenoside) {
		this.budenoside = budenoside;
	}

	public int getPrednisone() {
		return prednisone;
	}

	public void setPrednisone(int prednisone) {
		this.prednisone = prednisone;
	}

	public int getPrednisolone() {
		return prednisolone;
	}

	public void setPrednisolone(int prednisolone) {
		this.prednisolone = prednisolone;
	}

	public int getOrapred() {
		return orapred;
	}

	public void setOrapred(int orapred) {
		this.orapred = orapred;
	}

	public int getMontelukast() {
		return montelukast;
	}

	public void setMontelukast(int montelukast) {
		this.montelukast = montelukast;
	}

	public int getSingulair() {
		return singulair;
	}

	public void setSingulair(int singulair) {
		this.singulair = singulair;
	}






}
