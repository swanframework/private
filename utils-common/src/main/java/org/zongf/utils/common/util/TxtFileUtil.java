package org.zongf.utils.common.util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**文本文件读写, 当文件内容过大时, 需要考虑内存
 * @since 1.0
 * @author zongf
 * @created 2019-07-01
 */
public class TxtFileUtil {

    /** utf-8 编码*/
    private static String ENCODING_UTF8 = "UTF-8";

    /** gbk 编码 */
    private static String ENCODING_GBK = "GBK";

    /** gb2312 编码 */
    private static String ENCODING_GB2312 = "GB2312";

    /**读取文件内容, 默认以UTF-8编码打开文件
     * @param filePath 文件路径, 支持相对路径和绝对路径
     * @return 文本文件内容
     * @since 1.0
     * @author zongf
     * @created 2019-07-01
     */
    public static List<String> readFile(String filePath){
        return readFile(filePath, line -> false, ENCODING_UTF8);
    }

    /**读取文件内容, 以指定编码格式打开文件
     * @param filePath 文件路径, 支持相对路径和绝对路径
     * @param encoding 文件编码
     * @return List<String> 文件内容
     * @since 1.0
     * @author zongf
     * @created 2019-07-01
     */
    public static List<String> readFile(String filePath, String encoding){
        return readFile(filePath, line -> false, encoding);
    }

    /**读取文件内容, 可忽略空行
     * @param filePath 文件路径, 支持相对路径和绝对路径
     * @param ignoreEmptyLine 忽略空行
     * @return List<String> 文件内容
     * @since 1.0
     * @author zongf
     * @created 2019-07-01
     */
    public static List<String> readFile(String filePath, boolean ignoreEmptyLine){
        return readFile(filePath, line -> "".equals(line.trim()), ENCODING_UTF8);
    }

    /**读取文件内容, 以指定编码格式打开文件, 可忽略空行
     * @param filePath 文件路径, 支持相对路径和绝对路径
     * @param ignoreEmptyLine 忽略空行
     * @return List<String> 文件内容
     * @since 1.0
     * @author zongf
     * @created 2019-07-01
     */
    public static List<String> readFile(String filePath, boolean ignoreEmptyLine, String encoding){
        return readFile(filePath, line -> "".equals(line.trim()), encoding);
    }

    /**读取文件内容, 可自定义过滤器, 过滤行. 默认以UTF-8编码打开文件
     * @param filePath 文件路径, 支持相对路径和绝对路径
     * @param filter 过滤器
     * @return List<String> 文件内容
     * @since 1.0
     * @author zongf
     * @created 2019-07-01
     */
    public static List<String> readFile(String filePath, Predicate<String> filter){
        return readFile(filePath, filter, ENCODING_UTF8);
    }

    /**读取文件内容.
     * @param filePath 文件路径, 支持相对路径和绝对路径
     * @param encoding 设置以哪种文件编码打开文件
     * @param ignoreFilter 忽略的行过滤器
     * @return List<String> 文件内容
     * @since 1.0
     * @author zongf
     * @created 2019-07-01
     */
    public static List<String> readFile(String filePath, Predicate<String> ignoreFilter, String encoding) {

        // 创建list 存储文件内容
        List<String> contents = new ArrayList<String>();

        File file = new File(filePath);

        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(file), encoding));

            String line = null;

            while ((line = br.readLine()) != null) {
                if (!ignoreFilter.test(line)) {
                    contents.add(line);
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(filePath + "文件不存在", e);
        } catch (IOException e) {
            throw new RuntimeException(filePath + "文件打开失败", e);
        }finally {
            CloseUtil.close(br);
        }
        return contents;
    }

    /**向文件中追加内容. 默认以UTF-8编码写入文件. 当文件已存在时, 进行文件覆盖
     * @param contents 文件内容
     * @param filePath 文件路径, 支持相对路径和绝对路径
     * @since 1.0
     * @author zongf
     * @created 2019-07-01
     */
    public static void writeFile(List<String> contents, String filePath){
        writeFile(contents, filePath, ENCODING_UTF8, true);
    }

    /**向文件中追加内容, 默认以UTF-8编码写入文件
     * @param contents 文件内容
     * @param filePath 文件路径, 支持相对路径和绝对路径
     * @param overrideFile 当文件已存在时, 是否进行文件覆盖.
     * @since 1.0
     * @author zongf
     * @created 2019-07-01
     */
    public static void writeFile(List<String> contents, String filePath, boolean overrideFile){
        writeFile(contents, filePath, ENCODING_UTF8, overrideFile);
    }

    /**向文件中追加内容, 当文件存在时, 会进行文件覆盖
     * @param contents 文件内容
     * @param filePath 文件路径, 支持相对路径和绝对路径
     * @param encoding 指定新成文件文件编码
     * @since 1.0
     * @author zongf
     * @created 2019-07-01
     */
    public static void writeFile(List<String> contents, String filePath, String encoding){
        writeFile(contents, filePath, encoding, true);
    }

    /**向文件中追加内容.
     * @param contents 文件内容
     * @param filePath 文件路径, 支持相对路径和绝对路径
     * @param encoding 指定新成文件文件编码
     * @param overrideFile 当文件已存在时, 是否进行文件覆盖
     * @since 1.0
     * @author zongf
     * @created 2019-07-01
     */
    public static void writeFile(List<String> contents, String filePath, String encoding, boolean overrideFile) {

        File file = new File(filePath);

        // 校验文件是否存在
        if(file.exists() && !overrideFile){
            throw new RuntimeException("文件已存在!");
        }

        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), encoding));
            for (String content : contents) {
                bw.write(content);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            CloseUtil.close(bw);
        }
    }

    /**向文件中追加内容, 默认以UTF-8格式写入文件, 当文件不存在时创建新的文件.
     * @param contents 文件内容
     * @param filePath 文件路径, 支持相对路径和绝对路径
     * @since 1.0
     * @author zongf
     * @created 2019-07-01
     */
    public static void appendFile(List<String> contents, String filePath){
        appendFile(contents, filePath, ENCODING_UTF8, true);
    }

    /**向文件中追加内容, 默认写入文件编码为UTF-8
     * @param contents 文件内容
     * @param filePath 文件路径, 支持相对路径和绝对路径
     * @param creatNewFile 当文件不存在时, 是否创建新的文件
     * @since 1.0
     * @author zongf
     * @created 2019-07-01
     */
    public static void appendFile(List<String> contents, String filePath, boolean creatNewFile){
        appendFile(contents, filePath, ENCODING_UTF8, creatNewFile);
    }

    /**向文件中追加内容, 当文件不存在时, 创建新文件
     * @param contents 文件内容
     * @param filePath 文件路径, 支持相对路径和绝对路径
     * @param encoding 指定新成文件文件编码
     * @since 1.0
     * @author zongf
     * @created 2019-07-01
     */
    public static void appendFile(List<String> contents, String filePath, String encoding){
        appendFile(contents, filePath, encoding, true);
    }

    /**向文件中追加内容.
     * @param contents 文件内容
     * @param filePath 文件路径, 支持相对路径和绝对路径
     * @param encoding 指定新成文件文件编码
     * @param creatNewFile 当文件不存在时, 是否创建新的文件
     * @since 1.0
     * @author zongf
     * @created 2019-07-01
     */
    public static void appendFile(List<String> contents, String filePath, String encoding, boolean creatNewFile) {

        File file = new File(filePath);

        // 校验文件是否存在
        if(!file.exists() && !creatNewFile){
            throw new RuntimeException("文件不存在!");
        }

        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true), encoding));
            for (String content : contents) {
                bw.write(content);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            CloseUtil.close(bw);
        }
    }

}