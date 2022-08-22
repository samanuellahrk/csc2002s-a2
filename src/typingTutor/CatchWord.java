package typingTutor;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

//Thread to monitor the word that has been typed.
public class CatchWord extends Thread {
	String target;
	static AtomicBoolean done ; //REMOVE
	static AtomicBoolean pause; //REMOVE
	
	private static  FallingWord[] words; //list of words
	private static int noWords; //how many
	private static Score score; //user score
	
	CatchWord(String typedWord) {
		target=typedWord;
	}
	
	public static void setWords(FallingWord[] wordList) {
		words=wordList;	
		noWords = words.length;
	}
	
	public static void setScore(Score sharedScore) {
		score=sharedScore;
	}
	
	public static void setFlags(AtomicBoolean d, AtomicBoolean p) {
		done=d;
		pause=p;
	}
	public static boolean isDuplicate(String a, FallingWord[] w){
		int count=0;
		for(int i = 0; i<w.length; i++){
			if(a.equals(w[i].getWord())){
				count++;
			}
		}
		if(count>1){
			return true;
		}
		else{
			return false;
		}
	}
	
	
	public void run() {
		int i=0;

		ArrayList<FallingWord> duplicates = new ArrayList<>();

		while (i<noWords) {		
			while(pause.get()) {};

			if(isDuplicate(target, words)){
				System.out.println("Target is duplicate");
			}
		   i++;
		}
		
	}	
}
