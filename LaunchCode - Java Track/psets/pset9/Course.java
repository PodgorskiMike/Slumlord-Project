package pset9;

public class Course {

	String Name;
	int Credits;
	int NumberofSeats;
	int RemainingSeats;
	String RosterofStudents;
	double GPA;
	
	public Course(String Name, int Credits, int NumberofSeats)
	{
		this.Name = Name;
		this.Credits = Credits;
		this.NumberofSeats = NumberofSeats;
		this.RemainingSeats = NumberofSeats;
		this.RosterofStudents = "";
		this.GPA = 0;
	}
	public String getName()
	{
		return this.Name;
	}
	public int getRemainingSeats()
	{
		return this.RemainingSeats;
	}
	public String toString()
	{
		return "Name: " + this.Name + " Credits: " + this.Credits + " Number of Seats: " + this.NumberofSeats + " Seats Remaining: " + this.RemainingSeats;
	}
	public boolean addStudent(Student h)
	{
		if(this.RemainingSeats <= 0)
		{
			return false;
		}
		int i = this.RosterofStudents.indexOf(h.Name);
		if(i == -1)
		{
			this.RosterofStudents = RosterofStudents + "h.Name";
			this.RemainingSeats = RemainingSeats -1;
			this.GPA = this.GPA  + h.GPA;
			return true;
		}
		else
		{
			return false;
		}
	}
	public String generateRoster()
	{
		return RosterofStudents;
	}
	public double averageGPA()
	{
		int students = this.NumberofSeats - this.RemainingSeats;
		double k = this.GPA / students;
		return k;
	}
	
}
