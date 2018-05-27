package Proto.gui.swing;

import Proto.domain.Verwaltung;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;

public class SwingGUI{

	private JFrame frame = getFenster();
	private JPanel studentenPanel = new JPanel(new BorderLayout());
	private JPanel projektPanel = new JPanel(new BorderLayout());
	private String[] studentenUeberschriften = new String[]{"ID", "Vorname", "Nachname", "Matrikelnummer", "Projekt", "Zufriedenheit"};
	private String[] projektUeberschriften = new String[]{"ID", "Titel", "Plätze"};
	private JScrollPane studentenTabelle = getTabelle(studentenUeberschriften , Verwaltung.present("matching"));
	private JScrollPane projektTabelle = getTabelle(projektUeberschriften, Verwaltung.present("projekte"));
	public void oeffne(){
		frame.add(getMenu());
		frame.pack();
		frame.setVisible(true);
	}

	public JScrollPane getTabelle(String[] reihenNamen, Object[][] daten){
		JTable tabelle = new JTable(daten, reihenNamen);
		final TableColumnModel columnModel = tabelle.getColumnModel();
		for(int column = 0; column < tabelle.getColumnCount(); column++){
			int width = 15; // Min width
			for(int row = 0; row < tabelle.getRowCount(); row++){
				TableCellRenderer renderer = tabelle.getCellRenderer(row, column);
				Component comp = tabelle.prepareRenderer(renderer, row, column);
				width = Math.max(comp.getPreferredSize().width + 1, width);
			}
//			if(width > 300)
//				width=300;
			width = Math.max(new JLabel(tabelle.getColumnName(column)).getPreferredSize().width + 1, width);
			columnModel.getColumn(column).setPreferredWidth(width);
		}
		return new JScrollPane(tabelle);
	}

	public JFrame getFenster(){
		JFrame ergebnis = new JFrame("Projektvergabe");
		ergebnis.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		ergebnis.setLocationByPlatform(true);
		ergebnis.setPreferredSize(new Dimension(700, 400));
		ergebnis.setResizable(false);
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}catch(Exception e){
			Toolkit.getDefaultToolkit().beep();
			JOptionPane.showMessageDialog(ergebnis,
					e.getMessage(),
					"Error",
					JOptionPane.ERROR_MESSAGE);
		}
		return ergebnis;
	}

	public JTabbedPane getMenu(){
		JTabbedPane ergebnis = new JTabbedPane();

		JToolBar studentenToolBar = new JToolBar();
		studentenToolBar.setFloatable(false);
		studentenToolBar.setMargin(new Insets(0,3,0,3));

		JToolBar projektToolBar = new JToolBar();
		projektToolBar.setFloatable(false);

		JButton zuweisung = new JButton("Zuweisung");
		zuweisung.addActionListener(e -> {
			Verwaltung.ordne();
			zuweisung.setEnabled(false);
			studentenPanel.remove(studentenTabelle);
			studentenTabelle = getTabelle(studentenUeberschriften, Verwaltung.present("matching"));
			studentenPanel.add(studentenTabelle);
			projektPanel.remove(projektTabelle);
			projektTabelle = getTabelle(projektUeberschriften, Verwaltung.present("projekte"));
			projektPanel.add(projektTabelle);
			frame.repaint();
		});
		zuweisung.setEnabled(false);
		projektToolBar.add(zuweisung);
		studentenToolBar.add(zuweisung);

		JButton importiereStudenten = new JButton("importiere Studenten");
		importiereStudenten.addActionListener( e -> {
			Verwaltung.importiereStudenten();
			zuweisung.setEnabled(true);
			studentenPanel.remove(studentenTabelle);
			studentenTabelle = getTabelle(studentenUeberschriften, Verwaltung.present("matching"));
			studentenPanel.add(studentenTabelle);
			frame.repaint();
		});
		importiereStudenten.setEnabled(false);
		studentenToolBar.add(importiereStudenten);

		JButton importiereProjekte = new JButton("importiere Projekte");
		importiereProjekte.addActionListener( e -> {
			Verwaltung.importiereProjekte();
			importiereStudenten.setEnabled(true);
			projektPanel.remove(projektTabelle);
			projektTabelle = getTabelle(projektUeberschriften, Verwaltung.present("projekte"));
			projektPanel.add(projektTabelle);
			frame.repaint();
		});
		projektToolBar.add(importiereProjekte);

		studentenPanel.add(studentenToolBar, BorderLayout.PAGE_START);
		studentenPanel.add(studentenTabelle, BorderLayout.PAGE_END);

		projektPanel.add(projektToolBar, BorderLayout.PAGE_START);
		projektPanel.add(projektTabelle,  BorderLayout.PAGE_END);

		ergebnis.addTab("Studenten", studentenPanel);
		ergebnis.addTab("Projekte", projektPanel);

		return ergebnis;
	}
}
