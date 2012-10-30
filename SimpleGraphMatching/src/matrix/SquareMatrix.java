package matrix;

public class SquareMatrix extends Matrix {

	public SquareMatrix(int size) {
		super(size, size);
	}
	
	public int getSize() {
		return this.getNumberOfRows();
	}
	
}
