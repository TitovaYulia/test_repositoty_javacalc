package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



public class FormCalc extends JFrame {

    //задаем поля, которы будет видно во всем классе
    boolean flagEq;
    JFrame frame;
    JPanel panel;
    JTextField linkINPut;
    JButton[] buttonsNumber;
    JButton buttonDiv;
    JButton buttonCancel;
    JButton buttonC;
    JButton buttonMul;
    JButton buttonMin;
    JButton buttonPl;
    JButton buttonEq;
    JButton buttonDot;

    public FormCalc() {
        //задаем форму в конструкторе
        setLayout(new GridLayout(5, 4, 3, 3));
        setBounds(100, 100, 400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame = new JFrame("Calculator");
        panel = new JPanel();
        linkINPut = new JTextField();
        buttonsNumber = new JButton[10];
        buttonCancel = new JButton("<-");
        buttonC = new JButton("C");
        buttonDot = new JButton(".");
        buttonDiv = new JButton("/");
        buttonMul = new JButton("*");
        buttonMin = new JButton("-");
        buttonPl = new JButton("+");
        buttonEq = new JButton("=");
        flagEq = false;
        calculatorButton(); //вызывваем метод, который инициализирует все цифры
        buttonDot.addActionListener(new ActButton());
        buttonDiv.addActionListener(new ActButton());
        buttonMul.addActionListener(new ActButton());
        buttonMin.addActionListener(new ActButton());
        buttonPl.addActionListener(new ActButton());
        buttonC.addActionListener(new CancelButton());
        buttonCancel.addActionListener(new CancelButton());
        buttonEq.addActionListener(new EqButton());


        linkINPut.setSize(500, 34);

        //добавляем кнопки и поле вывода в форму
        add(buttonsNumber[7]);
        add(buttonsNumber[8]);
        add(buttonsNumber[9]);
        add(buttonDiv);
        add(buttonsNumber[4]);
        add(buttonsNumber[5]);
        add(buttonsNumber[6]);
        add(buttonMul);
        add(buttonsNumber[1]);
        add(buttonsNumber[2]);
        add(buttonsNumber[3]);
        add(buttonMin);
        add(buttonsNumber[0]);
        add(buttonDot);
        add(buttonEq);
        add(buttonPl);
        add(linkINPut);
        add(buttonC);
        add(buttonCancel);

        frame.pack();


    }

    //метод, который добавляет символы из массива символов

    public void calculatorButton() {

        for (int i = 0; i < 10; i++) {
            buttonsNumber[i] = new JButton();
            buttonsNumber[i].setText(Integer.toString(i));
            buttonsNumber[i].addActionListener(new ActButton()); //добавляем прослушивающий интерфейс
        }

    }

    public class ActButton implements ActionListener {
        public void actionPerformed(ActionEvent e) {

            if (flagEq == true) { //флаг получения результата: после получения результата текс начинает вводится с пустой строки
                linkINPut.setText("");
                flagEq = false;
            }


            for (int i = 0; i < 10; i++) {
                if (e.getSource() == buttonsNumber[i]) {
                    linkINPut.setText(linkINPut.getText() + buttonsNumber[i].getText()); //добавляем в строку символы от 0 до 9 и "."

                }
            }
            String link = linkINPut.getText();
            int lengthstring = link.length();
            if (lengthstring != 0) { //нельзя вводить символы действия первым символом, кроме минуса
                String lastSimvol = linkINPut.getText().substring(lengthstring - 1, lengthstring);

            //проверяем, чтобы нельзя было ввести два действия друг за другом
                if (lastSimvol.equals("/") == false && lastSimvol.equals("*") == false && lastSimvol.equals("+") == false && lastSimvol.equals("-") == false && lastSimvol.equals(".") == false) {
                    if (e.getSource() == buttonDiv) {
                        linkINPut.setText(linkINPut.getText() + "/");
                    }
                    if (e.getSource() == buttonMul) {
                        linkINPut.setText(linkINPut.getText() + "*");
                    }
                    if (e.getSource() == buttonMin) {
                        linkINPut.setText(linkINPut.getText() + "-");
                    }
                    if (e.getSource() == buttonPl) {
                        linkINPut.setText(linkINPut.getText() + "+");
                    }


                    /*проверяем, что в последнем числе нету точек.
                     * Если есть, то не даем ввести еще одну */
                    if (e.getSource() == buttonDot) {
                        try {

                            String[] arrDot = link.split("\\+|\\-|\\*|\\/");
                            int lengthArr = arrDot.length;

                            if (arrDot[lengthArr - 1].indexOf(".") == -1) {
                                linkINPut.setText(linkINPut.getText() + ".");
                            }
                        } catch (Exception er) {
                            if (link.indexOf(".") == -1)
                                linkINPut.setText(linkINPut.getText() + ".");
                        }
                    }
                }
            }


            if (lengthstring == 0) { //возможно ввести минус первым символом
                if (e.getSource() == buttonMin) {
                    linkINPut.setText(linkINPut.getText() + "-");
                }
            }


        }
    }

    //отмена ввода и полное стирание
    public class CancelButton implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == buttonC) { //полное стирание
                linkINPut.setText("");
            }

            if (e.getSource() == buttonCancel) {//отмена последнего символа
                String value = linkINPut.getText();

                if (value.length() != 0) { //проверяем длину строки
                    String stayVal = value.substring(0, value.length() - 1);
                    linkINPut.setText(stayVal);
                }
            }

        }
    }

    //выполняем действия после нажатия на "="
    public class EqButton implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String line = "";
            String subOperate = "";
            String subStringResult = "0";
            String operate = "+";
            int startIndex = 0;
            float znachenie = 0;


            line = linkINPut.getText();
            int lenght = line.length();
            String lastSimvol = line.substring(lenght - 1, lenght);

            //проверяем, что последний символ перед "=" не действие
            if (lastSimvol.equals("-") == false && lastSimvol.equals("+") == false && lastSimvol.equals("*") == false && lastSimvol.equals("/") == false) {
                line = linkINPut.getText() + "=";

                //проверяем каждый символ на то, действие ли это
                for (int i = 0; i < line.length(); i++) {
                    subOperate = line.substring(i, i + 1);

                    //исключаем "-", если он стоит первым знаком
                    if (subOperate.equals("-") && line.length() == 1)
                        operate = "-";

                    if ((subOperate.equals("+") || subOperate.equals("-") || subOperate.equals("*") || subOperate.equals("/") || subOperate.equals("=")) && i != 0) {
                        subStringResult = line.substring(startIndex, i); //выделяем часть строки до символа действия (цифру)
                        startIndex = i + 1; //переносим определение начала следующей цифры

//
                        switch (operate) {//производим предшествующее действие
                            case "+":
                                znachenie += Float.parseFloat(subStringResult);
                                break;
                            case "-":
                                znachenie -= Float.parseFloat(subStringResult);
                                break;
                            case "/":
                                znachenie /= Float.parseFloat(subStringResult);
                                break;
                            case "*":
                                znachenie *= Double.parseDouble(subStringResult);
                                break;
                        }


                        operate = subOperate;//записываем новое действие
                        flagEq = ((operate.equals("=")) ? true : false); //если действие "=" (конец строки), то ставим флаг, что все подсчитано


                    }
                }

                linkINPut.setText(String.format("%.3g%n", znachenie)); //выводим значение с тремя знаками после запятой
            }
        }
    }
}

