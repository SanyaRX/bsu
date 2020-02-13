package Model.text.code;

import Model.text.TextPart;

import java.util.ArrayList;
import java.util.List;

/**
 * Class represents code blocks.
 * @autor Alexander Rai
 * @version 1.0
 */
public class CodeBlock extends TextPart {

    public static final String DIVIDER = "<code>";
    /** Code block */
    private List<TextPart> codeLines;

    /** Constructor for code block */
    public CodeBlock(List<TextPart> codeLines, int indexInText){
        super(indexInText);
        this.codeLines = codeLines;
    }

    /** Adds text line to the list */
    public void addLine(CodeLine codeLine){
        codeLines.add(codeLine);
    }

    /** Removes code line from the list by index */
    public void removeLine(int index){
        codeLines.remove(index);
    }

    /** Sets new list of code lines */
    public void setCodeLines(List<TextPart> codeLines) {
        this.codeLines = codeLines;
    }

    /** Getter for code lines list */
    public List<TextPart> getCodeLines() {
        return codeLines;
    }

    @Override
    public String toString() {
        String codeBlock = "";

        List<TextPart> list = new ArrayList<>(this.codeLines);
        list.sort((TextPart x, TextPart y) -> Integer.compare(x.getIndexInSentence(),
                y.getIndexInSentence()));
        for (TextPart part: list) {
            codeBlock += part.toString();
        }

        return CodeBlock.DIVIDER + "\n" + codeBlock.trim() + "\n" + CodeBlock.DIVIDER;
    }
}
