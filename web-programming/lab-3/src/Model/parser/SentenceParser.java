package Model.parser;

import Model.text.TextPart;
import Model.text.paragraph.PunctuationMark;
import Model.text.paragraph.Sentence;
import Model.text.paragraph.Word;

import java.util.ArrayList;
import java.util.List;

/**
 * Class represents paragraph parser.
 * @autor Alexander Rai
 * @version 1.0
 */
public class SentenceParser extends Parser {

    /** Sets next parser to null */
    public SentenceParser(){
        super(null);
    }

    @Override
    public List<TextPart> parse(String text) {

        String[] splitted = text.split("((?<=[" + " " + Sentence.DIVIDER + Word.DIVIDER + "])"
                + "|(?=[" + Sentence.DIVIDER + Word.DIVIDER + " " + "]))");
        List<TextPart> textParts = new ArrayList<>(splitted.length);

        for (int i = 0; i < splitted.length; i++){
            TextPart part = null;

            if (splitted[i].matches("[" + Sentence.DIVIDER + Word.DIVIDER + "]"))
                part = new PunctuationMark(splitted[i].trim(), i);
            else
                part = new Word(splitted[i].trim(), i);
            textParts.add(part);
        }

        return textParts;
    }
}
