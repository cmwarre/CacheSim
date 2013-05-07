public class Block implements Comparable<Block> {
	public int lastUsed = 0;
	public boolean valid = false;
	public boolean dirty = false;
	public int tag = 0;

	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(Block b) {

		if (b.lastUsed > this.lastUsed)
			return (1);
		else if (b.lastUsed == this.lastUsed)
			return (0);
		else
			return (-1);
		
	}
}
