/** Simpler demo of the In class. */
public class BasicInDemo {
	public static void main(final String[] args) {
		final In in = new In("BasicInDemo_input_file.txt");

		/*
		 * Every time you call a read method from the In class, it reads the next thing
		 * from the file, assuming it is of the specified type.
		 */

		/* Compare the calls below to the contents of BasicInDemo_input_file.txt */

		final int firstItemInFile = in.readInt();
		final double secondItemInFile = in.readDouble();
		final String thirdItemInFile = in.readString();
		final String fourthItemInFile = in.readString();
		final double fifthItemInFile = in.readDouble();

		System.out.println("The file contained "  + firstItemInFile + ", " + 
			               secondItemInFile + ", " + thirdItemInFile + ", " +
			               fourthItemInFile + ", and " + fifthItemInFile);
	}
} 