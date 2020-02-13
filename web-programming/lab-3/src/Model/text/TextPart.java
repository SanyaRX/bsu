package Model.text;

import java.security.InvalidParameterException;
/**
 * Class represents paragraph parts.
 * @autor Alexander Rai
 * @version 1.0
 */
public abstract class TextPart {
    /** Index of the part in paragraph */
    private int indexInSentence;
    protected Boolean spaceBefore = false;

    /** Constructor for Sentence part
     * @param indexInSentence - index of the part in paragraph
     * @throws InvalidParameterException
     * */
    public TextPart(int indexInSentence){
        if (indexInSentence < 0)
            throw new InvalidParameterException("Invalid index");

        this.indexInSentence = indexInSentence;
    }

    /** Sets index in paragraph
     * @throws InvalidParameterException
     * */
    public void setIndexInSentence(int indexInSentence) {
        if (indexInSentence <= 0)
            throw new InvalidParameterException("Invalid index");
        this.indexInSentence = indexInSentence;
    }

    /** Returns index in paragraph */
    public int getIndexInSentence() {
        return indexInSentence;
    }

    /** Returns bool value fo space before field */
    public Boolean getSpaceBefore() {
        return spaceBefore;
    }

    @Override
    public abstract String toString();
}
