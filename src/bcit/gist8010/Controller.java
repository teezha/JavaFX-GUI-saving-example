package bcit.gist8010;
//==============================================================================
// File         : some_file.java
//
// Current Author: First_Name Last_Name
//
// Previous Author: None
//
// Contact Info: somebody@somewhere.com
//
// Purpose :
//
// Dependencies: None
//
// Modification Log :
//    --> Created MMM-DD-YYYY (fl)
//    --> Updated MMM-DD-YYYY (fl)
//
// =============================================================================

import com.esri.core.map.Graphic;
import com.esri.core.symbol.SimpleFillSymbol;
import com.esri.core.symbol.SimpleLineSymbol;
import com.esri.core.symbol.SimpleMarkerSymbol;
import com.esri.map.ArcGISTiledMapServiceLayer;
import com.esri.map.GraphicsLayer;
import com.esri.map.JMap;
import com.esri.toolkit.overlays.DrawingCompleteEvent;
import com.esri.toolkit.overlays.DrawingCompleteListener;
import com.esri.toolkit.overlays.DrawingOverlay;
import javafx.embed.swing.SwingNode;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;

import java.awt.*;
import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable, DrawingCompleteListener {

    //===========================================================
// The following block does sets FXML hooks
//===========================================================

    @FXML
    Pane pane;

    @FXML
    SwingNode swingNode;


    // ===========================================================
    // Main map for the application
    // Required imports:
    //  import com.esri.map.JMap;
    // ===========================================================
    JMap map;


    @FXML
    Button btnPolygon;

    @FXML
    Button btnLine;

    @FXML
    Button btnPoint;

    @FXML
    Button btnClear;


    // ===========================================================
    // Common layers and overlays that will be added to the map
    // Required imports:
    // import com.esri.map.ArcGISTiledMapServiceLayer;
    // import com.esri.map.GraphicsLayer;
    // import com.esri.toolkit.overlays.DrawingOverlay;
    // ===========================================================
    GraphicsLayer graphicLayer;
    DrawingOverlay drawingOverlay;
    ArcGISTiledMapServiceLayer baseLayer;
    static final String baseURL = "http://maps.gov.bc.ca/arcserver/rest/services/Province/albers_cache/MapServer";


    // ===========================================================
    // Basic Symbology for points line and polygons
    // import com.esri.core.symbol.MarkerSymbol;
    // import com.esri.core.symbol.SimpleFillSymbol;
    // import com.esri.core.symbol.SimpleLineSymbol;
    // import com.esri.core.symbol.SimpleMarkerSymbol;
    // ===========================================================
    final SimpleLineSymbol symPolyLine = new SimpleLineSymbol(
            new Color(0, 0, 150), 3);
    final SimpleLineSymbol symPolyLineFree = new SimpleLineSymbol(
            new Color(200, 0, 0), 3);
    final SimpleLineSymbol symLine = new SimpleLineSymbol(new Color(0,
            0, 150), 3);
    final SimpleFillSymbol symRectangle = new SimpleFillSymbol(
            new Color(200, 0, 0, 60), new SimpleLineSymbol(
            new Color(200, 0, 0), 3));
    final SimpleLineSymbol symDottedLine = new SimpleLineSymbol(Color.BLACK, 2);
    final SimpleFillSymbol symPolygon = new SimpleFillSymbol(new Color(
            0, 0, 0, 80), symDottedLine);
    final SimpleMarkerSymbol pointSym = new SimpleMarkerSymbol(
            Color.RED, 15, SimpleMarkerSymbol.Style.DIAMOND);


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        map = new JMap();
        swingNode.setContent(map);


        // ===============================================================
        // Make a SWING JMap and a SwingNode then add the map to the node
        // ===============================================================
        baseLayer = new ArcGISTiledMapServiceLayer(baseURL);
        map.getLayers().add(baseLayer);


        // ===============================================================
        // Make a SWING JMap and a SwingNode then add the map to the node
        // ===============================================================
        graphicLayer = new GraphicsLayer();
        map.getLayers().add(graphicLayer);


        // ===============================================================
        // Make a SWING JMap and a SwingNode then add the map to the node
        // ===============================================================
        drawingOverlay = new DrawingOverlay();
        map.addMapOverlay(drawingOverlay);
        drawingOverlay.addDrawingCompleteListener(this);


    }

    //===========================================================
// The following block does closing
//===========================================================

    public void close() {

        System.exit(0);
    }
//===========================================================
// The following block does keep you drawing
//===========================================================

    @Override
    public void drawingCompleted(DrawingCompleteEvent event) {

        graphicLayer.addGraphic((Graphic) drawingOverlay.getAndClearFeature());
//===========================================================
// The following blocks does draws the lines points and polygons
//===========================================================

    }

    public void drawPolygon() {
        drawingOverlay.setUp(DrawingOverlay.DrawingMode.POLYGON, symPolygon, null);
    }

    public void drawLine() {
        drawingOverlay.setUp(DrawingOverlay.DrawingMode.POLYLINE, symLine, null);

    }

    public void drawPoint() {
        drawingOverlay.setUp(DrawingOverlay.DrawingMode.POINT, pointSym, null);

    }

    public void clear() {
        graphicLayer.removeAll();
    }

//===========================================================
// The following block does saves
//===========================================================

    public void save() {


        //====================================================
        // Let the user pick a file using a GUI
        //====================================================
        try {
            FileChooser getFile = new FileChooser();
            getFile.setInitialDirectory(new File("H:/var/gist/8010/"));
            File graphicFile = getFile.showSaveDialog(pane.getScene().getWindow());

            if (graphicFile != null) {
                //=======================================
                // do something with the file
                //=======================================

                int[] graphicsIDs = graphicLayer.getGraphicIDs();
                FileOutputStream fos = new FileOutputStream(graphicFile);
                ObjectOutputStream oos = new ObjectOutputStream(fos);

                for (int i = 0; i < graphicsIDs.length; i++) {
                    oos.writeObject(graphicLayer.getGraphic(graphicsIDs[i]));
                }

                oos.close();

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
//===========================================================
// The following block does loads
//===========================================================

    public void load() {

        //====================================================
        // Let the user pick a file using a GUI
        //====================================================
        try {
            FileChooser getFile = new FileChooser();
            getFile.setInitialDirectory(new File("H:/var/gist/8010/"));
            File graphicFile = getFile.showOpenDialog(pane.getScene().getWindow());

            if (graphicFile != null) {
                //=======================================
                // do something with the file
                //=======================================

                FileInputStream fis = new FileInputStream(graphicFile);
                ObjectInputStream ois = new ObjectInputStream(fis);


                while (fis.available() > 0) {

//===========================================================
// The following block does purge
//===========================================================

                    graphicLayer.addGraphic((Graphic) ois.readObject());


                }
                ois.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}


