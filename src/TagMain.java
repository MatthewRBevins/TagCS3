// CSE 143, Homework 4: Tag
// Instructor-provided testing program.

import java.io.*;
import java.util.*;

/**
 * Class TagMain is the main client program for assassin game management.
 * It reads names from a file names.txt, shuffles them, and uses them to
 * start the game.  The user is asked for the name of the next victim until
 * the game is over.
 */
public class TagMain {
    /** input file name from which to read data */
    public static final String INPUT_FILENAME = "names.txt";

    /** true for different results every run; false for predictable results */
    public static final boolean RANDOM = false;

    /**
     * If not random, use this value to guide the sequence of numbers
     * that will be generated by the Random object.
     */
    public static final int SEED = 42;


    public static void main(String[] args) throws FileNotFoundException {
        // read names into a Set to eliminate duplicates
        File inputFile = new File(INPUT_FILENAME);
        if (!inputFile.canRead()) {
            System.out.println("Required input file not found; exiting.\n" + inputFile.getAbsolutePath());
            System.exit(1);
        }
        Scanner input = new Scanner(inputFile);

        Set<String> names = new TreeSet<String>(String.CASE_INSENSITIVE_ORDER);
        while (input.hasNextLine()) {
            String name = input.nextLine().trim().intern();
            if (name.length() > 0) {
                names.add(name);
            }
        }

        // transfer to an ArrayList, shuffle and build an TagManager
        ArrayList<String> nameList = new ArrayList<String>(names);
        Random rand = (RANDOM) ? new Random() : new Random(SEED);
        Collections.shuffle(nameList, rand);

        TagManager manager = new TagManager(nameList);

        // prompt the user for victims until the game is over
        Scanner console = new Scanner(System.in);
        while (!manager.isGameOver()) {
            oneTag(console, manager);
        }

        // report who won
        System.out.println("Game was won by " + manager.winner());
        System.out.println("Final losers are as follows:");
        manager.printLosers();
    }

    /**
     * Handles the details of recording one victim.  Shows the current Tag
     * ring and losers to the user, prompts for a name and records the
     * tag if the name is legal.
     */
    public static void oneTag(Scanner console, TagManager manager) {
        // print both linked lists
        System.out.println("Current tag ring:");
        manager.printTagRing();
        System.out.println("Current losers:");
        manager.printLosers();

        // prompt for next victim to Tag
        System.out.println();
        System.out.print("next victim? ");
        String name = console.nextLine().trim();

        // tag the victim, if possible
        if (manager.losersContains(name)) {
            System.out.println(name + " is already tagged");
        } else if (!manager.tagRingContains(name)) {
            System.out.println("Unknown person.");
        } else {
            manager.tag(name);
        }
        System.out.println();
    }
}