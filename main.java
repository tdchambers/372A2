import javax.swing.JFrame;

public class main{
			public static void main(String[] args){
			
				Gui F = new Gui();
				F.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

				 F.layoutView();
				 F.setSize(400,300);
				 F.setVisible(true);
			}
		}
