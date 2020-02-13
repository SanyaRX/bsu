package Model.text.code;

import Model.text.TextPart;

/**
 * Class represents code line.
 * @autor Alexander Rai
 * @version 1.0
 */
public class CodeLine extends TextPart {
    /** Code line */

    public static final String DIVIDER = ";\n";
    private String codeLine = null;

    /**
     * Constructor for CodeLine class
     * @param codeLine - line of code
     * @param index - index of the code line in code block
     * */
    public CodeLine(String codeLine, int index){
        super(index);
        this.spaceBefore = false;
        this.codeLine = codeLine;
    }

    /** Setter for code line */
    public void setCodeLine(String codeLine) {
        this.codeLine = codeLine;
    }

    /** Getter for code line */
    public String getCodeLine() {
        return codeLine;
    }

    @Override
    public String toString() {
        return codeLine;
    }
}
