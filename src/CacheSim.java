import java.util.LinkedList;

/*=============================================================================
 |      Program:  CacheSim
 |
 |       Author:  Daniel Bacungan and Cody Warren
 |     Language:  Java
 |   To Compile:  javac Main.java
 |
 |      Started:  April 25, 2013
 |
 +-----------------------------------------------------------------------------
 |
 |  Description:  A cache simulator written in java.  It takes an input file
 |                with cache commands and gives a statistical output with
 |                reads, writes, hits, misses, etc.
 |
 |  Required Features Not Included:  
 |
 |  Known Bugs:  
 |
 +===========================================================================*/

public class CacheSim {

	private static int assoc = 1;
	private static int blkSize = 1;
	private static int numSets = 1024;

	private static Cache mainCache;
	private static String filename;
	private static LinkedList<Instruction> instructions = new LinkedList<Instruction>();

	public static void main(String[] args) {
		InstructionParser insnMem = new InstructionParser();

		parseArgs(args);

		System.out.println("Creating Cache");
		mainCache = new Cache(assoc, blkSize, numSets);

		if (!insnMem.open(filename)) {
			showHelp();
			System.exit(0);
		}

		System.out.println("Loading instructions from file...");
		instructions = insnMem.read();

		System.out.println("Simulating Cache");
		exeInstructions();
	}

	/*----------------------------------------------------------------
	 *  Function: parseArgs
	 *
	 *  Purpose: parses main arguments
	 *
	 *  Parameters:  main argumets
	 *
	 *  Returns:  void
	 *
	 *-------------------------------------------------------------------*/
	public static void parseArgs(String[] args) {
		char option;
		int val;
		String s;
		for (int i = 0; i < args.length; i++) {
			s = args[i];

			if (s.startsWith("-"))
				option = s.charAt(1);
			else
				option = '!';

			switch (option) {
			case 'a':
				val = Integer.parseInt(args[i + 1]);

				if (!Utility.isPow2(val))
					showHelp();

				assoc = val;
				i++;
				break;
			case 'b':
				val = Integer.parseInt(args[i + 1]);

				if (!Utility.isPow2(val))
					showHelp();

				blkSize = val;
				i++;
				break;
			case 'h':
				showHelp();
				break;
			case 'n':
				val = Integer.parseInt(args[i + 1]);

				if (!Utility.isPow2(val))
					showHelp();

				numSets = val;
				i++;
				break;
			case '!':
				filename = args[i];
				break;
			default:
				System.out.println("Error: Invalid Option");
				showHelp();
				break;
			}
		}
	}

	/*---------------------------------------------------------------------
	 * 
	 *  Function: showHelp
	 *
	 *  Purpose: Shows help dialog
	 *
	 *  Parameters:  None
	 *
	 *  Returns:  None
	 *
	 *-------------------------------------------------------------------*/
	public static void showHelp() {
		System.out.println();
		System.out.println("Usage: cachesim [options] <input filename>");
		System.out.println();
		System.out.println("Options: ");
		System.out.println("  -a <associativity>\t Sets associativity");
		System.out.println("  -b <block size>   \t Sets block size");
		System.out.println("  -h                \t Show Help");
		System.out.println("  -n <num sets>     \t Sets the number of sets");
		System.out.println();
		System.out.println("All parameters must be positive powers of 2");
		System.out.println();
		System.exit(0);
	}

	/*---------------------------------------------------------------------
	 * 
	 *  Function: exeInstructions
	 *
	 *  Purpose: Executes all instructions and prints statistics
	 *
	 *  Parameters: None
	 *
	 *  Returns:  @retur void
	 *
	 *-------------------------------------------------------------------*/
	public static void exeInstructions() {

		// Instruction Stats
		int numInsns = instructions.size();
		int readCount = 0;
		int writeCount = 0;
		int fetchCount = 0;

		// Read Stats
		int readHit = 0;
		int readMiss = 0;

		// Write Stats
		int writeHit = 0;
		int writeMiss = 0;

		// Hit/Miss Stats
		int totalHit = 0;
		int totalMiss = 0;
		double missRate = 0;
		double writeMissRate = 0;
		double readMissRate = 0;

		int i;

		Instruction current;

		for (i = 0; i < numInsns; i++) {
			current = instructions.get(i);

			// System.out.println(current.getLabelNum());
			// System.out.println(current.getBinAddr());

			switch (current.getLabelNum()) {
			case Instruction.TYPE_READ:
				readCount++;
				readHit += mainCache.read(current);
				break;
			case Instruction.TYPE_WRITE:
				writeCount++;
				writeHit += mainCache.write(current);
				break;
			case Instruction.TYPE_FETCH:
				fetchCount++;
				break;
			default:
				System.out.println("Unknown Instruction");
				break;
			}

			// Updates on progress if large simulation
			if (numInsns > 100000) {
				if (i == (int) (numInsns / 4)) {
					System.out.println("25% Done");
				}

				if (i == (int) (numInsns / 2)) {
					System.out.println("50% Done");
				}

				if (i == (int) (3 * numInsns / 4)) {
					System.out.println("75% Done");
				}
			}
		}

		// Compute Statistical Variables
		readMiss = readCount - readHit;
		writeMiss = writeCount - writeHit;

		totalHit = readHit + writeHit;
		totalMiss = readMiss + writeMiss;

		missRate = (double) totalMiss / (readCount + writeCount);
		readMissRate = (double) readMiss / readCount;
		writeMissRate = (double) writeMiss / writeCount;

		// Output Statistics
		System.out.println();
		System.out.println("Results: ");
		System.out.println();
		System.out.println("Accesses: " + numInsns);
		System.out.println("   Reads: " + readCount);
		System.out.println("  Writes: " + writeCount);
		System.out.println(" Fetches: " + fetchCount);
		System.out.println();
		System.out.println("Read Misses: " + readMiss);
		System.out.println("  Read Hits: " + readHit);
		System.out.println(" Read Total: " + readCount);
		System.out.println();
		System.out.println("Write Misses: " + writeMiss);
		System.out.println("  Write Hits: " + writeHit);
		System.out.println(" Write Total: " + writeCount);
		System.out.println(" Write Backs: " + Set.writeBackCount);
		System.out.println();
		System.out.println("     Total Hits: " + totalHit);
		System.out.println("   Total Misses: " + totalMiss);
		System.out.println(" Read Miss Rate: " + readMissRate);
		System.out.println("Write Miss Rate: " + writeMissRate);
		System.out.println("Total Miss Rate: " + missRate);
		System.out.println();
	}

}