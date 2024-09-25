import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public abstract class Utiles {

    public static final Scanner s = new Scanner(System.in);

    public static Random random = new Random();

    public static int ingresarEntero(int min, int max) {

        int opc=0;
        boolean error;

        do {
            error = false;
            try {
                opc = s.nextInt();
                if((opc<min)||(opc>max)) {
                    System.out.println("Error. Debe ingresar un numero del " + min + " al " + max);
                    error = true;
                }
                s.nextLine();
            } catch (InputMismatchException e) {
                s.nextLine();
                System.out.println("Error. Tipo de dato mal ingresado");
                error = true;

            }
        }while(error);

        return opc;
    }

    public static float ingresarFloat(float min, float max) {

        float opc=0;
        boolean error;

        do {
            error = false;
            try {
                opc = s.nextFloat();
                if((opc<min)||(opc>max)) {
                    System.out.println("Error. Debe ingresar un numero del " + min + " al " + max);
                    error = true;
                }
                s.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Error. Tipo de dato mal ingresado");
                error = true;
                s.nextLine();
            }
        }while(error);

        return opc;
    }

    public static Float ingresarFloat() {

        Float opc= 0f;
        boolean error;

        do {
            error = false;
            try {
                String nextLine = s.nextLine();
                opc = nextLine.isEmpty() ? null : Float.parseFloat(nextLine);
            } catch (InputMismatchException | NumberFormatException e) {
                System.out.println("Error. Tipo de dato mal ingresado");
                error = true;
                s.nextLine();
            }
        }while(error);

        return opc;
    }

    public static String ingresarString() {
        String str;
        boolean error;

        do {
            error = false;
            str = s.nextLine();

            if (str.trim().isEmpty()) {
                System.out.println("Error. Debe ingresar una cadena de texto no vacía.");
                error = true;
            }

        } while (error);

        return str;
    }


    public static void esperar(int milis) {
        try {
            Thread.sleep(milis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}