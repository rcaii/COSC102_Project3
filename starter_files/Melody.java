public class Melody{
	private LinkedQueue<Note> s = new LinkedQueue<Note>();
	private double totalDuration = 0;
	
	// constructor that stores the passed in queue o notes
	public Melody(QueueInterface<Note> song){
		int repeat = 0;
		
		while (!song.isEmpty()){
			Note n = song.dequeue();
			s.enqueue(n);
			if (n.isRepeat())
				repeat ++;
			
			if (repeat > 0){
				totalDuration += n.getDuration();
				if (repeat == 2)
					repeat = 0;
			}
			
			totalDuration += n.getDuration();
		}
	}
	
	// return the total duration for the song
	public double getTotalDuration(){
		return totalDuration;				
	}
	
	// display information for each note
	public String toString(){
		String str = "";
		for (int i = 0; i < s.size(); i++){
			Note n = s.dequeue();
			str += n.toString() + "\n";
			s.enqueue(n);
		}
		return str;
	}
	
	// change the tempo
	public void changeTempo(double tempo){
		for (int i = 0; i < s.size(); i++){
			Note n = s.dequeue();
			n.setDuration(n.getDuration()*tempo); // multiply by the given tempo
			s.enqueue(n);
		}
		totalDuration = totalDuration*tempo;		
	}
	
	// reverse the order of notes (use a stack to reverse the order)
	public void reverse(){
		VectorStack<Note> s1 = new VectorStack<Note>();
		while(!s.isEmpty()){
			s1.push(s.dequeue());
		}
		
		while(!s1.isEmpty()){
			s.enqueue(s1.pop());
		}
	}
	
	// Append the notes for two songs
	public void append(Melody other){
		for( int i = 0; i < other.getQueue().size(); i++){
			Note n = other.getQueue().dequeue();
			getQueue().enqueue(n);
			other.getQueue().enqueue(n);
			totalDuration += n.getDuration();
		}
	}
	
	// helper method to return the song
	public LinkedQueue<Note> getQueue(){
		return s;
	}
	
	// play the song
	public void play(){
		LinkedQueue<Note> temp = new LinkedQueue<Note>();		
		int repeat = 0;
		
		for (int i = 0; i < s.size(); i++){
			Note n1 = s.dequeue();
			
			if (n1.isRepeat()){
				repeat ++;
				if (repeat == 1){
					temp.enqueue(n1);
					n1.play();
				} else if (repeat == 2){
					playRepeat(temp);
				}
			} else {
				n1.play();
			}
			s.enqueue(n1);	
		}		
	}
	
	//helper method to play the repeat part
	public void playRepeat(LinkedQueue<Note> temp){

		for (int i = 0; i < temp.size(); i++){
			Note n2 = temp.dequeue();
			n2.play();
			temp.enqueue(n2);
		}
	}
			
}
	
