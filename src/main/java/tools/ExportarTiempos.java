package tools;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
public class ExportarTiempos {

    private static List<String> algoritmo = new ArrayList<>(Arrays.asList("NaivOnArray", "NaivLoopUnrollingTwo", "NaivLoopUnrollingFour", "StrassenNaiv", "WinogradOriginal", "WinogradScaled", "StrassenWinograd", "III_3_Sequential_Block", "IV_3_Sequential_Block", "V_3_SequentialBlock", "III_4_Parallel_Block", "IV_4_Parallel_Block", "V_4_Parallel_Block", "III_5_Enhanced_Parallel_Block", "IV_5_Enhanced_Parallel_Block"));

    private static List<String> matrices = new ArrayList<>(Arrays.asList("matriz256x256", "matriz512x512", "matriz1024x1024", "matriz2048x2048","matriz3072x3072", "matriz4096x4096", "matriz6144x6144", "matriz8192x8192"));

    public static List<String> getAlgoritmo() {
        return algoritmo;
    }

    public static List<String> getMatrices() {
        return matrices;
    }

    public static void exportarTiempos(List<Long> lista, int item) throws IOException {
        String rutaArchivo = "src/main/java/TimeResult/" + algoritmo.get(item - 1) + ".txt"; // Especifica la ruta del archivo con el nombre proporcionado
        FileWriter escritor = new FileWriter(rutaArchivo, true); // Modo append para agregar a lo existente
        BufferedWriter bufferEscritor = new BufferedWriter(escritor);

        for (Long numero : lista) {
            bufferEscritor.write(numero.toString());
            bufferEscritor.newLine();
        }

        bufferEscritor.close();
    }

    public static void exportarTiemposMatriz(List<Long> lista, int item) throws IOException {
        String rutaArchivo = "src/main/java/TimeResultMatriz/" + matrices.get(item - 1) + ".txt"; // Especifica la ruta del archivo con el nombre proporcionado
        FileWriter escritor = new FileWriter(rutaArchivo, true); // Modo append para agregar a lo existente
        BufferedWriter bufferEscritor = new BufferedWriter(escritor);
        for (Long numero : lista) {
            bufferEscritor.write(numero.toString());
            bufferEscritor.newLine();
        }
        bufferEscritor.close();
    }
}


