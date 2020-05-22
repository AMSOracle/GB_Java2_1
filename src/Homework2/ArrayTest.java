package Homework2;

public class ArrayTest {
    private static int MAXSIZE = 4;

    private static int sumArray(String[][] a) {
        int sum = 0;
        if (a.length != MAXSIZE) throw new MyArraySizeException("Размер массива не равен 4");
        for (int i = 0; i < MAXSIZE; i++) {
            if (a[i].length != MAXSIZE) throw new MyArraySizeException("Размер массива не равен 4");
            for (int j = 0; j < MAXSIZE; j++) {
                try {
                    sum += Integer.parseInt(a[i][j]);
                } catch (NumberFormatException ne) {
                    throw new MyArrayDataException("В ячейке [" + i + "][" + j + "] не число");
                }
            }
        }
        return sum;
    }

    public static void main(String[] args) {
        String[][] a = new String[][]{{"1", "2", "2", "3"}, {"1", "2", "2", "3"}, {"1", "2", "2", "3"}, {"1", "2", "f", "3"}};
        try {
            System.out.println("Сумма =" + sumArray(a));
        }catch (MyArraySizeException | MyArrayDataException me){
            System.out.println("Ошибка массива");
            me.printStackTrace();
        }

    }
}
