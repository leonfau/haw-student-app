package main;

import java.io.IOException;

import mensaPlan.EatingMenu;
import mensaPlan.MensaPlan;
import mensaPlan.MensaPlanImpl;
import java.util.Map;

public class main {


	public static void main(String[] args) {
		MensaPlan a =  MensaPlanImpl.MensaPlan();
		try {
			a.update();
			Map<String, EatingMenu> b = a.getMenus();
            for(Map.Entry<String, EatingMenu> c : b.entrySet()){
                System.out.println("---" + c.getKey() + "----");
                System.out.println(c.getValue());
            }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
