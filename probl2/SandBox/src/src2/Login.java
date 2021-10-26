package src2;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.JRadioButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JPasswordField;

public class Login extends JFrame {

	private JPanel contentPane;
	private JTextField textFieldEmail;
	private ButtonGroup grupSold;
	private JLabel lblError;
	private JRadioButton radioBtnRON;
	private JRadioButton radioBtnEURO;
	
	private String email = "";
	private String parola ="";
	private String tip_cont = "";
	
	private JPasswordField passwordField;
	private JRadioButton rdbtnFisc;
	private Statement stmt;
	private Connection con;
	private int id_fisc = 0;
	public Login() throws ClassNotFoundException, SQLException {
		
		//db
		Class.forName("com.mysql.jdbc.Driver");
		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/banca","root","root");
		stmt = con.createStatement(); 
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 416, 404);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
		
		grupSold = new ButtonGroup();
		
		JLabel lblNewLabel = new JLabel("Email");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel.setBounds(22, 91, 70, 42);
		contentPane.add(lblNewLabel);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					login();
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnLogin.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnLogin.setBounds(101, 210, 97, 40);
		contentPane.add(btnLogin);
		
		textFieldEmail = new JTextField();
		textFieldEmail.setFont(new Font("Tahoma", Font.PLAIN, 12));
		textFieldEmail.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				if(!textFieldEmail.getText().equals("") ) {
					lblError.setText("");
					email = textFieldEmail.getText();
				}else {
					lblError.setText("Email gresit!");
				}
			}
		});
		textFieldEmail.setBounds(101, 99, 222, 31);
		contentPane.add(textFieldEmail);
		textFieldEmail.setColumns(10);
		
		JButton btnCreareCont = new JButton("Creare cont");
		btnCreareCont.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					creareCont();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnCreareCont.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnCreareCont.setBounds(208, 210, 131, 42);
		contentPane.add(btnCreareCont);
		
		JLabel lblNewLabel_1 = new JLabel("Parola");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel_1.setBounds(22, 143, 70, 42);
		contentPane.add(lblNewLabel_1);
		
		radioBtnRON = new JRadioButton("RON");
		radioBtnRON.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(radioBtnRON.isSelected()) {
					tip_cont = "ron";
				}else {
					tip_cont = "";
				}
			}
		});
		radioBtnRON.setFont(new Font("Tahoma", Font.BOLD, 14));
		radioBtnRON.setBounds(329, 102, 103, 21);
		grupSold.add(radioBtnRON);
		contentPane.add(radioBtnRON);
		
		radioBtnEURO = new JRadioButton("EURO");
		radioBtnEURO.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(radioBtnEURO.isSelected()) {
					tip_cont = "euro";
				}else {
					tip_cont = "";
				}
			}
		});
		radioBtnEURO.setFont(new Font("Tahoma", Font.BOLD, 14));
		radioBtnEURO.setBounds(329, 154, 103, 21);
		grupSold.add(radioBtnEURO);
		contentPane.add(radioBtnEURO);
		
		
		lblError = new JLabel("");
		lblError.setForeground(Color.RED);
		lblError.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblError.setBounds(101, 192, 222, 13);
		contentPane.add(lblError);
		
		passwordField = new JPasswordField();
		passwordField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				if(!passwordField.getText().equals("") ) {
					lblError.setText("");
					parola = passwordField.getText();
				}else {
					lblError.setText("Parola gresita!");
				}
			}
		});
		passwordField.setFont(new Font("Tahoma", Font.PLAIN, 12));
		passwordField.setBounds(101, 143, 222, 33);
		contentPane.add(passwordField);
		
		rdbtnFisc = new JRadioButton("FISC");
		rdbtnFisc.setFont(new Font("Tahoma", Font.BOLD, 14));
		rdbtnFisc.setBounds(22, 220, 70, 21);
		contentPane.add(rdbtnFisc);
		
	}
	
	private  void creareCont() throws Exception {
		CreareCont contnou = new CreareCont(this,con);
		contnou.setVisible(true);
		this.setVisible(false);
	}
	
	private  void login() throws ClassNotFoundException {	
		try {
			
			
			String em="",pass="";
			ResultSet rs;
			
			if((radioBtnEURO.isSelected() || radioBtnRON.isSelected()) &&  !rdbtnFisc.isSelected()) {
				rs = stmt.executeQuery("SELECT email,parola FROM clienti where email='" + email + "';");
				if (rs.next()) {

					em = rs.getString(1);
					pass = rs.getString(2);
				}
				rs.close();
				if(!em.equals("") && !pass.equals("")) {
					if (email.equals(em) && parola.equals(pass) && (radioBtnEURO.isSelected() || radioBtnRON.isSelected())) {
						Client client = new Client(this, tip_cont, con, email);
						client.setVisible(true);
						this.setVisible(false);
					}else {
						lblError.setText("Nu aveti cont!");
					}
				}							 
			}
			if (rdbtnFisc.isSelected()) {
				
				rs = stmt.executeQuery("SELECT idfisc,email,parola FROM fisc where email='"+ email +"' and parola='"+ parola +"';");
				if(rs.next()) {
					id_fisc = rs.getInt(1);
					em = rs.getString(2);
					pass = rs.getString(3);
				}
				rs.close();
				
				if(email.equals(em) && parola.equals(pass)) {
					Fisc fisc = new Fisc(this,con,email,id_fisc);
					fisc.setVisible(true);
					this.setVisible(false);
				}else {
					lblError.setText("Nu aveti cont!");
				}
			}
						
		}catch(Exception ex) {
			
		}
		
		
	}
}
