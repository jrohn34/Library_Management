import javax.swing.*;
import java.awt.*;

public class InventoryChart extends JFrame {
    private int[] stats;
    private String title;

    public InventoryChart(String title, int[] stats) {
        this.stats = stats;
        this.title = title;
        setTitle(title);
        setSize(300, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().add(new GraphPanel());
        setVisible(true);
    }

    private class GraphPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            int barWidth = 100;
            int barFiction = 3 * stats[0];
            int barNonFiction = 3 * stats[1];
            g.setColor(Color.BLUE);
            g.fillRect(50, getHeight() - barFiction, barWidth, barFiction);
            g.setColor(Color.RED);
            g.fillRect(150, getHeight() - barNonFiction, barWidth, barNonFiction);

            // Add variable names to the bars
            g.setColor(Color.WHITE);
            g.drawString("Fiction:" + stats[0], 70, getHeight() - 10);
            g.drawString("NonFiction:" + stats[1], 155, getHeight() - 10);
        }
    }

    public void displayGraph() {
        setVisible(true);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int[] getStats() {
        return stats;
    }

    public void setStats(int[] stats) {
        this.stats = stats;
    }
}

