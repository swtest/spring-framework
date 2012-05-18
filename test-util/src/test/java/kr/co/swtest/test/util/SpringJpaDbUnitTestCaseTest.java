package kr.co.swtest.test.util;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * SpringJpaDbUnitTestCase 테스트케이스 <br/>
 * 예제역할도 함.
 *
 * @author <a href="mailto:davidchoi@nextree.co.kr">최영목</a>
 * @since 2012. 5. 19.
 */
public class SpringJpaDbUnitTestCaseTest extends SpringJpaDbUnitTestCase {

	@Override
	protected DbUnitDataType getDbUnitDataType() {
		return DbUnitDataType.HSQLDB;
	}

    // -------------------------------------------------------------------------
    // Test Method
    // -------------------------------------------------------------------------

    @Test
    public void test() {
        assertTrue(true);
    }

}
