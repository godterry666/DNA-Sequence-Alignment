
import java.util.ArrayList;
import java.util.List;




public class DynamicProgramming {
	private int row = 0;
	private int col = 0;
	private int t;
	
	public DynamicProgramming() {
	}
	static List<Matt> list = new ArrayList<Matt>();
	
	public int[][] getMatrix(String DNA1, String DNA2){
		int length = DNA1.length();
		int width = DNA2.length();
		int penalty = 0;
		int mat[][] = new int[length+1][width+1];
		System.out.println("矩阵为：\n");
		for(int i = width; i >= 0; i--) {
			mat[length][i] = 2 * (width - i);
		}
		for(int j = length; j >= 0; j--) {
			mat[j][width] = 2 * (length - j);
		}
		
		for(int i = length - 1;i >= 0;i--) {
			for(int j = width - 1; j >= 0;j--) {
				if(DNA1.charAt(i) == DNA2.charAt(j)) {
					penalty = 0;
				}
				else if(DNA1.charAt(i) != DNA2.charAt(j)) {
					penalty = 1;
				}
				mat[i][j] = Math.min(mat[i+1][j+1]+penalty, Math.min(mat[i+1][j]+2,mat[i][j+1]+2));
			}
		}
		for (int i = 0;i <= length;i++) {
			for (int j = 0;j <= width;j++) {
				System.out.printf("%3d", mat[i][j]);
			}
			System.out.println();
		}
		System.out.println();
		return mat;	
	}
	
	public List<Matt> trace(int mat[][], String DNA1, String DNA2, int sit) {
		if(sit == 0)
		{
			System.out.printf("（%d,%d）",row,col);
			System.out.println();
			list.add(new Matt(row, col));
		}
		else if(sit == 1)
		{
			row++;
			col++;
			System.out.printf("（%d,%d）",row,col);
			System.out.println();
			list.add(new Matt(row, col));
		}
		else if(sit == 2)
		{
			row++;
			System.out.printf("（%d,%d）",row,col);
			System.out.println();
			list.add(new Matt(row, col));
		}
		else if(sit == 3);
		{
			col++;
			System.out.printf("（%d,%d）",row,col);
			System.out.println();
			list.add(new Matt(row, col));
		}
		
		if (mat.length > 1 && mat[0].length > 1) 
		{
			int temp = mat[0][0];
			int penalty = 0;
			if (DNA1.charAt(0) == DNA2.charAt(0)) 
			{
				penalty = 0;
			}
			else 
			{
				penalty = 1;
			}
			
			if (temp == mat[0 + 1][0 + 1] + penalty) 
			{
				String Subrow = DNA1.substring(1);
				String Subcol = DNA2.substring(1);
				trace(getSub(mat, Subrow, Subcol, 1, 1), Subrow, Subcol, 1);
			} 
			else if (temp == mat[0][0 + 1] + 2) {
				String Subrow = DNA1.substring(0);
				String Subcol = DNA2.substring(1);
				trace(getSub(mat, Subrow, Subcol, 0, 1), Subrow, Subcol, 2);
			} 
			else if (temp == mat[0 + 1][0] + 2) {
				String Subrow = DNA1.substring(1);
				String Subcol = DNA2.substring(0);
				trace(getSub(mat, Subrow, Subcol, 1, 0), Subrow, Subcol, 3);
			}	
		}
		return list;
	}
	
	public int[][] getSub(int mat[][], String subDNA1, String subDNA2, int rstart, int cstart){
		int sublength = subDNA1.length();
		int subwidth = subDNA2.length();
		int[][] submat = new int[sublength + 1][subwidth + 1];
		for (int i = 0; i <= sublength; i++) {
			for (int j = 0; j <= subwidth; j++) {
				submat[i][j] = mat[rstart + i][cstart + j];
			}
		}
		return submat;
	}
	
	public char[][]getSequence(String DNA1, String DNA2, List<Matt> list){
		int length = Math.max(DNA1.length(), DNA2.length());
		char[][] sMatt = new char[2][length+1];
		
		sMatt[0][0] = DNA1.charAt(list.get(0).getRow());
		sMatt[1][0] = DNA1.charAt(list.get(0).getRow());
		for (int i = 1; i < list.size()-1; i++) {
			int iNew = list.get(i).getRow();
			int jNew = list.get(i).getCol();
			
			if ((iNew != list.get(i - 1).getRow()) && (jNew != list.get(i - 1).getCol())) 
			{
				sMatt[0][i] = DNA1.charAt(jNew);
				sMatt[1][i] = DNA2.charAt(iNew);
			} 
			else if ((iNew == list.get(i - 1).getRow()) && (jNew != list.get(i - 1).getCol())) {
				sMatt[0][i] = DNA1.charAt(jNew);
				sMatt[1][i] = '#';
			}
			else if ((iNew != list.get(i - 1).getRow()) && (jNew == list.get(i - 1).getCol())) 
			{
				sMatt[0][i] = '-';
				sMatt[1][i] = DNA2.charAt(iNew);
			}
		}

		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < length; j++) {
				System.out.print(sMatt[i][j] + " ");
			}
			System.out.println();
		}
		return sMatt;
	}
	
	class Matt {
		int row;
		int col;
 
		public Matt(int row, int col) {
			this.row = row;
			this.col = col;
		}
		public int getRow() {
			return row;
		}
		public int getCol() {
			return col;
		}
	}
	public static void main(String[] args) {
		String String1 = "AACAGTTACC";
		String String2 = "TAAGGTCA";
		DynamicProgramming seq = new DynamicProgramming();
		int[][] costArray = seq.getMatrix(String1, String2);
		 List<Matt> list = seq.trace(costArray, String1, String2, 0);
		char[][] sMatt = seq.getSequence(String1, String2, list);
		System.out.println("对齐序列为:\n" + sMatt);
	}
}

	
