package com.aerse.swift.maven;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.maven.wagon.ConnectionException;
import org.apache.maven.wagon.ResourceDoesNotExistException;
import org.apache.maven.wagon.TransferFailedException;
import org.apache.maven.wagon.authentication.AuthenticationException;
import org.apache.maven.wagon.authentication.AuthenticationInfo;
import org.apache.maven.wagon.authorization.AuthorizationException;
import org.apache.maven.wagon.proxy.ProxyInfo;
import org.apache.maven.wagon.proxy.ProxyInfoProvider;
import org.apache.maven.wagon.repository.Repository;
import org.openstack4j.api.OSClient.OSClientV3;
import org.openstack4j.api.client.IOSClientBuilder.V3;
import org.openstack4j.core.transport.Config;
import org.openstack4j.core.transport.ProxyHost;
import org.openstack4j.model.common.DLPayload;
import org.openstack4j.model.common.payloads.InputStreamPayload;
import org.openstack4j.model.storage.object.SwiftObject;
import org.openstack4j.model.storage.object.options.ObjectListOptions;
import org.openstack4j.openstack.OSFactory;

public class SwiftWagon extends AbstractWagon {

	private OSClientV3 client;
	private String container;

	public SwiftWagon() {
		super(true);
	}

	@Override
	protected void connectToRepository(Repository repository, AuthenticationInfo authenticationInfo, ProxyInfoProvider proxyInfoProvider) throws ConnectionException, AuthenticationException {
		if (client != null) {
			return;
		}
		V3 builder = OSFactory.builderV3().endpoint("https://" + repository.getHost() + "/" + repository.getBasedir());
		if (authenticationInfo != null) {
			builder.credentials(authenticationInfo.getUserName(), authenticationInfo.getPassword());
		}
		if (proxyInfoProvider != null) {
			ProxyInfo proxyInfo = proxyInfoProvider.getProxyInfo("https");
			if (proxyInfo != null) {
				builder.withConfig(Config.newConfig().withProxy(convert(proxyInfo)));
			}
		}
		try {
			client = builder.authenticate();
			container = repository.getId();
		} catch (org.openstack4j.api.exceptions.AuthenticationException e) {
			throw new AuthenticationException("unable to authenticate", e);
		}
	}

	private static ProxyHost convert(ProxyInfo proxy) {
		return ProxyHost.of(proxy.getHost(), proxy.getPort(), proxy.getUserName(), proxy.getPassword());
	}

	@Override
	protected boolean doesRemoteResourceExist(String resourceName) throws TransferFailedException, AuthorizationException {
		SwiftObject obj = client.objectStorage().objects().get(container, resourceName);
		if (obj == null) {
			return false;
		}
		return true;
	}

	@Override
	protected void disconnectFromRepository() throws ConnectionException {
		this.client = null;
		this.container = null;
	}

	@Override
	protected void getResource(String resourceName, File destination, TransferProgress transferProgress) throws TransferFailedException, ResourceDoesNotExistException, AuthorizationException {
		SwiftObject object = client.objectStorage().objects().get(container, resourceName);
		if (object == null) {
			throw new ResourceDoesNotExistException("doesn't exist: " + resourceName);
		}
		DLPayload payload = object.download();
		InputStream in = null;
		OutputStream out = null;
		try {
			in = payload.getInputStream();
			out = new TransferProgressFileOutputStream(destination, transferProgress);
			IOUtils.copy(in, out);
		} catch (IOException e) {
			throw new TransferFailedException("unable to save", e);
		} finally {
			IOUtils.closeQuietly(in, out);
		}
	}

	@Override
	protected boolean isRemoteResourceNewer(String resourceName, long timestamp) throws TransferFailedException, ResourceDoesNotExistException, AuthorizationException {
		SwiftObject obj = client.objectStorage().objects().get(container, resourceName);
		if (obj == null) {
			throw new ResourceDoesNotExistException("doesn't exist: " + resourceName);
		}
		return obj.getLastModified().getTime() > timestamp;
	}

	@Override
	protected List<String> listDirectory(String directory) throws TransferFailedException, ResourceDoesNotExistException, AuthorizationException {
		List<? extends SwiftObject> objects = client.objectStorage().objects().list(container, ObjectListOptions.create().marker(directory));
		List<String> result = new ArrayList<>();
		for (SwiftObject cur : objects) {
			result.add(cur.getName());
		}
		return result;
	}

	@Override
	protected void putResource(File source, String destination, TransferProgress transferProgress) throws TransferFailedException, ResourceDoesNotExistException, AuthorizationException {
		InputStream in = null;
		try {
			in = new TransferProgressFileInputStream(source, transferProgress);
			client.objectStorage().objects().put(container, destination, new InputStreamPayload(in));
		} catch (FileNotFoundException e) {
			throw new TransferFailedException("unable to put: " + destination, e);
		} finally {
			IOUtils.closeQuietly(in);
		}
	}

}
