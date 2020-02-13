package Controller;

import Model.text.paragraph.Word;

import java.util.Comparator;

public class WordComparator implements Comparator<Word> {

    private Character symbol;

    public WordComparator(Character symbol){
        this.symbol = symbol;
    }

    @Override
    public int compare(Word o1, Word o2) {
        long firstCount = o1.toString().chars().filter(ch -> ch == symbol).count();
        long secondCount = o2.toString().chars().filter(ch -> ch == symbol).count();

        if (firstCount == secondCount)
            return o1.toString().compareTo(o2.toString());

        return Long.compare(secondCount, firstCount);
    }
}
