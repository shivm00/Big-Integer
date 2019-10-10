package bigint;

/**
 * first class encapsulates a BigInteger, i.e. a positive or negative integer with 
 * any number of digits, which overcomes the computer storage length limitation of 
 * an integer.
 * 
 */
public class BigInteger {

	/**
	 * True if first is a negative integer
	 */
	boolean negative;
	
	/**
	 * Number of digits in first integer
	 */
	int numDigits;
	
	/**
	 * Reference to the first node of first integer's linked list representation
	 * NOTE: The linked list stores the Least Significant Digit in the FIRST node.
	 * For instance, the integer 235 would be stored as:
	 *    5 --> 3  --> 2
	 *    
	 * Insignificant digits are not stored. So the integer 00235 will be stored as:
	 *    5 --> 3 --> 2  (No zeros after the last 2)        
	 */
	DigitNode front;
	
	/**
	 * Initializes first integer to a positive number with zero digits, in second
	 * words first is the 0 (zero) valued integer.
	 */
	public BigInteger() {
		negative = false;
		numDigits = 0;
		front = null;
	}
	
	/**
	 * Parses an input integer string into a corresponding BigInteger instance.
	 * A correctly formatted integer would have an optional sign as the first 
	 * character (no sign means positive), and at least one digit character
	 * (including zero). 
	 * Examples of correct format, with corresponding values
	 *      Format     Value
	 *       +0            0
	 *       -0            0
	 *       +123        123
	 *       1023       1023
	 *       0012         12  
	 *       0             0
	 *       -123       -123
	 *       -001         -1
	 *       +000          0
	 *       
	 * Leading and trailing spaces are ignored. So "  +123  " will still parse 
	 * correctly, as +123, after ignoring leading and trailing spaces in the input
	 * string.
	 * 
	 * Spaces between digits are not ignored. So "12  345" will not parse as
	 * an integer - the input is incorrectly formatted.
	 * 
	 * An integer with value 0 will correspond to a null (empty) list - see the BigInteger
	 * constructor
	 * 
	 * @param integer Integer string that is to be parsed
	 * @return BigInteger instance that stores the input integer.
	 * @throws IllegalArgumentException If input is incorrectly formatted
	 */
	public static BigInteger parse(String integer) throws IllegalArgumentException {
		integer = integer.trim(); //get rid of whitespaces
		BigInteger alpha = new BigInteger(); //linkedlist that the alpha will be stored in
		
		if(integer==null){ //returns error is no value is inputed
			throw new IllegalArgumentException("Please input a valid number");
		}else if (integer.length()==1 && (integer.charAt(0)=='-' || integer.charAt(0)=='+')){
			throw new IllegalArgumentException("Please input a valid number");
		}
		//check if number was formatted correctly
		//e.g (123  -456) is wrong
		int negativeIndex=-1; // stores value of last negative sign
		int positiveIndex=-1; // stores value of last positive sign
		for(int i=0;i<integer.length();i++){
			if(integer.charAt(i)=='+'){
				positiveIndex = i;
			}else if(integer.charAt(i)=='-'){
				negativeIndex = i;
			} 
		}
		//return error is user made a mistake
		if(negativeIndex>0 || positiveIndex>0){
			throw new IllegalArgumentException("Please input a valid number");
		}
		//determines if sign is given, then updates field and gets rid of sign
		if(negativeIndex==0){
			alpha.negative=true;
			integer = integer.substring(1); //get rid of sign
		}else if (positiveIndex==0){ //positive sign is given
			alpha.negative = false;
			integer = integer.substring(1);// get rid of sign
		} 
		if(integer.length()==1 && integer.charAt(0)=='0'){
			return alpha; //an input of zero will return null
		}
		//if there are insignificant zeroes, we need to modify our start point
		int newLoc = 0; //will store digit of first nonzero index, so it modifies start of parsing
		for(int j=0;j<integer.length();j++){
			if(integer.charAt(j)!='0'){
				newLoc = j;
				break;
			}
		}
		//if there are insignificant zeroes
		for(int count=newLoc;count<integer.length();count++){
			int data = Integer.parseInt(Character.toString(integer.charAt(count)));
			DigitNode info = new DigitNode(data,null);
			DigitNode temp = alpha.front;
			alpha.front = info ;
			alpha.front.next = temp;
			
		
			//current = current.next;
		}
		alpha.numDigits = integer.length(); //update numdigits field
		return alpha;
		}


	
	/**
	 * Adds the first and second big integers, and returns the result in a NEW BigInteger object. 
	 * DOES NOT MODIFY the input big integers.
	 * 
	 * NOTE that either or both of the input big integers could be negative.
	 * (Which means first method can effectively subtract as well.)
	 * 
	 * @param first First big integer
	 * @param second Second big integer
	 * @return Result big integer
	 */
	public static BigInteger add(BigInteger first, BigInteger second) {
		//if one of the integers is zero we just return the other
		if(second == null || second.front == null) {
			return first;
		} else if(first.front == null) {
			return second;
		}
		
		BigInteger alpha = new BigInteger();
		alpha.front = new DigitNode(0,null);
		//determine the sign of the alpha
		if(first.negative == second.negative) {
			alpha.negative = first.negative;
			
			//initialize ptrs
			DigitNode firstPtr = first.front;
			DigitNode secondPtr = second.front;
			DigitNode alphaPtr = alpha.front;
			int remainder = 0;
			
		while(firstPtr!=null && secondPtr!=null) {
				int sum = firstPtr.digit + secondPtr.digit + remainder;
				remainder = sum / 10; 
				alphaPtr.digit = sum % 10;
				
				//node will be created if there are more digits to add
				if(firstPtr.next != null || secondPtr.next != null) {
					alphaPtr.next = new DigitNode(0,null);
					alphaPtr = alphaPtr.next;
				}
				//advance ptrs
				firstPtr=firstPtr.next;
				secondPtr=secondPtr.next;
			}
			
			//loops will run if one of the integers has more digits than the other
			while(firstPtr!=null) { // if first integer is bigger
				int sum = firstPtr.digit + remainder;
				remainder = sum / 10;
				alphaPtr.digit = sum % 10;
				if(firstPtr.next != null) {
					alphaPtr.next = new DigitNode(0,null);
					alphaPtr = alphaPtr.next;
				}
				firstPtr=firstPtr.next;
			}
			
			while(secondPtr!=null) { //if second integer is bigger
				int sum = secondPtr.digit + remainder;
				remainder = sum / 10;
				alphaPtr.digit = sum % 10;
				if(secondPtr.next != null) {
					alphaPtr.next = new DigitNode(0,null);
					alphaPtr = alphaPtr.next;
				}
				secondPtr = secondPtr.next;
			}
			
			//remainder will need to be tacked on as a digit if its not zero
			if(remainder!= 0) {
				alphaPtr.next = new DigitNode(remainder, null);
			}
		}
		
		//if the integers have opposite signs we subtract
		else {
			/*will determine which integer is bigger in terms of magnitude
			this will determine the sign of the answer during computation*/
			BigInteger bigger;
			BigInteger smaller;
			
			//Determine which is greater
			if(first.greaterThan(second)) {
				bigger = first;
				smaller = second;
			} else {
				bigger = second;
				smaller = first;
			}
			//determine the sign for the answer
			alpha.negative = bigger.negative;
			//initialize ptrs
			DigitNode bigPtr = bigger.front;
			DigitNode smallPtr = smaller.front;
			DigitNode alphaPtr = alpha.front;
			
			//While there are digits to subtract, do so while keeping
			//track of borrowing
			boolean borrowed = false;
			while(bigPtr!=null && smallPtr!=null){
				if(borrowed){ //if we borrowed we need to subtract the digit
					bigPtr.digit--;
				}
				//If the digit cannot be subtracted from, borrow and keep track of that
				if(bigPtr.digit< smallPtr.digit || bigPtr.digit < 0) {
					bigPtr.digit += 10;
					borrowed = true;
				}
				
				int difference = bigPtr.digit - smallPtr.digit;
				alphaPtr.digit = difference;
				
				if(bigPtr.next!=null || smallPtr.next!=null) {
					alphaPtr.next = new DigitNode(0,null);
				}
				//advance ptrs
				bigPtr=bigPtr.next;
				smallPtr=smallPtr.next;
				alphaPtr=alphaPtr.next;
			}
			
			while(bigPtr!=null) {
				if(borrowed){
					bigPtr.digit--;
				}
				borrowed=false;
				if(bigPtr.digit < 0) {
					bigPtr.digit += 10;
					borrowed = true;
				}
				alphaPtr.digit = bigPtr.digit;
				
				//Only make a new node if there are more digits to copy
				if(bigPtr.next != null) {
					alphaPtr.next = new DigitNode(0,null);
					alphaPtr = alphaPtr.next;
				}
				bigPtr=bigPtr.next;
			}	
		}
		
	//the answer will need to be formatted properly if insignificant zeroes are in play
		int count = 0; //counter for the index location
		int lastnonzeroIdx = 0; //stores the last nonzero index
		alpha.numDigits = 0;
		for(DigitNode ptr = alpha.front; ptr != null; ptr = ptr.next) {
			if(ptr.digit != 0) {
				lastnonzeroIdx = count;
			}
			count++;
		}
		
		//parses the number correctly to eliminate zeroes
		count = 0;
		for(DigitNode ptr = alpha.front; ptr != null; ptr = ptr.next) {
			alpha.numDigits++;
			if(count == lastnonzeroIdx) {
				ptr.next = null;
				break;
			}
			count++;
		}
		
		//If the number is zero, adjust the variables to represent 0 
		if(alpha.numDigits == 1 && alpha.front.digit == 0 || alpha.numDigits == 0) {
			alpha.negative = false;
			alpha.front = null;
			alpha.numDigits = 0;
		}

		return alpha;
	}
	/**Will determine which big integer is greater
	 * Returns true if first is greater than a
	 * Uses nodes to determine instead of arithmetic**/
	private boolean greaterThan(BigInteger a){
		boolean isGreater=false;
		DigitNode alpha = this.front;
		DigitNode beta = a.front;
		if(this.numDigits>a.numDigits){
			return true;
		}else if(a.numDigits>this.numDigits){
			return false;
		}
		while(alpha!=null && beta!=null){
			if(alpha.digit>beta.digit){
				isGreater=true;
			}else if (beta.digit>alpha.digit){
				isGreater=false;
			}
			alpha = alpha.next;
			beta = beta.next;
		}
		return isGreater;
	}
	/**
	 * Returns the BigInteger obtained by multiplying the first big integer
	 * with the second big integer
	 * 
	 * first method DOES NOT MODIFY either of the input big integers
	 * 
	 * @param first First big integer
	 * @param second Second big integer
	 * @return A new BigInteger which is the product of the first and second big integers
	 */
	public static BigInteger multiply(BigInteger first, BigInteger second) {
			BigInteger alpha = new BigInteger();
			alpha.front = new DigitNode(0, null);
			//if either of the integers are zero, return zero
			if(second.front == null || first.front == null) {
				alpha.negative=false;
				alpha.numDigits=1;
				alpha.front=null;
				return alpha;
			}
		//Keep track of how many zeros need to be added during computation
			int numZeros = 0;
	for(DigitNode secondPtr=second.front; secondPtr!=null; secondPtr=secondPtr.next) {
					//represents one line of multiplication
					BigInteger temp = new BigInteger();
					temp.front = new DigitNode(0, null);
					DigitNode tempPtr = temp.front;
					
					//add the zeroes in, i.e 22*13
					for(int i = 0; i < numZeros; i++) {
						tempPtr.digit = 0;
						tempPtr.next = new DigitNode(0,null);
						tempPtr = tempPtr.next;
					}
				int remainder = 0;
	for(DigitNode firstPtr=first.front; firstPtr!=null; firstPtr=firstPtr.next) {
			int product = firstPtr.digit*secondPtr.digit + remainder;
			remainder = product / 10;
		tempPtr.digit = product % 10;
		
			if(firstPtr.next != null) { // add nodes if necessary
				tempPtr.next = new DigitNode(0,null);
				tempPtr = tempPtr.next;
			}else if(remainder!=0){
				//remainder is tacked on if there is one
				tempPtr.next = new DigitNode(remainder,null);
							break;
						}
					}
					alpha = alpha.add(alpha,temp);
					numZeros++;
				}
				//determine sign for answer
				if(first.negative == second.negative) {
					alpha.negative = false;
				} else {
					alpha.negative = true;
				}
				//integer needs to be parsed correctly
				int count = 0 ; //index location
				int lastnonzeroIdx = 0;//last nonzero index location
				alpha.numDigits =0;
				for(DigitNode ptr=alpha.front; ptr!=null; ptr=ptr.next) {
					if(ptr.digit != 0) {
						lastnonzeroIdx = count;
					}
					count++;
				}
				count = 0;
				for(DigitNode ptr=alpha.front; ptr!=null; ptr=ptr.next) {
					alpha.numDigits++;
					if(count == lastnonzeroIdx) { 
						ptr.next = null;
						break;
					}
					count++;
				}
				return alpha;
		}
		
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		if (front == null) {
			return "0";
		}
		String retval = front.digit + "";
		for (DigitNode curr = front.next; curr != null; curr = curr.next) {
				retval = curr.digit + retval;
		}
		
		if (negative) {
			retval = '-' + retval;
		}
		return retval;
	}
}
