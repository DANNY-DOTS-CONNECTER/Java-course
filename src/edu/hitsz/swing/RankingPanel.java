package edu.hitsz.swing;

import edu.hitsz.application.Game;
import edu.hitsz.rankings.User;
import edu.hitsz.rankings.UserDao;
import edu.hitsz.rankings.UserDaoImp;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

/**
 * @author Zhoudanni
 */
public class RankingPanel {
    private JPanel mainPanel;
    private JPanel topPanel;
    private JPanel bottomPanel;
    private JButton deleteButton;
    private JTable scoreTable;
    private JScrollPane tableScrollPanel;
    private JLabel levelLabel;
    private JLabel rankingLabel;

    public RankingPanel() {

        //设置文字居中
        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(JLabel.CENTER);
        scoreTable.setDefaultRenderer(Object.class, tcr);




        levelLabel.setText("  Level：" + setLevelString());

        String[] columnName = {"Number", "Name", "Score", "Time"};
        UserDao userDao = new UserDaoImp();
        String[][] tableData = {{}};

        try {
            List<User> users = userDao.getAllUserList();
            if (users != null) {
                //将tableData初始化
                int length = users.size();
                int width = columnName.length;
                tableData = new String[length][width];
                //排序users列表
                Collections.sort(users);
                //从user列表拷贝信息到tableData
                for (int i = 0; i < length; i++) {
                    //从users列表，获取第i行用户数据
                    String[] temp = users.get(i).getSpecificComponent();
                    tableData[i][0] = Integer.toString(i + 1);
                    System.arraycopy(temp, 0, tableData[i], 1, width - 1);
                }
            }
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }

        //表格模型
        DefaultTableModel model = new DefaultTableModel(tableData, columnName) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    List<User> users = userDao.getAllUserList();
                    int row = scoreTable.getSelectedRow();
                    System.out.println(row);
                    //排序
                    Collections.sort(users);
                    long id = (users.get(row)).getId();
                    //跳出提示对话框提醒是否需要删除
                    //YES_OPTION = 0
                    int n = JOptionPane.showConfirmDialog(null, "是否确定要删除所选玩家？", "提示", JOptionPane.YES_NO_CANCEL_OPTION);
                    if (n == 0) {
                        model.removeRow(row);
                        userDao.deleteRecord(id);
                        refresh();
                    }
                } catch (IOException | ClassNotFoundException ex) {
                    ex.printStackTrace();
                }
            }
        });

        scoreTable.setModel(model);
        tableScrollPanel.setViewportView(scoreTable);

        JTableHeader header = scoreTable.getTableHeader();
        header.setFont(new Font("Algerian",Font.PLAIN,18));
        scoreTable.setRowHeight(28);

    }//end constructor

    public static void main(String[] args) {
        JFrame frame = new JFrame("rankingPanel");
        frame.setContentPane(new RankingPanel().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public void refresh() {
        String[] columnName = {"名次", "玩家名", "得分", "记录时间"};
        UserDao userDao = new UserDaoImp();
        String[][] tableData = {{}};

        try {
            List<User> users = userDao.getAllUserList();
            if (users != null) {
                //将tableData初始化
                int length = users.size();
                int width = columnName.length;
                tableData = new String[length][width];
                //排序users列表
                Collections.sort(users);
                //从user列表拷贝信息到tableData
                for (int i = 0; i < length; i++) {
                    //从users列表，获取第i行用户数据
                    String[] temp = users.get(i).getSpecificComponent();
                    tableData[i][0] = Integer.toString(i + 1);
                    System.arraycopy(temp, 0, tableData[i], 1, width - 1);
                }
            }
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }

        //表格模型
        DefaultTableModel model = new DefaultTableModel(tableData, columnName) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };

        scoreTable.setModel(model);
        tableScrollPanel.setViewportView(scoreTable);
    }

    public String setLevelString() {
        String levelText;
        switch (Game.level) {
            case 1:
                levelText = "Easy";
                break;
            case 2:
                levelText = "Medium";
                break;
            case 3:
                levelText = "Hard";
                break;
            default:
                levelText = "";
        }
        return levelText;
    }
}
