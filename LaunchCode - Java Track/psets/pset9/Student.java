package pset9;

public class Student {

	public String Name;
	public String FirstName;
	public String LastName;
	public int StudentID;
	public int Credits;
	public int ClassesTaken;
	public double QualityScore;
	public double GPA;
	
	public Student(String FirstName, String LastName, int StudentID)
	{
		this.FirstName = FirstName;
		this.LastName = LastName;
		String a = this.FirstName + " " + this.LastName;
		Name = a;
		this.StudentID = StudentID;
		Credits = 0;
		GPA = 0.0;
		ClassesTaken = 0;
		QualityScore = 0.0;
	}
	
	public String getName()
	{
		return this.Name;
	}
	public int getStudentID()
	{
		return this.StudentID;
	}
	public int getCredits()
	{
		return this.Credits;
	}
	public double getGPA()
	{
		return this.GPA;
	}
	
	public String getClassStanding()
	{
		String s1 = "Freshman";
		String s2 = "Sophomore";
		String s3 = "Junior";
		String s4 = "Senior";
		String s5 = "Error in getClassStanding";
		if( this.Credits < 30)
		{
			return s1;
		}
		else if( this.Credits >= 30 && this.Credits < 60)
		{
			return s2;
		}
		else if( this.Credits >= 60 && this.Credits < 90)
		{
			return s3;
		}
		else if( this.Credits >= 90)
		{
			return s4;
		}
		else
		{
			return s5;
		}
	}
		
	public void submitGrade(double grade, int cred)
	{
		double score = grade * cred;
		this.Credits = this.Credits + cred;
		this.QualityScore = this.QualityScore + score;
		double c = this.QualityScore / this.Credits;
		this.GPA = DecimalUtils.round(c,3);
	}
	public double computeTuition()
	{
		int d = this.Credits / 15;
		int f = this.Credits % 15;
		if(f != 0)
		{
			d = d +1;
		}
		double tuition = d * 20000.0;
		return tuition;
	}
	public Student createLegacy(Student g)
	{
		Student legacy = new Student(this.Name, g.Name, this.StudentID + g.StudentID);
		if( g.Credits > this.Credits)
		{
			legacy.Credits = g.Credits;
		}
		else
		{
			legacy.Credits = this.Credits;
		}
		legacy.GPA = (g.GPA + this.GPA) /2;
		return legacy;
		
	}
	
	
	
	public String toString()
	{
		return "Name: " + this.Name + " Student Id: " + this.StudentID + " Credits: " + this.Credits + " GPA: " + this.GPA;
	}
	public static void main(String args[])
	{
		
	}
	
	
	
	
	
}


