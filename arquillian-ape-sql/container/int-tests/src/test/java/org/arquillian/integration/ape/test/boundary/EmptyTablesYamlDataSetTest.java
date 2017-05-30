package org.arquillian.integration.ape.test.boundary;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.arquillian.ape.rdbms.Cleanup;
import org.arquillian.ape.rdbms.CleanupStrategy;
import org.arquillian.ape.rdbms.TestExecutionPhase;
import org.arquillian.ape.rdbms.UsingDataSet;
import org.arquillian.integration.ape.example.UserAccount;
import org.arquillian.integration.ape.util.Query;
import org.arquillian.integration.ape.util.UserPersistenceAssertion;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.arquillian.ape.rdbms.BuiltInCleanupStrategy.USED_TABLES_ONLY;

@RunWith(Arquillian.class)
@Cleanup(phase = TestExecutionPhase.BEFORE)
public class EmptyTablesYamlDataSetTest {

    @PersistenceContext
    EntityManager em;

    @Deployment
    public static Archive<?> createDeploymentPackage() {
        return ShrinkWrap.create(WebArchive.class, "test.war")
            .addPackage(UserAccount.class.getPackage())
            .addClasses(Query.class, UserPersistenceAssertion.class)
            // required for remote containers in order to run tests with FEST-Asserts
            .addPackages(true, "org.assertj.core")
            .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
            .addAsResource("test-persistence.xml", "META-INF/persistence.xml");
    }

    @Test
    @InSequence(1)
    @UsingDataSet("users.json")
    public void should_fail_when_empty_set_yaml_used_for_verifying_content_of_non_empty_database() {
        new UserPersistenceAssertion(em).assertUserAccountsStored();
    }

    @Test
    @InSequence(2)
    @UsingDataSet("empty/empty-tables.yml")
    @Cleanup(phase = TestExecutionPhase.BEFORE)
    @CleanupStrategy(USED_TABLES_ONLY)
    public void should_clean_when_yaml_with_empty_tables_provided() throws Exception {
        new UserPersistenceAssertion(em).assertNoUserAccountsStored();
    }
}