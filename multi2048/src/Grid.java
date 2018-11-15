public class Grid {
	private static final int size = 4;
	private static final int startTile = 2;
	private int numAvailableCells;
	private int[][] cells;
	
	public Grid() {
		numAvailableCells = size * size;
		cells = new int[size][size];
		initGrid();
	}
	
	private void initGrid() {
		for(int i = 0; i < startTile; i++) {
			addRandomlyTile();
		}
	}
	
	public void addRandomlyTile() {
		int randRow = (int)(Math.random() * size);
		int randCol = (int)(Math.random() * size);
		
		if(isEmpty(randRow, randCol)) {
			numAvailableCells--;
			setCells(randRow, randCol, Math.random() < 0.9 ? 2 : 4);
			return;
		}
		
		addRandomlyTile(); //if it can't put a tile in cell, try again!.
	}
	
	public void setCells(int row, int col, int value) {
		cells[row][col] = value;
	}
	
	public int[][] getCells() {
		return cells;
	}
	
	private boolean isEmpty(int row, int col) {
		if(cells[row][col] > 0) return false;
		return true;
	}
	
	public boolean isFull() {
		return numAvailableCells != 0;
	}
}
