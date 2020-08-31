package org.lcc.view;

import javax.swing.*;
import java.awt.*;


public class WinDialog extends JDialog {
    public WinDialog(JFrame frame, Boolean chesser) {
        setSize(240, 160);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setTitle("棋局结束");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        JLabel label = new JLabel();
        if (chesser) {
            label.setText("蓝方获胜");
        } else {
            label.setText("红方获胜");
        }
        Font font = new Font("微软雅黑", Font.PLAIN, 18);
        label.setFont(font);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        add(label, BorderLayout.CENTER);
    }
}
