package algorithm;

import java.util.Arrays;

public class StrassenWinograd {

    /**
     * Método para multiplicar dos matrices utilizando el algoritmo de Strassen-Winograd.
     *
     * @param A primera matriz
     * @param B segunda matriz
     * @param Result matriz de resultado
     * @param N número de filas de la matriz A
     * @param P número de columnas de la matriz A y número de filas de la matriz B
     * @param M número de columnas de la matriz B
     */

    public static void multiply(double[][] A, double[][] B, double[][] Result, int N, int P, int M) {

        /**
         * Método recursivo para el algoritmo de Strassen-Winograd.
         *
         * @param A primera matriz
         * @param B segunda matriz
         * @param Result matriz de resultado
         * @param N tamaño de la matriz
         * @param m tamaño mínimo de la matriz para el uso recursivo del algoritmo de Strassen-Winograd
         */
        int MaxSize;
        int k;
        int m;
        int NewSize;

        MaxSize = Math.max(N, Math.max(P, M));

        if (MaxSize < 16) {
            MaxSize = 16; // otherwise it is not possible to compute k
        }

        k = (int) Math.floor(Math.log(MaxSize) / Math.log(2)) - 4;
        m = (int) Math.floor(MaxSize * Math.pow(2, -k)) + 1;
        NewSize = m * (int) Math.pow(2, k);

        // add zero rows and columns to use Strassen's algorithm
        double[][] NewA = new double[NewSize][NewSize];
        double[][] NewB = new double[NewSize][NewSize];
        double[][] AuxResult = new double[NewSize][NewSize];

        // Initialize with zeros
        for (double[] row : NewA) {
            Arrays.fill(row, 0.0);
        }
        for (double[] row : NewB) {
            Arrays.fill(row, 0.0);
        }
        for (double[] row : AuxResult) {
            Arrays.fill(row, 0.0);
        }

        // Copy values from A and B
        for (int i = 0; i < N; i++) {
            System.arraycopy(A[i], 0, NewA[i], 0, P);
        }
        for (int i = 0; i < P; i++) {
            System.arraycopy(B[i], 0, NewB[i], 0, M);
        }

        strassenWinogradStep(NewA, NewB, AuxResult, NewSize, m);

        // Extract the result
        for (int i = 0; i < N; i++) {
            System.arraycopy(AuxResult[i], 0, Result[i], 0, M);
        }
    }

    private static void strassenWinogradStep(double[][] A, double[][] B, double[][] Result, int N, int m) {
        int NewSize;

        if ((N % 2 == 0) && (N > m)) { // recursive use of StrassenNaivStep
            NewSize = N / 2;

            // Decompose A and B
            double[][] A11 = new double[NewSize][NewSize];
            double[][] A12 = new double[NewSize][NewSize];
            double[][] A21 = new double[NewSize][NewSize];
            double[][] A22 = new double[NewSize][NewSize];
            double[][] B11 = new double[NewSize][NewSize];
            double[][] B12 = new double[NewSize][NewSize];
            double[][] B21 = new double[NewSize][NewSize];
            double[][] B22 = new double[NewSize][NewSize];

            // Create ResultPart, Aux1,...,Aux7 and Helper1, Helper2
            double[][] A1 = new double[NewSize][NewSize];
            double[][] A2 = new double[NewSize][NewSize];
            double[][] B1 = new double[NewSize][NewSize];
            double[][] B2 = new double[NewSize][NewSize];
            double[][] ResultPart11 = new double[NewSize][NewSize];
            double[][] ResultPart12 = new double[NewSize][NewSize];
            double[][] ResultPart21 = new double[NewSize][NewSize];
            double[][] ResultPart22 = new double[NewSize][NewSize];
            double[][] Helper1 = new double[NewSize][NewSize];
            double[][] Helper2 = new double[NewSize][NewSize];
            double[][] Aux1 = new double[NewSize][NewSize];
            double[][] Aux2 = new double[NewSize][NewSize];
            double[][] Aux3 = new double[NewSize][NewSize];
            double[][] Aux4 = new double[NewSize][NewSize];
            double[][] Aux5 = new double[NewSize][NewSize];
            double[][] Aux6 = new double[NewSize][NewSize];
            double[][] Aux7 = new double[NewSize][NewSize];
            double[][] Aux8 = new double[NewSize][NewSize];
            double[][] Aux9 = new double[NewSize][NewSize];

            // Fill new matrices
            for (int i = 0; i < NewSize; i++) {
                System.arraycopy(A[i], 0, A11[i], 0, NewSize);
                System.arraycopy(A[i], NewSize, A12[i], 0, NewSize);
                System.arraycopy(A[NewSize + i], 0, A21[i], 0, NewSize);
                System.arraycopy(A[NewSize + i], NewSize, A22[i], 0, NewSize);
                System.arraycopy(B[i], 0, B11[i], 0, NewSize);
                System.arraycopy(B[i], NewSize, B12[i], 0, NewSize);
                System.arraycopy(B[NewSize + i], 0, B21[i], 0, NewSize);
                System.arraycopy(B[NewSize + i], NewSize, B22[i], 0, NewSize);
            }

            // Computing the 4 + 9 auxiliary variables
            minus(A11, A21, A1, NewSize, NewSize);
            minus(A22, A1, A2, NewSize, NewSize);
            minus(B22, B12, B1, NewSize, NewSize);
            plus(B1, B11, B2, NewSize, NewSize);
            strassenWinogradStep(A11, B11, Aux1, NewSize, m);
            strassenWinogradStep(A12, B21, Aux2, NewSize, m);
            strassenWinogradStep(A2, B2, Aux3, NewSize, m);
            plus(A21, A22, Helper1, NewSize, NewSize);
            minus(B12, B11, Helper2, NewSize, NewSize);
            strassenWinogradStep(Helper1, Helper2, Aux4, NewSize, m);
            strassenWinogradStep(A1, B1, Aux5, NewSize, m);
            minus(A12, A2, Helper1, NewSize, NewSize);
            strassenWinogradStep(Helper1, B22, Aux6, NewSize, m);
            minus(B21, B2, Helper1, NewSize, NewSize);
            strassenWinogradStep(A22, Helper1, Aux7, NewSize, m);
            plus(Aux1, Aux3, Aux8, NewSize, NewSize);
            plus(Aux8, Aux4, Aux9, NewSize, NewSize);

            // Computing the four parts of the result
            plus(Aux1, Aux2, ResultPart11, NewSize, NewSize);
            plus(Aux9, Aux6, ResultPart12, NewSize, NewSize);
            plus(Aux8, Aux5, Helper1, NewSize, NewSize);
            plus(Helper1, Aux7, ResultPart21, NewSize, NewSize);
            plus(Aux9, Aux5, ResultPart22, NewSize, NewSize);

            // Store results in the "result matrix"
            for (int i = 0; i < NewSize; i++) {
                System.arraycopy(ResultPart11[i], 0, Result[i], 0, NewSize);
                System.arraycopy(ResultPart12[i], 0, Result[i], NewSize, NewSize);
                System.arraycopy(ResultPart21[i], 0, Result[NewSize + i], 0, NewSize);
                System.arraycopy(ResultPart22[i], 0, Result[NewSize + i], NewSize, NewSize);
            }
        } else {
            // Use naive algorithm
            naivStandard(A, B, Result, N, N, N);
        }
    }

    private static void plus(double[][] A, double[][] B, double[][] Result, int N, int M) {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                Result[i][j] = A[i][j] + B[i][j];
            }
        }
    }

    private static void minus(double[][] A, double[][] B, double[][] Result, int N, int M) {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                Result[i][j] = A[i][j] - B[i][j];
            }
        }
    }

    private static void naivStandard(double[][] A, double[][] B, double[][] Result, int N, int P, int M) {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                double aux = 0.0;
                for (int k = 0; k < P; k++) {
                    aux += A[i][k] * B[k][j];
                }
                Result[i][j] = aux;
            }
        }
    }

    public static void multiply(double[][] A, double[][] B) {
        int N = A.length;
        int P = A[0].length;
        int M = B[0].length;

        double[][] Result = new double[N][M];

        multiply(A, B, Result, N, P, M);
    }


}