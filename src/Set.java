import java.util.Arrays;

public class Set {

	public static int writeBackCount = 0;
	
	public Block blocks[];

	/*---------------------------------------------------------------------
	 * 
	 *  Function: Set
	 *
	 *  Purpose: Builds a set of cache blocks
	 *
	 *  Parameters: @param numBlks
	 *
	 *-------------------------------------------------------------------*/
	public Set(int numBlks) {
		blocks = new Block[numBlks];

		for (int i = 0; i < blocks.length; i++) {
			blocks[i] = new Block();
		}

	}

	/*---------------------------------------------------------------------
	 * 
	 *  Function: search
	 *
	 *  Purpose: Searches set for specified tag.  If writing, the dirty 
	 *           bit is set
	 *
	 *  Parameters: 
	 *             @param tag
	 *             @param wrEn
	 *             @return
	 *
	 *  Returns:  @return boolean
	 *
	 *-------------------------------------------------------------------*/
	public boolean search(int tag, boolean wrEn) {

		for (int i = 0; i < blocks.length; i++) {
			if (blocks[i].tag == tag && blocks[i].valid) {
				
				if(wrEn)
					blocks[i].dirty = true;

				return (true);
			}
		}

		return (false);
	}

	public void incLRU() {

		for (int i = 0; i < blocks.length; i++) {
			blocks[i].lastUsed++;
		}

	}

	/*---------------------------------------------------------------------
	 * 
	 *  Function: handleMiss
	 *
	 *  Purpose: Handles read/write miss by replacing missed block and
	 *           replacing least recently used block
	 *
	 *  Parameters: @param tag
	 *
	 *  Returns:  @return void
	 *
	 *-------------------------------------------------------------------*/
	public void handleMiss(int tag) {

		int i;

		// First sort all blocks
		sort();

		// Check for empty blocks to replace first
		for (i = 0; i < blocks.length; i++) {
			if (!blocks[i].valid) {
				blocks[i].valid = true;
				blocks[i].tag = tag;
			}
		}

		// Replace LRU block (last element in blocks)
		if(blocks[i-1].dirty)
			writeBackCount++;
		
		blocks[i - 1].tag = tag;
		blocks[i - 1].lastUsed = 0;
		blocks[i - 1].dirty = false;
	}

	/*---------------------------------------------------------------------
	 * 
	 *  Function: sort
	 *
	 *  Purpose: Sorts set blocks by least recently used
	 *
	 *  Parameters: 
	 *
	 *  Returns:  @return void
	 *
	 *-------------------------------------------------------------------*/
	public void sort() {
		Arrays.sort(blocks);
	}

}
