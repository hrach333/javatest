import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args)  throws IOException{
        //System.out.println(Calc("IV * II"));
        Scanner scanner = new Scanner(System.in, "UTF-8");
        while (true){
            String input =  scanner.nextLine();
            System.out.println(Calc(input));
        }

    }
    public static String Calc(String input) throws IOException {
        boolean romanNum = false;
        int a = 0;
        int b = 0;
        String[] word = input.split(" ");
        //Пробуем перевести римские в арабские, для расчета
        if (word[0].matches("[a-zA-Z]+")) {
            a = romanToArabic(word[0]);
            romanNum = true;
        } else {
            a =  Integer.parseInt(word[0]);
        }
        if (word[2].matches("[a-z-A-Z]+")) {
            b = romanToArabic(word[2]);
            romanNum = true;
        } else {
            b = Integer.parseInt(word[2]);
        }


        if ((a < 0 || a > 10) && (b < 0 || b > 10)){
            throw new IOException();
        }
        String iterator = String.valueOf(input.charAt(2));
        int result = 0;

        switch (word[1]){
            case "+":
                result = a + b;
                break;
            case "-":
                result = a - b;
                break;
            case "*":
                result = a * b;
                break;
            case "/":
                try {
                    result = a / b;
                } catch (ArithmeticException e){
                    System.out.println("Делить на 0 нельзя");
                }
                break;
        }
        input = String.valueOf(result);
        input = romanNum ? arabicToRoman(result) : input;
        return input;
    }

    /**
     * Переводит на римские цифры
     * @param input
     * @return
     */
    public static int romanToArabic(String input) {
        String romanNumeral = input.toUpperCase();
        int result = 0;

        List<RomanNumeral> romanNumerals = RomanNumeral.getReverseSortedValues();

        int i = 0;

        while ((romanNumeral.length() > 0) && (i < romanNumerals.size())) {
            RomanNumeral symbol = romanNumerals.get(i);
            if (romanNumeral.startsWith(symbol.name())) {
                result += symbol.getValue();
                romanNumeral = romanNumeral.substring(symbol.name().length());
            } else {
                i++;
            }
        }

        if (romanNumeral.length() > 0) {
            throw new IllegalArgumentException(input + " не может быть преобразован в римскую цифру");
        }

        return result;
    }

    /**
     * Переводит арабский на римские цифры
     * @param number
     * @return
     */
    public static String arabicToRoman(int number) {
        if ((number <= 0) || (number > 4000)) {
            throw new IllegalArgumentException(number + " is not in range (0,4000]");
        }

        List<RomanNumeral> romanNumerals = RomanNumeral.getReverseSortedValues();

        int i = 0;
        StringBuilder sb = new StringBuilder();

        while ((number > 0) && (i < romanNumerals.size())) {
            RomanNumeral currentSymbol = romanNumerals.get(i);
            if (currentSymbol.getValue() <= number) {
                sb.append(currentSymbol.name());
                number -= currentSymbol.getValue();
            } else {
                i++;
            }
        }

        return sb.toString();
    }
}
