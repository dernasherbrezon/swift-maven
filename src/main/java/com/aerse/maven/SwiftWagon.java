package com.aerse.maven;

import java.io.File;
import java.util.List;

import org.apache.maven.wagon.ConnectionException;
import org.apache.maven.wagon.ResourceDoesNotExistException;
import org.apache.maven.wagon.TransferFailedException;
import org.apache.maven.wagon.Wagon;
import org.apache.maven.wagon.authentication.AuthenticationException;
import org.apache.maven.wagon.authentication.AuthenticationInfo;
import org.apache.maven.wagon.authorization.AuthorizationException;
import org.apache.maven.wagon.events.SessionListener;
import org.apache.maven.wagon.events.TransferListener;
import org.apache.maven.wagon.proxy.ProxyInfo;
import org.apache.maven.wagon.proxy.ProxyInfoProvider;
import org.apache.maven.wagon.repository.Repository;

public class SwiftWagon implements Wagon {

	@Override
	public void get(String resourceName, File destination) throws TransferFailedException, ResourceDoesNotExistException, AuthorizationException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean getIfNewer(String resourceName, File destination, long timestamp) throws TransferFailedException, ResourceDoesNotExistException, AuthorizationException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void put(File source, String destination) throws TransferFailedException, ResourceDoesNotExistException, AuthorizationException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void putDirectory(File sourceDirectory, String destinationDirectory) throws TransferFailedException, ResourceDoesNotExistException, AuthorizationException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean resourceExists(String resourceName) throws TransferFailedException, AuthorizationException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<String> getFileList(String destinationDirectory) throws TransferFailedException, ResourceDoesNotExistException, AuthorizationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean supportsDirectoryCopy() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Repository getRepository() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void connect(Repository source) throws ConnectionException, AuthenticationException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void connect(Repository source, ProxyInfo proxyInfo) throws ConnectionException, AuthenticationException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void connect(Repository source, ProxyInfoProvider proxyInfoProvider) throws ConnectionException, AuthenticationException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void connect(Repository source, AuthenticationInfo authenticationInfo) throws ConnectionException, AuthenticationException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void connect(Repository source, AuthenticationInfo authenticationInfo, ProxyInfo proxyInfo) throws ConnectionException, AuthenticationException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void connect(Repository source, AuthenticationInfo authenticationInfo, ProxyInfoProvider proxyInfoProvider) throws ConnectionException, AuthenticationException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void openConnection() throws ConnectionException, AuthenticationException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void disconnect() throws ConnectionException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setTimeout(int timeoutValue) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getTimeout() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setReadTimeout(int timeoutValue) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getReadTimeout() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void addSessionListener(SessionListener listener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeSessionListener(SessionListener listener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean hasSessionListener(SessionListener listener) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void addTransferListener(TransferListener listener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeTransferListener(TransferListener listener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean hasTransferListener(TransferListener listener) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isInteractive() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setInteractive(boolean interactive) {
		// TODO Auto-generated method stub
		
	}

}
