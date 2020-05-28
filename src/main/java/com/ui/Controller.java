package com.ui;

import com.nlp.Result;
import com.nlp.TextComparator;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

/**
 * Класс, содержит логику обработки текстов и информацию о выводе результатов сравнения текстов
 */
public class Controller {
    private final View view;

    private TextComparator comparator;

    public Controller(View view) {
        this.view = view;

        Runnable task = () -> {
            this.comparator = new TextComparator();

            this.view.unblockButton();


        };

        task.run();

                view.addSetFirstFileAction(event -> {
            var fileChooser = new JFileChooser();

            var filter = new FileNameExtensionFilter("Txt file", "txt");
            fileChooser.setFileFilter(filter);

            if (fileChooser.showOpenDialog(view) == JFileChooser.APPROVE_OPTION) {
                view.setFirstFilePath(fileChooser.getSelectedFile().getAbsolutePath());
            }
        });
        view.addSetSecondFileAction(event -> {
            var fileChooser = new JFileChooser();

            var filter = new FileNameExtensionFilter("Txt file", "txt");
            fileChooser.setFileFilter(filter);

            if (fileChooser.showOpenDialog(view) == JFileChooser.APPROVE_OPTION) {
                view.setSecondFilePath(fileChooser.getSelectedFile().getAbsolutePath());
            }
        });

        view.addRunAction(event -> {
            view.setLogFieldText("");

            Result res;

            try {
                res = comparator.compare(
                        Files.readString(Paths.get(view.getFirstFilePath())),
                        Files.readString(Paths.get(view.getSecondFilePath())),
                        view.printShingles()
                );
            } catch (Exception e) {
                JOptionPane.showMessageDialog(
                        view,
                        "Пожалуйста, выберите .txt файл",
                        "Ошибка при чтении файлов",
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            view.addLogFieldLine(String.format("Мера схожести: %.2f", res.getMeasure()));
            view.addLogFieldLine("");

            view.addLogFieldLine("Цитаты первого текста:");
            if (res.getfQuotes() != null)
            {
                for (var s : res.getfQuotes())
                {
                    view.addLogFieldLine(s);
                }
            }
            view.addLogFieldLine(String.format("Средняя длина цитаты: %d", res.getfQuoteMidLen()));
            view.addLogFieldLine(String.format("Процентное соотношение цитат к тексту: %.2f", res.getfQuotePercent()));

            view.addLogFieldLine("Цитаты второго текста");
            if (res.getfQuotes() != null)
            {
                for (var s : res.getsQuotes())
                {
                    view.addLogFieldLine(s);
                }
            }
            view.addLogFieldLine(String.format("Средняя длина цитаты: %d", res.getsQuoteMidLen()));
            view.addLogFieldLine(String.format("Процентное соотношение цитат к тексту: %.2f", res.getsQuotePercent()));

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
            view.addLogFieldLine("");

            view.addLogFieldLine(String.format("Время работы: %d миллисекунд", res.getTime()));
        });
    }
}
