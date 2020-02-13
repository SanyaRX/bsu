package Model.parser;

import Model.text.TextPart;
import Model.text.code.CodeBlock;
import Model.text.code.CodeLine;
import Model.text.paragraph.Sentence;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Class represents paragraph parser.
 * @autor Alexander Rai
 * @version 1.0
 */
public class ParagraphParser extends Parser {

    /** Sets next parser to SentenceParser */
    public ParagraphParser(){
        super(new SentenceParser());
    }

    @Override
    public List<TextPart> parse(String text) {
        Pattern p = Pattern.compile(CodeBlock.DIVIDER);
        boolean isCode = p.matcher(text.subSequence(0, text.length())).find();

        if (isCode)
            text = text.split(CodeBlock.DIVIDER)[1];

        String[] splitted = text.split("(?<=[" + (isCode ? CodeLine.DIVIDER : Sentence.DIVIDER) + "])");
        List<TextPart> textParts = new ArrayList<>(splitted.length);
        for (int i = 0; i < splitted.length; i++){
            if(isCode)
                textParts.add(new CodeLine(splitted[i], i));
            else {
                List<TextPart> sentenceParts = nextParser.parse(splitted[i]);
                textParts.add(new Sentence(sentenceParts, i));
            }
        }

        return textParts;
    }
}
