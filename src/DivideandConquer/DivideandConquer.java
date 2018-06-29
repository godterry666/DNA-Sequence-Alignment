package DivideandConquer;

import java.util.ArrayList;
import java.util.List;

public class DivideandConquer {
	public DivideandConquer() {
	}

	static List<Sequence> list = new ArrayList<Sequence>();
	public int getCost(String DNA1, String DNA2, int i, int j) {
		int penalty = 0;
		int cost = 0;
		if (i == DNA1.length()) 
		{
			cost = 2*(DNA2.length() - j);
		} 
		else if (j == DNA2.length())
		{
			cost = 2*(DNA1.length() - i);
		} 
		else 
		{
			if (DNA1.charAt(i) == DNA2.charAt(j)) 
			{
				penalty = 0;
			} 
			else 
			{
				penalty = 1;
			}
		}
		
		
		if (i <= DNA1.length() - 2 && j <= DNA2.length()-2)
		{
			cost = Math.min(getCost(DNA1,DNA2, ++i, ++j) + penalty,Math.min(getCost(DNA1,DNA2, i, ++j) + 2,getCost(DNA1,DNA2, ++i, j)+ 2));
		}
		return cost;
	}

	class Sequence {
		private int row;
		private int col;
		private int opt;
		private Sequence(int row, int col, int opt) {
			this.row = row;
			this.col = col;
			this.opt = opt;
		}
		public int getRow() {
			return row;
		}
		public int getCol() {
			return col;
		}
		public int getOpt() {
			return opt;
		}	
	}
public static void main(String[] args) {
	/*String DNA1 = "AACAGTTACC";
	String DNA2 = "TAAGGTCA";*/
	String DNA1 = "AACAGTTACCATCGAAACAGAA";
	String DNA2 = "TAAGGTCACGACTAAGTAA";
	long startTime = System.currentTimeMillis();
	DivideandConquer divide = new DivideandConquer();
	int cost = divide.getCost(DNA1, DNA2, 0, 0);
	System.out.println("最优消耗:" + cost);
	long endTime = System.currentTimeMillis();
	System.out.println("程序运行时间: " + (endTime - startTime) + "ms");
}
}

