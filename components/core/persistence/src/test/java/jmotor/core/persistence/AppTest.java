package jmotor.core.persistence;

import jmotor.core.persistence.domain.SchedulerDomain;
import jmotor.core.persistence.generator.impl.SqlGeneratorDefaultImpl;
import jmotor.core.persistence.impl.AnnotationEntityMapping;
import jmotor.core.persistence.meta.TableMeta;
import junit.framework.TestCase;
import org.junit.Before;

/**
 * Component:
 * Description:
 * Date: 12-4-11
 *
 * @author Andy.Ai
 */
public class AppTest extends TestCase {
    private SchedulerDomain scheduler;
    private SqlGeneratorDefaultImpl sqlGenerator;

    @Before
    private void init() {
        scheduler = new SchedulerDomain();
        scheduler.setId(100L);
        scheduler.setName("jobScanner");
        EntityMappingManager entityMappingManager = new AnnotationEntityMapping();
        sqlGenerator = new SqlGeneratorDefaultImpl();
        sqlGenerator.setEntityMappingManager(entityMappingManager);
    }

    public void testEntityMapping() {
        EntityMappingManager entityMappingManager = new AnnotationEntityMapping();
        TableMeta tableMeta = entityMappingManager.registerEntity(SchedulerDomain.class);
        System.out.println(tableMeta.getTableName());
    }

    public void testSqlGeneratorInsert() {
        init();
        String sql = sqlGenerator.generateInsertSql(scheduler);
        System.out.println(sql);
    }

    public void testSqlGeneratorDelete() {
        init();
        String sql = sqlGenerator.generateDeleteSql(scheduler);
        System.out.println(sql);
    }

    public void testSqlGeneratorUpdate() {
        init();
        String sql = sqlGenerator.generateUpdateSql(scheduler);
        System.out.println(sql);
    }
}
