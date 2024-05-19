package algorithm;

import java.util.Arrays;

public class III_4_Parallel_Block {

    public static void alg_III_4_Parallel_Block(double[][] matrizA, double[][] matrizB, int size1, int size2) {
        double [][] matrizRes = new double[size1][size2];
        Arrays.stream(new int[]{0}).parallel().forEach(i1  -> {
            for (i1 = 0; i1 < size1; i1 += size2) {
                for (int j1 = 0; j1 < size1; j1 += size2) {
                    for (int k1 = 0; k1 < size1; k1 += size2) {
                        for (int i = i1; i < i1 + size2 && i < size1; i++) {
                            for (int j = j1; j < j1 + size2 && j < size1; j++) {
                                for (int k = k1; k < k1 + size2 && k < size1; k++) {
                                    matrizRes[i][j] += matrizA[i][k] * matrizB[k][j];
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
        alg_III_4_Parallel_Block(matrizA, matrizB, N, P);
    }
}