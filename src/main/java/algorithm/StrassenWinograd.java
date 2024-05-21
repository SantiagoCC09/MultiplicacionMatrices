package algorithm;

public class StrassenWinograd {

    public static int[][] multiply(int[][] A, int[][] B) {
        int n = A.length;
        int[][] result = new int[n][n];
        if (n == 1) {
            result[0][0] = A[0][0] * B[0][0];
        } else {
            int[][] A11 = new int[n / 2][n / 2];
            int[][] A12 = new int[n / 2][n / 2];
            int[][] A21 = new int[n / 2][n / 2];
            int[][] A22 = new int[n / 2][n / 2];

            int[][] B11 = new int[n / 2][n / 2];
            int[][] B12 = new int[n / 2][n / 2];
            int[][] B21 = new int[n / 2][n / 2];
            int[][] B22 = new int[n / 2][n / 2];

            // Divide matrices into 4 submatrices
            split(A, A11, 0, 0);
            split(A, A12, 0, n / 2);
            split(A, A21, n / 2, 0);
            split(A, A22, n / 2, n / 2);

            split(B, B11, 0, 0);
            split(B, B12, 0, n / 2);
            split(B, B21, n / 2, 0);
            split(B, B22, n / 2, n / 2);

            // Strassen-Winograd algorithm calculations
            int[][] P1 = multiply(add(A11, A22), add(B11, B22));
            int[][] P2 = multiply(add(A21, A22), B11);
            int[][] P3 = multiply(A11, subtract(B12, B22));
            int[][] P4 = multiply(A22, subtract(B21, B11));
            int[][] P5 = multiply(add(A11, A12), B22);
            int[][] P6 = multiply(subtract(A21, A11), add(B11, B12));
            int[][] P7 = multiply(subtract(A12, A22), add(B21, B22));

            int[][] C11 = add(subtract(add(P1, P4), P5), P7);
            int[][] C12 = add(P3, P5);
            int[][] C21 = add(P2, P4);
            int[][] C22 = add(subtract(add(P1, P3), P2), P6);

            // Combine 4 submatrices into one
            join(C11, result, 0, 0);
            join(C12, result, 0, n / 2);
            join(C21, result, n / 2, 0);
            join(C22, result, n / 2, n / 2);
        }
        return result;
    }

    private static void split(int[][] P, int[][] C, int iB, int jB) {
        for (int i = 0, i2 = iB; i < C.length; i++, i2++)
            for (int j = 0, j2 = jB; j < C.length; j++, j2++)
                C[i][j] = P[i2][j2];
    }

    private static void join(int[][] C, int[][] P, int iB, int jB) {
        for (int i = 0, i2 = iB; i < C.length; i++, i2++)
            for (int j = 0, j2 = jB; j < C.length; j++, j2++)
                P[i2][j2] = C[i][j];
    }

    private static int[][] add(int[][] A, int[][] B) {
        int n = A.length;
        int[][] result = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                result[i][j] = A[i][j] + B[i][j];
        return result;
    }

    private static int[][] subtract(int[][] A, int[][] B) {
        int n = A.length;
        int[][] result = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                result[i][j] = A[i][j] - B[i][j];
        return result;
    }
}