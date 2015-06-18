package client.boundary;

import client.business_logic.DirettoreBusinessLogic;
import server.entity.Posto;
import server.entity.Tavolo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by Andrew on 18/06/2015.
 */
public class DirettoreBoundary {

        private static final int MAX_POSTI_TAVOLO = 4;

        /** La frame del client di ingresso **/
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

            JButton buttonVisualizzaStato = new JButton("Visualizza stato fast food");
            buttonVisualizzaStato.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    frame.getContentPane().removeAll();
                    frame.getContentPane().revalidate();
                    frame.getContentPane().repaint();

                    DirettoreBusinessLogic direttoreBusinessLogic = new DirettoreBusinessLogic();
                    ArrayList<Tavolo> tavoli = direttoreBusinessLogic.visualizzaStatoFastFood();
                    for(Tavolo t : tavoli){
                        TableDrawing drawing = new TableDrawing(frame.getWidth(),frame.getHeight(),tavoli.size());
                        frame.getContentPane().add(drawing);
                        System.out.println("Tavolo : " + t.getNumero());
                        ArrayList<Posto> posti = t.getPosti();
                        for(int i = 0; i < posti.size(); i++){
                            ChairDrawing chairDrawing = new ChairDrawing(drawing,i,posti.get(i).getStatoString(),posti.size());
                            drawing.rectangles.add(chairDrawing);
                        }

                        drawing.revalidate();
                        drawing.repaint();

                    }
                }
            });
            frame.getContentPane().add(buttonVisualizzaStato);
        }
}

class TableDrawing extends JPanel {

    int x,y,w,h;
    int windowsWidth, windowsHeight;
    ArrayList<ChairDrawing> rectangles = new ArrayList<>();

    public TableDrawing(int ww , int wh , int numTavoli) {
        super();
        this.windowsWidth = ww;
        this.windowsHeight = wh;
        w = new Double((ww/numTavoli)*0.8).intValue();
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
        }
    }
}

class ChairDrawing extends Rectangle {

    int x,y,w,h;
    Color stato;

    public ChairDrawing(TableDrawing drawing, int numeroPosto , String stato , int numeroPosti) {
        //TODO: Le dimensioni della sedia sono in base al tavolo con maggior numero di posti.
        super();
        int tavX = drawing.x;
        int tavY = drawing.y;
        int tabW = drawing.w;
        int tabH = drawing.h;
        setStatoColor(stato);
        int offset = tabW / numeroPosti;
        x = tavX + (numeroPosto * offset);
        y = tavY - offset;
        w = (int) (offset * 0.6);
        h = (int) (offset * 0.6);
    }

    private void setStatoColor(String stato){
        //TODO: Potrebbe anche fare direttamente else, eliminare dopo testing approfondito.
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
