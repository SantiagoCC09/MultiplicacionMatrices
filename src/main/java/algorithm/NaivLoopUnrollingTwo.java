package algorithm;

import tools.Imprimir;

public class NaivLoopUnrollingTwo extends Imprimir {

    public static void naiveLoopUnrollingTwo(int[][] a, int[][] b) {
        int n = a.length;
        int[][] result = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j += 2) {
                int sum1 = 0, sum2 = 0;
                for (int k = 0; k < n; k++) {
                    sum1 += a[i][k] * b[k][j];
                    sum2 += a[i][k] * b[k][j + 1];
                }
                result[i][j] = sum1;
                result[i][j + 1] = sum2;
            }
        }
//        imprimirMatriz(result);
    }
}
