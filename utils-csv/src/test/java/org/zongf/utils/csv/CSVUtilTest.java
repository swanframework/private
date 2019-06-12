package org.zongf.utils.csv;

import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @Description: csv工具类测试类
 * @since 1.0
 * @author: zongf
 * @date: 2019-06-12 11:37
 */
public class CSVUtilTest {

    // 测试使用默认属性解析
    @Test
    public void test1() throws Exception{

        String filePath = "src/test/resources/student-update.csv";

        List<Student> students = CSVUtil.parse(filePath, Student.class, true);

        students.forEach(System.out::println);
    }

    // 测试自定义属性解析
    @Test
    public void test2() throws Exception{

        String filePath = "src/test/resources/student-update.csv";

        // 指定属性顺序
        String[] properties = new String[]{"id", "name", "chinese", "math", "english", "desc"};

        List<Student> students = CSVUtil.parse(filePath, Student.class, properties, true);

        students.forEach(System.out::println);
    }

    // 测试指定排除属性解析
    @Test
    public void test3() throws Exception{

        String filePath = "src/test/resources/student-save.csv";

        // 指定排除属性
        List<Student> students = CSVUtil.parse(filePath,  Student.class, true, "id");

        students.forEach(System.out::println);
    }

    // 测试写入文件
    @Test
    public void test_write1(){

        List<Object[]> contents = new ArrayList<>();
        contents.add(new Object[]{"id", "userName", "firstName", "lastName", "birthday"});
        contents.add(new Object[]{1, "john73", "John", "Doe", LocalDate.of(1973, 9, 15)});
        contents.add(null);
        contents.add(new Object[]{2, "mary", "Mary", "Meyer", LocalDate.of(1985, 3, 29)});
        CSVUtil.write("src/test/resources/hi.csv", contents);
    }

    // 测试写入文件
    @Test
    public void test_write2(){
        List<Student> studentList = new ArrayList<>();
        studentList.add(new Student(1001, "张三", 90, 80, 70, "语文好, 英语差"));
        studentList.add(new Student(1002, "李四", 80, 70, 90, "英语好, 数学差"));
        studentList.add(new Student(1003, "王五", 70, 90, 80, "数学好, 语文差"));

        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        map.put("学号", "id");
        map.put("姓名", "name");
        map.put("语文", "chinese");
        map.put("数学", "math");
        map.put("英语", "english");
        map.put("描述", "desc");

        CSVUtil.write("src/test/resources/hello.csv", true, 0, studentList, map);
    }

}
