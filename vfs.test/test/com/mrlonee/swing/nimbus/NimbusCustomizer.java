/*
 * NimbusCustomizer
 * Copyright 2010 (C) Mr LoNee - (Laurent NICOLAS) - www.mrlonee.com 
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, see <http://www.gnu.org/licenses/>.
 */
package com.mrlonee.swing.nimbus;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.colorchooser.AbstractColorChooserPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;

import org.jdesktop.laffy.buttons.ButtonPanel;
import org.jdesktop.laffy.buttons.CheckPanel;
import org.jdesktop.laffy.buttons.RadioPanel;
import org.jdesktop.laffy.buttons.TogglePanel;
import org.jdesktop.laffy.combospinner.ComboPanel;
import org.jdesktop.laffy.combospinner.EditableComboPanel;
import org.jdesktop.laffy.combospinner.SpinnerPanel;
import org.jdesktop.laffy.internalframes.InternalFramesPanel;
import org.jdesktop.laffy.list.ListPanel;
import org.jdesktop.laffy.progress.ProgressPanel;
import org.jdesktop.laffy.progress.VerticalProgressPanel;
import org.jdesktop.laffy.sliders.SliderPanel;
import org.jdesktop.laffy.sliders.VerticalSliderPanel;
import org.jdesktop.laffy.splitter.SplittersPanel;
import org.jdesktop.laffy.table.TablePanel;
import org.jdesktop.laffy.tabs.TabsPanel;
import org.jdesktop.laffy.texts.LabelPanel;
import org.jdesktop.laffy.texts.TextAreaPanel;
import org.jdesktop.laffy.texts.TextFieldPanel;
import org.jdesktop.laffy.tree.TreePanel;

public class NimbusCustomizer extends JFrame{

	private static final long serialVersionUID = 4420130042376614663L;
	protected JTable table ;
	
	public NimbusCustomizer(){
		super();
		this.initialize();
	}
	
	public void initialize(){
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBounds(
		GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds());
		
		this.getContentPane().setLayout(
				new BorderLayout());
		
		this.initializeMenuBar();
		this.initializeSwingContent();
		this.initializeCustomizer();
	}
	
	public void initializeSwingContent(){
		
		JTabbedPane swingContainer = new JTabbedPane();
		
		JPanel buttonPanel = new JPanel(new GridLayout(2, 2));
		
		ButtonPanel demoButton = new ButtonPanel();
		buttonPanel.add(demoButton);
		
		CheckPanel comboBoxDemo = new CheckPanel();
		buttonPanel.add(comboBoxDemo);
		
		TogglePanel toggleButtonDemo = new TogglePanel();
		buttonPanel.add(toggleButtonDemo);
		
		RadioPanel radioPanel = new RadioPanel();
		buttonPanel.add(radioPanel);
		
		swingContainer.addTab("Buttons", buttonPanel);
		
		JPanel comboSpinnerPanel = new JPanel(new GridLayout(2, 2));
		
		ComboPanel comboPanel = new ComboPanel();
		comboSpinnerPanel.add(comboPanel);
		
		EditableComboPanel editableComboPanel = new EditableComboPanel();
		comboSpinnerPanel.add(editableComboPanel);
		
		SpinnerPanel spinnerPanel = new SpinnerPanel();
		comboSpinnerPanel.add(spinnerPanel);
		
		SplittersPanel splittersPanel = new SplittersPanel();
		comboSpinnerPanel.add(splittersPanel);
		
		swingContainer.addTab("ComboSpinner", comboSpinnerPanel);
		
		JPanel sliderSplitterPanel = new JPanel(new GridLayout(1, 2));
		JPanel intermediatePanel = new JPanel(new GridLayout(2, 1));
		SliderPanel sliderPanel = new SliderPanel();
		intermediatePanel.add(sliderPanel);
		
		VerticalSliderPanel verticalSliderPanel = new VerticalSliderPanel();
		intermediatePanel.add(verticalSliderPanel);
		sliderSplitterPanel.add(intermediatePanel);
		
		TabsPanel tabsPanel = new TabsPanel();
		sliderSplitterPanel.add(tabsPanel);
		
		swingContainer.addTab("SliderTab", sliderSplitterPanel);
		
		
		JPanel listTableTreePanel = new JPanel(new GridLayout(1, 3));
		
		ListPanel sistPanel = new ListPanel();
		listTableTreePanel.add(sistPanel);
		
		TablePanel tablePanel = new TablePanel();
		listTableTreePanel.add(tablePanel);
		
		swingContainer.addTab("ListTable", listTableTreePanel);
		
		JPanel TreePanel = new JPanel(new GridLayout(1, 2));
		TreePanel treePanel = new TreePanel();
		TreePanel.add(treePanel);
		
		InternalFramesPanel internalFramesPanel = new InternalFramesPanel();
		TreePanel.add(internalFramesPanel);
		
		swingContainer.addTab("TreeInternalFrame", TreePanel);
		
		JPanel textPanel = new JPanel(new GridLayout(3, 1));
		
		LabelPanel labelPanel = new LabelPanel();
		textPanel.add(labelPanel);
		
		TextAreaPanel textAreaPanel = new TextAreaPanel();
		textPanel.add(textAreaPanel);
		
		TextFieldPanel textFieldPanel = new TextFieldPanel();
		textPanel.add(textFieldPanel);
		
		swingContainer.addTab("Label", textPanel);
		
		JPanel progressPanels = new JPanel(new GridLayout(2, 1));
		
		ProgressPanel progressPanel = new ProgressPanel();
		progressPanels.add(progressPanel);
		
		VerticalProgressPanel verticalProgressPanel = new VerticalProgressPanel();
		progressPanels.add(verticalProgressPanel);
		
		swingContainer.addTab("Progress", progressPanels);
		
		this.getContentPane().add(swingContainer, BorderLayout.CENTER);
	}
	
	private void updateLookAndFeel() {
		SwingUtilities.updateComponentTreeUI(this);
	}
	 
	public void initializeCustomizer(){
		Container customizerContainer = new JPanel();
		customizerContainer.setLayout(new BorderLayout());
		this.table = new JTable(new NimbusPropertyTableModel());
		
		final JColorChooser colorChooser = new JColorChooser();
		
		AbstractColorChooserPanel[] panels = 
			colorChooser.getChooserPanels();
		
		colorChooser.getSelectionModel().addChangeListener(new ChangeListener() {
			
			public void stateChanged(ChangeEvent e) {
				final Color newColor = colorChooser.getColor();
				final int selectedIndex = table.getSelectedRow();
				
				table.setValueAt(newColor, selectedIndex, 2);
				
				SwingUtilities.invokeLater(new Runnable(){

					public void run() {
						UIManager.put(
								table.getValueAt(selectedIndex, 0),
								newColor);
						
						updateLookAndFeel();
					}
					
				});
			}
		});
		JPanel chooserPanel = new JPanel(new GridLayout(1, 3));
		chooserPanel.add(panels[0]);
		chooserPanel.add(panels[1]);
		customizerContainer.add(chooserPanel, BorderLayout.WEST);
		
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION );
		table.setDefaultRenderer(Color.class, new ColorRenderer());
		
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			
			public void valueChanged(ListSelectionEvent e) {
				ListSelectionModel lsm = (ListSelectionModel)e.getSource();
				int index = lsm.getLeadSelectionIndex();
				Object objColor = (table.getModel().getValueAt(index, 2));
				if(objColor == null){
					objColor = (table.getModel().getValueAt(index, 1));
				}
				colorChooser.setColor((Color)objColor);	
			}
		});
		
		customizerContainer.add(table, BorderLayout.CENTER);
		
		this.getContentPane().add(customizerContainer, BorderLayout.NORTH);
	}
	
	public class NimbusPropertyTableModel extends AbstractTableModel{

		private static final long serialVersionUID = -4150546123577860441L;

		protected String[] columnNames = {
				"NimbusPropertyKey", 
				"InitialValue",
				"CustomizedValue"
		};
		
		protected Object[][] data = {
				{"control", 			new Color(214, 217, 223), 	null}, 
				{"info",				new Color(242, 242, 189), 	null}, 
				{"nimbusAlertYellow", 	new Color(255, 220, 35),  	null},  
				{"nimbusBase", 			new Color(51, 98, 140) , 	null}, 
				{"nimbusDisabledText", 	new Color(142, 143, 145), 	null},  
				{"nimbusFocus",			new Color(115, 164, 209),	null}, 
				{"nimbusGreen",			new Color(176, 179, 50),	null},
				{"nimbusInfoBlue",		new Color(49, 92, 180),		null},
				{"nimbusLightBackground",new Color(255, 255, 255),	null},
				{"nimbusOrange",		new Color(191, 98, 4),		null},
				{"nimbusRed",			new Color(169, 46, 34),		null},
				{"nimbusSelectedText",	new Color(255, 255, 255),	null},
				{"nimbusSelectionBackground",new Color(57, 105, 138),null},
				{"text",				new Color(0, 0, 0),			null},
		};
		
		public int getColumnCount() {
			return columnNames.length;
		}

		public int getRowCount() {
			return data.length;
		}

		public String getColumnName(int col){
			return columnNames[col];
		}
		
		public Object getValueAt(int rowIndex, int columnIndex) {
			return data[rowIndex][columnIndex];
		}
		
		public Class<?> getColumnClass(int c){
			if(c == 0){
				return String.class;
			}
			else if(c == 1){
				return Color.class;
			}
			else if(c == 2){
				return Color.class;
			}
			return null;
		}
		
		public void setValueAt(Object value, int row, int col){
			data[row][col] = value;
			fireTableCellUpdated(row, col);
		}
	}
	
	public class ColorRenderer extends JLabel implements TableCellRenderer{
		private static final long serialVersionUID = 5701053870014602477L;

		public ColorRenderer(){
			this.setOpaque(true);
		}
		
		public Component getTableCellRendererComponent(JTable table,
				Object value, boolean isSelected, boolean hasFocus, int row,
				int column) {
			Color newColor = (Color)value;
			setBackground(newColor);
			
			return this;
		}
	}
	
	public void initializeMenuBar(){
		JMenuBar menuBar = new JMenuBar();
		JMenu about= new JMenu("About");
		JMenuItem author = new JMenuItem("Author");
		author.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				final JDialog dialog = new JDialog(NimbusCustomizer.this, "About Author", true);
				StringBuilder text = new StringBuilder();
				text.append("This program is written by MrLoNee\n");
				text.append("Visit site at www.mrlonee.com\n");
				text.append("See you there !");
				JTextArea area = new JTextArea();
				area.setText(text.toString());
				dialog.setLayout(new BorderLayout());
				dialog.add(area, BorderLayout.CENTER);
				JButton okButton = new JButton("Ok");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dialog.setVisible(false);
					}
				});
				dialog.add(okButton, BorderLayout.SOUTH);
				dialog.pack();
				dialog.setTitle("About");
				dialog.setLocation(
						(int)(getWidth()/2.0-dialog.getWidth()/2.0),
						(int)(getHeight()/2.0-dialog.getHeight()/2.0));
				dialog.setVisible(true);
			}
		});
		about.add(author);
		
		JMenu file= new JMenu("File");
		
//		JMenuItem saveCustomization = new JMenuItem("Save Customization");
//		file.add(saveCustomization);
		
		JMenuItem seeCustomization = new JMenuItem("See Customization");
		file.add(seeCustomization);
		
//		final JFileChooser fileChooser = new JFileChooser();
		
//		saveCustomization.addActionListener(new ActionListener() {
//			
//			public void actionPerformed(ActionEvent e) {
//				//perform save of nimbus configuration here
//				int result = fileChooser.showSaveDialog(NimbusCustomizer.this);
//				if(result == JFileChooser.APPROVE_OPTION){
//					File f = fileChooser.getSelectedFile();
//					if(!f.exists()){
//						try {
//							f.createNewFile();
//						} catch (IOException e1) {
//							e1.printStackTrace();
//						}
//					}
//					BufferedWriter writer = null;
//					try {
//						writer = new BufferedWriter(new FileWriter(f));
//						
//						for(int i=0; i<table.getModel().getRowCount(); i++ ){
//							String key = (String)table.getModel().getValueAt(i, 0);
//							Color color = (Color)table.getModel().getValueAt(i, 2);
//							
//							if(color != null){
//								String toWrite = 
//									"UIManager.put(\""+key+"\", new Color("+
//								color.getRed()+", "+
//								color.getGreen()+", "+
//								color.getBlue()+"));";
//								writer.write(toWrite);
//								writer.newLine();	
//							}
//							
//						}
//						
//					} catch (IOException e1) {
//						e1.printStackTrace();
//					}
//					
//					finally{
//						if(writer != null){
//							try {
//								writer.flush();
//								writer.close();
//							} catch (IOException e1) {
//								e1.printStackTrace();
//							}
//						}
//					}
//				}
//			}
//		});
		
		seeCustomization.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame customizationFrame = new JFrame();
				customizationFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				
				StringBuilder builder = new StringBuilder();
				for(int i=0; i<table.getModel().getRowCount(); i++ ){
					String key = (String)table.getModel().getValueAt(i, 0);
					Color color = (Color)table.getModel().getValueAt(i, 2);
					
					if(color != null){
						String toWrite = 
							"UIManager.put(\""+key+"\", new Color("+
						color.getRed()+", "+
						color.getGreen()+", "+
						color.getBlue()+"));\n";
						builder.append(toWrite);
					}
					
				}
				
				JTextArea area = new JTextArea(builder.toString());
				
				customizationFrame.getContentPane().add(area);
				customizationFrame.pack();
				customizationFrame.setVisible(true);
				}
		});
		
		JMenuItem exit = new JMenuItem("Exit");
		file.add(exit);
		exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		menuBar.add(file);
		menuBar.add(about);
		this.setJMenuBar(menuBar);
	}
	
	public void setVisible(){
		this.setVisible(true);
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		NimbusCustomizer customizer = new NimbusCustomizer();
		customizer.setVisible();
	}

}
