package tools;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class GraficaPython extends JFrame {
    private JComboBox<String> matrixSizeComboBox;
    private JPanel chartPanel;

    private final List<String> algoritmos = Arrays.asList(
            "NaivOnArray", "NaivLoopUnrollingTwo", "NaivLoopUnrollingFour",
            "WinogradOriginal", "WinogradScaled", "StrassenNaiv",
            "StrassenWinograd", "III_3_Sequential_Block", "III_4_Parallel_Block",
            "III_5_Enhanced_Parallel_Block", "IV_3_Sequential_Block",
            "IV_4_Parallel_Block", "IV_5_Enhanced_Parallel_Block",
            "V_3_Sequential_Block", "V_4_Parallel_Block"
    );

    public GraficaPython() {
        setTitle("Comparación de Tiempos de Ejecución");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        matrixSizeComboBox = new JComboBox<>(new String[]{
                "16x16", "32x32", "64x64", "128x128",
                "256x256", "512x512", "1024x1024", "2048x2048"
        });
        matrixSizeComboBox.addActionListener(e -> updateChart());

        chartPanel = new JPanel();
        chartPanel.setLayout(new BorderLayout());

        add(matrixSizeComboBox, BorderLayout.NORTH);
        add(chartPanel, BorderLayout.CENTER);

        updateChart();
    }

    private void updateChart() {
        String selectedSize = (String) matrixSizeComboBox.getSelectedItem();
        DefaultCategoryDataset dataset = createDataset(selectedSize);
        JFreeChart barChart = ChartFactory.createBarChart(
                "Comparación de Tiempos de Ejecución para Matriz " + selectedSize,
                "Algoritmo",
                "Tiempo (ms)",
                dataset,
                PlotOrientation.VERTICAL,
                true, true, false);

        CategoryPlot plot = (CategoryPlot) barChart.getPlot();
        BarRenderer renderer = (BarRenderer) plot.getRenderer();

        CategoryAxis domainAxis = plot.getDomainAxis();
        domainAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_90);

        // Cambiar la fuente del eje x
        domainAxis.setTickLabelFont(new Font("SansSerif", Font.PLAIN, 24)); // Cambia el tamaño según sea necesario

        // Cambiar la fuente del eje y
        plot.getRangeAxis().setTickLabelFont(new Font("SansSerif", Font.PLAIN, 18)); // Cambia el tamaño según sea necesario

        // Cambiar el color de las barras a azul
        renderer.setSeriesPaint(0, Color.RED);

        // Ajustar el ancho máximo de las barras
        renderer.setMaximumBarWidth(1); // Puedes ajustar este valor según sea necesario
        renderer.setItemMargin(0); // Reducir el margen entre las barras dentro de una categoría

        // Ajustar la distancia entre las barras
        plot.getDomainAxis().setCategoryMargin(0.2); // Reducir el margen entre las categorías

        ChartPanel chartPanel = new ChartPanel(barChart);
        this.chartPanel.removeAll();
        this.chartPanel.add(chartPanel, BorderLayout.CENTER);
        this.chartPanel.validate();
    }


    private DefaultCategoryDataset createDataset(String matrixSize) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        String javaFilePath = "src/main/java/TimeResultMatriz_Python/matriz" + matrixSize + ".txt";

        for (int i = 0; i < algoritmos.size(); i++) {
            long tiempoJavaNanosegundos = leerTiempoDelArchivo(javaFilePath, i);
            double tiempoJavaMilisegundos = tiempoJavaNanosegundos / 1e6;
            // Agregar el tiempo con el número del algoritmo para Java
            dataset.addValue(tiempoJavaMilisegundos, "Python", algoritmos.get(i));
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
            GraficaJava frame = new GraficaJava();
            frame.setVisible(true);
        });
    }
}