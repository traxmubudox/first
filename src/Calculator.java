public class Calculator {
    private Oper oper;
    private Operand operand1 = new Operand();
    private Operand operand2 = new Operand();
    public Calculator() {
        oper = Oper.NONE;
    }

    public Operand calc() throws CalculatorException
    {
        Operand operand = new Operand();
        operand.type = operand1.type;

        switch (oper)
        {
            case PLUS:
                operand.value = operand1.value + operand2.value;
                break;
            case MINUS:
                    operand.value = operand1.value - operand2.value;
                break;
            case MULTI:
                    operand.value = operand1.value * operand2.value;
                break;
            case DIV:
                if (operand2.value != 0)
                    operand.value = operand1.value / operand2.value;
                break;
        }

        if (operand.type == TypeOperand.ROME && operand.value < 1) throw new CalculatorException("ошибка. результат вычисления меньше еденицы");

        return operand;
    }

    public int parseStr(String s) throws CalculatorException{
        int r = 0;
        oper = Oper.NONE;

        int pos_oper = 0;

        for (; pos_oper < s.length(); pos_oper++) {
            char c = s.charAt(pos_oper);
            if (c == '+') {
                oper = Oper.PLUS;
                break;
            } else if (c == '-') {
                oper = Oper.MINUS;
                break;
            } else if (c == '*') {
                oper = Oper.MULTI;
                break;
            } else if (c == '/') {
                oper = Oper.DIV;
                break;
            }
        }

        if (oper == Oper.NONE)  throw new CalculatorException("ошибка. не найден оператор (+,-,/,*) в строке " + s); // ошибка. не найден оператор в строке(+,-,/,*)

        String s1 = s.substring(0, pos_oper);
        String s2 = s.substring(pos_oper+1);
        s1 = s1.trim();
        s1 = s1.toUpperCase();
        s2 = s2.trim();
        s2 = s2.toUpperCase();

       if (parseOperand(s1, operand1) > 0) throw new CalculatorException("ошибка. первый операнд " + s1 + " в строке " + s + " неправилного формата. должно быть арабское или римское число от 1 до 10"); // ошибка. неправилного формата первый операнд
       if (parseOperand(s2, operand2) > 0) throw new CalculatorException("ошибка. второй операнд " + s2 + " в строке  " + s + " неправилного формата. должно быть арабское или римское число от 1 до 10");  // ошибка. неправилного формата второй операнд

        if (operand1.type != operand2.type) throw new CalculatorException("ошибка. операнды в строке " + s + " разных типов. должны быть только арабиские или только римские цифры "); // ошибка. операнды разных типов

        if (operand1.type == TypeOperand.ARAB)
        {
            if (operand1.value < 1) throw new CalculatorException("ошибка. первый операнд " + s1 + " в строке " + s + " меньше единицы. должно быть арабское число от 1 до 10"); // ошибка. первый операнд меньше единицы
            if (operand1.value > 10) throw new CalculatorException("ошибка. первый операнд " + s1 + " в строке " + s + " больше десяти. должно быть арабское число от 1 до 10"); // ошибка. первый операнд больше десяти
            if (operand2.value < 1) throw new CalculatorException("ошибка. второй операнд " + s2 + " в строке " + s + " меньше единицы. должно быть арабское число от 1 до 10"); // ошибка. второй операнд меньше единицы
            if (operand2.value > 10) throw new CalculatorException("ошибка. второй операнд " + s2 + " в строке " + s + " болььше десяти. должно быть арабское число от 1 до 10"); // ошибка. второй операнд больше десяти
        }
        else if (operand1.type == TypeOperand.ROME)
        {
            if (operand1.value < 1 || operand1.value > 10) throw new CalculatorException("ошибка. первый операнд " + s1 + " в строке " + s + " должно быть римское число от 1 до 10");
            if (operand2.value < 1 || operand2.value > 10) throw new CalculatorException("ошибка. второй операнд " + s2 + " в строке " + s + " должно быть римское число от 1 до 10");
        }

        return r;
    }

    public int parseOperand(String s, Operand operand)
    {
        int r = 0;
        String s1 = "";

        for (int i =  0; i < s.length(); i++)
        {
            char c = s.charAt(i);

            if (c == '0' || c == '1'  || c == '2'|| c == '3' || c == '4' || c == '5' ||
                    c == '6' || c == '7' || c == '8'  || c == '9')
            {
                s1 = s1 + c;
            }
            else
            {
               s1 = "";
               break;
            }
        }

        if (s1.length() != 0) //opperand арабское число
        {
           operand.type =TypeOperand.ARAB;
           operand.value = Integer.parseInt(s1);
        }
        else
        {
            for (int i =  0; i < s.length(); i++)
            {
                char c = s.charAt(i);

                if (c == 'I' || c == 'V'  || c == 'X')
                {
                    s1 = s1 + c;
                }
                else
                {
                    s1 = "";
                    break;
                }
            }

            if (s1.length() != 0) //opperand римское число
            {
                operand.type =TypeOperand.ROME;
                operand.value = convRomeToArab(s1);
            }
            else
            {
                r = 1;
            }
        }

        return r;
    }

    public int convRomeToArab(String s)
    {
        if (s.equals("I")) return 1;
        else if (s.equals("II")) return 2;
        else if (s.equals("III")) return 3;
        else if (s.equals("IV")) return 4;
        else if (s.equals("V")) return 5;
        else if (s.equals("VI")) return 6;
        else if (s.equals("VII")) return 7;
        else if (s.equals("VIII")) return 8;
        else if (s.equals("IX")) return 9;
        else if (s.equals("X")) return 10;
        else return 0;
    }

    public String convArabToRome(int  n)
    {
        String s = "";
        String s1 = "";
        String s10 = "";

        if (n == 100)
        {
            s = "C";
        }
        else {
            int n1 = n % 10;
            int n10 = n / 10;

            if (n1 == 1) s1 = "I";
            else if (n1 == 2) s1 = "II";
            else if (n1 == 3) s1 = "III";
            else if (n1 == 4) s1 = "IV";
            else if (n1 == 5) s1 = "V";
            else if (n1 == 6) s1 = "VI";
            else if (n1 == 7) s1 = "VII";
            else if (n1 == 8) s1 = "VIII";
            else if (n1 == 9) s1 = "IX";

            if (n10 == 1) s10 = "X";
            else if (n10 == 2) s10 = "XX";
            else if (n10 == 3) s10 = "XXX";
            else if (n10 == 4) s10 = "XL";
            else if (n10 == 5) s10 = "L";
            else if (n10 == 6) s10 = "LX";
            else if (n10 == 7) s10 = "LXX";
            else if (n10 == 8) s10 = "LXXX";
            else if (n10 == 9) s10 = "XC";

            s = s10 + s1;
        }

        return s;
    }
}

enum Oper
{
    NONE, PLUS, MINUS, MULTI, DIV
}
