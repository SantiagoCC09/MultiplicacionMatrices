package algorithm;

import tools.Imprimir;

public class NaivOnArray extends Imprimir {

    public static void naivOnArray(int[][] a, int[][] b) {
        int n = a.length;
        int[][] result = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                result[i][j] = 0;
                for (int k = 0; k < n; k++) {
                    result[i][j] += a[i][k]*b[k][j];
                }
            }
        }
//        imprimirMatriz(result);
    }
}
