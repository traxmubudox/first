public class Operand {
    public TypeOperand type;
    public int value;

    public Operand() {
        type = TypeOperand.NONE;
        value = 0;
    }
}

enum TypeOperand
{
    NONE,ARAB,ROME
}