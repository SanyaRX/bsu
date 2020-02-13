package Controller;

import Model.exceptions.ChangeWordException;
import Model.exceptions.FileLoadException;
import Model.text.Text;
import Model.text.TextPart;
import Model.text.paragraph.Paragraph;
import Model.text.paragraph.Sentence;
import Model.text.paragraph.Word;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

import localization.Localizator;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class TextController {

    private final static Logger logger = LogManager.getLogger("TextController");

    public TextController(){

    }

    public String loadText(String path) throws FileLoadException {

        String textString;
        try {
            byte[] encoded = Files.readAllBytes(Paths.get(path));
            textString = new String(encoded, Charset.forName("UTF-8"));
        } catch (IOException e) {
            throw new FileLoadException("Loading error", e);
        }
        logger.info(Localizator.getLocalizedString(Localizator.TEXT_LOADED));
        return textString;
    }

    public List<Word> sortText(Text text, Character symbol){
        logger.info(Localizator.getLocalizedString(Localizator.SORTING));
        List<Word> wordList = new ArrayList<>();

        for (int i = 0; i < text.getTextParts().size(); i += 2){
            Paragraph paragraph = (Paragraph)text.getTextParts().get(i);
            for (TextPart sentence : paragraph.getSentences()){
                for (TextPart part : ((Sentence)sentence).getParts()){
                    if (part instanceof Word && !part.toString().equals("")){
                        wordList.add((Word)part);
                    }
                }
            }
        }
        wordList.sort(new WordComparator(symbol));
        logger.info(Localizator.getLocalizedString(Localizator.TEXT_SORTED));
        return wordList;
    }

    public void changeWords(Text text) throws ChangeWordException {
        logger.info(Localizator.getLocalizedString(Localizator.WORDS_PROCESSING));
        for (int i = 0; i < text.getTextParts().size(); i += 2){
            Paragraph paragraph = (Paragraph)text.getTextParts().get(i);
            for (TextPart sentence : paragraph.getSentences()){
                for (TextPart part : ((Sentence)sentence).getParts()){
                    if (part instanceof Word && !part.toString().equals("")){
                        try {
                            ((Word) part).setWord(changeWordBackward(part.toString(), true));
                        } catch (InvalidParameterException e){
                            throw new ChangeWordException(e.getMessage(), e);
                        }
                    }
                }
            }
        }
    }

    public String changeWordBackward(String word, boolean forward){
        if (word.length() == 0)
            throw new InvalidParameterException("Word can't be empty");

        String newWord = "";
        if (forward) {
            Character charToDelete = word.charAt(0);
            newWord = word.toLowerCase().replaceAll("[" + Character.toLowerCase(charToDelete) + "]",
                    "");
            newWord = charToDelete + newWord;
        } else {
            Character charToDelete = word.charAt(word.length() - 1);
            newWord = word.toLowerCase().replaceAll("[" + Character.toLowerCase(charToDelete) + "]",
                    "");
            newWord += charToDelete;
        }

        return newWord.trim();
    }
}
