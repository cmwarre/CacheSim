public class Utility {
	
	/*---------------------------------------------------------------------
	 * 
	 *  Function: log2
	 *
	 *  Purpose: finds log base 2 of specified double
	 *
	 *  Parameters: @param num
	 *
	 *  Returns:  @return int
	 *
	 *-------------------------------------------------------------------*/
	public static int log2(double num){
		return((int)(Math.ceil(Math.log(num)/Math.log(2))));
	}
	
	/*---------------------------------------------------------------------
	 * 
	 *  Function: getTag
	 *
	 *  Purpose: Gets tag bits from binary address
	 *
	 *  Parameters: 
	 *             @param numBits
	 *             @param binAddr
	 *             @return
	 *
	 *  Returns:  @retur int
	 *
	 *-------------------------------------------------------------------*/
	public static int getTag(int numBits, String binAddr){
		String temp = binAddr.substring(0, numBits-1);
		return(Integer.parseInt(temp,2));
	}
	
	/*---------------------------------------------------------------------
	 * 
	 *  Function: isPow2
	 *
	 *  Purpose: Tests if specified number is a positive power of 2
	 *
	 *  Parameters: 
	 *             @param num
	 *
	 *  Returns:  @return boolean
	 *
	 *-------------------------------------------------------------------*/
	public static boolean isPow2(int num){
		double power = Math.log(num)/Math.log(2);
		
		// Check Power of 2
		if(power % 1 != 0)
			return(false);
		
		// Check Positive
		if(power > 0)
			return(true);
		else
			return(false);
	}
	
}

