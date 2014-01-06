//This class reflects the back end table
public class ABTGrid {
	
	String BusinessDate;
	String BookId;
	String Symbol;
	int Account;
	int Age;
	float ADPposition;
	float IDposition;
	float Difference;
	
	
	public void setBusinessDate(String BusinessDate)
	{
		this.BusinessDate=BusinessDate;
	}
	public String getBusinessDate(){return BusinessDate;}
	
	public void setBookId(String BookId)
	{
		this.BookId=BookId;
	}
	public String getBookId(){return BookId;}
	
	public void setSymbol(String Symbol)
	{
		this.Symbol=Symbol;
	}
	public String getSymbol(){return Symbol;}
	
	public void setAccount(int Account)
	{
		this.Account=Account;
	}
	public int getAccount(){return Account;}
	
	public void setAge(int Age)
	{
		this.Age=Age;
	}
	public int getAge(){return Age;}
	
	public void setADPposition(float ADPposition)
	{
		this.ADPposition=ADPposition;
	}
	public float getADPposition(){return ADPposition;}
	
	public void setIDposition(float IDposition)
	{
		this.IDposition=IDposition;
	}
	public float getIDposition(){return IDposition;}
	
	public void setDifference(float Difference)
	{
		this.Difference=Difference;
	}
	public float getDifference(){return Difference;}
	
	
}
