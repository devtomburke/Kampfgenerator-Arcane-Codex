import java.awt.Component;
import java.awt.HeadlessException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JTextField;

public class EnemyView{

	/**
	 * 
	 */
	private static final long serialVersionUID = -857809983371520087L;
	private int x;
	private int y;
	private int height;
	private int width;
	private EnemyData data;
	private List<Component> componentList = new ArrayList<Component>();
	
	private EnemyView(){
		
	};
	
	public static EnemyView create(){
		return new EnemyView();
		
	}
	
	public static EnemyView create(int x, int y, int height, int width, EnemyData data){
		EnemyView view = new EnemyView(x, y, height, width, data);
		//view.build();
		
		return view;
	};
	
	private EnemyView(int x, int y, int height, int width, EnemyData data) throws HeadlessException {
		super();
		this.x = x;
		this.y = y;
		this.height = height;
		this.width = width;
		this.data = data;
	}

	public void addToView(JFrame frame) throws IllegalArgumentException, IllegalAccessException{
		int i = 0;
		for (Field f : data.getClass().getDeclaredFields()) {
		    Object value = f.get(data);
		System.out.println(f.getName());
		    if(f.getType() == boolean.class ){
		    	JCheckBox box = new JCheckBox();
		    	box.setBounds(x+i*60, y, width, height);
		    	box.setSelected((boolean) value);
		    	componentList.add(box);
				frame.getContentPane().add(box);
		    }else{
		    	JTextField textfield = new JTextField();
				textfield.setText(value.toString());
				
				if(i==12){
					textfield.setEditable(false);
					textfield.setBounds(x+i*60, y, width, height);
				}else
					textfield.setBounds(x+i*60, y, width, height);
				
				componentList.add(textfield);
				frame.getContentPane().add(textfield);
		    }
			
			++i;
		}
		
		JTextField textfield = new JTextField();
		textfield.setText("");
		textfield.setEnabled(false);
		textfield.setBounds(x+13*60, y, width, height);
		componentList.add(textfield);
		frame.getContentPane().add(textfield);
	}

	public EnemyData getData() {
		return data;
	}

	public void setData(EnemyData data) {
		this.data = data;
	}

	public List<Component> getComponentList() {
		return componentList;
	}

	public void setComponentList(List<Component> componentList) {
		this.componentList = componentList;
	}
	
}
