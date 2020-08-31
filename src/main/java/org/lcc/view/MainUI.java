package org.lcc.view;

import org.lcc.server.ChessContent;
import org.lcc.server.MusicPlayer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainUI extends JFrame {
    public static final int WIDTH = 18;                  //棋盘的列
    public static final int HEIGHT = 18;                 //棋盘的行数
    public static final int GRID_SIZE = 28;              //棋盘网格的大小
    public static final int PANEL_BORDER = 50;           //棋盘与主窗体的间距
    public static final int DRAW_BORDER = 20;           //棋盘网格和棋盘边界的距离
    public static final int CHESS_RADIUS = 10;          //棋子的半径

    //创建保存棋类内容的对象
    ChessContent chessContent = new ChessContent();


    public MainUI() {
        setSize(2 * PANEL_BORDER + GRID_SIZE * WIDTH + 2 * DRAW_BORDER, 2 * PANEL_BORDER + GRID_SIZE * HEIGHT + 2 * DRAW_BORDER);
        setLocationRelativeTo(null);
        setLayout(null);
        setUndecorated(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Container container = getContentPane();

        //初始化棋盘信息
        chessContent.init();

        //播放音乐
        final MusicPlayer musicPlayer = new MusicPlayer();
        musicPlayer.play(true);

        //创建顶部面板
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        topPanel.setBounds(0, 0, 2 * PANEL_BORDER + GRID_SIZE * WIDTH + 2 * DRAW_BORDER, 40);
//        topPanel.setBackground(Color.RED);

        //在顶部面板中添加最小化按钮
        ImageIcon minIcon = new ImageIcon("src\\main\\resources\\images\\down.png");
        JButton minButton = new JButton(minIcon);
        minButton.setFocusPainted(false);  //取消焦点绘制
        minButton.setContentAreaFilled(false); //取消内容绘制
        minButton.setBorderPainted(false);    //取消边框绘制
        minButton.setBorder(null);
//        minButton.setSize(minIcon.getIconWidth(),minIcon.getIconHeight());
        topPanel.add(minButton);
        minButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setExtendedState(ICONIFIED);
            }
        });

        //在顶部面板中添加关闭按钮
        ImageIcon closeIcon = new ImageIcon("src\\main\\resources\\images\\close.png");
        JButton closeButton = new JButton(closeIcon);
        closeButton.setFocusPainted(false);  //取消焦点绘制
        closeButton.setContentAreaFilled(false); //取消内容绘制
        closeButton.setBorderPainted(false);    //取消边框绘制
        closeButton.setBorder(null);      //设置边框为null,防止按钮范围大于图片范围
//        closeButton.setSize(closeIcon.getIconWidth(),closeIcon.getIconHeight());
        topPanel.add(closeButton);
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                musicPlayer.stop();
                dispose();
            }
        });


        //棋盘面板
        JPanel panel = new JPanel() {
            @Override
            public void paint(Graphics g) {
                super.paint(g);
                drawChessboard(g);
                drawChess(g);
            }
        };
        panel.setBackground(Color.GRAY);
        panel.setBounds(PANEL_BORDER, PANEL_BORDER, WIDTH * GRID_SIZE + 2 * DRAW_BORDER, HEIGHT * GRID_SIZE + 2 * DRAW_BORDER);

        //增加鼠标监听，绘制棋子
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //鼠标监听落子判定
                checkChess(e);
            }
        });
        container.add(topPanel);
        container.add(panel);

        setVisible(true);
    }


    //画棋盘
    private void drawChessboard(Graphics g) {
        //画横线
        for (int i = 0; i <= HEIGHT; i++) {
            g.drawLine(DRAW_BORDER, i * GRID_SIZE + DRAW_BORDER, WIDTH * GRID_SIZE + DRAW_BORDER, i * GRID_SIZE + DRAW_BORDER);
        }
        //画竖线
        for (int i = 0; i <= WIDTH; i++) {
            g.drawLine(i * GRID_SIZE + DRAW_BORDER, DRAW_BORDER, i * GRID_SIZE + DRAW_BORDER, WIDTH * GRID_SIZE + DRAW_BORDER);
        }
    }

    //画棋子
    private void drawChess(Graphics g) {
        //遍历棋子信息数组
        for (int i = 0; i < chessContent.chess.length; i++) {
            for (int j = 0; j < chessContent.chess[0].length; j++) {
                if (chessContent.chess[i][j] == 1) {
                    g.setColor(Color.RED);
                    g.fillOval(DRAW_BORDER + i * GRID_SIZE - CHESS_RADIUS, DRAW_BORDER + j * GRID_SIZE - CHESS_RADIUS, CHESS_RADIUS * 2, CHESS_RADIUS * 2);
                } else if (chessContent.chess[i][j] == 2) {
                    g.setColor(Color.BLUE);
                    g.fillOval(DRAW_BORDER + i * GRID_SIZE - CHESS_RADIUS, DRAW_BORDER + j * GRID_SIZE - CHESS_RADIUS, CHESS_RADIUS * 2, CHESS_RADIUS * 2);
                }
            }
        }
    }

    //落子判定
    private void checkChess(MouseEvent e) {
        int localX = e.getX() - DRAW_BORDER;
        int localY = e.getY() - DRAW_BORDER;

        //在网格区域内
        if ((e.getX() >= DRAW_BORDER && e.getX() <= DRAW_BORDER + WIDTH * GRID_SIZE) && (e.getY() >= DRAW_BORDER && e.getY() <= DRAW_BORDER + HEIGHT * GRID_SIZE)) {
            //限定落子区域为网格焦点为中心的棋子半径区域  中空
            if (localX % GRID_SIZE < CHESS_RADIUS || localX % GRID_SIZE > GRID_SIZE - CHESS_RADIUS) {
                if (localY % GRID_SIZE < CHESS_RADIUS || localY % GRID_SIZE > GRID_SIZE - CHESS_RADIUS) {
                    //该点无棋子
                    if (chessContent.chess[(localX + GRID_SIZE / 2) / GRID_SIZE][(localY + GRID_SIZE / 2) / GRID_SIZE] == 0) {
                        if (chessContent.statu) {       //红方下
                            chessContent.chess[(localX + GRID_SIZE / 2) / GRID_SIZE][(localY + GRID_SIZE / 2) / GRID_SIZE] = 1;
                            repaint();                //绘图
                            chessContent.statu = false;    //切换状态
                        } else {                        //蓝方下
                            chessContent.chess[(localX + GRID_SIZE / 2) / GRID_SIZE][(localY + GRID_SIZE / 2) / GRID_SIZE] = 2;
                            repaint();          //绘图
                            chessContent.statu = true;    //切换状态
                        }
                        //判断胜负
                        if (chessContent.checkWin((localX + GRID_SIZE / 2) / GRID_SIZE, (localY + GRID_SIZE / 2) / GRID_SIZE)) {
                            new WinDialog(this, chessContent.statu).setVisible(true);
                            chessContent.init();         //该局结束，棋子信息初始化
                            chessContent.statu = true;              //先后手状态初始化，保证每次开局都是红方先手
                        }
                    }
                }
            }
        }
    }
}