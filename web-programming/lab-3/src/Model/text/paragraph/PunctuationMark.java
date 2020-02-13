package Model.text.paragraph;

import Model.text.TextPart;

/**
 * Class represents punctuation marks.
 * @autor Alexander Rai
 * @version 1.0
 */
public class PunctuationMark extends TextPart {
    /** mark */
    private String mark;


    /** Constructor for punctuation mark
     * @param mark - mark
     * @param indInSentence - index of mark in paragraph
     * */
    public PunctuationMark(String mark, int indInSentence){
        super(indInSentence);
        this.spaceBefore = false;
        this.mark = mark;
    }

    @Override
    public String toString() {
        return mark;
    }
}
