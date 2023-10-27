package CompanyDataTable.View;

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ViewTable extends JFrame implements ActionListener { // JFrame :  Java Swing 라이브러리에서 제공되는 GUI frame inherit // ActionListener : Java Swing에서 사용자의 액션(예: 버튼 클릭)에 대한 이벤트 처리를 위한 인터페이스
    public static void main(String[] args) {
        new ViewTable();
    }
    // ComboBox 선언 시작
    public JComboBox Category;
    public JComboBox Dept;
    public JComboBox Sex;
    public JComboBox Update;
    // ComboBox 선언 종료

    // User로부터 text input 받을 변수 선언 시작
    public JTextField setSalary_Bdate_employee = new JTextField(10); // User의 text input을 받는다
    public JTextField update_Salary_Address_Sex = new JTextField(10);
    // User로부터 text input 받을 변수 선언 종료

    // CheckBox 이름과 초기 상태 선언 시작
    public JCheckBox check1 = new JCheckBox("Name", true);
    public JCheckBox check2 = new JCheckBox("Ssn", true);
    public JCheckBox check3 = new JCheckBox("Bdate", true);
    public JCheckBox check4 = new JCheckBox("Address", true);
    public JCheckBox check5 = new JCheckBox("Sex", true);
    public JCheckBox check6 = new JCheckBox("Salary", true);
    public JCheckBox check7 = new JCheckBox("Supervisor", true);
    public JCheckBox check8 = new JCheckBox("Department", true);
    // CheckBox 이름과 초기 상태 선언 종료

    // 테이블의 열 제목을 저장하기 위해 Vector(동적으로 크기가 조절되는 배열) 선언 시작
    public Vector<String> Head = new Vector<String>();
    // 테이블의 열 제목을 저장하기 위해 Vector(동적으로 크기가 조절되는 배열) 선언 종료

    // 테이블 형식으로 데이터를 관리하기 위해 테이블 선언 시작
    public JTable table;
    public DefaultTableModel model;
    // 테이블 형식으로 데이터를 관리하기 위해 테이블 선언 종료

    // 업데이트 했을때 사용하기 위한 프로퍼티 선언 시작
    public int BOOLEAN_COLUMN = 0;
    public int NAME_COLUMN = 0;
    public int SALARY_COLUMN = 0;
    public int ADDRESS_COLUMN = 0;
    public int SEX_COLUMN = 0;
    public int DEPT_SALARY_COLUMN_R = 0;
    public int DEPT_SALARY_COLUMN_A = 0;
    public int DEPT_SALARY_COLUMN_H = 0;
    public String dShow;
    // 업데이트 했을때 사용하기 위한 프로퍼티 선언 종료


    // 화면에 보이는 버튼 선언 시작
    public JButton Search_Button = new JButton("검색");	// 검색 버튼
    public JButton Update_Button = new JButton("UPDATE");	// 버튼들
    public JButton Delete_Button = new JButton("선택한 데이터 삭제");
    public JButton Insert_Button = new JButton("직원 추가");
    // 화면에 보이는 버튼 선언 종료

    // 화면에 보이는 라벨 선언 시작
    public JLabel totalEmp = new JLabel("인원수 : "); // 총 인원수를 나타내는 label
    public JLabel totalCount = new JLabel(); // 총 선택된 인원수를 나타내는 label
    public JLabel empLabel = new JLabel("선택한 직원: "); // 선택된 직원의 label
    public JLabel showSelectedEmp = new JLabel(); // 나중에 선택된 직원의 label
    public JLabel setLabel = new JLabel("수정: "); // 수정할 목록과 수정할 내용을 표시하는 label
    // 화면에 보이는 라벨 선언 종료


    // 스크롤바 선언 시작
    public JPanel panel;
    public JScrollPane scrollPane;	// 스크롤 가능한 컨테이너로 제작
    public JPanel ComboBoxPanel = new JPanel();
    // 스크롤바 선언 종료

    // 인원수를 세기위한 변수인 count 선언 시작
    public int count = 0;
    // 인원수를 세기위한 변수인 count 선언 종료

    public Container thisContainer = this;	// 자기 자신을 나타낸다. 즉, Container 그 자체
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
        setTitle("Company Information");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1300, 600);
        setLocationRelativeTo(null);
        setVisible(true);
        // layout 설정 종료
    }
}




