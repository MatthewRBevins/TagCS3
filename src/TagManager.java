import java.util.ArrayList;
import java.util.Objects;

public class TagManager {
    TagNode first;
    TagNode firstLoser = null;
    TagManager(ArrayList<String> nameList) {
        TagNode firstNode = new TagNode((String) nameList.toArray()[0]);
        this.first = firstNode;
        TagNode last = firstNode;
        for (int i = 1; i < nameList.toArray().length; i++) {
            TagNode newNode = new TagNode((String) nameList.toArray()[i]);
            last.next = newNode;
            last = newNode;
        }
    }
    public void printLosers() {
        TagNode current = firstLoser;
        while (current != null) {
            System.out.println(current.name + " was tagged by " + current.tagger);
            current = current.next;
        }
    }
    public void printTagRing() {
        TagNode current = first;
        while (current != null) {
            if (current.next == null) {
                System.out.println(current.name + " is stalking " + first.name);
            }
            else {
                System.out.println(current.name + " is stalking " + current.next.name);
            }
            current = current.next;
        }
    }
    public void tag(String name) {
        TagNode current = first;
        while (current != null) {
            TagNode tagged = current.next;
            if (tagged == null) {
                tagged = first;
            }
            if (Objects.equals(tagged.name, name)) {
                if (firstLoser != null) firstLoser.next = firstLoser;
                    firstLoser = current.next;
                tagged.tagger = current.name;
                if (tagged.next != null) {
                    current.next = tagged.next;
                }
                else {
                    current.next = first;
                }
                tagged.next = null;
                break;
            }
            current = current.next;
        }
    }
    public String winner() {
        return "";
    }
    public boolean isGameOver() {
        return false;
    }
    public boolean losersContains(String name) {
        return false;
    }
    public boolean tagRingContains(String name) {
        return true;
    }
}
