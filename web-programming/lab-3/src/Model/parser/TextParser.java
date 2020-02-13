package Model.parser;

import Model.text.TextPart;
import Model.text.code.CodeBlock;
import Model.text.paragraph.Paragraph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Class represents text parser.
 * @autor Alexander Rai
 * @version 1.0
 */
public class TextParser extends Parser {

    /** Sets next parser to ParagraphParser */
    public TextParser(){
        super(new ParagraphParser());
    }
    @Override
    public List<TextPart> parse(String text) {
        text = text.replaceAll("[\t ]+", " ");
        String[] splittedText = Pattern.compile(CodeBlock.DIVIDER).split(text);
        List<TextPart> textParts = new ArrayList<>();
        for(int i = 0; i < splittedText.length; i++){
            if (i % 2 == 1){
                List<TextPart> codePart = nextParser.parse(CodeBlock.DIVIDER + splittedText[i]);
                textParts.add(new CodeBlock(codePart, i));
            } else {
                textParts.add(new Paragraph(nextParser.parse(splittedText[i]), i));
            }
        }
        return textParts;
    }
}
