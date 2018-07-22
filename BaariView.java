package baarisovellus;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.border.TitledBorder;
import javax.swing.event.*;
import javax.swing.table.DefaultTableModel;
public class BaariView extends JFrame {
    
    private BaariController ohjain;
    private BaariDAO dao;
    private JPanel sisältöpaneeli;
    private JList drinkit;
    private DefaultListModel listModel;
    private JPanel ainekset;
    private String[] vaihtoehdot;
    private String[] ingreds;
    private JTextField haku;
    private JButton lisaa;
    private JComboBox ingredList;
    private JComboBox addIngredList;
    private JPanel container;
    private String[] measurements ={"cl", "dl", "kpl", "tl", "rkl", "litra", 
                                    "hyppysellinen", "tippa"};
    private JComboBox mList;
    private JTextField nimi;
    private JButton poista;
    private JTable table;
    private DefaultTableModel tableModel;
    
    private JLabel lasi;
    private JLabel lasiValue;
    private JLabel tekotapa;
    private JLabel tekotapaValue;
    
    private JLabel glass;
    private JTextField glassValue;
    private JLabel method;
    private JTextField methodValue;
    
    public BaariView(String[] vaihtoehdot, String[] ingreds){
        this.vaihtoehdot = vaihtoehdot;
        this.ingreds = ingreds;
        alustaKomponentit();
        
    }
    
    public void rekisteröiOhjain(BaariController ohjain, BaariDAO dao){
        this.ohjain = ohjain;
        this.dao = dao;
    }
    
    private void alustaKomponentit(){
        setTitle("Baarin tarjonta");
        setSize(new Dimension(300,300));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new FlowLayout());
        sisältöpaneeli = new JPanel();
        sisältöpaneeli.setPreferredSize(new Dimension(400,360));
        
        
        container = new JPanel();
        //container.setPreferredSize(new Dimension(400,360));
        GroupLayout layout = new GroupLayout(container);
        container.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        lasi = new JLabel("Lasi");
        lasiValue = new JLabel("esim");
        tekotapa = new JLabel("Tekotapa");
        tekotapaValue = new JLabel("esim");
        ainekset = new JPanel();
        TitledBorder title;
        title = BorderFactory.createTitledBorder("Resepti");
        ainekset.setBorder(title);
        ainekset.setLayout(new BoxLayout(ainekset,BoxLayout.Y_AXIS));
        ainekset.setPreferredSize(new Dimension(200,200));
        haku = new JTextField("");
        haku.setPreferredSize(new Dimension(100,24));
        haku.getDocument().addDocumentListener(new DocumentListener(){
            
            @Override
            public void insertUpdate(DocumentEvent e) {
                filter();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                filter();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                filter();
            }
            private void filter(){
                String filter = haku.getText();
                filterModel(listModel, filter);
            }
        });
        ingredList = new JComboBox(ingreds);
        listModel = new DefaultListModel();
        for (int i=0; i<vaihtoehdot.length; i++) {
            listModel.addElement(vaihtoehdot[i]);
        }
        drinkit = new JList(listModel);
        
        
        drinkit.setVisibleRowCount(5);
        drinkit.setFixedCellWidth(100);
        JScrollPane listScroller = new JScrollPane(drinkit);
        listScroller.setPreferredSize(new Dimension(120,200));
        lisaa = new JButton("Lisää");
        
        lisaa.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                addWindow();
            }
        });
        
        poista = new JButton("Poista");
        poista.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                ohjain.deleteDrink();
                int position = drinkit.getSelectedIndex();
                drinkit.setSelectedIndex(position-1);
                listModel.removeElementAt(position);
                //drinkit.setSelectedIndex(0);
            }
        });
        
        drinkit.addListSelectionListener(new ListSelectionListener(){
            
            @Override
            public void valueChanged(ListSelectionEvent arg0){
                if(!arg0.getValueIsAdjusting()){
                    //ainekset.setText(ohjain.printString(dao.getDrink(drinkit.getSelectedValue().toString())));
                    //ohjain.printString(dao.getDrink(getDrink()));
                    ainekset.removeAll(); //tyhjentää paneelin
                    ohjain.aseta(getDrink()); //etsii ja asettaa paneeliin arrayn
                    ainekset.repaint();
                    ainekset.revalidate(); //piirtää paneelin uudelleen
                }
            }
        });
        
        layout.setHorizontalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(haku) 
                    .addComponent(ingredList)
                    .addComponent(listScroller)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(lisaa)
                            .addComponent(poista)
                        )
                )
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addComponent(lasi)
                            .addComponent(tekotapa)
                        )
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addComponent(lasiValue)
                            .addComponent(tekotapaValue)
                        )
                    )
                    .addComponent(ainekset))
                
        );

        layout.setVerticalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(haku)
                        .addComponent(lasi)
                        .addComponent(lasiValue)
                )            
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(ingredList)
                        .addComponent(tekotapa)
                        .addComponent(tekotapaValue)
                )
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(listScroller)
                .addComponent(ainekset)
            )
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(lisaa)
                .addComponent(poista)
            )
        );
        layout.linkSize(listScroller,ainekset);
        //container.add(haku);
        //container.add(ingredList);
        //container.add(drinkit);
        //sisältöpaneeli.add(ainekset);
        //container.add(lisaa);
        sisältöpaneeli.add(container);
        //sisältöpaneeli.add(ainekset);
        setContentPane(container);
 
        pack();
        setVisible(true);
        
    }
    
    public void filterModel(DefaultListModel model, String filter){
        for(String s : vaihtoehdot){
            if(!s.startsWith(filter)){
                if(model.contains(s)){
                    model.removeElement(s);
                }
            }else{
                if(!model.contains(s)){
                    model.addElement(s);
                }
            }
        }
    }
    
    public void addWindow(){
        JFrame frame = new JFrame("Lisää drinkki");
                JPanel panel = new JPanel();
                GroupLayout addLayout = new GroupLayout(panel);
                panel.setLayout(addLayout);
                addLayout.setAutoCreateGaps(true);
                addLayout.setAutoCreateContainerGaps(true);
                
                JPanel topButton = new JPanel();
                JPanel bottomButton = new JPanel();
                
                JPanel warningPanel = new JPanel();
                JLabel warningText = new JLabel("");
                warningText.setForeground(Color.red);
                
                JLabel DrinkLabel = new JLabel("Nimi");
                JLabel IngredLabel = new JLabel("Ainesosa");
                JLabel orLabel = new JLabel("tai");
                JLabel amountLabel = new JLabel("Määrä");
                JLabel mLabel = new JLabel("Mitta");
                
                mList = new JComboBox(measurements);
                addIngredList = new JComboBox(ingreds);
                
                glass = new JLabel("Lasi");
                method = new JLabel("Tekotapa");
                
                glassValue = new JTextField();
                methodValue = new JTextField();
                
                tableModel = new DefaultTableModel();
                tableModel.addColumn("Nimi");
                tableModel.addColumn("Määrä");
                tableModel.addColumn("Mitta");
                JTable table = new JTable(tableModel);
                JScrollPane tableScroller = new JScrollPane(table);
                tableScroller.setPreferredSize(new Dimension(200,200));
                nimi = new JTextField();
                nimi.setPreferredSize(new Dimension(100,24));
                
                JTextField aines = new JTextField();
                aines.setPreferredSize(new Dimension(100,24));
                
                    
                JTextField maara = new JTextField();
                maara.setPreferredSize(new Dimension(100,24));
                JButton addI = new JButton("+");
                addI.addActionListener(new ActionListener(){
                    @Override
                    public void actionPerformed(ActionEvent e){
                        if(aines.getText()==null || aines.getText().equals("")){
                            String data1 = (String) addIngredList.getSelectedItem();
                            String data2 = maara.getText();
                            String data3 = mList.getSelectedItem().toString();
                            Object[] row = {data1, data2, data3};
                            tableModel.addRow(row);
                            
                        }else{
                            String data1 = aines.getText();
                            String data2 = maara.getText();
                            String data3 = mList.getSelectedItem().toString();
                            Object[] row = {data1, data2, data3};
                            tableModel.addRow(row);
                            }
                        }
                });
                
                
                
                JButton delI = new JButton("-");
                delI.addActionListener(new ActionListener(){
                    @Override
                    public void actionPerformed(ActionEvent e){
                        tableModel.removeRow(table.getSelectedRow());
                    }
                });
                
                JButton add = new JButton("Lisää");
                add.addActionListener(new ActionListener(){
                    @Override
                    public void actionPerformed(ActionEvent e){
                        if(nimi.getText()==null||nimi.getText().equals("")){
                            warningText.setText("Anna drinkin nimi!");
                        }else{
                            ohjain.addNewDrink();
                            listModel.addElement(getNewDrinkName());
                            frame.dispose();
                        }
                    }
                });
                JButton cancel = new JButton("Peruuta");
                cancel.addActionListener(new ActionListener(){
                    @Override
                    public void actionPerformed(ActionEvent e){
                        frame.dispose();
                    }
                });
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                /*panel.add(label);
                panel.add(nimi);
                panel.add(aines);
                panel.add(maara);
                panel.add(addI);
                panel.add(delI);
                panel.add(ingredList);
                panel.add(tableScroller);
                panel.add(add);
                panel.add(cancel);*/
                topButton.add(addI);
                topButton.add(delI);
                bottomButton.add(add);
                bottomButton.add(cancel);
                warningPanel.add(warningText);
                addLayout.setHorizontalGroup(addLayout.createSequentialGroup()
                        .addGroup(addLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(DrinkLabel)
                        .addComponent(glass)
                        .addComponent(method)
                        .addComponent(IngredLabel)
                        .addComponent(amountLabel)    
                        )
                        .addGroup(addLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(nimi)
                        .addComponent(glassValue)
                        .addComponent(methodValue)
                        .addComponent(aines)
                        .addComponent(maara)
                        .addComponent(topButton)
                        .addComponent(tableScroller)
                        .addComponent(bottomButton)
                        .addComponent(warningPanel)
                        )
                        .addGroup(addLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(orLabel)
                        .addComponent(mLabel)
                        )
                        .addGroup(addLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(addIngredList)
                        .addComponent(mList)
                        )
                        
                        
                );
                
                addLayout.setVerticalGroup(addLayout.createSequentialGroup()
                        .addGroup(addLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(DrinkLabel)
                        .addComponent(nimi)
                        )
                        .addGroup(addLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(glass)
                                .addComponent(glassValue)
                        )
                        .addGroup(addLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(method)
                                .addComponent(methodValue)
                        )
                        .addGroup(addLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(IngredLabel)
                        .addComponent(aines)
                        .addComponent(orLabel)
                        .addComponent(addIngredList)
                        )
                        .addGroup(addLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(amountLabel)
                        .addComponent(maara)
                        .addComponent(mLabel)
                        .addComponent(mList)
                        )
                        .addComponent(topButton)
                        .addComponent(tableScroller)
                        .addComponent(warningPanel)
                        .addComponent(bottomButton)
                        
                );
                
                
                frame.setContentPane(panel);
                frame.pack();
                frame.setVisible(true);
    }
    
    public String getDrink(){
        return drinkit.getSelectedValue().toString();
    }
    
    public String getNewDrinkName(){
        return nimi.getText();
    }
    
    public String getNewGlass(){
        return glassValue.getText();
    }
    
    public String getNewMethod(){
        return methodValue.getText();
    }
    
    public String[] getNewIngredients(){
        int rows = tableModel.getRowCount();
        String[] ingredients=new String[rows];
        for(int i=0;i<rows;i++){
            ingredients[i] =(String) tableModel.getValueAt(i, 0);
        }
        return ingredients;
    }
    
    public int[] getNewAmounts(){
        int rows = tableModel.getRowCount();
        int[] amounts = new int[rows];
        for(int i=0;i<rows;i++){
            amounts[i] = Integer.valueOf((String) tableModel.getValueAt(i, 1));
        }
        return amounts;
    }
    
    public String[] getNewMeasurements(){
        int rows = tableModel.getRowCount();
        String[] measur = new String[rows];
        for(int i=0;i<rows;i++){
            measur[i] =(String) tableModel.getValueAt(i, 2);
        }
        return measur;
    }
    
    public void setVaihtoehdot(String[] drinks){
        this.vaihtoehdot = drinks;
    }
    
    public void setIngredients(String[] ainekset){
        JLabel[] aineet = new JLabel[ainekset.length];
        for(int i=0; i<=aineet.length-1;i++){
            aineet[i]=new JLabel(ainekset[i]);
            System.out.println(ainekset[i]);
            this.ainekset.add(aineet[i]);
        }
    }
    
    public void setGlass(String glass){
        lasiValue.setText(glass);
    }
    
    public void setMethod(String method){
        tekotapaValue.setText(method);
    }
    
}
