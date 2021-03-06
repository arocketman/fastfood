package clientDirettore.boundary;

import clientDirettore.business_logic.DirettoreBusinessLogic;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Timer;
import java.util.concurrent.TimeUnit;

/**
 * Client per direttore, utilizzato per effettuare le operazioni di visualizza stato fast food e altro.
 */
public class DirettoreBoundary {

        /* Dimensioni in pixels di un singolo posto, utilizzato per rappresentare lo stato del fast food */
        public static final int DIM_POSTO = 26;


        /** La frame del clientPosto di ingresso **/
        private JFrame frame;

        public static void main(String[] args) {
            EventQueue.invokeLater(new Runnable() {
                public void run() {
                    try {
                        DirettoreBoundary window = new DirettoreBoundary();
                        window.frame.setVisible(true);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        public DirettoreBoundary() {
            initialize();
        }

        /**
         * Inizializza i contenuti della jframe.
         */
        private void initialize() {

            //Inizializzazione frame
            frame = new JFrame();
            frame.setBounds(100, 100, 800, 400);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.getContentPane().setMaximumSize(frame.getContentPane().getPreferredSize());
            frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.X_AXIS));

            //Setup bottone visualizza stato fast food.
            JButton buttonVisualizzaStato = new JButton("Visualizza stato fast food");
            buttonVisualizzaStato.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    java.util.Timer timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            updateStato();
                        }
                    },0,5000);
                }
            });
            frame.getContentPane().add(buttonVisualizzaStato);

            //Setup bottone visualizza occupato > 15min
            JButton buttonVisualizzaOccupati = new JButton("Visualizza posti occupati da piu' di 15 min");
            buttonVisualizzaOccupati.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
                    java.util.Timer timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            updateStatoOccupati();
                        }
                    },0,5000);
                }
            });
            frame.getContentPane().add(buttonVisualizzaOccupati);
        }

    private void updateStatoOccupati() {
        frame.getContentPane().removeAll();
        frame.getContentPane().revalidate();
        frame.getContentPane().repaint();
        DirettoreBusinessLogic direttoreBusinessLogic = new DirettoreBusinessLogic();
        ArrayList<String> tavoli = direttoreBusinessLogic.visualizzaStatoFastFood();
        JsonParser jsonParser = new JsonParser();
        for(String t : tavoli){
            JsonObject tavoloJson = (JsonObject) jsonParser.parse(t);

            JsonArray posti = tavoloJson.getAsJsonArray("posti");
            JLabel tavoloLabel = new JLabel("Tavolo : " + tavoloJson.get("numero"));
            tavoloLabel.setFont(new Font("Arial",Font.BOLD,16));
            frame.getContentPane().add(tavoloLabel);
            for(int i = 0; i < posti.size(); i++){
                if(posti.get(i).getAsJsonObject().get("stato").getAsString().equalsIgnoreCase("occupato")) {
                    String occupazione = posti.get(i).getAsJsonObject().get("occupazione").getAsString();
                    DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.ENGLISH);
                    Date date = null;
                    try {
                        date = format.parse(occupazione);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    long tempoOccupazione = getDateDiff(date, new Date(), TimeUnit.MINUTES);
                    if (tempoOccupazione > 15) {
                        JLabel postoLabel = new JLabel("Posto " + posti.get(i).getAsJsonObject().get("codice").getAsString() + " , " + tempoOccupazione + " minuti. ");
                        frame.getContentPane().add(postoLabel);
                    }
                }
            }
            frame.getContentPane().revalidate();
            frame.getContentPane().repaint();

        }
    }

    private static long getDateDiff(Date date1, Date date2, TimeUnit timeUnit) {
        long diffInMillies = date2.getTime() - date1.getTime();
        return timeUnit.convert(diffInMillies,TimeUnit.MILLISECONDS);
    }


    /**
     * Aggiorna lo stato del fast food. Andando a lavorare sull'arrayList di tavoli.
     */
    private void updateStato(){
        frame.getContentPane().removeAll();
        frame.getContentPane().revalidate();
        frame.getContentPane().repaint();
        DirettoreBusinessLogic direttoreBusinessLogic = new DirettoreBusinessLogic();
        JsonParser jsonParser = new JsonParser();

        ArrayList<String> tavoli = direttoreBusinessLogic.visualizzaStatoFastFood();
        for(String t : tavoli){
            JsonObject tavoloJson = (JsonObject) jsonParser.parse(t);

            JsonArray posti = tavoloJson.getAsJsonArray("posti");
            TableDrawing drawing = new TableDrawing(frame.getWidth(),frame.getHeight(),tavoli.size(),posti.size(),Integer.valueOf(tavoloJson.get("numero").toString().replace("\"","")));
            frame.getContentPane().add(drawing);
            for(int i = 0; i < posti.size(); i++){
                ChairDrawing chairDrawing = new ChairDrawing(drawing,i,posti.get(i).getAsJsonObject().get("stato").getAsString());
                drawing.rectangles.add(chairDrawing);
            }

            drawing.revalidate();
            drawing.repaint();

        }
    }
}

/**
 * Rappresenta un disegno di un tavolo.
 */
class TableDrawing extends JPanel {

    int x,y,w,h;
    int windowsWidth, windowsHeight;
    ArrayList<ChairDrawing> rectangles = new ArrayList<>();
    int numTavoli , numTavolo;

    public TableDrawing(int ww , int wh , int numTavoli , int numPosti, int numTavolo) {
        super();
        this.windowsWidth = ww;
        this.windowsHeight = wh;
        this.numTavoli = numTavoli;
        this.numTavolo = numTavolo;
        w = DirettoreBoundary.DIM_POSTO * numPosti;
        h = new Double((wh/numTavoli)*0.2).intValue();
        x = ((ww / numTavoli ) / 2 ) - w/2;
        y = wh / 2;
        this.setBorder(BorderFactory.createLineBorder(Color.black));
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.black);
        g.fillRect(x,y, w, h);

        for(ChairDrawing rect : rectangles){
            g.setColor(rect.stato);
            g.fillRect(rect.x, rect.y, rect.w, rect.h);
            g.setColor(Color.black);
            g.setFont(new Font("Monospaced", Font.PLAIN, 70/numTavoli));
            g.drawString("Tavolo " + numTavolo,(getWidth()/2 ) /2 ,this.getY()+50);
        }
    }
}

/**
 * Rappresenta un disegno di una sedia.
 */
class ChairDrawing extends Rectangle {

    int x,y,w,h;
    Color stato;

    public ChairDrawing(TableDrawing drawing, int numeroPosto , String stato) {
        super();
        int tavX = drawing.x;
        int tavY = drawing.y;
        setStatoColor(stato);
        int offset = DirettoreBoundary.DIM_POSTO;
        x = tavX + (numeroPosto * offset);
        y = tavY - offset;
        w = (int) (offset * 0.6);
        h = (int) (offset * 0.6);
    }

    private void setStatoColor(String stato){
        if(stato.equalsIgnoreCase("libero"))
            this.stato = Color.green;
        else if(stato.equalsIgnoreCase("assegnato"))
            this.stato = Color.blue;
        else if(stato.equalsIgnoreCase("occupato"))
            this.stato = Color.red;
        else
            this.stato = Color.black;

    }
}
