package tools;

import java.util.ArrayList;
import java.util.List;

public class TiempoEjecucion {
    public static List<Long> matricesTiempoAlgoritmos = new ArrayList<>();

    public static void timeAlgortithm(long inicio, long fin){
        long time = (fin - inicio);
        matricesTiempoAlgoritmos.add(time);
        System.out.println(" Time: "+time+"ns");
    }
}