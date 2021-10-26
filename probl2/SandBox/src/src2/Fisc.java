package src2;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import src2.Fisc.NewClient;

import javax.swing.JComboBox;
import javax.swing.JRadioButton;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import java.awt.Font;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Fisc extends JFrame {

	private JPanel contentPane;

	private Statement stmt;
	private Connection con;
	private PreparedStatement preparedStmt;
	JButton btnCheck;
	JLabel lblNotificare;
	JTextArea textAreaInfo;
	JRadioButton rdbtnMonitorizare;
	JComboBox<NewClient> cmbBoxClienti;
	
	private String nume;
	private String email;
	private String cnp;
	private String id_cont_ron;
	private String id_cont_euro;
	private float suma_totala_RON = 0.f;
	private float suma_totala_EURO = 0.f;
	private int id_fisc = 0;
	private int id_client = 0;
	String info_message = "";
	String update_client = "";
	boolean monitor = false;
	
	public Fisc(JFrame modal,Connection con,String email,int id_fisc) {
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 552, 375);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		cmbBoxClienti = new JComboBox();
		cmbBoxClienti.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				change_state_combo(e);
			}
		});
		cmbBoxClienti.setFont(new Font("Tahoma", Font.PLAIN, 14));
		cmbBoxClienti.setBounds(123, 21, 176, 25);
		contentPane.add(cmbBoxClienti);
		
		rdbtnMonitorizare = new JRadioButton("Monitorizare");
		rdbtnMonitorizare.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				rdbtn_state_changed(e);
			}
		});
		rdbtnMonitorizare.setFont(new Font("Tahoma", Font.BOLD, 14));
		rdbtnMonitorizare.setBounds(318, 23, 137, 21);
		contentPane.add(rdbtnMonitorizare);
		
		JLabel lblNewLabel = new JLabel("Lista Clienti");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel.setBounds(10, 23, 103, 21);
		contentPane.add(lblNewLabel);
		
		textAreaInfo = new JTextArea();
		textAreaInfo.setEditable(false);
		textAreaInfo.setBounds(123, 70, 301, 187);
		contentPane.add(textAreaInfo);
		
		JLabel lblNewLabel_1 = new JLabel("Info Client");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_1.setBounds(10, 307, 103, 21);
		contentPane.add(lblNewLabel_1);
		
		lblNotificare = new JLabel("");
		lblNotificare.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNotificare.setBounds(123, 307, 345, 21);
		contentPane.add(lblNotificare);
		
		btnCheck = new JButton("\u2714");
		btnCheck.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnCheckClicked();
			}
		});
		btnCheck.setBounds(478, 307, 50, 25);
		contentPane.add(btnCheck);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);

		try {

			Class.forName("com.mysql.jdbc.Driver");
			this.con = con;
			stmt = this.con.createStatement();
			this.id_fisc = id_fisc;
			ResultSet rs = stmt.executeQuery("SELECT * from clienti;");
			DefaultComboBoxModel<NewClient> model = new DefaultComboBoxModel<NewClient>();
			   			  
			while (rs.next()) {
				 model.addElement(new NewClient(rs.getString(5)));
			}
			 cmbBoxClienti.setModel(model);
			rs.close();
			
			String getSelectedName = cmbBoxClienti.getSelectedItem().toString();
			rs = stmt.executeQuery("SELECT * FROM clienti where nume='"+ getSelectedName +"';");
			if(rs.next()) {
				id_client = rs.getInt(1);
				this.email = rs.getString(2);
				cnp = rs.getString(4);
				nume = rs.getString(5);
				id_cont_ron = rs.getString(6);
				id_cont_euro = rs.getString(7);
				suma_totala_RON = rs.getFloat(8);
				suma_totala_EURO = rs.getFloat(9);
				
			}
			rs.close();
			update_client = "";
			rs = stmt.executeQuery("SELECT * FROM banca_ WHERE id_fisc='" + this.id_fisc + "' and id_client='"+ this.id_client +"';");
			if(rs.next()) {
				
				monitor = rs.getBoolean(6);
				update_client = rs.getString(7);
			}
			if(monitor) {
				rdbtnMonitorizare.setSelected(true);
			}else {
				update_client = "";
				rdbtnMonitorizare.setSelected(false);
				btnCheck.setEnabled(false);
			}
			rs.close();
			lblNotificare.setText(update_client);
			
			info_message = "";
			textAreaInfo.setText(info_message);
			if(rdbtnMonitorizare.isSelected()) {
								
				info_message += "Nume:" + nume + "\r\n";
				info_message += "CNP:" + cnp + "\r\n";
				info_message += "Email:" + this.email + "\r\n";
				info_message += "ID_Cont_RON:" + id_cont_ron + "\r\n";
				info_message += "SOLD RON:" + suma_totala_RON + "\r\n";
				info_message += "ID_Cont_EURO:" + id_cont_euro + "\r\n";
				info_message += "SOLD EURO:" + suma_totala_EURO + "\r\n";
				
			}else {
				info_message ="Nu monitorizati acest client!";
				
			}
			
			textAreaInfo.setText(info_message);
			
		} catch (Exception ex) {

		}
										
	}

	private void change_state_combo(ItemEvent e) {		
		try {
				
			String getSelectedName = cmbBoxClienti.getSelectedItem().toString();
			ResultSet rs = stmt.executeQuery("SELECT * FROM clienti where nume='"+ getSelectedName +"';");
			if(rs.next()) {
				id_client = rs.getInt(1);
				email = rs.getString(2);
				cnp = rs.getString(4);
				nume = rs.getString(5);
				id_cont_ron = rs.getString(6);
				id_cont_euro = rs.getString(7);
				suma_totala_RON = rs.getFloat(8);
				suma_totala_EURO = rs.getFloat(9);
				
			}
			rs.close();
			update_client = "";
			rs = stmt.executeQuery("SELECT * FROM banca_ WHERE id_fisc='" + this.id_fisc + "' and id_client='"+ this.id_client + "';");
			if (rs.next()) {

				monitor = rs.getBoolean(6);
				update_client = rs.getString(7);
			}
			if (monitor) {
				rdbtnMonitorizare.setSelected(true);
			} else {
				update_client = "";
				btnCheck.setEnabled(false);
				rdbtnMonitorizare.setSelected(false);
			}
			rs.close();
			lblNotificare.setText(update_client);

			info_message = "";
			textAreaInfo.setText(info_message);
			
			if (rdbtnMonitorizare.isSelected()) {
											
				info_message += "Nume:" + nume + "\r\n";
				info_message += "CNP:" + cnp + "\r\n";
				info_message += "Email:" + this.email + "\r\n";
				info_message += "ID_Cont_RON:" + id_cont_ron + "\r\n";
				info_message += "SOLD RON:" + suma_totala_RON + "\r\n";
				info_message += "ID_Cont_EURO:" + id_cont_euro + "\r\n";
				info_message += "SOLD EURO:" + suma_totala_EURO + "\r\n";
				
			}else {
				info_message ="Nu monitorizati acest client!";
			}
			
			textAreaInfo.setText(info_message);
		}catch(Exception ex) {
			
		}		
				
	}
	private void rdbtn_state_changed(ItemEvent e) {
		try {
			btnCheck.setEnabled(true);		
			info_message = "";
			if(rdbtnMonitorizare.isSelected()) {
				textAreaInfo.setText(info_message);
				
				preparedStmt = con.prepareStatement("UPDATE banca_ SET id_fisc='"+ this.id_fisc+ "', monitorizare='1' WHERE id_client='"+ this.id_client +"';");
				preparedStmt.execute();
				
				info_message += "Nume:" + nume + "\r\n";
				info_message += "CNP:" + cnp + "\r\n";
				info_message += "Email:" + email + "\r\n";
				info_message += "ID_Cont_RON:" + id_cont_ron + "\r\n";
				info_message += "SOLD RON:" + suma_totala_RON + "\r\n";
				info_message += "ID_Cont_EURO:" + id_cont_euro + "\r\n";
				info_message += "SOLD EURO:" + suma_totala_EURO + "\r\n";

				textAreaInfo.setText(info_message);
			}else {
				
				preparedStmt = con.prepareStatement("UPDATE banca_ SET id_fisc='"+ this.id_fisc+ "', monitorizare='0' WHERE id_client='"+ this.id_client +"';");
				preparedStmt.execute();
				
				info_message ="Nu monitorizati acest client!";
			}
									
			textAreaInfo.setText(info_message);
		}catch(Exception ex) {
			
		}	
	}
	private void btnCheckClicked() {
		try {
			
			preparedStmt = con.prepareStatement("UPDATE banca_ SET check_fisc='1' WHERE id_client='"+ this.id_client +"'");
			preparedStmt.execute();
			update_client = "";
			lblNotificare.setText(update_client);
			btnCheck.setEnabled(false);
			
		}catch(Exception ex) {
			
		}
	}
	class NewClient {
		
		private String value;

	    public NewClient(String value) {
	        this.value = value;
	       
	    }
	    
	    @Override
	    public String toString() {
	    	return this.value + "";
	    }
	}
}
