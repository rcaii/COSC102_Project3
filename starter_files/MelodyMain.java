// MelodyMain
//
// Instructor-provided code.
// This program tests your Melody object on any file you want.
//
// When the program starts, 
//   1. type load
//   2. enter the name of the file you want to play and
//   3. type play to hear it
//
// Use other commands to manipulate and test the Melody's other 
// functionality.
//
// You have to change this file to call the constructor/methods
// of Melody.java

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Scanner;

public class MelodyMain {
  
  private static Scanner console = new Scanner(System.in);
  
  //TO UNCOMMENT
  private Melody melody = null;
  
  public MelodyMain() {
    menu(); 
  }
  
  
  // prints out an introduction describing how to use the program
  private void menu() {
    System.out.println("Welcome to MelodyMain. Type the word in the left "
                         + " column to do the action on the right");
    System.out.println("load     : load a new input file");
    System.out.println("save     : output to a file");
    System.out.println("print    : prints the contents of the last loaded song");
    System.out.println("play     : play the last loaded song");
    System.out.println("reverse  : reverse the last loaded song");
    System.out.println("duration : print out the length of the last loaded song in seconds");
    System.out.println("tempo    : change the speed by a percentage");
    System.out.println("append   : appends notes from a second melody to the loaded one");
    System.out.println("quit     : exit the program");
    try {
      getCommands();
    } catch (Exception e) {
      
    }
  }
  
  public void getCommands() throws FileNotFoundException{
    
    String command = "nothing";
    while(!command.equalsIgnoreCase("quit")) {
      System.out.print("What would you like to do? ");
      command = console.next();
      
      if(!(command.equalsIgnoreCase("quit") || command.equalsIgnoreCase("load")) 
      	//) { //COMMENT
           && melody == null) { //UNCOMMENT
        System.out.println("You must load a song before trying to manipulate it");    
      } else if(command.equalsIgnoreCase("load")) {
        System.out.print("File name? ");
        File file = checkFile(console.next(), console);
        Scanner input = new Scanner(file);
        
        QueueInterface<Note> song = read(input);
        melody = new Melody(song);
        
        //construct a Melody object
        // melody = ...   read(input);    // helper method: what is returned 
        //System.out.println("You need to allocate a Melody object");
      } else if (command.equalsIgnoreCase("play")) {
        melody.play();
        
        //System.out.println("The song is played.");
      } else if (command.equalsIgnoreCase("reverse")) {
        melody.reverse();
        //System.out.println("Not implemented");
      } else if (command.equalsIgnoreCase("save")) {
        System.out.print("Output file? ");
        PrintStream output = new PrintStream(new File(console.next()));
        output.print(melody);
      } else if (command.equalsIgnoreCase("print")) {
        System.out.print(melody);
        //System.out.println("Not implemented");
      } else if (command.equalsIgnoreCase("duration")) {
        System.out.println(melody.getTotalDuration() + " seconds long");
        //System.out.println("Not implemented");
      } else if (command.equalsIgnoreCase("tempo")) {
        System.out.print("Percentage? ");
        double tempo = console.nextDouble();
        melody.changeTempo(tempo);
        //System.out.println("Not implemented");
      } else if (command.equalsIgnoreCase("append")) {
        System.out.print("File name of second song? ");
        File file = checkFile(console.next(), console);
        
        Scanner input1 = new Scanner(file);
        QueueInterface<Note> song1 = read(input1);
        Melody other = new Melody(song1);
        melody.append(other);
        //System.out.println("Not implemented");
      } else if (!command.equalsIgnoreCase("quit")) {
        System.out.println("Invalid command. Please try again.");
        menu();
      } 
    }
  }
  
  // THESE HELPERS are useful: study them

  // checks to make sure the file exists. Prompts the user for a new file
  // until they input a valid one. Returns a file that exists.
  public static File checkFile(String name, Scanner console) {
    File file = new File(name);
    while (!file.exists()) {
      System.out.print("Invalid file. File name? ");
      file = new File(console.next());
    }
    return file;
  }
  
  // returns a Queue filled with the notes specified in the passed in 
  // Scanner. The notes will appear in the same order in the Queue
  // as they did in the file.
  public static QueueInterface<Note> read (Scanner input) {
    QueueInterface<Note> song = new LinkedQueue<Note>();
    
    while(input.hasNext()) {
      double duration = input.nextDouble();
      Pitch pitch = Pitch.valueOf(input.next());
      if(pitch.equals(Pitch.R)) {
        song.enqueue(new Note(duration, input.nextBoolean()));          
      } else {
        song.enqueue(new Note(duration, pitch, input.nextInt(), 
                          Accidental.valueOf(input.next()), input.nextBoolean()));
      }          
    }
    return song;
  }
  
  public static void main(String[] args)  {
    MelodyMain melodyMain = new MelodyMain();
    //melodyMain.menu();
  }
  
}
