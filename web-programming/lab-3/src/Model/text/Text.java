package Model.text;

import java.util.ArrayList;
import java.util.List;

/**
 * Class represents splitted text.
 * @autor Alexander Rai
 * @version 1.0
 */
public class Text {
    /** List of text's sentences */
    private List<TextPart> textParts = null;

    /** Constructor with no parameters */
    public Text(){
        textParts = new ArrayList<>();
    }

    /** Constructor with lists of sentences and code blocks
     * @param textParts - list of text parts
     * */
    public Text(List<TextPart> textParts){
        this.textParts = textParts;
    }

    /** Setter for text part */
    public void setTextParts(List<TextPart> textParts) {
        this.textParts = textParts;
    }

    /** Getter for text part */
    public List<TextPart> getTextParts() {
        return textParts;
    }

    /** Add new text part to the list */
    public void addPart(TextPart textPart){
        textParts.add(textPart);
    }

    /** Removes text part from the list by index */
    public void removePart(int index){
        textParts.remove(index);
    }

    /** Returns whole text as a string */
    @Override
    public String toString() {
        String text = "";

        List<TextPart> list = new ArrayList<>(this.textParts);
        list.sort((TextPart x, TextPart y) -> Integer.compare(x.getIndexInSentence(),
                y.getIndexInSentence()));
        for (TextPart part: list) {
            text += part.toString() + '\n';
        }

        return text.trim();
    }
}
