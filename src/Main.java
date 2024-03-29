import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args)  throws IOException{
        //System.out.println(Calc("IV * II"));
        Scanner scanner = new Scanner(System.in, "UTF-8");
        boolean work = true;
        while (work){
            String input =  scanner.nextLine();
            if (input.equals("exit"))  work = false;
            else System.out.println(сalc(input));

        }

    }
    public static String сalc(String input) throws IOException {
        boolean romanNum1 = false;
        boolean romanNum2 = false;
        int a = 0;
        int b = 0;
        String[] word = input.split(" ");
        if (word.length > 3) throw new IOException("Не должно быть более двух операндов");
        //Пробуем перевести римские в арабские, для расчета
        if (word[0].matches("[a-zA-Z]+") && isRoman(word[0])) {
            a = romanToArabic(word[0]);
            romanNum1 = true;
        } else {
            a =  Integer.parseInt(word[0]);
        }
        if (word[2].matches("[a-z-A-Z]+") && isRoman(word[2])) {
            b = romanToArabic(word[2]);
            romanNum2 = true;
        } else {
            b = Integer.parseInt(word[2]);
        }

        //Если одино цифра арабский а другой римский то выбрасываем исключение
        if ((!romanNum1 && romanNum2) || (romanNum1 && !romanNum2)) {
            throw  new IOException("Все значение должны быть одного типа.");
        }
        // +                                -
        if ((a < 0 || a > 10 ) || (b < 0 || b > 10)){

            throw new IOException("Больше 10 нельзя !!!");

        }
        String iterator = String.valueOf(input.charAt(2));
        int result = 0;
        //проверям оператора
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
                    System.out.println("Деление на ноль");
                }
                break;
            default:
                throw new ArithmeticException("некорректный математический оператор");
        }
        input = String.valueOf(result);
        input = romanNum1 && romanNum2 ? arabicToRoman(result) : input;
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
    //функция проверяет правильнось написание римских цыфр
    public static boolean isRoman(String input) {
        return input.matches("^(M{0,3})(CM|CD|D?C{0,3})(XC|XL|L?X{0,3})(IX|IV|V?I{0,3})$");
    }
}
