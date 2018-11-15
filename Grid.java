public class Grid {
	private static final int size = 4;
	private int[][] cells;
	
	public Grid() {
		cells = new int[size][size];
	}
	
	public void setCells(int row, int col, int value) {
		cells[row][col] = value;
	}
	
	public int[][] getCells() {
		return cells;
	}
}
