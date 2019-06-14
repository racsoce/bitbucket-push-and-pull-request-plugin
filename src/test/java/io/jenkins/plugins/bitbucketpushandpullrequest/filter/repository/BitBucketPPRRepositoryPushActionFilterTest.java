package io.jenkins.plugins.bitbucketpushandpullrequest.filter.repository;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class BitBucketPPRRepositoryPushActionFilterTest {
  
  @Test
  public void testMatches() {
    String allowedBranches = "develop,feature/*";
    BitBucketPPRRepositoryPushActionFilter classUnderTest =
        new BitBucketPPRRepositoryPushActionFilter(false, allowedBranches);

    assertTrue(classUnderTest.matches("develop", allowedBranches));
    assertTrue(classUnderTest.matches("feature/new-stuff", allowedBranches));
  }
  
  @Test
  public void testMatches_not() {
    String allowedBranches = "develop";
    BitBucketPPRRepositoryPushActionFilter classUnderTest =
        new BitBucketPPRRepositoryPushActionFilter(false, allowedBranches);

    assertFalse(classUnderTest.matches("master", allowedBranches));
  }
  
  @Test
  public void testMatches_empty_branches()
  {
    String allowedBranches = "";
    BitBucketPPRRepositoryPushActionFilter classUnderTest =
        new BitBucketPPRRepositoryPushActionFilter(false, allowedBranches);

    assertTrue(classUnderTest.matches("master", allowedBranches));
    assertTrue(classUnderTest.matches("develop", allowedBranches));
    assertTrue(classUnderTest.matches("feature/new-stuff", allowedBranches));
  }
}