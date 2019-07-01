import org.junit.Test;
import org.zongf.docs.MarkdownUtil;


/** markdown 文档工具类生成
 * @since 1.0
 * @author zongf
 * @created 2019-07-01
 */
public class MarkDownUtilTest {

    // 测试单个方法
    @Test
    public void test(){
        String filePath = "../utils-common/src/main/java/org/zongf/utils/common/util/ReflectUtil.java";
        MarkdownUtil.createMDDoc(filePath, "apis");
    }

    // 测试批量方法
    @Test
    public void createMDDocs(){
        String fileDir = "../utils-common/src/main/java/org/zongf/utils/common/util";
        String mdDir = "apis";
        MarkdownUtil.createMDDocs(fileDir, mdDir);
    }
}
