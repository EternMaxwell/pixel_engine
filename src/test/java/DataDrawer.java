import com.maxwell_dev.pixel_engine.render.opengl.Image;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

public class DataDrawer {
    public static int tickWidth = 1;
    public static int tickAll = 1024 * 16;
    public static int width = tickAll * tickWidth;
    public static int height = 1024;
    public static int xoffset = 60;
    public static int yoffset = 30;
    public static String folder = "results\\";
    public static double max = 25;
    public static void main(String args[]){
        BufferedImage image = new BufferedImage(width + xoffset, height + yoffset, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();

        int yoffsetForText = 20;
        int y = 0;

        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, width + xoffset, height + yoffset);

        g2d.setColor(Color.BLACK);
        g2d.drawLine(xoffset, height, width + xoffset, height);
        g2d.drawLine(xoffset, height, xoffset, 0);

        g2d.setFont(new Font("Arial", Font.PLAIN, 20));

        //render coordinates
        for (int i = 0; (i + 1) * 100 < tickAll; i++) {
            g2d.drawString(String.valueOf(i * 100) + "tick", i * 100 * tickWidth + xoffset, height + 20);
            g2d.drawLine(i * 100 * tickWidth + xoffset, height - 5, i * 100 * tickWidth + xoffset, height);
        }
        for(int i = 0; i < max; i++){
            g2d.drawString(String.valueOf(i) + "ms", xoffset - 55, height - (int) (i * height / max));
            g2d.drawLine(xoffset - 5, height - (int) (i * height / max), xoffset, height - (int) (i * height / max));
        }

        //get all files in the folder
        File[] files = new File(folder).listFiles();

        for(File file: files){
            try {
                BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(file));

                g2d.setColor(new Color((int) (Math.random() * 0x1000000)));

                //render 图解
                g2d.fillRect(width - 1024, 109 + y * yoffsetForText, 100, 2);
                g2d.drawString(file.getName().split("\\.")[2], width - 900, 120 + y * yoffsetForText);

                StringBuilder stringBuilder = new StringBuilder();
                int c;
                while (true) {
                    if ((c = bufferedInputStream.read()) == -1) break;
                    stringBuilder.append((char) c);
                }
                String[] data = stringBuilder.toString().split("\n");
                for (int i = 0; i < data.length - 1; i++) {
                    String[] split = data[i].split(" ");
                    double time = Double.parseDouble(split[1]);
                    double nextTime = Double.parseDouble(data[i + 1].split(" ")[1]);
                    g2d.drawLine(i * tickWidth + xoffset, height - yoffset - (int) (time / max * height), (i + 1) * tickWidth + xoffset, height - yoffset - (int) (nextTime / max * height));
                }
                y++;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        g2d.dispose();
        try {
            ImageIO.write(image, "png", new File("resultImages\\resultAll.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
