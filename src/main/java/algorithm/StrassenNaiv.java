package algorithm;

public class StrassenNaiv {
    public static void algStrassenNaiv(double[][] matrizA, double[][] matrizB, double[][] matrizRes, int N, int P, int M) {
        int maxSize, k, m, newSize, i, j;
        maxSize = max(N,P);

        if (maxSize < 16) {
            maxSize = 16; // De forma contraria no es posible calcular el valor de k
        }
        k = (int) Math.floor(Math.log(maxSize)/Math.log(2)) -4;
        m = (int) Math.floor(maxSize * Math.pow(2,-k)) +1;

        newSize = m * (int) Math.pow(2,k);

        double[][] newA = new double[newSize][];
        double[][] newB = new double[newSize][];
        double[][] auxResult = new double[newSize][];
        for (i = 0; i < newSize; i++) {
            newA[i] = new double[newSize];
            newB[i] = new double[newSize];
            auxResult[i] = new double[newSize];
        }

        for (i = 0; i < newSize; i++) {
            for (j = 0; j < newSize; j++) {
                newA[i][j] = 0.0;
                newB[i][j] = 0.0;
            }
        }
        for (i = 0; i < N; i++) {
            for (j = 0; j < P; j++) {
                newA[i][j] = matrizA[i][j];
            }
        }
        for (i = 0; i < N; i++) {
            for (j = 0; j < M; j++) {
                newB[i][j] = matrizB[i][j];
            }
        }
        strassenNaivStep(newA, newB, auxResult, newSize, m);
        // Extraer el resultado
        for (i = 0; i < N; i++) {
            for (j = 0; j < M; j++) {
                matrizRes[i][j] = auxResult[i][j]; // Resultado
            }
        }
    }

    private static void strassenNaivStep(double[][] matrizA, double[][] matrizB, double[][] matrizRes, int N, int m) {
        int i, j, newSize;

        if ((N % 2 == 0) && (N > m)) {
            newSize = N / 2;

            double[][] varA11 = new double[newSize][];
            double[][] varA12 = new double[newSize][];
            double[][] varA21 = new double[newSize][];
            double[][] varA22 = new double[newSize][];
            double[][] varB11 = new double[newSize][];
            double[][] varB12 = new double[newSize][];
            double[][] varB21 = new double[newSize][];
            double[][] varB22 = new double[newSize][];

            double[][] resultadoPart11 = new double[newSize][];
            double[][] resultadoPart12 = new double[newSize][];
            double[][] resultadoPart21 = new double[newSize][];
            double[][] resultadoPart22 = new double[newSize][];

            double[][] helper1 = new double[newSize][];
            double[][] helper2 = new double[newSize][];

            double[][] aux1 = new double[newSize][];
            double[][] aux2 = new double[newSize][];
            double[][] aux3 = new double[newSize][];
            double[][] aux4 = new double[newSize][];
            double[][] aux5 = new double[newSize][];
            double[][] aux6 = new double[newSize][];
            double[][] aux7 = new double[newSize][];

            for (i = 0; i < newSize; i++) {
                varA11[i] = new double[newSize];
                varA12[i] = new double[newSize];
                varA21[i] = new double[newSize];
                varA22[i] = new double[newSize];
                varB11[i] = new double[newSize];
                varB12[i] = new double[newSize];
                varB21[i] = new double[newSize];
                varB22[i] = new double[newSize];

                resultadoPart11[i] = new double[newSize];
                resultadoPart12[i] = new double[newSize];
                resultadoPart21[i] = new double[newSize];
                resultadoPart22[i] = new double[newSize];

                helper1[i] = new double[newSize];
                helper2[i] = new double[newSize];

                aux1[i] = new double[newSize];
                aux2[i] = new double[newSize];
                aux3[i] = new double[newSize];
                aux4[i] = new double[newSize];
                aux5[i] = new double[newSize];
                aux6[i] = new double[newSize];
                aux7[i] = new double[newSize];
            }
            // Para llenar nuevas matrices
            for (i = 0; i < newSize; i++) {
                for (j = 0; j < newSize; j++) {
                    varA11[i][j] = matrizA[i][j];
                }
            }

            for (i = 0; i < newSize; i++) {
                for (j = 0; j < newSize; j++) {
                    varA12[i][j] = matrizA[i][newSize + j];
                }
            }

            for (i = 0; i < newSize; i++) {
                for (j = 0; j < newSize; j++) {
                    varA21[i][j] = matrizA[newSize + i][j];
                }
            }

            for (i = 0; i < newSize; i++) {
                for (j = 0; j < newSize; j++) {
                    varA22[i][j] = matrizA[newSize + i][newSize + j];
                }
            }

            for (i = 0; i < newSize; i++) {
                for (j = 0; j < newSize; j++) {
                    varB11[i][j] = matrizB[i][j];
                }
            }

            for (i = 0; i < newSize; i++) {
                for (j = 0; j < newSize; j++) {
                    varB12[i][j] = matrizB[i][newSize + j];
                }
            }

            for (i = 0; i < newSize; i++) {
                for (j = 0; j < newSize; j++) {
                    varB21[i][j] = matrizB[newSize + i][j];
                }
            }

            for (i = 0; i < newSize; i++) {
                for (j = 0; j < newSize; j++) {
                    varB22[i][j] = matrizB[newSize + i][newSize + j];
                }
            }
            plus(varA11, varA22, helper1, newSize, newSize);
            plus(varB11, varB22, helper2, newSize, newSize);
            strassenNaivStep(helper1, helper2, aux1, newSize, m);

            plus(varA21, varA22, helper1, newSize, newSize);
            strassenNaivStep(helper1, varB11,aux2, newSize, m);

            minus(varB12, varB22, helper1, newSize, newSize);
            strassenNaivStep(varA11, helper1, aux3, newSize, m);

            minus(varB21, varB11, helper1, newSize, newSize);
            strassenNaivStep(varA22, helper1, aux4, newSize, m);

            plus(varA11, varA12, helper1, newSize, newSize);
            strassenNaivStep(helper1, varB22, aux5, newSize, m);

            minus(varA21, varA11, helper1, newSize, newSize);
            plus(varB11, varB12, helper2, newSize, newSize);
            strassenNaivStep(helper1, helper2, aux6, newSize, m);

            minus(varA12, varA22, helper1, newSize, newSize);
            plus(varB21, varB22, helper2, newSize, newSize);
            strassenNaivStep(helper1, helper2, aux7, newSize, m);

            // Calcular las partes del resultado (cuatro)
            plus(aux1, aux4, resultadoPart11, newSize, newSize);
            minus(resultadoPart11, aux5, resultadoPart11, newSize, newSize);
            plus(resultadoPart11, aux7, resultadoPart11, newSize, newSize);

            plus(aux3, aux5, resultadoPart12, newSize, newSize);
            plus(aux2, aux4, resultadoPart21, newSize, newSize);

            plus(aux1, aux3, resultadoPart22, newSize, newSize);
            minus(resultadoPart22, aux2, resultadoPart22, newSize, newSize);
            plus(resultadoPart22, aux6, resultadoPart22, newSize, newSize);

            // Almacenar resultados en la matriz
            for (i = 0; i < newSize; i++) {
                for (j = 0; j < newSize; j++) {
                    matrizRes[i][j] = resultadoPart11[i][j];
                }
            }

            for (i = 0; i < newSize; i++) {
                for (j = 0; j < newSize; j++) {
                    matrizRes[i][newSize + j] = resultadoPart12[i][j];
                }
            }

            for( i = 0; i < newSize; i++){
                for( j = 0; j < newSize; j++){
                    matrizRes[newSize + i][j] = resultadoPart21[i][j];
                }
            }

            for( i = 0; i < newSize; i++){
                for( j = 0; j < newSize; j++){
                    matrizRes[newSize + i][newSize + j] = resultadoPart22[i][j];
                }
            }

            // Variables auxiliares
            varA11 = null;
            varA12 = null;
            varA21 = null;
            varA22 = null;

            varB11 = null;
            varB12 = null;
            varB21 = null;
            varB22 = null;

            resultadoPart11 = null;
            resultadoPart12 = null;
            resultadoPart21 = null;
            resultadoPart22 = null;

            helper1 = null;
            helper2 = null;

            aux1 = null;
            aux2 = null;
            aux3 = null;
            aux4 = null;
            aux5 = null;
            aux6 = null;
            aux7 = null;
        } else {
            // Algoritmo ingenuo
            algoritmoNaivStandard(matrizA, matrizB, matrizRes, N, N, N);
        }
    }

    private static void algoritmoNaivStandard(double[][] matrizA, double[][] matrizB, double[][] matrizRes, int N, int P, int M) {
        double aux;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                aux = 0.0;
                for (int k = 0; k < P; k++) {
                    aux += matrizA[i][k] * matrizB[k][j];
                }
                matrizRes[i][j] = aux;
            }
        }
    }

    private static int max(int N, int P) {
        if (N < P) {
            return P;
        } else {
            return N;
        }
    }

    private static void minus(double[][] matrizA, double[][] matrizB, double[][] matrizRes, int N, int M) {
        for (int i =0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                matrizRes[i][j] = matrizA[i][j] - matrizB[i][j];
            }
        }
    }

    private static void plus(double[][] matrizA, double[][] matrizB, double[][] matrizRes, int N, int M) {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                matrizRes[i][j] = matrizA[i][j] + matrizB[i][j];
            }
        }
    }

    public static void multiply(double[][] matrizA, double[][] matrizB) {
        int N = matrizA.length;
        int P = matrizB.length;
        int M = matrizB[0].length;
        double[][] matrizRes = new double[N][M];
        algStrassenNaiv(matrizA, matrizB, matrizRes, N, P, M);
    }
}
