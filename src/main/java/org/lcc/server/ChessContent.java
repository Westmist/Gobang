package org.lcc.server;

import org.lcc.view.MainUI;

public class ChessContent {
    public int[][] chess = new int[MainUI.WIDTH + 1][MainUI.HEIGHT + 1];
    public Boolean statu = true;      //交替落子状态

    //初始化数组
    public void init() {
        for (int i = 0; i <= MainUI.WIDTH; i++) {
            for (int j = 0; j <= MainUI.HEIGHT; j++) {
                chess[i][j] = 0;
            }
        }
    }

    public Boolean checkWin(int x, int y) {
        //获取当前棋子颜色
        int chessColor = chess[x][y];

        //判断横向是否赢
        int countA = 1;
        for (int i = 1; i < 5; i++) {
            if (x + i <= MainUI.WIDTH) {                 //防止数组越界
                if (chess[x + i][y] == chessColor) {
                    countA++;
                } else {
                    break;
                }
            }
        }
        for (int i = 1; i < 5; i++) {
            if (x - i >= 0) {
                if (chess[x - i][y] == chessColor) {
                    countA++;
                } else {
                    break;
                }
            }
        }

        //判断纵向是否成龙
        int countB = 1;
        for (int i = 1; i < 5; i++) {
            if (y + i <= MainUI.HEIGHT) {
                if (chess[x][y + i] == chessColor) {
                    countB++;
                } else {
                    break;
                }
            }
        }
        for (int i = 1; i < 5; i++) {
            if (y - i >= 0) {
                if (chess[x][y - i] == chessColor) {
                    countB++;
                } else {
                    break;
                }
            }
        }

        //判断撇
        int countC = 1;
        for (int i = 1; i < 5; i++) {
            if (x + i <= MainUI.HEIGHT && y - i >= 0) {
                if (chess[x + i][y - i] == chessColor) {
                    countC++;
                } else {
                    break;
                }
            }

        }
        for (int i = 1; i < 5; i++) {
            if (x - i >= 0 && y + i <= MainUI.HEIGHT) {
                if (chess[x - i][y + i] == chessColor) {
                    countC++;
                } else {
                    break;
                }
            }
        }

        //判断捺
        int countD = 1;
        for (int i = 1; i < 5; i++) {
            if (x - i >= 0 && y - i >= 0) {
                if (chess[x - i][y - i] == chessColor) {
                    countD++;
                } else {
                    break;
                }
            }
        }
        for (int i = 1; i < 5; i++) {
            if (x + i <= MainUI.HEIGHT && y + i <= MainUI.HEIGHT) {
                if (chess[x + i][y + i] == chessColor) {
                    countD++;
                } else {
                    break;
                }
            }
        }

        //判断返回
        if (countA >= 5 || countB >= 5 || countC >= 5 || countD >= 5) {
            return true;
        } else {
            return false;
        }
    }
}
