/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.metamodel.couchdb;

import java.io.File;
import java.io.FileReader;
import java.util.Properties;

import junit.framework.TestCase;

public abstract class CouchDbTestCase extends TestCase {
    
    private static final String DEFAULT_TEST_DATABASE_NAME = "eobjects_metamodel_test";

    private String _hostname;
    private boolean _configured;
    private String _databaseName;

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        Properties properties = new Properties();
        File file = new File(getPropertyFilePath());
        if (file.exists()) {
            properties.load(new FileReader(file));
            _hostname = properties.getProperty("couchdb.hostname");
            _databaseName = properties.getProperty("couchdb.databaseName");
            if (_databaseName == null || _databaseName.isEmpty()) {
                _databaseName = DEFAULT_TEST_DATABASE_NAME;
            }
            
            _configured = (_hostname != null && !_hostname.isEmpty());
            
            if (_configured) {
                System.out.println("Loaded CouchDB configuration. Hostname=" + _hostname + ", Database=" + _databaseName);
            }
        } else {
            _configured = false;
        }
    }

    private String getPropertyFilePath() {
        String userHome = System.getProperty("user.home");
        return userHome + "/metamodel-integrationtest-configuration.properties";
    }

    protected String getInvalidConfigurationMessage() {
        return "!!! WARN !!! CouchDB module ignored\r\n" + "Please configure couchdb connection locally ("
                + getPropertyFilePath() + "), to run integration tests";
    }

    public boolean isConfigured() {
        return _configured;
    }
    
    public String getHostname() {
        return _hostname;
    }

    public String getDatabaseName() {
        return _databaseName;
    }
}
