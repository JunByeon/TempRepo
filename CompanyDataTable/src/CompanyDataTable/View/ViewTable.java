package CompanyDataTable.View;

import java.sql.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class ViewTable extends JFrame implements ActionListener {
    // JFrame :  Java Swing 라이브러리에서 제공되는 GUI frame inherit
    // ActionListener : Java Swing에서 사용자의 액션(예: 버튼 클릭)에 대한 이벤트 처리를 위한 인터페이스(actionPerformed method)
    public static void main(String[] args) {
        new ViewTable(); // main 함수 실행시 GUI 생성과 동시에 기능 동작 실시
    }
    // JDBC를 위한 필드 선언 시작
    Connection conn;
    Statement statement;
    ResultSet resultSet;
    Insert insert = new Insert();
    // JDBC를 위한 필드 선언 종료

    // ComboBox 선언 시작
    JComboBox Category;
    JComboBox Dept;
    JComboBox Sex;
    JComboBox Update;
    // ComboBox 선언 종료

    // User로부터 text input 받을 변수 선언 시작
    JTextField setSalary_Bdate_employee = new JTextField(10); // User의 text input을 받는다
    JTextField update_Salary_Address_Sex = new JTextField(10);
    // User로부터 text input 받을 변수 선언 종료

    // CheckBox 이름과 초기 상태 선언 시작
    JCheckBox check1 = new JCheckBox("Name", true);
    JCheckBox check2 = new JCheckBox("Ssn", true);
    JCheckBox check3 = new JCheckBox("Bdate", true);
    JCheckBox check4 = new JCheckBox("Address", true);
    JCheckBox check5 = new JCheckBox("Sex", true);
    JCheckBox check6 = new JCheckBox("Salary", true);
    JCheckBox check7 = new JCheckBox("Supervisor", true);
    JCheckBox check8 = new JCheckBox("Department", true);
    // CheckBox 이름과 초기 상태 선언 종료

    // 테이블의 열 제목을 저장하기 위해 Vector(동적으로 크기가 조절되는 배열) 선언 시작
    Vector<String> Head = new Vector<String>();
    // 테이블의 열 제목을 저장하기 위해 Vector(동적으로 크기가 조절되는 배열) 선언 종료

    // 테이블 형식으로 데이터를 관리하기 위해 테이블 선언 시작
    JTable table;
    DefaultTableModel model;
    // 테이블 형식으로 데이터를 관리하기 위해 테이블 선언 종료

    // 업데이트 했을때 사용하기 위한 프로퍼티 선언 시작
    int BOOLEAN_COLUMN = 0;
    int NAME_COLUMN = 0;
    int SALARY_COLUMN = 0;
    int ADDRESS_COLUMN = 0;
    int SEX_COLUMN = 0;
    int DEPT_SALARY_COLUMN_R = 0;
    int DEPT_SALARY_COLUMN_A = 0;
    int DEPT_SALARY_COLUMN_H = 0;
    String dShow;
    // 업데이트 했을때 사용하기 위한 프로퍼티 선언 종료


    // 화면에 보이는 버튼 선언 시작
    JButton Search_Button = new JButton("검색");	// 검색 버튼
    JButton Update_Button = new JButton("UPDATE");	// 버튼들
    JButton Delete_Button = new JButton("선택한 데이터 삭제");
    JButton Insert_Button = new JButton("직원 추가");
    // 화면에 보이는 버튼 선언 종료

    // 화면에 보이는 라벨 선언 시작
    JLabel totalEmp = new JLabel("인원수 : "); // 총 인원수를 나타내는 label
    JLabel totalCount = new JLabel(); // 총 선택된 인원수를 나타내는 label
    JLabel empLabel = new JLabel("선택한 직원: "); // 선택된 직원의 label
    JLabel showSelectedEmp = new JLabel(); // 나중에 선택된 직원의 label
    JLabel setLabel = new JLabel("수정: "); // 수정할 목록과 수정할 내용을 표시하는 label
    // 화면에 보이는 라벨 선언 종료


    // 스크롤바 선언 시작
    JPanel panel;
    JScrollPane scrollPane;	// 스크롤 가능한 컨테이너로 제작
    JPanel ComboBoxPanel = new JPanel();
    // 스크롤바 선언 종료

    // 인원수를 세기위한 변수인 count 선언 시작
    int count = 0;
    // 인원수를 세기위한 변수인 count 선언 종료

    Container thisContainer = this;	// 자기 자신을 나타낸다. 즉, Container 그 자체
    public ViewTable() {
        // 테이블에 채울 내용 설정 시작
        String[] category = { "전체", "부서","성별","연봉","생일","부하직원","부양가족" };
        String[] dept = { "Research", "Administration", "Headquarters" };
        String[] sex = {"F","M"};
        String[] update = {"Address","Sex","Salary","Dept_Salary_R", "Dept_Salary_A", "Dept_Salary_H" }; // 8번 추가
        Category = new JComboBox(category);
        Dept = new JComboBox(dept);
        Sex = new JComboBox(sex);
        Update = new JComboBox(update);
        // 테이블에 채울 내용 설정 종료

        // category 변화나 update가 눌리면 이벤트 처리 액션 설정 시작
        Category.addActionListener(this);
        Update.addActionListener(this);
        // category 변화나 update가 눌리면 이벤트 처리 액션 설정 종료

        // 각각의 버튼들마다 이벤트 설정 시작
        Search_Button.addActionListener(this);
        Delete_Button.addActionListener(this);
        Insert_Button.addActionListener(this);
        Update_Button.addActionListener(this);
        // 각각의 버튼들마다 이벤트 설정 종료

        // layout 설정 시작
        ComboBoxPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        ComboBoxPanel.add(new JLabel("검색 범위 "));
        ComboBoxPanel.add(Category);
        JPanel CheckBoxPanel = new JPanel();
        CheckBoxPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        CheckBoxPanel.add(new JLabel("검색 항목 "));
        CheckBoxPanel.add(check1);
        CheckBoxPanel.add(check2);
        CheckBoxPanel.add(check3);
        CheckBoxPanel.add(check4);
        CheckBoxPanel.add(check5);
        CheckBoxPanel.add(check6);
        CheckBoxPanel.add(check7);
        CheckBoxPanel.add(check8);
        CheckBoxPanel.add(Search_Button);
        JPanel InsertPanel = new JPanel();
        InsertPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        InsertPanel.add(Insert_Button);
        JPanel CheckBoxInsertPanel = new JPanel();
        CheckBoxInsertPanel.setLayout(new BoxLayout(CheckBoxInsertPanel, BoxLayout.X_AXIS));
        CheckBoxInsertPanel.add(CheckBoxPanel);
        CheckBoxInsertPanel.add(InsertPanel);
        JPanel ShowSelectedPanel = new JPanel();
        ShowSelectedPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        empLabel.setFont(new Font("Dialog", Font.BOLD, 16));
        showSelectedEmp.setFont(new Font("Dialog", Font.BOLD, 16));
        dShow = "";
        ShowSelectedPanel.add(empLabel);
        ShowSelectedPanel.add(showSelectedEmp);
        JPanel TotalPanel = new JPanel();
        TotalPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        TotalPanel.add(totalEmp);
        TotalPanel.add(totalCount);
        JPanel UpdatePanel = new JPanel();
        UpdatePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        UpdatePanel.add(setLabel);
        UpdatePanel.add(Update);
        UpdatePanel.add(update_Salary_Address_Sex);
        UpdatePanel.add(Update_Button);
        JPanel DeletePanel = new JPanel();
        DeletePanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        DeletePanel.add(Delete_Button);
        JPanel Top = new JPanel();
        Top.setLayout(new BoxLayout(Top, BoxLayout.Y_AXIS));
        Top.add(ComboBoxPanel);
        Top.add(CheckBoxInsertPanel);
        JPanel Halfway = new JPanel();
        Halfway.setLayout(new BoxLayout(Halfway, BoxLayout.X_AXIS));
        Halfway.add(ShowSelectedPanel);
        JPanel Bottom = new JPanel();
        Bottom.setLayout(new BoxLayout(Bottom, BoxLayout.X_AXIS));
        Bottom.add(TotalPanel);
        Bottom.add(UpdatePanel);
        Bottom.add(DeletePanel);
        JPanel ShowVertical = new JPanel();
        ShowVertical.setLayout(new BoxLayout(ShowVertical, BoxLayout.Y_AXIS));
        ShowVertical.add(Halfway);
        ShowVertical.add(Bottom);
        add(Top, BorderLayout.NORTH);
        add(ShowVertical, BorderLayout.SOUTH);
        setTitle("Information Retrival System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1300, 600);
        setLocationRelativeTo(null);
        setVisible(true);
        // layout 설정 종료
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // DB연결
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // JDBC 드라이버 연결

            String user = "root";
            String pwd = "b9081620"; // 비밀번호 입력
            String dbname = "company";
            String url = "jdbc:mysql://localhost:3306/" + dbname + "?serverTimezone=UTC";

            conn = DriverManager.getConnection(url, user, pwd);
            System.out.println("정상적으로 연결되었습니다.");

        } catch (SQLException e1) {
            System.err.println("연결할 수 없습니다.");
            e1.printStackTrace();
        } catch (ClassNotFoundException e1) {
            System.err.println("드라이버를 로드할 수 없습니다.");
            e1.printStackTrace();
        }

        // ------------------------------------------------------------------------ //

        if (count == 1) {
            thisContainer.remove(panel);
            revalidate();
        }

        String category_item = Category.getSelectedItem().toString();

        if(category_item == "부서") {
            if(ComboBoxPanel.getComponentCount() != 2) {
                ComboBoxPanel.remove(2);
            }
            ComboBoxPanel.add(Dept);
            ComboBoxPanel.repaint();
            revalidate();
        }else if(category_item == "성별") {
            if(ComboBoxPanel.getComponentCount() != 2) {
                System.out.println(ComboBoxPanel.getComponentCount());
                ComboBoxPanel.remove(2);
            }
            ComboBoxPanel.add(Sex);
            ComboBoxPanel.repaint();
            revalidate();
        }else if(category_item.equals("연봉") || category_item.equals("생일")
                || category_item.equals("부하직원") || category_item.equals("부양가족")){
            if(ComboBoxPanel.getComponentCount() != 2) {
                ComboBoxPanel.remove(2);
            }
            ComboBoxPanel.repaint();
            ComboBoxPanel.add(setSalary_Bdate_employee);
            revalidate();
        }else if(category_item.equals("전체")) {
            if(ComboBoxPanel.getComponentCount() != 2) {
                ComboBoxPanel.remove(2);
            }
            ComboBoxPanel.repaint();
            revalidate();
        }


        if (e.getSource() == Search_Button) {
            String getSalary_Bdate_employee = null;
            String stmt;
            if(setSalary_Bdate_employee.getText() != null) {
                getSalary_Bdate_employee = setSalary_Bdate_employee.getText();
            }

            if(Category.getSelectedItem().toString() == "부양가족") {
                Head.clear();
                Head.add(" ");
                Head.add("부양가족 이름");
                Head.add("성별");
                Head.add("생일");
                Head.add("관계");

                String fname = null;
                String minit = null;
                String lname = null;

                int name_correct = 1;

                try {
                    String[] name = getSalary_Bdate_employee.split(" ");
                    fname = name[0];
                    minit = name[1];
                    lname = name[2];
                }catch(Exception e3) {
                    name_correct = 0;
                    JOptionPane.showMessageDialog(null, "이름을 fname minit lname 순으로 정확하게 입력해주세요.");
                }



                stmt = "select d.dependent_name, d.sex,d.bdate,d.relationship from employee s, dependent d where s.ssn = d.essn";
                stmt += " and s.fname = \"" +fname + "\""+
                        " and s.minit = \"" + minit + "\""+
                        " and s.lname = \"" + lname + "\"" + ";";
                setSalary_Bdate_employee.setText("");
                model = new DefaultTableModel(Head, 0) {
                    @Override
                    public boolean isCellEditable(int row, int column) {
                        if (column > 0) {
                            return true;
                        } else {
                            return true;
                        }
                    }
                };

                table = new JTable(model) {
                    @Override
                    public Class getColumnClass(int column) {
                        if (column == 0) {
                            return " ".getClass();
                        } else
                            return " ".getClass();
                    }
                };

                showSelectedEmp.setText(" ");
                int person_count = 0;
                try {
                    count = 1;
                    statement = conn.createStatement();
                    resultSet = statement.executeQuery(stmt);
                    ResultSetMetaData rsmd = resultSet.getMetaData();
                    int columnCnt = rsmd.getColumnCount();
                    int rowCnt = table.getRowCount();

                    while (resultSet.next()) {
                        Vector<Object> tuple = new Vector<Object>();
                        tuple.add(" ");
                        for (int i = 1; i < columnCnt + 1; i++) {
                            tuple.add(resultSet.getString(rsmd.getColumnName(i)));
                        }
                        model.addRow(tuple);
                        rowCnt++;
                        person_count++;
                    }
                    totalCount.setText(String.valueOf(rowCnt));

                } catch (SQLException ee) {
                    System.out.println("actionPerformed err : " + ee);
                    ee.printStackTrace();

                }

                if(name_correct == 1){															// 여기부터 추가
                    if(person_count == 0) {
                        JOptionPane.showMessageDialog(null, "부양가족이 없습니다.");
                    }else {
                        panel = new JPanel();
                        scrollPane = new JScrollPane(table);
                        scrollPane.setPreferredSize(new Dimension(1100, 400));
                        panel.add(scrollPane);
                        add(panel, BorderLayout.CENTER);
                        revalidate();
                    }
                }

            }else {
                if (check1.isSelected() || check2.isSelected() || check3.isSelected() || check4.isSelected() || check5.isSelected()
                        || check6.isSelected() || check7.isSelected() || check8.isSelected()) {
                    Head.clear();
                    Head.add("선택");

                    stmt = "select";
                    if (check1.isSelected()) {
                        stmt += " concat(e.fname,' ', e.minit,' ', e.lname,' ') as Name";
                        Head.add("NAME");
                    }
                    if (check2.isSelected()) {
                        if (!check1.isSelected())
                            stmt += " e.ssn";
                        else
                            stmt += ", e.ssn";
                        Head.add("SSN");
                    }
                    if (check3.isSelected()) {
                        if (!check1.isSelected() && !check2.isSelected())
                            stmt += " e.bdate";
                        else
                            stmt += ", e.bdate";
                        Head.add("BDATE");
                    }
                    if (check4.isSelected()) {
                        if (!check1.isSelected() && !check2.isSelected() && !check3.isSelected())
                            stmt += " e.address";
                        else
                            stmt += ", e.address";
                        Head.add("ADDRESS");
                    }
                    if (check5.isSelected()) {
                        if (!check1.isSelected() && !check2.isSelected() && !check3.isSelected() && !check4.isSelected())
                            stmt += " e.sex";
                        else
                            stmt += ", e.sex";
                        Head.add("SEX");
                    }
                    if (check6.isSelected()) {
                        if (!check1.isSelected() && !check2.isSelected() && !check3.isSelected() && !check4.isSelected()
                                && !check5.isSelected())
                            stmt += " e.salary";
                        else
                            stmt += ", e.salary";
                        Head.add("SALARY");
                    }
                    if (check7.isSelected()) {
                        if (!check1.isSelected() && !check2.isSelected() && !check3.isSelected() && !check4.isSelected() && !check5.isSelected()
                                && !check6.isSelected())
                            stmt += " concat(s.fname, ' ', s.minit, ' ',s.lname,' ') as Supervisor ";
                        else
                            stmt += ", concat(s.fname, ' ', s.minit, ' ',s.lname,' ') as Supervisor ";
                        Head.add("SUPERVISOR");
                    }
                    if (check8.isSelected()) {
                        if (!check1.isSelected() && !check2.isSelected() && !check3.isSelected() && !check4.isSelected() && !check5.isSelected()
                                && !check6.isSelected() && !check7.isSelected())
                            stmt += " dname";
                        else
                            stmt += ", dname";
                        Head.add("DEPARTMENT");
                    }
                    stmt += " from employee e left outer join employee s ";
                    stmt += "on e.super_ssn=s.ssn, department where e.dno = dnumber";

                    if (Category.getSelectedItem().toString() == "부서") {

                        if (Dept.getSelectedItem().toString() == "Research")
                            stmt += " and dname = \"Research\";";
                        else if (Dept.getSelectedItem().toString() == "Administration")
                            stmt += " and dname = \"Administration\";";
                        else if (Dept.getSelectedItem().toString() == "Headquarters")
                            stmt += " and dname = \"Headquarters\";";
                    }else if(Category.getSelectedItem().toString() == "성별") {
                        if(Sex.getSelectedItem().toString() == "F") {
                            stmt += " and e.SEX = \"F\";";
                        }else if(Sex.getSelectedItem().toString() == "M") {
                            stmt += " and e.SEX = \"M\";";
                        }
                    }else if(Category.getSelectedItem().toString() == "연봉") {
                        stmt += " and e.salary >=" +getSalary_Bdate_employee + ";";
                        setSalary_Bdate_employee.setText("");
                    }else if(Category.getSelectedItem().toString() == "생일") {
                        stmt += " and e.bdate like \"____-" + getSalary_Bdate_employee + "%\";";
                        setSalary_Bdate_employee.setText("");
                    }else if(Category.getSelectedItem().toString() == "부하직원") {
                        String[] name = getSalary_Bdate_employee.split(" ");
                        String fname = name[0];
                        String minit = name[1];
                        String lname = name[2];
                        stmt += " and s.fname = \"" +fname + "\""+
                                " and s.minit = \"" + minit + "\""+
                                " and s.lname = \"" + lname + "\"" + ";";
                        setSalary_Bdate_employee.setText("");
                    }

                    model = new DefaultTableModel(Head, 0) {
                        @Override
                        public boolean isCellEditable(int row, int column) {
                            if (column > 0) {
                                return false;
                            } else {
                                return true;
                            }
                        }
                    };
                    for (int i = 0; i < Head.size(); i++) {
                        if (Head.get(i) == "NAME") {
                            NAME_COLUMN = i;
                        } else if (Head.get(i) == "SALARY") {
                            SALARY_COLUMN = i;
                        }else if (Head.get(i) == "ADDRESS") {
                            ADDRESS_COLUMN = i;
                        }else if (Head.get(i) == "SEX") {
                            SEX_COLUMN = i;
                        } else if (Head.get(i) == "DEPT_SALARY_R") {
                            DEPT_SALARY_COLUMN_R = i;// 8번 추가
                        } else if (Head.get(i) == "DEPT_SALARY_A") {
                            DEPT_SALARY_COLUMN_A = i;// 8번 추가
                        } else if (Head.get(i) == "DEPT_SALARY_H") {
                            DEPT_SALARY_COLUMN_H = i;// 8번 추가
                        }

                    }
                    table = new JTable(model) {
                        @Override
                        public Class getColumnClass(int column) {
                            if (column == 0) {
                                return Boolean.class;
                            } else
                                return String.class;
                        }
                    };

                    showSelectedEmp.setText(" ");

                    try {
                        count = 1;
                        statement = conn.createStatement();
                        resultSet = statement.executeQuery(stmt);
                        ResultSetMetaData rsmd = resultSet.getMetaData();
                        int columnCnt = rsmd.getColumnCount();
                        int rowCnt = table.getRowCount();

                        while (resultSet.next()) {
                            Vector<Object> tuple = new Vector<Object>();
                            tuple.add(false);
                            for (int i = 1; i < columnCnt + 1; i++) {
                                tuple.add(resultSet.getString(rsmd.getColumnName(i)));
                            }
                            model.addRow(tuple);
                            rowCnt++;
                        }
                        totalCount.setText(String.valueOf(rowCnt));

                    } catch (SQLException ee) {
                        System.out.println("actionPerformed err : " + ee);
                        ee.printStackTrace();

                    }
                    panel = new JPanel();
                    scrollPane = new JScrollPane(table);
                    table.getModel().addTableModelListener(event -> {
                                int row = event.getFirstRow();
                                int column = event.getColumn();
                                if (column == BOOLEAN_COLUMN) {
                                    TableModel model = (TableModel) event.getSource();
                                    String columnName = model.getColumnName(1);
                                    Boolean checked = (Boolean) model.getValueAt(row, column);
                                    if (columnName == "NAME") {
                                        if (checked) {
                                            dShow = "";
                                            for (int i = 0; i < table.getRowCount(); i++) {
                                                if (table.getValueAt(i, 0) == Boolean.TRUE) {
                                                    dShow += (String) table.getValueAt(i, NAME_COLUMN) + "    ";

                                                }
                                            }
                                            showSelectedEmp.setText(dShow);
                                        } else {
                                            dShow = "";
                                            for (int i = 0; i < table.getRowCount(); i++) {
                                                if (table.getValueAt(i, 0) == Boolean.TRUE) {
                                                    dShow += (String) table.getValueAt(i, 1) + "    ";
                                                }
                                            }
                                            showSelectedEmp.setText(dShow);
                                        }
                                    }
                                }
                            }
                    );



                    scrollPane.setPreferredSize(new Dimension(1100, 400));
                    panel.add(scrollPane);
                    add(panel, BorderLayout.CENTER);
                    revalidate();

                } else {
                    JOptionPane.showMessageDialog(null, "검색 항목을 한개 이상 선택하세요.");
                }
            }

        }

        // DELETE
        if (e.getSource() == Delete_Button) {
            Vector<String> delete_ssn = new Vector<String>();

            try {

                String columnName = model.getColumnName(2);
                if (columnName == "SSN") {
                    for (int i = 0; i < table.getRowCount(); i++) {
                        if (table.getValueAt(i, 0) == Boolean.TRUE) {
                            delete_ssn.add((String) table.getValueAt(i, 2));
                        }
                    }
                    for (int i = 0; i < delete_ssn.size(); i++) {
                        for (int k = 0; k < model.getRowCount(); k++) {
                            if (table.getValueAt(k, 0) == Boolean.TRUE) {
                                model.removeRow(k);
                                totalCount.setText(String.valueOf(table.getRowCount()));
                            }
                        }
                    }
                    for (int i = 0; i < delete_ssn.size(); i++) {
                        String deleteStmt = "DELETE FROM EMPLOYEE WHERE Ssn=?";
                        PreparedStatement p = conn.prepareStatement(deleteStmt);
                        p.clearParameters();
                        p.setString(1, String.valueOf(delete_ssn.get(i)));
                        p.executeUpdate();

                    }
                } else {
                    JOptionPane.showMessageDialog(null, "삭제 작업을 진행하시려면 NAME, SSN 항목을 모두 체크해주세요.");
                }

                showSelectedEmp.setText(" ");

            } catch (SQLException e1) {
                System.out.println("actionPerformed err : " + e1);
                e1.printStackTrace();
            }
            panel = new JPanel();
            scrollPane = new JScrollPane(table);
            table.getModel().addTableModelListener(event -> {
                        int row = event.getFirstRow();
                        int column = event.getColumn();
                        if (column == BOOLEAN_COLUMN) {
                            TableModel model = (TableModel) event.getSource();
                            String columnName = model.getColumnName(1);
                            Boolean checked = (Boolean) model.getValueAt(row, column);
                            if (columnName == "NAME") {
                                if (checked) {
                                    dShow = "";
                                    for (int i = 0; i < table.getRowCount(); i++) {
                                        if (table.getValueAt(i, 0) == Boolean.TRUE) {
                                            dShow += (String) table.getValueAt(i, NAME_COLUMN) + "    ";

                                        }
                                    }
                                    showSelectedEmp.setText(dShow);
                                } else {
                                    dShow = "";
                                    for (int i = 0; i < table.getRowCount(); i++) {
                                        if (table.getValueAt(i, 0) == Boolean.TRUE) {
                                            dShow += (String) table.getValueAt(i, 1) + "    ";
                                        }
                                    }
                                    showSelectedEmp.setText(dShow);
                                }
                            }
                        }
                    }
            );



            scrollPane.setPreferredSize(new Dimension(1100, 400));
            panel.add(scrollPane);
            add(panel, BorderLayout.CENTER);
            revalidate();

        } // DELETE 끝

        // UPDATE

        if (e.getSource() == Update_Button) {

            Vector<String> update_ssn = new Vector<String>();
            try {
                String columnName = model.getColumnName(2);

                System.out.println(columnName);
                if (columnName == "SSN") {
                    if(Update.getSelectedItem().toString() == "Salary") {
                        if(model.getColumnName(SALARY_COLUMN) == "SALARY") {
                            for (int i = 0; i < table.getRowCount(); i++) {
                                if (table.getValueAt(i, 0) == Boolean.TRUE) {
                                    String updateSalary = update_Salary_Address_Sex.getText();
                                    table.setValueAt(Double.parseDouble(updateSalary), i, SALARY_COLUMN);
                                    String updateStmt = "UPDATE EMPLOYEE SET Salary=? , modified=SYSDATE() WHERE Ssn=?";
                                    PreparedStatement p = conn.prepareStatement(updateStmt);
                                    p.clearParameters();
                                    p.setString(1, updateSalary);
                                    p.setString(2, String.valueOf((String) table.getValueAt(i, 2)));
                                    p.executeUpdate();
                                }
                            }
                        }else {
                            JOptionPane.showMessageDialog(null, "수정 작업을 위해 SALARY를 체크해주세요");
                        }

                    }else if(Update.getSelectedItem().toString() == "Sex") {
                        if(model.getColumnName(SEX_COLUMN) == "SEX") {
                            for (int i = 0; i < table.getRowCount(); i++) {
                                if (table.getValueAt(i, 0) == Boolean.TRUE) {
                                    //update_ssn.add((String) table.getValueAt(i, 2));
                                    String updateSalary = update_Salary_Address_Sex.getText();
                                    table.setValueAt(updateSalary, i, SEX_COLUMN);
                                    String updateStmt = "UPDATE EMPLOYEE SET SEX=? , modified=SYSDATE() WHERE Ssn=?";
                                    PreparedStatement p = conn.prepareStatement(updateStmt);
                                    p.clearParameters();
                                    p.setString(1, String.valueOf(updateSalary));
                                    p.setString(2, String.valueOf((String) table.getValueAt(i, 2)));
                                    p.executeUpdate();
                                }
                            }
                        }else {
                            JOptionPane.showMessageDialog(null, "수정 작업을 위해 SEX를 체크해주세요");
                        }

                    }else if(Update.getSelectedItem().toString() == "Address") {
                        if(model.getColumnName(ADDRESS_COLUMN) == "ADDRESS") {
                            for (int i = 0; i < table.getRowCount(); i++) {
                                if (table.getValueAt(i, 0) == Boolean.TRUE) {
                                    //update_ssn.add((String) table.getValueAt(i, 2));
                                    String updateSalary = update_Salary_Address_Sex.getText();
                                    table.setValueAt(updateSalary, i, ADDRESS_COLUMN);
                                    String updateStmt = "UPDATE EMPLOYEE SET Address=? , modified=SYSDATE() WHERE Ssn=?";
                                    PreparedStatement p = conn.prepareStatement(updateStmt);
                                    p.clearParameters();
                                    p.setString(1, String.valueOf(updateSalary));
                                    p.setString(2, String.valueOf((String) table.getValueAt(i, 2)));
                                    p.executeUpdate();
                                }
                            }
                        }else {
                            JOptionPane.showMessageDialog(null, "수정 작업을 위해 ADDRESS를 체크해주세요");
                        }
                    }else if (Update.getSelectedItem().toString() == "Dept_Salary_R") {// 부서별로 월급 일괄 수정_Research
                        for (int i = 0; i < table.getRowCount(); i++) {
                            if (table.getValueAt(i, 8).toString().equals("Research")) {
                                String updateSalary = update_Salary_Address_Sex.getText();
                                table.setValueAt(Double.parseDouble(updateSalary), i, SALARY_COLUMN);
                                String ex = "Research";
                                String updateStmt = "UPDATE EMPLOYEE JOIN DEPARTMENT ON Dno = Dnumber SET Salary=? , modified=SYSDATE() WHERE Dname=?";
                                PreparedStatement p = conn.prepareStatement(updateStmt);
                                p.clearParameters();
                                p.setString(1, updateSalary);
                                p.setString(2, ex);
                                // System.out.println(updateSalary);
                                p.executeUpdate();
                            }
                        }
                    } else if (Update.getSelectedItem().toString() == "Dept_Salary_A") { // 부서별로 월급 일괄 수정_Administration
                        for (int i = 0; i < table.getRowCount(); i++) {
                            if (table.getValueAt(i, 8).toString().equals("Administration")) {
                                String updateSalary = update_Salary_Address_Sex.getText();
                                table.setValueAt(Double.parseDouble(updateSalary), i, SALARY_COLUMN);
                                String ex = "Administration";
                                String updateStmt = "UPDATE EMPLOYEE JOIN DEPARTMENT ON Dno = Dnumber SET Salary=? , modified=SYSDATE() WHERE Dname=?";
                                PreparedStatement p = conn.prepareStatement(updateStmt);
                                p.clearParameters();
                                p.setString(1, updateSalary);
                                p.setString(2, ex);
                                // System.out.println(updateSalary);
                                p.executeUpdate();

                            }
                        }
                    } else if (Update.getSelectedItem().toString() == "Dept_Salary_H") { // 부서별로 월급 일괄 수정_Headquarters
                        for (int i = 0; i < table.getRowCount(); i++) {
                            if (table.getValueAt(i, 8).toString().equals("Headquarters")) {
                                String updateSalary = update_Salary_Address_Sex.getText();
                                table.setValueAt(Double.parseDouble(updateSalary), i, SALARY_COLUMN);
                                String ex = "Headquarters";
                                String updateStmt = "UPDATE EMPLOYEE JOIN DEPARTMENT ON Dno = Dnumber SET Salary=? , modified=SYSDATE() WHERE Dname=?";
                                PreparedStatement p = conn.prepareStatement(updateStmt);
                                p.clearParameters();
                                p.setString(1, updateSalary);
                                p.setString(2, ex);
                                // System.out.println(updateSalary);
                                p.executeUpdate();
                            }
                        }

                    } else {
                        JOptionPane.showMessageDialog(null, "수정 작업을 위해 Name과 SSN열을 체크해주시기 바랍니다.");
                    }
                }
                showSelectedEmp.setText(" ");

            } catch (SQLException e1) {
                System.out.println("actionPerformed err : " + e1);
                e1.printStackTrace();
            }
            panel = new JPanel();
            scrollPane = new JScrollPane(table);
            table.getModel().addTableModelListener(event -> {
                int row = event.getFirstRow();
                int column = event.getColumn();
                if (column == BOOLEAN_COLUMN) {
                    TableModel model = (TableModel) event.getSource();
                    String columnName = model.getColumnName(1);
                    Boolean checked = (Boolean) model.getValueAt(row, column);
                    if (columnName == "NAME") {
                        if (checked) {
                            dShow = "";
                            for (int i = 0; i < table.getRowCount(); i++) {
                                if (table.getValueAt(i, 0) == Boolean.TRUE) {
                                    dShow += (String) table.getValueAt(i, NAME_COLUMN) + "    ";

                                }
                            }
                            showSelectedEmp.setText(dShow);
                        } else {
                            dShow = "";
                            for (int i = 0; i < table.getRowCount(); i++) {
                                if (table.getValueAt(i, 0) == Boolean.TRUE) {
                                    dShow += (String) table.getValueAt(i, 1) + "    ";
                                }
                            }
                            showSelectedEmp.setText(dShow);
                        }
                    }
                }
            }
        );

        scrollPane.setPreferredSize(new Dimension(1100, 400));
            panel.add(scrollPane);
            add(panel, BorderLayout.CENTER);
            revalidate();
        } // UPDATE 끝

        // insert 시
        if(e.getSource() == Insert_Button) {
            insert = new Insert();
            insert.button.addActionListener(this);
        }

        if(this.insert != null) {
            String ssn = null;
            if(e.getSource() == insert.button) {
                String text_FirstName,text_MiddleInit,text_LastName,text_Ssn,
                        text_Birthdate,text_Address,box_Sex,text_Salary,text_Super_ssn,text_Dno = "NULL";
                ssn = insert.text_Ssn.getText();
                text_FirstName = "'" + insert.text_FirstName.getText()+"'";
                text_MiddleInit = "'" + insert.text_MiddleInit.getText()+"'";
                text_LastName = "'" + insert.text_LastName.getText() + "'";
                text_Ssn = "'" + insert.text_Ssn.getText() + "'";
                text_Birthdate = "'" + insert.text_Birthdate.getText() + "'";
                text_Address = "'" + insert.text_Address.getText() + "'";
                box_Sex = "'" + insert.box_Sex.getSelectedItem().toString() + "'";
                text_Salary = insert.text_Salary.getText();
                text_Super_ssn = "'" + insert.text_Super_ssn.getText() + "'";
                text_Dno = insert.text_Dno.getText();

                if(text_MiddleInit.equals("'" + "'")) {
                    text_MiddleInit = "null";
                }else if(text_Birthdate.equals("'" + "'")) {
                    text_Birthdate = "null";
                }else if(text_Address.equals("'" + "'")) {
                    text_Address = "null";
                }else if(text_Salary.equals("'" + "'")) {
                    text_Salary = "null";
                }else if(text_Super_ssn.equals("'" + "'")) {
                    text_Super_ssn = "null";
                }else if(text_Dno.equals("'" + "'")) {
                    text_Dno = "null";
                }

                if( isStringEmpty(text_FirstName) || isStringEmpty(text_LastName) ||
                        isStringEmpty(text_Ssn) || isStringEmpty(text_Dno)) {
                    JOptionPane.showMessageDialog(null, "FirstName, LastName,Ssn,Dno는 비어있으면 안됩니다.");
                }else {
                    try {
                        String sql = "insert into Employee(Fname,Minit,Lname,Ssn,Bdate,Address,Sex,"
                                + "Salary,Super_ssn,Dno) "
                                + "values("+text_FirstName+","+
                                text_MiddleInit+","+
                                text_LastName+","+
                                text_Ssn+","+
                                text_Birthdate+","+
                                text_Address+","+
                                box_Sex+","+
                                Integer.parseInt(text_Salary)+","+
                                text_Super_ssn+","+
                                Integer.parseInt(text_Dno)+");";

                        Statement s = conn.createStatement();
                        int result = s.executeUpdate(sql);
                        insert.dispose();
                        JOptionPane.showMessageDialog(null, "직원 등록이 완료되었습니다.");
                    } catch (Exception e1) {
                        String error_string = e1.getMessage().toString();

                        if(error_string.contains("Duplicate entry")) {
                            JOptionPane.showMessageDialog(null, "Ssn이 존재합니다. 다른 Ssn을 입력해주세요.");
                        }else if(error_string.contains("Bdate")){
                            JOptionPane.showMessageDialog(null, "생일을 올바르게 입력해주세요");
                        }else if(error_string.contains("For input string")){
                            JOptionPane.showMessageDialog(null, "Salary와 Dno는 숫자를 입력해야 합니다.");
                        }else if(error_string.contains("Minit")){
                            JOptionPane.showMessageDialog(null, "Minit에는 한 글자만 입력해주세요.");
                        }else if(error_string.contains("Ssn")){
                            JOptionPane.showMessageDialog(null, "Ssn은 9자리까지만 입력 가능합니다.");
                        }else {
                            JOptionPane.showMessageDialog(null, "다시 입력해주시기 바랍니다.");
                        }

                    }

                    try {
                        String updateStmt = "update employee set created = current_timestamp(),modified = current_timestamp() where ssn = ?;";

                        PreparedStatement p = conn.prepareStatement(updateStmt);
                        p.clearParameters();
                        p.setString(1, insert.text_Ssn.getText());
                        p.executeUpdate();
                    }catch(SQLException e2) {
                        e2.printStackTrace();
                    }

                }
            }
        }
    }

    public boolean isStringEmpty(String str) {
        return str.isEmpty() || str == null;
    }

    class Insert extends JFrame {
        JButton button = new JButton("정보 추가하기");
        JLabel set_FirstName = new JLabel("First Name :\t");
        JTextField text_FirstName = new JTextField(10);
        JLabel set_MiddleInit = new JLabel("Middle Init :\t");
        JTextField text_MiddleInit = new JTextField(10);
        JLabel set_LastName = new JLabel("Last Name :\t");
        JTextField text_LastName = new JTextField(10);
        JLabel set_Ssn = new JLabel("Ssn :\t\t\t\t\t\t\t\t\t\t\t\t");
        JTextField text_Ssn = new JTextField(10);
        JLabel set_Birthdate = new JLabel("Birthdate :\t\t\t");
        JTextField text_Birthdate = new JTextField(10);
        JLabel set_Address = new JLabel("Address :\t\t\t\t");
        JTextField text_Address = new JTextField(10);
        JLabel set_Sex = new JLabel("Sex :\t\t\t\t\t\t\t\t\t\t\t\t\t ");
        String[] sex = {"F","M"};
        JComboBox box_Sex = new JComboBox(sex);

        JLabel set_Salary = new JLabel("Salary : \t\t\t\t\t\t\t");
        JTextField text_Salary = new JTextField(10);

        JLabel set_Super_ssn = new JLabel("Super_ssn :\t");
        JTextField text_Super_ssn = new JTextField(10);

        JLabel set_Dno = new JLabel("Dno : \t\t\t\t\t\t\t\t\t");
        JTextField text_Dno = new JTextField(10);


        public Insert() {
            JPanel FirstName = new JPanel();
            FirstName.setLayout(new FlowLayout(FlowLayout.LEFT));
            FirstName.add(set_FirstName);
            FirstName.add(text_FirstName);

            JPanel MiddleInit = new JPanel();
            MiddleInit.setLayout(new FlowLayout(FlowLayout.LEFT));
            MiddleInit.add(set_MiddleInit);
            MiddleInit.add(text_MiddleInit);

            JPanel LastName = new JPanel();
            LastName.setLayout(new FlowLayout(FlowLayout.LEFT));
            LastName.add(set_LastName);
            LastName.add(text_LastName);

            JPanel Ssn = new JPanel();
            Ssn.setLayout(new FlowLayout(FlowLayout.LEFT));
            Ssn.add(set_Ssn);
            Ssn.add(text_Ssn);

            JPanel Birthdate = new JPanel();
            Birthdate.setLayout(new FlowLayout(FlowLayout.LEFT));
            Birthdate.add(set_Birthdate);
            Birthdate.add(text_Birthdate);

            JPanel Address = new JPanel();
            Address.setLayout(new FlowLayout(FlowLayout.LEFT));
            Address.add(set_Address);
            Address.add(text_Address);

            JPanel Sex = new JPanel();
            Sex.setLayout(new FlowLayout(FlowLayout.LEFT));
            Sex.add(set_Sex);
            Sex.add(box_Sex);

            JPanel Salary = new JPanel();
            Salary.setLayout(new FlowLayout(FlowLayout.LEFT));
            Salary.add(set_Salary);
            Salary.add(text_Salary);

            JPanel Super_ssn = new JPanel();
            Super_ssn.setLayout(new FlowLayout(FlowLayout.LEFT));
            Super_ssn.add(set_Super_ssn);
            Super_ssn.add(text_Super_ssn);

            JPanel Dno = new JPanel();
            Dno.setLayout(new FlowLayout(FlowLayout.LEFT));
            Dno.add(set_Dno);
            Dno.add(text_Dno);

            JPanel button_panel = new JPanel();
            button_panel.setLayout(new FlowLayout(FlowLayout.CENTER));
            button_panel.add(button);


            JPanel Top = new JPanel();
            Top.setLayout(new BoxLayout(Top, BoxLayout.Y_AXIS));
            Top.add(FirstName);
            Top.add(MiddleInit);
            Top.add(LastName);
            Top.add(Ssn);
            Top.add(Birthdate);
            Top.add(Address);
            Top.add(Sex);
            Top.add(Salary);
            Top.add(Super_ssn);
            Top.add(Dno);
            Top.add(button_panel);


            add(Top, BorderLayout.CENTER);

            setTitle("직원 추가");
            setSize(300, 500);
            setLocationRelativeTo(null);
            setVisible(true);
        }
    }
}




