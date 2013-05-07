import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;

public class InstructionParser {

	private String filename;

	private FileInputStream fileIn = null;
	private BufferedReader fileReader = null;

	// Empty Constructor
	public InstructionParser() {
	}

	/*----------------------------------------------------------------
	 |  Method: Open
	 |
	 |  Purpose: Opens files and readers
	 |
	 |  Parameters:  Filename
	 |
	 |  Returns:  success/fail boolean
	 |
	 *-------------------------------------------------------------------*/
	public boolean open(String filename) {
		this.filename = filename;

		if (this.filename == null) {
			System.out.println("Error: Can't Find File");
			return (false);
		}

		try {
			fileIn = new FileInputStream(this.filename);
		} catch (FileNotFoundException e) {
			System.out.println("Error: Can't Find File");
			return (false);
		}
		fileReader = new BufferedReader(new InputStreamReader(fileIn));

		return (true);
	}

	/*----------------------------------------------------------------
	 |  Method: Read
	 |
	 |  Purpose: Reads all data from file
	 |
	 |  Parameters:  None
	 |
	 |  Returns:  Instruction Queue with all of read data
	 |
	 *-------------------------------------------------------------------*/
	public LinkedList<Instruction> read() {
		String strLine;
		LinkedList<Instruction> instructionQueue = new LinkedList<Instruction>();
		Instruction temp = null;

		try {
			while ((strLine = fileReader.readLine()) != null) {
				String[] parts = strLine.split(" "); // read line from file and
												     // split
				
				String label = parts[0];   // get label (read, write, fetch)
				String hexaddr = parts[1]; // get address in hex
				temp = new Instruction(label, hexaddr);
				instructionQueue.add(temp);
			}
		} catch (IOException e) {
			e.printStackTrace();
			return (null);
		}

		return (instructionQueue);
	}

	/*----------------------------------------------------------------
	 |  Method: Close
	 |
	 |  Purpose: Closes Readers and Files
	 |
	 |  Parameters:  None
	 |
	 |  Returns:  None
	 |
	 *-------------------------------------------------------------------*/
	public void close() {
		try {
			fileReader.close();
			fileIn.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
