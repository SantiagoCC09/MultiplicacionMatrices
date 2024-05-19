package tools;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class LeerArchivoTxt {

    public static int[][] leerArchivo(String ruta, int n) {

        int[][] matriz = new int[n][n]; // matriz para guardar los números

        try {
            File archivo = new File(ruta);
            Scanner lector = new Scanner(archivo);

            int i = 0;
            int j = 0;
            while (lector.hasNextInt()) {
                matriz[i][j] = lector.nextInt();
                j++;
                if (j == n) {
                    i++;
                    j = 0;
                }
            }
            lector.close();
        } catch (FileNotFoundException e) {
            System.out.println("Archivo no encontrado: " + e.getMessage());
        }
        return matriz;
    }

}
