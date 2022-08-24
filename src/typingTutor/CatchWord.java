package typingTutor;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

//Thread to monitor the word that has been typed.
public class CatchWord extends Thread {
	String target;
	static AtomicBoolean done ; //REMOVE
	static AtomicBoolean pause; //REMOVE
	
	private static  FallingWord[] words; //list of words
	private static HungryWord[] hungryWords;

	private static int noWords; //how many
	private static Score score; //user score
	
	CatchWord(String typedWord) {
		target=typedWord;
	}
	
	public static void setWords(FallingWord[] wordList) {
		words=wordList;	
		noWords = words.length;
	}
	public static void setHungryWords(HungryWord[] wordList2) {
		hungryWords=wordList2;	
		//noWords = words.length;
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
	public static FallingWord duplicates(String tar, FallingWord[] w){

		ArrayList<FallingWord> duplicates = new ArrayList<>();

		for(int i = 0; i<w.length; i++){

			if(tar.equals(w[i].getWord())){
				duplicates.add(w[i]);
			}

		}

		for(int a = 0; a<duplicates.size()-1; a++){
			for(int b = a+1; b<duplicates.size(); b++){
				if(duplicates.get(a).getY() < duplicates.get(b).getY()){
					return duplicates.get(b);
				}else{
					return duplicates.get(a);
				}
			}
		}
		return null;
	}
	
	
	public void run() {
		int i=0;

		ArrayList<FallingWord> duplicates = new ArrayList<>();

		while (i<noWords) {		
			while(pause.get()) {};

			if( (hungryWords[0].getY() == words[i].getY()) && (hungryWords[0].getX() == words[i].getX()) ){
					score.missedWord();
					words[i].resetWord();
					break;
				}
				
				if(isDuplicate(target, words)){
					System.out.println("point1");
					(duplicates(target,words)).resetWord();
					System.out.println("point2");
					//System.out.println( " score! '" + target); //for checking
					score.caughtWord(target.length());	
					break;
					//System.out.println("Target is duplicate");
				}else if( (words[i].matchWord(target)) ){
					System.out.println("point3");
					words[i].resetWord();
					System.out.println( " score! '" + target); //for checking
					score.caughtWord(target.length());	
					break;
				}else if( (hungryWords[0].matchWord(target)) ){
					System.out.println("point3");
					hungryWords[0].resetWord();
					System.out.println( " score! '" + target); //for checking
					score.caughtWord(target.length());	
					break;
				}//else 
			
		   i++;
		}
		
	}	
}
