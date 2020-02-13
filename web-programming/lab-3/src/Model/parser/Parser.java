package Model.parser;

import Model.text.TextPart;

import java.util.List;

/**
 * Class represents parser.
 * @autor Alexander Rai
 * @version 1.0
 */
public abstract class Parser {

    /** Next parser in chain */
    protected Parser nextParser = null;

    /** Constructor for Parser
     * @param nextParser - next parser
     * */
    public Parser(Parser nextParser){
        this.nextParser = nextParser;
    }

    /** Getter for next parser */
    public Parser getNextParser() {
        return nextParser;
    }

    /** Setter for parser */
    public void setNextParser(Parser nextParser) {
        this.nextParser = nextParser;
    }

    public abstract List<TextPart> parse(String text);
}
