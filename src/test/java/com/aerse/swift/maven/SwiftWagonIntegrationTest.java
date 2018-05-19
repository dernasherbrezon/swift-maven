/*
 * Copyright 2010-2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.aerse.swift.maven;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.List;
import java.util.UUID;

import org.apache.maven.wagon.ConnectionException;
import org.apache.maven.wagon.ResourceDoesNotExistException;
import org.apache.maven.wagon.TransferFailedException;
import org.apache.maven.wagon.authentication.AuthenticationException;
import org.apache.maven.wagon.authentication.AuthenticationInfo;
import org.apache.maven.wagon.authorization.AuthorizationException;
import org.apache.maven.wagon.repository.Repository;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

@Ignore
public final class SwiftWagonIntegrationTest {

	@Rule
	public TemporaryFolder tempFolder = new TemporaryFolder();

	private SwiftWagon wagon;

	@Before
	public void start() throws ConnectionException, AuthenticationException {
		wagon = new SwiftWagon();
		Repository repo = new Repository("1", "https://api.selcdn.ru/v3");
		repo.setName("test");
		AuthenticationInfo auth = new AuthenticationInfo();
		auth.setUserName(System.getProperty("selectel.username"));
		auth.setPassword(System.getProperty("selectel.password"));
		wagon.connect(repo, auth);
	}

	@Test
	public void doesRemoteResourceExistDoesNotExist() throws TransferFailedException, AuthorizationException {
		assertFalse(wagon.doesRemoteResourceExist(UUID.randomUUID().toString()));
	}

	@Test
	public void doesRemoteResourceExistExist() throws TransferFailedException, ResourceDoesNotExistException, AuthorizationException {
		String destination = UUID.randomUUID().toString();
		File temp = createFile(UUID.randomUUID().toString());
		wagon.put(temp, destination);
		assertTrue(wagon.doesRemoteResourceExist(destination));
	}

	private File createFile(String data) {
		File temp = new File(tempFolder.getRoot(), UUID.randomUUID().toString());
		try (Writer fos = new BufferedWriter(new FileWriter(temp))) {
			fos.write(data);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return temp;
	}

	@Test
	public void isRemoteResourceNewerOlder() throws ResourceDoesNotExistException, TransferFailedException, AuthorizationException {
		String destination = UUID.randomUUID().toString();
		File temp = createFile(UUID.randomUUID().toString());
		wagon.put(temp, destination);
		assertFalse(wagon.isRemoteResourceNewer(destination, System.currentTimeMillis() + 10000));
	}

	@Test(expected = ResourceDoesNotExistException.class)
	public void isRemoteResourceNewerDoesNotExist() throws ResourceDoesNotExistException, TransferFailedException, AuthorizationException {
		wagon.isRemoteResourceNewer(UUID.randomUUID().toString(), 0);
	}

	@Test
	public void listDirectoryTopLevel() throws ResourceDoesNotExistException, TransferFailedException, AuthorizationException {
		String destination = UUID.randomUUID().toString() + "/" + UUID.randomUUID().toString();
		File temp = createFile(UUID.randomUUID().toString());
		wagon.put(temp, destination);
		List<String> directoryContents = wagon.listDirectory("");
		assertTrue(directoryContents.contains(destination));
	}

	@Test
	public void getResource() throws TransferFailedException, ResourceDoesNotExistException, AuthorizationException {
		String destination = UUID.randomUUID().toString();
		File temp = createFile(UUID.randomUUID().toString());
		wagon.put(temp, destination);

		File target = new File(tempFolder.getRoot(), UUID.randomUUID().toString());
		wagon.get(destination, target);
		assertTrue(target.exists());
	}

	@Test(expected = ResourceDoesNotExistException.class)
	public void getResourceSourceDoesNotExist() throws TransferFailedException, ResourceDoesNotExistException, AuthorizationException {
		this.wagon.get(UUID.randomUUID().toString(), new File(tempFolder.getRoot(), UUID.randomUUID().toString()));
	}

}
