
public class EnemyData {

	public String name;
	public int VW;
	public int SR;
	public int GW;
	public int LP;
	public int RS;
	public int initiative;
	public int LPaktuell;
	public int angriff;
	public String schaden;
	public boolean isUndead;
	public boolean isInvisible;
	public String zustand;

	private EnemyData() {
	};

	private EnemyData(String name, int vW, int sR, int gW, int lP, int rS, int initiative, int angriff, String schaden,
			boolean isUndead, boolean isInvisible, String zustand) {
		super();
		this.name = name;
		VW = vW;
		SR = sR;
		GW = gW;
		LP = lP;
		RS = rS;
		this.initiative = initiative;
		this.angriff= angriff;
		this.schaden = schaden;
		this.isUndead = isUndead;
		this.isInvisible = isInvisible;
		LPaktuell = lP;
		this.zustand = zustand;
	}

	public static EnemyData create() {
		return new EnemyData();

	}

	public static EnemyData create(String name, int vW, int sR, int gW, int lP, int rS, int initiative, int angriff,
			String schaden, boolean isUndead, boolean isInvisible, String zustand) {
		return new EnemyData(name, vW, sR, gW, lP, rS, initiative, angriff, schaden, isUndead, isInvisible, zustand);

	}

}
