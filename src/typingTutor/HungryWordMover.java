package typingTutor;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class HungryWordMover extends Thread{

    
    private FallingWord myWord;
	private HungryWord hungWord;
	private AtomicBoolean done;
	private AtomicBoolean pause; 
	private Score score;
	CountDownLatch startLatch; //so all can start at once
	
	HungryWordMover( HungryWord word) {
		hungWord = word;
	}
	/*WordMover( HungryWordMover word) {
		hungWord = word;
	}*/
	
	HungryWordMover( HungryWord word,FallingWord myWord, WordDictionary dict, Score score,
			CountDownLatch startLatch, AtomicBoolean d, AtomicBoolean p) {
		this(word);
		this.startLatch = startLatch;
		this.score=score;
		this.done=d;
		this.pause=p;
		this.myWord=myWord;
	}
	/*WordMover( HungryWordMover word,WordDictionary dict, Score score,
			CountDownLatch startLatch, AtomicBoolean d, AtomicBoolean p) {
		this(word);
		this.startLatch = startLatch;
		this.score=score;
		this.done=d;
		this.pause=p;
	}*/
	
	public boolean dropp(HungryWord h, FallingWord f){

		if( (h.getY()+10 == f.getY()+10) && (h.getX()+10 == f.getX()+10) || (h.getY()-10 == f.getY()-10) && (h.getX()-10 == f.getX()-10)){
			return true;
		}
		return false;
	}
	
	public void run() {
        
		//h
	//System.out.println(myWord.getWord() + " falling speed = " + myWord.getSpeed());
        //WordMover m = new WordMover(word)
        		try {
			System.out.println(hungWord.getWord() + " waiting to start " );
			startLatch.await();
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} //wait for other threads to start
		System.out.println(hungWord.getWord() + " started" );
		while (!done.get()) {				
			//animate the word
			while (!hungWord.dropped() && !done.get()) {
				    hungWord.drop(10);
					try {
						sleep(hungWord.getSpeed());
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					};		
					while(pause.get()&&!done.get()) {};
			}
			if (!done.get() && hungWord.dropped()) {
				score.missedWord();
				hungWord.resetWord();
			}
			if (dropp(hungWord, myWord)) {
				score.missedWord();
				myWord.resetWord();
				//hungWord.resetWord();
			}
            //if(hungWord.getY() == WordMover.myWord.getY())
			//hungWord.resetWord();
		//}
	}
	
}
}
