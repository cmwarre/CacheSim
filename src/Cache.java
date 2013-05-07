public class Cache {

	// debug flag
	private boolean D = true;
	
	private int tagLen;
	private int indexLen;
	private int blockLen;
	

	private Set[] sets;



	/*---------------------------------------------------------------------
	 * 
	 *  Function: Cache
	 *
	 *  Purpose: Creates Cache object and defines index lengths
	 *           tag lengths, block lengths
	 *
	 *  Parameters: @param
	 *				@param assoc
	 *				@param blkSize
	 *				@param numSets
	 *
	 *-------------------------------------------------------------------*/
	public Cache(int assoc, int blkSize, int numSets) {
		sets = new Set[numSets];

		// Initialize all sets
		for (int i = 0; i < sets.length; i++) {
			sets[i] = new Set(assoc);
		}

		// Find item lengths for given cache arch.
		// Assumes 32-bit address
		indexLen = Utility.log2(numSets); // Address to set
		blockLen = Utility.log2(blkSize) + 2; // Address to block byte
		tagLen = 32 - (indexLen + blockLen);
		
		if(D){
			System.out.println("Index Length: " + indexLen);
			System.out.println("Block Length:" + blockLen);
			System.out.println("Tag Length: " + tagLen);
		}
	}

	/*---------------------------------------------------------------------
	 * 
	 *  Function: read
	 *
	 *  Purpose: Performs a read on the cache.  Finds set index and reads
	 *
	 *  Parameters: @param insn
	 *
	 *  Returns:  @return int
	 *
	 *-------------------------------------------------------------------*/
	public int read(Instruction insn) {
		
		String address = insn.getBinAddr();

		String sTag = address.substring(0, tagLen-1);
		String sIndex = address.substring(tagLen, tagLen+indexLen);

		int index = Integer.parseInt(sIndex, 2);
		int tag = Integer.parseInt(sTag, 2);

		boolean found = false;

		// Search indexed set for tag
		found = sets[index].search(tag, false);

		if (found) {
			incLRU();
			return (1);
		} else {
			sets[index].handleMiss(tag);
			incLRU();
			return (0);
		}
	}

	/*---------------------------------------------------------------------
	 * 
	 *  Function: incLRU
	 *
	 *  Purpose: Increments LRU counter in each set
	 *
	 *  Parameters: 
	 *
	 *  Returns:  @return void
	 *
	 *-------------------------------------------------------------------*/
	public void incLRU() {
		for (int i = 0; i < sets.length; i++) {
			sets[i].incLRU();
		}
	}

	/*---------------------------------------------------------------------
	 * 
	 *  Function: write
	 *
	 *  Purpose: Performs a write on the cache.  Finds index and writes.
	 *
	 *  Parameters: @param insn
	 *
	 *  Returns:  @return int
	 *
	 *-------------------------------------------------------------------*/
	public int write(Instruction insn) {
		String address = insn.getBinAddr();

		String sTag = address.substring(0, tagLen-1);
		String sIndex = address.substring(tagLen, tagLen+indexLen);

		int index = Integer.parseInt(sIndex, 2);
		int tag = Integer.parseInt(sTag, 2);

		boolean found = false;

		// Search indexed set for tag
		found = sets[index].search(tag, true);

		if (found) {
			incLRU();
			return (1);
		} else {
			sets[index].handleMiss(tag);
			incLRU();
			return (0);
		}
	}

}
