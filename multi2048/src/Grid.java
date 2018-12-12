public class Grid {
	private static final int size = 4;
	private static final int startTile = 2;
	private int numAvailableCells;
	private int[][] cells;
	private int bestScore;
	private int value;
	
	public Grid() {
		numAvailableCells = size * size;
		cells = new int[size][size];
		initGrid();
		bestScore = 0;
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
		
		if(!isFull()) {
			addRandomlyTile(); //if it can't put a tile in cell, try again!.
		}
	}
	
	public void setCells(int row, int col, int value) {
		cells[row][col] = value;
	}
	
	public int[][] getCells() {
		return cells;
	}
	
	public int getBestScore() {
		return bestScore;
	}
	
	private boolean isEmpty(int row, int col) {
		if(cells[row][col] > 0) return false;
		return true;
	}
	
	public boolean isFull() {
		return numAvailableCells == 0;
	}

	public void _merge(int dir, int y, int x) {
		final int[] dx = {0, 0, -1, 1};
		final int[] dy = {1, -1, 0, 0};

		if(cells[y][x] == 0) { //don't need to move
			return;
		}

		int nextY;
		int nextX;
		while(true) {
			nextY = y + dy[dir];
			nextX = x + dx[dir];

			if(nextY < 0 || nextY >= size ||
			   nextX < 0 || nextX >= size) {
				return;
			}

			if(cells[nextY][nextX] == 0) {
				cells[nextY][nextX] = cells[y][x];
				cells[y][x] = 0;
			} else {
				break;
			}

			y = nextY;
			x = nextX;
		}

		if(cells[nextY][nextX] == cells[y][x]) {
			cells[nextY][nextX] = cells[y][x] * 2;
			value += cells[nextY][nextX];
			cells[y][x] = 0;
			numAvailableCells++;
		}
	}
	
	public void merge(String strDir) {
		int intDir = -1;
		value = 0;
		
		if(strDir.equals("ArrowDown")) {
			intDir = 0;
		} else if(strDir.equals("ArrowUp")) {
			intDir = 1;
		} else if(strDir.equals("ArrowLeft")) {
			intDir = 2;
		} else if(strDir.equals("ArrowRight")){ //equals ArrowRight
			intDir = 3;
		} else {
			return;
		}
		
		if(intDir == 0) { //Down
			for (int y = size - 1; y >= 0; y--) {
				for (int x = 0; x < size; x++) {
					_merge(intDir, y, x);
				}
			}
		} else if(intDir == 3) { //Right
			for (int y = 0; y < size; y++) {
				for(int x = size - 1; x >= 0; x--) {
					_merge(intDir, y, x);
				}
			}
		} else { //Up, Left
			for(int y = 0; y < size; y++) {
				for(int x = 0; x < size; x++) {
					_merge(intDir, y, x);
				}
			}
		}
		
		bestScore += value;
	}
}
