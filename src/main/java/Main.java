import algorithm.*;
import tools.ExportarTiempos;
import tools.GraphFrame;
import tools.LeerArchivoTxt;
import tools.TiempoEjecucion;
import javax.swing.*;
import java.io.*;
import java.util.List;
import java.util.Random;

public class Main {

    static long inicio;
    static long fin;
    static int[][] Matriz1;
    static int[][] Matriz2;

    public static void main(String[] args) throws Exception {
        //Se debe tener creado las carpetas 1matriz y 2matriz en el src/main/java/
        crearMatrices(); //GENERA LAS MATRICES (TARDA VARIOS MINUTOS POR SU MAGNITUD)

        /*          1.NaivOnArray
                    2.NaivLoopUnrollingTwo
                    3.NaivLoopUnrollingFour
                    4.StrassenNaiv
                    5.WinogradOriginal
                    6.WinogradScaled
                    7.StrassenWinograd
                    8.III_3_Sequential_Block
                    9.IV_3_Sequential_Block
                    10.V_3_SequentialBlock
                    11.III_4_Parallel_Block
                    12.IV_4_Parallel_Block
                    13.V_4_Parallel_Block
                    14.III_5_Enhanced_Parallel_Block
                    15.IV_5_Enhanced_Parallel_Block

                    Caso 1: 256
                    Caso 2: 512
                    Caso 3: 1024
                    Caso 4: 2048
                    Caso 5: 3072
                    Caso 6: 4096
                    Caso 7: 6144
                    Caso 8: 8192

                     */
        // Llamar al método vaciarTiempos() para limpiar los archivos de tiempos antes de empezar
        //vaciarTiempos();

        /*
        for(int algoritmo=1; algoritmo<=15; algoritmo++) {
            for (int caso = 1; caso <= 8; caso++) {

                System.out.println("Ejecutando algoritmo "+algoritmo+" caso "+caso+"...");
                matrices(caso);
                algorithm(algoritmo);
                System.out.println("Termino algoritmo: " + algoritmo + " caso " + caso + "\n");
                try {

                    ExportarTiempos.exportarTiemposMatriz(TiempoEjecucion.matricesTiempoAlgoritmos, caso);
                    ExportarTiempos.exportarTiempos(TiempoEjecucion.matricesTiempoAlgoritmos, algoritmo);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                TiempoEjecucion.matricesTiempoAlgoritmos.clear();

            }
        }

         */
        mostrarGrafica();
    }

    private static void mostrarGrafica() {
        SwingUtilities.invokeLater(() -> {
            GraphFrame frame = new GraphFrame();
            frame.setVisible(true);
        });
    }

    // Método para limpiar los archivos de tiempos
    public static void vaciarTiempos() {
        String ruta = "src/main/java/";
        try {
            for (String algoritmo : ExportarTiempos.getAlgoritmo()) {
                String rutaArchivo = ruta+"TimeResult/" + algoritmo + ".txt";
                new PrintWriter(rutaArchivo).close();
            }
            for (String matriz : ExportarTiempos.getMatrices()) {
                String rutaArchivo = ruta+"TimeResultMatriz/" + matriz + ".txt";
                new PrintWriter(rutaArchivo).close();
            }
            System.out.println("Archivos de tiempos vaciados correctamente.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void crearMatrices() {
        String ruta = "src/main/java/";
        int n = 16; // Número de columnas
        int m = 16; // Número de filas

        // Generar la matriz con números aleatorios de 6 dígitos
        for (int i = 0; i < 8; i++) {
            int[][] matriz = generarMatriz(n, m);
            // Guardar la matriz en un archivo
            guardarMatrizEnArchivo(matriz, ruta+"1matriz/1matriz_" + n + "x" + m + ".txt");
            guardarMatrizEnArchivo(matriz, ruta+"2matriz/2matriz_" + n + "x" + m + ".txt");

            n=n*2;
            m=m*2;
        }

        System.out.println("Matrices generadas \n");
    }



    public static int[][] generarMatriz(int n, int m) {
        int[][] matriz = new int[m][n];
        Random rand = new Random();

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                matriz[i][j] = rand.nextInt(900000) + 100000; // Generar número aleatorio de 6 dígitos
            }
        }
        return matriz;
    }

    public static void guardarMatrizEnArchivo(int[][] matriz, String nombreArchivo) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nombreArchivo))) {
            for (int i = 0; i < matriz.length; i++) {
                for (int j = 0; j < matriz[i].length; j++) {
                    writer.write(String.format("%d ", matriz[i][j])); // Escribir cada elemento de la matriz
                }
                writer.newLine(); // Nueva línea después de cada fila
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void algorithm(int option) {
        final double[][] matrizDouble1 = convertToIntToDouble(Matriz1);
        final double[][] matrizDouble2 = convertToIntToDouble(Matriz2);
        try {
            switch (option) {
                case 1:
                    // NaivOnArray
                    executeAlgorithm(() -> NaivOnArray.naivOnArray(Matriz1, Matriz2), option);
                    break;
                case 2:
                    // NaivLoopUnrollingTwo
                    executeAlgorithm(() -> NaivLoopUnrollingTwo.naiveLoopUnrollingTwo(Matriz1, Matriz2), option);
                    break;
                case 3:
                    // NaivLoopUnrollingFour
                    executeAlgorithm(() -> NaivLoopUnrollingFour.naivLoopUnrollingFour(Matriz1, Matriz2), option);
                    break;
                case 4:
                    // StrassenNaiv
                    executeAlgorithm(() -> StrassenNaiv.multiply(matrizDouble1, matrizDouble2), option);
                    break;
                case 5:
                    // WinogradOriginal
                    executeAlgorithm(() -> WinogradOriginal.multiply(matrizDouble1, matrizDouble2), option);
                    break;
                case 6:
                    // WinogradScaled
                    executeAlgorithm(() -> WinogradScaled.multiply(matrizDouble1, matrizDouble2), option);
                    break;
                case 7:
                    // StrassenWinograd
                    executeAlgorithm(() -> StrassenWinograd.multiply(matrizDouble1, matrizDouble2), option);
                    break;
                case 8:
                    // III_3_Sequential_Block
                    executeAlgorithm(() -> III_3_Sequential_Block.multiply(matrizDouble1, matrizDouble2), option);
                    break;
                case 9:
                    // IV_3_Sequential_Block
                    executeAlgorithm(() -> IV_3_Sequential_Block.multiply(matrizDouble1, matrizDouble2), option);
                    break;
                case 10:
                    // V_3_SequentialBlock
                    executeAlgorithm(() -> V_3_Sequential_Block.multiply(matrizDouble1, matrizDouble2), option);
                    break;
                case 11:
                    // III_4_Parallel_Block
                    executeAlgorithm(() -> III_4_Parallel_Block.multiply(matrizDouble1, matrizDouble2), option);
                    break;
                case 12:
                    // IV_4_Parallel_Block
                    executeAlgorithm(() -> IV_4_Parallel_Block.multiply(matrizDouble1, matrizDouble2), option);
                    break;
                case 13:
                    // V_4_Parallel_Block
                    executeAlgorithm(() -> V_4_Parallel_Block.multiply(matrizDouble1, matrizDouble2), option);
                    break;
                case 14:
                    // III_5_Enhanced_Parallel_Block
                    executeAlgorithm(() -> III_5_Enhanced_Parallel_Block.multiply(matrizDouble1, matrizDouble2), option);
                    break;
                case 15:
                    // IV_5_Enhanced_Parallel_Block
                    executeAlgorithm(() -> IV_5_Enhanced_Parallel_Block.multiply(matrizDouble1, matrizDouble2), option);
                    break;
                default:
                    System.out.println("Opción incorrecta");
            }
        } finally {
            // Liberar recursos
            Matriz1 = null;
            Matriz2 = null;
            System.gc(); // Sugerir recolección de basura
        }
    }

    private static void executeAlgorithm(Runnable algorithm, int option) {
        inicio = System.nanoTime();
        algorithm.run();
        fin = System.nanoTime();
        TiempoEjecucion.timeAlgortithm(inicio, fin);
        // Guardar tiempo inmediatamente después de la ejecución del algoritmo
    }

    public static double[][] convertToIntToDouble(int[][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length;
        double[][] result = new double[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result[i][j] = (double) matrix[i][j];
            }
        }
        return result;
    }

    public static void matrices(int caso) {
        String ruta = "src/main/java/";
        String ruta1 = ruta+"1matriz/";
        String ruta2 = ruta+"2matriz/";
        int tam=0;
        switch (caso) {

            case 1:
                tam=16;
                break;
            case 2:
                tam=32;
                break;
            case 3:
                tam=64;
                break;
            case 4:
                tam=128;
                break;
            case 5:
                tam=256;
                break;
            case 6:
                tam=512;
                break;
            case 7:
                tam=1024;
                break;
            case 8:
                tam=2048;
                break;
            default:

                break;
        }
        Matriz1 = LeerArchivoTxt.leerArchivo(ruta1+"1matriz_"+tam+"x"+tam+".txt", tam);
        Matriz2 = LeerArchivoTxt.leerArchivo(ruta2+"2matriz_"+tam+"x"+tam+".txt", tam);
    }
}