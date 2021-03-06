/*******************************************************************************
* Copyright (c) 2019 Red Hat Inc. and others.
* All rights reserved. This program and the accompanying materials
* which accompanies this distribution, and is available at
* http://www.eclipse.org/legal/epl-v20.html
*
* Contributors:
*     Red Hat Inc. - initial API and implementation
*******************************************************************************/
package com.redhat.devtools.intellij.quarkus.gradle;

import com.redhat.devtools.intellij.quarkus.search.PropertiesManager;
import com.redhat.devtools.intellij.quarkus.search.PsiUtilsImpl;
import org.apache.commons.io.FileUtils;
import org.eclipse.lsp4mp.commons.ClasspathKind;
import org.eclipse.lsp4mp.commons.DocumentFormat;
import org.eclipse.lsp4mp.commons.MicroProfileProjectInfo;
import org.eclipse.lsp4mp.commons.MicroProfilePropertiesScope;
import org.junit.Test;

import java.io.File;

import static com.redhat.devtools.intellij.quarkus.module.MicroProfileAssert.assertProperties;
import static com.redhat.devtools.intellij.quarkus.module.MicroProfileAssert.assertPropertiesDuplicate;
import static com.redhat.devtools.intellij.quarkus.module.MicroProfileAssert.p;

/**
 * Test the availability of the MicroProfile GraphQL properties
 *
 * @author Kathryn Kodama
 *
 */
public class GradleMicroProfileGraphQLTest extends GradleTestCase {

	@Override
	public void setUp() throws Exception {
		super.setUp();
		FileUtils.copyDirectory(new File("projects/gradle/microprofile-graphql"), new File(getProjectPath()));
		importProject();
	}

	@Test
	public void testMicroprofileGraphQL() throws Exception {

		MicroProfileProjectInfo infoFromClasspath = PropertiesManager.getInstance().getMicroProfileProjectInfo(getModule("microprofile-graphql.main"), MicroProfilePropertiesScope.SOURCES_AND_DEPENDENCIES, ClasspathKind.SRC, PsiUtilsImpl.getInstance(), DocumentFormat.PlainText);

		assertProperties(infoFromClasspath,

				p(null, "mp.graphql.defaultErrorMessage", "java.lang.String",
						null, true,
						"io.smallrye.graphql.cdi.config.GraphQLConfig", "defaultErrorMessage", null, 0, "Server Error"),

				p(null, "mp.graphql.hideErrorMessage", "java.util.Optional<java.util.List<java.lang.String>>",
						null, true,
						"io.smallrye.graphql.cdi.config.GraphQLConfig", "hideList", null, 0, ""),

				p(null, "mp.graphql.showErrorMessage", "java.util.Optional<java.util.List<java.lang.String>>",
						null, true,
						"io.smallrye.graphql.cdi.config.GraphQLConfig", "showList", null, 0, "")
		);

		assertPropertiesDuplicate(infoFromClasspath);
	}
}
