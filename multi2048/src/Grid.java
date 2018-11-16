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
	
	public void merge(String strDir) {
		final int[] dx = {0, 0, -1, 1};
		final int[] dy = {1, -1, 0, 0};
		int intDir;
		
		if(strDir.equals("ArrowDown")) {
			intDir = 0;
		} else if(strDir.equals("ArrowUp")) {
			intDir = 1;
		} else if(strDir.equals("ArrowLeft")) {
			intDir = 2;
		} else { //equals ArrowRight
			intDir = 3;
		}
		
		while (true) {
			int cntMove = 0;
			
			for (int y = 0; y < size; y++) {
				for (int x = 0; x < size; x++) {
					int nextX = x + dx[intDir];
					int nextY = y + dy[intDir];

					if (nextX < 0 || nextX >= size || nextY < 0 || nextY >= size) { // index exception
						continue;
					}
					
					if(cells[y][x] == 0) { //don't need to move
						continue;
					}
					
					if(cells[nextY][nextX] == cells[y][x]) {
						cells[nextY][nextX] *= 2;
						cells[y][x] = 0;
						cntMove++;
					} else if(cells[nextY][nextX] == 0) {
						cells[nextY][nextX] = cells[y][x];
						cells[y][x] = 0;
						cntMove++;
					}
				}
			}
			
			if(cntMove == 0) {
				break;
			}
		}
	}
}
