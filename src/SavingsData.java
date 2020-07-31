import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SavingsData extends Application {


    private double savingsV, interestR;
    private LineChart<Number, Number> lineChart;
    private XYChart.Series savingsData, interestData;

    public static void main(String[] args) {
        launch(SavingsData.class);
    }

    @Override
    public void start(Stage stage) throws Exception {
        BorderPane layout = new BorderPane();

        VBox top = new VBox();

        BorderPane savings = new BorderPane();
        BorderPane interest = new BorderPane();

        savings.setLeft(new Label("Monthly savings"));

        Slider saved = new Slider(25, 250, 25);
        saved.setShowTickMarks(true);
        saved.setShowTickLabels(true);
        saved.setShowTickLabels(true);
        saved.setShowTickMarks(true);
        saved.setMajorTickUnit(25);
        saved.setMinorTickCount(3);
        saved.setSnapToTicks(true);

        Label savedData = new Label(Double.toString(saved.getValue()));

        savings.setCenter(saved);
        savings.setRight(savedData);

        savings.setPadding(new Insets(10, 20, 10, 20));

        interest.setLeft(new Label("Yearly interest rate"));

        interest.setPadding(new Insets(10, 20, 10, 20));

        Slider interestRate = new Slider(0, 10, 0);
        interestRate.setShowTickMarks(true);
        interestRate.setShowTickLabels(true);
        interestRate.setMajorTickUnit(2.0);
        interestRate.setMinorTickCount(1);
        interestRate.setSnapToTicks(true);

        Label interestRateData = new Label(Double.toString(interestRate.getValue()));

        interest.setCenter(interestRate);
        interest.setRight(interestRateData);

        top.getChildren().add(savings);
        top.getChildren().add(interest);

        layout.setTop(top);

        this.savingsV = saved.getValue();
        this.interestR = interestRate.getValue();

        NumberAxis xAxis = new NumberAxis(0, 30, 1);
        NumberAxis yAxis = new NumberAxis();

        Double[] savingsOnChart = new Double[31];
        Double[] interestOnChart = new Double[31];

        savingsData = new XYChart.Series();
        savingsData.setName("Savings");
        interestData = new XYChart.Series();
        interestData.setName("Savings With interest");

        savingsOnChart[0] = 0.0;
        interestOnChart[0] = 0.0;

        savingsData.getData().add(new XYChart.Data(0, savingsOnChart[0]));
        interestData.getData().add(new XYChart.Data(0, interestOnChart[0]));

        System.out.println("Savings: " + this.savingsV);
        System.out.println("Interest: " + this.interestR);

        this.savingsV = saved.getValue();
        this.interestR = interestRate.getValue();

        for (int i = 1; i < 31; i++) {

            savingsOnChart[i] = this.savingsV * 12 * i;
            savingsData.getData().add(new XYChart.Data(i, savingsOnChart[i]));
            interestOnChart[i] = (interestOnChart[i - 1] + savingsV * 12) * (1 + (this.interestR / 100.0));
            interestData.getData().add(new XYChart.Data(i, interestOnChart[i]));
        }

        lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.getData().add(savingsData);
        lineChart.getData().add(interestData);

        layout.setCenter(lineChart);

        saved.valueProperty().addListener((event, old, newV) -> {
            String newValue;
            newValue = String.format("%.2f", newV);
            savedData.setText(newValue);
            this.savingsV = (double) newV;
            this.updateChart(layout);
        });

        interestRate.valueProperty().addListener((event, old, newV) -> {
            String newValue;
            newValue = String.format("%.2f", newV);
            interestRateData.setText(newValue);
            this.interestR = (double) newV;
            this.updateChart(layout);
        });

        Scene scene = new Scene(layout);

        stage.setScene(scene);
        stage.show();
    }

    private void updateChart(BorderPane layout) {

        Double[] savingsOnChart = new Double[31];
        Double[] interestOnChart = new Double[31];

        savingsData = new XYChart.Series();
        savingsData.setName("Savings");
        interestData = new XYChart.Series();
        interestData.setName("Savings With interest");

        savingsOnChart[0] = 0.0;
        interestOnChart[0] = 0.0;

        this.savingsData.getData().clear();
        this.savingsData.getData().clear();

        savingsData.getData().add(new XYChart.Data(0, savingsOnChart[0]));
        interestData.getData().add(new XYChart.Data(0, interestOnChart[0]));

        System.out.println("Savings: " + this.savingsV);
        System.out.println("Interest: " + this.interestR);

        for (int i = 1; i < 31; i++) {
            savingsOnChart[i] = this.savingsV * 12 * i;
            savingsData.getData().add(new XYChart.Data(i, savingsOnChart[i]));
            interestOnChart[i] = (interestOnChart[i - 1] + savingsV * 12) * (1 + (this.interestR / 100.0));
            interestData.getData().add(new XYChart.Data(i, interestOnChart[i]));
        }

        this.lineChart.getData().clear();

        this.lineChart.getData().add(interestData);
        this.lineChart.getData().add(savingsData);

        layout.setCenter(this.lineChart);

    }
}
