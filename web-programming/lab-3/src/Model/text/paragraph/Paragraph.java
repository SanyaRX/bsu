package Model.text.paragraph;

import Model.text.TextPart;
import java.util.ArrayList;
import java.util.List;

/**
 * Class represents text paragraph.
 * @autor Alexander Rai
 * @version 1.0
 */

public class Paragraph extends TextPart {

    /**
     * List of sentences
     */
    private List<TextPart> sentences = null;

    /** Constructor for paragraph */
    public Paragraph(List<TextPart> sentences, int indexInText){
        super(indexInText);
        this.sentences = sentences;
    }

    /** Adds sentence to the list */
    public void addSentence(Sentence codeLine){
        sentences.add(codeLine);
    }

    /** Removes sentence from the list by index */
    public void removeSentence(int index){
        sentences.remove(index);
    }

    /** Sets new list of sentences */
    public void setSentence(List<TextPart> sentences) {
        this.sentences = sentences;
    }

    /** Getter for sentences list */
    public List<TextPart> getSentences() {
        return sentences;
    }

    @Override
    public String toString() {
        String paragraph = "";

        List<TextPart> list = new ArrayList<>(this.sentences);
        list.sort((TextPart x, TextPart y) -> Integer.compare(x.getIndexInSentence(),
                y.getIndexInSentence()));
        for (TextPart part: list) {
            paragraph += part.toString() + ' ';
        }

        return paragraph.trim();
    }

}
