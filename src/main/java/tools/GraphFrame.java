package tools;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class GraphFrame extends JFrame {
    private JComboBox<String> matrixSizeComboBox;
    private JPanel chartPanel;

    public GraphFrame() {
        setTitle("Algoritmo de Comparación de Tiempos");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        matrixSizeComboBox = new JComboBox<>(new String[]{
                "256x256", "512x512", "1024x1024", "2048x2048",
                "4096x4096", "6144x6144", "8192x8192", "10240x10240"
        });
        matrixSizeComboBox.addActionListener(e -> updateChart());

        chartPanel = new JPanel();
        chartPanel.setLayout(new BorderLayout());

        add(matrixSizeComboBox, BorderLayout.NORTH);
        add(chartPanel, BorderLayout.CENTER);

        updateChart(); // Initial chart
    }

    private void updateChart() {
        String selectedSize = (String) matrixSizeComboBox.getSelectedItem();
        DefaultCategoryDataset dataset = createDataset(selectedSize);
        JFreeChart barChart = ChartFactory.createBarChart(
                "Tiempos de Ejecución para Matriz " + selectedSize,
                "Algoritmo",
                "Tiempo (s)", // Cambiado a segundos
                dataset,
                PlotOrientation.VERTICAL,
                true, true, false);

        ChartPanel chartPanel = new ChartPanel(barChart);
        this.chartPanel.removeAll();
        this.chartPanel.add(chartPanel, BorderLayout.CENTER);
        this.chartPanel.validate();
    }

    private DefaultCategoryDataset createDataset(String matrixSize) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        List<String> algoritmos = ExportarTiempos.getAlgoritmo();
        String filePath = "src/main/java/TimeResultMatriz/matriz" + matrixSize + ".txt";

        for (int i = 0; i < algoritmos.size(); i++) {
            long tiempoNanosegundos = leerTiempoDelArchivo(filePath, i);
            double tiempoSegundos = tiempoNanosegundos / 1e9; // Convertir a segundos
            dataset.addValue(tiempoSegundos, algoritmos.get(i), algoritmos.get(i)); // Usar el nombre del algoritmo
        }

        return dataset;
    }

    private long leerTiempoDelArchivo(String filePath, int algorithmIndex) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            int lineCount = 0;
            while ((line = br.readLine()) != null) {
                if (lineCount == algorithmIndex) {
                    return Long.parseLong(line);
                }
                lineCount++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GraphFrame frame = new GraphFrame();
            frame.setVisible(true);
        });
    }
}
