package algorithm;

public class WinogradScaled {
    public static void algWinogradScaled(double[][] matrizA, double[][] matrizB, double[][] matrizRes, int N, int P, int M) {
        int i, j;
        double[][] copyA = new double[N][P];
        double[][] copyB = new double[P][M];

        double a = normInf(matrizA, N, P);
        double b = normInf(matrizB, P, M);
        double lambda = Math.floor(0.5 + Math.log(b/a)/Math.log(4));

        multiplyWithScalar(matrizA, copyA, N, P, Math.pow(2 , lambda));
        multiplyWithScalar(matrizB, copyB, P, M, Math.pow(2 , -lambda));

        algWinogradOriginal(copyA, copyB, matrizRes, N, P, M);

    }

    private static void algWinogradOriginal(double[][] matrizA, double[][] matrizB, double[][] matrizRes, int N, int P, int M) {
        int i, j, k;
        double aux;
        int upsilon = P % 2;
        int gamma = P - upsilon;
        double[] y = new double[M];
        double[] z = new double[N];

        for (i = 0; i < M; i++) {
            aux = 0.0;
            for (j = 0; j < gamma; j += 2) {
                aux += matrizA[i][j] * matrizA[i][j + 1];
            }
            y[i] = aux;
        }

        for (i = 0; i < N; i++) {
            aux = 0.0;
            for (j = 0; j < gamma; j+=2) {
                aux += matrizB[j][i] * matrizB[j+1][i];
            }
            z[i] = aux;
        }

        if (upsilon == 1) {
            int PP = P - 1;
            for (i = 0; i < M; i++) {
                for (k = 0; k < N; k++) {
                    aux = 0.0;
                    for (j = 0; j < gamma; j+=2) {
                        aux += (matrizA[i][j] + matrizB[j+1][k])
                                * (matrizA[i][j+1] + matrizB[j][k]);
                    }
                    matrizRes[i][k] = aux - y[i] - z[k] + matrizA[i][PP] * matrizB[PP][k];
                }
            }
        } else {
            for (i = 0; i < M; i++) {
                aux = 0.0;
                for (k = 0; k < N; k++) {
                    for (j = 0; j < gamma; j += 2){
                        aux += (matrizA[i][j] + matrizB[j+1][k])
                                * (matrizA[i][j+1] + matrizB[j][k]);
                    }
                    matrizRes[i][k] = aux - y[i] -z[k];
                }
            }
        }
        // Liberar memoria, se igualan las variables a null
        y = null;
        z = null;

    }

    private static void multiplyWithScalar(double[][] matrizA, double[][] matrizB, int N, int M, double scalar) {
        int i , j;
        for(i = 0; i < N; i++) {
            for (j = 0; j < M; j++) {
                matrizB[i][j] = matrizA[i][j] * scalar;
            }
        }
    }

    private static double normInf(double[][] matrizA, int N, int M) {
        int i, j;
        double max = Double.NEGATIVE_INFINITY;
        for(i = 0; i < N; i++) {
            double suma = 0.0;
            for (j = 0; j < M; j++) {
                suma += Math.abs(matrizA[i][j]);
            }
            if (suma > max) {
                max = suma;
            }
        }
        return max;
    }

    public static void multiply(double[][] matrizA, double[][] matrizB) {
        int N = matrizA.length;
        int P = matrizB.length;
        int M = matrizB[0].length;
        double[][] matrizRes = new double[N][M];
        algWinogradScaled(matrizA, matrizB, matrizRes, N, P, M);
    }
}