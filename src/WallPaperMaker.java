/* author: Brady Smith
 * 
 * Creates an image composed of the recently liked quotes on the GoodReads homepage (www.goodreads.com) * 
 * Is dependent on the goodReadsQuoteGrabber: https://github.com/BradySmith/GoodReadsQuoteGrabber
 * 
 */

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JTextArea;

public class WallPaperMaker {
	GoodReadsQuoteGrabber gr;
	String s;
	
	public WallPaperMaker(){
		gr = new GoodReadsQuoteGrabber();
		s = "";
	}
	
	public void fillString(int i){
		gr.goodReadsQuoteGrab(i);
		for (Quotes j : gr.q){
			String tempString;
			tempString = j.quote + " - ";
			tempString = tempString + j.author + " • ";
			System.out.println(tempString);

			// Filters out the non-english quotes, that is those with illegal characters
			if (tempString.matches("^[-a-zA-Z0-9\"“”;:.?!’',• ]+$")){
				s = s + tempString;
				System.out.println("Yes");
			}
			else
				System.out.println("no");
		}
	}
	
	public static void main(String[] args) {
		WallPaperMaker wp = new WallPaperMaker();

		int i = 1;
		
		// The stop condition for how long of a string we need
		// This will be smaller for smaller wallpapers and similarly larger for larger wallpapers
		while (wp.s.length() < 40000){ //40000			
			wp.fillString(i);
			i++;
		}

		JTextArea textArea = new JTextArea();
		
		// Font Settings
		textArea.setFont(new Font("Calibri", Font.PLAIN, 18));
		textArea.setText(wp.s);
		textArea.setWrapStyleWord(true);
		textArea.setLineWrap(true);
		textArea.setEditable(false);
		
		// Background Color
		textArea.setBackground(Color.BLACK);
		
		// Font Color
		textArea.setForeground(Color.GRAY);
		
		// Adjust Wallpaper size here
		textArea.setSize(5760, 1080);	
		
		Component c = textArea;		
		JFrame f = new JFrame();
		f.setUndecorated(true);
		f.getContentPane().add(c);
		f.pack();
		BufferedImage bi = new BufferedImage(c.getWidth(), c.getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D graphics = bi.createGraphics();
		c.print(graphics);
		graphics.dispose();
		f.dispose();
		
		try {
			// Output location
		    File outputfile = new File("saved.png");
		    ImageIO.write(bi, "png", outputfile);
		} catch (IOException e) {}
		
	}
}
