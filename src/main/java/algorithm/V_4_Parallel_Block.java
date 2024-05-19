package algorithm;

import java.util.Arrays;

public class V_4_Parallel_Block {
    public static void alg_V_4_ParallelBlockTres(double[][] matrizA, double[][] matrizB, int size1, int size2) {
        double[][] matrizC = new double[size1][size1];
        Arrays.stream(new int[]{0}).parallel().forEach(i1 -> {
            for (i1 = 0; i1 < size1; i1 += size2) {
                for (int j1 = 0; j1 < size1; j1 += size2) {
                    for (int k1 = 0; k1 < size1; k1 += size2) {
                        for (int i = i1; i < i1 + size2 && i < size1; i++) {
                            for (int j = j1; j < j1 + size2 && j < size1; j++) {
                                for (int k = k1; k < k1 + size2 && k < size1; k++) {
                                    matrizC[k][i] += matrizA[k][j] * matrizB[j][i];
                                }
                            }
                        }
                    }
                }
            }
        });
    }

    public static void multiply(double[][] matrizA, double[][] matrizB) {
        int N = matrizA.length;
        int P = matrizB.length;
        alg_V_4_ParallelBlockTres(matrizA, matrizB, N, P);
    }
}