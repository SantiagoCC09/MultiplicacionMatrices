package algorithm;

public class StrassenWinograd {

    public static void algStrassenWinograd(double[][] matrizA, double[][] matrizB, double[][] matrizRes, int N, int P, int M) {
        int maxSize, valorK, valorM,  newSize, i, j;
        maxSize = max(N,P);
        maxSize = max(maxSize,M);
        if (maxSize < 16) {
            maxSize = 16; // Con otro valor no es posible hallar k
        }
        valorK = (int) Math.floor(Math.log(maxSize)/Math.log(2) -4);
        valorM = (int) Math.floor(maxSize * Math.pow(2, -valorK) +1);
        newSize = valorM * (int) Math.pow(2 , valorK);

        double [][] newA = new double[newSize][];
        double [][] newB = new double[newSize][];
        double [][] auxResultado = new double[newSize][];
        for (i = 0; i < newSize; i++) {
            newA[i] = new double[newSize];
            newB[i] = new double[newSize];
            auxResultado[i] = new double[newSize];
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

        for (i = 0; i < P; i++) {
            for (j = 0; j < M; j++) {
                newB[i][j] = matrizB[i][j];
            }
        }

        // llamado al metodo
        strassenWinogradStep(newA, newB, auxResultado, newSize, valorM);

        // Ciclos para extraer el resultado
        for (i = 0; i < N; i++) {
            for (j = 0; j < M; j++) {
                matrizRes[i][j] = auxResultado[i][j];
            }
        }

    }

    private static void strassenWinogradStep(double[][] matrizA, double[][] matrizB, double[][] matrizRes, int N, int valorM) {
        int i , j, newSize;

        if ((N % 2 == 0) && (N > valorM)) {
            newSize = N / 2;

            double[][] varA1 = new double[newSize][];
            double[][] varA2 = new double[newSize][];
            double[][] varB1 = new double[newSize][];
            double[][] varB2 = new double[newSize][];

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
            double[][] aux8 = new double[newSize][];
            double[][] aux9 = new double[newSize][];

            for (i = 0; i < newSize; i++) {
                varA1[i] = new double[newSize];
                varA2[i] = new double[newSize];
                varB1[i] = new double[newSize];
                varB2[i] = new double[newSize];

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
                aux8[i] = new double[newSize];
                aux9[i] = new double[newSize];
            }
            // Ciclos para llenar nuevas matrices
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
                    varA22[i][j] = matrizA[  newSize + i][newSize + j];
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

            minus(varA11, varA21, varA1, newSize, newSize);
            minus(varA22, varA1, varA2, newSize, newSize);
            minus(varB22, varB12, varB1, newSize, newSize);
            plus(varB1, varB11, varB2, newSize, newSize);

            strassenWinogradStep(varA11, varB11, aux1, newSize, valorM);
            strassenWinogradStep(varA12, varB21, aux2, newSize, valorM);
            strassenWinogradStep(varA2, varB2, aux3, newSize, valorM);
            plus(varA21, varA22, helper1, newSize, newSize);
            minus(varB12, varB11, helper2, newSize, newSize);
            strassenWinogradStep(helper1, helper2, aux4, newSize, valorM);
            strassenWinogradStep(varA1, varB1, aux5, newSize, valorM);
            minus(varA12, varA2, helper1, newSize, newSize);
            strassenWinogradStep(helper1, varB22, aux6, newSize, valorM);
            minus(varB21, varB2, helper1, newSize, newSize);
            strassenWinogradStep(varA22, helper1, aux7, newSize, valorM);
            plus(aux1, aux3, aux8, newSize, newSize);
            plus(aux8, aux4, aux9, newSize, newSize);

            //
            plus(aux1, aux2, resultadoPart11, newSize, newSize);
            plus(aux9, aux6, resultadoPart12, newSize, newSize);
            plus(aux8, aux5, helper1, newSize, newSize);
            plus(helper1, aux7, resultadoPart21, newSize, newSize);
            plus(aux9, aux5, resultadoPart22, newSize, newSize);

            // ciclos para alamcenar los resultados en la matrizRes[][]
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

            for (i = 0; i < newSize; i++) {
                for (j = 0; j < newSize; j++) {
                    matrizRes[newSize + i][j] = resultadoPart21[i][j];
                }
            }

            for (i = 0; i < newSize; i++) {
                for (j = 0; j < newSize; j++) {
                    matrizRes[newSize + i][newSize + j] = resultadoPart22[i][j];
                }
            }

            // Liberar variables, se igualan a null
            varA1 = null;
            varA2 = null;
            varB1 = null;
            varB1 = null;


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
            algoritmoNaivStandard(matrizA, matrizB, matrizRes, N, N, N);
        }
    }

    public static void algoritmoNaivStandard(double[][] matrizA,double[][] matrizB,double[][] matrizRes,int N, int P, int M)
    {
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

    private static void plus(double[][] matrizA, double[][] matrizB, double[][] matrizRes, int N, int M) {
        for (int i = 0; i < N;i++) {
            for (int j = 0; j < M; j++) {
                matrizRes[i][j] = matrizA[i][j] + matrizB[i][j];
            }
        }
    }

    private static void minus(double[][] matrizA, double[][] matrizB, double[][] matrizRes, int N, int M) {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                matrizRes[i][j] = matrizA[i][j] - matrizB[i][j];
            }
        }
    }
    public static int max (int N, int P){
        if (N < P){
            return P;
        } else {
            return N;
        }

    }

    public static void multiply(double[][] matrizA, double[][] matrizB) {
        int N = matrizA.length;
        int P = matrizB.length;
        int M = matrizB[0].length;
        double[][] matrizRes = new double[N][M];
        algStrassenWinograd(matrizA, matrizB, matrizRes, N, P, M);
    }
}