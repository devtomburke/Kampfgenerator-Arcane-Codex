import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class Kampfgenerator {

	private JFrame frame;

	private int INIT_LINE_NUMBER = 10;
	private static int WIDTH = 40;
	private static int HEIGHT = 20;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Kampfgenerator window = new Kampfgenerator();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * 
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 */
	public Kampfgenerator() throws IllegalArgumentException, IllegalAccessException {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 * 
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 */
	private void initialize() throws IllegalArgumentException, IllegalAccessException {
		List<EnemyData> enemyList = new ArrayList<EnemyData>();
		for (int i = 0; i < INIT_LINE_NUMBER; i++) {
			enemyList.add(
					EnemyData.create("txtName" + i, 14, 14, 14, 30, 4, 0, 5, "2w10+5", false, false, "Angeschlagen"));
		}

		buildFrame();
		setLabels();
		List<EnemyView> enemyViewList = new ArrayList<EnemyView>();
		for (int i = 0; i < INIT_LINE_NUMBER; i++) {
			EnemyView v = EnemyView.create(10, 41 + i * 31, HEIGHT, WIDTH, enemyList.get(i));
			enemyViewList.add(v);
			v.addToView(frame);
		}

		JButton btnInitiative = new JButton("Initiative");
		btnInitiative.setToolTipText("<html>Würfelt die Initiative aus.<br>" + "Negative Werte sind möglich.<br>"
				+ "Der Wundgrad beeinflusst den Wurf.<br>" + "2w10+Initiative-Verwundung<br>"
				+ "Farbcode:<br>Rot=kritischer Fehlschlag<br>" + "Gelb=automatischer Fehlschlag<br>"
				+ "Türkis=automatischer Treffer<br>" + "Grün=kritischer Treffer</html>");
		btnInitiative.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Random rand = new Random(System.currentTimeMillis());
				for (EnemyView view : enemyViewList) {
					int wurfEins = rand.nextInt(10) + 1 + view.getData().initiative;
					int wurfZwei = rand.nextInt(10) + 1;

					JTextField tf = (JTextField) view.getComponentList().get(13);
					tf.setBackground(Color.WHITE);
					tf.setText(String.valueOf(wurfEins + wurfZwei));
				}

			}
		});
		btnInitiative.setBounds(885, 35, 89, 23);
		frame.getContentPane().add(btnInitiative);

		JButton btnAngriff = new JButton("Angriff");
		btnAngriff.setToolTipText("<html>Würfelt den Angriff aus.<br>" + "Negative Werte sind möglich.<br>"
				+ "Der Wundgrad beeinflusst den Wurf.<br>" + "2w10+Angriff-Verwundung<br>"
				+ "Farbcode:<br>Rot=kritischer Fehlschlag<br>" + "Gelb=automatischer Fehlschlag<br>"
				+ "Türkis=automatischer Treffer<br>" + "Grün=kritischer Treffer</html>");
		btnAngriff.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg1) {
				Random rand = new Random(System.currentTimeMillis());
				for (EnemyView view : enemyViewList) {
					int wurfEins = rand.nextInt(10) + 1;
					int wurfZwei = rand.nextInt(10) + 1;
					int wurf = wurfEins + wurfZwei;
					int gesamt = wurfEins + wurfZwei + view.getData().angriff;

					JTextField tf = (JTextField) view.getComponentList().get(13);
					if (wurf == 2) {
						tf.setBackground(Color.RED);
					} else if (wurf == 3) {
						tf.setBackground(Color.YELLOW);
					} else if (wurf == 19) {
						tf.setBackground(Color.BLUE);
					} else if (wurf == 20) {
						tf.setBackground(Color.GREEN);
					} else {
						tf.setBackground(Color.WHITE);
					}
					tf.setText(String.valueOf(gesamt));
				}
			}
		});
		btnAngriff.setBounds(885, 69, 89, 23);
		frame.getContentPane().add(btnAngriff);

		JButton btnSchaden = new JButton("Schaden");
		btnSchaden.setToolTipText("<html>Würfelt den angegebenden Waffenschaden aus.<br> "
				+ "Ein Malus kann nicht verwendet werden.<br> "
				+ "Die Angabe erfolgt wie folgt: Anzahl der Würfel(n)Würfelart(w10 oder w5)+Bonus<br> "
				+ "BSP:2w10+4</html>");
		btnSchaden.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg2) {
				Random rand = new Random(System.currentTimeMillis());
				for (EnemyView view : enemyViewList) {
					String schaden = view.getData().schaden.replace("+", "P").replace("-", "M").toUpperCase();
					String[] dmgSplit = schaden.split("\\D");
					int dmg = 0;
					for (int i = 0; i < Integer.parseInt(dmgSplit[0]); i++) {
						dmg += rand.nextInt(Integer.parseInt(dmgSplit[1])) + 1;
					}
					if (schaden.contains("P")) {
						dmg += Integer.parseInt(dmgSplit[2]);
					} else {
						dmg -= Integer.parseInt(dmgSplit[2]);
					}
					JTextField tf = (JTextField) view.getComponentList().get(13);
					tf.setText(String.valueOf(dmg));
				}
			}
		});
		btnSchaden.setBounds(885, 103, 89, 23);
		frame.getContentPane().add(btnSchaden);

		// JButton btnScSchaden = new JButton("SC Schaden");
		// btnScSchaden.setToolTipText("<html>Verrechnet automatisch den
		// eingegebenden Schaden.<br>"
		// + "Lebenspunkte aktuell-Schaden+RS<br>"
		// + "Der minimale Schaden beträgt 0.<br>"
		// + "Der Verwundungsgrad wird automatisch mit eingerechnet.<br> "
		// + "Der Würfelwurf wird aktualisiert bei neuem
		// Verwundungsgrad.</html>");
		// btnScSchaden.addActionListener(new ActionListener() {
		// public void actionPerformed(ActionEvent arg3) {
		// int i,LP,LPa,DMG,Wurf;
		// String Zustand1,Zustand2;
		//
		// for(i=0;i<22;i++){
		// DMG =
		// Integer.parseInt(strInputSCDMG[i].getText())-Integer.parseInt(strInputRS[i].getText());
		// LPa = Integer.parseInt(strInputLPa[i].getText());
		// LP = Integer.parseInt(strInputLP[i].getText());
		// Wurf = Integer.parseInt(strInputWurf[i].getText());
		// Zustand1 =strInputZustand[i].getText();
		// Zustand2 ="Angeschlagen";
		//
		// if(DMG<1)
		// {
		// DMG = 0;
		// }
		//
		// LPa=LPa-DMG;
		//
		// if(LPa>=(LP/6*5))
		// {
		// Zustand2="Angeschlagen";
		// }
		// else if(LPa>=(LP/6*4))
		// {
		// Zustand2="Verletzt";
		// }
		// else if(LPa>=(LP/6*3))
		// {
		// Zustand2="Verwundet";
		// }
		// else if(LPa>=(LP/6*2))
		// {
		// Zustand2="Schwer verwundet";
		// }
		// else if(LPa>=(LP/6))
		// {
		// Zustand2="Außer Gefecht";
		// }
		// else if(LPa<(LP/6) && LPa>=0)
		// {
		// Zustand2="Koma";
		// }
		// else if(LPa<=0)
		// {
		// Zustand2="Tod";
		// }
		//
		// if(Zustand1.equals(Zustand2))
		// {
		//
		// }
		// else if((Zustand1.equals("Angeschlagen")) &&
		// (Zustand2.equals("Verletzt")))
		// {
		// Wurf=Wurf-2;
		// }
		// else if((Zustand1.equals("Angeschlagen")) &&
		// (Zustand2.equals("Verwundet")))
		// {
		// Wurf=Wurf-4;
		// }
		// else if((Zustand1.equals("Angeschlagen")) && (Zustand2.equals("Schwer
		// verwundet")))
		// {
		// Wurf=Wurf-6;
		// }
		// else if((Zustand1.equals("Verletzt")) &&
		// (Zustand2.equals("Verwundet")))
		// {
		// Wurf=Wurf-2;
		// }
		// else if((Zustand1.equals("Verletzt")) && (Zustand2.equals("Schwer
		// verwundet")))
		// {
		// Wurf=Wurf-4;
		// }
		// else if((Zustand1.equals("Verwundet")) && (Zustand2.equals("Schwer
		// verwundet")))
		// {
		// Wurf=Wurf-2;
		// }
		//
		// strInputWurf[i].setText(Integer.toString(Wurf));
		// strInputZustand[i].setText(Zustand2);
		// strInputLPa[i].setText(Integer.toString(LPa));
		// strInputSCDMG[i].setText("0");
		// }}
		// });
		// btnScSchaden.setBounds(885, 137, 89, 23);
		// frame.getContentPane().add(btnScSchaden);
		//
		// JButton btnReset = new JButton("Reset");
		// btnReset.setToolTipText("<html>Setzt die aktuellen Lebenspunkte auf
		// <br>"
		// + "die angegebenen Lebenspunkte.<br>"
		// + "Die Würfel werden zurückgesetzt.</html>");
		// btnReset.addActionListener(new ActionListener() {
		// public void actionPerformed(ActionEvent arg4) {
		// int i;
		//
		// for(i=0;i<22;i++){
		//
		// strInputWurf[i].setText("0");
		// strInputZustand[i].setText("Angeschlagen");
		// strInputLPa[i].setText(strInputLP[i].getText());
		// strInputSCDMG[i].setText("0");
		// strInputWurf[i].setOpaque(false);
		// }
		// }
		// });
		// btnReset.setBounds(885, 171, 89, 23);
		// frame.getContentPane().add(btnReset);
		//
		//
		// JLabel lblCarnifix = new JLabel("Made by Carnifix");
		// lblCarnifix.setBounds(0, 0, 984, 762);
		// frame.getContentPane().add(lblCarnifix);
		//

	}

	private void setLabels() {
		String[] names = { "Gegner", "VW", "SR", "GW", "LP", "RS", "Initiative", "LPAktuell", "Angriff", "SCDamage",
				"Invis", "Undead", "Zustand", "Wurf" };
		int i = 0;
		for (String name : names) {
			JLabel label = new JLabel(name);
			label.setBounds(10 + i * 60, 11, 40, 14);
			frame.getContentPane().add(label);
			++i;
		}
	}

	private void buildFrame() {
		frame = new JFrame();
		frame.setBounds(100, 100, 1000, 800);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
	}
}
