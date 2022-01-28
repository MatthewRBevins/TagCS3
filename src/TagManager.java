import java.util.ArrayList;
import java.util.Objects;
//TODO:comment all code, review variable names, check line lengths (<100 chars)

/**
 * The TagManager class creates and manages two linked lists: players alive and players tagged.
 *
 * @author Matthew Bevins
 */

public class TagManager {
    /**
     * first is a TagNode instance that stores the first player in the tag ring.
     */
    TagNode first;
    /**
     * firstLoser is a TagNode instance that stores the most recently tagged player.
     */
    TagNode firstLoser = null;

    /**
     * TagManager constructor creates new TagNodes for each index in the nameList ArrayList.
     * @param nameList The list of people in the tag ring.
     */
    TagManager(ArrayList<String> nameList) {
        if (nameList == null || nameList.isEmpty()) {
            throw new IllegalArgumentException();
        }
        // Set the first node in the tag ring to the first player in nameList.
        TagNode firstNode = new TagNode((String) nameList.toArray()[0]);
        this.first = firstNode;
        TagNode last = firstNode;
        // Go through the rest of the names in nameList, set previous node's next reference to the new node, and set the last TagNode to the new node.
        for (int i = 1; i < nameList.toArray().length; i++) {
            TagNode newNode = new TagNode((String) nameList.toArray()[i]);
            last.next = newNode;
            last = newNode;
        }
    }

    /**
     * Prints each player in the tag ring and who they are stalking.
     */
    public void printTagRing() {
        TagNode current = first;
        // Iterate through all players in the tag ring and println their names and who they are stalking.
        while (current != null) {
            // If the current TagNode is the last one, go back to the first TagNode.
            if (current.next == null) {
                System.out.println("  " + current.name + " is stalking " + first.name);
            }
            else {
                System.out.println("  " + current.name + " is stalking " + current.next.name);
            }
            current = current.next;
        }
    }

    /**
     * Prints each player that has been tagged, starting with the most recently tagged player.
     */
    public void printLosers() {
        TagNode current = firstLoser;
        // Iterate through all losers and println their names and who tagged them.
        while (current != null) {
            System.out.println("  " + current.name + " was tagged by " + current.tagger);
            current = current.next;
        }
    }

    /**
     * Checks if the given name is part of the tag ring.
     * @param name String storing the given player name to check.
     * @return Boolean - whether name is part of the tag ring.
     */
    public boolean tagRingContains(String name) {
        TagNode current = first;
        // Iterate through all players in the tag ring and if the current TagNode matches the given name, return true.
        while (current != null) {
            if (Objects.equals(current.name.toLowerCase(), name)) return true;
            current = current.next;
        }
        // If no matches were found, return false.
        return false;
    }

    /**
     * Checks if the given name has been tagged.
     * @param name String storing the given player name to check.
     * @return Boolean - whether name is part of the losers.
     */
    public boolean losersContains(String name) {
        TagNode current = firstLoser;
        // Iterate through all losers and if the current TagNode matches the given name, return true.
        while (current != null) {
            if (Objects.equals(current.name.toLowerCase(), name)) return true;
            current = current.next;
        }
        // If no matches were found, return false;
        return false;
    }

    /**
     * Checks if the game is over.
     * @return Whether there is only one player in the tag ring, meaning the game is over.
     */
    public boolean isGameOver() {
        return first.next == null;
    }

    /**
     * Gets the name of the winner.
     * @return The name of the winner, and null if the game isn't over.
     */
    public String winner() {
        if (! isGameOver()) {
            return null;
        }
        return first.name;
    }

    /**
     * Tags a given player name by moving the player from the tag ring to the losers.
     * @param name String storing the given player name to tag.
     */
    //TODO:COMMENT THIS
    public void tag(String name) {
        if (isGameOver()) {
            throw new IllegalStateException();
        }
        else if (! tagRingContains(name)) {
            throw new IllegalArgumentException();
        }
        TagNode current = first;
        while (current != null) {
            TagNode tagged = current.next;
            if (tagged == null) tagged = first;
            if (Objects.equals(tagged.name.toLowerCase(), name)) {
                // If tagged player is the first node, change the first node to the next player
                // and set the tagger's next reference to null
                if (tagged == first) {
                    first = tagged.next;
                    current.next = null;
                }
                // If tagged player isn't the first node, set the tagger's
                // next reference to the tagged player's next reference.
                else {
                    current.next = tagged.next;
                }
                // Set the tagger string to the tagger's name
                tagged.tagger = current.name;
                // Set the tagged player's next reference to the previous loser (firstLoser)
                tagged.next = firstLoser;
                // Set the new tagged player as the first loser
                firstLoser = tagged;
                break;
            }
            current = current.next;
        }
    }
}
