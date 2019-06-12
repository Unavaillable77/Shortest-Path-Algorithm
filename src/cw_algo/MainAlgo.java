//Raul Cicos W1547867
package cw_algo;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.border.LineBorder;

/**
 *
 * @author w1547867
 */

public class MainAlgo extends javax.swing.JFrame {

    private final int SIZE = 20;
    private Spot[][] spots = new Spot[SIZE][SIZE];
    private JLabel[][] grid = new JLabel[SIZE][SIZE];
    private boolean startSet = false;
    private boolean finishSet = false;

    private Spot startSpot;
    private Spot finishSpot;

    private ArrayList<Spot> neighbours = new ArrayList<>();
    private ArrayList<Spot> openSet = new ArrayList<>();
    private ArrayList<Spot> closedSet = new ArrayList<>();
    private ArrayList<Spot> path = new ArrayList<>();
    private boolean loopStop = false;

    private int cost = 0;
    private long startTime;
    private long endTime;
    private long totalTime;

    public MainAlgo() {
        initComponents();
        this.setLocationRelativeTo(null);// center form in the screen

        MouseAdapter myMA = new MouseAdapterMod(); // creates a mouseAdapter for each of the labels in the grid
        int cells[][] //this is to put the weight in order of the image from the specification 
                = {{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 2, 1, 1, 1, 1, 1, 1, 1},
                {4, 4, 1, 4, 1, 1, 1, 2, 2, 2, 2, 2, 2, 1, 1, 1, 1, 1, 1, 1},
                {4, 4, 4, 4, 4, 4, 1, 1, 2, 3, 3, 3, 2, 1, 1, 1, 1, 1, 1, 1},
                {4, 4, 4, 4, 4, 4, 1, 1, 2, 3, 3, 3, 3, 2, 1, 1, 1, 1, 1, 1},
                {1, 1, 4, 1, 1, 1, 1, 1, 2, 2, 3, 3, 3, 2, 1, 1, 2, 2, 1, 1},
                {1, 4, 4, 1, 2, 2, 1, 1, 1, 2, 2, 2, 2, 2, 1, 2, 2, 2, 1, 1},
                {4, 2, 1, 1, 2, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 1, 1, 1},
                {1, 2, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 4, 4},
                {1, 1, 2, 3, 3, 2, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 4, 4, 4},
                {1, 2, 3, 3, 3, 3, 2, 2, 1, 1, 1, 1, 4, 4, 4, 4, 4, 4, 4, 1},
                {1, 2, 3, 2, 2, 2, 3, 2, 4, 1, 1, 1, 4, 4, 4, 4, 2, 1, 1, 1},
                {1, 2, 2, 1, 1, 1, 4, 4, 4, 4, 1, 1, 4, 4, 4, 1, 1, 1, 1, 1},
                {1, 1, 4, 4, 4, 4, 4, 4, 4, 4, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                {4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 1, 5, 1, 1, 1, 1, 1, 1, 1, 5},
                {1, 1, 4, 4, 4, 4, 1, 1, 1, 2, 2, 5, 5, 1, 1, 1, 1, 1, 1, 5},
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 5, 5, 5, 1, 1, 5, 5, 5},
                {1, 2, 2, 2, 2, 2, 1, 1, 1, 2, 1, 1, 1, 5, 5, 5, 5, 5, 5, 5},
                {2, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 5, 5, 5, 5, 5, 5, 5},
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 5, 5, 5, 5, 5, 5, 5, 5, 5},
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 5, 5, 5, 5, 5, 5, 5, 5, 5}};

        for (int x = 0; x < SIZE; x++) { // initialises the spots 2D aray with spots that have x an y coordinates as well as the weight
            for (int y = 0; y < SIZE; y++) {
                spots[x][y] = new Spot(x, y, cells[x][y]);
                if (spots[x][y].getWeight() == 5) {
                    spots[x][y].setWall(true);
                }
            }
        }

        for (int x = 0; x < SIZE; x++) { // initialises the neighbours for each of the spots
            for (int y = 0; y < SIZE; y++) {
                spots[x][y].addNeighbours(spots);
            }
        }

        jPanelData.setLayout(new GridLayout(20, 20, 3, 3)); // creates the grid in the JPanel
        for (int i = 0; i < SIZE; i++) { // initialises a 2D array of JLabels called grid
            for (int j = 0; j < SIZE; j++) {
                grid[i][j] = new JLabel();
                grid[i][j].addMouseListener(myMA); // adds mouse listener to each label in order to get coordinates by clicking on a label
                Dimension preferredSize = new Dimension(27, 5);
                grid[i][j].setPreferredSize(preferredSize);
                grid[i][j].setBorder(new LineBorder(Color.BLACK));
                //grid[i][j].setBackground(Color.black);
                grid[i][j].setOpaque(true); // sets them opaque in order to show the color (depending on weight)
                jPanelData.add(grid[i][j]); // adds each label to the JPanel
            }
        }
        jLabelTotalCost.setVisible(false); // sets the calculations visible to false until the calculation of path has been made 
        jTextFieldTotalCost.setVisible(false);
        jLabelTotalTime.setVisible(false);
        jTextFieldTotalTime.setVisible(false);
        jButtonPathOnly.setEnabled(false);
        
        updateTable(grid, spots); // updates the grid with the colours of the spots array
    }

    class MouseAdapterMod extends MouseAdapter {
        
        @Override
        public void mousePressed(MouseEvent e) { // gets the coordinates of the JLabel pressed
            int xCor = e.getComponent().getX() / 30; // x coordinate
            int yCor = (e.getComponent().getY() - 1) / 30; // y coordinate
            if (!startSet) {
                jTextFieldStart1.setText(Integer.toString(yCor)); // if the start hasn't been chosen then it will set those coordinates first
                jTextFieldStart2.setText(Integer.toString(xCor));
            } else if (!finishSet) { // it will set the goal / finish coordinates after the start has been set
                jTextFieldFinish1.setText(Integer.toString(yCor));
                jTextFieldFinish2.setText(Integer.toString(xCor));
            }
        }
    }

    public void updateTable(JLabel[][] grid, Spot[][] spots) {
        // gets the weight of each of the spot in the spots array
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                int colour = spots[i][j].getWeight();
                // checks if its in the open set, closed set, if its the path or start and finish, lowest priority first so start and finish will always show as its highest
                if (spots[i][j].isOpenSet()) {
                    colour = 40;
                }
                if (spots[i][j].isClosedSet()) {
                    colour = 50;
                }
                if (spots[i][j].isPath()) {
                    colour = 30;
                }
                if (spots[i][j].isStart()) {
                    colour = 10;
                }
                if (spots[i][j].isFinish()) {
                    colour = 20;
                }

                switch (colour) {
                    case 1:
                        grid[i][j].setBackground(Color.WHITE); // weight 1
                        break;
                    case 2:
                        grid[i][j].setBackground(Color.LIGHT_GRAY); // weight 2 
                        break;
                    case 3:
                        grid[i][j].setBackground(Color.GRAY); // weight 3
                        break;
                    case 4:
                        grid[i][j].setBackground(Color.DARK_GRAY); // weight 4
                        break;
                    case 5:
                        grid[i][j].setBackground(Color.BLACK);// weight 5 which is the Wall
                        break;
                    case 10:
                        grid[i][j].setBackground(Color.GREEN); // the start
                        break;
                    case 20:
                        grid[i][j].setBackground(Color.RED); // the finish / goal
                        break;
                    case 30:
                        grid[i][j].setBackground(Color.BLUE); // the path from start to finish
                        break;
                    case 40:
                        grid[i][j].setBackground(Color.ORANGE); // openset
                        break;
                    case 50:
                        grid[i][j].setBackground(Color.PINK); // closed set
                        break;
                }
            }
        }
    }

    public double heuristic(Spot neighbour, Spot goal) { // gets the heuristic between a neighbour and the goal 
        int xStart = neighbour.getX();
        int yStart = neighbour.getY();

        int xFinish = goal.getX();
        int yFinish = goal.getY();

        double heuristic = 0;
        switch (jComboBoxHeuristic.getSelectedIndex()) {
            case 0:
                // Manhattan
                heuristic = Math.abs(xStart - xFinish) + Math.abs(yStart - yFinish);
                break;
            case 1:
                // Euclidean
                heuristic = Math.sqrt(((xStart - xFinish) * (xStart - xFinish)) + ((yStart - yFinish) * (yStart - yFinish)));
                break;
            case 2:
                // Chebyshev
                heuristic = Math.max(Math.abs(xStart - xFinish), Math.abs(yStart - yFinish));
                break;
            default:
                JOptionPane.showMessageDialog(null, "Heuristic error");
                break;
        }
        return heuristic;
    }

    public void removeFromArray(ArrayList openSet, Spot spot) { // removes a element from the open set array
        for (int i = openSet.size() - 1; i >= 0; i--) {
            if (openSet.get(i) == spot) {
                openSet.remove(i);
            }
        }
    }

    public void aStar(Spot start, Spot goal) { // performs the A* algorithm 
        startTime = System.nanoTime();
        openSet.add(startSpot); // gets the current time in nanoseconds
//        start.setF(heuristic(start, goal));

        while (!loopStop) { // while loopStop is false
            int winner = 0;
            for (int i = 0; i < openSet.size(); i++) { //
                if (openSet.get(i).getF() < openSet.get(winner).getF()) {
                    winner = i;
                }
            }
            Spot current = openSet.get(winner);

            if (current == goal) {
                loopStop = true;
                Spot temp = current;
                path.add(temp);
                while (temp.getPrevious() != null) {
                    path.add(temp.getPrevious());
                    temp = temp.getPrevious();
                }
            }

            removeFromArray(openSet, current);
            closedSet.add(current);

            neighbours = current.getNeighbours();

            for (int i = 0; i < neighbours.size(); i++) {
                Spot neighbour = neighbours.get(i);

                if (!closedSet.contains(neighbour) && !neighbour.isWall()) {
                    int tempG = current.getG() + neighbour.getWeight(); // change for weight

                    boolean bestPath = false;
                    if (openSet.contains(neighbour)) {
                        if (tempG < neighbour.getG()) {
                            neighbour.setG(tempG);
                            bestPath = true;
                        }
                    } else {
                        neighbour.setG(tempG);
                        openSet.add(neighbour);
                        bestPath = true;
                    }
                    if (bestPath) {
                        neighbour.setPrevious(current);
                        neighbour.setH(heuristic(neighbour, goal));
                        neighbour.setF(neighbour.getG() + neighbour.getH());
                        neighbour.setOpenSet(true);
                    }
                }
            }
            for (int i = 0; i < openSet.size(); i++) {
                openSet.get(i).setOpenSet(true);
                updateTable(grid, spots);
            }
            for (int i = 0; i < closedSet.size(); i++) {
                closedSet.get(i).setOpenSet(false);
                closedSet.get(i).setClosedSet(true);
                updateTable(grid, spots);
            }

        }
        for (int i = 0; i < path.size(); i++) {
            path.get(i).setPath(true);
            if (i > 0) {
                cost += path.get(i).getWeight();
            }
            updateTable(grid, spots);
        }
        endTime = System.nanoTime(); // gets the current time in nanoseconds
        totalTime = (endTime - startTime) / 1000000; // gets the time between the start of the algorightm and end 

        jLabelTotalCost.setVisible(true);
        jTextFieldTotalCost.setVisible(true);
        jTextFieldTotalCost.setText(Integer.toString(cost));
        jLabelTotalTime.setVisible(true);
        jTextFieldTotalTime.setVisible(true);
        jTextFieldTotalTime.setText(Long.toString(totalTime));

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabelClose = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabelMin = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanelCalculation = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabelCalculation = new javax.swing.JLabel();
        jTextFieldStart2 = new javax.swing.JTextField();
        jTextFieldStart1 = new javax.swing.JTextField();
        jTextFieldFinish1 = new javax.swing.JTextField();
        jTextFieldFinish2 = new javax.swing.JTextField();
        jButtonSetFinish = new javax.swing.JButton();
        jButtonSetStart = new javax.swing.JButton();
        jButtonReset = new javax.swing.JButton();
        jPanelCalculation1 = new javax.swing.JPanel();
        jComboBoxHeuristic = new javax.swing.JComboBox<>();
        jPanel5 = new javax.swing.JPanel();
        jLabelCalculation1 = new javax.swing.JLabel();
        jButtonCalculate = new javax.swing.JButton();
        jButtonPathOnly = new javax.swing.JButton();
        jLabelTotalCost = new javax.swing.JLabel();
        jTextFieldTotalCost = new javax.swing.JTextField();
        jLabelTotalTime = new javax.swing.JLabel();
        jTextFieldTotalTime = new javax.swing.JTextField();
        jPanelData = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        jPanel1.setBackground(new java.awt.Color(248, 148, 6));

        jLabelClose.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabelClose.setForeground(new java.awt.Color(255, 255, 255));
        jLabelClose.setText("X");
        jLabelClose.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabelClose.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabelCloseMouseClicked(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Path Finding Algorithm");

        jLabelMin.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabelMin.setForeground(new java.awt.Color(255, 255, 255));
        jLabelMin.setText("-");
        jLabelMin.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabelMin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabelMinMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabelMin)
                .addGap(18, 18, 18)
                .addComponent(jLabelClose)
                .addGap(21, 21, 21))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(29, 29, 29)
                    .addComponent(jLabel2)
                    .addContainerGap(662, Short.MAX_VALUE)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelMin, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
                    .addComponent(jLabelClose))
                .addContainerGap())
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                    .addContainerGap()))
        );

        jPanel2.setBackground(new java.awt.Color(44, 62, 80));

        jPanelCalculation.setBackground(new java.awt.Color(108, 122, 137));
        jPanelCalculation.setPreferredSize(new java.awt.Dimension(750, 100));

        jPanel4.setBackground(new java.awt.Color(248, 148, 6));

        jLabelCalculation.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabelCalculation.setForeground(new java.awt.Color(236, 240, 241));
        jLabelCalculation.setText("Set Start / Finish");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelCalculation)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelCalculation)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTextFieldStart2.setBackground(new java.awt.Color(108, 122, 137));
        jTextFieldStart2.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jTextFieldStart2.setForeground(new java.awt.Color(255, 255, 255));
        jTextFieldStart2.setText("0");
        jTextFieldStart2.setEnabled(false);

        jTextFieldStart1.setBackground(new java.awt.Color(108, 122, 137));
        jTextFieldStart1.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jTextFieldStart1.setForeground(new java.awt.Color(255, 255, 255));
        jTextFieldStart1.setText("0");
        jTextFieldStart1.setEnabled(false);

        jTextFieldFinish1.setBackground(new java.awt.Color(108, 122, 137));
        jTextFieldFinish1.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jTextFieldFinish1.setForeground(new java.awt.Color(255, 255, 255));
        jTextFieldFinish1.setText("0");
        jTextFieldFinish1.setEnabled(false);

        jTextFieldFinish2.setBackground(new java.awt.Color(108, 122, 137));
        jTextFieldFinish2.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jTextFieldFinish2.setForeground(new java.awt.Color(255, 255, 255));
        jTextFieldFinish2.setText("0");
        jTextFieldFinish2.setEnabled(false);

        jButtonSetFinish.setBackground(new java.awt.Color(34, 167, 240));
        jButtonSetFinish.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButtonSetFinish.setForeground(new java.awt.Color(255, 255, 255));
        jButtonSetFinish.setText("Set Finish");
        jButtonSetFinish.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSetFinishActionPerformed(evt);
            }
        });

        jButtonSetStart.setBackground(new java.awt.Color(34, 167, 240));
        jButtonSetStart.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButtonSetStart.setForeground(new java.awt.Color(255, 255, 255));
        jButtonSetStart.setText("Set Start");
        jButtonSetStart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSetStartActionPerformed(evt);
            }
        });

        jButtonReset.setBackground(new java.awt.Color(34, 167, 240));
        jButtonReset.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButtonReset.setForeground(new java.awt.Color(255, 255, 255));
        jButtonReset.setText("Reset");
        jButtonReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonResetActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelCalculationLayout = new javax.swing.GroupLayout(jPanelCalculation);
        jPanelCalculation.setLayout(jPanelCalculationLayout);
        jPanelCalculationLayout.setHorizontalGroup(
            jPanelCalculationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanelCalculationLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelCalculationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelCalculationLayout.createSequentialGroup()
                        .addComponent(jTextFieldStart1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jTextFieldStart2, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButtonSetStart, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanelCalculationLayout.createSequentialGroup()
                        .addComponent(jTextFieldFinish1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jTextFieldFinish2, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButtonSetFinish)))
                .addContainerGap(83, Short.MAX_VALUE))
            .addGroup(jPanelCalculationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanelCalculationLayout.createSequentialGroup()
                    .addGap(88, 88, 88)
                    .addComponent(jButtonReset, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(141, Short.MAX_VALUE)))
        );
        jPanelCalculationLayout.setVerticalGroup(
            jPanelCalculationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelCalculationLayout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanelCalculationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanelCalculationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jTextFieldStart1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextFieldStart2, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jButtonSetStart, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanelCalculationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldFinish1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldFinish2, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonSetFinish, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(100, Short.MAX_VALUE))
            .addGroup(jPanelCalculationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelCalculationLayout.createSequentialGroup()
                    .addContainerGap(186, Short.MAX_VALUE)
                    .addComponent(jButtonReset, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(36, 36, 36)))
        );

        jPanelCalculation1.setBackground(new java.awt.Color(108, 122, 137));
        jPanelCalculation1.setPreferredSize(new java.awt.Dimension(750, 100));

        jComboBoxHeuristic.setBackground(new java.awt.Color(34, 167, 240));
        jComboBoxHeuristic.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jComboBoxHeuristic.setForeground(new java.awt.Color(255, 255, 255));
        jComboBoxHeuristic.setMaximumRowCount(5);
        jComboBoxHeuristic.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Manhattan", "Euclidean", "Chebyshev" }));

        jPanel5.setBackground(new java.awt.Color(248, 148, 6));

        jLabelCalculation1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabelCalculation1.setForeground(new java.awt.Color(236, 240, 241));
        jLabelCalculation1.setText("Heuristic");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelCalculation1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelCalculation1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jButtonCalculate.setBackground(new java.awt.Color(34, 167, 240));
        jButtonCalculate.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButtonCalculate.setForeground(new java.awt.Color(255, 255, 255));
        jButtonCalculate.setText("Calculate");
        jButtonCalculate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCalculateActionPerformed(evt);
            }
        });

        jButtonPathOnly.setBackground(new java.awt.Color(34, 167, 240));
        jButtonPathOnly.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButtonPathOnly.setForeground(new java.awt.Color(255, 255, 255));
        jButtonPathOnly.setText("Show Path Only");
        jButtonPathOnly.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonPathOnlyActionPerformed(evt);
            }
        });

        jLabelTotalCost.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabelTotalCost.setForeground(new java.awt.Color(255, 255, 255));
        jLabelTotalCost.setText("Total Cost:");

        jTextFieldTotalCost.setEditable(false);
        jTextFieldTotalCost.setBackground(new java.awt.Color(108, 122, 137));
        jTextFieldTotalCost.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTextFieldTotalCost.setForeground(new java.awt.Color(255, 255, 255));
        jTextFieldTotalCost.setText("0");

        jLabelTotalTime.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabelTotalTime.setForeground(new java.awt.Color(255, 255, 255));
        jLabelTotalTime.setText("Total Time(ms):");

        jTextFieldTotalTime.setEditable(false);
        jTextFieldTotalTime.setBackground(new java.awt.Color(108, 122, 137));
        jTextFieldTotalTime.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTextFieldTotalTime.setForeground(new java.awt.Color(255, 255, 255));
        jTextFieldTotalTime.setText("0");

        javax.swing.GroupLayout jPanelCalculation1Layout = new javax.swing.GroupLayout(jPanelCalculation1);
        jPanelCalculation1.setLayout(jPanelCalculation1Layout);
        jPanelCalculation1Layout.setHorizontalGroup(
            jPanelCalculation1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanelCalculation1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelCalculation1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jComboBoxHeuristic, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanelCalculation1Layout.createSequentialGroup()
                        .addComponent(jButtonCalculate, javax.swing.GroupLayout.DEFAULT_SIZE, 159, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonPathOnly)))
                .addContainerGap())
            .addGroup(jPanelCalculation1Layout.createSequentialGroup()
                .addGap(57, 57, 57)
                .addGroup(jPanelCalculation1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanelCalculation1Layout.createSequentialGroup()
                        .addComponent(jLabelTotalTime)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldTotalTime, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanelCalculation1Layout.createSequentialGroup()
                        .addComponent(jLabelTotalCost, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldTotalCost, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanelCalculation1Layout.setVerticalGroup(
            jPanelCalculation1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelCalculation1Layout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jComboBoxHeuristic, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanelCalculation1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonCalculate, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonPathOnly, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanelCalculation1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelTotalCost, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldTotalCost, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelCalculation1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelTotalTime, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldTotalTime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(17, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanelDataLayout = new javax.swing.GroupLayout(jPanelData);
        jPanelData.setLayout(jPanelDataLayout);
        jPanelDataLayout.setHorizontalGroup(
            jPanelDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 600, Short.MAX_VALUE)
        );
        jPanelDataLayout.setVerticalGroup(
            jPanelDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanelData, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanelCalculation, javax.swing.GroupLayout.PREFERRED_SIZE, 328, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanelCalculation1, javax.swing.GroupLayout.PREFERRED_SIZE, 328, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanelData, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jPanelCalculation, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanelCalculation1, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 66, Short.MAX_VALUE)))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jLabelCloseMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelCloseMouseClicked
        System.exit(0);
    }//GEN-LAST:event_jLabelCloseMouseClicked

    private void jLabelMinMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelMinMouseClicked
        this.setState(JFrame.ICONIFIED);
    }//GEN-LAST:event_jLabelMinMouseClicked

    private void jButtonSetStartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSetStartActionPerformed

        int row = Integer.parseInt(jTextFieldStart1.getText());
        int column = Integer.parseInt(jTextFieldStart2.getText());
        if (spots[row][column].getWeight() == 5) {
            JOptionPane.showMessageDialog(null, "Can not choose Black Squares!");
        } else {
            startSet = true;
            startSpot = (Spot) spots[row][column];
            spots[row][column].setStart(true);
            jButtonSetStart.setEnabled(false);
            updateTable(grid, spots);
        }
    }//GEN-LAST:event_jButtonSetStartActionPerformed

    private void jButtonSetFinishActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSetFinishActionPerformed

        int row = Integer.parseInt(jTextFieldFinish1.getText());
        int column = Integer.parseInt(jTextFieldFinish2.getText());
        if (spots[row][column].getWeight() == 5) {
            JOptionPane.showMessageDialog(null, "Can not choose Black Squares!");
        } else {
            finishSet = true;
            finishSpot = (Spot) spots[row][column];
            spots[row][column].setFinish(true);
            jButtonSetFinish.setEnabled(false);
            updateTable(grid, spots);
        }
    }//GEN-LAST:event_jButtonSetFinishActionPerformed

    private void jButtonCalculateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCalculateActionPerformed
        if ((startSet) && (finishSet)) {
            aStar(startSpot, finishSpot);

            jComboBoxHeuristic.setEnabled(false);
            jButtonCalculate.setEnabled(false);
            jButtonPathOnly.setEnabled(true);
        } else {
            JOptionPane.showMessageDialog(null, "Set the Start and Finish!");
        }
    }//GEN-LAST:event_jButtonCalculateActionPerformed

    private void jButtonResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonResetActionPerformed
        new MainAlgo().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButtonResetActionPerformed

    private void jButtonPathOnlyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonPathOnlyActionPerformed
        for (int x = 0; x < SIZE; x++) {
            for (int y = 0; y < SIZE; y++) {
                spots[x][y].setOpenSet(false);
                spots[x][y].setClosedSet(false);
            }
        }
        jButtonPathOnly.setEnabled(false);
        updateTable(grid, spots);
    }//GEN-LAST:event_jButtonPathOnlyActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;

                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
        }

        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainAlgo().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonCalculate;
    private javax.swing.JButton jButtonPathOnly;
    private javax.swing.JButton jButtonReset;
    private javax.swing.JButton jButtonSetFinish;
    private javax.swing.JButton jButtonSetStart;
    private javax.swing.JComboBox<String> jComboBoxHeuristic;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabelCalculation;
    private javax.swing.JLabel jLabelCalculation1;
    private javax.swing.JLabel jLabelClose;
    private javax.swing.JLabel jLabelMin;
    private javax.swing.JLabel jLabelTotalCost;
    private javax.swing.JLabel jLabelTotalTime;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanelCalculation;
    private javax.swing.JPanel jPanelCalculation1;
    private javax.swing.JPanel jPanelData;
    private javax.swing.JTextField jTextFieldFinish1;
    private javax.swing.JTextField jTextFieldFinish2;
    private javax.swing.JTextField jTextFieldStart1;
    private javax.swing.JTextField jTextFieldStart2;
    private javax.swing.JTextField jTextFieldTotalCost;
    private javax.swing.JTextField jTextFieldTotalTime;
    // End of variables declaration//GEN-END:variables

}
