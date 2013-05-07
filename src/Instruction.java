public class Instruction {
	public static final int TYPE_READ = 0;
	public static final int TYPE_WRITE = 1;
	public static final int TYPE_FETCH = 2;

	private long addr;
	private String hexAddr;
	private String binAddr;
	
	private int labelNum;
	private String label;
	

	/*----------------------------------------------------------------
	 |  Method: Constructor
	 |
	 |  Purpose: Creates Instruction Object and converts all values to
	 |           binary/int/long
	 |
	 |  Parameters:  None
	 |
	 |  Returns:  Instruction
	 |
	 *-------------------------------------------------------------------*/
	public Instruction(String label, String hexAddr) {
		this.label = label;
		this.hexAddr = hexAddr;

		labelNum = Integer.parseInt(label);
		addr = Long.parseLong(hexAddr,16);
		binAddr = Long.toBinaryString(addr);
		
		// Make sure bin addr is 32 bits
		while(binAddr.length() < 32){
			binAddr = "0" + binAddr;
		}
	}

	/*
	 * Accessors
	 * 
	 */
	public int getLabelNum() {
		return labelNum;
	}

	public void setLabelNum(int labelNum) {
		this.labelNum = labelNum;
	}

	public long getAddr() {
		return addr;
	}

	public void setAddr(long addr) {
		this.addr = addr;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getHexAddr() {
		return hexAddr;
	}

	public void setHexAddr(String hexAddr) {
		this.hexAddr = hexAddr;
	}

	public String getBinAddr() {
		return binAddr;
	}

	public void setBinAddr(String binAddr) {
		this.binAddr = binAddr;
	}

}
