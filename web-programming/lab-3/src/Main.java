
import Controller.TextController;
import Model.exceptions.ChangeWordException;
import Model.exceptions.FileLoadException;
import Model.parser.TextParser;
import Model.text.Text;

import localization.Localizator;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.Arrays;


public class Main {
    private final static Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        TextController textController = new TextController();
        Localizator.setLocale("ru");
        String fileText = null;
        try {
            fileText = textController.loadText("src\\files\\ru_text.txt");
        } catch (FileLoadException e) {
            logger.error(e.getMessage(), e);
            return;
        }

        logger.info(Localizator.getLocalizedString(Localizator.TEXT_LOADED));
        System.out.println();
        System.out.println(fileText);
        System.out.println();
        logger.info(Localizator.getLocalizedString(Localizator.TEXT_PARSING));
        TextParser textParser = new TextParser();
        Text text = new Text(textParser.parse(fileText));
        logger.info(Localizator.getLocalizedString(Localizator.TEXT_PARSED));

        logger.info(Localizator.getLocalizedString(Localizator.SORTING));
        String sortedText = Arrays.toString(textController.sortText(text, 'к').toArray());
        logger.info(Localizator.getLocalizedString(Localizator.TEXT_SORTED));
        System.out.println(sortedText);

        try {
            logger.info(Localizator.getLocalizedString(Localizator.WORDS_PROCESSING));
            textController.changeWords(text);
            System.out.println(text);
        } catch (ChangeWordException e){
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
    }


}
