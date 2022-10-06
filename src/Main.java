import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Calculator calc = new Calculator();
        System.out.println("Введите выражение  и нажмите Ввод:");
        String s = sc.nextLine();
        sc.close();

        try {
            calc.parseStr(s);
            Operand operand = calc.calc();
            if (operand.type == TypeOperand.ARAB)
                System.out.println("Ответ = " + operand.value);
            else if (operand.type == TypeOperand.ROME)
                System.out.println("Ответ = " + calc.convArabToRome(operand.value));
        }
        catch(CalculatorException ex){
            System.out.println(ex.getMessage());
        }
    }

}


