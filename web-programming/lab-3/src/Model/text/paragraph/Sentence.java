package Model.text.paragraph;

import Model.text.TextPart;

import java.util.ArrayList;
import java.util.List;

/**
 * Class represents sentences.
 * @autor Alexander Rai
 * @version 1.0
 */
public class Sentence extends TextPart {

    /** Dividers for paragraph */
    public static final String DIVIDER = "\\.!\\?";

    /** List of paragraph's parts */
    private List<TextPart> parts;

    /** Constructor for Sentence
     * @param indexInText - index of the paragraph in text
     * @param parts - list of paragraph's parts
     * */
    public Sentence(List<TextPart> parts, int indexInText){
        super(indexInText);
        this.parts = parts;
    }

    /** Getter for parts */
    public List<TextPart> getParts() {
        return parts;
    }

    /** Setter for parts */
    public void setParts(List<TextPart> parts) {
        this.parts = parts;
    }

    /** Adds new paragraph part to the list */
    public void addPart(TextPart sentencePart){
        this.parts.add(sentencePart);
    }

    /** Removes paragraph part by index */
    public void removePart(int index){
        this.parts.remove(index);
    }

    /** Returns paragraph as string */
    @Override
    public String toString() {
        String sentence = "";

        List<TextPart> list = new ArrayList<>(this.parts);
        list.sort((TextPart x, TextPart y) -> Integer.compare(x.getIndexInSentence(),
                y.getIndexInSentence()));
        for (TextPart part: list) {
            sentence += ( part.getSpaceBefore() ? " " : "") + part.toString();
        }

        return sentence.trim();
    }
}
