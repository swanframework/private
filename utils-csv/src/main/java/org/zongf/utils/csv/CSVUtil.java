package org.zongf.utils.csv;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.lang.reflect.Field;
import java.util.*;

/**
 * @Description:  csv 文件解析/生成工具类
 * @since 1.0
 * @author: zongf
 * @time: 2019-06-12 11:37:57
 */
public class CSVUtil {

    /**
     * @Description: 解析CSV文件为Java 对象集合, 默认情况下, java属性声明顺序与csv列顺序一一对应
     * @param csvFilePath 文件路径
     * @param clz 对象类型
     * @param skipFirst 是否跳过文件首行
     * @param ignoreFields 忽略的属性列表
     * @return: List
     * @since 1.0
     * @author: zongf
     * @time: 2019-06-12 13:46:15
     */
    public static <T> List<T> parse(String csvFilePath, Class<T> clz, boolean skipFirst, String... ignoreFields) {

        // 获取类声明的所有属性
        Field[] fields = clz.getDeclaredFields();

        // 获取csv每列映射的字段名列表
        List<String> fieldList = new ArrayList<>();
        out: for (Field field : fields) {
           for (String ignoreField : ignoreFields) {
                if (field.getName().equals(ignoreField)) {
                    continue out;
                }
            }
            fieldList.add(field.getName());
        }

        // 将字段名称列表转换为字段名称数组
        String[] fieldNames = new String[fieldList.size()];
        for (int i = 0; i < fieldList.size(); i++) {
            fieldNames[i] = fieldList.get(i);
        }

        // 解析文件
        return parse(csvFilePath, clz, fieldNames, skipFirst);
    }

    /**
     * @Description: 解析CSV文件为Java 对象集合, 默认情况下, java属性声明顺序与csv列顺序一一对应
     * @param csvFilePath 文件路径
     * @param clz 对象类型
     * @param skipFirst 是否跳过文件首行
     * @return: List
     * @since 1.0
     * @author: zongf
     * @time: 2019-06-12 13:46:15
     */
    public static <T> List<T> parse(String csvFilePath, Class<T> clz, boolean skipFirst) {
        // 获取类声明字段列表
        Field[] fields = clz.getDeclaredFields();

        // 获取字段与csv 映射关系
        String[] fieldNames = new String[fields.length];
        for (int i = 0; i < fields.length; i++) {
            fieldNames[i] = fields[i].getName();
        }

        // 解析文件
        return parse(csvFilePath, clz, fieldNames, skipFirst);
    }

    /**
     * @Description: 解析CSV文件为Java 对象集合
     * @param csvFilePath 文件路径
     * @param clz 对象类型
     * @param fieldNames csv文件列与java属性映射关系. csv列从左向右, 顺序不能变
     * @param skipFirst 是否跳过文件首行
     * @return List
     * @since 1.0
     * @author: zongf
     * @time: 2019-06-12 13:46:15
     */
    public static <T> List<T> parse(String csvFilePath, Class<T> clz, String[] fieldNames, boolean skipFirst){
        // 创建保存结果集合
        List<T> list = new ArrayList<>();

        // 创建csv 文件解析器
        CSVFormat csvFormat = CSVFormat.RFC4180.withHeader(fieldNames);

        // 是否忽略首行
        if(skipFirst) csvFormat = csvFormat.withSkipHeaderRecord();

        try {

            // 解析文件
            CSVParser parser = csvFormat.parse(new FileReader(csvFilePath));

            while (parser.iterator().hasNext()) {

                CSVRecord record = parser.iterator().next();

                // 创建实例
                T t = clz.newInstance();

                // 设置属性
                for (String property : fieldNames) {
                    Field field = clz.getDeclaredField(property);
                    setValue(field, t, record.get(property));
                }
                list.add(t);
            }
        } catch (Exception e) {
            throw new RuntimeException(csvFilePath + "-解析csv文件失败", e);
        }
        return list;
    }

    /**
     * @Description: 生成csv 文件, 一次性写入文件. 如果文件存在, 则进行覆盖
     * @param csvFilePath csv 文件路径名
     * @param contents 文件内容
     * @param titles 文件标题行
     * @since 1.0
     * @author: zongf
     * @time: 2019-06-12 14:27:15
     */
    public static <T> void write(String csvFilePath, List<Object[]> contents, String... titles) {
        write(csvFilePath, true, 0, contents,  titles);
    }

    /**
     * @Description: 生成csv 文件, 一次性写入文件
     * @param csvFilePath csv 文件路径名
     * @param overrideFile 如果csv文件已存在, 是否进行覆盖
     * @param contents 文件内容
     * @param titles 文件标题行
     * @since 1.0
     * @author: zongf
     * @time: 2019-06-12 14:27:15
     */
    public static <T> void write(String csvFilePath, boolean overrideFile, List<Object[]> contents, String... titles) {
        write(csvFilePath, overrideFile, 0, contents,  titles);
    }

    /**
     * @Description: 生成csv 文件
     * @param csvFilePath csv 文件路径名
     * @param flushSize 批量刷新行数, 设置为0 表示一次性写入文件
     * @param contents 文件内容
     * @param titles 文件标题行
     * @since 1.0
     * @author: zongf
     * @time: 2019-06-12 14:27:15
     */
    public static <T> void write(String csvFilePath,  int flushSize, List<Object[]> contents, Object... titles) {
        write(csvFilePath, true, 0, contents,  titles);
    }

    /**
     * @Description: 生成csv 文件
     * @param csvFilePath csv 文件路径名
     * @param overrideFile 如果csv文件已存在, 是否进行覆盖
     * @param flushSize 批量刷新行数, 设置为0 表示一次性写入文件
     * @param contents 文件内容
     * @param titles 文件标题行
     * @since 1.0
     * @author: zongf
     * @time: 2019-06-12 14:27:15
     */
    public static <T> void write(String csvFilePath, boolean overrideFile,  int flushSize, List<Object[]> contents, Object... titles) {

        CSVPrinter csvPrinter = null;
        try {

            File file = new File(csvFilePath);

            // 校验文件是否存在
            if (file.exists() && !overrideFile) {
                throw new RuntimeException(csvFilePath + "-文件存在!");
            }

            // 创建写入流
            csvPrinter = new CSVPrinter(new FileWriter(file), CSVFormat.DEFAULT);

            // 写入标题
            if (titles != null && titles.length > 0) {
                csvPrinter.printRecord(titles);
            }

            // 写入内容
            for (int i = 0; i < contents.size(); i++) {
                Object[] content = contents.get(i);

                // 如果为null, 则写入空行
                if (content == null) {
                    csvPrinter.println();
                }else {
                    csvPrinter.printRecord(content);
                }

                // 批量刷新
                if (flushSize != 0 && i % flushSize == 0) {
                    csvPrinter.flush();
                }
            }

            // 刷新
            csvPrinter.flush();
        } catch (IOException e) {
            throw new RuntimeException("写入csv文件异常!", e);
        } finally {
            close(csvPrinter);
        }
    }

    /**
     * @Description: 生成csv 文件, 文件存在时覆盖, 一次性写入
     *
     * @param csvFilePath csv 文件路径名
     * @param contents 文件内容
     * @param mapping 文件标题行与字段映射关系
     * @since 1.0
     * @author: zongf
     * @time: 2019-06-12 14:27:15
     */
    public static <T> void write(String csvFilePath, List<T> contents, LinkedHashMap<String, String> mapping) {
        write(csvFilePath, true, 0, contents, mapping);
    }

    /**
     * @Description: 生成csv 文件, 一次性写入文件
     * @param csvFilePath csv 文件路径名
     * @param overrideFile 如果csv文件已存在, 是否进行覆盖
     * @param contents 文件内容
     * @param mapping 文件标题行与字段映射关系
     * @since 1.0
     * @author: zongf
     * @time: 2019-06-12 14:27:15
     */
    public static <T> void write(String csvFilePath, boolean overrideFile, List<T> contents, LinkedHashMap<String, String> mapping) {
        write(csvFilePath, overrideFile, 0, contents, mapping);
    }

    /**
     * @Description: 生成csv 文件, 文件存在时覆盖
     * @param csvFilePath csv 文件路径名
     * @param flushSize 批量刷新行数, 设置为0 表示一次性写入文件
     * @param contents 文件内容
     * @param mapping 文件标题行与字段映射关系
     * @since 1.0
     * @author: zongf
     * @time: 2019-06-12 14:27:15
     */
    public static <T> void write(String csvFilePath, int flushSize, List<T> contents, LinkedHashMap<String, String> mapping) {
        write(csvFilePath, true, flushSize, contents, mapping);
    }

    /**
     * @Description: 生成csv 文件
     * @param csvFilePath csv 文件路径名
     * @param overrideFile 如果csv文件已存在, 是否进行覆盖
     * @param flushSize 批量刷新行数, 设置为0 表示一次性写入文件
     * @param contents 文件内容
     * @param mapping 文件标题行与字段映射关系
     * @since 1.0
     * @author: zongf
     * @time: 2019-06-12 14:27:15
     */
    public static <T> void write(String csvFilePath, boolean overrideFile, int flushSize, List<T> contents, LinkedHashMap<String, String> mapping) {

        // 校验参数
        if (contents == null || contents.size() == 0) {
            throw new RuntimeException("文件内容不能为空");
        }

        // 获取标题
        Set<String> titles = mapping.keySet();

        // 获取字段名
        Collection<String> fieldNames = mapping.values();

        // 字段列表
        List<Field> fieldList = new ArrayList<>();

        try {
            // 获取字段列表
            Class<?> clz = contents.get(0).getClass();
            for (String fieldName : fieldNames) {
                Field declaredField = clz.getDeclaredField(fieldName);
                declaredField.setAccessible(true);
                fieldList.add(declaredField);
            }

            // 将每个对象的字段值封装为一个对象数组
            List<Object[]> contentList = new ArrayList<>();
            for (T content : contents) {

                List<Object> fieldValueList = new ArrayList<>();

                for (Field field : fieldList) {
                    fieldValueList.add(field.get(content));
                }

                contentList.add(fieldValueList.toArray());
            }

            // 生成文件
            write(csvFilePath, overrideFile, flushSize, contentList, titles.toArray());

        } catch (Exception e) {
            throw new RuntimeException(csvFilePath + "-csv文件创建异常!", e);
        } finally {
            // 关闭字段访问
            for (Field field : fieldList) {
                field.setAccessible(false);
            }
        }
    }

    /**
     * @Description: 反射为属性赋值
     * @param field 字段
     * @param target 目标对象
     * @param value 值
     * @since 1.0
     * @author: zongf
     * @time: 2019-06-12 13:55:59
     */
    private static void setValue(Field field, Object target, String value) {
        // 如果value 为null, 则返回
        if(value == null) return;

        // 设置字段可进行修改
        field.setAccessible(true);

        try {
            if (String.class.equals(field.getType())) {
                field.set(target, value);
            } else if (int.class.equals(field.getType()) || Integer.class.equals(field.getType())) {
                field.set(target, Integer.valueOf(value));
            } else if (float.class.equals(field.getType()) || Float.class.equals(field.getType())) {
                field.set(target, Float.valueOf(value));
            } else if (double.class.equals(field.getType()) || Double.class.equals(field.getType())) {
                field.set(target, Double.valueOf(value));
            } else if (boolean.class.equals(field.getType()) || Boolean.class.equals(field.getType())) {
                field.set(target, Boolean.valueOf(value));
            } else if (char.class.equals(field.getType()) || Character.class.equals(field.getType())) {
                if (value != null && value.toCharArray().length > 0) {
                    field.set(target, value.toCharArray()[0]);
                }
            } else if (byte.class.equals(field.getType()) || Byte.class.equals(field.getType())) {
                field.set(target, Byte.valueOf(value));
            } else if (short.class.equals(field.getType()) || Short.class.equals(field.getType())) {
                field.set(target, Short.valueOf(value));
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException("设置字段异常, 字段名:" + field.getName() + ", 值:" + value, e);
        }finally {
            field.setAccessible(false);
        }
    }


    /**
     * @Description: 关闭流
     * @param closeable 任意可关闭对象
     * @since 1.0
     * @author: zongf
     * @time: 2019-06-12 14:29:43
     */
    private static void close(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
