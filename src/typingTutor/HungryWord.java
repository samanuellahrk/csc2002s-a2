package typingTutor;

public class HungryWord extends Thread{
    private String word; // the word
	private int x; //position - width
	private int y; // postion - height
	private int maxX; //maximum height
	private boolean dropped; //flag for if user does not manage to catch word in time
	
	private int movingSpeed; //how fast this word is
	private static int maxWait=1000;
	private static int minWait=100;

	public static WordDictionary dict;

    HungryWord() { //constructor with defaults
		word="computer"; // a default - not used
		x=0;
		y=150;	
		maxX=600;
		dropped=false;
		movingSpeed=(int)(Math.random() * (maxWait-minWait)+minWait); 
	}
	
	HungryWord(String text) { 
		this();
		this.word=text;
	}
	
	HungryWord(String text,int x, int maxX) { //most commonly used constructor - sets it all.
		this(text);
		this.x=x; //only need to set x, word is at top of screen at start
		this.maxX=maxX;
	}
	
	public static void increaseSpeed( ) {
		minWait+=50;
		maxWait+=50;
	}
	
	public static void resetSpeed( ) {
		maxWait=1000;
		minWait=100;
	}
	

// all getters and setters must be synchronized
	public synchronized  void setX(int x) {
		if (x>maxX) {
			x=maxX;
			dropped=true; //user did not manage to catch this word
		}
		this.x=x;
	}
	
	public synchronized  void setY(int y) {
		this.y=y;
	}
	
	public synchronized  void setWord(String text) {
		this.word=text;
	}

	public synchronized  String getWord() {
		return word;
	}
	
	public synchronized  int getX() {
		return x;
	}	
	
	public synchronized  int getY() {
		return y;
	}
	
	public synchronized  int getSpeed() {
		return movingSpeed;
	}

	public synchronized void setPos(int x, int y) {
		setY(y);
		setX(x);
	}
	public synchronized void resetPos() {
		setX(0);
	}

	public synchronized void resetWord() {
		resetPos();
		word=dict.getNewWord();
		dropped=false;
		movingSpeed=(int)(Math.random() * (maxWait-minWait)+minWait); 
		//System.out.println(getWord() + " falling speed = " + getSpeed());
	}
	
	public synchronized boolean matchWord(String typedText) {
		//System.out.println("Matching against: "+text);
		if (typedText.equals(this.word)) {
			//resetWord();
			return true;
		}
		else
			return false;
	}

	public synchronized  void drop(int inc) {
		setX(x+inc);
	}
	
	public synchronized  boolean dropped() {
		return dropped;
	}

}

