package org.eco.mubisoft.good_and_cheap;

import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

@Suite
@SelectPackages({
        "org.eco.mubisoft.good_and_cheap.application",
        "org.eco.mubisoft.good_and_cheap.product",
        "org.eco.mubisoft.good_and_cheap.recipe",
        "org.eco.mubisoft.good_and_cheap.control"
})
@SuiteDisplayName("Good and Cheap application's tests")
public class GoodAndCheapApplicationSuite {
}