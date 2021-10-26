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
import javax.swing.JButton;
import javax.swing.JRadioButton;
import java.awt.Font;
import javax.swing.JTextField;
import java.awt.Cursor;
import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Random;

public class CreareCont extends JFrame {

	private JPanel contentPane;
	private JTextField txtFieldNume;
	private JTextField txtFieldCNP;
	private JTextField txtFieldEmail;
	private JPasswordField passwordField;
	private JPasswordField passwordField_1;
	private JButton btnAnulare;
	private Login main;	
	private JLabel lblNume;
	private JLabel lblCNP;
	private JLabel lblEmail;
	private JLabel lblParola;
	private JLabel lblRepetareParola;
	private JButton btnCreareCont;
	
	private String nume = "";
	private String cnp = "";
	private String email = "";
	private String parola = "";
	private String r_parola = "";
	private Connection con;	
	private PreparedStatement preparedStmt;
	private Statement stmt;
	
	public CreareCont(JFrame modal,Connection con) throws Exception {
		Class.forName("com.mysql.jdbc.Driver");
		main = (Login)modal;
		this.con = con;
		stmt = this.con.createStatement(); 
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 517, 440);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Nume");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel.setBounds(124, 24, 59, 29);
		contentPane.add(lblNewLabel);
		
		btnCreareCont = new JButton("CREARE CONT");
		btnCreareCont.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					creare_cont();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		btnCreareCont.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnCreareCont.setBounds(93, 348, 150, 37);
		contentPane.add(btnCreareCont);
		
		btnAnulare = new JButton("ANULARE");
		btnAnulare.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				anulare();
			}
		});
		btnAnulare.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnAnulare.setBounds(380, 348, 111, 37);
		contentPane.add(btnAnulare);
		
		JRadioButton rdbtnNewRadioButton = new JRadioButton("RON");
		rdbtnNewRadioButton.setToolTipText("Se activeaza ambele conturi");
		rdbtnNewRadioButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		rdbtnNewRadioButton.setEnabled(false);
		rdbtnNewRadioButton.setSelected(true);
		rdbtnNewRadioButton.setFont(new Font("Tahoma", Font.BOLD, 14));
		rdbtnNewRadioButton.setBounds(225, 296, 59, 21);
		contentPane.add(rdbtnNewRadioButton);
		
		JLabel lblNewLabel_1 = new JLabel("CNP");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_1.setBounds(135, 75, 48, 29);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Email");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_2.setBounds(124, 125, 59, 29);
		contentPane.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("Parola");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_3.setBounds(124, 183, 59, 29);
		contentPane.add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("Repetare parola");
		lblNewLabel_4.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_4.setBounds(63, 241, 120, 29);
		contentPane.add(lblNewLabel_4);
		
		JLabel lblNewLabel_5 = new JLabel("Tip cont");
		lblNewLabel_5.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_5.setBounds(115, 292, 68, 29);
		contentPane.add(lblNewLabel_5);
		
		txtFieldNume = new JTextField();
		txtFieldNume.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				if(!txtFieldNume.getText().equals("")) {
					lblNume.setText("");
					nume = txtFieldNume.getText();
				}else {
					lblNume.setText("Camp gol!");
				}
			}
		});
		txtFieldNume.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtFieldNume.setBounds(209, 26, 242, 26);
		contentPane.add(txtFieldNume);
		txtFieldNume.setColumns(10);
		
		txtFieldCNP = new JTextField();
		txtFieldCNP.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char ch = e.getKeyChar();
				if(!Character.isDigit(ch)) {
					e.consume();
				}
			}
		});
		txtFieldCNP.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
//				txtFieldCNP.getText().length() > 13 || txtFieldCNP.getText().length() < 13 ||
				if( txtFieldCNP.getText().equals("") ) {
					lblCNP.setText("CNP invalid!");
				}else {
					lblCNP.setText("");
					cnp = txtFieldCNP.getText();
				}
			}
		});
		txtFieldCNP.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtFieldCNP.setColumns(10);
		
		txtFieldCNP.setBounds(209, 78, 242, 26);
		contentPane.add(txtFieldCNP);
		
		txtFieldEmail = new JTextField();
		txtFieldEmail.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				if(!txtFieldEmail.getText().equals("")) {
					lblEmail.setText("");
					email = txtFieldEmail.getText();
				}else {
					lblEmail.setText("Email invalid!");
				}
			}
		});
		txtFieldEmail.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtFieldEmail.setColumns(10);
		txtFieldEmail.setBounds(209, 132, 242, 26);
		contentPane.add(txtFieldEmail);
		
		JRadioButton rdbtnEuro = new JRadioButton("EURO");
		rdbtnEuro.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		rdbtnEuro.setEnabled(false);
		rdbtnEuro.setSelected(true);
		rdbtnEuro.setFont(new Font("Tahoma", Font.BOLD, 14));
		rdbtnEuro.setBounds(289, 296, 103, 21);
		contentPane.add(rdbtnEuro);
		
		passwordField = new JPasswordField();
		passwordField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				if(!passwordField.getText().equals("") && passwordField.getText().equals(passwordField_1.getText())) {
					lblParola.setText("");
					lblRepetareParola.setText("");
					
				}else {
					lblParola.setText("Parolele nu corespund!");
				}
			}
		});
		passwordField.setFont(new Font("Tahoma", Font.BOLD, 14));
		passwordField.setBounds(210, 190, 242, 22);
		contentPane.add(passwordField);
		
		passwordField_1 = new JPasswordField();
		passwordField_1.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				if(!passwordField_1.getText().equals("") && passwordField_1.getText().equals(passwordField.getText())) {
					lblRepetareParola.setText("");
					lblParola.setText("");
					parola = passwordField.getText();
				}else{
					lblRepetareParola.setText("Parolele nu corespund!");
				}
			}
		});
		passwordField_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		passwordField_1.setBounds(209, 248, 242, 22);
		contentPane.add(passwordField_1);
		
		lblNume = new JLabel("");
		lblNume.setForeground(Color.RED);
		lblNume.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNume.setBounds(209, 55, 242, 13);
		contentPane.add(lblNume);
		
		lblCNP = new JLabel("");
		lblCNP.setForeground(Color.RED);
		lblCNP.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblCNP.setBounds(209, 109, 242, 13);
		contentPane.add(lblCNP);
		
		lblEmail = new JLabel("");
		lblEmail.setForeground(Color.RED);
		lblEmail.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblEmail.setBounds(209, 168, 242, 13);
		contentPane.add(lblEmail);
		
		lblParola = new JLabel("");
		lblParola.setForeground(Color.RED);
		lblParola.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblParola.setBounds(209, 225, 242, 13);
		contentPane.add(lblParola);
		
		lblRepetareParola = new JLabel("");
		lblRepetareParola.setForeground(Color.RED);
		lblRepetareParola.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblRepetareParola.setBounds(209, 280, 242, 13);
		contentPane.add(lblRepetareParola);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
	}
	private void anulare() {
		this.setVisible(false);
		this.dispose();
		main.setVisible(true);
	}
	
	private  void creare_cont() throws SQLException, ClassNotFoundException {
		if (lblNume.getText().equals("") && lblCNP.getText().equals("") && lblEmail.getText().equals("")
				&& lblParola.getText().equals("") && lblRepetareParola.getText().equals("")) {
			
			//db	
			String cont_ron = "RO" + randomCode(8);
			String cont_euro ="EU" + randomCode(8);
			String query = "INSERT INTO clienti(email,parola,cnp,nume,id_cont_ron,id_cont_euro,sold_ron,sold_euro) VALUES(?,?,?,?,?,?,?,?)";
			preparedStmt = con.prepareStatement(query);
			preparedStmt.setString(1, email);
			preparedStmt.setString(2, parola);
			preparedStmt.setString(3, cnp);
			preparedStmt.setString(4, nume);
			preparedStmt.setString(5,cont_ron);
			preparedStmt.setString(6,cont_euro);
			preparedStmt.setFloat(7, 0);
			preparedStmt.setFloat(8, 0);
			
			preparedStmt.execute();
			
			ResultSet rs1 = stmt.executeQuery("SELECT idclienti FROM clienti WHERE email='"+ email +"';");
			int id_client = 0;
			if(rs1.next()) {
				id_client = rs1.getInt(1);
			}
			
			query = "INSERT INTO banca_(id_client,id_cont_ron,id_cont_euro) VALUES(?,?,?);";
			preparedStmt = con.prepareStatement(query);
			preparedStmt.setInt(1, id_client);
			preparedStmt.setString(2, cont_ron);
			preparedStmt.setString(3, cont_euro);
			
			preparedStmt.execute();
			
			JLabel messageLabel1 = new JLabel("Ati creat cont nou!");
			JOptionPane.showConfirmDialog(null, messageLabel1, "Cont creat!", JOptionPane.DEFAULT_OPTION);
			Client client = new Client(main, "ron",con,email);
			this.setVisible(false);
			this.dispose();
			client.setVisible(true);

		}
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
