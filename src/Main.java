import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

import java.util.List;


public class Main extends Application {

    private Rectangle rectangles[][];

    @Override
    public void start(Stage primaryStage) throws Exception{

        //grid setup
        primaryStage.setTitle("Game of life");
        GridPane gridpane = new GridPane();
        gridpane.setMinSize(400, 200);
        gridpane.setPadding(new Insets(10, 10, 10, 10));
        gridpane.setVgap(5);
        gridpane.setHgap(5);
        gridpane.setAlignment(Pos.CENTER);
        Scene scene = new Scene(gridpane);

        //read from the file
        Boolean mat[][] = readFromCSV();

        //create the grid
        Grid g = new Grid(mat.length, mat[0].length);
        g.initializeWithMatrix(mat);
        rectangles = new Rectangle[mat.length][mat[0].length];

        //initialize rectangles
        for(int i = 0; i < mat.length; i++){
            for(int j = 0; j < mat[0].length; j++){
                Rectangle r = new Rectangle(30, 30);
                if(mat[i][j])
                    r.setFill(Color.GREEN);
                else
                    r.setFill(Color.RED);
                rectangles[i][j] = r;

                gridpane.add(r, i, j);

            }
        }

        //call the updating function for the rectangles
        Timeline timeline = new Timeline(
                new KeyFrame(
                        Duration.seconds(1),
                        event -> updateRectangles(g)
                )
        );



        //set ticks
        timeline.setCycleCount(100);
        timeline.play();

        //run the thing
        scene.setRoot(gridpane);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    private void updateRectangles(Grid g)
    {
        Boolean mat[][] = g.run();  //apeleaza metoda din Grid care se duce la urmatoarea generatie
        for(int i = 0; i < mat.length; i++){
            for (int j = 0; j < mat[0].length; j++){
                if(mat[i][j])
                    rectangles[i][j].setFill(Color.GREEN);
                else
                    rectangles[i][j].setFill(Color.RED);
            }
        }

    }

    static Boolean[][] readFromCSV()
    {
        final String DELIMITER;
        final String ALIVE;
        final String DEAD;
        List<List<Boolean>> records = new ArrayList<>();
        int max = 0;    //pastreaza maximul numarului de coloane

        try(BufferedReader br = new BufferedReader(new FileReader("initializare grid incomplet.csv")))
        {
            DELIMITER = String.valueOf((char) br.read()); //primul caracter este delimitatorul

            String line = br.readLine();
            String[] values = line.split(DELIMITER, -1); //split dupa delimiter, pentru ceva gen ;; baga null

            ALIVE = values[0];   //al doilea e valoarea pentru alive
            DEAD = values[1];    //si cea pentru dead

            //ia caracterele de pe fiecare linie
            while ((line = br.readLine()) != null){
                List<Boolean> a = new ArrayList<>();

                values = line.split(DELIMITER);
                for(String v : values){
                    if(v.equals(ALIVE))
                        a.add(true);
                    else
                        a.add(false);
                }
                if(values.length > max)
                    max = values.length;

                records.add(a);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

        //converteste ce a citit in matrice de booleene
        Boolean mat[][] = new Boolean[records.size()][max];
        for (int i = 0; i < records.size(); i++){
            int lineSize = records.get(i).size();
            for (int j = 0; j < lineSize; j++) {
                mat[i][j] = records.get(i).get(j);

            }

            for (int j = lineSize; j < max; j++){
                mat[i][j] = false;
            }
        }

        return mat;
    }

    public static void main(String[] args)
    {

        launch(args);

    }
}
