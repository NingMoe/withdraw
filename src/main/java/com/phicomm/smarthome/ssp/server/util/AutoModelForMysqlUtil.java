package com.phicomm.smarthome.ssp.server.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JOptionPane;

import com.phicomm.smarthome.util.OptDateUtil;

/**
 * @author fujiang.mao
 * @date 2017-08-1
 * @version V1.0
 * @Description: 根据MySQL表字段自动生成Java实体类 注：默认连接为开发库，如有其他数据库连接需要，自行配置；
 *               生成实体字段个别需要手动导入包； 生成实体后请手动刷新实体所在路径包；
 */
public class AutoModelForMysqlUtil {

    // 数据库连接
    private static final String URL = "jdbc:mysql://172.31.34.128:3306/"
            + JOptionPane.showInputDialog("请输入数据库名称", "stat_sharedwifi");
    private static final String NAME = "root";
    private static final String PASS = "root";
    private static final String DRIVER = "com.mysql.jdbc.Driver";

    private static String packageOutPath = JOptionPane.showInputDialog("请输入生成实体所在包路径",
            "main.java.com.phicomm.smarthome.ssp.server.model");
    private static String tablename = JOptionPane.showInputDialog("请输入表名", "");
    private static String authorName = JOptionPane.showInputDialog("请输入作者名字", "fujiang.mao");

    private String[] colnames; // 列名数组
    private String[] colTypes; // 列名类型数组
    private int[] colSizes; // 列名大小数组
    private boolean isUtil = false; // 是否需要导入包java.util.*
    private boolean isSql = false; // 是否需要导入包java.sql.*

    /**
     * 构造函数
     */
    @SuppressWarnings("static-access")
    public AutoModelForMysqlUtil() {
        // 创建连接
        Connection con = null;
        // 查要生成实体类的表
        String sql = "select * from " + tablename;
        PreparedStatement preparedStatement = null;
        try {
            try {
                Class.forName(DRIVER);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            con = DriverManager.getConnection(URL, NAME, PASS);
            preparedStatement = con.prepareStatement(sql);
            ResultSetMetaData rsmd = preparedStatement.getMetaData();
            int size = rsmd.getColumnCount();// 统计列
            colnames = new String[size];
            colTypes = new String[size];
            colSizes = new int[size];
            Map<String, Object> paramMap = new HashMap<String, Object>();
            ResultSet comments = con.createStatement().executeQuery("show full columns from " + tablename);
            while (comments.next()) {
                paramMap.put(comments.getString("Field"), comments.getString("Comment"));
            }
            for (int i = 0; i < size; i++) {
                colnames[i] = rsmd.getColumnName(i + 1);
                colTypes[i] = rsmd.getColumnTypeName(i + 1);
                if (colTypes[i].equalsIgnoreCase("datetime")) {
                    isUtil = true;
                }
                if (colTypes[i].equalsIgnoreCase("image") || colTypes[i].equalsIgnoreCase("text")) {
                    isSql = true;
                }
                colSizes[i] = rsmd.getColumnDisplaySize(i + 1);
            }
            String content = parse(colnames, colTypes, colSizes, paramMap);
            try {
                File directory = new File("");
                String outputPath = directory.getAbsolutePath() + "/src/" + this.packageOutPath.replace(".", "/") + "/"
                        + initcap(tablename) + "Model" + ".java";
                FileWriter fw = new FileWriter(outputPath);
                PrintWriter pw = new PrintWriter(fw);
                pw.println(content);
                pw.flush();
                pw.close();
                System.out.println("实体创建成功！实体所在路径：" + packageOutPath + "." + initcap(tablename) + ".java");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 生成实体类主体代码
     * 
     * @param colnames
     * @param colTypes
     * @param colSizes
     * @param paramMap
     * @return
     */
    @SuppressWarnings("static-access")
    private String parse(String[] colnames, String[] colTypes, int[] colSizes, Map<String, Object> paramMap) {
        StringBuffer sb = new StringBuffer();
        sb.append("package " + this.packageOutPath.replace("main.java.", "") + ";\r\n");
        sb.append("\r\n");
        // 判断是否导入工具包
        if (isUtil) {
            sb.append("import java.util.Date;\r\n");
        }
        if (isSql) {
            sb.append("import java.sql.*;\r\n");
        }
        // 注释内容部分
        sb.append("/**\r\n");
        sb.append(" * @author " + this.authorName + "\r\n");
        sb.append(" * @date " + OptDateUtil.nowDate() + "\r\n");
        sb.append(" * @version V1.0" + "\r\n");
        sb.append(" * @Description " + tablename + "实体类" + "\r\n");
        sb.append(" */");
        // 实体内容部分
        sb.append("\r\npublic class " + initcap(tablename) + "Model" + " {\r\n");
        processAllAttrs(sb, paramMap);// 属性
        processAllMethod(sb);// get set方法
        sb.append("}\r\n");
        return sb.toString();
    }

    /**
     * 生成所有属性
     * 
     * @param sb
     * @param paramMap
     */
    private void processAllAttrs(StringBuffer sb, Map<String, Object> paramMap) {
        for (int i = 0; i < colnames.length; i++) {
            String commont = "";// 生成字段注释
            if (paramMap.get(colnames[i]) != null && !"".equals(paramMap.get(colnames[i]))) {
                commont = "// " + paramMap.get(colnames[i]);
            }
            sb.append("\n\t@JsonProperty(\"" + colnames[i] + "\")\n");
            sb.append("\tprivate " + sqlType2JavaType(colTypes[i]) + " "
                    + replaceUnderlineAndfirstToUpper(colnames[i], "_", "") + ";" + commont + "\r\n");
        }
    }

    /**
     * 生成所有方法
     * 
     * @param sb
     */
    private void processAllMethod(StringBuffer sb) {
        sb.append("\n");
        for (int i = 0; i < colnames.length; i++) {
            sb.append("\tpublic void set" + initcap(replaceUnderlineAndfirstToUpper(colnames[i], "_", "")) + "("
                    + sqlType2JavaType(colTypes[i]) + " " + replaceUnderlineAndfirstToUpper(colnames[i], "_", "")
                    + ") {\r\n");
            sb.append("\t\tthis." + replaceUnderlineAndfirstToUpper(colnames[i], "_", "") + "="
                    + replaceUnderlineAndfirstToUpper(colnames[i], "_", "") + ";\r\n");
            sb.append("\t} \r\n\n");
            sb.append("\tpublic " + sqlType2JavaType(colTypes[i]) + " get"
                    + initcap(replaceUnderlineAndfirstToUpper(colnames[i], "_", "")) + "() {\r\n");
            sb.append("\t\treturn " + replaceUnderlineAndfirstToUpper(colnames[i], "_", "") + ";\r\n");
            sb.append("\t} \r\n\n");
        }
    }

    /**
     * 将输入字符串的首字母改成大写
     * 
     * @param str
     * @return
     */
    private String initcap(String str) {
        char[] ch = str.toCharArray();
        if (ch[0] >= 'a' && ch[0] <= 'z') {
            ch[0] = (char) (ch[0] - 32);
        }
        String rs = new String(ch);
        return replaceUnderlineAndfirstToUpper(rs, "_", "");
    }

    /**
     * 获得列的数据类型
     * 
     * @param sqlType
     * @return 注：类型不完全，可自行添加
     */
    private String sqlType2JavaType(String sqlType) {
        if (sqlType.equalsIgnoreCase("varchar") || sqlType.equalsIgnoreCase("char")
                || sqlType.equalsIgnoreCase("nvarchar") || sqlType.equalsIgnoreCase("nchar")
                || sqlType.equalsIgnoreCase("text") || sqlType.equalsIgnoreCase("date")
                || sqlType.equalsIgnoreCase("timestamp")) {
            return "String";
        } else if (sqlType.equalsIgnoreCase("int")) {
            return "int";
        } else if (sqlType.equalsIgnoreCase("bigint")) {
            return "long";
        } else if (sqlType.equalsIgnoreCase("datetime")) {
            return "Date";
        } else if (sqlType.equalsIgnoreCase("numeric") || sqlType.equalsIgnoreCase("real")
                || sqlType.equalsIgnoreCase("money") || sqlType.equalsIgnoreCase("smallmoney")) {
            return "double";
        } else if (sqlType.equalsIgnoreCase("bit")) {
            return "boolean";
        } else if (sqlType.equalsIgnoreCase("tinyint")) {
            return "byte";
        } else if (sqlType.equalsIgnoreCase("smallint")) {
            return "short";
        } else if (sqlType.equalsIgnoreCase("float")) {
            return "float";
        } else if (sqlType.equalsIgnoreCase("image")) {
            return "Blod";
        } else if (sqlType.equalsIgnoreCase("decimal")) {
            return "BigDecimal";
        }
        return null;
    }

    /**
     * 替换字符串并让它的下一个字母为大写
     * 
     * @param srcStr
     * @param org
     * @param ob
     * @return
     */
    public static String replaceUnderlineAndfirstToUpper(String srcStr, String org, String ob) {
        String newString = "";
        int first = 0;
        while (srcStr.indexOf(org) != -1) {
            first = srcStr.indexOf(org);
            if (first != srcStr.length()) {
                newString = newString + srcStr.substring(0, first) + ob;
                srcStr = srcStr.substring(first + org.length(), srcStr.length());
                srcStr = firstCharacterToUpper(srcStr);
            }
        }
        newString = newString + srcStr;
        return newString;
    }

    /**
     * 首字母大写
     * 
     * @param srcStr
     * @return
     */
    public static String firstCharacterToUpper(String srcStr) {
        return srcStr.substring(0, 1).toUpperCase() + srcStr.substring(1);
    }

    public static void main(String[] args) {
        if (packageOutPath == null || authorName == null || tablename == null
                || "jdbc:mysql://172.31.34.128:3306/".equals(URL)) {
            return;
        }
        new AutoModelForMysqlUtil();
    }
}