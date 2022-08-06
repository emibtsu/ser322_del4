package del4;
import java.awt.*; 
import javax.swing.*;
import java.io.IOException; 
import java.io.File; 
import javax.imageio.ImageIO;

public class JImagePanel extends JPanel
{
private static final long serialVersionUID = 1L;
private Image image = null;
private int width;
private int height;

public JImagePanel(String path)
{

    try {
        File pathToFile = new File(path);
        this.image = ImageIO.read(pathToFile);
    } catch (IOException ex) {
        ex.printStackTrace();
    }
    this.image = image;
    this.width = image.getWidth(this)/2;
    this.height = image.getHeight(this)/2;
}

@Override
public void paintComponent(Graphics g)
{
    super.paintComponent(g);
    if (image != null)
    {
        int x = this.getParent().getWidth()/2 - width;
        int y = this.getParent().getHeight()/2 - height;
        g.drawImage(image,x,y,this);
    }
}
}