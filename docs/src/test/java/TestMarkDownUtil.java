import org.junit.Test;
import org.zongf.docs.MarkdownUtil;

/**
 * @Description:
 * @author: zongf
 * @date: 2019-06-12 15:44
 * @since 1.0
 */
public class TestMarkDownUtil {

    // 测试单个方法
    @Test
    public void test(){
        String filePath = "../utils-common/src/main/java/org/zongf/utils/common/util/AssertUtil.java";
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
