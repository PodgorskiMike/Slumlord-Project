
public class Rectangle {
	private int length;
	private int height;
	
	public Rectangle(int length, int height)
	{
		this.length = length;
		this.height = height;
	}
	
	public int area()
	{
		int area = this.length * this.height;
		return area;
	}
	public int perimeter()
	{
		int perimeter = (this.length + this.height) * 2;
		return perimeter;
	}
	public boolean bigger(int length, int height)
	{
		int area1 = length * height;
		int areaRectangle = this.length * this.height;
		if(areaRectangle > area1)
			return true;
		else
			return false;
	}
	public boolean isSquare()
	{
		if (this.length == this.height)
			return true;
		else
			return false;
	}
	public static void main(String args[])
	{
		Rectangle myRectangle = new Rectangle(4,5);
		System.out.println(myRectangle.area());
		System.out.println(myRectangle.perimeter());
		System.out.println(myRectangle.bigger(6, 6));
		System.out.println(myRectangle.isSquare());
	}

}
