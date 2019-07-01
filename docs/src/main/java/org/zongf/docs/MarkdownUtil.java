package org.zongf.docs;

import org.apache.commons.lang3.StringUtils;
import org.zongf.utils.common.util.TxtFileUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/** 生成MarkDown格式的API文档
 * @since 1.0
 * @author zongf
 * @created 2019-07-01
 */
public class MarkdownUtil {

    /**批量生成markdown 文档
     * @param utildir 工具类目录
     * @param mdFileDir 生成markdown 文件目录
     * @since 1.0
     * @author zongf
     * @created 2019-07-01
     */
    public static void createMDDocs(String utildir, String mdFileDir) {

        File dir = new File(utildir);

        if(!dir.exists()) throw new RuntimeException(utildir + "-目录不存在");

        if(!dir.isDirectory()) throw new RuntimeException("不是文件夹");

        for (String fileName : dir.list()) {
            System.out.println("开始解析文件:" + utildir + "/" + fileName);
            createMDDoc(utildir + "/" + fileName , mdFileDir);
        }

    }

    /**生成markdown 文档
     * @param utilFilePath 工具类地址
     * @param mdFileDir md文件生成目录
     * @since 1.0
     * @author zongf
     * @created 2019-07-01
     */
    public static void createMDDoc(String utilFilePath, String mdFileDir) {

        // 转换windows换行符号
        utilFilePath = utilFilePath.replace("\\", "/");

        List<String> lines = new ArrayList<>();
        lines.addAll(getApiTable(utilFilePath));
        lines.add("\n");
        lines.addAll(getSourceCode(utilFilePath));

        // 生成文件
        createFile(utilFilePath, mdFileDir, lines);
    }

    /**获取API表格内容
     * @param utilFilePath 工具类路径
     * @return: List
     * @since 1.0
     * @author zongf
     * @created 2019-07-01
     */
    private static List<String> getApiTable(String utilFilePath) {

        List<String> lines = new ArrayList<>();

        // 读取文件内容
        List<String> contentList = TxtFileUtil.readFile(utilFilePath);

        // 移除所有空格
        contentList.forEach(line->line.trim());

        // 注释, 方法, 结束关键字
        String commentTag = String.valueOf("/**");
        String methodTag = "public static";
        String paramTag = "@param";

        // 标题一
        lines.add("### 1. API 列表\n");

        // 表头处理
        lines.add("| 方法签名 | 方法描述 | 参数说明 |");
        lines.add("| :--- | :--- | :--- |");

        String description = null;
        LinkedHashMap paramMap = new LinkedHashMap();

        for (String line : contentList) {
            if(line.contains(commentTag)){
                description = line.split("\\/\\*\\*")[1].trim();
            } else if (line.contains(paramTag)) {
                // 移除@param字符串
                line = line.split(paramTag)[1].trim();
                String[] array = line.split("\\s+");
                String paramName = array[0];
                String paramDesc = line.split(paramName)[1];
                paramMap.put(paramName, paramDesc);

            } else if (line.contains(methodTag)) {
                String methodName = line.replace("{", "").trim();

                StringBuffer sb = new StringBuffer();
                sb.append("| " + methodName + " | " + description + " | ");

                paramMap.forEach((paramName, paramDesc)->{
                    sb.append(paramName + ":" + paramDesc + " <br/>");
                });
                sb.append(" |");
                lines.add(sb.toString());
                paramMap.clear();
            }
        }
        return lines;
    }

    /**生成源代码内容
     * @param utilFilePath 工具类文件地址
     * @return: List 文件内容
     * @since 1.0
     * @author zongf
     * @created 2019-07-01
     */
    private static List<String> getSourceCode(String utilFilePath){
        // 读取源代码内容
        List<String> codeList = TxtFileUtil.readFile(utilFilePath);

        List<String> lines = new ArrayList<>();

        lines.add("### 2. 源码");
        lines.add("\n");
        lines.add("```java\n");
        lines.addAll(codeList);
        lines.add("```");
        lines.add("\n");

        return lines;
    }

    /**生成markdown文件
     * @param utilFilePath 工具类路径
     * @param mdFileDir md文件生成目录
     * @param lines 文件内容
     * @since 1.0
     * @author zongf
     * @created 2019-07-01
     */
    private static void createFile(String utilFilePath, String mdFileDir, List<String> lines) {
        // 获取文件简单名称
        String simpleName = StringUtils.substringAfterLast(utilFilePath, "/");

        // 获取markDown文件名称
        String mdFileName = simpleName.replace("java", "md");

        // 写成markDown 文件
        TxtFileUtil.writeFile(lines, mdFileDir + "/" + mdFileName);
    }
}
