package algorithm;

import tools.Imprimir;

public class NaivLoopUnrollingFour extends Imprimir {

    public static void naivLoopUnrollingFour(int[][] A, int[][] B) {
        int N = A.length;
        int[][] Result = new int[N][N];
        int i, j, k;
        int aux = 0;
        if (N % 4 == 0) {
            for (i = 0; i < N; i++) {
                for (j = 0; j < N; j++) {
                    aux = 0;
                    for (k = 0; k < N; k += 4) {
                        aux += A[i][k]*B[k][j] + A[i][k+1]*B[k+1][j] + A[i][k+2]*B[k+2][j]
                                + A[i][k+3]*B[k+3][j];
                    }
                    Result[i][j] = aux;
                }
            }
        } else if (N % 4 == 1) {
            int PP = N - 1;
            for (i = 0; i < N; i++) {
                for (j = 0; j < N; j++) {
                    aux = 0;
                    for (k = 0; k < PP; k += 4) {
                        aux += A[i][k]*B[k][j] + A[i][k+1]*B[k+1][j] + A[i][k+2]*B[k+2][j]
                                + A[i][k+3]*B[k+3][j];
                    }
                    Result[i][j] = (aux + A[i][PP]*B[PP][j]);
                }
            }
        } else if (N % 4 == 2) {
            int PP = N - 2;
            int PPP = N - 1;
            for (i = 0; i < N; i++) {
                for (j = 0; j < N; j++) {
                    aux = 0;
                    for (k = 0; k < PP; k += 4) {
                        aux += A[i][k]*B[k][j] + A[i][k+1]*B[k+1][j] + A[i][k+2]*B[k+2][j]
                                + A[i][k+3]*B[k+3][j];
                    }
                    Result[i][j] = (aux + A[i][PP]*B[PP][j] + A[i][PPP]*B[PPP][j]);
                }
            }
        } else {
            int PP = N - 3;
            int PPP = N - 2;
            int PPPP = N - 1;
            for (i = 0; i < N; i++) {
                for (j = 0; j < N; j++) {
                    aux = 0;
                    for (k = 0; k < PP; k += 4) {
                        aux += A[i][k]*B[k][j] + A[i][k+1]*B[k+1][j] + A[i][k+2]*B[k+2][j]
                                + A[i][k+3]*B[k+3][j];
                    }
                    Result[i][j] = (aux + A[i][PP]*B[PP][j] + A[i][PPP]*B[PPP][j]
                                                + A[i][PPPP]*B[PPPP][j]);
                }
            }
        }
//        imprimirMatriz(Result);
    }
}
