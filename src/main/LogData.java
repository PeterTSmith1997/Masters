package main;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.DefaultTableModel;

import admin.LoginUI;
import dataModel.Analise;
import dataModel.DataStore;
import dataModel.IPFunctions;
import dataModel.Reader;
import net.miginfocom.swing.MigLayout;

/**
 * @author peter
 * @version 19 Jul 2019
 */
public class LogData extends JFrame {

	/**
	 *
	 */
	private static final long serialVersionUID = -5672269806381056292L;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {
				new LogData();
			}
		});
	}

	private JFrame frmLogFileReader;
	private JScrollPane botsScrollPane;
	private JTable tbMain;
	private DefaultTableModel mainsMd;
	private JPanel serchIPPL;
	private JTextField txtFilter;
	private JLabel lblSearchByTimes;
	private int timesHitIp = 0;
	private int timesHitPages = 0;
	private JButton btnReadFile;
	private Reader reader;

	private DataStore dataStore;
	private Font deflautFont;
	private JButton btnAdmin;

	public LogData() {
		dataStore = new DataStore();
		reader = new Reader(dataStore);
		makeui();

	}

	/**
	 * @param dataStore
	 */
	public LogData(DataStore dataStore) {
		this.dataStore = dataStore;
		makeui();
		reader = new Reader(dataStore);

	}

	/**
	 * @return the timesHitIp
	 */
	public int getTimesHitIp() {
		return timesHitIp;
	}

	/**
	 * @return the timesHitPages
	 */
	public int getTimesHitPages() {
		return timesHitPages;
	}

	public void makeui() {
		LogData mainUi = this;
		frmLogFileReader = new JFrame();
		frmLogFileReader.setResizable(false);
		frmLogFileReader.setTitle("Log file reader");
		frmLogFileReader.setExtendedState(Frame.MAXIMIZED_BOTH);
		frmLogFileReader.setBounds(100, 100, 1169, 686);
		frmLogFileReader.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout(0, 0));
		frmLogFileReader.getContentPane().setLayout(new MigLayout("", "[654px,grow]", "[17px][715px][85px][106px,grow]"));
        deflautFont = new Font("Tahoma", Font.BOLD, 14);
		JLabel lbIPs = new JLabel("IPs on site");
		lbIPs.setHorizontalAlignment(SwingConstants.CENTER);
		lbIPs.setFont(deflautFont);
		frmLogFileReader.getContentPane().add(lbIPs,
				"cell 0 0,alignx center,aligny center");

		botsScrollPane = new JScrollPane();
		frmLogFileReader.getContentPane().add(botsScrollPane, "cell 0 1,grow");

		tbMain = new JTable();
		String ipHeader[] = new String[] { "IP", "Frequency", "risk" };
		mainsMd = new DefaultTableModel(null, ipHeader);
		tbMain.setModel(mainsMd);
		lbIPs.setLabelFor(tbMain);
		tbMain.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JTable target = (JTable) e.getSource();
				int row = target.getSelectedRow();
				String ip = (String) tbMain.getValueAt(row, 0);
				IPDetails details = new IPDetails(dataStore, ip);
				details.setVisible(true);
				frmLogFileReader.dispose();
			}
		});

		tbMain.setAutoCreateRowSorter(true);

		botsScrollPane.setViewportView(tbMain);
		JScrollPane scrollPane = new JScrollPane();
		getContentPane().add(scrollPane, BorderLayout.WEST);

		serchIPPL = new JPanel();
		frmLogFileReader.getContentPane().add(serchIPPL, "cell 0 2,grow");
		lblSearchByTimes = new JLabel("Search by times hit");
		lblSearchByTimes.setLabelFor(txtFilter);
		serchIPPL.add(lblSearchByTimes);

		
				btnReadFile = new JButton("Read file (Start here)");
				serchIPPL.add(btnReadFile);
				
				btnAdmin = new JButton("Admin login");
				serchIPPL.add(btnAdmin);
				btnAdmin.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						LoginUI loginUI = new LoginUI(dataStore);
						loginUI.setVisible(true);
						frmLogFileReader.dispose();
					}
				});
				btnReadFile.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						JFileChooser jfc = new JFileChooser(FileSystemView
								.getFileSystemView().getDefaultDirectory());
						jfc.setDialogTitle("Select a log file");
						jfc.setAcceptAllFileFilterUsed(false);
						FileNameExtensionFilter filter = new FileNameExtensionFilter(
								"Text files", "txt");
						jfc.addChoosableFileFilter(filter);
						jfc.setMultiSelectionEnabled(true);
						int returnValue = jfc.showSaveDialog(null);
						
						if (returnValue == JFileChooser.APPROVE_OPTION) {
							File[] files = jfc.getSelectedFiles();
							JOptionPane.showMessageDialog(null, "Processing fliles",
									"loading", JOptionPane.INFORMATION_MESSAGE);
							for (File f: files) {
								reader.setFile(f.getAbsolutePath());
								reader.readFile();
							}
							dataStore.setNumberOfFiles(files.length);
							Analise analise = new Analise();
							dataStore.setOrrcancesOfip(analise.getIpCounts(dataStore.getHits()));
							dataStore.setReferers(analise.getRefererCounts(dataStore.getHits()));
							dataStore.setProtcals(analise.getProtocalCounts(dataStore.getHits()));
							dataStore.setPages(analise.getPageCounts(dataStore.getHits()));
							analise.getTimeCounts(dataStore.getHits());
						
							IPFunctions ipFunctions = new IPFunctions();
							String ipHeader[] = new String[] { "ip", "Number", "Risk" };
								    mainsMd = new DefaultTableModel(null, ipHeader) {
										/**
										 *
										 */
										private static final long serialVersionUID = 4585202425202280069L;

										@Override
										public boolean isCellEditable(int row, int columm) {
											return false;
										}
									};
									tbMain.setModel(mainsMd);
							for (int i = 0; i < dataStore.getHits().size(); i++) {
								String key = dataStore.getHits().get(i).getiPaddr();
								HashMap<String, Integer> countMap = new HashMap<String, Integer>();
								if (countMap.containsKey(key)) {


								} else {
								
									Integer risk = analise.calulateRisk(key, dataStore, ipFunctions.getLocation(key));
									dataStore.addRisk(key, risk);
									
									System.out.println("row");
									Integer value = dataStore.getOrrcancesOfip().get(key);
									String vs = value.toString();
									countMap.put(key, value);
									mainsMd.addRow(new String[] {key, vs, risk.toString()});
									
									System.err.println(key+ vs+ risk.toString());
								}
								}
						}

					}
				});

		this.frmLogFileReader.setVisible(true);

	}

	/**
	 * @param timesHitIp
	 *            the timesHitIp to set
	 */
	public void setTimesHitIp(int timesHitIp) {
		this.timesHitIp = timesHitIp;
	}

	/**
	 * @param timesHitPages
	 *            the timesHitPages to set
	 */
	public void setTimesHitPages(int timesHitPages) {
		this.timesHitPages = timesHitPages;
	}


}