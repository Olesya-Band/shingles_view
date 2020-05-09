package com.ui;

import com.nlp.TextComparator;
import com.nlp.TextCompareResult;

import javax.swing.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

public class Controller {
    private View view;

    private TextComparator comparator;

    public Controller(View view) {
        this.view = view;

        this.comparator = new TextComparator();

        view.addSetFirstFileAction(event -> {
            var fileChooser = new JFileChooser();

            if (fileChooser.showOpenDialog(view) == JFileChooser.APPROVE_OPTION) {
                view.setFirstFilePath(fileChooser.getSelectedFile().getAbsolutePath());
            }
        });
        view.addSetSecondFileAction(event -> {
            var fileChooser = new JFileChooser();

            if (fileChooser.showOpenDialog(view) == JFileChooser.APPROVE_OPTION) {
                view.setSecondFilePath(fileChooser.getSelectedFile().getAbsolutePath());
            }
        });

        view.addRunAction(event -> {
            view.setLogFieldText("");

            TextCompareResult res;

            try {
                res = comparator.run(
                        Files.readString(Paths.get(view.getFirstFilePath())),
                        Files.readString(Paths.get(view.getSecondFilePath()))
                );
            } catch (Exception e) {
                JOptionPane.showMessageDialog(
                        view,
                        e.getClass().getSimpleName() + " " + e.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            view.addLogFieldLine(String.format("Цитаты первого текста: %s", res.getfQuotes()));
            view.addLogFieldLine(String.format("Средняя длина цитаты: %d", res.getfQuoteMidLen()));
            view.addLogFieldLine(String.format("Процентное соотношение цитат к тексту: %f", res.getfQuotePercent()));

            view.addLogFieldLine(String.format("Цитаты второго текста: %s", res.getsQuotes()));
            view.addLogFieldLine(String.format("Средняя длина цитаты: %d", res.getsQuoteMidLen()));
            view.addLogFieldLine(String.format("Процентное соотношение цитат к тексту: %f", res.getsQuotePercent()));

            view.addLogFieldLine(String.format("Одинаковые цитаты: %s", res.getEqualsQuotes()));

            if (view.printShingles()) {
                view.addLogFieldLine("Шинглы первого текста");

                for (var shingle : res.getfShingles()) {
                    view.addLogFieldLine(Arrays.toString(shingle));
                }

                view.addLogFieldLine("Шинглы второго текста");

                for (var shingle : res.getsShingles()) {
                    view.addLogFieldLine(Arrays.toString(shingle));
                }
            }

            view.addLogFieldLine(String.format("Мера схожести: %f", res.getEqualsMeasure()));
            view.addLogFieldLine(String.format("Время работы: %d милисекунд", res.getTime()));
        });
    }
}
