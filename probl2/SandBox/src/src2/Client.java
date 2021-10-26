package src2;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.JRadioButton;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;
import java.awt.event.ActionEvent;
import javax.swing.SpinnerNumberModel;
import java.awt.Color;
import javax.swing.JTextArea;

public class Client extends JFrame {

	private JPanel contentPane;
	private ButtonGroup grup_sold;
	private JButton btnDepunere;
	private JButton btnRetragere;
	private JButton btnLichidare;
	private JButton btnSchimbareCont;
	private JButton btnAfisareInfo;
	private JSpinner spinDepunere;
	private JSpinner spinRetragere;
	private JRadioButton rdbtnEuro;
	private JRadioButton rdbtnRon;
	private JLabel lblSold_Total_RON;
	private JLabel lblSold_Total_EURO;
	private JLabel lblTipCont;

	private int id_client = 0;
	private String nume;
	private String email;
	private String cnp;
	private String id_cont_ron;
	private String id_cont_euro;

	private String tip_cont;
	private float suma_totala_RON = 0.f;
	private float suma_totala_EURO = 0.f;
	private JButton btnOk;
	private JButton btnOk_1;
	private JLabel lblMaxim;
	private JTextArea textAreaInfo;
	private Login main;
	private Connection con;
	private Statement stmt;
	private JLabel lblNume;
	private String update_client = "";
	private PreparedStatement preparedStmt;
	
	private String info_message = "";

	public Client(JFrame frame, String tip_cont, Connection con, String email) throws ClassNotFoundException {
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);

		main = (Login) frame;
		setResizable(false);

		this.tip_cont = tip_cont;

		// preluare date din db
		try {

			Class.forName("com.mysql.jdbc.Driver");
			this.con = con;
			this.email = email;
			stmt = this.con.createStatement();
			String query = "SELECT * FROM clienti WHERE email='" + this.email + "';";
			ResultSet rs = stmt.executeQuery(query);
			if (rs.next()) {
				this.id_client = rs.getInt(1);
				cnp = rs.getString(4);
				nume = rs.getString(5);
				id_cont_ron = rs.getString(6);
				id_cont_euro = rs.getString(7);
				suma_totala_RON = rs.getFloat(8);
				suma_totala_EURO = rs.getFloat(9);

			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 557, 520);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		grup_sold = new ButtonGroup();
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("Bine ai venit");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel.setBounds(10, 10, 111, 29);
		contentPane.add(lblNewLabel);

		JLabel lblCont = new JLabel("CONT:");
		lblCont.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblCont.setBounds(336, 10, 82, 23);
		contentPane.add(lblCont);

		JButton btnNewButton = new JButton("IESIRE");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				iesire(e);
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnNewButton.setBounds(458, 438, 95, 44);
		contentPane.add(btnNewButton);

		JLabel lblSold = new JLabel("SOLD:");
		lblSold.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblSold.setBounds(336, 46, 83, 23);
		contentPane.add(lblSold);

		btnDepunere = new JButton("DEPUNERE");
		btnDepunere.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				depunere_sold(e);
			}
		});
		btnDepunere.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnDepunere.setBounds(10, 212, 141, 35);
		contentPane.add(btnDepunere);

		btnRetragere = new JButton("RETRAGERE");
		btnRetragere.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				retragere_sold(e);
			}
		});
		btnRetragere.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnRetragere.setBounds(10, 268, 141, 35);
		contentPane.add(btnRetragere);

		btnLichidare = new JButton("LICHIDARE");
		btnLichidare.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lichidare_cont(e);
			}
		});
		btnLichidare.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnLichidare.setBounds(10, 323, 141, 35);
		contentPane.add(btnLichidare);

		btnSchimbareCont = new JButton("SCHIMBARE CONT");
		btnSchimbareCont.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				schimbare_cont(e);
			}
		});
		btnSchimbareCont.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnSchimbareCont.setBounds(10, 167, 204, 35);
		contentPane.add(btnSchimbareCont);

		lblTipCont = new JLabel(this.tip_cont.toUpperCase());
		lblTipCont.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblTipCont.setBounds(416, 10, 83, 23);
		contentPane.add(lblTipCont);
		lblSold_Total_RON = new JLabel();
		lblSold_Total_RON.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblSold_Total_RON.setBounds(416, 43, 83, 23);
		contentPane.add(lblSold_Total_RON);

		lblSold_Total_EURO = new JLabel();
		lblSold_Total_EURO.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblSold_Total_EURO.setBounds(416, 43, 83, 23);
		contentPane.add(lblSold_Total_EURO);

		if (this.tip_cont.equalsIgnoreCase("ron")) {

			lblSold_Total_EURO.setText("");
			lblSold_Total_RON.setText(suma_totala_RON + "");

		} else if (this.tip_cont.equalsIgnoreCase("euro")) {

			lblSold_Total_RON.setText("");
			lblSold_Total_EURO.setText(suma_totala_EURO + "");
		}

		rdbtnRon = new JRadioButton("RON");
		rdbtnRon.setFont(new Font("Tahoma", Font.BOLD, 14));
		rdbtnRon.setBounds(220, 175, 70, 21);
		grup_sold.add(rdbtnRon);
		contentPane.add(rdbtnRon);

		rdbtnEuro = new JRadioButton("EURO");
		rdbtnEuro.setFont(new Font("Tahoma", Font.BOLD, 14));
		rdbtnEuro.setBounds(286, 175, 70, 21);
		grup_sold.add(rdbtnEuro);
		contentPane.add(rdbtnEuro);

		if (this.tip_cont.equalsIgnoreCase("ron")) {
			rdbtnRon.setSelected(true);
		} else if (this.tip_cont.equalsIgnoreCase("euro")) {
			rdbtnEuro.setSelected(true);
		}
		spinDepunere = new JSpinner();
		spinDepunere.setModel(new SpinnerNumberModel(new Float(0), new Float(0), new Float(1000), new Float(10)));

		spinDepunere.setEnabled(false);
		spinDepunere.setFont(new Font("Tahoma", Font.BOLD, 16));
		spinDepunere.setBounds(161, 215, 95, 35);
		contentPane.add(spinDepunere);

		spinRetragere = new JSpinner();
		spinRetragere.setModel(new SpinnerNumberModel(new Float(0), new Float(0), new Float(1000), new Float(10)));
		spinRetragere.setEnabled(false);
		spinRetragere.setFont(new Font("Tahoma", Font.BOLD, 16));
		spinRetragere.setBounds(161, 271, 95, 29);
		contentPane.add(spinRetragere);

		btnAfisareInfo = new JButton("AFISARE INFO");
		btnAfisareInfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				afisare_info();
			}
		});
		btnAfisareInfo.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnAfisareInfo.setBounds(10, 376, 141, 29);
		contentPane.add(btnAfisareInfo);

		btnOk = new JButton("OK");
		btnOk.setEnabled(false);
		btnOk.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnOk.setBounds(259, 214, 56, 35);
		contentPane.add(btnOk);

		btnOk_1 = new JButton("OK");
		btnOk_1.setEnabled(false);
		btnOk_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnOk_1.setBounds(259, 268, 56, 35);
		contentPane.add(btnOk_1);

		lblMaxim = new JLabel("");
		lblMaxim.setForeground(Color.RED);
		lblMaxim.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblMaxim.setBounds(161, 248, 130, 23);
		contentPane.add(lblMaxim);

		textAreaInfo = new JTextArea();
		textAreaInfo.setWrapStyleWord(true);
		textAreaInfo.setEnabled(true);
		textAreaInfo.setEditable(false);
		textAreaInfo.setLineWrap(true);
		textAreaInfo.setFont(new Font("Monospaced", Font.PLAIN, 12));
		textAreaInfo.setBounds(161, 310, 287, 172);

		contentPane.add(textAreaInfo);

		lblNume = new JLabel(nume);
		lblNume.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNume.setBounds(131, 10, 195, 29);
		contentPane.add(lblNume);

	}

	// +
	private void depunere_sold(ActionEvent e) {
		spinDepunere.setEnabled(true);
		btnOk.setEnabled(true);
		btnDepunere.setEnabled(false);
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				depunere_sold_clicked();
			}
		});

	}

	// +
	private void depunere_sold_clicked() {
		update_client = "";
		switch (tip_cont) {
		case "ron":

			if (Float.parseFloat(spinDepunere.getValue().toString()) > 1000) {
				lblMaxim.setText("Maxim!");
			} else
				lblMaxim.setText("");

			suma_totala_RON += Float.parseFloat(spinDepunere.getValue().toString());

			// db ron
			try {
				preparedStmt = con.prepareStatement("UPDATE clienti SET sold_ron='" + suma_totala_RON
						+ "' WHERE id_cont_ron='" + id_cont_ron + "';");
				preparedStmt.execute();

				update_client = "depunere " + Float.parseFloat(spinDepunere.getValue().toString()) + " RON";
				preparedStmt = con.prepareStatement("UPDATE banca_ SET update_client='" + update_client
						+ "' WHERE id_client='" + this.id_client + "'");
				preparedStmt.execute();
				ResultSet rs = stmt
						.executeQuery("SELECT sold_ron from clienti where id_cont_ron='" + id_cont_ron + "';");
				if (rs.next()) {
					lblSold_Total_RON.setText(rs.getFloat(1) + "");
				}

				rs.close();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			break;
		case "euro":
			if (Float.parseFloat(spinDepunere.getValue().toString()) > 1000) {
				lblMaxim.setText("Maxim!");
			} else
				lblMaxim.setText("");

			suma_totala_EURO += Float.parseFloat(spinDepunere.getValue().toString());

			try {
				preparedStmt = con.prepareStatement("UPDATE clienti SET sold_euro='" + suma_totala_EURO
						+ "' WHERE id_cont_euro='" + id_cont_euro + "';");
				preparedStmt.execute();

				update_client = "depunere " + Float.parseFloat(spinDepunere.getValue().toString()) + " EURO";
				preparedStmt = con.prepareStatement("UPDATE banca_ SET update_client='" + update_client
						+ "' WHERE id_client='" + this.id_client + "'");
				preparedStmt.execute();

				ResultSet rs = stmt
						.executeQuery("SELECT sold_euro from clienti where id_cont_euro='" + id_cont_euro + "';");
				if (rs.next()) {
					lblSold_Total_EURO.setText(rs.getFloat(1) + "");
				}

				rs.close();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			break;

		}
		btnDepunere.setEnabled(true);
		btnOk.setEnabled(false);
		spinDepunere.setValue(0);
		spinDepunere.setEnabled(false);
	}

	// -
	private void retragere_sold(ActionEvent e) {
		spinRetragere.setEnabled(true);
		btnOk_1.setEnabled(true);
		btnRetragere.setEnabled(false);
		btnOk_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				retragere_sold_clicked();
			}
		});
	}

	// -
	private void retragere_sold_clicked() {

		switch (tip_cont) {
		case "ron":
			if (Float.parseFloat(spinRetragere.getValue().toString()) > suma_totala_RON)
				lblMaxim.setText("Maxim!");
			else {
				if (Float.parseFloat(spinRetragere.getValue().toString()) > 1000) {
					lblMaxim.setText("Maxim!");
				} else
					lblMaxim.setText("");

				suma_totala_RON -= Float.parseFloat(spinRetragere.getValue().toString());
				// db ron

				try {
					preparedStmt = con.prepareStatement("UPDATE clienti SET sold_ron='" + suma_totala_RON + "' WHERE id_cont_ron='" + id_cont_ron + "';");
					preparedStmt.execute();

					update_client = "retragere "+ Float.parseFloat(spinRetragere.getValue().toString()) +" RON";
					preparedStmt = con.prepareStatement("UPDATE banca_ SET update_client='"+ update_client +"' WHERE id_client='"+ this.id_client+"'");
					preparedStmt.execute();
					
					ResultSet rs = stmt.executeQuery("SELECT sold_ron from clienti where id_cont_ron='" + id_cont_ron + "';");
					if (rs.next()) {
						lblSold_Total_RON.setText(rs.getFloat(1) + "");
					}

					rs.close();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}

			break;
		case "euro":
			if (Float.parseFloat(spinRetragere.getValue().toString()) > suma_totala_EURO) {
				lblMaxim.setText("Maxim!");
			} else {
				if (Float.parseFloat(spinRetragere.getValue().toString()) > 1000) {
					lblMaxim.setText("Maxim!");
				} else
					lblMaxim.setText("");

				suma_totala_EURO -= Float.parseFloat(spinRetragere.getValue().toString());
				// db euro
				try {
					preparedStmt = con.prepareStatement("UPDATE clienti SET sold_euro='" + suma_totala_EURO + "' WHERE id_cont_euro='" + id_cont_euro + "';");
					preparedStmt.execute();

					update_client = "retragere "+ Float.parseFloat(spinRetragere.getValue().toString()) +" EURO";
					preparedStmt = con.prepareStatement("UPDATE banca_ SET update_client='"+ update_client +"' WHERE id_client='"+ this.id_client+"'");
					preparedStmt.execute();
					
					ResultSet rs = stmt.executeQuery("SELECT sold_euro from clienti where id_cont_euro='" + id_cont_euro + "';");
					if (rs.next()) {
						lblSold_Total_EURO.setText(rs.getFloat(1) + "");
					}

					rs.close();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
			lblSold_Total_EURO.setText(suma_totala_EURO + "");
			break;

		}
		btnRetragere.setEnabled(true);
		btnOk_1.setEnabled(false);
		spinRetragere.setValue(0);
		spinRetragere.setEnabled(false);
	}

	// change
	private void schimbare_cont(ActionEvent e) {
		try {
			if (rdbtnRon.isSelected()) {

				lblTipCont.setText("ron".toUpperCase());
				this.tip_cont = "ron";
				lblSold_Total_EURO.setText("");
				ResultSet rs = stmt
						.executeQuery("SELECT sold_ron from clienti where id_cont_ron='" + id_cont_ron + "';");
				if (rs.next()) {
					lblSold_Total_RON.setText(rs.getFloat(1) + "");
				}

			} else if (rdbtnEuro.isSelected()) {
				lblTipCont.setText("euro".toUpperCase());
				this.tip_cont = "euro";
				lblSold_Total_RON.setText("");
				ResultSet rs = stmt
						.executeQuery("SELECT sold_euro from clienti where id_cont_euro='" + id_cont_euro + "';");
				if (rs.next()) {
					lblSold_Total_EURO.setText(rs.getFloat(1) + "");
				}

			}
		} catch (Exception ex) {

		}
	}

	private void afisare_info() {
		try {
			
			ResultSet rs = stmt.executeQuery("SELECT * from clienti where email='" + email + "';");
			if (rs.next()) {
				email = rs.getString(2);
				cnp = rs.getString(4);
				nume = rs.getString(5);
				id_cont_ron = rs.getString(6);
				id_cont_euro = rs.getString(7);
				suma_totala_RON = rs.getFloat(8);
				suma_totala_EURO = rs.getFloat(9);
			}

			info_message += "Nume:" + nume + "\r\n";
			info_message += "CNP:" + cnp + "\r\n";
			info_message += "Email:" + email + "\r\n";
			info_message += "ID_Cont_RON:" + id_cont_ron + "\r\n";
			info_message += "SOLD RON:" + suma_totala_RON + "\r\n";
			info_message += "ID_Cont_EURO:" + id_cont_euro + "\r\n";
			info_message += "SOLD EURO:" + suma_totala_EURO + "\r\n";

			textAreaInfo.setText(info_message);
		} catch (Exception ex) {

		}

	}

	// delete user
	private void lichidare_cont(ActionEvent e) {
		try {

			if (this.suma_totala_RON == 0 && this.suma_totala_EURO == 0) {

				int x = JOptionPane.showOptionDialog(null, "Doriti sa va lichidati conturile?", "Lichidare conturi...",
						JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, null, null);
				if (x == JOptionPane.YES_OPTION) {
					// stergere cont din bd

					preparedStmt = con.prepareStatement("DELETE FROM clienti WHERE email='" + this.email + "';");
					preparedStmt.execute();
					
					update_client = nume + " si-a lichidat conturile RON/EURO";
					preparedStmt = con.prepareStatement("UPDATE banca_ SET update_client='"+ update_client +"' WHERE id_client='"+ this.id_client+"'");
					preparedStmt.execute();
					
					JLabel messageLabel1 = new JLabel("Conturile au fost lichidate");
					JOptionPane.showConfirmDialog(null, messageLabel1, "Lichidare conturi!",
							JOptionPane.DEFAULT_OPTION);
					this.setVisible(false);
					this.dispose();
					main.setVisible(true);
				}

			}
		} catch (Exception ex) {

		}

	}

	// exit
	private void iesire(ActionEvent e) {
		this.setVisible(false);
		this.dispose();
		main.setVisible(true);
	}

	public String randomCode(int length) {
		String alphabet = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz"; // 9
		int n = alphabet.length(); // 10

		String result = new String();
		Random r = new Random(); // 11

		for (int i = 0; i < length; i++) // 12
		{
			result = result + alphabet.charAt(r.nextInt(n)); // 13
		}
		return result;
	}
}
