import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class TicTacToe implements ActionListener{

	private static String [][] grid = {{"", "", ""},
									   {"", "", ""},
									   {"", "", ""}};
	private static int turnCount = 1;

	private JFrame frame;
	private JPanel panel;
	private static JButton [][] spot = new JButton[3][3];
	private static JLabel [][] filled = new JLabel[3][3];
	private static JButton newGame = new JButton("New Game");
	private static JLabel header = new JLabel("");

	public TicTacToe(){
		frame = new JFrame("TIC TAC TOE");
		panel = new JPanel();
		frame.getContentPane();

		newGame.setBounds(380, 215, 95, 30);
		newGame.setBackground(Color.black);
		newGame.setForeground(Color.white);
		newGame.addActionListener(this);
		panel.add(newGame);
		
		int x = 135;
		int y = 50;
		
		for (int i = 0; i < 3; i++){
			x = 135;
			for (int j = 0; j < 3; j++){
				spot[i][j] = new JButton("");
				spot[i][j].setBounds(x, y, 45, 45);
				filled[i][j] = new JLabel("");
				filled[i][j].setBounds(x+17, y, 45, 45);
				x+=45;
			}
			y+=45;
		}

		order();
		
		panel.setLayout(null);
		for (int i = 0; i < 3; i++){
			for (int j = 0; j < 3; j++){
				spot[i][j].addActionListener(this);
				spot[i][j].setBackground(Color.black);
				panel.add(spot[i][j]);
				panel.add(filled[i][j]);
			}
		}	

		panel.add(header);
		panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(panel);
		frame.setSize(500, 300);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}	

	public static void add(String symbol, int row, int column){
		grid[row][column] = symbol;
		spot[row][column].setVisible(false);
		filled[row][column].setText(symbol);	
	}
	
	public static void order(){
		Random randy = new Random();
		int turn = randy.nextInt(2);
		
		if (turn == 0){
			add("X", 0, 0);
			header.setText("YOU ARE GOING SECOND");
			header.setBounds(135, -45, 150, 150);
			turnCount++;
		}else{
			header.setText("YOU ARE GOING FIRST");
			header.setBounds(140, -45, 150, 150);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == newGame){
			for (int i = 0; i < 3; i++){
				for (int j = 0; j < 3; j++){
					grid[i][j] = "";
					spot[i][j].setVisible(true);
					filled[i][j].setText("");
					filled[i][j].setForeground(Color.black);
				}
			}
			turnCount = 1;
			header.setForeground(Color.black);
			order();
			return;
		}

		for (int i = 0; i < 3; i++){
			for (int j = 0; j < 3; j++){
				if (e.getSource() == spot[i][j]){
					add("O", i, j);
				}
			}
		}

		if (turnCount == 9){
			header.setBounds(192, -45, 150, 150);
			header.setText("TIE");
			header.setForeground(Color.green);
		}

		bestMove();

		if (isWin()){
			header.setBounds(175, -45, 150, 150);
			header.setText("YOU LOSE");
			header.setForeground(Color.red);
			for (int i = 0; i < 3; i++){
				for (int j = 0; j < 3; j++){
					spot[i][j].setVisible(false);
				}
			}
		}else if (turnCount == 9){
			header.setBounds(192, -45, 150, 150);
			header.setText("TIE");
			header.setForeground(Color.green);
		}
		turnCount++;
	}

	public static void bestMove(){
		turnCount++;
		if (turnCount == 1){
			add("X", 0, 0);
			
		}else if (turnCount == 2){
			if (grid[1][1].equals("O")){
				add("X", 0, 0);
			}else{
				add("X", 1, 1);
			}

		}else if (turnCount == 3){
			if (grid[1][1].equals("O")){
				add("X", 2, 2);
			}else if (grid[0][1].equals("O") || grid[0][2].equals("O") || grid[2][1].equals("O")){
				add("X", 2, 0);
			}else{
				add("X", 0, 2);
			}

			
		}else if (turnCount == 4){
			if (canWin("O")){
				return;
			}else if (grid[1][1].equals("O") && grid[2][2].equals("O")){
				add("X", 0, 2);
			}else if ((grid[0][0].equals("O") && grid[2][2].equals("O")) || (grid[0][2].equals("O")) && grid[2][0].equals("O")){
				add("X", 0, 1);

			}else if(grid[0][0].equals("") && grid[1][0].equals("O") && grid[0][1].equals("O")){
				add("X", 0, 0);
			}else if(grid[0][2].equals("") && grid[1][2].equals("O") && grid[0][1].equals("O")){
				add("X", 0, 2);
			}else if(grid[2][0].equals("") && grid[1][0].equals("O") && grid[2][1].equals("O")){
				add("X", 2, 0);
			}else if(grid[2][2].equals("") && grid[1][2].equals("O") && grid[2][1].equals("O")){
				add("X", 2, 2);

			}else if(grid[0][0].equals("") && (grid[1][0].equals("O") || grid[0][1].equals("O"))){
				add("X", 0, 0);
			}else if(grid[0][2].equals("") && (grid[1][2].equals("O") || grid[0][1].equals("O"))){
				add("X", 0, 2);
			}else if(grid[2][0].equals("") && (grid[1][0].equals("O") || grid[2][1].equals("O"))){
				add("X", 2, 0);
			}else if(grid[2][2].equals("") && (grid[1][2].equals("O") || grid[2][1].equals("O"))){
				add("X", 2, 2);
			}
			
		}else if (turnCount == 5){
			if (canWin("X")){
				return;
			}else if (canWin("O")){
				return;
			}else if (grid[0][2].equals("") && grid[1][2].equals("") && grid[0][1].equals("")){
				add("X", 0, 2);
			}else if (grid[2][2].equals("") && grid[1][2].equals("") && grid[2][1].equals("")){
				add("X", 2, 2);
			}else if (grid[2][0].equals("") && grid[2][1].equals("") && grid[1][0].equals("")){
				add("X", 2, 0);
			}

		}else if (turnCount == 6){
			if (canWin("X")){
				return;
			}else if (canWin("O")){
				return;
			}else if(grid[0][1].equals("") && grid[1][1].equals("X") && grid[2][1].equals("")){
				add("X", 0, 1);
			}else if(grid[1][0].equals("") && grid[1][1].equals("X") && grid[1][2].equals("")){
				add("X", 1, 2);
			}else if(grid[0][0].equals("")){
				add("X", 0, 0);
			}else if(grid[0][2].equals("")){
				add("X", 0, 2);
			}else if(grid[2][0].equals("")){
				add("X", 2, 0);
			}else if(grid[2][2].equals("")){
				add("X", 2, 2);
			}else{
				for (int i = 0; i < 3; i++){
					for (int j = 0; j < 3; j++){
						if (grid[i][j].equals("")){
							add("X", i, j);
							return;
						}
					}
				}
			}

		}else if (turnCount == 7){
			if (canWin("X")){
				return;
			}else if (canWin("O")){
				return;
			}

		}else if (turnCount == 8){
			if (canWin("X")){
				return;
			}else if (canWin("O")){
				return;
			}else{
				for (int i = 0; i < 3; i++){
					for (int j = 0; j < 3; j++){
						if (grid[i][j].equals("")){
							add("X", i, j);
							return;
						}
					}
				}
			}

		}else if (turnCount == 9){
			for (int i = 0; i < 3; i++){
				for (int j = 0; j < 3; j++){
					if (grid[i][j].equals("")){
						add("X", i, j);
						return;
					}
				}
			}
		}
	}
	
	public static boolean canWin(String s){
		//Horizontal / Vertical 1st
		for (int i = 0; i < 3; i++){
			if (grid[i][0].equals("") && grid[i][1].equals(s) && grid[i][2].equals(s)){
				add("X", i, 0);
				return true;
			}else if (grid[0][i].equals("") && grid[1][i].equals(s) && grid[2][i].equals(s)){
				add("X", 0, i);
				return true;
			}
		}
		//Horizontal / Vertical 2nd
		for (int i = 0; i < 3; i++){
			if (grid[i][0].equals(s) && grid[i][1].equals("") && grid[i][2].equals(s)){
				add("X", i, 1);
				return true;
			}else if (grid[0][i].equals(s) && grid[1][i].equals("") && grid[2][i].equals(s)){
				add("X", 1, i);
				return true;
			}
		}
		//Horizontal / Vertical 3rd
		for (int i = 0; i < 3; i++){
			if (grid[i][0].equals(s) && grid[i][1].equals(s) && grid[i][2].equals("")){
				add("X", i, 2);
				return true;
			}else if (grid[0][i].equals(s) && grid[1][i].equals(s) && grid[2][i].equals("")){
				add("X", 2, i);
				return true;
			}
		}
		//Diagonal1
		if (grid[0][0].equals("") && grid[1][1].equals(s) && grid[2][2].equals(s)){
			add("X", 0, 0);
			return true;
		}else if (grid[0][0].equals(s) && grid[1][1].equals("") && grid[2][2].equals(s)){
			add("X", 1, 1);
			return true;
		}else if (grid[0][0].equals(s) && grid[1][1].equals(s) && grid[2][2].equals("")){
			add("X", 2, 2);
			return true;
		}
		//Diagonal2
		if (grid[0][2].equals("") && grid[1][1].equals(s) && grid[2][0].equals(s)){
			add("X", 0, 2);
			return true;
		}else if (grid[0][2].equals(s) && grid[1][1].equals("") && grid[2][0].equals(s)){
			add("X", 1, 1);
			return true;
		}else if (grid[0][2].equals(s) && grid[1][1].equals(s) && grid[2][0].equals("")){
			add("X", 2, 0);
			return true;
		}
		return false;
	}

	public static boolean isWin(){
		for (int i = 0; i < 3; i++){
			if (grid[i][0].equals("X") && grid[i][1].equals("X") && grid[i][2].equals("X")){
				filled[i][0].setForeground(Color.red);
				filled[i][1].setForeground(Color.red);
				filled[i][2].setForeground(Color.red);
				return true;
			}else if (grid[0][i].equals("X") && grid[1][i].equals("X") && grid[2][i].equals("X")){
				filled[0][i].setForeground(Color.red);
				filled[1][i].setForeground(Color.red);
				filled[2][i].setForeground(Color.red);
				return true;
			}
		}
		if (grid[0][0].equals("X") && grid[1][1].equals("X") && grid[2][2].equals("X")){
			filled[0][0].setForeground(Color.red);
			filled[1][1].setForeground(Color.red);
			filled[2][2].setForeground(Color.red);
			return true;
		}else if(grid[2][0].equals("X") && grid[1][1].equals("X") && grid[0][2].equals("X")){
			filled[0][2].setForeground(Color.red);
			filled[1][1].setForeground(Color.red);
			filled[2][0].setForeground(Color.red);
			return true;
		}
		return false;
	}

	public static void main (String [] args){
		new TicTacToe();
	}
}
