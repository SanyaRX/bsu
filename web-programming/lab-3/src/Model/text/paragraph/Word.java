package Model.text.paragraph;

import Model.text.TextPart;

import java.security.InvalidParameterException;

/**
 * Class represents simple word.
 * @autor Alexander Rai
 * @version 1.0
 */
public class Word extends TextPart {

    /** Divider for word */
    public static final String DIVIDER = ",:;\'\"";

    /** Word */
    private String word = null;
    /** Constructor for Word class
     * @param word - word to save
     * @param indInSentence - index of word if the paragraph
     * */
    public Word(String word, int indInSentence){
        super(indInSentence);
        this.spaceBefore = true;
        this.word = word;
    }

    public void setWord(String word) {
        if(word.length() == 0)
            throw new InvalidParameterException("Word length can't be 0");
        this.word = word;
    }

    @Override
    public String toString() {
        return word;
    }
}
