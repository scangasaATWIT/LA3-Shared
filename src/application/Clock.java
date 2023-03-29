package application;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeLineJoin;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Clock extends Application {

  private static final int WIDTH = 500;
  private static final int HEIGHT = 500;
  private static final int CENTER_X = WIDTH / 2;
  private static final int CENTER_Y = (HEIGHT - 50) / 2;
  private static final int RADIUS = 150;

  private Canvas canvas;
  private GraphicsContext gc;
  private Label timeLabel;

  @Override
  public void start(Stage primaryStage) throws Exception {
    BorderPane root = new BorderPane();
    root.setCenter(createClock());
    root.setBottom(createTimeLabel());

    Scene scene = new Scene(root, WIDTH, HEIGHT + 50);
    primaryStage.setScene(scene);
    primaryStage.setTitle("JavaFX Clock");
    primaryStage.show();

    setTime(22, 44, 37);
  }

  private Group createClock() {
    Group clock = new Group();
    canvas = new Canvas(WIDTH, HEIGHT - 50);
    gc = canvas.getGraphicsContext2D();
    drawClockFace();
    drawTicks();
    drawNumbers();
    drawHands();

    clock.getChildren().add(canvas);
    return clock;
  }

  private HBox createTimeLabel() {
    HBox hbox = new HBox();
    hbox.setAlignment(Pos.CENTER);
    timeLabel = new Label();
    timeLabel.setFont(Font.font("Comic Sans MS", 24));
    timeLabel.setTextFill(Color.BLACK);
    hbox.getChildren().add(timeLabel);
    BorderPane.setMargin(hbox, new Insets(0, 0, 20, 0));
    hbox.setPadding(new Insets(20, 0, 0, 0));
    return hbox;
  }

  private void drawClockFace() {
    gc.setFill(Color.WHITE);
    gc.fillArc(CENTER_X - RADIUS, CENTER_Y - RADIUS, RADIUS * 2, RADIUS * 2, 0, 360, ArcType.ROUND);
    gc.setStroke(Color.BLACK);
    gc.setLineWidth(2);
    gc.strokeArc(CENTER_X - RADIUS, CENTER_Y - RADIUS, RADIUS * 2, RADIUS * 2, 0, 360, ArcType.OPEN);
  }

  private void drawTicks() {
    for (int i = 1; i <= 60; i++) {
      double angle = i * Math.PI / 30;
      int tickLength = i % 5 == 0 ? 10 : 5;
      tickLength -= 1;
      int x1 = (int)(CENTER_X + (RADIUS - tickLength) * Math.sin(angle));
      int y1 = (int)(CENTER_Y - (RADIUS - tickLength) * Math.cos(angle));
      int x2 = (int)(CENTER_X + RADIUS * Math.sin(angle));
      int y2 = (int)(CENTER_Y - RADIUS * Math.cos(angle));
      gc.setLineWidth(2);
      gc.strokeLine(x1, y1, x2, y2);
    }
  }

  private void drawNumbers() {
    for (int i = 1; i <= 12; i++) {
      double angle = i * Math.PI / 6;
      int x = (int)(CENTER_X + (RADIUS - 30) * Math.sin(angle));
      int y = (int)(CENTER_Y - (RADIUS - 30) * Math.cos(angle)) + 10;
      gc.setFill(Color.BLACK);
      gc.setFont(Font.font("Comic Sans MS", 20));
      gc.fillText(Integer.toString(i), x, y);
    }
  }

  private void drawHands() {
    int hour = 22;
    int minute = 44;
    int second = 37;

    double hourAngle = (hour % 12 + minute / 60.0) * 30 * Math.PI / 180;
    double minuteAngle = (minute + second / 60.0) * 6 * Math.PI / 180;
    double secondAngle = second * 6 * Math.PI / 180;

    drawHand(hourAngle, RADIUS * 0.4, 3, Color.BLACK);
    drawHand(minuteAngle, RADIUS * 0.6, 3, Color.BLACK);
    drawHand(secondAngle, RADIUS * 0.8, 3, Color.BLACK);
  }

  private void drawHand(double angle, double length, int width, Color color) {
    int x = (int)(CENTER_X + length * Math.sin(angle));
    int y = (int)(CENTER_Y - length * Math.cos(angle));
    gc.setLineWidth(width);
    gc.setStroke(color);
    gc.setLineCap(StrokeLineCap.ROUND);
    gc.setLineJoin(StrokeLineJoin.ROUND);

    gc.beginPath();
    gc.moveTo(CENTER_X, CENTER_Y);
    gc.lineTo(x, y);
    gc.stroke();
  }

  private void drawHands(int hour, int minute, int second) {
    double hourAngle = (hour % 12 + minute / 60.0) * 30 * Math.PI / 180;
    double minuteAngle = (minute + second / 60.0) * 6 * Math.PI / 180;
    double secondAngle = second * 6 * Math.PI / 180;

    drawHand(hourAngle, RADIUS * 0.4, 3, Color.BLACK);
    drawHand(minuteAngle, RADIUS * 0.6, 3, Color.BLACK);
    drawHand(secondAngle, RADIUS * 0.8, 3, Color.BLACK);
  }

  private void updateLabel(int hour, int minute, int second) {
    String time = String.format("%02d:%02d:%02d", hour, minute, second);
    timeLabel.setText(time);
  }

  private void setTime(int hour, int minute, int second) {
    gc.clearRect(0, 0, WIDTH, HEIGHT - 50);
    drawClockFace();
    drawTicks();
    drawNumbers();
    drawHands(hour, minute, second);
    updateLabel(hour, minute, second);
  }

  public static void main(String[] args) {
    launch(args);
  }
}